package rnikolaus.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
	private static final String MESSAGE = "message";
	private final static String DBNAME = "Notes";
	public final static String TABLE = "Notestable";
	private final static String ID_COLUMN = "id";
	private final static String TITLE_COLUMN = "title";
	private final static int VERSION = 1;
	private static final String DATABASE_CREATE = "create table " + TABLE + "("
			+ ID_COLUMN + " integer primary key autoincrement, " + TITLE_COLUMN
			+ " text," + MESSAGE + " text" + ");";

	public Dbhelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);
	    onCreate(db);
	}

}
