/*     */ package fileedit;
/*     */ 
/*     */ import com.topcoder.client.contestant.ProblemComponentModel;
/*     */ import com.topcoder.shared.language.Language;
/*     */ import com.topcoder.shared.problem.Renderer;
/*     */ import java.awt.Dialog;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringReader;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Observable;
/*     */ import java.util.Observer;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public class EntryPoint
/*     */   implements Observer
/*     */ {
/*     */   JPanel panel;
/*  22 */   JTextArea log = new JTextArea();
/*     */   boolean overridefname;
/*     */   File directory;
/*  24 */   File fullPath = null;
/*     */   String fileName;
/*     */   String beginCut;
/*     */   String endCut;
/*  25 */   String initialSrc = null;
/*  26 */   Preferences pref = new Preferences(this);
/*  27 */   Map userDefinedTags = new HashMap();
/*     */   Language language;
/*  29 */   ProblemComponentModel component = null;
/*     */   Renderer renderer;
/*     */   private static final String POWEREDBY = "// Powered by FileEdit";
/*     */ 
/*     */   public EntryPoint()
/*     */   {
/*  36 */     this.log.setForeground(Common.FG_COLOR);
/*  37 */     this.log.setBackground(Common.BG_COLOR);
/*     */ 
/*  40 */     loadPreferences();
/*     */ 
/*  43 */     this.panel = new FileEditorPanel(this.log);
/*     */   }
/*     */ 
/*     */   public void configure() {
/*  47 */     new ConfigurationDialog().show();
/*     */   }
/*     */   public void setUserDefinedTags(Map userDefinedTags) {
/*  50 */     this.userDefinedTags = userDefinedTags;
/*     */   }
/*     */ 
/*     */   public void setProblemComponent(ProblemComponentModel component, Language lang, Renderer renderer) {
/*  54 */     this.component = component;
/*  55 */     this.language = lang;
/*  56 */     this.renderer = renderer;
/*     */   }
/*     */ 
/*     */   public JPanel getEditorPanel()
/*     */   {
/*  66 */     return this.panel;
/*     */   }
/*     */ 
/*     */   public String getSource()
/*     */   {
/*  71 */     if (this.fullPath == null) {
/*  72 */       writeLog("Trying to read source but file isn't initialized!.  Returning nothing.");
/*  73 */       return "";
/*     */     }
/*     */ 
/*  77 */     if (!this.fullPath.exists()) {
/*  78 */       writeLog("Trying to read File " + this.fullPath + " but it does not exist!.  Returning nothing.");
/*  79 */       return "";
/*     */     }
/*     */ 
/*  83 */     int len = (int)this.fullPath.length();
/*  84 */     StringBuffer sourceComments = new StringBuffer(len);
/*  85 */     StringBuffer source = new StringBuffer(len);
/*     */ 
/*  88 */     BufferedReader in = null;
/*  89 */     boolean ignoreLine = false;
/*     */     try {
/*  91 */       in = new BufferedReader(new FileReader(this.fullPath));
/*     */       while (true)
/*     */       {
/*  94 */         String line = in.readLine();
/*  95 */         if (line == null) {
/*     */           break;
/*     */         }
/*  98 */         sourceComments.append(line);
/*  99 */         sourceComments.append(Utilities.lineEnding);
/*     */ 
/* 102 */         if (line.trim().equals(this.beginCut.trim())) { ignoreLine = true;
/* 103 */         } else if (line.trim().equals(this.endCut.trim())) { ignoreLine = false;
/* 104 */         } else if (!ignoreLine)
/*     */         {
/* 107 */           source.append(line);
/* 108 */           source.append(Utilities.lineEnding);
/*     */         }
/*     */       }
/*     */     } catch (IOException e) { writeLog("Error reading source code from file " + this.fullPath + ": " + e.toString() + Utilities.lineEnding + "Returning nothing.");
/* 112 */       return ""; } finally {
/* 113 */       break label305; localObject1 = returnAddress;
/* 114 */       if (in != null) try { in.close(); } catch (IOException e)
/*     */         {
/*     */         } 
/*     */     }
/* 118 */     label305: writeLog("Source read from file " + this.fullPath);
/*     */ 
/* 121 */     if ((this.initialSrc != null) && (this.initialSrc.equals(sourceComments.toString()))) {
/* 122 */       writeLog("No changes to initial source - returning nothing");
/* 123 */       return "";
/*     */     }
/*     */ 
/* 128 */     if ((this.pref.isPoweredBy()) && (!source.toString().endsWith("// Powered by FileEdit")) && (source.length() != 0)) {
/* 129 */       source.append(Utilities.lineEnding);
/* 130 */       source.append(Utilities.lineEnding);
/* 131 */       source.append("// Powered by FileEdit");
/*     */     }
/*     */ 
/* 135 */     String sig = getSignature();
/* 136 */     if ((!source.toString().startsWith(sig)) && (source.length() != 0)) {
/* 137 */       source.insert(0, sig);
/*     */     }
/*     */ 
/* 141 */     return source.toString();
/*     */   }
/*     */ 
/*     */   protected String getSignature()
/*     */   {
/* 147 */     String sigFileName = this.pref.getSignatureFileName().trim();
/*     */ 
/* 150 */     if (!sigFileName.equals(""))
/*     */     {
/* 153 */       File sigFile = new File(sigFileName);
/*     */ 
/* 156 */       if (sigFile.exists())
/*     */       {
/* 159 */         StringBuffer sig = new StringBuffer((int)sigFile.length());
/* 160 */         BufferedReader in = null;
/*     */         try
/*     */         {
/* 164 */           in = new BufferedReader(new FileReader(sigFile));
/*     */           while (true) {
/* 166 */             String line = in.readLine();
/* 167 */             if (line == null) break;
/* 168 */             sig.append(line);
/* 169 */             sig.append(Utilities.lineEnding);
/*     */           }
/*     */ 
/* 173 */           return sig.toString();
/*     */         } catch (IOException e) {
/* 175 */           writeLog("Error reading the signature file " + sigFileName + ":" + e.toString() + Utilities.lineEnding + "SigFile ignored"); } finally {
/* 176 */           break label196; localObject1 = returnAddress;
/* 177 */           if (in != null) try { in.close(); } catch (IOException e)
/*     */             {
/*     */             } 
/*     */         }
/*     */       }
/*     */     }
/* 183 */     label196: return "";
/*     */   }
/*     */ 
/*     */   public void setSource(String source)
/*     */   {
/* 189 */     String className = this.component.getClassName();
/*     */ 
/* 192 */     String tFileName = (this.overridefname ? this.fileName : className) + "." + (this.language.getId() == 1 ? this.pref.getJAVAExtension() : this.language.getId() == 3 ? this.pref.getCPPExtension() : this.pref.getCSHARPExtension());
/*     */     try
/*     */     {
/* 197 */       problemDescription = this.pref.isHTMLDesc() ? this.renderer.toHTML(this.language) : this.renderer.toPlainText(this.language);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       String problemDescription;
/* 199 */       System.err.println("Exception happened during rendering: " + e);
/* 200 */       problemDescription = this.pref.isHTMLDesc() ? "<html><body>Error happened - see applet for problem text</body></html>" : "Error happened - see applet for problem text";
/*     */     }
/*     */ 
/* 204 */     String problemDescription = this.pref.isHTMLDesc() ? problemDescription : Utilities.parseProblem(problemDescription);
/*     */ 
/* 207 */     if ((source == null) || (source.equals("")) || (source.equals(this.component.getDefaultSolution()))) {
/* 208 */       source = Utilities.getSource(this.language, this.component, tFileName, this.pref.isHTMLDesc() ? "" : problemDescription);
/*     */     }
/*     */ 
/* 212 */     source = Utilities.replaceUserDefined(source, this.userDefinedTags);
/*     */ 
/* 215 */     this.fullPath = new File(this.directory, tFileName);
/*     */ 
/* 218 */     if (this.fullPath.exists())
/*     */     {
/* 220 */       if (!this.pref.isBackup()) {
/* 221 */         writeLog("File '" + this.fullPath + "' already exists - not overwriting due to config option");
/* 222 */         return;
/*     */       }
/*     */ 
/* 226 */       File backup = new File(this.directory, tFileName + ".bak");
/* 227 */       if (backup.exists()) {
/* 228 */         if (backup.delete())
/* 229 */           writeLog("Backup file " + backup + " exists and was deleted");
/*     */         else {
/* 231 */           writeLog("Error deleting backup file " + backup);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 236 */       File renameIt = new File(this.directory, tFileName);
/* 237 */       if (renameIt.renameTo(backup)) {
/* 238 */         writeLog("File " + this.fullPath + " is being backed up to " + backup);
/*     */       } else {
/* 240 */         writeLog("Error backing up file " + this.fullPath);
/* 241 */         writeLog("No Backup exists for the file - be careful");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 246 */     BufferedReader in = null;
/* 247 */     BufferedWriter out = null;
/*     */     try
/*     */     {
/* 250 */       if (this.directory.mkdirs()) writeLog("Directory " + this.directory + " was created");
/* 251 */       if (this.fullPath.createNewFile()) writeLog("File " + this.fullPath + " was created");
/*     */ 
/* 254 */       out = new BufferedWriter(new FileWriter(this.fullPath));
/* 255 */       in = new BufferedReader(new StringReader(source));
/*     */       while (true) {
/* 257 */         String line = in.readLine();
/* 258 */         if (line == null) break;
/* 259 */         out.write(line);
/* 260 */         out.write(Utilities.lineEnding);
/*     */       }
/* 262 */       writeLog("Source successfully written to " + this.fullPath);
/*     */     } catch (IOException e) {
/*     */     } finally {
/* 265 */       break label849; localObject1 = returnAddress;
/*     */       try {
/* 267 */         in.close();
/*     */       }
/*     */       catch (IOException localIOException1) {
/*     */       }
/*     */       try {
/* 272 */         out.flush();
/* 273 */         out.close();
/*     */       } catch (IOException e) {
/* 275 */         writeLog("Error closing the file " + this.fullPath + ": " + e.toString());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 280 */     label849: if (this.pref.isWriteProblemDescFile()) {
/* 281 */       String pFileName = (this.overridefname ? this.fileName : className) + "." + this.pref.getProblemDescExtension();
/* 282 */       File pFullPath = new File(this.directory, pFileName);
/*     */       try
/*     */       {
/* 285 */         if (this.directory.mkdirs()) writeLog("Directory " + this.directory + " was created");
/* 286 */         if (pFullPath.createNewFile()) writeLog("File " + pFullPath + " was created");
/*     */ 
/* 289 */         out = new BufferedWriter(new FileWriter(pFullPath));
/* 290 */         in = new BufferedReader(new StringReader(problemDescription));
/*     */         while (true) {
/* 292 */           String line = in.readLine();
/* 293 */           if (line == null) break;
/* 294 */           out.write(line);
/* 295 */           out.write(Utilities.lineEnding);
/*     */         }
/* 297 */         writeLog("Problem Description successfully written to " + pFullPath);
/*     */       } catch (IOException e) {
/*     */       } finally {
/* 300 */         return; e = returnAddress;
/*     */         try {
/* 302 */           in.close();
/*     */         }
/*     */         catch (IOException localIOException2) {
/*     */         }
/*     */         try {
/* 307 */           out.flush();
/* 308 */           out.close();
/*     */         } catch (IOException e) {
/* 310 */           writeLog("Error closing the file " + pFullPath + ": " + e.toString());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update(Observable o, Object a)
/*     */   {
/* 318 */     loadPreferences();
/*     */   }
/*     */ 
/*     */   private final void loadPreferences() {
/* 322 */     String dirName = this.pref.getDirectoryName();
/* 323 */     this.directory = new File(dirName.trim());
/*     */ 
/* 326 */     this.fileName = this.pref.getFileName();
/*     */ 
/* 329 */     this.beginCut = this.pref.getBeginCut();
/* 330 */     this.endCut = this.pref.getEndCut();
/*     */ 
/* 333 */     this.overridefname = this.pref.isOverrideFileName();
/*     */   }
/*     */ 
/*     */   private final void writeLog(String text) {
/* 337 */     this.log.append(text);
/* 338 */     this.log.append("\n");
/* 339 */     this.log.setCaretPosition(this.log.getDocument().getLength() - 1);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 343 */     ArrayList parms = new ArrayList();
/* 344 */     parms.add("String");
/* 345 */     parms.add("String");
/* 346 */     parms.add("int");
/*     */ 
/* 350 */     EntryPoint en = new EntryPoint();
/*     */ 
/* 356 */     System.out.println(en.getSource());
/*     */   }
/*     */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.EntryPoint
 * JD-Core Version:    0.6.2
 */