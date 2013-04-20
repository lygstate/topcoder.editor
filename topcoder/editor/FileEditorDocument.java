package topcoder.editor;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FileEditorDocument extends PlainDocument {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8521905029764038396L;
	private int limit;
	private boolean toUppercase = false;

	public FileEditorDocument(int limit) {
		this.limit = limit;
	}

	public FileEditorDocument(int limit, boolean upper) {
		this.limit = limit;
		this.toUppercase = upper;
	}

	public void insertString(int offset, String str, AttributeSet attr)
			throws BadLocationException {
		if (str == null)
			return;

		if (getLength() + str.length() <= this.limit) {
			if (this.toUppercase)
				str = str.toUpperCase();
			super.insertString(offset, str, attr);
		}
	}
}
