/** 
 * ConfigurationDialog.java
 *
 * Description:		PopsEditor Configuration Dialog
 * @author			Tim "Pops" Roberts
 * @version			1.0
 */

package topcoder.editor;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

class ConfigurationDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -827816084045078323L;
	private Preferences pref;
	private JTabbedPane tab			= new JTabbedPane();
	private JButton saveButton		= new JButton("Save");
	private JButton closeButton		= new JButton("Close");
	
	private ConfigurationInterface[] config;
	private WindowHandler windowHandler		= new WindowHandler();
	
	public ConfigurationDialog(Preferences pref) {

		super((JFrame)null, "CodeProcessor Configuration", true);
		
		this.pref = pref;
		config = new ConfigurationInterface[] { new ConfigurationPanel(pref) };
		
		setSize(new Dimension(600,400));

		// Configure the content pane
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		contentPane.setForeground(Common.FG_COLOR);
		contentPane.setBackground(Common.WPB_COLOR);
		
		
		tab.setForeground(Common.FG_COLOR);
		tab.setBackground(Common.WPB_COLOR);
		
		// Add tabs
		for (int x=0;x<config.length;x++) {
			tab.addTab(config[x].getTabTitle(), config[x].getTabIcon(), (Component)config[x], config[x].getTabToolTip());
		}

		
		// Create button panel
		contentPane.add(tab, new GridBagConstraints(0,0,3,1,1,1,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(10,10,10,10),0,0));
		contentPane.add(saveButton, new GridBagConstraints(1,1,1,1,0,0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10,10,10,10),0,0));
		contentPane.add(closeButton, new GridBagConstraints(2,1,1,1,0,0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10,0,10,10),0,0));

		// Add listeners
		saveButton.addActionListener(this);
		closeButton.addActionListener(this);

		// Set the close operations
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);		
		addWindowListener(windowHandler);
		
		this.pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == saveButton) {
			save();
		} else if (src == closeButton) {
			windowHandler.windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}

	public boolean save() {
		
		// Save each of the tabs
		for (int x=0;x<config.length;x++) {
			if(!config[x].savePreferences()) {
				tab.setSelectedIndex(x);
				return false;
			}
		}
			
		// Write out the preferences
		try {
			pref.save();
			for(int x=0;x<config.length;x++) config[x].resetSavePending();
			Common.showMessage("Save","Preferences were saved successfully - changes will **NOT** have effect until you restart your browser",null);
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error saving preferences", JOptionPane.ERROR_MESSAGE);     	
			return false;
		}
		
	}
	
	private class WindowHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
		
			// Find out if anything has save's pending
			boolean savePending=false;
			for(int x=0;x<config.length;x++) if(config[x].isSavePending()) { savePending=true; break; }
			
			// If so...
			if (savePending) {

				// Should we save?
				if (Common.confirm("Save Pending", "Changes are pending.  Do you want to save before closing?", null)) {
					// Try to save
					if (!save()) return;
				}
			}
			// Close the window
			dispose();
		}
	}
	
}


/* @(#)ConfigurationDialog.java */
