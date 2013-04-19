package codeprocessor;

import com.topcoder.client.contestant.ProblemComponentModel;
import com.topcoder.shared.language.Language;
import com.topcoder.shared.problem.Renderer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class DynamicCodeProcessor {
	private Object processor = null;
	private HashMap<String, Method> methodCache = new HashMap<String, Method>();
	private static final String CONFIGURE = "configure";
	private static final String PREPROCESS = "preProcess";
	private static final String POSTPROCESS = "postProcess";
	private static final String GETUSERDEFINEDTAGS = "getUserDefinedTags";
	private String codeProcessorName;

	public DynamicCodeProcessor(String paramString) throws InstantiationError {
		if (paramString == null)
			throw instantiationError("Null CodeProcessor", "Null CodeProcessor");

		if (paramString.equals(EntryPoint.class.getName())) {
			throw instantiationError(paramString,
					"You cannot embed the code processor within itself");
		}

		Class<?> localClass;
		try {
			localClass = Class.forName(paramString);
		} catch (ClassNotFoundException localClassNotFoundException) {
			throw instantiationError(paramString,
					localClassNotFoundException.toString());
		}

		this.methodCache.put(
				"preProcess",
				getMethod(localClass, "preProcess", new Class[] { String.class,
						ProblemComponentModel.class, Language.class,
						Renderer.class }));
		this.methodCache.put(
				"postProcess",
				getMethod(localClass, "postProcess", new Class[] {
						String.class, Language.class }));
		this.methodCache.put("getUserDefinedTags",
				getMethod(localClass, "getUserDefinedTags", new Class[0]));
		this.methodCache.put("configure",
				getMethod(localClass, "configure", new Class[0]));
		try {
			this.processor = localClass.newInstance();
		} catch (IllegalAccessException localIllegalAccessException) {
			throw instantiationError(paramString,
					localIllegalAccessException.toString());
		} catch (InstantiationException localInstantiationException) {
			throw instantiationError(paramString,
					localInstantiationException.toString());
		}

		this.codeProcessorName = paramString;
	}

	public String getCodeProcessorName() {
		return this.codeProcessorName;
	}

	public boolean isConfigureDefined() {
		return this.methodCache.get("configure") != null;
	}

	public boolean isPreProcessDefined() {
		return this.methodCache.get("preProcess") != null;
	}

	public boolean isPostProcessDefined() {
		return this.methodCache.get("postProcess") != null;
	}

	public boolean isUserDefinedTagsDefined() {
		return this.methodCache.get("getUserDefinedTags") != null;
	}

	public String preProcess(String paramString,
			ProblemComponentModel paramProblemComponentModel,
			Language paramLanguage, Renderer paramRenderer) {
		try {
			if (this.methodCache.get("preProcess") == null)
				return paramString;
			return (String) invokeMethod("preProcess", new Object[] {
					paramString, paramProblemComponentModel, paramLanguage,
					paramRenderer });
		} catch (ClassCastException localClassCastException) {
			printBadRC("preProcess", String.class.toString());
		}
		return null;
	}

	public String postProcess(String paramString, Language paramLanguage) {
		try {
			if (this.methodCache.get("postProcess") == null)
				return paramString;
			return (String) invokeMethod("postProcess", new Object[] {
					paramString, paramLanguage });
		} catch (ClassCastException localClassCastException) {
			printBadRC("postProcess", String.class.toString());
		}
		return null;
	}

	public Map getUserDefinedTags() {
		try {
			if (this.methodCache.get("getUserDefinedTags") == null)
				return new HashMap();
			return (Map) invokeMethod("getUserDefinedTags", new Object[0]);
		} catch (ClassCastException localClassCastException) {
			printBadRC("getUserDefinedTags", Map.class.toString());
		}
		return null;
	}

	public void configure() {
		if (this.methodCache.get("configure") == null)
			return;
		invokeMethod("configure", new Object[0]);
	}

	private final Object invokeMethod(String paramString,
			Object[] paramArrayOfObject) {
		if ((this.methodCache == null) || (this.processor == null))
			return null;
		try {
			Method localMethod = this.methodCache.get(paramString);
			return localMethod == null ? null : localMethod.invoke(
					this.processor, paramArrayOfObject);
		} catch (Exception localException) {
			System.err.println("Error invoking method "
					+ paramString
					+ "("
					+ (paramArrayOfObject == null ? "" : Arrays.asList(
							paramArrayOfObject).toString()) + ")");
			localException.fillInStackTrace().printStackTrace();
		}
		return null;
	}

	private final Method getMethod(Class paramClass, String paramString,
			Class[] paramArrayOfClass) {
		try {
			return paramClass.getMethod(paramString, paramArrayOfClass);
		} catch (NoSuchMethodException localNoSuchMethodException) {
		}
		return null;
	}

	private final void printBadClassPath(String paramString1,
			String paramString2) {
		StringBuffer localStringBuffer = new StringBuffer("The classpath ");
		localStringBuffer.append(paramString2);
		localStringBuffer.append(" for the plugin ");
		localStringBuffer.append(paramString1);
		localStringBuffer
				.append(" threw a MalFormedURLException and will be ignored.");
		System.err.println(localStringBuffer.toString());
	}

	private final void printBadRC(String paramString1, String paramString2) {
		StringBuffer localStringBuffer = new StringBuffer("Method ");
		localStringBuffer.append(paramString1);
		localStringBuffer.append(" did not return an object of type ");
		localStringBuffer.append(paramString2);
		localStringBuffer.append(".  Returned object ignored.");
		System.err.println(localStringBuffer.toString());
	}

	private final NoSuchMethodError noSuchMethod(final String paramString) {
		StringBuffer localStringBuffer = new StringBuffer("Required Method: ");
		localStringBuffer.append(paramString);
		localStringBuffer.append(" is not defined by the editor plugin.");

		NoSuchMethodError localNoSuchMethodError = new NoSuchMethodError(
				localStringBuffer.toString());
		localNoSuchMethodError.fillInStackTrace().printStackTrace();
		return localNoSuchMethodError;
	}

	private final InstantiationError instantiationError(String paramString1,
			String paramString2) {
		StringBuffer localStringBuffer = new StringBuffer("Cannot instantiate ");
		localStringBuffer.append(paramString1);
		localStringBuffer.append(". ");
		localStringBuffer.append(paramString2);

		InstantiationError localInstantiationError = new InstantiationError(
				localStringBuffer.toString());
		localInstantiationError.fillInStackTrace().printStackTrace();
		return localInstantiationError;
	}
}
