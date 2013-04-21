/** 
 * Preferences.java
 *
 * Description:		Preferences class for FileEdit
 * @author			Tim "Pops" Roberts
 * @version			3.0
 */

package topcoder.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import com.topcoder.client.contestApplet.common.LocalPreferences;

import java.lang.reflect.*;

public class Preferences {
	private static LocalPreferences pref = LocalPreferences.getInstance();
	private Observer notify;

	public final static String POWEREDBY = "topcoder.editor.config.poweredby";

	public final static String NUMCODEPROCESSORS = "topcoder.editor.config.numofcodeprocessors";
	public final static String CODEPROCESSOR = "topcoder.editor.config.codeprocessor";

	public static final String JAVATEMPLATE = "topcoder.editor.config.javatemplate";
	public static final String CPPTEMPLATE = "topcoder.editor.config.cpptemplate";
	public static final String CSHARPTEMPLATE = "topcoder.editor.config.csharptemplate";
	public static final String JAVAEXTENSION = "topcoder.editor.config.javaextension";
	public static final String CPPEXTENSION = "topcoder.editor.config.cppextension";
	public static final String CSHARPEXTENSION = "topcoder.editor.config.csharpextension";
	public static final String INDENTTYPE = "topcoder.editor.config.indenttype";
	public static final String TABSIZE = "topcoder.editor.config.tabsize";

	public static final String DIRNAMEKEY = "topcoder.editor.config.dirName";
	public static final String FILENAMEKEY = "topcoder.editor.config.fileName";
	public static final String OVERRIDEFILENAME = "topcoder.editor.config.overrideFileName";
	public static final String PROVIDEBREAKS = "topcoder.editor.config.provideBreaks";
	public static final String BREAKAT = "topcoder.editor.config.breakAt";
	public static final String LINECOMMENTS = "topcoder.editor.config.lineComments";
	public static final String BEGINCUT = "topcoder.editor.config.beginCut";
	public static final String ENDCUT = "topcoder.editor.config.endCut";
	public static final String PROBDESCFILEWRITE = "topcoder.editor.config.probdescfilewrite";
	public static final String PROBDESCFILEEXTENSION = "topcoder.editor.config.probdescfileextnsion";
	public static final String SIGNATUREFILENAME = "topcoder.editor.config.signaturefilename";
	public static final String HTMLDESC = "topcoder.editor.config.htmldesc";
	public static final String BACKUP = "topcoder.editor.config.backup";

