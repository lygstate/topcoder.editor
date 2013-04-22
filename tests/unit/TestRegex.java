package tests.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import topcoder.editor.Editor;

public class TestRegex {

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
	public void test() {
		String ret = Editor.expandYear("TCO13 Round 1A DIV 1 - ", "TCO");
		assertEquals("TCO2013 Round 1A DIV 1 - ", ret);
	}

}
