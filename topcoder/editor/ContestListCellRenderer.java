/*    */ package topcoder.editor;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.ListCellRenderer;
/*    */ 
/*    */ public class ContestListCellRenderer extends JLabel
/*    */   implements ListCellRenderer
/*    */ {
/* 10 */   JList internalList = null;
/*    */ 
/*    */   public ContestListCellRenderer()
/*    */   {
/* 16 */     setOpaque(true);
/*    */   }
/*    */ 
/*    */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*    */   {
/* 27 */     if ((this.internalList != list) && (list != null)) {
/* 28 */       this.internalList = list;
/*    */ 
/* 30 */       list.setSelectionForeground(EditorCommon.TF_COLOR);
/* 31 */       list.setSelectionBackground(EditorCommon.TB_COLOR);
/*    */     }
/*    */ 
/* 34 */     if (isSelected) {
/* 35 */       setForeground(EditorCommon.HF_COLOR);
/* 36 */       setBackground(EditorCommon.HB_COLOR);
/*    */     } else {
/* 38 */       setForeground(EditorCommon.TF_COLOR);
/* 39 */       setBackground(EditorCommon.TB_COLOR);
/*    */     }
/*    */ 
/* 42 */     if (value != null) {
/* 43 */       setText(value.toString());
/*    */     }
/*    */ 
/* 46 */     return this;
/*    */   }
/*    */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.ContestListCellRenderer
 * JD-Core Version:    0.6.2
 */