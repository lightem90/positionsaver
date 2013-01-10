package eu.cartsc.fermate.db;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Fermate entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see eu.cartsc.fermate.db.Fermate
 * @author MyEclipse Persistence Tools
 */

public class FermateDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(FermateDAO.class);
	// property constants
	public static final String IDIMEI = "idimei";
	public static final String NOME_FERMATA = "nomeFermata";
	public static final String INDIRIZZO = "indirizzo";
	public static final String DESCRIZIONE = "descrizione";

	public void save(Fermate transientInstance) {
		log.debug("saving Fermate instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Fermate persistentInstance) {
		log.debug("deleting Fermate instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Fermate findById(java.lang.Integer id) {
		log.debug("getting Fermate instance with id: " + id);
		try {
			Fermate instance = (Fermate) getSession().get(
					"eu.cartsc.fermate.db.Fermate", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Fermate> findByExample(Fermate instance) {
		log.debug("finding Fermate instance by example");
		try {
			List<Fermate> results = (List<Fermate>) getSession()
					.createCriteria("eu.cartsc.fermate.db.Fermate")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Fermate instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Fermate as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Fermate> findByIdimei(Object idimei) {
		return findByProperty(IDIMEI, idimei);
	}

	public List<Fermate> findByNomeFermata(Object nomeFermata) {
		return findByProperty(NOME_FERMATA, nomeFermata);
	}

	public List<Fermate> findByIndirizzo(Object indirizzo) {
		return findByProperty(INDIRIZZO, indirizzo);
	}

	public List<Fermate> findByDescrizione(Object descrizione) {
		return findByProperty(DESCRIZIONE, descrizione);
	}

	public List findAll() {
		log.debug("finding all Fermate instances");
		try {
			String queryString = "from Fermate";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Fermate merge(Fermate detachedInstance) {
		log.debug("merging Fermate instance");
		try {
			Fermate result = (Fermate) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Fermate instance) {
		log.debug("attaching dirty Fermate instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Fermate instance) {
		log.debug("attaching clean Fermate instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}