package tests.unit;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import topcoder.editor.Preferences;

public class PreferencesTest extends Preferences {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetProperties() {
		removeAllProperties();
		String[] x = getCodeProcessors();
		assertArrayEquals(new String[]{"topcoder.editor.ExampleProcessor"}, x);
	}

}
