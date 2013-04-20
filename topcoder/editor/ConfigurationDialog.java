package topcoder.editor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import topcoder.editor.panels.CodeTemplateConfig;
import topcoder.editor.panels.CodeProcessorConfig;
import topcoder.editor.panels.ConfigurationInterface;
import topcoder.editor.panels.EditConfig;
import topcoder.editor.panels.FileEditorCommon;

public class ConfigurationDialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1712681747171330702L;
	private Preferences pref = new Preferences();
	private JTabbedPane tab = new JTabbedPane();
	private JButton saveButton = new JButton("Save");
	private JButton closeButton = new JButton("Close");

	private ConfigurationInterface[] config = {
			new CodeProcessorConfig(pref),
			new EditConfig(this.pref),
			new CodeTemplateConfig(this.pref),
			};
	private WindowHandler windowHandler = new WindowHandler();

	public ConfigurationDialog() {
		super((JFrame) null, "TopCoder Editor Configuration", true);
		setSize(new Dimension(600, 400));

		Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, 0));
		contentPane.setForeground(FileEditorCommon.FG_COLOR);
		contentPane.setBackground(FileEditorCommon.WPB_COLOR);

		this.tab.setForeground(FileEditorCommon.FG_COLOR);
		this.tab.setBackground(FileEditorCommon.WPB_COLOR);

		for (int x = 0; x < this.config.length; x++) {
			this.tab.addTab(this.config[x].getTabTitle(),
					this.config[x].getTabIcon(), (Component) this.config[x],
					this.config[x].getTabToolTip());
		}

		Box buttonPanel = Box.createHorizontalBox();
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(this.saveButton);
		buttonPanel.add(Box.createHorizontalStrut(10));
		buttonPanel.add(this.closeButton);

		Box mainPanel = Box.createVerticalBox();
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(this.tab);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(buttonPanel);
		mainPanel.add(Box.createVerticalStrut(10));

		contentPane.add(Box.createHorizontalStrut(10));
		contentPane.add(mainPanel);
		contentPane.add(Box.createHorizontalStrut(10));

		this.saveButton.addActionListener(this);
		this.closeButton.addActionListener(this);

		setDefaultCloseOperation(0);
		addWindowListener(this.windowHandler);

		pack();
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == this.saveButton)
			save();
		else if (src == this.closeButton)
			this.windowHandler.windowClosing(new WindowEvent(this, 201));
	}

	public boolean save() {
		for (int x = 0; x < this.config.length; x++) {
			if (!this.config[x].savePreferences()) {
				this.tab.setSelectedIndex(x);
				return false;
			}
		}

		try {
			this.pref.save();
			for (int x = 0; x < this.config.length; x++)
				this.config[x].resetSavePending();
			FileEditorCommon.showMessage("Save", "Preferences were saved successfully",
					null);
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.toString(),
					"Error saving preferences", 0);
		}
		return false;
	}

	private class WindowHandler extends WindowAdapter {
		WindowHandler() {
		}

		public void windowClosing(WindowEvent e) {
			boolean savePending = false;
			for (int x = 0; x < ConfigurationDialog.this.config.length; x++)
				if (ConfigurationDialog.this.config[x].isSavePending()) {
					savePending = true;
					break;
				}

			if (savePending) {
				if (FileEditorCommon
						.confirm(
								"Save Pending",
								"Changes are pending.  Do you want to save before closing?",
								null)) {
					if (!ConfigurationDialog.this.save())
						return;
				}
			}

			ConfigurationDialog.this.dispose();
		}
	}

	public static void main(String[] args) {
		ConfigurationDialog ff = new ConfigurationDialog();
		ff.setVisible(true);
	}
}
