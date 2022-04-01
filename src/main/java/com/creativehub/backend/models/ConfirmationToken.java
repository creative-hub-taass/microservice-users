package com.creativehub.backend.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_sequence")
	@SequenceGenerator(name = "confirmation_token_sequence")
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime expiresAt;

	private LocalDateTime confirmedAt;

	@ManyToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
}