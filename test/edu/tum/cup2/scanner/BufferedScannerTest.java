package edu.tum.cup2.scanner;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static edu.tum.cup2.test.GrammarWiki1.Terminals.*;


/**
 * Test for BufferedScanner.
 * BufferedScanner uses a buffer to implement a look ahead functionality.
 * 
 * @author Michael Hausmann
 */
public class BufferedScannerTest 
{
	private BufferedScannerForTesting fTestObject;
	
	/**
	 * setting up the test fixture
	 */
	@Before public void init()
	{
		Scanner concreteScanner = new TestScanner(_1, plus, _0, times, _1); //Test input: _1, plus, _0, times, _1
		fTestObject = new BufferedScannerForTesting(concreteScanner);
	}
	
	/**
	 * Testcase for BufferedScanner
	 * @throws Exception
	 */
	@Test public void bufferedScannerTest()
	throws Exception
	{
		System.out.println("=== Testing BufferedScanner... ===");
		try{
			List<ScannerToken<? extends Object>> list1 = this.fTestObject.lookAhead(2);
			System.out.println(list1);
			Assert.assertEquals(this.fTestObject.getBufferLength(), 2);
			Assert.assertEquals(list1.get(0), this.fTestObject.readNextTerminal());
			Assert.assertEquals(this.fTestObject.getBufferLength(), 1);
			Assert.assertEquals(list1.get(1), this.fTestObject.readNextTerminal());
			Assert.assertEquals(this.fTestObject.getBufferLength(), 0);
			System.out.println(this.fTestObject.readNextTerminal());
			Assert.assertEquals(this.fTestObject.getBufferLength(), 0);
			List<ScannerToken<? extends Object>> list2 = this.fTestObject.lookAhead(2);
			Assert.assertEquals(this.fTestObject.getBufferLength(), 2);
			System.out.println(list2);
			//consume 2 terminals
			this.fTestObject.readNextTerminal(); this.fTestObject.readNextTerminal();
			Assert.assertEquals(this.fTestObject.getBufferLength(), 0);
			this.fTestObject.readNextTerminal();
			List<ScannerToken<? extends Object>> list3 = this.fTestObject.lookAhead(2);
			System.out.println(this.fTestObject.getBufferLength());
			System.out.println(list3);
			System.out.println("=== Testing BufferedScanner Finished ; SUCCESS ===");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.err.println("=== Testing BufferedScanner Finished ; FAIL ===");
		}
	}
} /* end of test class */
