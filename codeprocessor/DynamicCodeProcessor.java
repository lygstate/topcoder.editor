/*     */ package codeprocessor;
/*     */ 
/*     */ import com.topcoder.client.contestant.ProblemComponentModel;
/*     */ import com.topcoder.shared.language.Language;
/*     */ import com.topcoder.shared.problem.Renderer;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ class DynamicCodeProcessor
/*     */ {
/*  21 */   private Object processor = null;
/*  22 */   private HashMap methodCache = new HashMap();
/*     */   private static final String CONFIGURE = "configure";
/*     */   private static final String PREPROCESS = "preProcess";
/*     */   private static final String POSTPROCESS = "postProcess";
/*     */   private static final String GETUSERDEFINEDTAGS = "getUserDefinedTags";
/*     */   private String codeProcessorName;
/*     */ 
/*     */   public DynamicCodeProcessor(String paramString)
/*     */     throws InstantiationError
/*     */   {
/*  34 */     if (paramString == null) throw instantiationError("Null CodeProcessor", "Null CodeProcessor");
/*     */ 
/*  36 */     if (paramString.equals(EntryPoint.class.getName())) {
/*  37 */       throw instantiationError(paramString, "You cannot embed the code processor within itself");
/*     */     }
/*     */ 
/*     */     Class localClass;
/*     */     try
/*     */     {
/*  43 */       localClass = Class.forName(paramString);
/*     */     } catch (ClassNotFoundException localClassNotFoundException) {
/*  45 */       throw instantiationError(paramString, localClassNotFoundException.toString());
/*     */     }
/*     */ 
/*  49 */     this.methodCache.put("preProcess", getMethod(localClass, "preProcess", new Class[] { String.class, ProblemComponentModel.class, Language.class, Renderer.class }));
/*  50 */     this.methodCache.put("postProcess", getMethod(localClass, "postProcess", new Class[] { String.class, Language.class }));
/*  51 */     this.methodCache.put("getUserDefinedTags", getMethod(localClass, "getUserDefinedTags", new Class[0]));
/*  52 */     this.methodCache.put("configure", getMethod(localClass, "configure", new Class[0]));
/*     */     try
/*     */     {
/*  59 */       this.processor = localClass.newInstance();
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/*  61 */       throw instantiationError(paramString, localIllegalAccessException.toString());
/*     */     } catch (InstantiationException localInstantiationException) {
/*  63 */       throw instantiationError(paramString, localInstantiationException.toString());
/*     */     }
/*     */ 
/*  66 */     this.codeProcessorName = paramString;
/*     */   }
/*     */ 
/*     */   public String getCodeProcessorName()
/*     */   {
/*  71 */     return this.codeProcessorName;
/*     */   }
/*     */ 
/*     */   public boolean isConfigureDefined() {
/*  75 */     return this.methodCache.get("configure") != null;
/*     */   }
/*     */ 
/*     */   public boolean isPreProcessDefined() {
/*  79 */     return this.methodCache.get("preProcess") != null;
/*     */   }
/*     */ 
/*     */   public boolean isPostProcessDefined() {
/*  83 */     return this.methodCache.get("postProcess") != null;
/*     */   }
/*     */ 
/*     */   public boolean isUserDefinedTagsDefined() {
/*  87 */     return this.methodCache.get("getUserDefinedTags") != null;
/*     */   }
/*     */ 
/*     */   public String preProcess(String paramString, ProblemComponentModel paramProblemComponentModel, Language paramLanguage, Renderer paramRenderer)
/*     */   {
/*     */     try {
/*  93 */       if (this.methodCache.get("preProcess") == null) return paramString;
/*  94 */       return (String)invokeMethod("preProcess", new Object[] { paramString, paramProblemComponentModel, paramLanguage, paramRenderer });
/*     */     } catch (ClassCastException localClassCastException) {
/*  96 */       printBadRC("preProcess", String.class.toString());
/*  97 */     }return null;
/*     */   }
/*     */ 
/*     */   public String postProcess(String paramString, Language paramLanguage)
/*     */   {
/*     */     try
/*     */     {
/* 104 */       if (this.methodCache.get("postProcess") == null) return paramString;
/* 105 */       return (String)invokeMethod("postProcess", new Object[] { paramString, paramLanguage });
/*     */     } catch (ClassCastException localClassCastException) {
/* 107 */       printBadRC("postProcess", String.class.toString());
/* 108 */     }return null;
/*     */   }
/*     */ 
/*     */   public Map getUserDefinedTags()
/*     */   {
/*     */     try {
/* 114 */       if (this.methodCache.get("getUserDefinedTags") == null) return new HashMap();
/* 115 */       return (Map)invokeMethod("getUserDefinedTags", new Object[0]);
/*     */     } catch (ClassCastException localClassCastException) {
/* 117 */       printBadRC("getUserDefinedTags", Map.class.toString());
/* 118 */     }return null;
/*     */   }
/*     */ 
/*     */   public void configure()
/*     */   {
/* 123 */     if (this.methodCache.get("configure") == null) return;
/* 124 */     invokeMethod("configure", new Object[0]);
/*     */   }
/*     */ 
/*     */   private final Object invokeMethod(String paramString, Object[] paramArrayOfObject)
/*     */   {
/* 130 */     if ((this.methodCache == null) || (this.processor == null)) return null;
/*     */     try
/*     */     {
/* 133 */       Method localMethod = (Method)this.methodCache.get(paramString);
/* 134 */       return localMethod == null ? null : localMethod.invoke(this.processor, paramArrayOfObject);
/*     */     } catch (Exception localException) {
/* 136 */       System.err.println("Error invoking method " + paramString + "(" + (paramArrayOfObject == null ? "" : Arrays.asList(paramArrayOfObject).toString()) + ")");
/* 137 */       localException.fillInStackTrace().printStackTrace();
/* 138 */     }return null;
/*     */   }
/*     */ 
/*     */   private final Method getMethod(Class paramClass, String paramString, Class[] paramArrayOfClass)
/*     */   {
/*     */     try
/*     */     {
/* 145 */       return paramClass.getMethod(paramString, paramArrayOfClass);
/*     */     } catch (NoSuchMethodException localNoSuchMethodException) {
/*     */     }
/* 148 */     return null;
/*     */   }
/*     */ 
/*     */   private final void printBadClassPath(String paramString1, String paramString2)
/*     */   {
/* 154 */     StringBuffer localStringBuffer = new StringBuffer("The classpath ");
/* 155 */     localStringBuffer.append(paramString2);
/* 156 */     localStringBuffer.append(" for the plugin ");
/* 157 */     localStringBuffer.append(paramString1);
/* 158 */     localStringBuffer.append(" threw a MalFormedURLException and will be ignored.");
/* 159 */     System.err.println(localStringBuffer.toString());
/*     */   }
/*     */ 
/*     */   private final void printBadRC(String paramString1, String paramString2)
/*     */   {
/* 164 */     StringBuffer localStringBuffer = new StringBuffer("Method ");
/* 165 */     localStringBuffer.append(paramString1);
/* 166 */     localStringBuffer.append(" did not return an object of type ");
/* 167 */     localStringBuffer.append(paramString2);
/* 168 */     localStringBuffer.append(".  Returned object ignored.");
/* 169 */     System.err.println(localStringBuffer.toString());
/*     */   }
/*     */ 
/*     */   private final NoSuchMethodError noSuchMethod(String paramString)
/*     */   {
/* 175 */     StringBuffer localStringBuffer = new StringBuffer("Required Method: ");
/* 176 */     localStringBuffer.append(paramString);
/* 177 */     localStringBuffer.append(" is not defined by the editor plugin.");
/*     */ 
/* 180 */     NoSuchMethodError localNoSuchMethodError = new NoSuchMethodError(localStringBuffer.toString());
/* 181 */     localNoSuchMethodError.fillInStackTrace().printStackTrace();
/* 182 */     return localNoSuchMethodError;
/*     */   }
/*     */ 
/*     */   private final InstantiationError instantiationError(String paramString1, String paramString2)
/*     */   {
/* 188 */     StringBuffer localStringBuffer = new StringBuffer("Cannot instantiate ");
/* 189 */     localStringBuffer.append(paramString1);
/* 190 */     localStringBuffer.append(". ");
/* 191 */     localStringBuffer.append(paramString2);
/*     */ 
/* 194 */     InstantiationError localInstantiationError = new InstantiationError(localStringBuffer.toString());
/* 195 */     localInstantiationError.fillInStackTrace().printStackTrace();
/* 196 */     return localInstantiationError;
/*     */   }
/*     */ }

/* Location:           C:\Users\Game\Downloads\CodeProcessor.jar
 * Qualified Name:     codeprocessor.DynamicCodeProcessor
 * JD-Core Version:    0.6.2
 */