package conch.yaoms.reader.buffer.etxt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import conch.yaoms.reader.MainActivity;
import conch.yaoms.reader.buffer.BookBuffer;
import conch.yaoms.reader.model.BookChapter;
import conch.yaoms.reader.model.BookChapterTitle;
import conch.yaoms.reader.model.BookCheckFile;
import conch.yaoms.reader.model.BookTitle;
import conch.yaoms.reader.model.etxt.EtxtChapter;
import conch.yaoms.reader.model.etxt.EtxtChapterTitle;
import conch.yaoms.reader.model.etxt.EtxtTitle;

public class EtxtBuffer implements BookBuffer {

	private BookTitle bookTitle;

	private int currentChapterIndex;

	private BookCheckFile bookCheckFile;

	private BookChapterTitle[] chapterTitles;

	private File bookFile;

	private String defaultCharset = "UTF-8";

	public String getDefaultCharset() {
		return defaultCharset;
	}

	public void setDefaultCharset(String defaultCharset) {
		this.defaultCharset = defaultCharset;
	}

	public EtxtBuffer(BookCheckFile checkFile) {
		this.bookCheckFile = checkFile;
		bookFile = new File(bookCheckFile.getFullNme());
		bookTitle = readBookTitle();
		currentChapterIndex = bookCheckFile.getCurrentChapterIndex();
		chapterTitles = readChapterTitles();
		
	}

	private BookChapterTitle[] readChapterTitles() {
		Log.i(MainActivity.TAG, "读取章节列表");
		try {
			List<EtxtChapterTitle> chapterTitles = new ArrayList<EtxtChapterTitle>();
			int index = 0;
			InputStream is = new FileInputStream(bookFile);
			LineNumberReader br = new LineNumberReader(new InputStreamReader(
					is, defaultCharset));
			String line = null;
			int lineNumber = br.getLineNumber();
			while ((line = br.readLine()) != null) {
				if (line.contains(" 字数:")) {
					EtxtChapterTitle etxtChapterTitle = new EtxtChapterTitle();
					etxtChapterTitle.setIndex(index++);
					etxtChapterTitle.setLineNumber(lineNumber);
					etxtChapterTitle.setTitle(line.substring(0, line
							.lastIndexOf(" 字数:")));
					chapterTitles.add(etxtChapterTitle);
				}
				lineNumber = br.getLineNumber();
			}
			EtxtChapterTitle[] chapterTitles2 = new EtxtChapterTitle[chapterTitles
					.size()];
			chapterTitles.toArray(chapterTitles2);
			return chapterTitles2;
		} catch (FileNotFoundException e) {
			Log.e(MainActivity.TAG, "文件不存在！", e);
		} catch (IOException e) {
			Log.e(MainActivity.TAG, "IO异常！", e);
		}
		return null;
	}

	private BookTitle readBookTitle() {
		try {
			EtxtTitle title = new EtxtTitle();
			LineNumberReader br = new LineNumberReader(new InputStreamReader(
					new FileInputStream(bookFile), defaultCharset));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("书名:")) {
					title.setBookName(line.substring(3).trim());
				}
				if (line.startsWith("作者:")) {
					title.setAuthor(line.substring(3).trim());
				}
			}
			return title;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BookTitle getBookTitle() {
		return bookTitle;
	}

	@Override
	public BookChapter getChapter(int chapterIndex) {
		if (chapterIndex < chapterTitles.length - 1 && chapterIndex >= 0) {
			EtxtChapter chapter = new EtxtChapter();
			chapter.setChapterTitle(chapterTitles[chapterIndex]);
			try {
				LineNumberReader br = new LineNumberReader(
						new InputStreamReader(new FileInputStream(bookFile),
								defaultCharset));
				br.setLineNumber(chapterTitles[chapterIndex].getLineNumber() + 1);
				StringBuffer chapterBuffer = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line.contains(" 字数:")) {
						break;
					}
					if (line.startsWith("  ")) {
						chapterBuffer.append(line.substring(2));
					}
				}
				chapter.setChapterBody(chapterBuffer.toString());
				br.close();
				return chapter;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int getChapterCount() {
		return chapterTitles.length;
	}

	@Override
	public int getCurrentChapterIndex() {
		return currentChapterIndex;
	}

	@Override
	public BookChapter getNextChapter() {
		return getChapter(++currentChapterIndex);
	}

	@Override
	public BookChapter getPrevChapter() {
		return getChapter(--currentChapterIndex);
	}

	@Override
	public BookCheckFile getBookCheckFile() {
		return bookCheckFile;
	}

	@Override
	public BookChapter getCurrentChapter() {
		return getChapter(currentChapterIndex);
	}

}
