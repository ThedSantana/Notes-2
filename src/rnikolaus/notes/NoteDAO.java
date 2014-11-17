package rnikolaus.notes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NoteDAO {
	
	
	private Dbhelper dbHelper;
	private SQLiteDatabase database;
	
	
	
	public NoteDAO(Context context) {
		dbHelper = new Dbhelper(context);
		
		
	}
	public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	
	public  Note createNote(){	
		return createNote("","");
	}
	public  Note createNote(String title, String message){
		ContentValues values = new ContentValues();
		values.put(Dbhelper.Columns.TITLE.getName(), title);
		values.put(Dbhelper.Columns.MESSAGE.getName(), message);
		values.put(Dbhelper.Columns.DATE.getName(), new Date().getTime());
		long insertId = database.insert(Dbhelper.TABLE, null,
		        values);
		Note note = getNoteById(insertId);

		return note;
	}
	public  void modifyTitle(long id, String title){
		Note note = getNoteById(id);
		note.setTitle(title);
		saveNote(note);
	}
	public  void modifyMessage(long id, String message){
		Note note = getNoteById(id);
		note.setMessage(message);
		saveNote(note);
	}
	public  void modify(long id,String title, String message){
		Note note = getNoteById(id);
		note.setTitle(title);
		note.setMessage(message);
		saveNote(note);
	}
	
	public  void deleteNote(long id){
	    database.delete(Dbhelper.TABLE, "id"
	        + " = " + id, null);
	}
	public void saveNote(Note note){
		ContentValues values = new ContentValues();
		
		values.put("title", note.getTitle());
		values.put("message", note.getMessage() );
		database.update(Dbhelper.TABLE,values,"id = "+note.getId(),null);
	}
	
	public  Collection<Note> listNotes(){
		List<Note> noteList = new ArrayList<Note>();

	    Cursor cursor = database.query(Dbhelper.TABLE,
	        null, null, null, null, null, "id DESC");

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Note note = loadNote(cursor);
	      noteList.add(note);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return noteList;
		
	}
	public  Note getNoteById(long id){
		List<Note> noteList = new ArrayList<Note>();

	    Cursor cursor = database.query(Dbhelper.TABLE,
	        null, "id = "+id, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Note note = loadNote(cursor);
	      noteList.add(note);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
		return noteList.get(0);
	}
	
	private Note loadNote(Cursor cursor){
		long id =cursor.getLong(cursor.getColumnIndex(Dbhelper.Columns.ID.getName()));
		String title =cursor.getString(cursor.getColumnIndex(Dbhelper.Columns.TITLE.getName()));
		String message =cursor.getString(cursor.getColumnIndex(Dbhelper.Columns.MESSAGE.getName()));
		long date = cursor.getLong(cursor.getColumnIndex(Dbhelper.Columns.DATE.getName()));
		Note note = new Note(id);
		note.setTitle(title);
		note.setMessage(message);
		note.setDate(new Date(date));
		return note;
	}

}
