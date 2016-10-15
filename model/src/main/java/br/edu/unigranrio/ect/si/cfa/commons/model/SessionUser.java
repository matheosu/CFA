package br.edu.unigranrio.ect.si.cfa.commons.model;


import br.edu.unigranrio.ect.si.cfa.commons.util.DateTimeUtils;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Calendar;

@Entity
@Table(name = "\"sessionUser\"")
public class SessionUser extends BaseEntity<Long> {

	private static final long serialVersionUID = -8067729738451603353L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_session_user")
	@SequenceGenerator(name = "generator_session_user", sequenceName = "seq_session_user", allocationSize = 1)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar loginDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar logoutDate;

	@Embedded
	private SessionInfo sessionInfo;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_fk"))
	private User user;

	public SessionUser() {
	}

	public SessionUser(Calendar loginDate, User user) {
		this();
		setLoginDate(loginDate);
		setUser(user);
		setLogoutDate(null);
	}

	public SessionUser(Calendar loginDate, SessionInfo sessionInfo, User user) {
		this(loginDate, user);
		setSessionInfo(sessionInfo);
	}

	public SessionUser(Calendar loginDate, Calendar logoutDate, SessionInfo sessionInfo, User user) {
		this(loginDate, sessionInfo, user);
		setLogoutDate(logoutDate);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Calendar loginDate) {
		this.loginDate = loginDate;
	}

	public Calendar getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Calendar logoutDate) {
		this.logoutDate = logoutDate;
	}

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SessionUser that = (SessionUser) o;

        return getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }

    @Override
	public String toString() {
		return super.toString() +
				" [ LoginDate: " + DateTimeUtils.calendar2String(getLoginDate()) + " ]" +
				" [ LogoutDate: " + DateTimeUtils.calendar2String(getLogoutDate()) + " ]" +
				" [ UserId: " + getUser().getId() + " ]" +
                " [ UserName: " + getUser().getName() + " ]" +
                " [ UserEmail: " + getUser().getEmail() + " ]";
	}
}
