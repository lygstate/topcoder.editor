package topcoder.editor;

/** 
 * DynamicEditor.java
 *
 * Description:		This class is a specialized proxy for the real plugin class
 * @author			Tim "Pops" Roberts (troberts@bigfoot.com)
 * @version			1.0
 */

import java.lang.reflect.Method;
import java.util.*;

import javax.swing.JPanel;

import com.topcoder.client.contestant.ProblemComponentModel;
import com.topcoder.shared.language.Language;
import com.topcoder.shared.problem.Renderer;

class DynamicEditor {

	// Editor Object and it's methods
	private Object editor = null;	
	private HashMap methodCache = new HashMap();
	
	// Functions Constants
	private final static String CONFIGURE			= "configure";
	private final static String CLEAR				= "clear";
	private final static String GETEDITORPANEL		= "getEditorPanel";
	private final static String GETSOURCE			= "getSource";
	private final static String SETCOMPILERESULTS	= "setCompileResults";
	private final static String SETTEXTENABLED		= "setTextEnabled";
	private final static String SETSOURCE			= "setSource";
	private final static String SETPROBLEMCOMPONENT	= "setProblemComponent";
	private final static String SETUSERDEFINEDTAGS	= "setUserDefinedTags";

	private final static String SETNAME = "setName";
	private final static String ISCACHEABLE = "isCacheable";
	private final static String STARTUSING = "startUsing";
	private final static String STOPUSING = "stopUsing";
	private final static String DISPOSE = "dispose";
	private final static String INSTALL = "install";
	private final static String UNINSTALL = "uninstall";
	
	private String editorPluginName;

	public DynamicEditor(String editorPlugin) throws InstantiationError, NoSuchMethodError {
		
		if (editorPlugin==null) throw instantiationError("Null Plugin", "Null Plugin");
		
		if(editorPlugin.equals(EntryPoint.class.getName())) {
			throw instantiationError(editorPlugin, "You cannot embed the code processor within itself");
		}
		
							
		Class pluginClass;
		try {
			pluginClass = Class.forName(editorPlugin);
		} catch(ClassNotFoundException e) {
			throw instantiationError(editorPlugin, e.toString());
		}

		// Store all the methodCache into the cache
		methodCache.put(CONFIGURE, getMethod(pluginClass, CONFIGURE, null));
		methodCache.put(CLEAR, getMethod(pluginClass, CLEAR, null));
		methodCache.put(GETEDITORPANEL, getMethod(pluginClass, GETEDITORPANEL, null));
		methodCache.put(GETSOURCE, getMethod(pluginClass, GETSOURCE, null));
		methodCache.put(SETCOMPILERESULTS, getMethod(pluginClass, SETCOMPILERESULTS, new Class[] {Boolean.class, String.class}));
		methodCache.put(SETTEXTENABLED, getMethod(pluginClass, SETTEXTENABLED, new Class[] {Boolean.class}));
		methodCache.put(SETSOURCE, getMethod(pluginClass, SETSOURCE, new Class[] {String.class}));
		methodCache.put(SETPROBLEMCOMPONENT, getMethod(pluginClass, SETPROBLEMCOMPONENT, new Class[] {ProblemComponentModel.class, Language.class, Renderer.class}));
		methodCache.put(SETUSERDEFINEDTAGS, getMethod(pluginClass, SETUSERDEFINEDTAGS, new Class[] {Map.class}));
		methodCache.put(SETNAME, getMethod(pluginClass, SETNAME, new Class[]{String.class}));
		methodCache.put(ISCACHEABLE, getMethod(pluginClass, ISCACHEABLE, null));
		methodCache.put(STARTUSING, getMethod(pluginClass, STARTUSING, null));
		methodCache.put(STOPUSING, getMethod(pluginClass, STOPUSING, null));
		methodCache.put(DISPOSE, getMethod(pluginClass, DISPOSE, null));
		methodCache.put(INSTALL, getMethod(pluginClass, INSTALL, null));
		methodCache.put(UNINSTALL, getMethod(pluginClass, UNINSTALL, null));

		// Verify that the REQUIRED methods were present
		if (methodCache.get(SETSOURCE)==null)		{throw noSuchMethod(SETSOURCE);}
		if (methodCache.get(GETSOURCE)==null)		{throw noSuchMethod(GETSOURCE);}
		if (methodCache.get(GETEDITORPANEL)==null)  {throw noSuchMethod(GETEDITORPANEL);}

	
		// Create the editor object
		try {
			editor = pluginClass.newInstance();
		} catch (IllegalAccessException e) {
			throw instantiationError(editorPlugin, e.toString());
		} catch (InstantiationException e) {
			throw instantiationError(editorPlugin, e.toString());
		}
		
		editorPluginName = editorPlugin;
	}
	
	public String getEditorName() {
		return editorPluginName;
	}
	
