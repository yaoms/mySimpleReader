package conch.yaoms.reader.list.etxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import conch.yaoms.reader.MainActivity;
import conch.yaoms.reader.list.BookLister;
import conch.yaoms.reader.model.BookCheckFile;
import conch.yaoms.reader.model.etxt.EtxtCheckFile;

public class EtxtLister implements BookLister {

	public List<BookCheckFile> getCheckFiles(File dir) {

		File[] chks = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".chk");
			}
		});

		List<BookCheckFile> checkFiles = new ArrayList<BookCheckFile>();
		for (File chkFile : chks) {
			Log.d(MainActivity.TAG, chkFile.getName());
			EtxtCheckFile checkFile = new EtxtCheckFile(dir);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						chkFile));
				String line = null;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("sign:")) {
						checkFile.setSign(line.substring(5));
					}
					if (line.startsWith("path:")) {
						checkFile.setFullName(line.substring(5));
					}
					if (line.startsWith("time:")) {
						checkFile.setLastReadTime(Long.parseLong(line
								.substring(5)));
					}
					if (line.startsWith("index:")) {
						checkFile.setCurrentChapterIndex(Integer.parseInt(line
								.substring(6)));
					}
					checkFile.setOk(true);
				}
				checkFiles.add(checkFile);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return checkFiles;
	}
}
