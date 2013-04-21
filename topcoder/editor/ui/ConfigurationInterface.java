package topcoder.editor.ui;

import javax.swing.Icon;

public abstract interface ConfigurationInterface {
	public abstract String getTabTitle();

	public abstract String getTabToolTip();

	public abstract Icon getTabIcon();

	/* These two function should be implemented, because these are 
	 * depending on the specific UI
	 */
	/* Local variable -> UI */
	public abstract void loadPreferencesToUI();
	/* UI -> Local variable */
	public abstract boolean savePreferencesFromUI();
}
