package eu.cartsc.fermate.db.test;


import java.util.ArrayList;

import org.junit.Test;

import eu.cartsc.fermate.db.FermateDAO;
import eu.cartsc.fermate.db.Fermate;
import eu.cartsc.fermate.db.helper.FermateDAOHelper;

import junit.framework.TestCase;

public class FermateDAOTest extends TestCase {
	
	FermateDAO fdao = new FermateDAO();
	FermateDAOHelper fdaoh = new FermateDAOHelper();
	ArrayList<Fermate> flist = new ArrayList<Fermate>();

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		flist = (ArrayList<Fermate>) fdao.findAll();
		assertNotNull(flist);
		assertTrue(flist.size() > 0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindByIdimei() {
		String idimei ="354692041143955" ;
		flist = (ArrayList<Fermate>) fdaoh.findByIdImeiOrd(idimei);
		assertNotNull(flist);
		assertTrue(flist.size() > 0);
	}


}
