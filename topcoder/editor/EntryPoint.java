/** 
 * EntryPoint.java
 *
 * Description:		This is the entry point class for the code processor
 * @author			Tim "Pops" Roberts
 * @version			2.0
 */

package topcoder.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.topcoder.client.contestant.ProblemComponentModel;
import com.topcoder.shared.language.Language;
import com.topcoder.shared.problem.Renderer;

public class EntryPoint {

	// The user defined editor 
	private Editor editor = null;

	// Current language chosen
	private Language language;

	// Current problem model
	private ProblemComponentModel problem;

	// Current problem renderer
	private Renderer renderer;

	// Our preferences
	static private Preferences pref = null;

	public final static String PRODUCT_NAME = "TopCoder Editor https://github.com/lygstate/topcoder.editor";
	public final static String PRODUCT_VERSION = "0.10";

	// Tag line
	public final static String POWEREDBY = "// Powered by " + PRODUCT_NAME;

	/** Constructor that does nothing */
	public EntryPoint() {
	}

	// Pass through to editor
	public JPanel getEditorPanel() {
		loadPreferences();
		return editor.getEditorPanel();
	}

	// Get the code processors, processing codes with the
	// needed replacements.
	static public CodeProcessor[] getProcessors() {
		String[] processorNames = pref.getCodeProcessors();
		ArrayList<CodeProcessor> processors = new ArrayList<CodeProcessor>();
		for (int i = 0; i < processorNames.length; ++i) {
			CodeProcessor processor = CodeProcessor.create(processorNames[i]);
			if (processor != null) {
				processors.add(processor);
			}
		}
		return processors.toArray(new CodeProcessor[processors.size()]);
	}

	// Get the source, then post process it with the processors and return it.
	public String getSource() {
		loadPreferences();

		CodeProcessor[] processors = getProcessors();

		// Get the source
		String source = editor.getSource();
		String cpSource = (source == null ? "" : source);

		for (int i = 0; i < processors.length; ++i) {
			String tempSource = processors[i].postProcess(cpSource, language);
			if (tempSource != null)
				cpSource = tempSource;
		}

		StringBuffer temp = new StringBuffer(cpSource);

		// Write the powered by line (only if doesn't exceed max length for
		// source)
		// Note: this is just to see who and how many people are using this
		// plugin
		if (pref.isPoweredBy()
				/* Only when support for line comment, then append POWRBY */
				&& pref.isWriteCodeDescFile()
				&& !temp.toString().endsWith(POWEREDBY) && temp.length() != 0) {
			temp.append("\n");
			temp.append(POWEREDBY);
		}

		return temp.toString();
	}

	// Preprocessing the source and the pass it to the editor
	public void setSource(String source) {
		loadPreferences();

		CodeProcessor[] processors = getProcessors();

		// This probably needs to be broken up and cleaned up...
		String newSource = source;

		Map<String, String> userDefinedTags = new HashMap<String, String>();
		for (int i = 0; i < processors.length; ++i) {
			String tempSource = processors[i].preProcess(newSource, problem,
					language, renderer);
			if (tempSource != null)
				newSource = tempSource;
			Map<String, String> temp = processors[i].getUserDefinedTags();
			userDefinedTags.putAll(temp);
		}

		editor.setUserDefinedTags(userDefinedTags);
		editor.setSource(newSource);
	}

	/** Show the configuration dialog */
	public void configure() {
		loadPreferences();
		new ConfigurationDialog(this).setVisible(true);
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
	public void setProblemComponent(ProblemComponentModel problem,
			Language lang, Renderer renderer) {
		// Save the parameters.
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
		// TODO: Doesn't implemented yet
		return Boolean.FALSE;
	}

	private final void loadPreferences() {
		// Load the preferences
		pref = Preferences.getInstance();

		/* The editor ins */
		if (editor == null)
		{
			editor = new Editor();
		}
	}
}
