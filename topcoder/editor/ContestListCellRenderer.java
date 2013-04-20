package topcoder.editor;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import topcoder.editor.panels.FileEditorCommon;

public class ContestListCellRenderer extends JLabel implements ListCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -766414738431860512L;
	JList internalList = null;

	public ContestListCellRenderer() {
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if ((this.internalList != list) && (list != null)) {
			this.internalList = list;

			list.setSelectionForeground(FileEditorCommon.TF_COLOR);
			list.setSelectionBackground(FileEditorCommon.TB_COLOR);
		}

		if (isSelected) {
			setForeground(FileEditorCommon.HF_COLOR);
			setBackground(FileEditorCommon.HB_COLOR);
		} else {
			setForeground(FileEditorCommon.TF_COLOR);
			setBackground(FileEditorCommon.TB_COLOR);
		}

		if (value != null) {
			setText(value.toString());
		}

		return this;
	}
}
