/** 
 * EntryPoint.java
 *
 * Description:		This is the entry point class for the code processor
 * @author			Tim "Pops" Roberts
 * @version			2.0
 */

package codeprocessor;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.topcoder.client.contestant.ProblemComponentModel;
import com.topcoder.shared.language.Language;
import com.topcoder.shared.problem.Renderer;

public class EntryPoint {
	
	// The dynamic editor proxy
	private DynamicEditor editor=null;
	
	// The dynamic code processor proxy
	private DynamicCodeProcessor[] codeProcessor=null;

	// Current language chosen
	private Language language;
	
	// Current problem model
	private ProblemComponentModel problem;
	
	// Current problem renderer
	private Renderer renderer;

	// The unique name given (by the user) to this code processor
	private String name = "";
	
	// Our preferences
	private Preferences pref=null;
	
	// Tag line
	private final static String POWEREDBY = "// Powered by CodeProcessor";
	
	/** Constructor that does nothing */
	public EntryPoint() {
	}
	
	/** SetName plugin API */
	public void setName(String name) {
		// Attach codeprocessor to the name and save it
		this.name = "codeprocessor." + name;
		
		// Load the preferences (based on the above name)
		loadPreferences();
		
		// Pass the name to the given editor
		if(editor!=null) editor.setName(name);
	}
	
	/** Show the configuration dialog */
	public void configure() { 
		loadPreferences();
		new ConfigurationDialog(pref).setVisible(true); 
	}
	
	/** Configure the editor */
	public boolean configureEditor() { 
		loadPreferences();
		if(editor!=null) return editor.configure();
		return false;
		 
	}
	
	/** Start using the editor */
	public void startUsing() { 
		loadPreferences();
		if(editor!=null) editor.startUsing();
	}
	
	/** Stop using the editor */
	public void stopUsing() { 
		loadPreferences();
		if(editor!=null) editor.stopUsing();
	}
	
	/** Dispose of the editor */
	public void dispose() { 
		loadPreferences();
		if(editor!=null) editor.dispose();
	}

	/** Install the editor */	
	public void install() { 
		loadPreferences();
		if(editor!=null) editor.install();
	}
	
	public void uninstall() { 
		loadPreferences();
		if(editor!=null) editor.uninstall();
	}
	
	// Pass through to editor
	public void clear() { 
		loadPreferences();
		if(editor!=null) editor.clear(); 
	}
	
	// Pass through to editor
	public void setTextEnabled(Boolean enable) { 
		loadPreferences();
		if(editor!=null) editor.setTextEnabled(enable); 
	}
	
	// Pass through to editor
	public Boolean setCompileResults(Boolean success, String message) { 
		loadPreferences();
		if(editor!=null) return new Boolean(editor.setCompileResults(success.booleanValue(), message));
		return Boolean.FALSE; 
	}
	
	
	// Pass through to editor
	public JPanel getEditorPanel() { 
		loadPreferences();
		if(editor!=null) return editor.getEditorPanel();
		return new JPanel(); 
	}	

	// Get the source, the post process it with the processor and return that
	public String getSource() { 
		loadPreferences();
		if(editor==null) return "";

		String source = editor.getSource();
		if(codeProcessor==null) return source;
		
		// Get the source
		String cpSource = (source==null ? "" : source);
		for(int x=0;x<codeProcessor.length;x++) {
			String tempSource = codeProcessor[x].postProcess(cpSource, language);
			if(tempSource!=null) cpSource = tempSource;
		}
		
		StringBuffer temp = new StringBuffer(cpSource);

		// Write the powered by line (only if doesn't exceed max length for source)
		// Note: this is just to see who and how many people are using this plugin
		if(pref.isPoweredBy()  && !temp.toString().endsWith(POWEREDBY) && temp.length()!=0) {
			temp.append("\n");
			temp.append(POWEREDBY);
		}
		
		return temp.toString();
	}
			
	private final void loadPreferences() {
		// If already loaded - ignore
		if(pref!=null) return;
		
		// Create the preferences
		pref = new Preferences(name);
		
		String editorEntryPoint   = pref.getPluginEntryPoint();
		String[] codeProcessorClass = pref.getCodeProcessors();
		
		if(editorEntryPoint==null || editorEntryPoint.equals("")) return;
		if(codeProcessorClass==null || codeProcessorClass.length==0) return;
		
		try {
			if(editor==null || !editor.getEditorName().equals(editorEntryPoint)) editor = new DynamicEditor(editorEntryPoint);
			if(codeProcessor==null || noMatch(codeProcessorClass, codeProcessor)) {
				codeProcessor = new DynamicCodeProcessor[codeProcessorClass.length];
				for(int x=0;x<codeProcessorClass.length;x++) {
					codeProcessor[x] = new DynamicCodeProcessor(codeProcessorClass[x]);
				}
			} 
		} catch (Throwable t) {
			editor = null;
			codeProcessor = null;
			Common.showMessage("Error", "Error initializing the CodeProcessor: " + t, null);
		}

	}	
	
	private boolean noMatch(String[] cd, DynamicCodeProcessor[] dcp) {
		if(cd.length!=dcp.length) return true;
		for(int x=0;x<cd.length;x++) {
			if(!dcp[x].getCodeProcessorName().equals(cd[x])) return true;
		}
		return false;
	}
	
	// Preprocess the source and the pass it to the editro	
	public void setSource(String source) {
		loadPreferences();
		// This probably needs to be broken up and cleaned up...
		String newSource = source;
		Map userDefinedTags = new HashMap();
		if(codeProcessor!=null) {
			for(int x=0;x<codeProcessor.length;x++) {
				String tempSource = codeProcessor[x].preProcess(newSource, problem, language, renderer);
				if(tempSource!=null) newSource = tempSource;

				Map temp = codeProcessor[x].getUserDefinedTags();
				if(temp!=null) userDefinedTags.putAll(temp);
			}
			
		}
		
		// Pass it to the editor
		if(editor==null) return;
		
		editor.setUserDefinedTags(userDefinedTags);
		editor.setSource(newSource);

	}

	public void setProblemComponent(ProblemComponentModel problem, Language lang, Renderer renderer) {
		// Save the parms
		this.problem = problem;
		this.language = lang;
		this.renderer = renderer;

		// Pass it to the editor
		if(editor!=null) editor.setProblemComponent(problem, lang, renderer);
	}
	
}


