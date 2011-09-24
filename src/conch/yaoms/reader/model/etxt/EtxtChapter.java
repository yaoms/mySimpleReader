package conch.yaoms.reader.model.etxt;

import conch.yaoms.reader.model.BookChapter;
import conch.yaoms.reader.model.BookChapterTitle;

public class EtxtChapter implements BookChapter {
	
	private BookChapterTitle chapterTitle;
	
	private String chapterBody;

	public void setChapterTitle(BookChapterTitle chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	public void setChapterBody(String chapterBody) {
		this.chapterBody = chapterBody;
	}

	@Override
	public String getChapterBody() {
		return chapterBody;
	}

	@Override
	public String getChapterTitle() {
		return chapterTitle.getTitle();
	}

	@Override
	public int getCharacterCount() {
		return chapterBody.length();
	}

}
