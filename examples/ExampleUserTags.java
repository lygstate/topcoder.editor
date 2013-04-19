package examples;

import com.topcoder.client.contestant.ProblemComponentModel;
import com.topcoder.shared.language.Language;
import com.topcoder.shared.problem.Renderer;
import java.util.HashMap;
import java.util.Map;

public class ExampleUserTags {
	HashMap<String, String> myTags = new HashMap<String, String>();

	public String preProcess(String paramString,
			ProblemComponentModel paramProblemComponentModel,
			Language paramLanguage, Renderer paramRenderer) {
		if (!paramString.equals(""))
			return paramString;

		this.myTags.put("$PHONE$", "If you want to hire me - call 867-5309");
		this.myTags.put("$NAME$", "Jenny");
		this.myTags.put("$LANGUAGE$", paramLanguage.getName());

		return "";
	}

	public Map<String, String> getUserDefinedTags() {
		return this.myTags;
	}
}