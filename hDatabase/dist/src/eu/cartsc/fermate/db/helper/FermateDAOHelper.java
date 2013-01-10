package eu.cartsc.fermate.db.helper;

import java.util.List;

import org.hibernate.Query;

import eu.cartsc.fermate.db.Fermate;
import eu.cartsc.fermate.db.FermateDAO;

public class FermateDAOHelper extends FermateDAO {
	
	
	public List findByPropertyOrd(String propertyName, String value) {
		
		try {
			String queryString = "from Fermate as model where model."
					+ propertyName + "= ?" + " order by model.data desc ";
			Query q = getSession().createQuery(queryString);
			q.setMaxResults(1);
			q.setParameter(0, value);
			return q.list();
		} catch (RuntimeException re) {			
			throw re;
		}	
	}
	
   public List findByIdImeiOrd(String IdImei){
   return findByPropertyOrd(IDIMEI, IdImei );
   }
}
