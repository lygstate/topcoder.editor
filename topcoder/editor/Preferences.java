/** 
 * Preferences.java
 *
 * Description:		Preferences class for FileEdit
 * @author			Tim "Pops" Roberts
 * @version			3.0
 */

package topcoder.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import com.topcoder.client.contestApplet.common.LocalPreferences;

import java.lang.reflect.*;

public class Preferences {
	private static LocalPreferences pref = LocalPreferences.getInstance();
	private Observer notify;

	public final static String POWEREDBY = "topcoder.editor.config.poweredBy";

	public final static String CODEPROCESSORNUMBER = "topcoder.editor.config.codeProcessorNumber";
	public final static String CODEPROCESSOR = "topcoder.editor.config.codeProcessor";

	public static final String JAVATEMPLATE = "topcoder.editor.config.javaTemplate";
	public static final String CPPTEMPLATE = "topcoder.editor.config.cppTemplate";
	public static final String CSHARPTEMPLATE = "topcoder.editor.config.csharpTemplate";
	public static final String JAVAEXTENSION = "topcoder.editor.config.javaExtension";
	public static final String CPPEXTENSION = "topcoder.editor.config.cppExtension";
	public static final String CSHARPEXTENSION = "topcoder.editor.config.csharpExtension";

	public static final String INDENTTYPE = "topcoder.editor.config.indentType";
	public static final String TABSIZE = "topcoder.editor.config.tabSize";

	public static final String DIRNAMEKEY = "topcoder.editor.config.dirName";

	/* Use class name as file name */
	public static final String USECLASSNAME = "topcoder.editor.config.useClassName";
	public static final String FILENAMEKEY = "topcoder.editor.config.fileName";

	public static final String PROVIDEBREAKS = "topcoder.editor.config.provideBreaks";
	public static final String BREAKAT = "topcoder.editor.config.breakAt";
	public static final String BEGINCUT = "topcoder.editor.config.beginCut";
	public static final String ENDCUT = "topcoder.editor.config.endCut";

	public static final String WRITEHTMLDESCFILE = "topcoder.editor.config.writeHtmlDescfile";
	public static final String WRITETEXTDESCFILE = "topcoder.editor.config.writeTextDescfile";
	public static final String TEXTDESCFILEEXTENSION = "topcoder.editor.config.textDescFileExtension";
	public static final String WRITECODEDESCFILE = "topcoder.editor.config.writeCodeDescFile";

	public static final String SIGNATUREFILENAME = "topcoder.editor.config.signatureFilename";
	public static final String BACKUP = "topcoder.editor.config.backup";

	public final static class Tuple<X, Y> {
		public final X x;
		public final Y y;

		public Tuple(X x, Y y) {
			this.x = x;
			this.y = y;
		}
	}

	/* The method main body, and the getter/setter method tuple */
	private static Map<String, Tuple<Method, Method>> methodMap = Preferences
			.getPropertyMethods();
	private static List<Class<?>> supportedClass = Arrays
			.asList(new Class<?>[] { int.class, boolean.class, String.class,
					String[].class });

	private static Preferences instance = null;

	protected Preferences() {
	}

	public static Preferences getInstance() {
		if (instance == null) {
			instance = new Preferences();
		}
		return instance;
	}

	public void addSaveObserver(Observer notify) {
		if (this.notify != null) {
			pref.removeSaveObserver(notify);
		}
		this.notify = notify;
		pref.addSaveObserver(notify);
	}

	public void finalize() {
		// Clean up
		if (notify != null) {
			pref.removeSaveObserver(notify);
			this.notify = null;
		}
	}

	public boolean isPoweredBy() {
		return getBooleanProperty(POWEREDBY, true);
	}

	public void setPoweredBy(boolean showPowerBy) {
		setBooleanProperty(POWEREDBY, showPowerBy);
	}

