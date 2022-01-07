import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class RichiestaCollaborazione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "idMittente")
    private Long idMittente;
    @Column(name = "idDestinatario")
    private Long idDestinatario;
    @Column(name = "Titolo")
    private String titolo;
    @Column(name = "Descrizione")
    private String descrizione;
    @Column(name = "timeStamp")
    private Timestamp timestamp;
    @Column(name = "contatto")
    private String contatto; //FIXME: contatto come?
    @Column(name = "categoria")
    private String categoria; //FIXME: opzionale

    public RichiestaCollaborazione(Long idMittente, Long idDestinatario, String titolo, String descrizione, Timestamp timestamp, String contatto, String categoria) {
        this.idMittente = idMittente;
        this.idDestinatario = idDestinatario;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.timestamp = timestamp;
        this.contatto = contatto;
        this.categoria = categoria;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMittente() {
        return idMittente;
    }

    public void setIdMittente(Long idMittente) {
        this.idMittente = idMittente;
    }

    public Long getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Long idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContatto() {
        return contatto;
    }

    public void setContatto(String contatto) {
        this.contatto = contatto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Id
    public Long getId() {
        return id;
    }
}
