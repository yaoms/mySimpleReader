package conch.yaoms.reader.model;



public interface BookCheckFile {
	
	String getFullNme();

	String getSign();
	
	int getCurrentChapterIndex(); 
	
	long getLastReadTime();
	
	boolean isOk();
	
	
	

	
	String sign(String fullName);
	
}
