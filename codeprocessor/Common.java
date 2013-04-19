/*     */ package codeprocessor;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ class Common
/*     */ {
/*  17 */   public static final Color FG_COLOR = Color.white;
/*  18 */   public static final Color BG_COLOR = Color.black;
/*  19 */   public static final Color WPB_COLOR = Color.decode("0x333333");
/*  20 */   public static final Color TF_COLOR = Color.white;
/*  21 */   public static final Color TB_COLOR = Color.black;
/*  22 */   public static final Color HF_COLOR = Color.white;
/*  23 */   public static final Color HB_COLOR = Color.decode("0x003300");
/*  24 */   public static final Font DEFAULTFONT = new Font("SansSerif", 0, 12);
/*     */ 
/*  26 */   public static final Box createHorizontalBox(Component[] paramArrayOfComponent) { return createHorizontalBox(paramArrayOfComponent, true); }
/*     */ 
/*     */   public static final Box createHorizontalBox(Component[] paramArrayOfComponent, boolean paramBoolean) {
/*  29 */     Box localBox = Box.createHorizontalBox();
/*  30 */     if (paramArrayOfComponent.length == 0) {
/*  31 */       return localBox;
/*     */     }
/*  33 */     for (int i = 0; i < paramArrayOfComponent.length - 1; i++) {
/*  34 */       localBox.add(paramArrayOfComponent[i]);
/*  35 */       localBox.add(Box.createHorizontalStrut(5));
/*     */     }
/*     */ 
/*  38 */     localBox.add(paramArrayOfComponent[(paramArrayOfComponent.length - 1)]);
/*  39 */     if (paramBoolean)
/*  40 */       localBox.add(Box.createHorizontalGlue());
/*  41 */     return localBox;
/*     */   }
/*     */ 
/*     */   public static final JTable createJTable() {
/*  45 */     JTable localJTable = new JTable();
/*  46 */     localJTable.setBackground(TB_COLOR);
/*  47 */     localJTable.setForeground(TF_COLOR);
/*  48 */     localJTable.setSelectionBackground(HB_COLOR);
/*  49 */     localJTable.setSelectionForeground(HF_COLOR);
/*  50 */     localJTable.setShowGrid(false);
/*  51 */     return localJTable;
/*     */   }
/*     */   public static final JLabel createJLabel(String paramString) {
/*  54 */     return createJLabel(paramString, DEFAULTFONT);
/*     */   }
/*     */   public static final JLabel createJLabel(String paramString, Font paramFont) {
/*  57 */     return createJLabel(paramString, null, 2, paramFont);
/*     */   }
/*     */   public static final JLabel createJLabel(String paramString, Dimension paramDimension) {
/*  60 */     return createJLabel(paramString, paramDimension, 2, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JLabel createJLabel(String paramString, Dimension paramDimension, int paramInt)
/*     */   {
/*  66 */     return createJLabel(paramString, paramDimension, paramInt, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JLabel createJLabel(String paramString, Dimension paramDimension, int paramInt, Font paramFont)
/*     */   {
/*  73 */     JLabel localJLabel = new JLabel(paramString);
/*  74 */     localJLabel.setForeground(FG_COLOR);
/*  75 */     localJLabel.setBackground(WPB_COLOR);
/*  76 */     localJLabel.setFont(paramFont);
/*  77 */     localJLabel.setHorizontalAlignment(paramInt);
/*  78 */     if (paramDimension != null) {
/*  79 */       localJLabel.setMinimumSize(paramDimension);
/*  80 */       localJLabel.setPreferredSize(paramDimension);
/*  81 */       localJLabel.setMaximumSize(paramDimension);
/*     */     }
/*  83 */     return localJLabel;
/*     */   }
/*     */   public static final JTextField createJTextField(int paramInt, Dimension paramDimension) {
/*  86 */     return createJTextField(paramInt, paramDimension, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JTextField createJTextField(int paramInt, Dimension paramDimension, Font paramFont)
/*     */   {
/*  92 */     JTextField localJTextField = new JTextField(paramInt);
/*  93 */     localJTextField.setForeground(FG_COLOR);
/*  94 */     localJTextField.setBackground(BG_COLOR);
/*  95 */     localJTextField.setCaretColor(FG_COLOR);
/*  96 */     localJTextField.setFont(paramFont);
/*  97 */     localJTextField.setBorder(BorderFactory.createLineBorder(FG_COLOR, 1));
/*  98 */     localJTextField.setMaximumSize(paramDimension);
/*  99 */     localJTextField.addFocusListener(new SelectAll(localJTextField));
/* 100 */     return localJTextField;
/*     */   }
/*     */   public static final JScrollPane createJScrollPane(Component paramComponent) {
/* 103 */     return createJScrollPane(paramComponent, null, null);
/*     */   }
/*     */ 
/*     */   public static final JScrollPane createJScrollPane(Component paramComponent, Dimension paramDimension) {
/* 107 */     return createJScrollPane(paramComponent, paramDimension, null);
/*     */   }
/*     */ 
/*     */   public static final JScrollPane createJScrollPane(Component paramComponent, Dimension paramDimension, Border paramBorder)
/*     */   {
/* 112 */     JScrollPane localJScrollPane = new JScrollPane(paramComponent);
/* 113 */     localJScrollPane.setBackground(WPB_COLOR);
/* 114 */     localJScrollPane.getViewport().setBackground(WPB_COLOR);
/* 115 */     if (paramDimension != null) localJScrollPane.getViewport().setPreferredSize(paramDimension);
/* 116 */     if (paramBorder != null) localJScrollPane.setBorder(paramBorder);
/*     */ 
/* 118 */     return localJScrollPane;
/*     */   }
/*     */ 
/*     */   public static final void setDefaultAttributes(Container paramContainer) {
/* 122 */     setDefaultAttributes(paramContainer, new BorderLayout());
/*     */   }
/*     */ 
/*     */   public static final void setDefaultAttributes(Container paramContainer, LayoutManager paramLayoutManager)
/*     */   {
/* 127 */     paramContainer.setLayout(paramLayoutManager);
/* 128 */     paramContainer.setBackground(WPB_COLOR);
/*     */   }
/*     */ 
/*     */   public static final JButton createJButton(String paramString)
/*     */   {
/* 140 */     return createJButton(paramString, null, DEFAULTFONT);
/*     */   }
/*     */   public static final JButton createJButton(String paramString, Dimension paramDimension) {
/* 143 */     return createJButton(paramString, paramDimension, DEFAULTFONT);
/*     */   }
/*     */   public static final JButton createJButton(String paramString, Font paramFont) {
/* 146 */     return createJButton(paramString, null, paramFont);
/*     */   }
/*     */ 
/*     */   public static final JButton createJButton(String paramString, Dimension paramDimension, Font paramFont)
/*     */   {
/* 152 */     JButton localJButton = new JButton(paramString);
/* 153 */     localJButton.setFont(paramFont);
/* 154 */     if (paramDimension != null) {
/* 155 */       localJButton.setMinimumSize(paramDimension);
/* 156 */       localJButton.setPreferredSize(paramDimension);
/* 157 */       localJButton.setMaximumSize(paramDimension);
/*     */     }
/* 159 */     return localJButton;
/*     */   }
/*     */   public static void showMessage(String paramString1, String paramString2, Component paramComponent) {
/* 162 */     JOptionPane.showMessageDialog(paramComponent, paramString2, paramString1, 1);
/*     */   }
/*     */ 
/*     */   public static boolean confirm(String paramString1, String paramString2, Component paramComponent)
/*     */   {
/* 169 */     int i = JOptionPane.showConfirmDialog(paramComponent, paramString2, paramString1, 0, 2);
/*     */ 
/* 176 */     if (i == 0) {
/* 177 */       return true;
/*     */     }
/* 179 */     return false;
/*     */   }
/*     */   public static String input(String paramString1, String paramString2, Component paramComponent) {
/* 182 */     String str = JOptionPane.showInputDialog(paramComponent, paramString2, paramString1, 3);
/*     */ 
/* 184 */     return str;
/*     */   }
/*     */ 
/*     */   private static class SelectAll extends FocusAdapter
/*     */   {
/*     */     JTextComponent parent;
/*     */ 
/*     */     public SelectAll(JTextComponent paramJTextComponent)
/*     */     {
/* 133 */       this.parent = paramJTextComponent;
/*     */     }
/*     */     public void focusGained(FocusEvent paramFocusEvent) {
/* 136 */       this.parent.selectAll();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Game\Downloads\CodeProcessor.jar
 * Qualified Name:     codeprocessor.Common
 * JD-Core Version:    0.6.2
 */