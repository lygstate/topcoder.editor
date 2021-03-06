/** 
 * ConfigurationPanel.java
 *
 * Description:		Configuration for the CodeProcessor
 * @author			Tim "Pops" Roberts
 * @version			3.0
 */

package topcoder.editor.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import topcoder.editor.CodeProcessor;
import topcoder.editor.EntryPoint;

public class CodeProcessorConfig extends JPanel
	implements ActionListener, ConfigurationInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8321962248388177104L;

	private JButton configure = Common.createJButton("Configure");
	private JButton verify = Common.createJButton("Verify");
	private JButton upAction = Common.createJButton("Up");
	private JButton downAction = Common.createJButton("Down");
	private JButton addAction = Common.createJButton("Add");
	private JButton deleteAction = Common.createJButton("Delete");

	private JTable processorTable = Common.createJTable();
	private MyModel myModel;

	private String[] prefCodeProcessors;

	public CodeProcessorConfig(EntryPoint entry) {
		Common.setDefaultAttributes(this);
		this.setLayout(new GridBagLayout());

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
		this.add(configure, new GridBagConstraints(2, 6, 1, 1, 0,
				0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 10), 0, 0));
		this.add(new JLabel(""), new GridBagConstraints(0, 7, 3, 1, 1, 1,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
						0, 0, 10), 0, 0));

		/* Create the table model */
		myModel = new MyModel();
		processorTable.setModel(myModel);
		processorTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);

		upAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (processorTable.getCellEditor() != null)
					processorTable.getCellEditor().stopCellEditing();
				int row = myModel.moveUp(processorTable.getSelectedRow());
				if (row >= 0) {
					processorTable.setRowSelectionInterval(row, row);
				}
			}
		});

		downAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (processorTable.getCellEditor() != null)
					processorTable.getCellEditor().stopCellEditing();
				int row = myModel.moveDown(processorTable.getSelectedRow());
				if (row >= 0) {
					processorTable.setRowSelectionInterval(row, row);
				}
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

		// Setup addActionListeners ()
		configure.addActionListener(this);
		verify.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// Stop any editing
		if (processorTable.getCellEditor() != null)
			processorTable.getCellEditor().stopCellEditing();

		int selected = processorTable.getSelectedRow();
		if (selected < 0) {
			selected = 0;
		}

		String[] processorNames = myModel.getModel();
		String name = null;
		if (selected < processorNames.length) {
			name = processorNames[selected];
		}

		CodeProcessor processor = CodeProcessor.create(name); 

		if (e.getSource() == verify) {
			if  (name == null){
				Common.showMessage("Code processor error.", "No code processors in the list", null);
			} else if (processor == null) {
				String info = String.format("Verification failed, the code processor [%s] doesn't exist.", name);
				Common.showMessage("Code processor error.", info, null);
			} else {
				String info = String.format("Verification passed, the code processor [%s] exist.", name);
				Common.showMessage("Code processor inforamtion.", info, null);
			}
		}
		else if (e.getSource() == configure) {
			if (name == null) {
				Common.showMessage("Code processor configure error.", "No code processors to configure", null);
			} else if (processor == null) {
				String info = String.format("The code processor [%s] doesn't exist", name);
				Common.showMessage("Code processor configure error.", info, null);
			} else {
				processor.configure();
			}
		}
	}

	@Override
	public String getTabTitle() {
		return "Code Processors";
	}

	@Override
	public String getTabToolTip() {
		return "Specify code processors";
	}

	@Override
	public Icon getTabIcon() {
		return null;
	}

	@Override
	public void loadPreferencesToUI() {
		myModel.removeAll();
		myModel.addAll(this.prefCodeProcessors);
		processorTable.updateUI();
	}

	@Override
	public boolean savePreferencesFromUI() {
		// Stop any editing
		if (processorTable.getCellEditor() != null)
			processorTable.getCellEditor().stopCellEditing();

		int row = processorTable.getSelectedRow();
		for (int x = 0; x < myModel.getModel().length; x++) {
			processorTable.getSelectionModel().setSelectionInterval(x, x);
		}
		if (row >= 0) {
			processorTable.getSelectionModel().setSelectionInterval(row, row);
		}
		String [] processorNames = myModel.getModel();

		/* Test the existence of the configuration CodePrecessor class */
		for (int i = 0; i < processorNames.length; ++i){
			if (CodeProcessor.create(processorNames[i]) == null) {
				Common.showMessage("Code processor list", "The code processor[" + processorNames[i] + "]doesn't exist.", null);
				return false;
			}
		}
		this.prefCodeProcessors = processorNames; 
		return true;
	}

	// Table model
	class MyModel extends AbstractTableModel implements TableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7330713721262498425L;

		private ArrayList<Object> model = new ArrayList<Object>();

		public MyModel() {
		}

		public void removeAll() {
			model.clear();
		}

		public void addAll(String [] current) {
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
		}

		public int moveUp(int row) {
			if (row <= 0 || row > getRowCount() - 1)
				return row;
			Object orig = model.get(row - 1);
			Object moveit = model.get(row);
			model.set(row - 1, moveit);
			model.set(row, orig);
			this.fireTableRowsUpdated(row - 1, row);
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
			return row + 1;
		}

		public int deleteRow(int row) {
			if (row < 0 || row >= model.size())
				return row;
			model.remove(row);
			this.fireTableRowsDeleted(row, row);
			if (row >= model.size())
				row = model.size() - 1;
			return row;
		}

		public int addRow() {
			model.add("");
			this.fireTableRowsInserted(model.size() - 1, model.size() - 1);
			return model.size() - 1;
		}

	}

}
