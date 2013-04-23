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

import topcoder.editor.EntryPoint;
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
			.createJLabel("Enter filename (code,html,text) to use (no extension):");
	JCheckBox overrideFileNameField = Common
			.createJCheckBox("Make filename equal to classname");

	JTextField fileNameField = Common.createJTextField(40, new Dimension(400,
			21));

	JCheckBox htmlDescFileCheckBox = Common
			.createJCheckBox("Write the problem description using HTML");
	JCheckBox textDescFileCheckBox = Common
			.createJCheckBox("Write the problem description using plain text with extension name:");
	JTextField textDescFileField = Common.createJTextField(4, new Dimension(
			75, 21));
	JCheckBox codeDescFileCheckBox = Common
			.createJCheckBox("Write the problem description into source code by line comment");

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
	private String prefTextDescExtension;
	private String prefSignatureFileName;
	private boolean prefUseClassName;

	private boolean prefWriteCodeDescFile;
	private boolean prefWriteTextDescFile;
	private boolean prefWriteHtmlDescFile;

	private boolean prefProvideBreaks;
	private int prefBreakAt;

	private boolean prefBackup;

	public EditorConfig(EntryPoint entry) {
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

		Box WriteCodeDescFilePane = Common.createHorizontalBox(
				new Component[] { this.codeDescFileCheckBox }, true);

		Box WriteHtmlDescFilePane = Common.createHorizontalBox(
				new Component[] { this.htmlDescFileCheckBox }, true);

		Box breakAtPane = Common.createHorizontalBox(new Component[] {
				this.provideBreakField, this.breakAtField }, true);

		Box beginCutPane = Common.createHorizontalBox(new Component[] {
				this.beginCutLabel, this.beginCutField }, true);

		Box endCutPane = Common.createHorizontalBox(new Component[] {
				this.endCutLabel, this.endCutField }, true);

		Box probDescBox = Common.createHorizontalBox(new Component[] {
				this.textDescFileCheckBox,
				this.textDescFileField
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
		all.add(WriteHtmlDescFilePane);

		all.add(Box.createVerticalStrut(5));
		all.add(probDescBox);

		all.add(Box.createVerticalStrut(5));
		all.add(WriteCodeDescFilePane);
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

		this.codeDescFileCheckBox.addActionListener(this);
		this.overrideFileNameField.addActionListener(this);
		this.textDescFileCheckBox.addActionListener(this);
		this.provideBreakField.addActionListener(this);
		this.htmlDescFileCheckBox.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.savePreferencesFromUI();
		this.loadPreferencesToUI();
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

		this.overrideFileNameField.setSelected(this.prefUseClassName);

		this.fileNameLabel.setEnabled(!this.prefUseClassName);
		this.fileNameField.setText(this.prefFileName);
		this.fileNameField.setEnabled(!this.prefUseClassName);

		String sigFileName = this.prefSignatureFileName;
		this.sigFileField.setText(sigFileName);

		this.provideBreakField.setSelected(this.prefProvideBreaks);
		this.breakAtField.setText(String.valueOf(this.prefBreakAt));
		this.breakAtField.setEnabled(this.provideBreakField.isSelected());

		this.beginCutField.setText(this.prefBeginCut);
		this.endCutField.setText(this.prefEndCut);

		this.htmlDescFileCheckBox.setSelected(this.prefWriteHtmlDescFile);
		this.textDescFileCheckBox.setSelected(this.prefWriteTextDescFile);
		this.textDescFileField.setText(this.prefTextDescExtension);
		this.textDescFileField.setEnabled(this.prefWriteTextDescFile);

		this.codeDescFileCheckBox.setSelected(this.prefWriteCodeDescFile);
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

		this.prefUseClassName = this.overrideFileNameField.isSelected();

		this.prefWriteHtmlDescFile = this.htmlDescFileCheckBox.isSelected();
		this.prefWriteTextDescFile = this.textDescFileCheckBox.isSelected();
		this.prefTextDescExtension = this.textDescFileField.getText();
		this.prefWriteCodeDescFile = this.codeDescFileCheckBox.isSelected();

		this.prefProvideBreaks = this.provideBreakField.isSelected();
		this.prefBreakAt = breakAt;

		this.prefBackup = this.backup.isSelected();

		return true;
	}

}
