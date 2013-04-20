package topcoder.editor;

import com.topcoder.client.contestant.ProblemComponentModel;
import com.topcoder.shared.language.Language;
import com.topcoder.shared.problem.Renderer;

import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import topcoder.editor.ui.Common;

public class Editor implements Observer {
	JPanel panel;
	JTextArea log = new JTextArea();
	boolean overridefname;
	File directory;
	File fullPath = null;
	String fileName;
	String beginCut;
	String endCut;
	String initialSrc = null;
	Preferences pref = new Preferences(this);
	Map userDefinedTags = new HashMap();
	Language language;
	ProblemComponentModel component = null;
	Renderer renderer;
	private static final String POWEREDBY = "// Powered by FileEdit";

	public Editor() {
		this.log.setForeground(Common.FG_COLOR);
		this.log.setBackground(Common.BG_COLOR);

		loadPreferences();

		this.panel = new FileEditorPanel(this.log);
	}

	public void configure() {
		new ConfigurationDialog().show();
	}

	public void setUserDefinedTags(Map userDefinedTags) {
		this.userDefinedTags = userDefinedTags;
	}

	public void setProblemComponent(ProblemComponentModel component,
			Language lang, Renderer renderer) {
		this.component = component;
		this.language = lang;
		this.renderer = renderer;
	}

	public JPanel getEditorPanel() {
		return this.panel;
	}

	public String getSource() {
		if (this.fullPath == null) {
			writeLog("Trying to read source but file isn't initialized!.  Returning nothing.");
			return "";
		}

		if (!this.fullPath.exists()) {
			writeLog("Trying to read File " + this.fullPath
					+ " but it does not exist!.  Returning nothing.");
			return "";
		}

		int len = (int) this.fullPath.length();
		StringBuffer sourceComments = new StringBuffer(len);
		StringBuffer source = new StringBuffer(len);

		BufferedReader in = null;
		boolean ignoreLine = false;
		try {
			in = new BufferedReader(new FileReader(this.fullPath));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				sourceComments.append(line);
				sourceComments.append(Utilities.lineEnding);

				if (line.trim().equals(this.beginCut.trim())) {
					ignoreLine = true;
				} else if (line.trim().equals(this.endCut.trim())) {
					ignoreLine = false;
				} else if (!ignoreLine) {
					source.append(line);
					source.append(Utilities.lineEnding);
				}
			}
		} catch (IOException e) {
			writeLog("Error reading source code from file " + this.fullPath
					+ ": " + e.toString() + Utilities.lineEnding
					+ "Returning nothing.");
			return "";
		} finally {
			break label305;
			localObject1 = returnAddress;
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
		label305: writeLog("Source read from file " + this.fullPath);

		if ((this.initialSrc != null)
				&& (this.initialSrc.equals(sourceComments.toString()))) {
			writeLog("No changes to initial source - returning nothing");
			return "";
		}

		if ((this.pref.isPoweredBy())
				&& (!source.toString().endsWith("// Powered by FileEdit"))
				&& (source.length() != 0)) {
			source.append(Utilities.lineEnding);
			source.append(Utilities.lineEnding);
			source.append("// Powered by FileEdit");
		}

		String sig = getSignature();
		if ((!source.toString().startsWith(sig)) && (source.length() != 0)) {
			source.insert(0, sig);
		}

		return source.toString();
	}

	protected String getSignature() {
		String sigFileName = this.pref.getSignatureFileName().trim();

		if (!sigFileName.equals("")) {
			File sigFile = new File(sigFileName);

			if (sigFile.exists()) {
				StringBuffer sig = new StringBuffer((int) sigFile.length());
				BufferedReader in = null;
				try {
					in = new BufferedReader(new FileReader(sigFile));
					while (true) {
						String line = in.readLine();
						if (line == null)
							break;
						sig.append(line);
						sig.append(Utilities.lineEnding);
					}

					return sig.toString();
				} catch (IOException e) {
					writeLog("Error reading the signature file " + sigFileName
							+ ":" + e.toString() + Utilities.lineEnding
							+ "SigFile ignored");
				} finally {
					break label196;
					localObject1 = returnAddress;
					if (in != null)
						try {
							in.close();
						} catch (IOException e) {
						}
				}
			}
		}
		label196: return "";
	}