	public String[] getCodeProcessors() {
		boolean needUpdate = false;
		int num = getIntProperty(Preferences.CODEPROCESSORNUMBER, -1);
		if (num == -1) {
			String[] codeProcessors = { "topcoder.editor.ExampleProcessor" };
			setCodeProcessors(codeProcessors);
			return codeProcessors;
		}
		List<String> rc = new ArrayList<String>();
		for (int x = 0; x < num; x++) {
			String codeProcessor = getStringProperty(Preferences.CODEPROCESSOR
					+ x, "");
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
		int num = getIntProperty(CODEPROCESSORNUMBER, -1);

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
			setIntProperty(CODEPROCESSORNUMBER, codeProcessors.length);
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

	public boolean isUseClassName() {
		return getBooleanProperty(USECLASSNAME, true);
	}

	public void setUseClassName(boolean override) {
		setBooleanProperty(USECLASSNAME, override);
	}

	public boolean isProvideBreaks() {
		return getBooleanProperty(PROVIDEBREAKS, false);
	}

	public void setProvideBreaks(boolean provideBreaks) {
		setBooleanProperty(PROVIDEBREAKS, provideBreaks);
	}

	public boolean isWriteHtmlDescFile() {
		return getBooleanProperty(WRITEHTMLDESCFILE, true);
	}

	public void setWriteHtmlDescFile(boolean WriteHtmlDescFile) {
		setBooleanProperty(WRITEHTMLDESCFILE, WriteHtmlDescFile);
	}

	public boolean isWriteTextDescFile() {
		return getBooleanProperty(WRITETEXTDESCFILE, true);
	}

	public void setWriteTextDescFile(boolean probDescFile) {
		setBooleanProperty(WRITETEXTDESCFILE, probDescFile);
	}

	public String getTextDescExtension() {
		return getStringProperty(TEXTDESCFILEEXTENSION, "txt");
	}

	public boolean isWriteCodeDescFile() {
		return getBooleanProperty(WRITECODEDESCFILE, true);
	}

	public void setWriteCodeDescFile(boolean WriteCodeDescFile) {
		setBooleanProperty(WRITECODEDESCFILE, WriteCodeDescFile);
	}

	public void setTextDescExtension(String text) {
		setStringProperty(TEXTDESCFILEEXTENSION, text);
	}

	public boolean isBackup() {
		return getBooleanProperty(BACKUP, false);
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

	static Method getMethod(Class<?> c, String name) {
		try {
			return c.getMethod(name);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		return null;
	}

	public static Map<String, Tuple<Method, Method>> getPropertyMethods() {
		Method[] declaredMethods = Preferences.class.getDeclaredMethods();
		Map<String, Tuple<Method, Method>> map = new HashMap<String, Tuple<Method, Method>>();
		for (Method field : declaredMethods) {
			int mod = field.getModifiers();
			String name = field.getName();
			if (name.startsWith("set")) {
				name = name.substring("set".length());
			} else {
				name = null;
			}
			if (name == null || name.equals("")) {
				continue;
			}
			if (!Modifier.isPublic(mod)) {
				continue;
			}
			Method m = null;
			if ((m = getMethod(Preferences.class, "get" + name)) != null) {
				map.put(name, new Tuple<Method, Method>(m, field));
			} else if ((m = getMethod(Preferences.class, "is" + name)) != null) {
				map.put(name, new Tuple<Method, Method>(m, field));
			}
		}
		return map;
	}

	public static Map<String, Field> getPreferenceFields(Object o) {
		Class<?> c = o.getClass();
		Field[] prefFields = c.getDeclaredFields();
		Map<String, Field> fields = new HashMap<String, Field>();

		for (Field field : prefFields) {
			int mod = field.getModifiers();
			String name = field.getName();
			Class<?> type = field.getType();
			if (!supportedClass.contains(type)) {
				continue;
			}
			if (name.startsWith("pref")) {
				name = name.substring("pref".length());
			} else {
				name = null;
			}
			if (name == null || name.equals("")) {
				continue;
			}
			if (!methodMap.containsKey(name)) {
				continue;
			}
			if (Modifier.isPrivate(mod)) {
				fields.put(name, field);
			}
		}
		return fields;
	}

	/* Preferences -> Local variable */
	public void loadInto(Object o) {
		Map<String, Field> fields = getPreferenceFields(o);
		for (String name : fields.keySet()) {
			Tuple<Method, Method> m = methodMap.get(name);
			Field f = fields.get(name);
			try {
				Object x = m.x.invoke(this);
				Class<?> type = f.getType();
				f.setAccessible(true);
				if (type.equals(int.class)) {
					f.setInt(o, (Integer) x);
				} else if (type.equals(boolean.class)) {
					f.setBoolean(o, (Boolean) x);
				} else {
					f.set(o, x);
				}
			} catch (Exception e) {
				// Ignore all exceptions
			}
		}
	}

	/* Different between Preferences and Local variable */
	public boolean isDifferentWith(Object o) {
		Map<String, Field> fields = getPreferenceFields(o);
		for (String name : fields.keySet()) {
			Tuple<Method, Method> m = methodMap.get(name);
			try {
				Object x = m.x.invoke(this);
				Field f = fields.get(name);
				f.setAccessible(true);
				Object y = f.get(o);
				Class<?> type = f.getType();
				if (type.isArray()) {
					if (!Arrays.equals((Object[]) x, (Object[]) y)) {
						return true;
					}
				} else if (!x.equals(y)) {
					return true;
				}
			} catch (Exception e) {
				// Ignore all exceptions
			}
		}
		return false;
	}

	/* Local variable -> Preferences */
	public void saveFrom(Object o) {
		Map<String, Field> fields = getPreferenceFields(o);
		for (String name : fields.keySet()) {
			Tuple<Method, Method> m = methodMap.get(name);
			Field f = fields.get(name);
			try {
				Class<?> type = f.getType();
				f.setAccessible(true);
				Object y = f.get(o);
				if (type.equals(int.class)) {
					m.y.invoke(this, (int) (Integer) y);
				} else if (type.equals(boolean.class)) {
					m.y.invoke(this, (boolean) (Boolean) y);
				} else {
					m.y.invoke(this, y);
				}
			} catch (Exception e) {
				// Ignore all exceptions
			}
		}
	}

	public void removeAllProperties() {
		for (String propertyKey : getPropertyKeys()) {
			pref.removeProperty(propertyKey);
		}
	}

}