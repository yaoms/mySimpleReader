package conch.yaoms.reader.model.etxt;

import conch.yaoms.reader.model.BookChapterTitle;

public class EtxtChapterTitle
		implements
			BookChapterTitle,
			Comparable<EtxtChapterTitle> {

	private int index;

	private int lineNumber;

	private String title;

	public void setIndex(int index) {
		this.index = index;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Override
	public int compareTo(EtxtChapterTitle another) {
		return index - another.getIndex();
	}

}
