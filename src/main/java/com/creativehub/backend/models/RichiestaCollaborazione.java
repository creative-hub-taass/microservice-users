package com.creativehub.backend.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name = "RichiesteCollaborazione")
public class RichiestaCollaborazione {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@ToString.Exclude
	@OneToOne(optional = false)
	@JoinColumn(name = "idMittente", nullable = false)
	private User mittente;

	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "idDestinatario")
	private User destinatario;

	@Column(name = "titolo")
	private String titolo;

	@Column(name = "descrizione")
	private String descrizione;

	@Column(name = "timestamp")
	private Timestamp timestamp;

	@Column(name = "contatto")
	private String contatto; //FIXME: contatto come?

	@Column(name = "categoria")
	private String categoria;
}
