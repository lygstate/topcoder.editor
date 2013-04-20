package tests.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import topcoder.editor.Utilities;

public class UtilitiesTest{

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
	public void testParseProblem() {
		String probState = "PROBLEM STATEMENT\r\nWhen putting together a problem set, a writer must keep in mind the difficulty and length of a problem.  A good problem set is one with an easy, a middle, and a hard problem, but doesn't take too much or too little time to complete.\r\n\nYou will be given an input of three int[].  The first int[] consists of easy problem times, the second consists of middle problem times, and the third consists of hard problem times.  Return the number of legal problem set combinations, where a legal set contains exactly 1 easy, 1 middle and 1 hard problem, and the total time is between 60 and 75 inclusive.\r\n\r\nDEFINITION\r\nClass name: Chooser\r\nMethod name: numSets\r\nParameters: int[], int[], int[]\r\nReturns: int\r\nThe method signature is:\r\nint numSets(int[] easy, int[] middle, int[] hard)\r\nBe sure your method is public.\r\n\r\nTopCoder will ensure the following:\r\n*Each int[] will contain between 0 and 10 elements, inclusive.\r\n*Each element of easy will be an int between 5 and 15, inclusive.\r\n*Each element of middle will be an int between 15 and 45, inclusive.\r\n*Each element of hard will be an int between 30 and 55, inclusive.\r\n\r\nEXAMPLES\r{5,10,15}\r\n{15,25}\r\n{45}\r\nThere are 3*2*1=6 possible sets.  However, since 10+25+45=80 and 15+25+45=85, two of the sets are illegal, so the method returns 4.\r\n\r\n{5,5,5}\r\n{15,15,15}\r\n{45,45,45}\r\nThere are 3*3*3=27 possible sets, all legal.  The return value is 27.\r\n\r\n{5,5,5}\r\n{15,15,15}\r\n{45,45,35}\r\nThere are 27 possible sets again, but for this input any set with the 35 minute hard problem is too short.  Therefore there are only 3*3*2=18 legal sets, and the return value is 18.\r\n\r\n{}\r\n{15,25}\r\n{30,35,40}\r\nSince there are no easy problems, there are no legal problem sets.  The return value is 0.\n";
		String probStateParsed = "// PROBLEM STATEMENT\r\n// When putting together a problem set, a writer must keep in mind the difficulty and length of a problem.  A good problem set is one with an easy, a middle, and a hard problem, but doesn't take too much or too little time to complete.\r\n// \r\n// You will be given an input of three int[].  The first int[] consists of easy problem times, the second consists of middle problem times, and the third consists of hard problem times.  Return the number of legal problem set combinations, where a legal set contains exactly 1 easy, 1 middle and 1 hard problem, and the total time is between 60 and 75 inclusive.\r\n// \r\n// DEFINITION\r\n// Class name: Chooser\r\n// Method name: numSets\r\n// Parameters: int[], int[], int[]\r\n// Returns: int\r\n// The method signature is:\r\n// int numSets(int[] easy, int[] middle, int[] hard)\r\n// Be sure your method is public.\r\n// \r\n// TopCoder will ensure the following:\r\n// *Each int[] will contain between 0 and 10 elements, inclusive.\r\n// *Each element of easy will be an int between 5 and 15, inclusive.\r\n// *Each element of middle will be an int between 15 and 45, inclusive.\r\n// *Each element of hard will be an int between 30 and 55, inclusive.\r\n// \r\n// EXAMPLES\r\n// {5,10,15}\r\n// {15,25}\r\n// {45}\r\n// There are 3*2*1=6 possible sets.  However, since 10+25+45=80 and 15+25+45=85, two of the sets are illegal, so the method returns 4.\r\n// \r\n// {5,5,5}\r\n// {15,15,15}\r\n// {45,45,45}\r\n// There are 3*3*3=27 possible sets, all legal.  The return value is 27.\r\n// \r\n// {5,5,5}\r\n// {15,15,15}\r\n// {45,45,35}\r\n// There are 27 possible sets again, but for this input any set with the 35 minute hard problem is too short.  Therefore there are only 3*3*2=18 legal sets, and the return value is 18.\r\n// \r\n// {}\r\n// {15,25}\r\n// {30,35,40}\r\n// Since there are no easy problems, there are no legal problem sets.  The return value is 0.\r\n// ";

		Utilities.pref.removeAllProperties();
		String ret = Utilities.parseProblem(probState);
		assertEquals(probStateParsed, ret);
	}

	@Test
	public void testReplaceLineNumber() {
		String ret = Utilities.replaceLineNumber("Test it $LINENUMBER$ again!\r\n Hello, the world\r\n Haha $NEXTLINENUMBER$ Done\r\n What's do you mean$NEXTLINENUMBER$");
		assertEquals("Test it 1 again!\r\n Hello, the world\r\n Haha 4 Done\r\n What's do you mean5\r\n", ret);
	}
}
