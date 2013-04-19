/*    */ package fileedit;
/*    */ 
/*    */ import javax.swing.text.AbstractDocument;
/*    */ import javax.swing.text.AttributeSet;
/*    */ import javax.swing.text.BadLocationException;
/*    */ import javax.swing.text.PlainDocument;
/*    */ 
/*    */ public class PlainDocumentLimited extends PlainDocument
/*    */ {
/*    */   private int limit;
/*  6 */   private boolean toUppercase = false;
/*    */ 
/*    */   public PlainDocumentLimited(int limit)
/*    */   {
/* 10 */     this.limit = limit;
/*    */   }
/*    */ 
/*    */   public PlainDocumentLimited(int limit, boolean upper)
/*    */   {
/* 15 */     this.limit = limit;
/* 16 */     this.toUppercase = upper;
/*    */   }
/*    */ 
/*    */   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
/* 20 */     if (str == null) return;
/*    */ 
/* 22 */     if (getLength() + str.length() <= this.limit) {
/* 23 */       if (this.toUppercase) str = str.toUpperCase();
/* 24 */       super.insertString(offset, str, attr);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.PlainDocumentLimited
 * JD-Core Version:    0.6.2
 */