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

import topcoder.editor.ui.CodeProcessorConfig;
import topcoder.editor.ui.CodeTemplateConfig;
import topcoder.editor.ui.Common;
import topcoder.editor.ui.ConfigurationInterface;
import topcoder.editor.ui.EditorConfig;

public class ConfigurationDialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1712681747171330702L;

	private Preferences pref;
	ConfigurationInterface[] config;

	private JTabbedPane tab = new JTabbedPane();
	private JButton resetButton = new JButton("Reset");
	private JButton saveButton = new JButton("Save");
	private JButton closeButton = new JButton("Close");

	private WindowHandler windowHandler = new WindowHandler();

	public ConfigurationDialog(Preferences pref) {
		super((JFrame) null, "TopCoder Editor Configuration", true);
		setSize(new Dimension(600, 400));
		this.pref = pref;
		this.config = new ConfigurationInterface[] {
				new EditorConfig(this.pref),
				new CodeProcessorConfig(pref),
				new CodeTemplateConfig(this.pref),
				};

		Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, 0));
		contentPane.setForeground(Common.FG_COLOR);
		contentPane.setBackground(Common.WPB_COLOR);

		this.tab.setForeground(Common.FG_COLOR);
		this.tab.setBackground(Common.WPB_COLOR);

		for (int x = 0; x < config.length; x++) {
			this.tab.addTab(
					config[x].getTabTitle(),
					config[x].getTabIcon(),
					(Component)config[x],
					config[x].getTabToolTip());
		}

		Box buttonPanel = Box.createHorizontalBox();
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(this.resetButton);
		buttonPanel.add(Box.createHorizontalStrut(10));
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

		this.load();

		this.resetButton.addActionListener(this);
		this.saveButton.addActionListener(this);
		this.closeButton.addActionListener(this);

		setDefaultCloseOperation(0);
		addWindowListener(this.windowHandler);

		pack();
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == this.saveButton) {
			if (false == this.saveFromUI()) {
				Common.showMessage("Save", "The UI contains wrong configuration, save failed.",
						null);
				return ;
			}
			save();
		} else if (src == this.closeButton) {
			this.windowHandler.windowClosing(new WindowEvent(this, 201));
		} else if (src == this.resetButton) {
			if (Common
					.confirm(
							"Reset configuration",
							"Do you want to reset all TopCoder Editor settings?",
							null)) {
				this.pref.removeAllProperties();
				this.load();
			}
		}
	}

	/* Load the preferences from File, and then load into the UI element */
	public void load() {
		for (int x = 0; x < this.config.length; x++) {
			ConfigurationInterface configX = this.config[x];
			/* Load preferences from the config file into config variable*/
			pref.loadInto(configX);
			/* Load preferences from config variable into config UI */
			configX.loadPreferencesToUI();
		}
	}

	/* Save the preferences in the config variables into pref variable.
	 * and then save into the preference file.  
	 */
	public boolean save() {
		for (int x = 0; x < this.config.length; x++) {
			/* Save into the preference class */
			pref.saveFrom(this.config[x]);
		}

		try {
			/* Save into the preference file */
			this.pref.save();
			Common.showMessage("Save", "Preferences were saved successfully",
					null);
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.toString(),
					"Error saving preferences", 0);
		}
		return false;
	}

	public boolean saveFromUI() {
		for (int x = 0; x < this.config.length; x++) {
			/* The preferences contained in the UI is not correct */
			if (false == this.config[x].savePreferencesFromUI()) {
				return false;
			}
		}
		return true;
	}

	public boolean isSavePending() {
		for (int x = 0; x < this.config.length; x++) {
			/* There is different between the config class and pref class */
			if (pref.isDifferentWith(this.config[x])) {
				return true;
			}
		}
		return false;
	}

	private class WindowHandler extends WindowAdapter {
		WindowHandler() {
		}

		public void windowClosing(WindowEvent e) {
			if (false == ConfigurationDialog.this.saveFromUI()) {
				Common.showMessage("Save", "The UI contains wrong configuration, can not save.",
						null);
			} else if (ConfigurationDialog.this.isSavePending()) {
				if (Common
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
		ConfigurationDialog ff = new ConfigurationDialog(new Preferences());
		ff.setVisible(true);
	}
}
