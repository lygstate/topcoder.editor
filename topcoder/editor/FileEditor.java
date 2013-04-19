/*    */ package topcoder.editor;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JTextArea;
/*    */ import javax.swing.text.JTextComponent;
/*    */ 
/*    */ public class FileEditor extends JTextArea
/*    */ {
/*    */   public FileEditor(String string)
/*    */   {
/* 26 */     init(string);
/*    */   }
/*    */ 
/*    */   public FileEditor(String string, int rows, int columns)
/*    */   {
/* 41 */     super(rows, columns);
/* 42 */     init(string);
/*    */   }
/*    */ 
/*    */   public void init(String text)
/*    */   {
/* 47 */     setText(text);
/*    */ 
/* 49 */     setFont(new Font("Courier", 0, 12));
/* 50 */     setMargin(new Insets(5, 5, 5, 5));
/* 51 */     setSelectedTextColor(EditorCommon.HF_COLOR);
/* 52 */     setSelectionColor(EditorCommon.HB_COLOR);
/* 53 */     setCaretColor(EditorCommon.FG_COLOR);
/* 54 */     setForeground(EditorCommon.FG_COLOR);
/* 55 */     setBackground(EditorCommon.BG_COLOR);
/* 56 */     setEditable(false);
/*    */   }
/*    */ 
/*    */   public void setText(String text)
/*    */   {
/* 68 */     super.setText(text);
/* 69 */     setCaretPosition(0);
/*    */   }
/*    */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.FileEditor
 * JD-Core Version:    0.6.2
 */