package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.commons.model.SessionInfo;
import br.edu.unigranrio.ect.si.cfa.commons.model.SessionUser;
import br.edu.unigranrio.ect.si.cfa.commons.model.User;
import br.edu.unigranrio.ect.si.cfa.commons.util.Constants;
import br.edu.unigranrio.ect.si.cfa.commons.util.SecurityUtils;
import br.edu.unigranrio.ect.si.cfa.service.AuthService;
import br.edu.unigranrio.ect.si.cfa.service.LocalMessage;
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

@Named
public class AuthServiceBean implements AuthService {

    private static final long serialVersionUID = 3826447269146100577L;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceBean.class);
    private static final String USER_AGENT = "user-agent";

    /* HTTP */
    @Inject HttpSession session;
    @Inject HttpServletRequest request;

    /* Services */
    @Inject UserService userService;
    @Inject LocalMessage localMessage;
    @Inject SessionUserService sessionUserService;

    @Override
    @Transactional
    public User doLogin(String email, String password) throws AuthException {
        // Identify User
        User user = identify(email);

        // Verify if User is Active
        if(!user.isActive()) {
            String inactiveMsg = localMessage.get("user.inactive", user);
            logger.warn(inactiveMsg);
            throw new AuthException(inactiveMsg);
        }

        // Verify Password
        if (!verifyPassword(user, password)) {
            String wrongPsswdMsg = localMessage.get("user.password.wrong");
            logger.warn(user +": " + wrongPsswdMsg);
            throw new AuthException(wrongPsswdMsg);
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

    private SessionInfo createSessionInfo(User user)  {
        Locale userLocale = request.getLocale();
        InetAddress inetAddr = null;

        try {
            inetAddr = InetAddress.getByName(request.getRemoteAddr());
        } catch (UnknownHostException e) {
            logger.warn(localMessage.get("error.discovery.inetaddr", user), e);
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
            logger.info(localMessage.get("user.session.create", sessionUser));
        } catch (Exception e) {
            logger.error(localMessage.get("user.session.create.error",user), e);
            throw new AuthException(localMessage.get("user.session.login.error"), e);
        }
    }

    private void closeSession(SessionUser sessionUser) {
        if(sessionUser != null){
            sessionUser.setLogoutDate(Calendar.getInstance());
            sessionUser = sessionUserService.update(sessionUser);
            logger.info(localMessage.get("user.session.close", sessionUser));
        }
    }

    private void logoutSession(User user) throws AuthException {
        try{
            user = userService.find(User.class, user.getId());

            // Find Active Session User
            SessionUser sessionUser = sessionUserService.findActiveSessionUser(user);

            // Close session user if exist active session
            closeSession(sessionUser);
        } catch (Exception e) {
            String logoutErrorMsg = localMessage.get("user.session.logout.error");
            logger.error(logoutErrorMsg, e);
            throw new AuthException(logoutErrorMsg, e);
        }
    }

    private User identify(String email) throws AuthException {
        User user = userService.findUserByEmail(email);
        if(user == null) {
            String notFoundMsg = localMessage.get("user.notfound", email);
            logger.warn(notFoundMsg);
            throw new AuthException(notFoundMsg);
        }

        return user;
    }

    private boolean verifyPassword(User user, String password)  {
        String encryptPassword = SecurityUtils.encrypt(password);
        return user != null && user.getPassword().equals(encryptPassword);
    }
}
