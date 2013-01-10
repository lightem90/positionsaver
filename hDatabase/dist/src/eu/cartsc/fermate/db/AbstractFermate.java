package eu.cartsc.fermate.db;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractFermate entity provides the base persistence definition of the
 * Fermate entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractFermate implements java.io.Serializable {

	// Fields

	private Integer idaut;
	private String idimei;
	private String nomeFermata;
	private String indirizzo;
	private String descrizione;
	private Timestamp data;

	// Constructors

	/** default constructor */
	public AbstractFermate() {
	}

	/** full constructor */
	public AbstractFermate(String idimei, String nomeFermata, String indirizzo,
			String descrizione, Timestamp data) {
		this.idimei = idimei;
		this.nomeFermata = nomeFermata;
		this.indirizzo = indirizzo;
		this.descrizione = descrizione;
		this.data = data;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idaut", unique = true, nullable = false)
	public Integer getIdaut() {
		return this.idaut;
	}

	public void setIdaut(Integer idaut) {
		this.idaut = idaut;
	}

	@Column(name = "idimei", length = 15)
	public String getIdimei() {
		return this.idimei;
	}

	public void setIdimei(String idimei) {
		this.idimei = idimei;
	}

	@Column(name = "nomeFermata", length = 65535)
	public String getNomeFermata() {
		return this.nomeFermata;
	}

	public void setNomeFermata(String nomeFermata) {
		this.nomeFermata = nomeFermata;
	}

	@Column(name = "indirizzo", length = 65535)
	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	@Column(name = "descrizione", length = 65535)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "data", length = 19)
	public Timestamp getData() {
		return this.data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

}