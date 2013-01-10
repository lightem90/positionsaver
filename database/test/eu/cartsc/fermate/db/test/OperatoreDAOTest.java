package eu.cartsc.fermate.db.test;

import java.util.ArrayList;

import org.junit.Test;

import eu.cartsc.fermate.db.Operatore;
import eu.cartsc.fermate.db.OperatoreDAO;

import junit.framework.TestCase;

public class OperatoreDAOTest extends TestCase {

	OperatoreDAO fdao = new OperatoreDAO();

	ArrayList<Operatore> flist = new ArrayList<Operatore>();

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {

		flist = (ArrayList<Operatore>) fdao.findAll();
		assertNotNull(flist);
		assertTrue(flist.size() > 0);

	}

}
