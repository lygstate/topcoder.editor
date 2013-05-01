package tests.plugins;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.client.contestant.ProblemComponentModel;
import com.topcoder.shared.language.Language;
import com.topcoder.shared.problem.Renderer;

import topcoder.editor.CodeProcessor;
import topcoder.editor.ui.Common;

public class DemoProcessor extends CodeProcessor {
	private final Map<String, String> tags = new HashMap<String, String>();

	public String preProcess(
			String source,
			ProblemComponentModel problem,
			Language language,
			Renderer render) {
		tags.put(DEMOTAG, "DemoProcessor");
		return source;
	}

	public void configure() {
		Common.showMessage("Processor configure", "There is nothing to configure!", null);
	}

	private static final String DEMOTAG = "$DEMOTAG$"; 
}