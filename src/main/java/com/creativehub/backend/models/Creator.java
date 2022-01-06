package com.creativehub.backend.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "creator")
public class Creator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "birth_date", nullable = false)
	private Date birthDate;

	@Column(name = "bio", nullable = false, columnDefinition="TEXT")
	private String bio;

	@Enumerated(EnumType.STRING)
	@Column(name = "creator_type", nullable = false)
	private CreatorType creatorType;

	@Lob
	@Column(name = "avatar", nullable = false)
	private byte[] avatar;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Creator creator = (Creator) o;
		return id != null && Objects.equals(id, creator.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}