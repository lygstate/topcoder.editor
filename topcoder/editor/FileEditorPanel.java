/*    */ package topcoder.editor;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Font;
/*    */ import java.awt.Window;
/*    */ import javax.swing.Box;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTextArea;
/*    */ 
/*    */ public class FileEditorPanel extends JPanel
/*    */ {
/*    */   public FileEditorPanel(JTextArea textArea)
/*    */   {
/* 24 */     EditorCommon.setDefaultAttributes(this);
/*    */ 
/* 27 */     JLabel statusLabel = EditorCommon.createJLabel("FileEdit v2.70", new Font(getFont().getFontName(), 3, getFont().getSize()));
/* 28 */     JLabel reminderLabel = EditorCommon.createJLabel("*** Make sure you remotely compile before test/submit ***", new Font(getFont().getFontName(), 3, getFont().getSize()));
/*    */ 
/* 31 */     JScrollPane scrollPane = EditorCommon.createJScrollPane(textArea);
/* 32 */     scrollPane.setBorder(EditorCommon.getTitledBorder("Activity Log"));
/*    */ 
/* 35 */     Box status = EditorCommon.createHorizontalBox(new Component[] { statusLabel, Box.createHorizontalGlue(), reminderLabel }, false);
/*    */ 
/* 38 */     add(scrollPane, "Center");
/* 39 */     add(status, "South");
/*    */   }
/*    */ 
/*    */   public static void main(String[] s)
/*    */   {
/* 45 */     JFrame frame = new JFrame();
/* 46 */     frame.setDefaultCloseOperation(3);
/* 47 */     JTextArea text = new JTextArea();
/* 48 */     text.setForeground(EditorCommon.FG_COLOR);
/* 49 */     text.setBackground(EditorCommon.BG_COLOR);
/* 50 */     frame.getContentPane().add(new FileEditorPanel(text));
/* 51 */     frame.pack();
/* 52 */     frame.show();
/*    */   }
/*    */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.FileEditorPanel
 * JD-Core Version:    0.6.2
 */