package conch.yaoms.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import conch.yaoms.reader.buffer.etxt.EtxtBuffer;
import conch.yaoms.reader.list.etxt.EtxtLister;
import conch.yaoms.reader.model.BookChapter;
import conch.yaoms.reader.model.BookCheckFile;
import conch.yaoms.reader.model.etxt.EtxtCheckFile;

public class MainActivity extends Activity {

	public static final String TAG = "Yaoms";

	private List<BookCheckFile> checkFiles;

	private File chkFileDir;

	private EtxtBuffer buffer;

	private TextView textView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.i(TAG, "简单阅读器 启动");

		textView = (TextView) findViewById(R.id.textView);

		chkFileDir = new File(getFilesDir(), "chk");
		if (!chkFileDir.exists()) {
			chkFileDir.mkdirs();
		}

		checkFiles = new EtxtLister().getCheckFiles(chkFileDir);

		if (checkFiles == null) {
			checkFiles = new ArrayList<BookCheckFile>();
		}

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0 :
				selectFile();
				break;

			default :
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
		bundle.putString("suffix", ".etxt");
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
			case REQUEST_RESULT :
				if (resultCode == RESULT_OK && intent != null) {
					// obtain the filename
					String filename = intent.getDataString();
					if (filename != null) {
						// Get rid of URI prefix:
						if (filename.startsWith("file://")) {
							filename = filename.substring(7);
						}
						// Do something with the file here
						Toast.makeText(this, filename, Toast.LENGTH_SHORT)
								.show();
						EtxtCheckFile checkFile = new EtxtCheckFile(chkFileDir);
						checkFile.setFullName(filename);
						checkFile.setCurrentChapterIndex(0);
						checkFile.setSign(checkFile.sign(filename));
						checkFile.setLastReadTime(0);
						checkFile.setOk(true);
						EtxtCheckFile oldCheckFile = getFromList(checkFile);
						if (oldCheckFile == null) {
							checkFile.save();
							checkFiles.add(checkFile);
						} else {
							checkFile = oldCheckFile;
						}
						openBook(checkFile);
					}
				}
				break;
		}
	}

	private EtxtCheckFile getFromList(EtxtCheckFile checkFile) {
		for (BookCheckFile chk : checkFiles) {
			if (chk.getFullNme().equals(checkFile.getFullNme())
					&& chk.getSign().equals(checkFile.getSign())) {
				return (EtxtCheckFile) chk;
			}
		}
		return null;
	}

	private void openBook(EtxtCheckFile checkFile) {
		buffer = new EtxtBuffer(checkFile);

		BookChapter currentChapter = buffer.getCurrentChapter();
		showChapter(currentChapter);

	}

	private void showChapter(BookChapter chapter) {
		String title = buffer.getBookTitle().getBookName() + " / ";
		title += chapter.getChapterTitle().getTitle();
		String body = chapter.getChapterBody();

		setTitle(title);
		Log.i(TAG, "body: " + body);
		textView.setText(body);
	}
}