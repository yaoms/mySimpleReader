package conch.yaoms.reader.buffer;

import conch.yaoms.reader.model.BookCheckFile;
import conch.yaoms.reader.model.BookTitle;
import conch.yaoms.reader.model.BookChapter;

public interface BookBuffer {
	
	BookCheckFile getBookCheckFile();
	
	BookTitle getBookTitle();
	
	BookChapter getNextChapter();
	
	BookChapter getChapter(int chapterIndex);
	
	BookChapter getCurrentChapter();
	
	BookChapter getPrevChapter();
	
	int getCurrentChapterIndex();
	
	int getChapterCount();
	
	void setDefaultCharset(String charsetName);
	
	String getDefaultCharset();

}
