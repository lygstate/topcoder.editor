package topcoder.editor;

import java.util.HashMap;
import java.util.Map;

import topcoder.editor.ui.Common;

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
		String info = String.format("There is nothing to configure [%s]!", this.getClass().getName());
		Common.showMessage("Code processor configure", info, null);
	}

	public static CodeProcessor create(String name) {
		if (name == null) {
			return null;
		}
		try {
			return (CodeProcessor) Class.forName(name).newInstance();
		}
		catch (Exception e) 
		{
			return null;
		}
	}
}
