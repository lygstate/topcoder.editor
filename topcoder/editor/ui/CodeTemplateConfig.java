package topcoder.editor.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import topcoder.editor.Preferences;

public class CodeTemplateConfig extends JPanel implements ItemListener,
		ConfigurationInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6686485488532379662L;
	public static final String CPP = "C++";
	public static final String CSHARP = "C#";
	public static final String JAVA = "Java";

	private JLabel languageLabel = Common.createJLabel("Language:",
			new Dimension(80, 20));
	private JComboBox language = Common.createJComboBox(new String[] { "C++",
			"Java", "C#" });

	private JLabel extensionLabel = Common.createJLabel("Extension:",
			new Dimension(80, 20));
	private JTextField extension = Common.createJTextField(5, new Dimension(
			150, 20));

	private JLabel identLabel = Common.createJLabel("Ident:", new Dimension(80,
			20));
	private JComboBox indent = Common.createJComboBox(new String[] { "Space",
			"Tab" });

	private JLabel tabSizeLabel = Common.createJLabel("Tab Size:",
			new Dimension(80, 20));
	private JTextField tabSizeField = Common.createJTextField(5, new Dimension(
			150, 20));

	private JTextArea template = Common.createJTextArea("");

	private String prefCPPTemplate;
	private String prefCPPExtension;
	private String prefCSHARPTemplate;
	private String prefCSHARPExtension;
	private String prefJAVATemplate;
	private String prefJAVAExtension;

	private String prefIndentType;
	private int prefTabSize;

	public CodeTemplateConfig(Preferences pref) {
		Common.setDefaultAttributes(this);

		Box lang = Common.createHorizontalBox(new Component[] {
				this.languageLabel, this.language, Box.createHorizontalGlue(),
				this.extensionLabel, this.extension });

		Box indent = Common.createHorizontalBox(new Component[] {
				this.identLabel, this.indent, Box.createHorizontalGlue(),
				this.tabSizeLabel, this.tabSizeField });

		Box upperBoxs = Common
				.createVerticalBox(new Component[] { lang, indent });

		JScrollPane scroll = Common.createJScrollPane(this.template);

		/* Filter integer document for TabSize */
		((PlainDocument) this.tabSizeField.getDocument())
				.setDocumentFilter(new IntFilter());

		add(upperBoxs, "North");
		add(scroll, "Center");

		this.language.addItemListener(this);
		this.indent.addItemListener(this);
	}

	/*
	 * Switch to one of languages C/C++,Java, C#, load the extension and
	 * template content from variable to the UI controls
	 */
	private void loadPrefLangToUI() {
		String lang = (String) language.getSelectedItem();
		if (lang.equals("Java")) {
			this.template.setText(this.prefJAVATemplate);
			this.extension.setText(this.prefJAVAExtension);
		} else if (lang.equals("C++")) {
			this.template.setText(this.prefCPPTemplate);
			this.extension.setText(this.prefCPPExtension);
		} else {
			this.template.setText(this.prefCSHARPTemplate);
			this.extension.setText(this.prefCSHARPExtension);
		}
	}

	/*
	 * Switch from one of languages C/C++,Java, C#, save the extension and
	 * template content into variable from the UI controls
	 */
	private void savePrefLangFromUI(String lang) {
		String templateText = this.template.getText();
		if (templateText == null)
			templateText = "";

		String templateExt = this.extension.getText();
		if (templateExt == null)
			templateExt = "";

		if (lang.equals("Java")) {
			this.prefJAVATemplate = templateText;
			this.prefJAVAExtension = templateExt;
		} else if (lang.equals("C++")) {
			this.prefCPPTemplate = templateText;
			this.prefCPPExtension = templateExt;
		} else {
			this.prefCSHARPTemplate = templateText;
			this.prefCSHARPExtension = templateExt;
		}
	}

	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();
		if (source == this.language) {
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				String lang = (String) e.getItem();
				savePrefLangFromUI(lang);
			} else {
				loadPrefLangToUI();
			}
		} else if (source == this.indent) {
			String indentType = (String) e.getItem();
			if (e.getStateChange() == ItemEvent.SELECTED) {
				this.prefIndentType = indentType;
			}
		}
	}

	@Override
	public String getTabTitle() {
		return "Code Template";
	}

	@Override
	public String getTabToolTip() {
		return "Specify code templates, please use leading Tab for template content.";
	}

	@Override
	public Icon getTabIcon() {
		return null;
	}

	@Override
	public void loadPreferencesToUI() {
		this.loadPrefLangToUI();
		this.language.setSelectedItem("Java");

		this.indent.setSelectedItem(prefIndentType);
		this.tabSizeField.setText(String.valueOf(this.prefTabSize));
	}

	@Override
	public boolean savePreferencesFromUI() {

		try {
			int tabSize = Integer.parseInt(this.tabSizeField.getText());
			this.prefTabSize = tabSize;
		} catch (Exception e) {
			return false;
		}
		this.savePrefLangFromUI((String) this.language.getSelectedItem());
		return true;
	}

	class IntFilter extends DocumentFilter {
		@Override
		public void insertString(FilterBypass fb, int offset, String string,
				AttributeSet attr) throws BadLocationException {

			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.insert(offset, string);

			if (test(sb.toString())) {
				super.insertString(fb, offset, string, attr);
			} else {
				// warn the user and don't allow the insert
			}
		}

		private boolean test(String text) {
			try {
				Integer.parseInt(text);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length,
				String text, AttributeSet attrs) throws BadLocationException {

			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.replace(offset, offset + length, text);

			if (test(sb.toString())) {
				super.replace(fb, offset, length, text, attrs);
			} else {
				// warn the user and don't allow the insert
			}

		}

		@Override
		public void remove(FilterBypass fb, int offset, int length)
				throws BadLocationException {
			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.delete(offset, offset + length);

			if (test(sb.toString())) {
				super.remove(fb, offset, length);
			} else {
				// warn the user and don't allow the insert
			}

		}
	}

}
