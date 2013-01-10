package eu.cartsc.fermate.db;

import javax.persistence.Entity;
import org.apache.log4j.*; 
import javax.persistence.Table;

/**
 * Operatore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "operatore")
public class Operatore extends AbstractOperatore implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public Operatore() {
	}

	/** minimal constructor */
	public Operatore(String idimei) {
		super(idimei);
	}

	/** full constructor */
	public Operatore(String idimei, String anagrafica) {
		super(idimei, anagrafica);
	}

}
