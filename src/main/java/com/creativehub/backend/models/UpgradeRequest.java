package com.creativehub.backend.models;

import com.creativehub.backend.models.enums.CreatorType;
import com.creativehub.backend.models.enums.UpgradeRequestStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "bio", nullable = false)
	private String bio;

	@Column(name = "portfolio", nullable = false, columnDefinition = "TEXT")
	private String portfolio;

	@Column(name = "motivationalText", nullable = false)
	private String motivationalText;

	@Column(name = "artname", nullable = false)
	private String artName;

	@Column(name = "birthdate", nullable = false)
	private Date birthDate;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "avatar", nullable = false, columnDefinition = "TEXT")
	private String avatar;

	@Column(name = "payment_email", nullable = false)
	private String paymentEmail;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private UpgradeRequestStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name = "creator_type", nullable = false)
	private CreatorType creatorType;
}
