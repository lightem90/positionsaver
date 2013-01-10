package eu.cartsc.fermate.servlet;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

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

			URL url = new URL(
					"http://localhost:8080/eu.cartsc.fermate.web/sync");
			String query = String
					.format("idimei=%s&nomeFermata=%s&indirizzo=%s&descrizione=%S&data=%s",
							URLEncoder.encode("164973461253798", "UTF-8"),
							URLEncoder.encode("scuola2", "UTF-8"),
							URLEncoder.encode("via notari", "UTF-8"),
							URLEncoder.encode("tabella bruciata", "UTF-8"),
							URLEncoder.encode("2012-07-28 15:40:26.0", "UTF-8"));

			URLConnection connection = new URL(url + "?" + query)
					.openConnection();
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			InputStream response = connection.getInputStream();
			response.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			if (tx != null && tx.isActive())
				tx.rollback();
		} finally {
			sx.close();
		}
	}

}
