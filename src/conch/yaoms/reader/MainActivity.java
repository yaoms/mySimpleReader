package conch.yaoms.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import conch.yaoms.reader.buffer.etxt.EtxtBuffer;
import conch.yaoms.reader.model.BookCheckFile;
import conch.yaoms.reader.model.etxt.EtxtCheckFile;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        File chkFileDir = new File(getFilesDir(), "chk");
        File[] chks = chkFileDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".chk");
			}
		});
        
        List<EtxtCheckFile> checkFiles = new ArrayList<EtxtCheckFile>();
        for (File chkFile : chks) {
        	EtxtCheckFile checkFile = new EtxtCheckFile();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(chkFile));
				String line = null;
				while ((line = reader.readLine()) != null) {
					if (line.matches("^sign:\\w\\+$")) {
						checkFile.setMd5sum(line.substring(5));
					}
					if (line.matches("^path:.\\+$")) {
						checkFile.setFullName(line.substring(5));
					}
					if (line.matches("^time:\\d\\+$")) {
						checkFile.setLastReadTime(Long.parseLong(line.substring(5)));
					}
					if (line.matches("^index:\\d\\+$")) {
						checkFile.setCurrentChapterIndex(Integer.parseInt(line.substring(6)));
					}
					checkFile.setOk(false);
				}
				checkFiles.add(checkFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        for (EtxtCheckFile etxtCheckFile : checkFiles) {
			try {
				String md5sum = etxtCheckFile.md5sum(etxtCheckFile.getFullNme());
				if (md5sum.equalsIgnoreCase(etxtCheckFile.getMD5Sum())) {
					etxtCheckFile.setOk(true);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        EtxtBuffer buffer = new EtxtBuffer(checkFiles.get(0));
        buffer.getCurrentChapter().getChapterBody();
    }
}