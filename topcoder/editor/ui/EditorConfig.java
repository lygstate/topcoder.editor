package topcoder.editor.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import topcoder.editor.Preferences;

public class EditorConfig extends JPanel
	implements ActionListener, ConfigurationInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2663430179796252986L;

	JLabel dirNameLabel = Common
			.createJLabel("Enter directory to read/write problems to:");
	JTextField dirNameField = Common.createJTextField(40,
			new Dimension(400, 21));

	JCheckBox backup = Common
			.createJCheckBox("Backup existing file then overwrite (uncheck if you want to keep existing file)");

	JLabel fileNameLabel = Common
			.createJLabel("Enter filename to use (no extension):");
	JCheckBox overrideFileNameField = Common
			.createJCheckBox("Make filename equal to classname");

	JTextField fileNameField = Common.createJTextField(40, new Dimension(400,
			21));

	JCheckBox htmlDesc = Common
			.createJCheckBox("Write the problem description using HTML");
	JCheckBox useLineComments = Common
			.createJCheckBox("Use Line Comments for Problem Description");
	JCheckBox problemDescFileWrite = Common
			.createJCheckBox("Write the problem description using plain text with extension name:");
	JTextField problemDescFileField = Common.createJTextField(4, new Dimension(
			75, 21));

	JCheckBox provideBreakField = Common.createJCheckBox("Force Breaks at");
	JTextField breakAtField = Common.createJTextField(4, new Dimension(75, 21));
	JLabel beginCutLabel = Common.createJLabel("$BEGINCUT$ ");
	JTextField beginCutField = Common.createJTextField(40, new Dimension(400,
			21));
	JLabel endCutLabel = Common.createJLabel("$ENDCUT$ ");
	JTextField endCutField = Common
			.createJTextField(40, new Dimension(400, 21));

	JLabel sigFileLabel = Common.createJLabel("Enter signature filename: ");
	JTextField sigFileField = Common.createJTextField(40,
			new Dimension(400, 21));

	private String prefDirectoryName;
	private String prefFileName;
	private String prefBeginCut;
	private String prefEndCut;
	private String prefProblemDescExtension;
	private String prefSignatureFileName;
	private boolean prefLineComments;
	private boolean prefOverrideFileName;
	private boolean prefProvideBreaks;
	private boolean prefWriteProblemDescFile;
	private int prefBreakAt;
	private boolean prefHTMLDesc;
	private boolean prefBackup;

	public EditorConfig(Preferences prefx) {
		Common.setDefaultAttributes(this, new BorderLayout());

		setBackground(Common.WPB_COLOR);

		Box dirNamePane = Common.createHorizontalBox(new Component[] {
				this.dirNameLabel, this.dirNameField }, true);

		Box backupPane = Common.createHorizontalBox(
				new Component[] { this.backup }, true);

		Box fileNamePane = Common.createHorizontalBox(
				new Component[] { Box.createHorizontalStrut(20),
						this.fileNameLabel, this.fileNameField }, true);

		Box sigFilePane = Common.createHorizontalBox(new Component[] {
				this.sigFileLabel, this.sigFileField }, true);

		Box overridePane = Common.createHorizontalBox(
				new Component[] { this.overrideFileNameField }, true);

		Box lineCommentsPane = Common.createHorizontalBox(
				new Component[] { this.useLineComments }, true);

		Box htmlDescPane = Common.createHorizontalBox(
				new Component[] { this.htmlDesc }, true);

		Box breakAtPane = Common.createHorizontalBox(new Component[] {
				this.provideBreakField, this.breakAtField }, true);

		Box beginCutPane = Common.createHorizontalBox(new Component[] {
				this.beginCutLabel, this.beginCutField }, true);

		Box endCutPane = Common.createHorizontalBox(new Component[] {
				this.endCutLabel, this.endCutField }, true);

		Box probDescBox = Common.createHorizontalBox(new Component[] {
				this.problemDescFileWrite,
				this.problemDescFileField
				}, true);

		Box all = Box.createVerticalBox();
		all.add(Box.createVerticalStrut(10));
		all.add(dirNamePane);
		all.add(Box.createVerticalStrut(5));
		all.add(backupPane);
		all.add(Box.createVerticalStrut(5));
		all.add(overridePane);
		all.add(Box.createVerticalStrut(1));
		all.add(fileNamePane);

		all.add(Box.createVerticalStrut(5));
		all.add(htmlDescPane);

		all.add(Box.createVerticalStrut(5));
		all.add(probDescBox);

		all.add(Box.createVerticalStrut(5));
		all.add(lineCommentsPane);
		all.add(Box.createVerticalStrut(1));
		all.add(breakAtPane);
		all.add(Box.createVerticalStrut(1));
		all.add(beginCutPane);
		all.add(Box.createVerticalStrut(1));
		all.add(endCutPane);
		all.add(Box.createVerticalStrut(5));
		all.add(sigFilePane);
		all.add(Box.createVerticalGlue());

		add(all, "North");

		this.useLineComments.addActionListener(this);
		this.overrideFileNameField.addActionListener(this);
		this.problemDescFileWrite.addActionListener(this);
		this.provideBreakField.addActionListener(this);
		this.htmlDesc.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == this.overrideFileNameField) {
			this.fileNameLabel.setEnabled(!this.overrideFileNameField
					.isSelected());
			this.fileNameField.setEnabled(!this.overrideFileNameField
					.isSelected());
		} else if ((source == this.provideBreakField)
				&& (!this.htmlDesc.isSelected())) {
			this.breakAtField.setEnabled(this.provideBreakField.isSelected());
		} else if (source == this.problemDescFileWrite) {
			if (this.htmlDesc.isSelected())
				this.problemDescFileWrite.setSelected(true);
			this.problemDescFileField.setEnabled(this.problemDescFileWrite
					.isSelected());
			this.useLineComments.setEnabled(!this.problemDescFileWrite
					.isSelected());
			if (this.problemDescFileWrite.isSelected())
				this.useLineComments.setSelected(false);
		} else if (source == this.htmlDesc) {
			this.useLineComments.setEnabled(!this.htmlDesc.isSelected());
			this.provideBreakField.setEnabled(!this.htmlDesc.isSelected());
			this.breakAtField.setEnabled(!this.htmlDesc.isSelected());

			if (this.htmlDesc.isSelected()) {
				this.useLineComments.setSelected(false);
				this.provideBreakField.setSelected(false);
				this.problemDescFileWrite.setEnabled(true);
				this.problemDescFileWrite.setSelected(true);

				this.problemDescFileField.setEnabled(true);
			}
			if (this.problemDescFileWrite.isSelected()) {
				this.useLineComments.setEnabled(false);
				this.useLineComments.setSelected(false);
			}
		}
	}

	public String getTabTitle() {
		return "Editor";
	}

	public String getTabToolTip() {
		return "Editor configuration";
	}

	public Icon getTabIcon() {
		return null;
	}

	@Override
	public void loadPreferencesToUI() {
		this.dirNameField.setText(this.prefDirectoryName);

		this.backup.setSelected(this.prefBackup);

		this.overrideFileNameField.setSelected(this.prefOverrideFileName);

		this.useLineComments.setSelected(this.prefLineComments);

		this.problemDescFileWrite.setSelected(this.prefWriteProblemDescFile);

		String probDescExt = this.prefProblemDescExtension;
		this.problemDescFileField.setText(probDescExt);
		this.problemDescFileField.setEnabled(this.problemDescFileWrite
				.isSelected());

		String fileName = this.prefFileName;
		this.fileNameLabel.setEnabled(!this.overrideFileNameField.isSelected());
		this.fileNameField.setText(fileName);
		this.fileNameField.setEnabled(!this.overrideFileNameField.isSelected());

		String sigFileName = this.prefSignatureFileName;
		this.sigFileField.setText(sigFileName);

		this.provideBreakField.setSelected(this.prefProvideBreaks);
		this.breakAtField.setText(String.valueOf(this.prefBreakAt));
		this.breakAtField.setEnabled(this.provideBreakField.isSelected());

		this.beginCutField.setText(this.prefBeginCut);

		this.endCutField.setText(this.prefEndCut);

		this.useLineComments.setSelected(this.prefLineComments);
		if (this.prefWriteProblemDescFile) {
			this.useLineComments.setEnabled(false);
			this.useLineComments.setSelected(false);
		}

		this.htmlDesc.setSelected(this.prefHTMLDesc);
		if (this.htmlDesc.isSelected()) {
			this.useLineComments.setEnabled(false);

			this.provideBreakField.setEnabled(false);
			this.breakAtField.setEnabled(false);

			this.problemDescFileWrite.setEnabled(false);
			this.problemDescFileWrite.setSelected(false);

			this.problemDescFileField.setEnabled(false);
		}		
	}

	@Override
	public boolean savePreferencesFromUI() {
		if ((this.overrideFileNameField.isSelected())
				&& (this.fileNameField.getText().trim().equals(""))) {
			Common.showMessage("Error", "You must specify a filename", null);
			return false;
		}
		int breakAt;

		try {
			breakAt = Integer.parseInt(this.breakAtField.getText());
		} catch (NumberFormatException e) {
			Common.showMessage("Error", "The break at is not a number", null);
			return false;
		}
		this.prefDirectoryName = this.dirNameField.getText();
		this.prefFileName = this.fileNameField.getText();
		this.prefBeginCut = this.beginCutField.getText();
		this.prefEndCut = this.endCutField.getText();
		this.prefSignatureFileName = this.sigFileField.getText();

		this.prefLineComments = this.useLineComments.isSelected();
		this.prefOverrideFileName = this.overrideFileNameField.isSelected();

		this.prefWriteProblemDescFile = this.problemDescFileWrite.isSelected();
		this.prefProblemDescExtension = this.problemDescFileField.getText();

		this.prefProvideBreaks = this.provideBreakField.isSelected();
		this.prefBreakAt = breakAt;

		this.prefHTMLDesc = this.htmlDesc.isSelected();
		this.prefBackup = this.backup.isSelected();

		return true;
	}

	public static void main(String[] s) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(3);
		Preferences pref = new Preferences();
		pref.removeAllProperties();
		EditorConfig config = new EditorConfig(pref);
		pref.loadInto(config);
		config.loadPreferencesToUI();
		frame.getContentPane().add(config);
		frame.pack();
		frame.setVisible(true);
	}

}
