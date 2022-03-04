package com.creativehub.backend.models;

import com.creativehub.backend.models.enums.StatusRichiestaUpgrade;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "RichiesteUpgrade")
public class RichiestaUpgrade {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@ToString.Exclude
	@OneToOne(optional = false)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cognome")
	private String cognome;

	@Column(name = "bio")
	private String bio;

	@Column(name = "portfolio", columnDefinition = "TEXT")
	private String portfolio;

	@Column(name = "testoMotivazione")
	private String testoMotivazione;

	@Column(name = "nomeArte")
	private String nomeArte;

	@Column(name = "dataNascira")
	private Date dataNascita;

	@Column(name = "username")
	private String username;

	@Column(name = "avatar", columnDefinition = "TEXT")
	private String avatar;

	@Enumerated(EnumType.STRING)
	@Column(name = "statusRichiestaUpgrade", nullable = false)
	private StatusRichiestaUpgrade statusRichiestaUpgrade;
}
