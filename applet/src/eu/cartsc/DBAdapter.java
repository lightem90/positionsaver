package eu.cartsc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

	private Context context;
	private SQLiteDatabase database;
	public databaseFermate dbFermate;

	private static final String DB_TABLE = "fermate";
	private static final String KEY_IDIMEI = "idimei";
	private static final String KEY_NOME = "nomeFermata";
	private static final String KEY_INDIRIZZO = "indirizzo";
	private static final String KEY_DESC = "descrizione";
	private static final String KEY_DATA = "data";

	public DBAdapter(Context context) {
		this.context = context;
	}

	public DBAdapter open() throws SQLException {

		dbFermate = new databaseFermate(context);
		database = dbFermate.getWritableDatabase();
		return this;
	}

	public void close() {

		dbFermate.close();

	}

	private ContentValues createContentValues(String idimei,
			String nomeFermata, String indirizzo, String descrizione,
			String data) {

		ContentValues values = new ContentValues();
		values.put(KEY_IDIMEI, idimei);
		values.put(KEY_NOME, nomeFermata);
		values.put(KEY_INDIRIZZO, indirizzo);
		values.put(KEY_DESC, descrizione);
		values.put(KEY_DATA, data);

		return values;
	}

	public long creaFermata(String idimei, String nomeFermata,
			String indirizzo, String descrizione, String data) {

		ContentValues initialValues = createContentValues(idimei, nomeFermata,
				indirizzo, descrizione, data);
		return database.insertOrThrow(DB_TABLE, null, initialValues);
	}

	public Cursor cercaTutteFermate() {
		return database.query(DB_TABLE, new String[] { KEY_IDIMEI, KEY_NOME,
				KEY_INDIRIZZO, KEY_DESC, KEY_DATA }, null, null, null, null,
				null);
	}

	public Cursor selPrimaData() {
		return database
				.rawQuery(
						"SELECT idimei, nomeFermata, indirizzo, descrizione, data FROM fermate ORDER BY data ASC",
						null);
	}

	public int cancella() {
		return database.delete(DB_TABLE, null, null);
	}
}
