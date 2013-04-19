/*     */ package fileedit;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JCheckBoxMenuItem;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.tree.DefaultTreeCellRenderer;
/*     */ import javax.swing.tree.TreeModel;
/*     */ 
/*     */ public class Common
/*     */ {
/*  25 */   public static final Color FG_COLOR = Color.white;
/*  26 */   public static final Color BG_COLOR = Color.black;
/*  27 */   public static final Color WPB_COLOR = Color.decode("0x333333");
/*  28 */   public static final Color TF_COLOR = Color.white;
/*  29 */   public static final Color TB_COLOR = Color.black;
/*  30 */   public static final Color HF_COLOR = Color.white;
/*  31 */   public static final Color HB_COLOR = Color.decode("0x003300");
/*  32 */   public static final Color THF_COLOR = Color.decode("0xCCFF99");
/*  33 */   public static final Color THB_COLOR = Color.black;
/*  34 */   public static final Color PT_COLOR = Color.decode("0xCCFF99");
/*  35 */   public static final Color PB_COLOR = Color.black;
/*     */   public static final int CONTESTCONSTANTSJAVA = 1;
/*     */   public static final int CONTESTCONSTANTSCPP = 3;
/*     */   public static final int CONTESTCONSTANTSCSHARP = 4;
/*  41 */   public static final Font DEFAULTFONT = new Font("SansSerif", 0, 12);
/*     */ 
/*     */   public static final Box createHorizontalBox(Component[] a) {
/*  44 */     return createHorizontalBox(a, true);
/*     */   }
/*     */ 
/*     */   public static final Box createHorizontalBox(Component[] a, boolean endGlue) {
/*  48 */     Box temp = Box.createHorizontalBox();
/*  49 */     if (a.length == 0) return temp;
/*     */ 
/*  52 */     for (int x = 0; x < a.length - 1; x++) {
/*  53 */       temp.add(a[x]);
/*  54 */       temp.add(Box.createHorizontalStrut(5));
/*     */     }
/*     */ 
/*  58 */     temp.add(a[(a.length - 1)]);
/*  59 */     if (endGlue) temp.add(Box.createHorizontalGlue());
/*     */ 
/*  61 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final JLabel createJLabel(String text) {
/*  65 */     return createJLabel(text, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JLabel createJLabel(String text, Font font) {
/*  69 */     return createJLabel(text, null, 2, font);
/*     */   }
/*     */ 
/*     */   public static final JLabel createJLabel(String text, Dimension size) {
/*  73 */     return createJLabel(text, size, 2, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JLabel createJLabel(String text, Dimension size, int alignment) {
/*  77 */     return createJLabel(text, size, alignment, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JLabel createJLabel(String text, Dimension size, int alignment, Font font)
/*     */   {
/*  82 */     JLabel temp = new JLabel(text);
/*  83 */     temp.setForeground(FG_COLOR);
/*  84 */     temp.setBackground(WPB_COLOR);
/*  85 */     temp.setFont(font);
/*  86 */     temp.setHorizontalAlignment(alignment);
/*     */ 
/*  88 */     if (size != null) {
/*  89 */       temp.setMinimumSize(size);
/*  90 */       temp.setPreferredSize(size);
/*  91 */       temp.setMaximumSize(size);
/*     */     }
/*     */ 
/*  94 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final JCheckBox createJCheckBox(String text) {
/*  98 */     return createJCheckBox(text, false, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JCheckBox createJCheckBox(String text, boolean selected, Font font) {
/* 102 */     JCheckBox temp = new JCheckBox(text);
/* 103 */     temp.setSelected(selected);
/* 104 */     temp.setFont(font);
/* 105 */     temp.setForeground(FG_COLOR);
/* 106 */     temp.setBackground(WPB_COLOR);
/* 107 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final JCheckBoxMenuItem createJCheckBoxMenuItem(String text, boolean selected) {
/* 111 */     return createJCheckBoxMenuItem(text, selected, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JCheckBoxMenuItem createJCheckBoxMenuItem(String text, boolean selected, Font font) {
/* 115 */     JCheckBoxMenuItem temp = new JCheckBoxMenuItem(text, selected);
/* 116 */     temp.setFont(font);
/* 117 */     temp.setForeground(FG_COLOR);
/* 118 */     temp.setBackground(WPB_COLOR);
/* 119 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final JPopupMenu createJPopupMenu(JMenuItem[] items) {
/* 123 */     JPopupMenu temp = new JPopupMenu();
/* 124 */     for (int x = 0; x < items.length; x++) temp.add(items[x]);
/* 125 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final JTextArea createJTextArea(String text) {
/* 129 */     return createJTextArea(text, null, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JTextArea createJTextArea(String text, boolean selectAll) {
/* 133 */     return createJTextArea(text, null, DEFAULTFONT, selectAll);
/*     */   }
/*     */ 
/*     */   public static final JTextArea createJTextArea(String text, Dimension size) {
/* 137 */     return createJTextArea(text, size, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JTextArea createJTextArea(String text, Font font) {
/* 141 */     return createJTextArea(text, null, font);
/*     */   }
/*     */ 
/*     */   public static final JTextArea createJTextArea(String text, Dimension size, Font font) {
/* 145 */     return createJTextArea(text, size, font, true);
/*     */   }
/*     */   public static final JTextArea createJTextArea(String text, Dimension size, Font font, boolean selectAll) {
/* 148 */     JTextArea temp = new JTextArea(text);
/* 149 */     temp.setForeground(FG_COLOR);
/* 150 */     temp.setBackground(BG_COLOR);
/* 151 */     temp.setCaretColor(FG_COLOR);
/* 152 */     temp.setFont(font);
/* 153 */     temp.setBorder(BorderFactory.createLineBorder(FG_COLOR, 1));
/* 154 */     if (size != null) temp.setPreferredSize(size);
/*     */ 
/* 156 */     if (selectAll) temp.addFocusListener(new SelectAll(temp));
/* 157 */     temp.setLineWrap(false);
/* 158 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final JTextField createJTextField(int size, Dimension max) {
/* 162 */     return createJTextField(size, max, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JTextField createJTextField(int size, Dimension max, Font font) {
/* 166 */     JTextField temp = new JTextField(size);
/* 167 */     temp.setForeground(FG_COLOR);
/* 168 */     temp.setBackground(BG_COLOR);
/* 169 */     temp.setCaretColor(FG_COLOR);
/* 170 */     temp.setFont(font);
/* 171 */     temp.setBorder(BorderFactory.createLineBorder(FG_COLOR, 1));
/* 172 */     temp.setMaximumSize(max);
/* 173 */     temp.addFocusListener(new SelectAll(temp));
/* 174 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final JTextField createFixedJTextField(int size, Dimension max) {
/* 178 */     return createFixedJTextField(size, max, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JTextField createFixedJTextField(int size, Dimension max, Font font) {
/* 182 */     JTextField temp = new JTextField(size);
/* 183 */     temp.setForeground(FG_COLOR);
/* 184 */     temp.setBackground(BG_COLOR);
/* 185 */     temp.setCaretColor(FG_COLOR);
/* 186 */     temp.setFont(font);
/* 187 */     temp.setBorder(BorderFactory.createLineBorder(FG_COLOR, 1));
/* 188 */     temp.setMaximumSize(max);
/* 189 */     PlainDocumentLimited doc = new PlainDocumentLimited(size);
/* 190 */     temp.setDocument(doc);
/* 191 */     temp.addFocusListener(new SelectAll(temp));
/* 192 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final JButton createJButton(String text) {
/* 196 */     return createJButton(text, null, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JButton createJButton(String text, Dimension size) {
/* 200 */     return createJButton(text, size, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JButton createJButton(String text, Font font) {
/* 204 */     return createJButton(text, null, font);
/*     */   }
/*     */ 
/*     */   public static final JButton createJButton(String text, Dimension size, Font font) {
/* 208 */     JButton temp = new JButton(text);
/* 209 */     temp.setFont(font);
/*     */ 
/* 211 */     if (size != null) {
/* 212 */       temp.setMinimumSize(size);
/* 213 */       temp.setPreferredSize(size);
/* 214 */       temp.setMaximumSize(size);
/*     */     }
/* 216 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final JComboBox createJComboBox(String[] list)
/*     */   {
/* 221 */     return createJComboBox(list, DEFAULTFONT);
/*     */   }
/*     */ 
/*     */   public static final JComboBox createJComboBox(String[] list, Font font) {
/* 225 */     JComboBox temp = createComboBox();
/* 226 */     temp.setFont(font);
/* 227 */     for (int x = 0; x < list.length; x++) temp.addItem(list[x]);
/* 228 */     return temp;
/*     */   }
/*     */ 
/*     */   public static JComboBox createComboBox() {
/* 232 */     JComboBox jcb = new JComboBox();
/* 233 */     jcb.setBackground(WPB_COLOR);
/* 234 */     jcb.setForeground(Color.white);
/*     */ 
/* 236 */     jcb.setRenderer(new ContestListCellRenderer());
/*     */ 
/* 238 */     return jcb;
/*     */   }
/*     */ 
/*     */   public static final JScrollPane createJScrollPane(Component a) {
/* 242 */     return createJScrollPane(a, null, null);
/*     */   }
/*     */ 
/*     */   public static final JScrollPane createJScrollPane(Component a, Dimension size) {
/* 246 */     return createJScrollPane(a, size, null);
/*     */   }
/*     */ 
/*     */   public static final JScrollPane createJScrollPane(Component a, Dimension size, Border border)
/*     */   {
/* 251 */     JScrollPane temp = new JScrollPane(a);
/* 252 */     temp.setBackground(WPB_COLOR);
/* 253 */     temp.getViewport().setBackground(WPB_COLOR);
/* 254 */     if (size != null) temp.getViewport().setPreferredSize(size);
/* 255 */     if (border != null) temp.setBorder(border);
/*     */ 
/* 257 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final void setDefaultAttributes(Container panel) {
/* 261 */     setDefaultAttributes(panel, new BorderLayout());
/*     */   }
/*     */ 
/*     */   public static final void setDefaultAttributes(Container panel, LayoutManager layout) {
/* 265 */     panel.setLayout(layout);
/* 266 */     panel.setBackground(WPB_COLOR);
/*     */   }
/*     */ 
/*     */   public static final JTree createJTree(TreeModel model)
/*     */   {
/* 271 */     JTree temp = new JTree(model);
/* 272 */     temp.setBackground(BG_COLOR);
/* 273 */     temp.setForeground(FG_COLOR);
/* 274 */     temp.setRootVisible(false);
/*     */ 
/* 277 */     PopsTreeCellRenderer rend = new PopsTreeCellRenderer();
/* 278 */     rend.setTextNonSelectionColor(FG_COLOR);
/* 279 */     rend.setTextSelectionColor(FG_COLOR);
/* 280 */     rend.setBackgroundSelectionColor(HB_COLOR);
/* 281 */     rend.setBackgroundNonSelectionColor(BG_COLOR);
/* 282 */     rend.setBorderSelectionColor(HB_COLOR);
/* 283 */     rend.setBackground(BG_COLOR);
/*     */ 
/* 285 */     temp.setCellRenderer(rend);
/* 286 */     temp.setDoubleBuffered(true);
/* 287 */     return temp;
/*     */   }
/*     */ 
/*     */   public static final JList createJList(Object[] list)
/*     */   {
/* 292 */     JList temp = new JList(list);
/* 293 */     temp.setBackground(BG_COLOR);
/* 294 */     temp.setForeground(FG_COLOR);
/* 295 */     temp.setSelectionBackground(HB_COLOR);
/* 296 */     temp.setSelectionForeground(HF_COLOR);
/*     */ 
/* 298 */     return temp;
/*     */   }
/*     */ 
/*     */   public static Point adjustWindowLocation(Point location)
/*     */   {
/* 326 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*     */ 
/* 329 */     if (location.x < 0) location.x = 0;
/* 330 */     if (location.x > screenSize.width - 125) location.x = (screenSize.width - 125);
/*     */ 
/* 332 */     if (location.y < 0) location.y = 0;
/* 333 */     if (location.y > screenSize.height - 125) location.y = (screenSize.height - 125);
/*     */ 
/* 336 */     return location;
/*     */   }
/*     */ 
/*     */   public static Dimension adjustWindowSize(Point location, Dimension size)
/*     */   {
/* 344 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*     */ 
/* 347 */     if (size.width < 0) size.width = 600;
/* 348 */     if (size.width + location.x > screenSize.width) screenSize.width -= location.x;
/*     */ 
/* 350 */     if (size.height < 0) size.height = 400;
/* 351 */     if (size.height + location.y > screenSize.height) screenSize.height -= location.y;
/*     */ 
/* 353 */     return size;
/*     */   }
/*     */ 
/*     */   public static TitledBorder getTitledBorder(String title) {
/* 357 */     Border border = new RoundBorder(PB_COLOR, 5, true);
/* 358 */     MyTitledBorder tb = new MyTitledBorder(border, title, 1, 1);
/* 359 */     tb.setTitleColor(PT_COLOR);
/* 360 */     return tb;
/*     */   }
/*     */ 
/*     */   public static void showMessage(String title, String msg, Component comp) {
/* 364 */     JOptionPane.showMessageDialog(comp, msg, title, 1);
/*     */   }
/*     */ 
/*     */   public static boolean confirm(String title, String msg, Component comp) {
/* 368 */     int choice = JOptionPane.showConfirmDialog(comp, msg, title, 
/* 369 */       0, 
/* 370 */       2);
/*     */ 
/* 372 */     if (choice == 0) return true;
/*     */ 
/* 374 */     return false;
/*     */   }
/*     */ 
/*     */   public static String input(String title, String msg, Component comp) {
/* 378 */     String value = JOptionPane.showInputDialog(comp, msg, title, 
/* 379 */       3);
/* 380 */     return value;
/*     */   }
/*     */ 
/*     */   private static class SelectAll extends FocusAdapter
/*     */   {
/*     */     JTextComponent parent;
/*     */ 
/*     */     public SelectAll(JTextComponent parent)
/*     */     {
/* 304 */       this.parent = parent;
/*     */     }
/*     */ 
/*     */     public void focusGained(FocusEvent e) {
/* 308 */       this.parent.selectAll();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class PopsTreeCellRenderer extends DefaultTreeCellRenderer {
/*     */     public Dimension getPreferredSize() {
/* 314 */       Dimension ret = super.getPreferredSize();
/* 315 */       if (ret != null) ret = new Dimension(ret.width + 25, ret.height);
/* 316 */       return ret;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.Common
 * JD-Core Version:    0.6.2
 */