package topcoder.editor.ui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditorPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4611382144783069067L;

	public EditorPanel(JTextArea textArea) {
		Common.setDefaultAttributes(this);

		JLabel statusLabel = Common.createJLabel(Common.PRODUCT_NAME, new Font(
				getFont().getFontName(), 3, getFont().getSize()));
		JLabel reminderLabel = Common.createJLabel(
				"*** Make sure you remotely compile before test/submit ***",
				new Font(getFont().getFontName(), 3, getFont().getSize()));

		JScrollPane scrollPane = Common.createJScrollPane(textArea);
		scrollPane.setBorder(Common.getTitledBorder("Activity Log"));

		Box status = Common.createVerticalBox(new Component[] { statusLabel,
				Box.createVerticalGlue(), reminderLabel }, false);

		add(scrollPane, "Center");
		add(status, "South");
	}

	public static void main(String[] s) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(3);
		JTextArea text = new JTextArea();
		text.setForeground(Common.FG_COLOR);
		text.setBackground(Common.BG_COLOR);
		frame.getContentPane().add(new EditorPanel(text));
		frame.pack();
		frame.setVisible(true);
	}
}