	/* ---------------------------- INTERFACE methods -------------------------*/
	public void setTextEnabled(Boolean enable) { invokeMethod(SETTEXTENABLED, new Object[] {enable}); }
	public void setSource(String source) { invokeMethod(SETSOURCE, new Object[] {source}); }
	public void setProblemComponent(ProblemComponentModel component, Language lang, Renderer renderer) { invokeMethod(SETPROBLEMCOMPONENT, new Object[] {component, lang, renderer}); }
	public void setUserDefinedTags(Map userDefinedTags) { invokeMethod(SETUSERDEFINEDTAGS, new Object[] {userDefinedTags}); }
	public void setName(String name) {
		invokeMethod(SETNAME, new Object[]{name});
	}

	public void startUsing() {
		invokeMethod(STARTUSING, null);
	}

	public void stopUsing() {
		invokeMethod(STOPUSING, null);
	}

	public void dispose() {
		invokeMethod(DISPOSE, null);
	}

	public void install() {
		invokeMethod(INSTALL, null);
	}

	public void uninstall() {
		invokeMethod(UNINSTALL, null);
	}

	public boolean isCacheable() {
		// Return TRUE if there is NO configure method
		if (methodCache.get(ISCACHEABLE) == null) return true;

		// Call iscacheable and return the value
		try {
			return ((Boolean)invokeMethod(ISCACHEABLE, null)).booleanValue();
		} catch (ClassCastException e) {
			printBadRC(ISCACHEABLE, Boolean.class.toString());
			return true;
		}
	}

	public JPanel getEditorPanel() {
		try {
			return (JPanel)invokeMethod(GETEDITORPANEL, null);
		} catch (ClassCastException e) {
			printBadRC(GETEDITORPANEL, JPanel.class.toString());
			return null;
		}
	}

	public String getSource() {
		try {
			return (String)invokeMethod(GETSOURCE, null);
		} catch (ClassCastException e) {
			printBadRC(GETSOURCE, String.class.toString());
			return null;
		}
	}
	
	public void clear() { invokeMethod(CLEAR, null); }

	public boolean configure() { 
		// Return FALSE if there is NO configure method
		if (methodCache.get(CONFIGURE)==null) return false;
		
		// Call configure and return true
		invokeMethod(CONFIGURE, null); 
		return true;
	}
	
	public boolean setCompileResults(boolean success, String message) { 
		// Return FALSE if there is NO configure method
		if (methodCache.get(SETCOMPILERESULTS)==null) return false;
		
		// Call configure and return true
		try {
			return ((Boolean)invokeMethod(SETCOMPILERESULTS, new Object[] { new Boolean(success), message })).booleanValue(); 
		} catch (ClassCastException e) {
			printBadRC(SETCOMPILERESULTS, Boolean.class.toString());
			return false;
		}
	}
	
	/* ---------------------------- HELPER methods ----------------------------*/
	// Invokes a given method with the passed parameters
	private final Object invokeMethod(String methodName, Object[] parms) {
		// Return null if no object is current
		if (methodCache==null || editor==null) return null;
			
		try {
			// Invoke the proper method with the passed parameters
			Method mthd = (Method)methodCache.get(methodName);
			return mthd==null ? null : mthd.invoke(editor, parms);
		} catch (Exception e) {
			System.err.println("Error invoking method " + methodName + "(" + (parms==null ? "" : Arrays.asList(parms).toString()) + ")");
			e.fillInStackTrace().printStackTrace();
			return null;
		}
	}
	
	// Adds the specified Method (or null if not found) to the method cache
	private final Method getMethod(Class pluginClass, String methodName, Class[] parms) {
		try {
			Method method = pluginClass.getMethod(methodName, parms);
			return method;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	// Print the bad classpath message
	private final void printBadClassPath(String pluginName, String classPath) {
		StringBuffer str = new StringBuffer("The classpath ");
		str.append(classPath);
		str.append(" for the plugin ");
		str.append(pluginName);
		str.append(" threw a MalFormedURLException and will be ignored.");
		System.err.println(str.toString());
	}
	
	// Print the bad return code message
	private final void printBadRC(String methodName, String expected) {
		StringBuffer str = new StringBuffer("Method ");
		str.append(methodName);
		str.append(" did not return an object of type ");
		str.append(expected);
		str.append(".  Returned object ignored.");
		System.err.println(str.toString());
	}
	
	// Creates the NoSuchMethodError	
	private final NoSuchMethodError noSuchMethod(String methodName) {
		// Format the error message
		StringBuffer str = new StringBuffer("Required Method: ");
		str.append(methodName);
		str.append(" is not defined by the editor plugin.");
		
		// Write to console and throw error
		NoSuchMethodError error = new NoSuchMethodError(str.toString());
		error.fillInStackTrace().printStackTrace();
		return error;
	}
		 
	// Creates the InstantiationEror
	private final InstantiationError instantiationError(String className, String reason) {
		// Format the error message
		StringBuffer str = new StringBuffer("Cannot instantiate ");
		str.append(className);
		str.append(". ");
		str.append(reason);
		
		// Write to console and throw error
		InstantiationError error = new InstantiationError(str.toString());
		error.fillInStackTrace().printStackTrace();
		return error;
	}

}