	public void setSource(String source) {
		String className = this.component.getClassName();

		String tFileName = (this.overridefname ? this.fileName : className)
				+ "."
				+ (this.language.getId() == 1 ? this.pref.getJAVAExtension()
						: this.language.getId() == 3 ? this.pref
								.getCPPExtension() : this.pref
								.getCSHARPExtension());
		try {
			problemDescription = this.pref.isHTMLDesc() ? this.renderer
					.toHTML(this.language) : this.renderer
					.toPlainText(this.language);
		} catch (Exception e) {
			String problemDescription;
			System.err.println("Exception happened during rendering: " + e);
			problemDescription = this.pref.isHTMLDesc() ? "<html><body>Error happened - see applet for problem text</body></html>"
					: "Error happened - see applet for problem text";
		}

		String problemDescription = this.pref.isHTMLDesc() ? problemDescription
				: Utilities.parseProblem(problemDescription);

		if ((source == null) || (source.equals(""))
				|| (source.equals(this.component.getDefaultSolution()))) {
			source = Utilities
					.getSource(this.language, this.component, tFileName,
							this.pref.isHTMLDesc() ? "" : problemDescription);
		}

		source = Utilities.replaceUserDefined(source, this.userDefinedTags);

		this.fullPath = new File(this.directory, tFileName);

		if (this.fullPath.exists()) {
			if (!this.pref.isBackup()) {
				writeLog("File '"
						+ this.fullPath
						+ "' already exists - not overwriting due to config option");
				return;
			}

			File backup = new File(this.directory, tFileName + ".bak");
			if (backup.exists()) {
				if (backup.delete())
					writeLog("Backup file " + backup
							+ " exists and was deleted");
				else {
					writeLog("Error deleting backup file " + backup);
				}

			}

			File renameIt = new File(this.directory, tFileName);
			if (renameIt.renameTo(backup)) {
				writeLog("File " + this.fullPath + " is being backed up to "
						+ backup);
			} else {
				writeLog("Error backing up file " + this.fullPath);
				writeLog("No Backup exists for the file - be careful");
			}

		}

		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			if (this.directory.mkdirs())
				writeLog("Directory " + this.directory + " was created");
			if (this.fullPath.createNewFile())
				writeLog("File " + this.fullPath + " was created");

			out = new BufferedWriter(new FileWriter(this.fullPath));
			in = new BufferedReader(new StringReader(source));
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				out.write(line);
				out.write(Utilities.lineEnding);
			}
			writeLog("Source successfully written to " + this.fullPath);
		} catch (IOException e) {
		} finally {
			break label849;
			localObject1 = returnAddress;
			try {
				in.close();
			} catch (IOException localIOException1) {
			}
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				writeLog("Error closing the file " + this.fullPath + ": "
						+ e.toString());
			}

		}

		label849: if (this.pref.isWriteProblemDescFile()) {
			String pFileName = (this.overridefname ? this.fileName : className)
					+ "." + this.pref.getProblemDescExtension();
			File pFullPath = new File(this.directory, pFileName);
			try {
				if (this.directory.mkdirs())
					writeLog("Directory " + this.directory + " was created");
				if (pFullPath.createNewFile())
					writeLog("File " + pFullPath + " was created");

				out = new BufferedWriter(new FileWriter(pFullPath));
				in = new BufferedReader(new StringReader(problemDescription));
				while (true) {
					String line = in.readLine();
					if (line == null)
						break;
					out.write(line);
					out.write(Utilities.lineEnding);
				}
				writeLog("Problem Description successfully written to "
						+ pFullPath);
			} catch (IOException e) {
			} finally {
				return;
				e = returnAddress;
				try {
					in.close();
				} catch (IOException localIOException2) {
				}
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					writeLog("Error closing the file " + pFullPath + ": "
							+ e.toString());
				}
			}
		}
	}

	public void update(Observable o, Object a) {
		loadPreferences();
	}

	private final void loadPreferences() {
		String dirName = this.pref.getDirectoryName();
		this.directory = new File(dirName.trim());

		this.fileName = this.pref.getFileName();

		this.beginCut = this.pref.getBeginCut();
		this.endCut = this.pref.getEndCut();

		this.overridefname = this.pref.isOverrideFileName();
	}

	private final void writeLog(String text) {
		this.log.append(text);
		this.log.append("\n");
		this.log.setCaretPosition(this.log.getDocument().getLength() - 1);
	}

	public static void main(String[] args) {
		ArrayList parms = new ArrayList();
		parms.add("String");
		parms.add("String");
		parms.add("int");

		Editor en = new Editor();

		System.out.println(en.getSource());
	}
}
