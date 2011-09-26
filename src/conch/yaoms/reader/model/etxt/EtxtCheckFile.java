package conch.yaoms.reader.model.etxt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import android.util.Log;

import conch.yaoms.reader.model.BookCheckFile;
import conch.yaoms.utils.MD5Utils;
import conch.yaoms.utils.StreamUtils;

public class EtxtCheckFile implements BookCheckFile {

	private int currentChapterIndex;

	private long lastReadTime;

	private String fullName;

	private String sign;

	private boolean ok;

	private File baseDir;

	public EtxtCheckFile(File baseDir) {
		setBaseDir(baseDir);
	}

	public File getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	@Override
	public int getCurrentChapterIndex() {
		return currentChapterIndex;
	}

	@Override
	public String getFullNme() {
		return fullName;
	}

	@Override
	public String getSign() {
		return sign;
	}

	public void setCurrentChapterIndex(int currentChapterIndex) {
		this.currentChapterIndex = currentChapterIndex;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String sign(String fullName) {
		File file = new File(fullName);
		if (file.exists() && file.isFile()) {
			return "" + file.length();
		} else {
			return "";
		}
	}

	@Override
	public long getLastReadTime() {
		return lastReadTime;
	}

	public void setLastReadTime(long lastReadTime) {
		this.lastReadTime = lastReadTime;
	}

	public void save() {
		try {
			File file = new File(baseDir, getSign() + ".chk");
			PrintWriter pw = new PrintWriter(file);
			pw.print("sign:");
			pw.println(getSign());
			pw.print("path:");
			pw.println(getFullNme());
			pw.print("time:");
			pw.println(getLastReadTime());
			pw.print("index:");
			pw.println(getCurrentChapterIndex());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
