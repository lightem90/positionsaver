package eu.cartsc.fermate.db;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Operatore entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see eu.cartsc.fermate.db.Operatore
 * @author MyEclipse Persistence Tools
 */

public class OperatoreDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(OperatoreDAO.class);
	// property constants
	public static final String ANAGRAFICA = "anagrafica";

	public void save(Operatore transientInstance) {
		log.debug("saving Operatore instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Operatore persistentInstance) {
		log.debug("deleting Operatore instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Operatore findById(java.lang.String id) {
		log.debug("getting Operatore instance with id: " + id);
		try {
			Operatore instance = (Operatore) getSession().get(
					"eu.cartsc.fermate.db.Operatore", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Operatore> findByExample(Operatore instance) {
		log.debug("finding Operatore instance by example");
		try {
			List<Operatore> results = (List<Operatore>) getSession()
					.createCriteria("eu.cartsc.fermate.db.Operatore")
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
		log.debug("finding Operatore instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Operatore as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Operatore> findByAnagrafica(Object anagrafica) {
		return findByProperty(ANAGRAFICA, anagrafica);
	}

	public List findAll() {
		log.debug("finding all Operatore instances");
		try {
			String queryString = "from Operatore";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Operatore merge(Operatore detachedInstance) {
		log.debug("merging Operatore instance");
		try {
			Operatore result = (Operatore) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Operatore instance) {
		log.debug("attaching dirty Operatore instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Operatore instance) {
		log.debug("attaching clean Operatore instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}