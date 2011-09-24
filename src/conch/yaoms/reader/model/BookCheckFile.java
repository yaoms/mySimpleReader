package conch.yaoms.reader.model;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface BookCheckFile {
	
	String getFullNme();

	String getMD5Sum();
	
	int getCurrentChapterIndex(); 
	
	long getLastReadTime();
	
	boolean isOk();
	
	
	

	
	String md5sum(String fullName) throws FileNotFoundException, IOException;
	
}
