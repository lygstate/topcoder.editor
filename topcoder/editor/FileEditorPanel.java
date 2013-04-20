package topcoder.editor;

import java.awt.Component;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FileEditorPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4611382144783069067L;

	public FileEditorPanel(JTextArea textArea) {
		EditorCommon.setDefaultAttributes(this);

		JLabel statusLabel = EditorCommon.createJLabel("TopCoder Editor v0.10",
				new Font(getFont().getFontName(), 3, getFont().getSize()));
		JLabel reminderLabel = EditorCommon.createJLabel(
				"*** Make sure you remotely compile before test/submit ***",
				new Font(getFont().getFontName(), 3, getFont().getSize()));

		JScrollPane scrollPane = EditorCommon.createJScrollPane(textArea);
		scrollPane.setBorder(EditorCommon.getTitledBorder("Activity Log"));

		Box status = EditorCommon
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
		text.setForeground(EditorCommon.FG_COLOR);
		text.setBackground(EditorCommon.BG_COLOR);
		frame.getContentPane().add(new FileEditorPanel(text));
		frame.pack();
		frame.setVisible(true);
	}
}