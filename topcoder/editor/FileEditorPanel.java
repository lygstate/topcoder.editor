package topcoder.editor;

import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import topcoder.editor.panels.FileEditorCommon;

public class FileEditorPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4611382144783069067L;

	public FileEditorPanel(JTextArea textArea) {
		FileEditorCommon.setDefaultAttributes(this);

		JLabel statusLabel = FileEditorCommon.createJLabel("TopCoder Editor v0.10",
				new Font(getFont().getFontName(), 3, getFont().getSize()));
		JLabel reminderLabel = FileEditorCommon.createJLabel(
				"*** Make sure you remotely compile before test/submit ***",
				new Font(getFont().getFontName(), 3, getFont().getSize()));

		JScrollPane scrollPane = FileEditorCommon.createJScrollPane(textArea);
		scrollPane.setBorder(FileEditorCommon.getTitledBorder("Activity Log"));

		Box status = FileEditorCommon
				.createHorizontalBox(
						new Component[] { statusLabel,
								Box.createHorizontalGlue(), reminderLabel },
						false);

		add(scrollPane, "Center");
		add(status, "South");
	}

	public static void main(String[] s) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(3);
		JTextArea text = new JTextArea();
		text.setForeground(FileEditorCommon.FG_COLOR);
		text.setBackground(FileEditorCommon.BG_COLOR);
		frame.getContentPane().add(new FileEditorPanel(text));
		frame.pack();
		frame.setVisible(true);
	}
}