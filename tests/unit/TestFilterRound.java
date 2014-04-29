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
		assertEquals("SRM144", Editor.filterRoundName("SRM 144 DIV 2 - "));
		assertEquals("SichuanProvinceCollegeTour", Editor.filterRoundName("Sichuan Province College Tour DIV 1 - ")); //SRM373
		assertEquals("EastChinaCollegeTourRound1", Editor.filterRoundName("East China College Tour Round 1 DIV 1 - "));//SRM 400
		assertEquals("SRM580", Editor.filterRoundName("Single Round Match 580 - Round 1"));
		
		assertEquals("TCO2001SemiAB", Editor.filterRoundName("Inv 2001 Semi A+B - "));
		assertEquals("TCO2002REGMWW", Editor.filterRoundName("CC 2002 REG MW/W - "));
		assertEquals("TCO2002REGSemi", Editor.filterRoundName("CC 2002 REG Semi - "));
		assertEquals("TCO2003Round1NESERegion", Editor.filterRoundName("TCCC 2003 Round 1 NE/SE Region - "));
		assertEquals("TCO2003CharityChallenge", Editor.filterRoundName("CRPF Charity Challenge - "));
		assertEquals("TCO2004QualificationSet1", Editor.filterRoundName("TCO04 Qualification Set 1 - "));
		assertEquals("TCO2004Round2", Editor.filterRoundName("TCO04 Round 2 - "));
		assertEquals("TCO2005SemifinalRoom1", Editor.filterRoundName("TCO05 Semifinal Room 1 - "));
		assertEquals("TCO2006Qualification7914", Editor.filterRoundName("TCO 06 Qualification 7/9/14 - "));
		assertEquals("TCO2006Qual1", Editor.filterRoundName("TCCC06 Qual 1 DIV 1 - "));
		assertEquals("TCO2008ChinaRound1D", Editor.filterRoundName("2008 TopCoder China Tournament Round 1D DIV 1 - "));
		assertEquals("TCO2008ChinaRound1A", Editor.filterRoundName("2008 China Tournament Round 1A Div 1 - "));
		assertEquals("TCO2008ChinaRound2", Editor.filterRoundName("TopCoder China Tournament Round 2 DIV 1 - "));
		assertEquals("TCO2009Qual1", Editor.filterRoundName("TCO'09 Qual 1 DIV 1 - "));
		assertEquals("TCO2012Round1A", Editor.filterRoundName("TCO 2012 Round 1A DIV 1 - "));
		assertEquals("TCHSSRM1", Editor.filterRoundName("TCHS SRM 1 - "));
		assertEquals("TCHS2007GammaRound1", Editor.filterRoundName("TCHS07 Gamma Round 1 DIV 1 - "));
	}

}
