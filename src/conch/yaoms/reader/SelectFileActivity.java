package conch.yaoms.reader;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectFileActivity extends Activity implements OnItemClickListener {

	private File[] dirs;

	private File[] files;

	private String pwd;

	private ListView listView;

	private String parentDir;

	private String suffix;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public File[] getDirs() {
		return dirs;
	}

	public void setDirs(File[] dirs) {
		this.dirs = dirs;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public ListView getListView() {
		return listView;
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	public String getParentDir() {
		return parentDir;
	}

	public void setParentDir(String parentDir) {
		this.parentDir = parentDir.equals("") ? "/" : parentDir;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);

		setContentView(listView);

		Intent intent = getIntent();

		Bundle bundle = intent.getExtras();

		setSuffix(bundle.getString("suffix"));

		openDir(bundle.getString("path"));

	}

	private void openDir(String dirName) {
		setTitle(dirName);
		setPwd(dirName);
		setParentDir(dirName.substring(0, dirName.lastIndexOf("/")));

		File dir = new File(getPwd());
		if (dir.exists() && dir.isDirectory()) {
			dirs = sortFile(dir.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			}));

			files = sortFile(dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String filename) {
					return filename.endsWith(suffix);
				}

			}));

			listView.setOnItemClickListener(this);

			List<String> textViews = new ArrayList<String>();

			for (File file : dirs) {
				textViews.add(file.getName() + "/");
			}

			for (File file : files) {
				textViews.add(file.getName());
			}

			ArrayAdapter<String> textAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, textViews);
			listView.setAdapter(textAdapter);
			// listView.invalidate();

		} else {
			ArrayAdapter<String> textAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, new String[] {});
			listView.setAdapter(textAdapter);
			// listView.invalidate();
		}
	}

	private File[] sortFile(File[] listFiles) {
		Arrays.sort(listFiles);
		return listFiles;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String filename = "";
		if (arg2 >= dirs.length) {
			filename += files[arg2 - dirs.length];
			result(filename);
		} else {
			filename += dirs[arg2];
			openDir(filename);
		}
	}

	private void result(String filename) {
		setResult(-1, new Intent().setData(Uri.parse(filename)));
		finish();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (getPwd().equals("/")) {
				finish();
				return true;
			} else {
				openDir(getParentDir());
				return true;
			}

		}

		return false;
	}
}
