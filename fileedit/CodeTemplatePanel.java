/*     */ package fileedit;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ public class CodeTemplatePanel extends JPanel
/*     */   implements ItemListener, DocumentListener, ConfigurationInterface
/*     */ {
/*     */   public static final String CPP = "C++";
/*     */   public static final String CSHARP = "C#";
/*     */   public static final String JAVA = "Java";
/*     */   private final Preferences pref;
/*  22 */   private JComboBox language = Common.createJComboBox(new String[] { "C++", "Java", "C#" });
/*  23 */   private JLabel languageLabel = Common.createJLabel("Language: ");
/*  24 */   private JLabel extensionLabel = Common.createJLabel("Extension: ");
/*  25 */   private JTextField extension = Common.createJTextField(5, new Dimension(150, 20));
/*  26 */   private JTextArea template = Common.createJTextArea("");
/*  27 */   private boolean savePending = false;
/*     */   private String CPPTemplate;
/*     */   private String CPPExtension;
/*     */   private String CSHARPTemplate;
/*     */   private String CSHARPExtension;
/*     */   private String JAVATemplate;
/*     */   private String JAVAExtension;
/*  31 */   private boolean initializing = true;
/*     */ 
/*     */   public CodeTemplatePanel(Preferences pref)
/*     */   {
/*  35 */     this.pref = pref;
/*     */ 
/*  38 */     this.JAVATemplate = pref.getJAVATemplate();
/*  39 */     this.CPPTemplate = pref.getCPPTemplate();
/*  40 */     this.CSHARPTemplate = pref.getCSHARPTemplate();
/*  41 */     this.JAVAExtension = pref.getJAVAExtension();
/*  42 */     this.CPPExtension = pref.getCPPExtension();
/*  43 */     this.CSHARPExtension = pref.getCSHARPExtension();
/*     */ 
/*  46 */     Common.setDefaultAttributes(this);
/*     */ 
/*  49 */     Box lang = Common.createHorizontalBox(new Component[] { this.languageLabel, this.language, Box.createHorizontalGlue(), this.extensionLabel, this.extension });
/*     */ 
/*  51 */     JScrollPane scroll = Common.createJScrollPane(this.template);
/*  52 */     this.template.getDocument().addDocumentListener(this);
/*  53 */     this.extension.getDocument().addDocumentListener(this);
/*     */ 
/*  56 */     add(lang, "North");
/*  57 */     add(scroll, "Center");
/*     */ 
/*  60 */     this.language.addItemListener(this);
/*     */ 
/*  63 */     this.template.setText(this.JAVATemplate);
/*  64 */     this.extension.setText(this.JAVAExtension);
/*  65 */     this.language.setSelectedItem("Java");
/*     */ 
/*  68 */     this.savePending = false;
/*     */ 
/*  71 */     this.initializing = false;
/*     */   }
/*     */ 
/*     */   public void itemStateChanged(ItemEvent e)
/*     */   {
/*  77 */     if (this.initializing) return;
/*     */ 
/*  80 */     String templateText = this.template.getText();
/*  81 */     if (templateText == null) templateText = "";
/*     */ 
/*  84 */     String templateExt = this.extension.getText();
/*  85 */     if (templateExt == null) templateExt = "";
/*  86 */     String lang = (String)e.getItem();
/*  87 */     boolean isSelected = e.getStateChange() == 1;
/*     */ 
/*  89 */     if (isSelected) {
/*  90 */       if (lang.equals("Java")) {
/*  91 */         this.template.setText(this.JAVATemplate);
/*  92 */         this.extension.setText(this.JAVAExtension);
/*  93 */       } else if (lang.equals("C++")) {
/*  94 */         this.template.setText(this.CPPTemplate);
/*  95 */         this.extension.setText(this.CPPExtension);
/*     */       } else {
/*  97 */         this.template.setText(this.CSHARPTemplate);
/*  98 */         this.extension.setText(this.CSHARPExtension);
/*     */       }
/*     */     }
/* 101 */     else if (lang.equals("Java")) {
/* 102 */       this.JAVATemplate = templateText;
/* 103 */       this.JAVAExtension = templateExt;
/* 104 */     } else if (lang.equals("C++")) {
/* 105 */       this.CPPTemplate = templateText;
/* 106 */       this.CPPExtension = templateExt;
/*     */     } else {
/* 108 */       this.CSHARPTemplate = templateText;
/* 109 */       this.CSHARPExtension = templateExt;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void changedUpdate(DocumentEvent e)
/*     */   {
/* 116 */     this.savePending = true; } 
/* 117 */   public void insertUpdate(DocumentEvent e) { this.savePending = true; } 
/* 118 */   public void removeUpdate(DocumentEvent e) { this.savePending = true; } 
/*     */   public String getTabTitle() {
/* 120 */     return "Code Template"; } 
/* 121 */   public Icon getTabIcon() { return null; } 
/* 122 */   public String getTabToolTip() { return "Specify code templates"; } 
/* 123 */   public boolean isSavePending() { return this.savePending; } 
/* 124 */   public void resetSavePending() { this.savePending = false; }
/*     */ 
/*     */   public boolean savePreferences()
/*     */   {
/* 128 */     String lang = (String)this.language.getSelectedItem();
/* 129 */     if (lang.equals("Java")) {
/* 130 */       this.JAVATemplate = this.template.getText();
/* 131 */       this.JAVAExtension = this.extension.getText();
/* 132 */     } else if (lang.equals("C++")) {
/* 133 */       this.CPPTemplate = this.template.getText();
/* 134 */       this.CPPExtension = this.extension.getText();
/*     */     } else {
/* 136 */       this.CSHARPTemplate = this.template.getText();
/* 137 */       this.CSHARPExtension = this.extension.getText();
/*     */     }
/*     */ 
/* 140 */     this.pref.setJAVATemplate(this.JAVATemplate);
/* 141 */     this.pref.setCPPTemplate(this.CPPTemplate);
/* 142 */     this.pref.setCSHARPTemplate(this.CSHARPTemplate);
/* 143 */     this.pref.setJAVAExtension(this.JAVAExtension);
/* 144 */     this.pref.setCPPExtension(this.CPPExtension);
/* 145 */     this.pref.setCSHARPExtension(this.CSHARPExtension);
/* 146 */     return true;
/*     */   }
/*     */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.CodeTemplatePanel
 * JD-Core Version:    0.6.2
 */