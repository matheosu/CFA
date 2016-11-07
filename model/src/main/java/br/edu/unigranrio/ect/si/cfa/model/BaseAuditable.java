package br.edu.unigranrio.ect.si.cfa.model;

import br.edu.unigranrio.ect.si.cfa.commons.model.Auditable;
import br.edu.unigranrio.ect.si.cfa.commons.util.DateTimeUtils;
import br.edu.unigranrio.ect.si.cfa.commons.model.listener.AuditableListener;

import javax.persistence.*;
import java.util.Calendar;

@MappedSuperclass
@EntityListeners({AuditableListener.class})
public abstract class BaseAuditable<PK extends Number> extends BaseEntity<PK> implements Auditable<PK> {

	private static final long serialVersionUID = 8554506860263888514L;

	@ManyToOne
	@JoinColumn(name = "userCreatedId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "userCreated_fk"))
	private User userCreated;

	@Column(name = "userCreatedId")
	private Long userCreatedId;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateCreated;

	@ManyToOne
	@JoinColumn(name = "userUpdatedId", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "userUpdated_fk"))
	private User userUpdated;

	@Column(name = "userUpdatedId")
	private Long userUpdatedId;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateUpdated;

	@Version
	private Integer version;

	public User getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(User userCreated) {
		this.userCreated = userCreated;
	}

	@Override
	public Long getUserCreatedId() {
		return userCreatedId;
	}

	@Override
	public void setUserCreatedId(Long userCreatedId) {
		this.userCreatedId = userCreatedId;
	}

	@Override
	public Calendar getDateCreated() {
		return dateCreated;
	}

	@Override
	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(User userUpdated) {
		this.userUpdated = userUpdated;
	}

	@Override
	public Long getUserUpdatedId() {
		return userUpdatedId;
	}

	@Override
	public void setUserUpdatedId(Long userUpdatedId) {
		this.userUpdatedId = userUpdatedId;
	}

	@Override
	public Calendar getDateUpdated() {
		return dateUpdated;
	}

	@Override
	public void setDateUpdated(Calendar dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return super.toString()+ "Auditable [" +
				"userCreatedId:" + userCreatedId +
				", dateCreated:" + DateTimeUtils.calendar2String(dateCreated) +
				", userUpdatedId:" + userUpdatedId +
				", dateUpdated:" + DateTimeUtils.calendar2String(dateUpdated) +
				", version:" + version +
				"]" ;
	}
}