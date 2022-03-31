package com.creativehub.backend.models;

import com.creativehub.backend.models.enums.CreatorType;
import com.creativehub.backend.models.enums.UpgradeRequestStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "upgrade_requests")
public class UpgradeRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@ToString.Exclude
	@ManyToOne(optional = false)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "bio")
	private String bio;

	@Column(name = "portfolio", columnDefinition = "TEXT")
	private String portfolio;

	@Column(name = "motivationalText")
	private String motivationalText;

	@Column(name = "artname")
	private String artName;

	@Column(name = "birthdate")
	private Date birthDate;

	@Column(name = "username")
	private String username;

	@Column(name = "avatar", columnDefinition = "TEXT")
	private String avatar;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private UpgradeRequestStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name = "creator_type", nullable = false)
	private CreatorType creatorType;
}
