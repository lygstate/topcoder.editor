/*     */ package fileedit;
/*     */ 
/*     */ import com.topcoder.client.contestApplet.common.LocalPreferences;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Observer;
/*     */ 
/*     */ public class Preferences
/*     */ {
/*  16 */   private static LocalPreferences pref = LocalPreferences.getInstance();
/*     */   private Observer notify;
/*     */   public static final String JAVATEMPLATE = "fileeditor.config.javatemplate";
/*     */   public static final String CPPTEMPLATE = "fileeditor.config.cpptemplate";
/*     */   public static final String CSHARPTEMPLATE = "fileeditor.config.csharptemplate";
/*     */   public static final String JAVAEXTENSION = "fileeditor.config.javaextension";
/*     */   public static final String CPPEXTENSION = "fileeditor.config.cppextension";
/*     */   public static final String CSHARPEXTENSION = "fileeditor.config.csharpextension";
/*     */   public static final String DIRNAMEKEY = "fileeditor.config.dirName";
/*     */   public static final String FILENAMEKEY = "fileeditor.config.fileName";
/*     */   public static final String OVERRIDEFILENAME = "fileeditor.config.overrideFileName";
/*     */   public static final String PROVIDEBREAKS = "fileeditor.config.provideBreaks";
/*     */   public static final String BREAKAT = "fileeditor.config.breakAt";
/*     */   public static final String LINECOMMENTS = "fileeditor.config.lineComments";
/*     */   public static final String BEGINCUT = "fileeditor.config.beginCut";
/*     */   public static final String ENDCUT = "fileeditor.config.endCut";
/*     */   public static final String PROBDESCFILEWRITE = "fileeditor.config.probdescfilewrite";
/*     */   public static final String PROBDESCFILEEXTENSION = "fileeditor.config.probdescfileextnsion";
/*     */   public static final String SIGNATUREFILENAME = "fileeditor.config.signaturefilename";
/*     */   public static final String POWEREDBY = "fileeditor.config.poweredby";
/*     */   public static final String HTMLDESC = "fileeditor.config.htmldesc";
/*     */   public static final String BACKUP = "fileeditor.config.backup";
/*     */ 
/*     */   public Preferences()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Preferences(Observer notify)
/*     */   {
/*  46 */     this.notify = notify;
/*  47 */     pref.addSaveObserver(notify);
/*     */   }
/*     */ 
/*     */   public void finalize()
/*     */   {
/*  52 */     if (this.notify != null) pref.removeSaveObserver(this.notify); 
/*     */   }
/*     */ 
/*     */   public String getJAVATemplate()
/*     */   {
/*  56 */     return getStringProperty("fileeditor.config.javatemplate", "$BEGINCUT$\n$PROBLEMDESC$\n$ENDCUT$\nimport java.util.*;\npublic class $CLASSNAME$ {\n\tpublic $RC$ $METHODNAME$($METHODPARMS$) {\n\t\t\n\t}\n\tpublic static void main(String[] args) {\n\t\t$CLASSNAME$ temp = new $CLASSNAME$();\n\t\tSystem.out.println(temp.$METHODNAME$($METHODPARMS$));\n\t}\n}");
/*     */   }
/*     */ 
/*     */   public String getJAVAExtension() {
/*  60 */     return getStringProperty("fileeditor.config.javaextension", "java");
/*     */   }
/*     */ 
/*     */   public final String getCSHARPTemplate() {
/*  64 */     return getStringProperty("fileeditor.config.csharptemplate", "using System;\r\nusing System.Collections;\r\npublic class $CLASSNAME$ {\r\n\tpublic $RC$ $METHODNAME$($METHODPARMS$) {\r\n\t\t$CARETPOSITION$\r\n\t}\r\n}");
/*     */   }
/*     */ 
/*     */   public String getCSHARPExtension() {
/*  68 */     return getStringProperty("fileeditor.config.csharpextension", "cs");
/*     */   }
/*     */ 
/*     */   public String getCPPTemplate() {
/*  72 */     return getStringProperty("fileeditor.config.cpptemplate", "$BEGINCUT$\n$PROBLEMDESC$\n$ENDCUT$\n#line $NEXTLINENUMBER$ \"$FILENAME$\"\n#include <string>\n#include <vector>\nclass $CLASSNAME$ {\n\tpublic:\n\t$RC$ $METHODNAME$($METHODPARMS$) {\n\t\t\n\t}\n};");
/*     */   }
/*     */ 
/*     */   public String getCPPExtension() {
/*  76 */     return getStringProperty("fileeditor.config.cppextension", "cpp");
/*     */   }
/*     */ 
/*     */   public String getDirectoryName() {
/*  80 */     return getStringProperty("fileeditor.config.dirName", ".");
/*     */   }
/*     */ 
/*     */   public String getFileName() {
/*  84 */     return getStringProperty("fileeditor.config.fileName", "problem");
/*     */   }
/*     */ 
/*     */   public String getSignatureFileName() {
/*  88 */     return getStringProperty("fileeditor.config.signaturefilename", "");
/*     */   }
/*     */ 
/*     */   public String getBeginCut() {
/*  92 */     return getStringProperty("fileeditor.config.beginCut", "// BEGIN CUT HERE");
/*     */   }
/*     */ 
/*     */   public String getEndCut() {
/*  96 */     return getStringProperty("fileeditor.config.endCut", "// END CUT HERE");
/*     */   }
/*     */ 
/*     */   public String getProblemDescExtension() {
/* 100 */     return getStringProperty("fileeditor.config.probdescfileextnsion", "txt");
/*     */   }
/*     */ 
/*     */   public boolean isOverrideFileName() {
/* 104 */     return getBooleanProperty("fileeditor.config.overrideFileName", false);
/*     */   }
/*     */ 
/*     */   public boolean isProvideBreaks() {
/* 108 */     return getBooleanProperty("fileeditor.config.provideBreaks", false);
/*     */   }
/*     */ 
/*     */   public boolean isLineComments() {
/* 112 */     return getBooleanProperty("fileeditor.config.lineComments", true);
/*     */   }
/*     */ 
/*     */   public boolean isWriteProblemDescFile() {
/* 116 */     return getBooleanProperty("fileeditor.config.probdescfilewrite", false);
/*     */   }
/*     */ 
/*     */   public boolean isPoweredBy() {
/* 120 */     return getBooleanProperty("fileeditor.config.poweredby", true);
/*     */   }
/*     */ 
/*     */   public boolean isHTMLDesc() {
/* 124 */     return getBooleanProperty("fileeditor.config.htmldesc", false);
/*     */   }
/*     */ 
/*     */   public boolean isBackup() {
/* 128 */     return getBooleanProperty("fileeditor.config.backup", true);
/*     */   }
/*     */ 
/*     */   public int getBreakAt() {
/* 132 */     String value = pref.getProperty("fileeditor.config.breakAt");
/* 133 */     if ((value == null) || (value.equals(""))) return 60;
/*     */     try
/*     */     {
/* 136 */       return Integer.parseInt(value); } catch (NumberFormatException e) {
/*     */     }
/* 138 */     return 60;
/*     */   }
/*     */ 
/*     */   public void setJAVATemplate(String template) {
/* 142 */     pref.setProperty("fileeditor.config.javatemplate", template); } 
/* 143 */   public void setCPPTemplate(String template) { pref.setProperty("fileeditor.config.cpptemplate", template); } 
/* 144 */   public void setCSHARPTemplate(String template) { pref.setProperty("fileeditor.config.csharptemplate", template); } 
/* 145 */   public void setJAVAExtension(String extension) { pref.setProperty("fileeditor.config.javaextension", extension); } 
/* 146 */   public void setCPPExtension(String extension) { pref.setProperty("fileeditor.config.cppextension", extension); } 
/* 147 */   public void setCSHARPExtension(String extension) { pref.setProperty("fileeditor.config.csharpextension", extension); } 
/* 148 */   public void setDirectoryName(String text) { pref.setProperty("fileeditor.config.dirName", text); } 
/* 149 */   public void setFileName(String text) { pref.setProperty("fileeditor.config.fileName", text); } 
/* 150 */   public void setBeginCut(String text) { pref.setProperty("fileeditor.config.beginCut", text); } 
/* 151 */   public void setEndCut(String text) { pref.setProperty("fileeditor.config.endCut", text); } 
/* 152 */   public void setSignatureFileName(String text) { pref.setProperty("fileeditor.config.signaturefilename", text); } 
/* 153 */   public void setProblemDescExtension(String text) { pref.setProperty("fileeditor.config.probdescfileextnsion", text); } 
/* 154 */   public void setOverrideFileName(boolean override) { pref.setProperty("fileeditor.config.overrideFileName", override ? "true" : "false"); } 
/* 155 */   public void setProvideBreaks(boolean provideBreaks) { pref.setProperty("fileeditor.config.provideBreaks", provideBreaks ? "true" : "false"); } 
/* 156 */   public void setLineComments(boolean lineComments) { pref.setProperty("fileeditor.config.lineComments", lineComments ? "true" : "false"); } 
/* 157 */   public void setWriteProblemDescFile(boolean probDescFile) { pref.setProperty("fileeditor.config.probdescfilewrite", probDescFile ? "true" : "false"); } 
/* 158 */   public void setHTMLDesc(boolean htmlDesc) { pref.setProperty("fileeditor.config.htmldesc", htmlDesc ? "true" : "false"); } 
/* 159 */   public void setBackup(boolean backup) { pref.setProperty("fileeditor.config.backup", backup ? "true" : "false"); } 
/* 160 */   public void setBreakAt(int breakAt) { pref.setProperty("fileeditor.config.breakAt", String.valueOf(breakAt)); }
/*     */ 
/*     */   private final String getStringProperty(String key, String defaultValue) {
/* 163 */     String value = pref.getProperty(key);
/* 164 */     return (value == null) || (value.equals("")) ? defaultValue : value;
/*     */   }
/*     */ 
/*     */   private final boolean getBooleanProperty(String key, boolean defaultValue) {
/* 168 */     String value = pref.getProperty(key);
/* 169 */     return (value == null) || (value.equals("")) ? defaultValue : value.equals("true");
/*     */   }
/*     */ 
/*     */   public void save() throws IOException {
/* 173 */     pref.savePreferences();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 177 */     Preferences pref = new Preferences();
/* 178 */     pref.setJAVATemplate("kdjf");
/*     */     try { pref.save(); } catch (Exception e) { System.out.println(e); }
/*     */ 
/*     */   }
/*     */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.Preferences
 * JD-Core Version:    0.6.2
 */