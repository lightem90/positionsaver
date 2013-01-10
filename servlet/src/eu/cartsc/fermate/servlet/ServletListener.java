package eu.cartsc.fermate.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import eu.cartsc.fermate.db.Fermate;
import eu.cartsc.fermate.db.FermateDAO;
import eu.cartsc.fermate.db.Operatore;
import eu.cartsc.fermate.db.OperatoreDAO;
import eu.cartsc.fermate.db.helper.FermateDAOHelper;

public class ServletListener extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletListener() {

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, String[]> params = request.getParameterMap();
		if (params.isEmpty())
			System.out.println("non sono stati passati parametri\r");

		else {
			try {
				if (syncsave(params))
					response.setStatus(200);
				else
					System.out.printf("La fermata non è stata inserita\r");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public String converti(String[] a) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			result.append(a[i]);
		}
		return result.toString();

	};

	public boolean credenziali(String idimei) throws SQLException {

		boolean flag = false;
		OperatoreDAO oDao = new OperatoreDAO();
		@SuppressWarnings("unchecked")
		List<Operatore> ls = oDao.findAll();

		for (Operatore operatore : ls) {

			if (operatore.getIdimei().equalsIgnoreCase(idimei)) {
				System.out.printf("Accesso autorizzato a: " + idimei + "\r");
				flag = true;
			}
		}

		return flag;

	}

	public boolean syncsave(Map<String, String[]> request) throws SQLException {

		Boolean flag = false;

		String[] kidimei = request.get("idimei");
		String[] knomeFermata = request.get("nomeFermata");
		String[] kindirizzo = request.get("indirizzo");
		String[] kdescrizione = request.get("descrizione");
		String[] kdata = request.get("data");

		String idimei = converti(kidimei);
		String nomeFermata = converti(knomeFermata);
		String indirizzo = converti(kindirizzo);
		String descrizione = converti(kdescrizione);
		String data = converti(kdata);

		if (credenziali(idimei)) {

			Timestamp dinsert = null;
			Timestamp dsync = null;
			String datasync;

			@SuppressWarnings("unchecked")
			List<Fermate> ls = new FermateDAOHelper().findByIdImeiOrd(idimei);
			if (!ls.isEmpty()) {
				Fermate fermata = (Fermate) ls.get(0);
				datasync = fermata.getData().toString();
			} else
				datasync = "1990-01-01 00:00:01";

			dsync = Timestamp.valueOf(datasync);
			dinsert = Timestamp.valueOf(data);

			if (dsync.compareTo(dinsert) < 0) {

				FermateDAO f = new FermateDAO();
				Transaction tx = null;
				Session sx = null;

				try {
					sx = f.getSession();

					tx = sx.beginTransaction();

					f.attachDirty(new Fermate(idimei, nomeFermata, indirizzo,
							descrizione, dinsert));

					tx.commit();

				} catch (RuntimeException e) {
					if (tx != null && tx.isActive())
						tx.rollback();
				} finally {
					sx.close();

				}

				flag = true;
			}

		} else
			System.out.printf("Accesso non autorizzato\r");

		return flag;
	}
}
