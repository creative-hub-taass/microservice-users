import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RichiesteUpgrade")
public class RichiestaUpgrade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cognome")
    private String cognome;
    @Column(name = "bio")
    private String bio;
    @Column(name = "portfolio")
    private byte[] portfolio;
    @Column(name = "testoMotivazione")
    private String testoMotivazione;
    @Column(name = "nomeArte")
    private String nomeArte;
    @Column(name = "dataNascira")
    private Date dataNascita;
    @Column(name = "username")
    private String username;
    @Column(name = "avatar")
    private byte[] avatar;
    @Column(name = "status")
    private String status; //FIXME: era una string?

    public RichiestaUpgrade(Long userId,
                            String nome,
                            String cognome,
                            String bio,
                            byte[] portfolio,
                            String testoMotivazione,
                            String  nomeArte,
                            Date dataNascita,
                            String username,
                            byte[] avatar,
                            String status) {
        this.userId = userId;
        this.nome = nome;
        this.cognome = cognome;
        this.bio = bio;
        this.portfolio = portfolio;
        this.testoMotivazione = testoMotivazione;
        this.nomeArte = nomeArte;
        this.dataNascita = dataNascita;
        this.username = username;
        this.avatar = avatar;
        this.status = status;
    }

    public RichiestaUpgrade() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public byte[] getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(byte[] portfolio) {
        this.portfolio = portfolio;
    }

    public String getTestoMotivazione() {
        return testoMotivazione;
    }

    public void setTestoMotivazione(String testoMotivazione) {
        this.testoMotivazione = testoMotivazione;
    }

    public String getNomeArte() {
        return nomeArte;
    }

    public void setNomeArte(String nomeArte) {
        this.nomeArte = nomeArte;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
