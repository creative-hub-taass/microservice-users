package com.creativehub.backend.models;

import com.creativehub.backend.models.enums.CreatorType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "creator")
public class Creator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private UUID id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "birth_date", nullable = false)
	private Date birthDate;

	@Column(name = "bio", nullable = false, columnDefinition = "TEXT")
	private String bio;

	@Enumerated(EnumType.STRING)
	@Column(name = "creator_type", nullable = false)
	private CreatorType creatorType;

	@Column(name = "avatar", nullable = false, columnDefinition = "TEXT")
	private String avatar;

	@Column(name = "payment_email", nullable = false)
	private String paymentEmail;
}