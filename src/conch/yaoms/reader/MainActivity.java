package conch.yaoms.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import conch.yaoms.reader.list.etxt.EtxtLister;
import conch.yaoms.reader.model.BookCheckFile;
import conch.yaoms.reader.model.etxt.EtxtCheckFile;

public class MainActivity extends Activity {

	private List<BookCheckFile> checkFiles;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		File chkFileDir = new File(getFilesDir(), "chk");
		
		List<BookCheckFile> checkFiles = new EtxtLister()
				.getCheckFiles(chkFileDir);
		
		if (checkFiles == null) {
			checkFiles = new ArrayList<BookCheckFile>();
		}

//		EtxtBuffer buffer = new EtxtBuffer(checkFiles.get(0));
//		buffer.getCurrentChapter().getChapterBody();

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			selectFile();
			break;

		default:
			break;
		}
		return false;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, R.string.open);
		return true;
	}

	/**
	 * Opens System Explorer to open a file.
	 */
	private static final int REQUEST_RESULT = 1;

	private void selectFile() {
		Intent intent = new Intent("conch.yaoms.action.PICK_FILE");

		Bundle bundle = new Bundle();
		bundle.putString("path", "/sdcard");
		bundle.putString("suffix", ".cfg");
		intent.putExtras(bundle);

		startActivityForResult(intent, REQUEST_RESULT);
	}

	/**
	 * This is called when the user has selected the file or directory and
	 * clicked a button to return to your application.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		switch (requestCode) {
		case REQUEST_RESULT:
			if (resultCode == RESULT_OK && intent != null) {
				// obtain the filename
				String filename = intent.getDataString();
				if (filename != null) {
					// Get rid of URI prefix:
					if (filename.startsWith("file://")) {
						filename = filename.substring(7);
					}
					// Do something with the file here
					Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
					EtxtCheckFile checkFile = new EtxtCheckFile();
					checkFile.setFullName(filename);
					checkFile.setCurrentChapterIndex(0);
					checkFile.setLastReadTime(0);
					try {
						checkFile.setMd5sum(checkFile.md5sum(filename));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					checkFile.setOk(true);
					checkFiles.add(checkFile);
				}
			}
			break;
		}
	}
}