	public Preferences() {
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

	public void setPoweredBy(boolean showPowerBy) {
		setBooleanProperty(POWEREDBY, showPowerBy);
	}

	public String[] getCodeProcessors() {
		boolean needUpdate = false;
		int num = getIntProperty(Preferences.NUMCODEPROCESSORS, -1);
		if (num == -1) {
			String[] codeProcessors = { "topcoder.editor.ExampleProcessor" };
			setCodeProcessors(codeProcessors);
			return codeProcessors;
		}
		List<String> rc = new ArrayList<String>();
		for (int x = 0; x < num; x++) {
			String codeProcessor =
					getStringProperty(Preferences.CODEPROCESSOR+ x, "");
			if (codeProcessor == null || codeProcessor.trim().length() == 0) {
				needUpdate = true;
				continue;
			}
			rc.add(codeProcessor);
		}
		String[] ret = rc.toArray(new String[0]);
		if (needUpdate) {
			setCodeProcessors(ret);
		}
		return ret;
	}

	public void setCodeProcessors(String[] codeProcessors) {
		int num = getIntProperty(NUMCODEPROCESSORS, -1);

		// Remove the excess ones
		if (num > codeProcessors.length) {
			for (int x = codeProcessors.length; x < num; x++) {
				pref.removeProperty(CODEPROCESSOR + x);
			}
		}

		// Save/overlay the existing ones
		for (int x = 0; x < codeProcessors.length; x++) {
			setStringProperty(CODEPROCESSOR + x, codeProcessors[x] == null ? ""
					: codeProcessors[x]);
		}

		// Sync the number
		if (num != codeProcessors.length) {
			setIntProperty(NUMCODEPROCESSORS, codeProcessors.length);
		}
	}

	public String getJAVATemplate() {
		return getStringProperty(
				JAVATEMPLATE,
				"$BEGINCUT$\n$PROBLEMDESC$\n$ENDCUT$\nimport java.util.*;\npublic class $CLASSNAME$ {\n\tpublic $RC$ $METHODNAME$($METHODPARMS$) {\n\t\t\n\t}\n\tpublic static void main(String[] args) {\n\t\t$CLASSNAME$ temp = new $CLASSNAME$();\n\t\tSystem.out.println(temp.$METHODNAME$($METHODPARMS$));\n\t}\n}");
	}

	public void setJAVATemplate(String template) {
		pref.setProperty(JAVATEMPLATE, template);
	}

	public String getJAVAExtension() {
		return getStringProperty(JAVAEXTENSION, "java");
	}

	public void setJAVAExtension(String extension) {
		pref.setProperty(JAVAEXTENSION, extension);
	}

	public final String getCSHARPTemplate() {
		return getStringProperty(
				CSHARPTEMPLATE,
				"using System;\r\nusing System.Collections;\r\npublic class $CLASSNAME$ {\r\n\tpublic $RC$ $METHODNAME$($METHODPARMS$) {\r\n\t\t$CARETPOSITION$\r\n\t}\r\n}");
	}

	public void setCSHARPTemplate(String template) {
		setStringProperty(CSHARPTEMPLATE, template);
	}

	public String getCSHARPExtension() {
		return getStringProperty(CSHARPEXTENSION, "cs");
	}

	public void setCSHARPExtension(String extension) {
		setStringProperty(CSHARPEXTENSION, extension);
	}

	public String getCPPTemplate() {
		return getStringProperty(
				CPPTEMPLATE,
				"$BEGINCUT$\n$PROBLEMDESC$\n$ENDCUT$\n#line $NEXTLINENUMBER$ \"$FILENAME$\"\n#include <string>\n#include <vector>\nclass $CLASSNAME$ {\n\tpublic:\n\t$RC$ $METHODNAME$($METHODPARMS$) {\n\t\t\n\t}\n};");
	}

	public void setCPPTemplate(String template) {
		setStringProperty(CPPTEMPLATE, template);
	}

	public String getCPPExtension() {
		return getStringProperty(CPPEXTENSION, "cpp");
	}

	public void setCPPExtension(String extension) {
		setStringProperty(CPPEXTENSION, extension);
	}

	public String getIndentType() {
		return getStringProperty(INDENTTYPE, "Tab");
	}

	public void setIndentType(String indenType) {
		setStringProperty(INDENTTYPE, indenType);
	}

	public int getTabSize() {
		return getIntProperty(TABSIZE, 4);
	}

	public void setTabSize(int tabSize) {
		setIntProperty(TABSIZE, tabSize);
	}

	public String getDirectoryName() {
		return getStringProperty(DIRNAMEKEY, ".");
	}

	public void setDirectoryName(String text) {
		setStringProperty(DIRNAMEKEY, text);
	}

	public String getFileName() {
		return getStringProperty(FILENAMEKEY, "problem");
	}

	public void setFileName(String text) {
		setStringProperty(FILENAMEKEY, text);
	}

	public String getSignatureFileName() {
		return getStringProperty(SIGNATUREFILENAME, "");
	}

	public void setSignatureFileName(String text) {
		setStringProperty(SIGNATUREFILENAME, text);
	}

	public String getBeginCut() {
		return getStringProperty(BEGINCUT, "// BEGIN CUT HERE");
	}

	public void setBeginCut(String text) {
		setStringProperty(BEGINCUT, text);
	}

	public String getEndCut() {
		return getStringProperty(ENDCUT, "// END CUT HERE");
	}

	public void setEndCut(String text) {
		setStringProperty(ENDCUT, text);
	}

	public String getProblemDescExtension() {
		return getStringProperty(PROBDESCFILEEXTENSION, "txt");
	}

	public void setProblemDescExtension(String text) {
		setStringProperty(PROBDESCFILEEXTENSION, text);
	}

	public boolean isOverrideFileName() {
		return getBooleanProperty(OVERRIDEFILENAME, false);
	}

	public void setOverrideFileName(boolean override) {
		setBooleanProperty(OVERRIDEFILENAME, override);
	}

	public boolean isProvideBreaks() {
		return getBooleanProperty(PROVIDEBREAKS, false);
	}

	public void setProvideBreaks(boolean provideBreaks) {
		setBooleanProperty(PROVIDEBREAKS, provideBreaks);
	}

	public boolean isLineComments() {
		return getBooleanProperty(LINECOMMENTS, true);
	}

	public void setLineComments(boolean lineComments) {
		setBooleanProperty(LINECOMMENTS, lineComments);
	}

	public boolean isWriteProblemDescFile() {
		return getBooleanProperty(PROBDESCFILEWRITE, false);
	}

	public void setWriteProblemDescFile(boolean probDescFile) {
		setBooleanProperty(PROBDESCFILEWRITE, probDescFile);
	}

	public boolean isHTMLDesc() {
		return getBooleanProperty(HTMLDESC, false);
	}

	public void setHTMLDesc(boolean htmlDesc) {
		setBooleanProperty(HTMLDESC, htmlDesc);
	}

	public boolean isBackup() {
		return getBooleanProperty(BACKUP, true);
	}

	public void setBackup(boolean backup) {
		setBooleanProperty(BACKUP, backup);
	}

	public int getBreakAt() {
		return getIntProperty(BREAKAT, 60);
	}

	public void setBreakAt(int breakAt) {
		setIntProperty(BREAKAT, breakAt);
	}

	private final String getStringProperty(String key, String defaultValue) {
		String value = pref.getProperty(key);
		if (value == null || (value.equals("") && !defaultValue.equals(""))) {
			setStringProperty(key, defaultValue);
			return defaultValue;
		}
		return value;
	}

	private final void setStringProperty(String key, String value) {
		pref.setProperty(key, value);
	}

	private final boolean getBooleanProperty(String key, boolean defaultValue) {
		String value = pref.getProperty(key);
		if (value != null) {
			if (value.equals("true")) {
				return true;
			} else if (value.equals("false")) {
				return false;
			}
		}
		setBooleanProperty(key, defaultValue);
		return defaultValue;
	}

	private final void setBooleanProperty(String key, boolean value) {
		pref.setProperty(key, value ? "true" : "false");
	}

	private final int getIntProperty(String key, int defaultInt) {
		String value = pref.getProperty(key);
		if ((value == null) || (value.equals(""))) {
			setIntProperty(key, defaultInt);
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			setIntProperty(key, defaultInt);
			return defaultInt;
		}
	}

	private final void setIntProperty(String key, int value) {
		pref.setProperty(key, String.valueOf(value));
	}

	public void save() throws java.io.IOException {
		pref.savePreferences();
	}

	public static List<String> getPropertyKeys() {
		Field[] declaredFields = Preferences.class.getDeclaredFields();
		List<String> propertyKeys = new ArrayList<String>();
		for (Field field : declaredFields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) && Modifier.isFinal(mod)
					&& field.getType() == String.class) {
				try {
					String propertyKey = (String) field.get(null);
					propertyKeys.add(propertyKey);
				} catch (Exception e) {
				}
			}
		}
		return propertyKeys;
	}

	public static List<Field> getPreferenceFields(Object o, Class<?> c) {
		Field[] prefFields = c.getDeclaredFields();
		List<Field> prefs = new ArrayList<Field>();
		for (Field field : prefFields) {
			int mod = field.getModifiers();
			Class<?> type = field.getType();
			if (Modifier.isPrivate(mod)
				&& (type == String.class
				|| type == int.class
				|| type == boolean.class)) {
				try {
				} catch (Exception e) {
				}
			}
		}
		return prefs;
	}

	public void removeAllProperties() {
		for (String propertyKey : getPropertyKeys()) {
			pref.removeProperty(propertyKey);
		}
	}

	public static void main(String[] args) {
		Preferences pref = new Preferences();
		pref.setJAVATemplate("kdjf");
		pref.getBreakAt();
		// pref.removeAllProperty();
		try {
			pref.save();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}