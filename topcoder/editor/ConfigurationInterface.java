/** 
 * ConfigurationInterface.java
 *
 * Description:		Interface used by all the configuration panels
 * @author			Tim "Pops" Roberts
 * @version			1.0
 */

package topcoder.editor;


interface ConfigurationInterface {
	public String getTabTitle();
	public String getTabToolTip();
	public javax.swing.Icon getTabIcon();
	public boolean isSavePending();
	public boolean savePreferences();
	public void resetSavePending();
}


/* @(#)ConfigurationInterface.java */
