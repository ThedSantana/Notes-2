package rnikolaus.notes;

import java.util.Date;

public class Note {
private long id;
private String title;
private String message;
private Date date;
public Note(long id) {
	this.id = id;
}
public Date getDate(){
	return date;
}
public void setDate(Date date){
	this.date=date;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public long getId() {
	return id;
}
@Override
	public String toString() {
		return title;
	}

}
