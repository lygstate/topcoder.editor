package topcoder.editor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.client.contestant.ProblemComponentModel;
import com.topcoder.shared.language.Language;
import com.topcoder.shared.problem.InvalidTypeException;
import com.topcoder.shared.problem.Renderer;
import com.topcoder.shared.problem.DataType;
import com.topcoder.shared.problem.TestCase;

/**
 * @author kyky
 * 
 *         This test generator class is for Java, C#, and C++. It is based on
 *         PopsProcessor which is a Java-only test generator developed by Tim
 *         Roberts.
 * 
 *         This code works only with code templates specifically written for
 *         compatibility with the plugin. The templates for each supported
 *         language are in the templates directory of this source distribution.
 *         The Example Processor, generating test case for Examples in TopCoder
 *         Description
 */
public class ExampleProcessor extends CodeProcessor {

	/* Storage the current processed language id. */
	static int langID;
	static int LANG_UNSUPPORTED = 0;
	static int LANG_CPP = 1;
	static int LANG_CSHARP = 2;
	static int LANG_JAVA = 3;

	public static int parseLang(Language lang)
	{
		//{ "Java", "C#", "C++" };
		String langName = lang.getName();
		if (langName.equals("C++"))
		{
			return LANG_CPP;
		}
		else if (langName.equals("Java"))
		{
			return LANG_JAVA;
		}
		else if (langName.equals("C#"))
		{
			return LANG_CSHARP;
		}
		else
		{
			return LANG_UNSUPPORTED;
		}
	}

	/**
	 * Obtains a list of user-defined tags.
	 * 
	 * @return a list of user-defined tags.
	 */
	public Map<String, String> getUserDefinedTags() {
		return tags;
	}

	/**
	 * 
	 * @param src
	 * @param problem
	 * @param lang
	 * @param rend
	 * @return
	 */
	public String preProcess(String src, ProblemComponentModel problem,
			Language lang, Renderer rend) {
		pref = Preferences.getInstance();

		tags.put(WRITERCODE, problem.getDefaultSolution());

		langID = parseLang(lang);
		// If the source not equal to the default solution, return the existing
		// source
		if (src != null && src.length() > 0
				&& !src.equals(problem.getDefaultSolution())
				&& sameLanguage(langID, src)) {
			return src;
		}

		if (!problem.hasSignature()) {
			tags.put(MAINBODY, "// Problem has no signature");
			return src;
		}
		if (langID == LANG_UNSUPPORTED) {
			return src;
		}

		TestCase[] tests = problem.getTestCases();
		DataType[] pt = problem.getParamTypes();
		String[] pn = problem.getParamNames();
		StringBuffer buf = new StringBuffer();

		if (tests != null) {
			for (int i = 0; i != tests.length; i++) {
				if (langID == LANG_CPP) {
					buf.append(indent(2) + "{\n");
				}
				String[] vals = tests[i].getInput();
				for (int j = 0; j != pt.length; j++) {
					genValueDef(buf, pt[j], vals[j], lang, pn[j]);
				}
				genValueDef(buf,
						problem.getReturnType(),
						tests[i].getOutput(),
						lang,
						RETVAL);
				if (langID == LANG_CPP) {
					buf.append(indent(3)
							+ problem.getClassName() + " theObject;\n"
							+ indent(3) + "eq(" + (i)
							+ ", theObject." + problem.getMethodName() + "(");
				} else {
					buf.append(indent(3) + "eq(" + (i) + ",(new "
							+ problem.getClassName() + "())."
							+ problem.getMethodName() + "(");
				}
				for (int j = 0; j != pt.length; j++) {
					if (j != 0) {
						buf.append(", ");
					}
					genValueRef(buf, pt[j], vals[j], lang, pn[j]);
				}
				buf.append("),");
				genValueRef(buf, problem.getReturnType(), tests[i].getOutput(),
						lang, RETVAL);
				buf.append(");");
				if (langID == LANG_CPP) {
					buf.append("\n" + indent(2) + "}");
				}
				if (i != tests.length - 1) {
					buf.append("\n");
				}
			}
		}
		tags.put(MAINBODY, buf.toString());
		return "";
	}

