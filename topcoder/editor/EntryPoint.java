/** 
 * EntryPoint.java
 *
 * Description:		This is the entry point class for the code processor
 * @author			Tim "Pops" Roberts
 * @version			2.0
 */

package topcoder.editor;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import topcoder.editor.panels.Common;

import com.topcoder.client.contestant.ProblemComponentModel;
import com.topcoder.shared.language.Language;
import com.topcoder.shared.problem.Renderer;

public class EntryPoint {
	
	// The dynamic editor proxy
	private Editor editor=null;

	// The Code Processor, process code with the 
	// need replacement.
	private CodeProcessor processor;

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
	private final static String POWEREDBY = "// Powered by TopCoder Editor https://github.com/lygstate/topcoder.editor";
	
	/** Constructor that does nothing */
	public EntryPoint() {
	}
	
	/** SetName plugin API */
	public void setName(String name) {
		// Attach codeprocessor to the name and save it
		this.name = "topcoder.editor." + name;
		
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
		if(processor==null) return source;
		// Get the source
		String cpSource = (source==null ? "" : source);
		String tempSource = processor.postProcess(cpSource, language);
		if(tempSource!=null) cpSource = tempSource;

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
		processor = new ExampleProcessor();
		editor = new Editor();
		// Create the preferences
		pref = new Preferences(name);
		
		String editorEntryPoint   = pref.getPluginEntryPoint();
		if(editorEntryPoint==null || editorEntryPoint.equals("")) return;
		
		try {
			if(editor==null || !editor.getEditorName().equals(editorEntryPoint)) editor = new DynamicEditor(editorEntryPoint);
		} catch (Throwable t) {
			editor = null;
			Common.showMessage("Error", "Error initializing the CodeProcessor: " + t, null);
		}
	}

	// Preprocess the source and the pass it to the editor	
	public void setSource(String source) {
		loadPreferences();
		// This probably needs to be broken up and cleaned up...
		String newSource = source;

		Map<String, String> userDefinedTags = new HashMap<String, String>();
		if (processor != null) {
			String tempSource = processor.preProcess(newSource, problem, language, renderer);
			if(tempSource!=null) newSource = tempSource;
			Map<String, String> temp = processor.getUserDefinedTags();
			userDefinedTags.putAll(temp);
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
