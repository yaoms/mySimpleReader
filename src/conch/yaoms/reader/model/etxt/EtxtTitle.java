package conch.yaoms.reader.model.etxt;

import conch.yaoms.reader.model.BookTitle;

public class EtxtTitle implements BookTitle {
	
	private String author;
	
	private String bookName;

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public String getBookName() {
		return bookName;
	}

}
