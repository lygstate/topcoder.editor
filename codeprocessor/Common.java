package codeprocessor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

class Common {
	public static final Color FG_COLOR = Color.white;
	public static final Color BG_COLOR = Color.black;
	public static final Color WPB_COLOR = Color.decode("0x333333");
	public static final Color TF_COLOR = Color.white;
	public static final Color TB_COLOR = Color.black;
	public static final Color HF_COLOR = Color.white;
	public static final Color HB_COLOR = Color.decode("0x003300");
	public static final Font DEFAULTFONT = new Font("SansSerif", 0, 12);

	public static final Box createHorizontalBox(
			Component[] paramArrayOfComponent) {
		return createHorizontalBox(paramArrayOfComponent, true);
	}

	public static final Box createHorizontalBox(
			Component[] paramArrayOfComponent, boolean paramBoolean) {
		Box localBox = Box.createHorizontalBox();
		if (paramArrayOfComponent.length == 0) {
			return localBox;
		}
		for (int i = 0; i < paramArrayOfComponent.length - 1; i++) {
			localBox.add(paramArrayOfComponent[i]);
			localBox.add(Box.createHorizontalStrut(5));
		}

		localBox.add(paramArrayOfComponent[(paramArrayOfComponent.length - 1)]);
		if (paramBoolean)
			localBox.add(Box.createHorizontalGlue());
		return localBox;
	}

	public static final JTable createJTable() {
		JTable localJTable = new JTable();
		localJTable.setBackground(TB_COLOR);
		localJTable.setForeground(TF_COLOR);
		localJTable.setSelectionBackground(HB_COLOR);
		localJTable.setSelectionForeground(HF_COLOR);
		localJTable.setShowGrid(false);
		return localJTable;
	}

	public static final JLabel createJLabel(String paramString) {
		return createJLabel(paramString, DEFAULTFONT);
	}

	public static final JLabel createJLabel(String paramString, Font paramFont) {
		return createJLabel(paramString, null, 2, paramFont);
	}

	public static final JLabel createJLabel(String paramString,
			Dimension paramDimension) {
		return createJLabel(paramString, paramDimension, 2, DEFAULTFONT);
	}

	public static final JLabel createJLabel(String paramString,
			Dimension paramDimension, int paramInt) {
		return createJLabel(paramString, paramDimension, paramInt, DEFAULTFONT);
	}

	public static final JLabel createJLabel(String paramString,
			Dimension paramDimension, int paramInt, Font paramFont) {
		JLabel localJLabel = new JLabel(paramString);
		localJLabel.setForeground(FG_COLOR);
		localJLabel.setBackground(WPB_COLOR);
		localJLabel.setFont(paramFont);
		localJLabel.setHorizontalAlignment(paramInt);
		if (paramDimension != null) {
			localJLabel.setMinimumSize(paramDimension);
			localJLabel.setPreferredSize(paramDimension);
			localJLabel.setMaximumSize(paramDimension);
		}
		return localJLabel;
	}

	public static final JTextField createJTextField(int paramInt,
			Dimension paramDimension) {
		return createJTextField(paramInt, paramDimension, DEFAULTFONT);
	}

	public static final JTextField createJTextField(int paramInt,
			Dimension paramDimension, Font paramFont) {
		JTextField localJTextField = new JTextField(paramInt);
		localJTextField.setForeground(FG_COLOR);
		localJTextField.setBackground(BG_COLOR);
		localJTextField.setCaretColor(FG_COLOR);
		localJTextField.setFont(paramFont);
		localJTextField.setBorder(BorderFactory.createLineBorder(FG_COLOR, 1));
		localJTextField.setMaximumSize(paramDimension);
		localJTextField.addFocusListener(new SelectAll(localJTextField));
		return localJTextField;
	}

	public static final JScrollPane createJScrollPane(Component paramComponent) {
		return createJScrollPane(paramComponent, null, null);
	}

	public static final JScrollPane createJScrollPane(Component paramComponent,
			Dimension paramDimension) {
		return createJScrollPane(paramComponent, paramDimension, null);
	}

	public static final JScrollPane createJScrollPane(Component paramComponent,
			Dimension paramDimension, Border paramBorder) {
		JScrollPane localJScrollPane = new JScrollPane(paramComponent);
		localJScrollPane.setBackground(WPB_COLOR);
		localJScrollPane.getViewport().setBackground(WPB_COLOR);
		if (paramDimension != null)
			localJScrollPane.getViewport().setPreferredSize(paramDimension);
		if (paramBorder != null)
			localJScrollPane.setBorder(paramBorder);

		return localJScrollPane;
	}

	public static final void setDefaultAttributes(Container paramContainer) {
		setDefaultAttributes(paramContainer, new BorderLayout());
	}

	public static final void setDefaultAttributes(Container paramContainer,
			LayoutManager paramLayoutManager) {
		paramContainer.setLayout(paramLayoutManager);
		paramContainer.setBackground(WPB_COLOR);
	}

	public static final JButton createJButton(String paramString) {
		return createJButton(paramString, null, DEFAULTFONT);
	}

	public static final JButton createJButton(String paramString,
			Dimension paramDimension) {
		return createJButton(paramString, paramDimension, DEFAULTFONT);
	}

	public static final JButton createJButton(String paramString, Font paramFont) {
		return createJButton(paramString, null, paramFont);
	}

	public static final JButton createJButton(String paramString,
			Dimension paramDimension, Font paramFont) {
		JButton localJButton = new JButton(paramString);
		localJButton.setFont(paramFont);
		if (paramDimension != null) {
			localJButton.setMinimumSize(paramDimension);
			localJButton.setPreferredSize(paramDimension);
			localJButton.setMaximumSize(paramDimension);
		}
		return localJButton;
	}

	public static void showMessage(String paramString1, String paramString2,
			Component paramComponent) {
		JOptionPane.showMessageDialog(paramComponent, paramString2,
				paramString1, 1);
	}

	public static boolean confirm(String paramString1, String paramString2,
			Component paramComponent) {
		int i = JOptionPane.showConfirmDialog(paramComponent, paramString2,
				paramString1, 0, 2);

		if (i == 0) {
			return true;
		}
		return false;
	}

	public static String input(String paramString1, String paramString2,
			Component paramComponent) {
		String str = JOptionPane.showInputDialog(paramComponent, paramString2,
				paramString1, 3);

		return str;
	}

	private static class SelectAll extends FocusAdapter {
		JTextComponent parent;

		public SelectAll(JTextComponent paramJTextComponent) {
			this.parent = paramJTextComponent;
		}

		public void focusGained(FocusEvent paramFocusEvent) {
			this.parent.selectAll();
		}
	}
}
