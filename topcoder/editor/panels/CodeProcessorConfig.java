/** 
 * ConfigurationPanel.java
 *
 * Description:		Configuration for the CodeProcessor
 * @author			Tim "Pops" Roberts
 * @version			3.0
 */

package topcoder.editor.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import topcoder.editor.ConfigurationInterface;
import topcoder.editor.Preferences;

public class CodeProcessorConfig extends JPanel implements ActionListener,
		DocumentListener, ConfigurationInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8321962248388177104L;

	private Preferences pref;

	private JButton codeProcessorConfigure = Common.createJButton("Configure");
	private JButton verify = Common.createJButton("Verify");
	private JButton upAction = Common.createJButton("Up");
	private JButton downAction = Common.createJButton("Down");
	private JButton addAction = Common.createJButton("Add");
	private JButton deleteAction = Common.createJButton("Delete");

	private JTable processorTable = Common.createJTable();
	private MyModel myModel;

	private boolean savePending = false;

	public CodeProcessorConfig(Preferences pref) {

		this.pref = pref;
		Common.setDefaultAttributes(this);
		this.setLayout(new GridBagLayout());

		// Setup the dirName field(s)
		myModel = new MyModel(pref.getCodeProcessors());
		processorTable.setModel(myModel);
		processorTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);

		// Setup actionlisteners (must be last to avoid savepending problems)

		this.add(Common.createJScrollPane(processorTable),
				new GridBagConstraints(0, 1, 2, 7, 1, 1,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(15, 10, 0, 10), 0, 0));
		this.add(addAction, new GridBagConstraints(2, 1, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(
						15, 0, 0, 10), 0, 0));
		this.add(deleteAction, new GridBagConstraints(2, 2, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
						0, 0, 10), 0, 0));
		this.add(upAction, new GridBagConstraints(2, 3, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(
						10, 0, 0, 10), 0, 0));
		this.add(downAction, new GridBagConstraints(2, 4, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
						0, 0, 10), 0, 0));
		this.add(verify, new GridBagConstraints(2, 5, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(
						10, 0, 0, 10), 0, 0));
		this.add(codeProcessorConfigure, new GridBagConstraints(2, 6, 1, 1, 0,
				0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 10), 0, 0));
		this.add(new JLabel(""), new GridBagConstraints(0, 7, 3, 1, 1, 1,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
						0, 0, 10), 0, 0));

		// reset pending flag
		savePending = false;

		codeProcessorConfigure.addActionListener(this);
		verify.addActionListener(this);

		upAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (processorTable.getCellEditor() != null)
					processorTable.getCellEditor().stopCellEditing();
				int row = myModel.moveUp(processorTable.getSelectedRow());
				processorTable.setRowSelectionInterval(row, row);
			}
		});

		downAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (processorTable.getCellEditor() != null)
					processorTable.getCellEditor().stopCellEditing();
				int row = myModel.moveDown(processorTable.getSelectedRow());
				processorTable.setRowSelectionInterval(row, row);
			}
		});

		addAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (processorTable.getCellEditor() != null)
					processorTable.getCellEditor().stopCellEditing();
				int row = myModel.addRow();
				processorTable.setRowSelectionInterval(row, row);
				processorTable.editCellAt(row, 0);
			}
		});

		deleteAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (processorTable.getCellEditor() != null)
					processorTable.getCellEditor().stopCellEditing();
				int row = myModel.deleteRow(processorTable.getSelectedRow());
				if (row < 0)
					return;
				processorTable.setRowSelectionInterval(row, row);
			}
		});

	}

	public void actionPerformed(ActionEvent e) {
		// Stop any editing
		if (processorTable.getCellEditor() != null)
			processorTable.getCellEditor().stopCellEditing();

		if (e.getSource() == verify) {
		} else if (e.getSource() == codeProcessorConfigure) {
		}

	}

	public void changedUpdate(DocumentEvent e) {
		savePending = true;
	}

	public void insertUpdate(DocumentEvent e) {
		savePending = true;
	}

	public void removeUpdate(DocumentEvent e) {
		savePending = true;
	}

	public String getTabTitle() {
		return "General";
	}

	public Icon getTabIcon() {
		return null;
	}

	public String getTabToolTip() {
		return "General Configuration";
	}

	public boolean isSavePending() {
		return savePending;
	}

	public void resetSavePending() {
		savePending = false;
	}

	public boolean savePreferences() {

		// Stop any editing
		if (processorTable.getCellEditor() != null)
			processorTable.getCellEditor().stopCellEditing();

		if (myModel.getModel().length == 0) {
			Common.showMessage("Error",
					"Please specify at least one code processor class", null);
			return false;
		}

		int row = processorTable.getSelectedRow();
		for (int x = 0; x < myModel.getModel().length; x++) {
			processorTable.getSelectionModel().setSelectionInterval(x, x);
		}
		if (row >= 0) {
			processorTable.getSelectionModel().setSelectionInterval(row, row);
		}

		pref.setCodeProcessors(myModel.getModel());

		resetSavePending();

		return true;
	}

	// Table model
	class MyModel extends AbstractTableModel implements TableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7330713721262498425L;

		private ArrayList<Object> model = new ArrayList<Object>();

		public MyModel(String[] current) {
			if (current != null)
				model.addAll(Arrays.asList(current));
		}

		public boolean isCellEditable(int row, int col) {
			return true;
		}

		public String[] getModel() {
			// Delete any space rows..
			for (int x = model.size() - 1; x >= 0; x--) {
				if (((String) model.get(x)).trim().length() == 0)
					deleteRow(x);
			}

			return (String[]) model.toArray(new String[0]);
		}

		public String getColumnName(int columnIndex) {
			return "CodeProcessor Script (in the order they will be called)";
		}

		public int getRowCount() {
			return model.size();
		}

		public int getColumnCount() {
			return 1;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			if (rowIndex < 0 || rowIndex >= model.size())
				return null;
			return model.get(rowIndex);
		}

		public void setValueAt(Object v, int row, int columnIndex) {
			if (row < 0 || row >= model.size())
				return;
			model.set(row, v);
			savePending = true;
		}

		public int moveUp(int row) {
			if (row <= 0 || row > getRowCount() - 1)
				return row;
			Object orig = model.get(row - 1);
			Object moveit = model.get(row);
			model.set(row - 1, moveit);
			model.set(row, orig);
			this.fireTableRowsUpdated(row - 1, row);
			savePending = true;
			return row - 1;
		}

		public int moveDown(int row) {
			if (row < 0 || row >= getRowCount() - 1)
				return row;
			Object orig = model.get(row + 1);
			Object moveit = model.get(row);
			model.set(row + 1, moveit);
			model.set(row, orig);
			this.fireTableRowsUpdated(row, row + 1);
			savePending = true;
			return row + 1;
		}

		public int deleteRow(int row) {
			if (row < 0 || row >= model.size())
				return row;
			model.remove(row);
			this.fireTableRowsDeleted(row, row);
			savePending = true;
			if (row >= model.size())
				row = model.size() - 1;
			return row;
		}

		public int addRow() {
			model.add("");
			this.fireTableRowsInserted(model.size() - 1, model.size() - 1);
			savePending = true;
			return model.size() - 1;
		}
	}

}
