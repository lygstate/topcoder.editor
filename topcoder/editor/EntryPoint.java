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

import topcoder.editor.ui.Common;

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

	// Our preferences
	private Preferences pref=null;
	
	// Tag line
	private final static String POWEREDBY = "// Powered by " + Common.PRODUCT_NAME;

	/** Constructor that does nothing */
	public EntryPoint() {
	}

	// Pass through to editor
	public JPanel getEditorPanel() { 
		loadPreferences();
		return editor.getEditorPanel(); 
	}	

	// Get the source, the post process it with the processor and return that
	public String getSource() {
		loadPreferences();

		String source = editor.getSource();
		if(processor==null) return source;
		// Get the source
		String cpSource = (source==null ? "" : source);
		String tempSource = processor.postProcess(cpSource, language);
		if(tempSource!=null) cpSource = tempSource;

		StringBuffer temp = new StringBuffer(cpSource);

		// Write the powered by line (only if doesn't exceed max length for source)
		// Note: this is just to see who and how many people are using this plugin
		if (pref.isPoweredBy()
				&& pref.isLineComments() /* Only when support for line comment, then append POWRBY */
				&& !temp.toString().endsWith(POWEREDBY)
				&& temp.length() != 0) {
			temp.append("\n");
			temp.append(POWEREDBY);
		}

		return temp.toString();
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

		editor.setUserDefinedTags(userDefinedTags);
		editor.setSource(newSource);
	}

	/** Show the configuration dialog */
	public void configure() { 
		loadPreferences();
		new ConfigurationDialog(pref).setVisible(true); 
	}

	// TODO: Not implemented
	public void clear() { 
		loadPreferences();
	}

	// TODO: Not implemented
	public void setTextEnabled(Boolean enable) { 
		loadPreferences();
	}

	// TODO: Not implemented
	public void setProblemComponent(ProblemComponentModel problem, Language lang, Renderer renderer) {
		// Save the parms
		this.problem = problem;
		this.language = lang;
		this.renderer = renderer;

		// Pass it to the editor
		editor.setProblemComponent(problem, lang, renderer);
	}

	// TODO: Not implemented
	public void setName(String name) {
		loadPreferences();
	}

	// TODO: Not implemented
	public void install() { 
		loadPreferences();
	}

	// TODO: Not implemented
	public void uninstall() { 
		loadPreferences();
	}

	// TODO: Not implemented
	public void startUsing() { 
		loadPreferences();
	}
	
	// TODO: Not implemented
	public void stopUsing() { 
		loadPreferences();
	}
	
	public void dispose() {
		// We doesn't dispose at all. Because isCacheable.
	}

	public boolean isCacheable() {
		return true;
	}

	// Pass through to editor
	public Boolean setCompileResults(Boolean success, String message) { 
		loadPreferences();
		//TODO: Doesn't implemented yet
		return Boolean.FALSE; 
	}

	private final void loadPreferences() {
		// If already loaded - ignore
		if(pref!=null) return;

		processor = new ExampleProcessor();

		/* The editor */
		editor = new Editor();
	
		// Create the preferences
		pref = new Preferences();
	}
}
