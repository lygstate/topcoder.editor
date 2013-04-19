/*     */ package fileedit;
/*     */ 
/*     */ import com.topcoder.client.contestant.ProblemComponentModel;
/*     */ import com.topcoder.shared.language.Language;
/*     */ import com.topcoder.shared.problem.DataType;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringReader;
/*     */ import java.text.BreakIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Utilities
/*     */ {
/*  20 */   public static final String lineEnding = System.getProperty("line.separator");
/*  21 */   private static final Preferences pref = new Preferences();
/*     */ 
/*     */   public static final String getSource(Language language, ProblemComponentModel component, String fileName, String problemText)
/*     */   {
/*  25 */     String source = "";
/*     */ 
/*  28 */     if (language.getId() == 1)
/*  29 */       source = source + pref.getJAVATemplate();
/*  30 */     else if (language.getId() == 3)
/*  31 */       source = source + pref.getCPPTemplate();
/*     */     else {
/*  33 */       source = source + pref.getCSHARPTemplate();
/*     */     }
/*     */ 
/*  40 */     source = replaceAll(source, "$BEGINCUT$", pref.getBeginCut());
/*  41 */     source = replaceAll(source, "$ENDCUT$", pref.getEndCut());
/*  42 */     source = replaceAll(source, "$PROBLEMDESC$", pref.isWriteProblemDescFile() ? "" : problemText);
/*  43 */     source = replaceAll(source, "$CLASSNAME$", component.getClassName());
/*  44 */     source = replaceAll(source, "$METHODNAME$", component.getMethodName());
/*  45 */     source = replaceAll(source, "$RC$", component.getReturnType().getDescriptor(language));
/*  46 */     source = replaceAll(source, "$FILENAME$", fileName);
/*     */ 
/*  48 */     StringBuffer parmString = new StringBuffer();
/*  49 */     DataType[] dataType = component.getParamTypes();
/*  50 */     String[] parms = component.getParamNames();
/*  51 */     char varName = 'a';
/*     */ 
/*  53 */     for (int x = 0; x < dataType.length; x++) {
/*  54 */       if (parmString.length() != 0) parmString.append(", ");
/*  55 */       parmString.append(dataType[x].getDescriptor(language));
/*  56 */       parmString.append(' ');
/*  57 */       if (x < parms.length) {
/*  58 */         parmString.append(parms[x]);
/*     */       } else {
/*  60 */         parmString.append(varName);
/*  61 */         varName = (char)(varName + '\001');
/*     */       }
/*     */     }
/*     */ 
/*  65 */     source = replaceAll(source, "$METHODPARMS$", parmString.toString());
/*  66 */     source = replaceLineNumber(source);
/*     */ 
/*  70 */     return source;
/*     */   }
/*     */ 
/*     */   public static final String replaceUserDefined(String source, Map userDefinedTags)
/*     */   {
/*  75 */     for (Iterator itr = userDefinedTags.keySet().iterator(); itr.hasNext(); ) {
/*     */       try
/*     */       {
/*  78 */         String tag = (String)itr.next();
/*  79 */         if (tag != null)
/*     */         {
/*  82 */           String replaceSource = (String)userDefinedTags.get(tag);
/*  83 */           if (replaceSource != null)
/*     */           {
/*  86 */             source = replaceAll(source, tag, replaceSource);
/*     */           }
/*     */         } } catch (ClassCastException e) { System.out.println("Error in userDefinedTags - either the tag or it's source was not a String type"); }
/*     */ 
/*     */     }
/*     */ 
/*  92 */     return source;
/*     */   }
/*     */ 
/*     */   public static final String replaceAll(String source, String text, String newText) {
/*  96 */     int pos = -1;
/*     */     while (true) {
/*  98 */       pos = source.indexOf(text);
/*  99 */       if (pos < 0) break;
/* 100 */       source = source.substring(0, pos) + newText + source.substring(pos + text.length());
/*     */     }
/* 102 */     return source;
/*     */   }
/*     */ 
/*     */   public static final String replaceLineNumber(String source)
/*     */   {
/* 107 */     LineNumberReader in = new LineNumberReader(new StringReader(source));
/* 108 */     StringBuffer str = new StringBuffer(source.length());
/*     */     while (true)
/*     */     {
/*     */       try
/*     */       {
/* 115 */         String temp = in.readLine();
/* 116 */         if (temp == null) break; 
/*     */       }
/* 118 */       catch (IOException e) { return source; }
/*     */ 
/*     */ 
/* 122 */       String temp = replaceAll(temp, "$LINENUMBER$", String.valueOf(in.getLineNumber()));
/* 123 */       temp = replaceAll(temp, "$NEXTLINENUMBER$", String.valueOf(in.getLineNumber() + 1));
/*     */ 
/* 126 */       str.append(temp);
/* 127 */       str.append(lineEnding);
/*     */     }
/*     */ 
/* 131 */     return str.toString();
/*     */   }
/*     */ 
/*     */   public static final String parseProblem(String problem)
/*     */   {
/* 137 */     if (problem.length() < 1) return problem;
/*     */ 
/* 140 */     int breakAt = pref.isProvideBreaks() ? pref.getBreakAt() : 2147483647;
/* 141 */     boolean lineComments = pref.isLineComments();
/*     */ 
/* 144 */     StringBuffer buf = new StringBuffer(problem.length() + 50);
/* 145 */     BreakIterator itr = BreakIterator.getLineInstance();
/* 146 */     itr.setText(problem);
/*     */ 
/* 149 */     int start = itr.first();
/* 150 */     int end = itr.next();
/*     */ 
/* 153 */     if (lineComments) buf.append("// ");
/*     */ 
/* 156 */     while (end != -1)
/*     */     {
/* 159 */       int p1 = problem.indexOf("\r\n", start);
/* 160 */       if (p1 < 0) p1 = 2147483647;
/*     */ 
/* 162 */       int p2 = problem.indexOf("\n", start);
/* 163 */       if (p2 < 0) p2 = 2147483647;
/*     */ 
/* 165 */       int p3 = problem.indexOf("\r", start);
/* 166 */       if (p3 < 0) p3 = 2147483647;
/*     */ 
/* 168 */       int pos = Math.min(p1, Math.min(p2, p3));
/* 169 */       if ((pos >= start) && (pos <= start + breakAt)) {
/* 170 */         buf.append(problem.substring(start, pos));
/* 171 */         buf.append(lineEnding);
/* 172 */         if (lineComments) buf.append("// ");
/* 173 */         start = pos;
/* 174 */         if ((pos + 2 < problem.length()) && (problem.substring(pos, pos + 2).equals("\r\n")))
/* 175 */           start += 2;
/*     */         else {
/* 177 */           start++;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 183 */         if (end - start >= breakAt)
/*     */         {
/* 185 */           int prev = itr.previous();
/* 186 */           if (start == prev)
/* 187 */             end = itr.next();
/*     */           else {
/* 189 */             end = prev;
/*     */           }
/*     */ 
/* 193 */           buf.append(problem.substring(start, end));
/* 194 */           buf.append(lineEnding);
/* 195 */           if (lineComments) buf.append("// ");
/* 196 */           start = end;
/*     */         }
/*     */ 
/* 200 */         end = itr.next();
/*     */       }
/*     */     }
/*     */ 
/* 204 */     buf.append(problem.substring(start, itr.last()));
/*     */ 
/* 208 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 212 */     ArrayList parms = new ArrayList();
/* 213 */     parms.add("String");
/* 214 */     parms.add("String");
/* 215 */     parms.add("int");
/* 216 */     String probState = "PROBLEM STATEMENT\r\nWhen putting together a problem set, a writer must keep in mind the difficulty and length of a problem.  A good problem set is one with an easy, a middle, and a hard problem, but doesn't take too much or too little time to complete.\r\n\nYou will be given an input of three int[].  The first int[] consists of easy problem times, the second consists of middle problem times, and the third consists of hard problem times.  Return the number of legal problem set combinations, where a legal set contains exactly 1 easy, 1 middle and 1 hard problem, and the total time is between 60 and 75 inclusive.\r\n\r\nDEFINITION\r\nClass name: Chooser\r\nMethod name: numSets\r\nParameters: int[], int[], int[]\r\nReturns: int\r\nThe method signature is:\r\nint numSets(int[] easy, int[] middle, int[] hard)\r\nBe sure your method is public.\r\n\r\nTopCoder will ensure the following:\r\n*Each int[] will contain between 0 and 10 elements, inclusive.\r\n*Each element of easy will be an int between 5 and 15, inclusive.\r\n*Each element of middle will be an int between 15 and 45, inclusive.\r\n*Each element of hard will be an int between 30 and 55, inclusive.\r\n\r\nEXAMPLES\r{5,10,15}\r\n{15,25}\r\n{45}\r\nThere are 3*2*1=6 possible sets.  However, since 10+25+45=80 and 15+25+45=85, two of the sets are illegal, so the method returns 4.\r\n\r\n{5,5,5}\r\n{15,15,15}\r\n{45,45,45}\r\nThere are 3*3*3=27 possible sets, all legal.  The return value is 27.\r\n\r\n{5,5,5}\r\n{15,15,15}\r\n{45,45,35}\r\nThere are 27 possible sets again, but for this input any set with the 35 minute hard problem is too short.  Therefore there are only 3*3*2=18 legal sets, and the return value is 18.\r\n\r\n{}\r\n{15,25}\r\n{30,35,40}\r\nSince there are no easy problems, there are no legal problem sets.  The return value is 0.\n";
/*     */     try
/*     */     {
/* 257 */       System.setOut(new PrintStream(new FileOutputStream("run.log"))); } catch (IOException localIOException) {
/*     */     }
/* 259 */     System.out.println(parseProblem(probState));
/*     */   }
/*     */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.Utilities
 * JD-Core Version:    0.6.2
 */