package topcoder.editor;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextArea;

import topcoder.editor.panels.FileEditorCommon;

public class FileEditor extends JTextArea {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5976081462062242923L;

	public FileEditor(String string) {
		init(string);
	}

	public FileEditor(String string, int rows, int columns) {
		super(rows, columns);
		init(string);
	}

	public void init(String text) {
		setText(text);

		setFont(new Font("Courier", 0, 12));
		setMargin(new Insets(5, 5, 5, 5));
		setSelectedTextColor(FileEditorCommon.HF_COLOR);
		setSelectionColor(FileEditorCommon.HB_COLOR);
		setCaretColor(FileEditorCommon.FG_COLOR);
		setForeground(FileEditorCommon.FG_COLOR);
		setBackground(FileEditorCommon.BG_COLOR);
		setEditable(false);
	}

	public void setText(String text) {
		super.setText(text);
		setCaretPosition(0);
	}
}
