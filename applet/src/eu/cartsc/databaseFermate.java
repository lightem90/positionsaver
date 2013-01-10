package eu.cartsc; 

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * 
 * @author matteo 
 *
 */
public class databaseFermate extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "fermate.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "CREATE TABLE fermate (idaut INTEGER PRIMARY KEY AUTOINCREMENT, idimei VARCHAR(15), nomeFermata TEXT NOT NULL, indirizzo TEXT, descrizione TEXT, data DATETIME);";

    
    public databaseFermate(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    
    public void onCreate(SQLiteDatabase database) {
            database.execSQL(DATABASE_CREATE);
    }

	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
	
	}
    
 }
