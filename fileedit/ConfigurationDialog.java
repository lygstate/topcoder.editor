/*     */ package fileedit;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.IOException;
/*     */ import java.util.EventObject;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ 
/*     */ public class ConfigurationDialog extends JDialog
/*     */   implements ActionListener
/*     */ {
/*  17 */   private Preferences pref = new Preferences();
/*  18 */   private JTabbedPane tab = new JTabbedPane();
/*  19 */   private JButton saveButton = new JButton("Save");
/*  20 */   private JButton closeButton = new JButton("Close");
/*     */ 
/*  22 */   private ConfigurationInterface[] config = { new FileEditorConfiguration(this.pref), new CodeTemplatePanel(this.pref) };
/*  23 */   private WindowHandler windowHandler = new WindowHandler();
/*     */ 
/*     */   public ConfigurationDialog()
/*     */   {
/*  27 */     super(null, "FileEdit Configuration", true);
/*     */ 
/*  29 */     setSize(new Dimension(600, 400));
/*     */ 
/*  32 */     Container contentPane = getContentPane();
/*  33 */     contentPane.setLayout(new BoxLayout(contentPane, 0));
/*  34 */     contentPane.setForeground(Common.FG_COLOR);
/*  35 */     contentPane.setBackground(Common.WPB_COLOR);
/*     */ 
/*  37 */     this.tab.setForeground(Common.FG_COLOR);
/*  38 */     this.tab.setBackground(Common.WPB_COLOR);
/*     */ 
/*  41 */     for (int x = 0; x < this.config.length; x++) {
/*  42 */       this.tab.addTab(this.config[x].getTabTitle(), this.config[x].getTabIcon(), (Component)this.config[x], this.config[x].getTabToolTip());
/*     */     }
/*     */ 
/*  46 */     Box buttonPanel = Box.createHorizontalBox();
/*  47 */     buttonPanel.add(Box.createHorizontalGlue());
/*  48 */     buttonPanel.add(this.saveButton);
/*  49 */     buttonPanel.add(Box.createHorizontalStrut(10));
/*  50 */     buttonPanel.add(this.closeButton);
/*     */ 
/*  53 */     Box mainPanel = Box.createVerticalBox();
/*  54 */     mainPanel.add(Box.createVerticalStrut(10));
/*  55 */     mainPanel.add(this.tab);
/*  56 */     mainPanel.add(Box.createVerticalStrut(10));
/*  57 */     mainPanel.add(buttonPanel);
/*  58 */     mainPanel.add(Box.createVerticalStrut(10));
/*     */ 
/*  60 */     contentPane.add(Box.createHorizontalStrut(10));
/*  61 */     contentPane.add(mainPanel);
/*  62 */     contentPane.add(Box.createHorizontalStrut(10));
/*     */ 
/*  65 */     this.saveButton.addActionListener(this);
/*  66 */     this.closeButton.addActionListener(this);
/*     */ 
/*  69 */     setDefaultCloseOperation(0);
/*  70 */     addWindowListener(this.windowHandler);
/*     */ 
/*  72 */     pack();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e) {
/*  76 */     Object src = e.getSource();
/*  77 */     if (src == this.saveButton)
/*  78 */       save();
/*  79 */     else if (src == this.closeButton)
/*  80 */       this.windowHandler.windowClosing(new WindowEvent(this, 201));
/*     */   }
/*     */ 
/*     */   public boolean save()
/*     */   {
/*  87 */     for (int x = 0; x < this.config.length; x++) {
/*  88 */       if (!this.config[x].savePreferences()) {
/*  89 */         this.tab.setSelectedIndex(x);
/*  90 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  96 */       this.pref.save();
/*  97 */       for (int x = 0; x < this.config.length; x++) this.config[x].resetSavePending();
/*  98 */       Common.showMessage("Save", "Preferences were saved successfully", null);
/*  99 */       return true;
/*     */     } catch (IOException e) {
/* 101 */       JOptionPane.showMessageDialog(null, e.toString(), "Error saving preferences", 0);
/* 102 */     }return false;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 130 */     ConfigurationDialog ff = new ConfigurationDialog();
/* 131 */     ff.show();
/*     */   }
/*     */ 
/*     */   private class WindowHandler extends WindowAdapter
/*     */   {
/*     */     WindowHandler()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void windowClosing(WindowEvent e)
/*     */     {
/* 111 */       boolean savePending = false;
/* 112 */       for (int x = 0; x < ConfigurationDialog.this.config.length; x++) if (ConfigurationDialog.this.config[x].isSavePending()) { savePending = true; break;
/*     */         }
/*     */ 
/* 115 */       if (savePending)
/*     */       {
/* 118 */         if (Common.confirm("Save Pending", "Changes are pending.  Do you want to save before closing?", null))
/*     */         {
/* 120 */           if (!ConfigurationDialog.this.save()) return;
/*     */         }
/*     */       }
/*     */ 
/* 124 */       ConfigurationDialog.this.dispose();
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\CI\tools\IDE\TopCoder\FileEdit.jar
 * Qualified Name:     fileedit.ConfigurationDialog
 * JD-Core Version:    0.6.2
 */