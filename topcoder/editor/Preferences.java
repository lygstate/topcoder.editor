/** 
 * Preferences.java
 *
 * Description:		Preferences class for FileEdit
 * @author			Tim "Pops" Roberts
 * @version			3.0
 */

package topcoder.editor;

import java.util.ArrayList;
import java.util.Observer;

import com.topcoder.client.contestApplet.common.LocalPreferences;

class Preferences {
	private static LocalPreferences pref = LocalPreferences.getInstance();
	private Observer notify;

	public String name;

	public final static String POWEREDBY = "topcoder.editor.config.poweredby";
	public final static String NUMCODEPROCESSORS = "topcoder.editor.config.numofcodeprocessors";
	public final static String CODEPROCESSOR = "topcoder.editor.config.codeprocessor";

	public Preferences(String name) {
		this.name = name;
	}

	public Preferences(Observer notify) {
		this.notify = notify;
		pref.addSaveObserver(notify);
	}

	public void finalize() {
		// Clean up
		if (notify != null)
			pref.removeSaveObserver(notify);
	}

	public boolean isPoweredBy() {
		return getBooleanProperty(POWEREDBY, true);
	}

	public String[] getCodeProcessors() {
		int num = getIntegerProperty(Preferences.NUMCODEPROCESSORS, 0);
		ArrayList<String> rc = new ArrayList<String>();
		for (int x = 0; x < num; x++) {
			String codeProcessor = getStringProperty(Preferences.CODEPROCESSOR
					+ x, "");
			if (codeProcessor == null || codeProcessor.trim().length() == 0)
				continue;
			rc.add(codeProcessor);
		}
		return (String[]) rc.toArray(new String[0]);
	}

	public void setCodeProcessors(String[] codeProcessor) {
		int num = getIntegerProperty(Preferences.NUMCODEPROCESSORS, 0);

		// Remove the excess ones
		if (num > codeProcessor.length) {
			for (int x = codeProcessor.length; x < num; x++) {
				pref.removeProperty(Preferences.CODEPROCESSOR + x);
			}
		}

		// Save/overlay the existing ones
		for (int x = 0; x < codeProcessor.length; x++) {
			pref.setProperty(name + Preferences.CODEPROCESSOR + x,
					codeProcessor[x] == null ? "" : codeProcessor[x]);
		}

		// Sync the number
		pref.setProperty(name + Preferences.NUMCODEPROCESSORS,
				String.valueOf(codeProcessor.length));
	}

	private final String getStringProperty(String key, String defaultValue) {
		String value = pref.getProperty(name + key);
		return value == null || value.equals("") ? defaultValue : value;
	}

	private final boolean getBooleanProperty(String key, boolean defaultValue) {
		String value = pref.getProperty(name + key);
		return value == null || value.equals("") ? defaultValue : value
				.equals("true");
	}

	private final int getIntegerProperty(String key, int defaultValue) {
		String value = pref.getProperty(name + key);
		if (value == null || value.equals(""))
			return defaultValue;

		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public void save() throws java.io.IOException {
		pref.savePreferences();
	}
}