	/**
	 * Generates an in-place value aggregate.
	 * 
	 * @param buf
	 *            The buffer to which to append the resulting declaration
	 * @param dt
	 *            The data type of the parameter
	 * @param val
	 *            the value of the parameter
	 * @param lang
	 *            the target language
	 */
	private static void genValue(
			StringBuffer buf,
			DataType dt,
			String val) {
		if (dt.getBaseName().toLowerCase().indexOf("long") != -1) {
			if (langID == LANG_CPP)
			{
				val = val.replaceAll("\\d+", "$0LL");
			}
			else
			{
				val = val.replaceAll("\\d+", "$0L");
			}
		}
		if (dt.getDimension() != 0) {
			val = val.replaceAll("\n", "\n" + indent( 4));
		}
		buf.append(val);
	}

	/**
	 * Generates a value reference or an in-place value aggregate for languages
	 * that support such aggregates.
	 * 
	 * @param buf
	 *            The buffer to which to append the resulting declaration
	 * @param dt
	 *            The data type of the parameter
	 * @param val
	 *            the value of the parameter
	 * @param lang
	 *            the target language
	 * @name name of the variable to reference, if any
	 */
	private static void genValueRef(
			StringBuffer buf,
			DataType dt,
			String val,
			Language lang,
			String name) {
		if (inPlace(dt)) {
			if (dt.getDimension() != 0) {
				buf.append("new ");
				buf.append(dt.getDescriptor(lang));
				buf.append(" ");
			}
			genValue(buf, dt, val);
		} else {
			if (!empty(val)) {
				buf.append(name);
			} else {
				buf.append(dt.getDescriptor(lang) + "()");
			}
		}
	}

	/**
	 * 
	 * @param buf
	 * @param dt
	 * @param val
	 * @param lang
	 * @param name
	 */
	private static void genValueDef(
			StringBuffer buf,
			DataType dt,
			String val,
			Language lang,
			String name) {
		if (!inPlace(dt) && !empty(val)) {
			try
			{
				DataType sub = dt.reduceDimension();
				buf.append(indent(3) +  sub.getDescriptor(lang)
					+ " " + name + "ARRAY[] = ");
			}
			catch(InvalidTypeException e)
			{
				//pass, that's impossible.
			}
			genValue(buf, dt, val);
			buf.append(";\n");
			buf.append(indent(3) + dt.getDescriptor(lang) + " "
					+ name + "( " + name + "ARRAY, " + name + "ARRAY+ARRSIZE("
					+ name + "ARRAY) );\n");
		}
	}

	/**
	 * 
	 * @param dt
	 * @param lang
	 * @return
	 */
	private static boolean inPlace(DataType dt) {
		return (dt.getDimension() == 0) || langID != LANG_CPP;
	}

	/**
	 * 
	 * @param lang
	 * @return
	 */
	private static String indent(int count) {
		
		if (langID == LANG_CPP) {
			if (count > 0) {
				count -= 1;
			}
		}
		String single = "";
		if (pref.getIndentType() == "Space") {
			char[] spaces = (new char[pref.getTabSize()]);
			Arrays.fill(spaces, ' ');
			single = spaces.toString();
		} else {
			single = "\t";
		}
		String ret = "";
		for (int i = 0; i < count; ++i) {
			ret = ret + single;
		}
		return ret;
	}

	private static boolean empty(String s) {
		return s.matches("[{][\t \n\r]*[}]");
	}

	/**
	 * 
	 * @param lang
	 * @param src
	 * @return
	 */
	private static boolean sameLanguage(int langID, String src) {
		if (langID == LANG_CPP) {
			return src.indexOf("#include") != -1;
		} else if (langID == LANG_JAVA) {
			return src.indexOf("import ") != -1;
		} else if (langID == LANG_CSHARP) {
			return src.indexOf("using ") != -1 && src.indexOf("#include") == -1;
		} else {
			return true;
		}
	}

	private static Preferences pref = Preferences.getInstance();

	/** This map stores user-defined tags */
	private final Map<String, String> tags = new HashMap<String, String>();
	/** Name of the Writer Code tag in the code template. */
	private static final String WRITERCODE = "$WRITERCODE$";
	/** Name of the Main Body tag in the code template. */
	private static final String MAINBODY = "$MAINBODY$";
	private static final String RETVAL = "retrunValue";
}
