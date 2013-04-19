/*     */ package fileedit;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class MyTitledBorder extends TitledBorder
/*     */ {
/*     */   protected static final int EDGE_SPACING = 3;
/*     */   protected static final int TEXT_SPACING = 3;
/*     */   protected static final int TEXT_INSET_H = 5;
/*  21 */   private Point textLoc = new Point();
/*     */ 
/*     */   public MyTitledBorder(Border b, String t, int j, int p)
/*     */   {
/*  27 */     super(b, t, j, p);
/*     */   }
/*     */ 
/*     */   private static boolean computeIntersection(Rectangle dest, int rx, int ry, int rw, int rh)
/*     */   {
/*  34 */     int x1 = Math.max(rx, dest.x);
/*  35 */     int x2 = Math.min(rx + rw, dest.x + dest.width);
/*  36 */     int y1 = Math.max(ry, dest.y);
/*  37 */     int y2 = Math.min(ry + rh, dest.y + dest.height);
/*  38 */     dest.x = x1;
/*  39 */     dest.y = y1;
/*  40 */     dest.width = (x2 - x1);
/*  41 */     dest.height = (y2 - y1);
/*     */ 
/*  43 */     if ((dest.width <= 0) || (dest.height <= 0)) {
/*  44 */       return false;
/*     */     }
/*     */ 
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*     */   {
/*  54 */     Border border = getBorder();
/*     */ 
/*  56 */     if ((getTitle() == null) || (getTitle().equals(""))) {
/*  57 */       if (border != null) {
/*  58 */         border.paintBorder(c, g, x, y, width, height);
/*     */       }
/*  60 */       return;
/*     */     }
/*     */ 
/*  63 */     Rectangle grooveRect = new Rectangle(x + 3, y + 3, 
/*  64 */       width - 6, 
/*  65 */       height - 6);
/*  66 */     Font font = g.getFont();
/*  67 */     Color color = g.getColor();
/*     */ 
/*  69 */     g.setFont(getFont(c));
/*     */ 
/*  71 */     FontMetrics fm = g.getFontMetrics();
/*  72 */     int fontHeight = fm.getHeight();
/*  73 */     int descent = fm.getDescent();
/*  74 */     int ascent = fm.getAscent();
/*     */ 
/*  76 */     int stringWidth = fm.stringWidth(getTitle());
/*     */     Insets insets;
/*     */     Insets insets;
/*  79 */     if (border != null)
/*  80 */       insets = border.getBorderInsets(c);
/*     */     else {
/*  82 */       insets = new Insets(0, 0, 0, 0);
/*     */     }
/*     */ 
/*  85 */     int titlePos = getTitlePosition();
/*  86 */     switch (titlePos) {
/*     */     case 1:
/*  88 */       int diff = ascent + descent + (Math.max(3, 
/*  89 */         6) - 3);
/*  90 */       grooveRect.y += diff;
/*  91 */       grooveRect.height -= diff;
/*  92 */       this.textLoc.y = (grooveRect.y - (descent + 3));
/*  93 */       break;
/*     */     case 0:
/*     */     case 2:
/*  96 */       int diff = Math.max(0, ascent / 2 + 3 - 3);
/*  97 */       grooveRect.y += diff;
/*  98 */       grooveRect.height -= diff;
/*  99 */       this.textLoc.y = 
/* 100 */         (grooveRect.y - descent + 
/* 100 */         (insets.top + ascent + descent) / 2);
/* 101 */       break;
/*     */     case 3:
/* 103 */       this.textLoc.y = (grooveRect.y + insets.top + ascent + 3);
/* 104 */       break;
/*     */     case 4:
/* 106 */       this.textLoc.y = 
/* 107 */         (grooveRect.y + grooveRect.height - (
/* 107 */         insets.bottom + descent + 3));
/* 108 */       break;
/*     */     case 5:
/* 110 */       grooveRect.height -= fontHeight / 2;
/* 111 */       this.textLoc.y = 
/* 112 */         (grooveRect.y + grooveRect.height - descent + 
/* 112 */         (ascent + descent - insets.bottom) / 2);
/* 113 */       break;
/*     */     case 6:
/* 115 */       grooveRect.height -= fontHeight;
/* 116 */       this.textLoc.y = 
/* 117 */         (grooveRect.y + grooveRect.height + ascent + 
/* 117 */         3);
/*     */     }
/*     */ 
/* 121 */     switch (getTitleJustification()) {
/*     */     case 0:
/*     */     case 1:
/* 124 */       this.textLoc.x = (grooveRect.x + 5 + insets.left);
/* 125 */       break;
/*     */     case 3:
/* 127 */       this.textLoc.x = 
/* 128 */         (grooveRect.x + grooveRect.width - (
/* 128 */         stringWidth + 5 + insets.right));
/* 129 */       break;
/*     */     case 2:
/* 131 */       this.textLoc.x = 
/* 132 */         (grooveRect.x + 
/* 132 */         (grooveRect.width - stringWidth) / 2);
/*     */     }
/*     */ 
/* 141 */     if (border != null) {
/* 142 */       if ((titlePos == 2) || (titlePos == 5)) {
/* 143 */         Rectangle clipRect = new Rectangle();
/*     */ 
/* 146 */         Rectangle saveClip = g.getClipBounds();
/*     */ 
/* 149 */         clipRect.setBounds(saveClip);
/* 150 */         if (computeIntersection(clipRect, x, y, this.textLoc.x, height)) {
/* 151 */           g.setClip(clipRect);
/* 152 */           border.paintBorder(c, g, grooveRect.x, grooveRect.y, 
/* 153 */             grooveRect.width, grooveRect.height);
/*     */         }
/*     */ 
/* 157 */         clipRect.setBounds(saveClip);
/* 158 */         if (computeIntersection(clipRect, this.textLoc.x + stringWidth, 0, 
/* 159 */           width - stringWidth - this.textLoc.x, height)) {
/* 160 */           g.setClip(clipRect);
/* 161 */           border.paintBorder(c, g, grooveRect.x, grooveRect.y, 
/* 162 */             grooveRect.width, grooveRect.height);
/*     */         }
/*     */ 
/* 166 */         clipRect.setBounds(saveClip);
/* 167 */         if (titlePos == 2) {
/* 168 */           if (computeIntersection(clipRect, this.textLoc.x, grooveRect.y + insets.top, 
/* 169 */             stringWidth, height - grooveRect.y - insets.top)) {
/* 170 */             g.setClip(clipRect);
/* 171 */             border.paintBorder(c, g, grooveRect.x, grooveRect.y, 
/* 172 */               grooveRect.width, grooveRect.height);
/*     */           }
/*     */         }
/* 175 */         else if (computeIntersection(clipRect, this.textLoc.x, y, 
/* 176 */           stringWidth, height - insets.bottom - (
/* 177 */           height - grooveRect.height - grooveRect.y))) {
/* 178 */           g.setClip(clipRect);
/* 179 */           border.paintBorder(c, g, grooveRect.x, grooveRect.y, 
/* 180 */             grooveRect.width, grooveRect.height);
/*     */         }
/*     */ 
/* 185 */         g.setClip(saveClip);
/*     */       }
/*     */       else {
/* 188 */         border.paintBorder(c, g, grooveRect.x, grooveRect.y, 
/* 189 */           grooveRect.width, grooveRect.height);
/*     */       }
/*     */     }
/*     */ 
/* 193 */     g.setColor(getTitleColor());
/* 194 */     g.drawString(getTitle(), this.textLoc.x, this.textLoc.y);
/*     */ 
/* 196 */     g.setFont(font);
/* 197 */     g.setColor(color);
/*     */   }
/*     */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.MyTitledBorder
 * JD-Core Version:    0.6.2
 */