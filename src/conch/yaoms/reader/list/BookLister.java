package conch.yaoms.reader.list;

import java.io.File;
import java.util.List;

import conch.yaoms.reader.model.BookCheckFile;

public interface BookLister {
	
	List<BookCheckFile> getCheckFiles(File dir);

}
