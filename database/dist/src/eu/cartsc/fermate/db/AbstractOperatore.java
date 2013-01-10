package eu.cartsc.fermate.db;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractOperatore entity provides the base persistence definition of the
 * Operatore entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractOperatore implements java.io.Serializable {

	// Fields

	private String idimei;
	private String anagrafica;

	// Constructors

	/** default constructor */
	public AbstractOperatore() {
	}

	/** minimal constructor */
	public AbstractOperatore(String idimei) {
		this.idimei = idimei;
	}

	/** full constructor */
	public AbstractOperatore(String idimei, String anagrafica) {
		this.idimei = idimei;
		this.anagrafica = anagrafica;
	}

	// Property accessors
	@Id
	@Column(name = "idimei", unique = true, nullable = false, length = 15)
	public String getIdimei() {
		return this.idimei;
	}

	public void setIdimei(String idimei) {
		this.idimei = idimei;
	}

	@Column(name = "anagrafica", length = 65535)
	public String getAnagrafica() {
		return this.anagrafica;
	}

	public void setAnagrafica(String anagrafica) {
		this.anagrafica = anagrafica;
	}

}