/*     */ package fileedit;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.EventObject;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public class FileEditorConfiguration extends JPanel
/*     */   implements ActionListener, DocumentListener, ConfigurationInterface
/*     */ {
/*     */   Preferences pref;
/*  21 */   JLabel dirNameLabel = Common.createJLabel("Enter directory to read/write problems to:");
/*  22 */   JTextField dirNameField = Common.createJTextField(40, new Dimension(400, 21));
/*  23 */   JCheckBox backup = Common.createJCheckBox("Backup existing file then overwrite (uncheck if you want to keep existing file)");
/*  24 */   JLabel fileNameLabel = Common.createJLabel("Enter filename to use (no extension):");
/*  25 */   JTextField fileNameField = Common.createJTextField(40, new Dimension(400, 21));
/*  26 */   JCheckBox htmlDesc = Common.createJCheckBox("Write the problem description using HTML");
/*  27 */   JCheckBox useLineComments = Common.createJCheckBox("Use Line Comments for Problem Description");
/*  28 */   JCheckBox overrideFileNameField = Common.createJCheckBox("Make filename equal to classname");
/*  29 */   JCheckBox provideBreakField = Common.createJCheckBox("Force Breaks at");
/*  30 */   JTextField breakAtField = Common.createJTextField(4, new Dimension(75, 21));
/*  31 */   JLabel beginCutLabel = Common.createJLabel("$BEGINCUT$ ");
/*  32 */   JTextField beginCutField = Common.createJTextField(40, new Dimension(400, 21));
/*  33 */   JLabel endCutLabel = Common.createJLabel("$ENDCUT$ ");
/*  34 */   JTextField endCutField = Common.createJTextField(40, new Dimension(400, 21));
/*     */ 
/*  36 */   JCheckBox problemDescFileWrite = Common.createJCheckBox("Write Problem Description to separate file");
/*  37 */   JLabel problemDescFileLabel = Common.createJLabel("File Extension: ");
/*  38 */   JTextField problemDescFileField = Common.createJTextField(4, new Dimension(75, 21));
/*  39 */   JLabel sigFileLabel = Common.createJLabel("Enter signature filename: ");
/*  40 */   JTextField sigFileField = Common.createJTextField(40, new Dimension(400, 21));
/*     */ 
/*  43 */   boolean savePending = false;
/*     */ 
/*     */   public FileEditorConfiguration(Preferences pref)
/*     */   {
/*  47 */     this.pref = pref;
/*  48 */     Common.setDefaultAttributes(this, new BorderLayout());
/*     */ 
/*  51 */     setBackground(Common.WPB_COLOR);
/*     */ 
/*  56 */     String dirName = pref.getDirectoryName();
/*  57 */     this.dirNameField.setText(dirName);
/*     */ 
/*  60 */     this.backup.setSelected(pref.isBackup());
/*     */ 
/*  63 */     this.overrideFileNameField.setSelected(!pref.isOverrideFileName());
/*     */ 
/*  66 */     this.useLineComments.setSelected(pref.isLineComments());
/*     */ 
/*  69 */     this.problemDescFileWrite.setSelected(pref.isWriteProblemDescFile());
/*     */ 
/*  72 */     String probDescExt = pref.getProblemDescExtension();
/*  73 */     this.problemDescFileField.setText(probDescExt);
/*  74 */     this.problemDescFileLabel.setEnabled(this.problemDescFileWrite.isSelected());
/*  75 */     this.problemDescFileField.setEnabled(this.problemDescFileWrite.isSelected());
/*     */ 
/*  78 */     String fileName = pref.getFileName();
/*  79 */     this.fileNameLabel.setEnabled(!this.overrideFileNameField.isSelected());
/*  80 */     this.fileNameField.setText(fileName);
/*  81 */     this.fileNameField.setEnabled(!this.overrideFileNameField.isSelected());
/*     */ 
/*  84 */     String sigFileName = pref.getSignatureFileName();
/*  85 */     this.sigFileField.setText(sigFileName);
/*     */ 
/*  88 */     this.provideBreakField.setSelected(pref.isProvideBreaks());
/*  89 */     this.breakAtField.setText(String.valueOf(pref.getBreakAt()));
/*  90 */     this.breakAtField.setEnabled(this.provideBreakField.isSelected());
/*     */ 
/*  93 */     String beginCutString = pref.getBeginCut();
/*  94 */     this.beginCutField.setText(beginCutString);
/*     */ 
/*  97 */     String endCutString = pref.getEndCut();
/*  98 */     this.endCutField.setText(endCutString);
/*     */ 
/* 101 */     this.useLineComments.setSelected(pref.isLineComments());
/* 102 */     if (pref.isWriteProblemDescFile()) {
/* 103 */       this.useLineComments.setEnabled(false);
/* 104 */       this.useLineComments.setSelected(false);
/*     */     }
/*     */ 
/* 108 */     this.htmlDesc.setSelected(pref.isHTMLDesc());
/* 109 */     if (this.htmlDesc.isSelected()) {
/* 110 */       this.useLineComments.setEnabled(false);
/*     */ 
/* 112 */       this.provideBreakField.setEnabled(false);
/* 113 */       this.breakAtField.setEnabled(false);
/*     */ 
/* 115 */       this.problemDescFileWrite.setEnabled(true);
/* 116 */       this.problemDescFileWrite.setSelected(true);
/*     */ 
/* 118 */       this.problemDescFileLabel.setEnabled(true);
/* 119 */       this.problemDescFileField.setEnabled(true);
/*     */     }
/*     */ 
/* 123 */     Box dirNamePane = Common.createHorizontalBox(new Component[] { this.dirNameLabel, this.dirNameField }, true);
/*     */ 
/* 126 */     Box backupPane = Common.createHorizontalBox(new Component[] { this.backup }, true);
/*     */ 
/* 129 */     Box fileNamePane = Common.createHorizontalBox(new Component[] { Box.createHorizontalStrut(20), this.fileNameLabel, this.fileNameField }, true);
/*     */ 
/* 132 */     Box sigFilePane = Common.createHorizontalBox(new Component[] { this.sigFileLabel, this.sigFileField }, true);
/*     */ 
/* 135 */     Box overridePane = Common.createHorizontalBox(new Component[] { this.overrideFileNameField }, true);
/*     */ 
/* 138 */     Box lineCommentsPane = Common.createHorizontalBox(new Component[] { this.useLineComments }, true);
/*     */ 
/* 141 */     Box htmlDescPane = Common.createHorizontalBox(new Component[] { this.htmlDesc }, true);
/*     */ 
/* 144 */     Box breakAtPane = Common.createHorizontalBox(new Component[] { this.provideBreakField, this.breakAtField }, true);
/*     */ 
/* 147 */     Box beginCutPane = Common.createHorizontalBox(new Component[] { this.beginCutLabel, this.beginCutField }, true);
/*     */ 
/* 150 */     Box endCutPane = Common.createHorizontalBox(new Component[] { this.endCutLabel, this.endCutField }, true);
/*     */ 
/* 153 */     Box probDescFileWriteBox = Common.createHorizontalBox(new Component[] { this.problemDescFileWrite }, true);
/*     */ 
/* 156 */     Box probDescFileExtBox = Common.createHorizontalBox(new Component[] { Box.createHorizontalStrut(20), this.problemDescFileLabel, this.problemDescFileField }, true);
/*     */ 
/* 159 */     Box all = Box.createVerticalBox();
/* 160 */     all.add(Box.createVerticalStrut(10));
/* 161 */     all.add(dirNamePane);
/* 162 */     all.add(Box.createVerticalStrut(5));
/* 163 */     all.add(backupPane);
/* 164 */     all.add(Box.createVerticalStrut(5));
/* 165 */     all.add(overridePane);
/* 166 */     all.add(Box.createVerticalStrut(1));
/* 167 */     all.add(fileNamePane);
/*     */ 
/* 169 */     all.add(Box.createVerticalStrut(5));
/* 170 */     all.add(htmlDescPane);
/*     */ 
/* 172 */     all.add(Box.createVerticalStrut(5));
/* 173 */     all.add(probDescFileWriteBox);
/* 174 */     all.add(Box.createVerticalStrut(1));
/* 175 */     all.add(probDescFileExtBox);
/*     */ 
/* 177 */     all.add(Box.createVerticalStrut(5));
/* 178 */     all.add(lineCommentsPane);
/* 179 */     all.add(Box.createVerticalStrut(1));
/* 180 */     all.add(breakAtPane);
/* 181 */     all.add(Box.createVerticalStrut(1));
/* 182 */     all.add(beginCutPane);
/* 183 */     all.add(Box.createVerticalStrut(1));
/* 184 */     all.add(endCutPane);
/* 185 */     all.add(Box.createVerticalStrut(5));
/* 186 */     all.add(sigFilePane);
/* 187 */     all.add(Box.createVerticalGlue());
/*     */ 
/* 190 */     add(all, "North");
/*     */ 
/* 193 */     this.dirNameField.getDocument().addDocumentListener(this);
/* 194 */     this.fileNameField.getDocument().addDocumentListener(this);
/* 195 */     this.breakAtField.getDocument().addDocumentListener(this);
/* 196 */     this.beginCutField.getDocument().addDocumentListener(this);
/* 197 */     this.endCutField.getDocument().addDocumentListener(this);
/* 198 */     this.problemDescFileField.getDocument().addDocumentListener(this);
/* 199 */     this.sigFileField.getDocument().addDocumentListener(this);
/* 200 */     this.useLineComments.addActionListener(this);
/* 201 */     this.overrideFileNameField.addActionListener(this);
/* 202 */     this.problemDescFileWrite.addActionListener(this);
/* 203 */     this.provideBreakField.addActionListener(this);
/* 204 */     this.htmlDesc.addActionListener(this);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 211 */     Object source = e.getSource();
/*     */ 
/* 213 */     this.savePending = true;
/*     */ 
/* 215 */     if (source == this.overrideFileNameField) {
/* 216 */       this.fileNameLabel.setEnabled(!this.overrideFileNameField.isSelected());
/* 217 */       this.fileNameField.setEnabled(!this.overrideFileNameField.isSelected());
/* 218 */     } else if ((source == this.provideBreakField) && (!this.htmlDesc.isSelected())) {
/* 219 */       this.breakAtField.setEnabled(this.provideBreakField.isSelected());
/* 220 */     } else if (source == this.problemDescFileWrite) {
/* 221 */       if (this.htmlDesc.isSelected()) this.problemDescFileWrite.setSelected(true);
/* 222 */       this.problemDescFileLabel.setEnabled(this.problemDescFileWrite.isSelected());
/* 223 */       this.problemDescFileField.setEnabled(this.problemDescFileWrite.isSelected());
/* 224 */       this.useLineComments.setEnabled(!this.problemDescFileWrite.isSelected());
/* 225 */       if (this.problemDescFileWrite.isSelected()) this.useLineComments.setSelected(false); 
/*     */     }
/* 226 */     else if (source == this.htmlDesc) {
/* 227 */       this.useLineComments.setEnabled(!this.htmlDesc.isSelected());
/* 228 */       this.provideBreakField.setEnabled(!this.htmlDesc.isSelected());
/* 229 */       this.breakAtField.setEnabled(!this.htmlDesc.isSelected());
/*     */ 
/* 231 */       if (this.htmlDesc.isSelected()) {
/* 232 */         this.useLineComments.setSelected(false);
/* 233 */         this.provideBreakField.setSelected(false);
/* 234 */         this.problemDescFileWrite.setEnabled(true);
/* 235 */         this.problemDescFileWrite.setSelected(true);
/*     */ 
/* 237 */         this.problemDescFileLabel.setEnabled(true);
/* 238 */         this.problemDescFileField.setEnabled(true);
/*     */       }
/* 240 */       if (this.problemDescFileWrite.isSelected()) {
/* 241 */         this.useLineComments.setEnabled(false);
/* 242 */         this.useLineComments.setSelected(false);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void changedUpdate(DocumentEvent e)
/*     */   {
/* 250 */     this.savePending = true; } 
/* 251 */   public void insertUpdate(DocumentEvent e) { this.savePending = true; } 
/* 252 */   public void removeUpdate(DocumentEvent e) { this.savePending = true; } 
/*     */   public String getTabTitle() {
/* 254 */     return "General"; } 
/* 255 */   public Icon getTabIcon() { return null; } 
/* 256 */   public String getTabToolTip() { return "General Configuration"; } 
/* 257 */   public boolean isSavePending() { return this.savePending; } 
/* 258 */   public void resetSavePending() { this.savePending = false; }
/*     */ 
/*     */   public boolean savePreferences()
/*     */   {
/* 262 */     if ((this.overrideFileNameField.isSelected()) && (this.fileNameField.getText().trim().equals(""))) {
/* 263 */       Common.showMessage("Error", "You must specify a filename", null);
/* 264 */       return false;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 270 */       breakAt = Integer.parseInt(this.breakAtField.getText());
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/*     */       int breakAt;
/* 272 */       Common.showMessage("Error", "The break at is not a number", null);
/* 273 */       return false;
/*     */     }
/*     */     int breakAt;
/* 277 */     this.pref.setDirectoryName(this.dirNameField.getText());
/* 278 */     this.pref.setFileName(this.fileNameField.getText());
/* 279 */     this.pref.setBeginCut(this.beginCutField.getText());
/* 280 */     this.pref.setEndCut(this.endCutField.getText());
/* 281 */     this.pref.setProblemDescExtension(this.problemDescFileField.getText());
/* 282 */     this.pref.setSignatureFileName(this.sigFileField.getText());
/*     */ 
/* 284 */     this.pref.setLineComments(this.useLineComments.isSelected());
/* 285 */     this.pref.setOverrideFileName(!this.overrideFileNameField.isSelected());
/* 286 */     this.pref.setProvideBreaks(this.provideBreakField.isSelected());
/* 287 */     this.pref.setWriteProblemDescFile(this.problemDescFileWrite.isSelected());
/* 288 */     this.pref.setBreakAt(breakAt);
/* 289 */     this.pref.setHTMLDesc(this.htmlDesc.isSelected());
/* 290 */     this.pref.setBackup(this.backup.isSelected());
/*     */ 
/* 292 */     return true;
/*     */   }
/*     */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.FileEditorConfiguration
 * JD-Core Version:    0.6.2
 */