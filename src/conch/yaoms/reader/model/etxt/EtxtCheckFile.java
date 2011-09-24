package conch.yaoms.reader.model.etxt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import conch.yaoms.reader.model.BookCheckFile;
import conch.yaoms.utils.MD5Utils;
import conch.yaoms.utils.StreamUtils;

public class EtxtCheckFile implements BookCheckFile {
	
	private int currentChapterIndex;
	
	private long lastReadTime;
	
	private String fullName;
	
	private String md5sum;
	
	private boolean ok;
	

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
	public String getMD5Sum() {
		return md5sum;
	}

	public void setCurrentChapterIndex(int currentChapterIndex) {
		this.currentChapterIndex = currentChapterIndex;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setMd5sum(String md5sum) {
		this.md5sum = md5sum;
	}

	@Override
	public String md5sum(String fullName) throws FileNotFoundException, IOException {
		byte[] data = StreamUtils.getBytes(new FileInputStream(new File(fullName)));
		return MD5Utils.md5sum(data);
	}

	@Override
	public long getLastReadTime() {
		return lastReadTime;
	}
	
	public void setLastReadTime(long lastReadTime) {
		this.lastReadTime = lastReadTime;
	}

}
