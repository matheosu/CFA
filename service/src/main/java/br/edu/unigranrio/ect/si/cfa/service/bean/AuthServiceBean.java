package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.commons.util.Constants;
import br.edu.unigranrio.ect.si.cfa.commons.util.Securities;
import br.edu.unigranrio.ect.si.cfa.model.SessionInfo;
import br.edu.unigranrio.ect.si.cfa.model.SessionUser;
import br.edu.unigranrio.ect.si.cfa.model.User;
import br.edu.unigranrio.ect.si.cfa.service.AuthService;
import br.edu.unigranrio.ect.si.cfa.service.SessionUserService;
import br.edu.unigranrio.ect.si.cfa.service.UserService;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.service.exception.AuthException;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Locale;

import static br.edu.unigranrio.ect.si.cfa.service.exception.AuthException.Type;

@Named
public class AuthServiceBean implements AuthService {

    private static final long serialVersionUID = 3826447269146100577L;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceBean.class);
    private static final String USER_AGENT = "user-agent";

    /* HTTP */
    private final HttpSession session;
    private final HttpServletRequest request;

    /* Services */
    private final UserService userService;
    private final SessionUserService sessionUserService;

    @Inject
    public AuthServiceBean(HttpSession session, HttpServletRequest request,
                           UserService userService, SessionUserService sessionUserService) {
        this.session = session;
        this.request = request;
        this.userService = userService;
        this.sessionUserService = sessionUserService;
    }

    @Override
    @Transactional
    public User doLogin(String email, String password) throws AuthException {
        // Identify User
        User user = identify(email);

        // Verify if User is Active
        if (!user.isActive()) {
            String inactiveMsg = "User Inactive " + user;
            logger.warn(inactiveMsg);
            throw new AuthException(inactiveMsg, Type.USER_INACTIVE);
        }

        // Verify Password
        if (!verifyPassword(user, password)) {
            String wrongPasswordMsg = "User " + user.getName() + " Wrong Password";
            logger.warn(wrongPasswordMsg);
            throw new AuthException(wrongPasswordMsg, Type.INVALID_PASSWORD);
        }

        // Login Session User
        loginSession(user);
        return user;
    }

    @Override
    @Transactional
    public void doLogout(User user) throws AuthException {
        // Logout Active Session
        logoutSession(user);

        // Invalidate Session
        session.invalidate();
    }

    private SessionInfo createSessionInfo(User user) {
        Locale userLocale = request.getLocale();
        InetAddress inetAddr = null;

        try {
            inetAddr = InetAddress.getByName(request.getRemoteAddr());
        } catch (UnknownHostException e) {
            logger.warn("Erro ao descobrir InetAddrs para o usuário {}", user, e);
        }

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader(USER_AGENT));
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();

        return new SessionInfo(userLocale, inetAddr, browser.getName(), os.getName());
    }

    private void loginSession(User user) throws AuthException {
        try {
            // Find Active Session User
            SessionUser sessionUser = sessionUserService.findActiveSessionUser(user);

            // Close session user if exist session active
            closeSession(sessionUser);

            // Create new Session
            sessionUser = new SessionUser(Calendar.getInstance(), createSessionInfo(user), user);
            sessionUserService.save(sessionUser);

            session.setAttribute(Constants.LOGGED_USER_ATTR, user.getId());
            logger.info("User Session Create {}", sessionUser);
        } catch (Exception e) {
            logger.error("User Session Create Error {}", user, e);
            throw new AuthException(e, Type.LOGIN);
        }
    }

    private void logoutSession(User user) throws AuthException {
        try {
            if (!userService.contains(user))
                user = userService.find(User.class, user.getId()).orElseThrow(() ->
                        new AuthException("User not found for logout", Type.LOGOUT));

            // Find Active Session User
            SessionUser sessionUser = sessionUserService.findActiveSessionUser(user);

            // Close session user if exist active session
            closeSession(sessionUser);
        } catch (Exception e) {
            logger.error("User Session Logout Error {}", user, e);
            throw new AuthException(e, Type.LOGOUT);
        }
    }

    private void closeSession(SessionUser sessionUser) {
        if (sessionUser != null) {
            sessionUser.setLogoutDate(Calendar.getInstance());
            sessionUser = sessionUserService.update(sessionUser);
            logger.info("User Session Close {}", sessionUser);
        }
    }

    private User identify(String email) throws AuthException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            logger.warn("User not found {}", email);
            throw new AuthException(Type.USER_NOT_FOUND);
        }

        return user;
    }

    private boolean verifyPassword(User user, String password) {
        String encryptPassword = Securities.encrypt(password);
        return user != null && user.getPassword().equals(encryptPassword);
    }
}
