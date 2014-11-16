package rnikolaus.notes;

public class Note {
private long id;
private String title;
private String message;
public Note(long id) {
	this.id = id;
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
