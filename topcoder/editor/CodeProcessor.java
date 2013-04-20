package topcoder.editor;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.client.contestant.ProblemComponentModel;
import com.topcoder.shared.language.Language;
import com.topcoder.shared.problem.Renderer;

public abstract class CodeProcessor {
	public Map<String, String> getUserDefinedTags() {
		return new HashMap<String, String>();
	}

	public String preProcess(
			String source,
			ProblemComponentModel problem,
			Language language,
			Renderer render) {
		return source;
	}

	public String postProcess(
			String source,
			Language language) {
		return source;
	}

	public void configure() {
	}
}
