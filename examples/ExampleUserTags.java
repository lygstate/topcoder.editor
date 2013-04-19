/*    */ package examples;
/*    */ 
/*    */ import com.topcoder.client.contestant.ProblemComponentModel;
/*    */ import com.topcoder.shared.language.Language;
/*    */ import com.topcoder.shared.problem.Renderer;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ExampleUserTags
/*    */ {
/* 21 */   HashMap myTags = new HashMap();
/*    */ 
/*    */   public String preProcess(String paramString, ProblemComponentModel paramProblemComponentModel, Language paramLanguage, Renderer paramRenderer)
/*    */   {
/* 35 */     if (!paramString.equals("")) return paramString;
/*    */ 
/* 46 */     this.myTags.put("$PHONE$", "If you want to hire me - call 867-5309");
/* 47 */     this.myTags.put("$NAME$", "Jenny");
/* 48 */     this.myTags.put("$LANGUAGE$", paramLanguage.getName());
/*    */ 
/* 51 */     return "";
/*    */   }
/*    */ 
/*    */   public Map getUserDefinedTags()
/*    */   {
/* 60 */     return this.myTags;
/*    */   }
/*    */ }

/* Location:           C:\Users\Game\Downloads\CodeProcessor.jar
 * Qualified Name:     examples.ExampleUserTags
 * JD-Core Version:    0.6.2
 */