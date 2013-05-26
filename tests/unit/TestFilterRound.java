package tests.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import topcoder.editor.Editor;

public class TestFilterRound {

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
	public void testTCO() {
		assertEquals("TCO2012Round1A", Editor.filterRoundName("TCO 2012 Round 1A DIV 1 - "));
		assertEquals("SRM580", Editor.filterRoundName("Single Round Match 580 - Round 1"));
		assertEquals("SRM400EastChina", Editor.filterRoundName("East China College Tour Round 1 DIV 1 - "));
		assertEquals("TCO2008ChinaRound1D", Editor.filterRoundName("2008 TopCoder China Tournament Round 1D DIV 1 - "));
		assertEquals("TCO2008ChinaRound1A", Editor.filterRoundName("2008 China Tournament Round 1A Div 1 - "));
		assertEquals("TCO2008ChinaRound2", Editor.filterRoundName("TopCoder China Tournament Round 2 DIV 1 - "));
		
	}

}
