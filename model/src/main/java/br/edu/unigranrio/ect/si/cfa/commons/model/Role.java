package br.edu.unigranrio.ect.si.cfa.commons.model;

import java.util.Collections;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "\"Role\"")
public class Role extends BaseAuditable<Long> {

	private static final long serialVersionUID = 3505187003425922556L;

	/**
	 * Default System Role ID
	 * */
	public static final Long systemRoleId = 1L;

	public static final int MAX_LENGTH_NAME = 100;
	public static final int MAX_LENGTH_DESCRIPTION = 200;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_role")
	@SequenceGenerator(name = "generator_role", sequenceName = "seq_role", allocationSize = 1, initialValue = 100)
	private Long id;

	@Column(nullable = false, length = MAX_LENGTH_NAME)
	private String name;

	@Column(length = MAX_LENGTH_DESCRIPTION)
	private String description;

	@OneToMany(mappedBy = "role", cascade = CascadeType.DETACH)
	private List<User> users;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "role_features",
			joinColumns = {
					@JoinColumn(name = "role_id", nullable = false, updatable = false,
							referencedColumnName = "id", foreignKey = @ForeignKey(name = "role_fk"))
			},
			inverseJoinColumns = {
					@JoinColumn(name = "feature_id",	nullable = false, updatable = false,
							referencedColumnName = "id", foreignKey = @ForeignKey(name = "feature_fk"))
			})
	private List<Feature> features;

	public Role() {
		initCollections();
	}

	public Role(String name, List<Feature> features) {
		setName(name);
		setFeatures(features);
	}

	public Role(String name, String description, List<Feature> features) {
		this(name, features);
		setDescription(description);
	}


	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> user) {
		this.users = user;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	private void initCollections() {
		setUsers(Collections.emptyList());
		setFeatures(Collections.emptyList());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Role role = (Role) o;

		return getId().equals(role.getId());

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + getId().hashCode();
		return result;
	}

	@Override
	public String toString() {
		return super.toString() + " [ Name: " + getName() + "] [ Description: " + getDescription() + "]";
	}
}
