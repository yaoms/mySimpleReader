package conch.yaoms.reader.list.etxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import conch.yaoms.reader.list.BookLister;
import conch.yaoms.reader.model.BookCheckFile;
import conch.yaoms.reader.model.etxt.EtxtCheckFile;

public class EtxtLister implements BookLister {

	public List<BookCheckFile> getCheckFiles(File dir) {
		
		if (dir.exists() && dir.isDirectory()) {
			File[] chks = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String filename) {
					return filename.endsWith(".chk");
				}
			});

			List<BookCheckFile> checkFiles = new ArrayList<BookCheckFile>();
			for (File chkFile : chks) {
				EtxtCheckFile checkFile = new EtxtCheckFile();
				try {
					BufferedReader reader = new BufferedReader(new FileReader(
							chkFile));
					String line = null;
					while ((line = reader.readLine()) != null) {
						if (line.matches("^sign:\\w\\+$")) {
							checkFile.setMd5sum(line.substring(5));
						}
						if (line.matches("^path:.\\+$")) {
							checkFile.setFullName(line.substring(5));
						}
						if (line.matches("^time:\\d\\+$")) {
							checkFile.setLastReadTime(Long.parseLong(line
									.substring(5)));
						}
						if (line.matches("^index:\\d\\+$")) {
							checkFile.setCurrentChapterIndex(Integer.parseInt(line
									.substring(6)));
						}
						checkFile.setOk(false);
					}
					checkFiles.add(checkFile);

					String md5sum = checkFile.md5sum(checkFile.getFullNme());
					if (md5sum.equalsIgnoreCase(checkFile.getMD5Sum())) {
						checkFile.setOk(true);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return checkFiles;
		}
		
		return null;
	}
}
