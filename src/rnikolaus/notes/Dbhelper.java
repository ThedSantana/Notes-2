package rnikolaus.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
	public enum Columns{
		ID("id","integer primary key autoincrement"),
		DATE("creationdate","integer not null"),
		TITLE("title","text"),
		MESSAGE("message","text");
		
		private String name;
		private String ddl;
		Columns(String name,String ddl){
			this.name= name;
			this.ddl=ddl;
		}
		String getName() {
			return name;
		}
		String getDdl() {
			return ddl;
		}	
	}
	
	private final static String DBNAME = "Notes";
	public final static String TABLE = "Notestable";
	
	private final static int VERSION = 2;
	
	public Dbhelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String dbDDL = "create table " + TABLE + "(";
		boolean first =true;
		for (Columns c :Columns.values()){
			if (!first){
				dbDDL+=", ";
			}
			dbDDL+=c.getName();
			dbDDL+=" ";
			dbDDL+=c.getDdl();
		}
		dbDDL+= ");";
		db.execSQL(dbDDL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);
	    onCreate(db);
	}

}
