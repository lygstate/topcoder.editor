package topcoder.editor.ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ContestListCellRenderer<E> extends JLabel implements ListCellRenderer<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -766414738431860512L;
	JList<? extends E> internalList = null;

	public ContestListCellRenderer() {
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList<? extends E> list, E value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if ((this.internalList != list) && (list != null)) {
			this.internalList = list;

			list.setSelectionForeground(Common.TF_COLOR);
			list.setSelectionBackground(Common.TB_COLOR);
		}

		if (isSelected) {
			setForeground(Common.HF_COLOR);
			setBackground(Common.HB_COLOR);
		} else {
			setForeground(Common.TF_COLOR);
			setBackground(Common.TB_COLOR);
		}

		if (value != null) {
			setText(value.toString());
		}

		return this;
	}
}
