package eu.cartsc.fermate.servlet;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import eu.cartsc.fermate.db.Fermate;
import eu.cartsc.fermate.db.FermateDAO;

public class ServletListenerTest {

	@Test
	public void testServletListener() {

	}

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {

	}

	@Test
	public void testsyncsave() {

		try {

//			URL url = new URL(
//					"http://server06:8081/fermate.test/sync");
			URL url = new URL("http://cart38:18080/fermate.test/sync");
			String query = String
					.format("idimei=%s&nomeFermata=%s&indirizzo=%s&descrizione=%S&data=%s",
							URLEncoder.encode("894512987654545", "UTF-8"),
							URLEncoder.encode("scuola1", "UTF-8"),
							URLEncoder.encode("via lugli", "UTF-8"),
							URLEncoder.encode("tabella ok", "UTF-8"),
							URLEncoder.encode("2012-05-28 12:40:30.0", "UTF-8"));

			URLConnection connection = new URL(url + "?" + query)
					.openConnection();
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			InputStream response = connection.getInputStream();
			response.close();

		} catch (Exception e) {
			e.printStackTrace();
			fail("Test Fallito. Non sono riuscito a collegarmi al server!");
		}

	}

	@Test
	public void testAttachDirty() {

		FermateDAO f = new FermateDAO();
		Transaction tx = null;
		Session sx = null;

		try {
			sx = f.getSession();

			tx = sx.beginTransaction();

			String datasync = "1990-01-01 00:00:01";

			Timestamp dinsert = Timestamp.valueOf(datasync);

			Fermate fe = new Fermate("457619435671842", "test", "test", "test",
					dinsert);

			f.attachDirty(fe);

			tx.commit();

		} catch (RuntimeException e) {
			fail("Test Fallito. Non sono riuscito a salvare i dati nel db!");
			if (tx != null && tx.isActive())
				tx.rollback();
		} finally {
			sx.close();
		}
	}

}
