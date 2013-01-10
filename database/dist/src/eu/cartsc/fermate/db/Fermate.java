package eu.cartsc.fermate.db;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Fermate entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "fermate")
public class Fermate extends AbstractFermate implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Fermate() {
	}

	/** full constructor */
	public Fermate(String idimei, String nomeFermata, String indirizzo,
			String descrizione, Timestamp data) {
		super(idimei, nomeFermata, indirizzo, descrizione, data);
	}

}
