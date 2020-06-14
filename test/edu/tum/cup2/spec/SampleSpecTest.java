package edu.tum.cup2.spec;

import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.spec.SampleSpec.Terminals.*;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.junit.Ignore;
import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.ScannerToken;
import edu.tum.cup2.scanner.TestScanner;


public class SampleSpecTest
{

	@Test public void test()
	{
		new SampleSpec();
	}
	
	
	@Test public void testCalc()
	{
		try
		{
			SampleSpec spec = new SampleSpec();
			LRParsingTable table = new LR1toLALRGenerator(spec, Verbosity.Sparse).getParsingTable();
			//check the generated parser with a small computation
			Integer result = (Integer) new LRParser(table).parse(new TestScanner(
				terminal(NUMBER, 30),
				terminal(PLUS),
				terminal(NUMBER, 4),
				terminal(TIMES),
				terminal(LPAREN),
				terminal(NUMBER, 2),
				terminal(PLUS),
				terminal(NUMBER, 15),
				terminal(RPAREN),
				terminal(SEMI)));
			//result must be 30 + 4 * (2 + 15) = 98
			assertEquals(new Integer(98), result);
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	@Test public void testSumUp()
	{
		try
		{
			SampleSpec spec = new SampleSpec();
			LRParsingTable table = new LR1Generator(spec, Verbosity.None).getParsingTable();
			//add the numbers from 1 to max
			int max = 50000;
			@SuppressWarnings("unchecked") ScannerToken[] input = new ScannerToken[max * 2];
			for (int i = 0; i < max; i++)
			{
				input[2*i+0] = terminal(NUMBER, i + 1);
				input[2*i+1] = terminal(PLUS);
			}
			input[2*max-2] = terminal(NUMBER, max);
			input[2*max-1] = terminal(SEMI);
			
			long time = System.currentTimeMillis();
			int result = (Integer) new LRParser(table).parse(new TestScanner(input),false);
			time = System.currentTimeMillis() - time;
			System.out.println("TIME: " + time + ", RESULT: " + result);
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	@Ignore @Test public void testSumUpFromFile()
	{
		try
		{
			SampleSpec spec = new SampleSpec();
			LRParsingTable table = new LR1Generator(spec, Verbosity.None).getParsingTable();
			
			long time = System.currentTimeMillis();
			int result = (Integer) new LRParser(table).parse(
				new SampleSpecScanner(new FileReader("sumup.txt")));
			time = System.currentTimeMillis() - time;
			System.out.println("TIME: " + time + ", RESULT: " + result);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	@Test public void testStack()
	{
		try
		{
			SampleSpec spec = new SampleSpec();
			LRParsingTable table = new LR1Generator(spec, Verbosity.None).getParsingTable();
			//3+2*( 3+2*( … ))  (max times)
			int max = 100000 - 1; //because corresponding CUP test starts at 1 instead of 0
			@SuppressWarnings("unchecked") ScannerToken[] input = new ScannerToken[max * 6 + 2];
			for (int i = 0; i < max; i++)
			{
				input[5*i+0] = terminal(NUMBER, 3);
				input[5*i+1] = terminal(PLUS);
				input[5*i+2] = terminal(NUMBER, 2);
				input[5*i+3] = terminal(TIMES);
				input[5*i+4] = terminal(LPAREN);
			}
			input[5*(max-1)+5] = terminal(NUMBER, 1);
			for (int i = 0; i < max; i++)
			{
				input[5*(max-1)+6+i] = terminal(RPAREN);
			}
			input[input.length-1] = terminal(SEMI);
			
			long time = System.currentTimeMillis();
			int result = (Integer) new LRParser(table).parse(new TestScanner(input),false);
			time = System.currentTimeMillis() - time;
			System.out.println("TIME: " + time + ", RESULT: " + result);
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	@Ignore @Test public void testStackFromFile()
	{
		try
		{
			SampleSpec spec = new SampleSpec();
			LRParsingTable table = new LR1Generator(spec, Verbosity.None).getParsingTable();
			
			long time = System.currentTimeMillis();
			int result = (Integer) new LRParser(table).parse(
				new SampleSpecScanner(new FileReader("stack.txt")));
			time = System.currentTimeMillis() - time;
			System.out.println("TIME: " + time + ", RESULT: " + result);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	public static void main(String[] args)
		throws Exception
	{
		//generate sumup.txt
		int max = 5000000;
		FileWriter outFile = new FileWriter("sumup.txt");
	  PrintWriter out = new PrintWriter(outFile);
	  for (int i = 0; i < max; i++)
		{
	  	out.print(i + 1);
	  	out.print("+");
		}
	  out.print(max);
	  out.print(";");
	  out.close();
	  
	  //generate stack.txt
	  max = 50000 - 1; //because corresponding CUP test starts at 1 instead of 0
	  outFile = new FileWriter("stack.txt");
	  out = new PrintWriter(outFile);
		for (int i = 0; i < max; i++)
		{
			out.print(3);
			out.print("+");
			out.print(2);
			out.print("*");
			out.print("(");
		}
		out.print(1);
		for (int i = 0; i < max; i++)
		{
			out.print(")");
		}
		out.print(";");
		out.close();
	}
	
	
}
