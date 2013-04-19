/*    */ package topcoder.editor;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.border.LineBorder;
/*    */ 
/*    */ public class RoundBorder extends LineBorder
/*    */ {
/*    */   public RoundBorder(Color color)
/*    */   {
/* 16 */     super(color, 1);
/* 17 */     this.roundedCorners = false;
/*    */   }
/*    */ 
/*    */   public RoundBorder(Color color, int thickness)
/*    */   {
/* 24 */     super(color, thickness);
/* 25 */     this.roundedCorners = false;
/*    */   }
/*    */ 
/*    */   public RoundBorder(Color color, int thickness, boolean roundedCorners)
/*    */   {
/* 32 */     super(color, thickness);
/* 33 */     this.roundedCorners = roundedCorners;
/*    */   }
/*    */ 
/*    */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*    */   {
/* 40 */     Color oldColor = g.getColor();
/*    */ 
/* 43 */     g.setColor(this.lineColor);
/* 44 */     for (int i = 0; i < this.thickness; i++) {
/* 45 */       if (!this.roundedCorners)
/* 46 */         g.drawRect(x + i, y + i, width - i - i - 1, height - i - i - 1);
/*    */       else {
/* 48 */         g.drawRoundRect(x + i, y + i, width - i - i - 1, height - i - i - 1, this.thickness, this.thickness);
/*    */       }
/*    */     }
/* 51 */     g.setColor(oldColor);
/*    */   }
/*    */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.RoundBorder
 * JD-Core Version:    0.6.2
 */