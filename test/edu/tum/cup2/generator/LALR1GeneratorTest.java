package edu.tum.cup2.generator;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.generator.states.LR1State;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.parser.tables.LRParsingTableTestTool;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.SpecCalc1;
import edu.tum.cup2.test.SpecCalc4;
import edu.tum.cup2.test.SpecJava14;
import edu.tum.cup2.test.SpecMiniJava;


/**
 * Unit tests for {@link LALR1Generator}
 * 
 * This class compares the parsing tables created by the {@link LALR1Generator}
 * with the parsing tables created by the {@link LR1toLALRGenerator}.
 * 
 * @author Michael Hausmann
 * @author Daniel Altmann
 * @author Andreas Wenger
 */
public class LALR1GeneratorTest 
{
	/**
	 * The main method of the LALR1GeneratorTest class
	 * can be called from the command line and gets passed any
	 * CUPSpecification name that is used for the comparison test.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		//make sure there is a class Name passed
		if(args.length != 1)
		{
			System.err.println("This program requires exactly one argument. "
					+ "This must be the name of a CUPSpecification which is going to be used to compare the parsing tables created with the LALR1Generator with the LR1toLALRGenerator.");
			return;
		}
		String strSpecName = args[0];
		
		//create Spec with Reflection
		CUP2Specification spec = createSpec(strSpecName);
		if(spec == null) return;
		
		try{
			lalrTest(spec, ".html");
		}
		catch(Exception e)
		{
			System.err.println("An exception of type " 
					+ e.getClass().getSimpleName() + " occured: " 
					+ e.getMessage() + "; " + e.getCause());
		}
		
	}
	
	/**
	 * createSpec 
	 * creates a CUPSpecification with Reflection
	 * @param specName Name of the Specification to create
	 * @return CUPSpecification
	 */
	@SuppressWarnings("unchecked")
	private static CUP2Specification createSpec(String specName)
	{
		Class<CUP2Specification> specClass = null;
		//get the class object for the specName
		try {
			Class aClass = Class.forName(specName);
			specClass = aClass.asSubclass(CUP2Specification.class);
		} catch(ClassNotFoundException e) {
			System.err.println("The class for the passed CUPSpecification could not be found.");
		} catch(Exception e)
		{
			System.err.println("The argument was not a valid CUPSpecification name!");
		}
		//having found the class, try to instantiate it
		try {
			CUP2Specification spec = specClass.newInstance();
			return spec;
		} catch(IllegalAccessException e) {
			System.err.println("The default construction of " + specName + " could not be accessed.");
		} catch(InstantiationException e) {
			System.err.println(specName + " could not be instantiated!");
		}
		return null;
	}

	private static void lalrTest(CUP2Specification spec, String strFileNameTail) throws GeneratorException
	{
		String strFileName1 = spec.getClass().getSimpleName();
		String strFileName2 = spec.getClass().getSimpleName();
		String strFileName3 = spec.getClass().getSimpleName();
		String strFileName4 = spec.getClass().getSimpleName();
		if(strFileName1 == null)
		{
			strFileName1 = "";
			strFileName2 = "";
			strFileName3 = "";
			strFileName4 = "";
		}
		strFileName1 += ".table1" + strFileNameTail;
		strFileName2 += ".table2" + strFileNameTail;
		strFileName3 += ".table3" + strFileNameTail;
		strFileName4 += ".table4" + strFileNameTail;
		
		//create an LALR1Generator
		System.out.print(spec.getClass().getSimpleName() + " - LALR1Generator: ");
		long timeStart = System.currentTimeMillis();
		LALR1Generator generator = new LALR1Generator(spec);
		long timeStop = System.currentTimeMillis();
		System.out.println((timeStop - timeStart) + " ms");
		//get the parse table from the LALR1Generator and create a parser with it
		LRParsingTable tblLALR1 = generator.getParsingTable();
		new LRParser(tblLALR1);
		//dump the parse table
		LRParsingTableDump.dumpToHTML(
				tblLALR1, 
				new File(strFileName1));
		
//		//create an LR1toLALRGenerator for comparison
//		System.out.print(spec.getClass().getSimpleName() + " - LR1toLALRGenerator: ");
//		timeStart = System.currentTimeMillis();
//		LR1toLALRGenerator generator2 = new LR1toLALRGenerator(spec);
//		timeStop = System.currentTimeMillis();
//		System.out.println((timeStop - timeStart) + " ms");
//		LRParsingTable tbl_LR1toLALR1 = generator2.getParsingTable();
//		new LRParser(tbl_LR1toLALR1);
//		LRParsingTableDump.dumpToHTML(tbl_LR1toLALR1, new File(strFileName2));
		
		//create an LALR1CPGenerator for comparison
		System.out.print(spec.getClass().getSimpleName() + " - LALR1CPGenerator: ");
		timeStart = System.currentTimeMillis();
		LALR1CPGenerator generator3 = new LALR1CPGenerator(spec);
		timeStop = System.currentTimeMillis();
		System.out.println((timeStop - timeStart) + " ms");
		
		//create an LALR1ParallelGenerator for comparison
		System.out.print(spec.getClass().getSimpleName() + " - LALR1ParallelGenerator: ");
		timeStart = System.currentTimeMillis();
		LALR1ParallelGenerator generator4 = new LALR1ParallelGenerator(spec);
		timeStop = System.currentTimeMillis();
		System.out.println((timeStop - timeStart) + " ms");

                //create an LALR1SCC for comparison
		System.out.print(spec.getClass().getSimpleName() + " - LALR1SCCGenerator: ");
		timeStart = System.currentTimeMillis();
		LALR1SCCGenerator generator5 = new LALR1SCCGenerator(spec);
		timeStop = System.currentTimeMillis();
		System.out.println((timeStop - timeStart) + " ms");
                
                
		// check if all automatons are equal
		Automaton<LR1Item, LR1State> autoLALR1 = generator.getAutomaton();
		Automaton<LR1Item, LR1State> autoLALR1CP = generator3.getAutomaton();
		Automaton<LR1Item, LR1State> autoLALR1Parallel = generator4.getAutomaton();
		Automaton<LR1Item, LR1State> autoLALR1SCC = generator5.getAutomaton();
                
 		Assert.assertEquals(autoLALR1.getStates().size(), autoLALR1CP.getStates().size());
		Assert.assertEquals(autoLALR1.getEdges().size(), autoLALR1CP.getEdges().size());
 		Assert.assertEquals(autoLALR1.getStates().size(), autoLALR1Parallel.getStates().size());
		Assert.assertEquals(autoLALR1.getEdges().size(), autoLALR1Parallel.getEdges().size());
 		Assert.assertEquals(autoLALR1.getEdges().size(), autoLALR1SCC.getEdges().size());
                // AutomatonTestTool.testContainmentOfAllStates(autoLALR1);
			
		AutomatonTestTool.testEquals(autoLALR1, autoLALR1CP);
		AutomatonTestTool.testEquals(autoLALR1, autoLALR1Parallel);
		AutomatonTestTool.testEquals(autoLALR1, autoLALR1SCC);
                
		LRParsingTable tbl_LALR1CP = generator3.getParsingTable();
		new LRParser(tbl_LALR1CP);
		LRParsingTableDump.dumpToHTML(tbl_LALR1CP, new File(strFileName3));
//		LRParsingTableTestTool.assertEquals(tbl_LR1toLALR1, tblLALR1);
		LRParsingTableTestTool.assertEquals(tblLALR1, tbl_LALR1CP);
		
		LRParsingTable tbl_LALR1Parallel = generator4.getParsingTable();
		new LRParser(tbl_LALR1CP);
		LRParsingTableDump.dumpToHTML(tbl_LALR1Parallel, new File(strFileName4));
		
		LRParsingTableTestTool.assertEquals(tblLALR1, tbl_LALR1CP);
		LRParsingTableTestTool.assertEquals(tblLALR1, tbl_LALR1Parallel);
			
	}
	
	private static void lalrTest(CUP2Specification spec) throws GeneratorException
	{
		lalrTest(spec, ".html");
	}
	

        
	/**
	 * Unit Test for LALRGenerator
	 * @throws Exception
	 */
	@Test public void lalrTestSpecCalc4() throws Exception
	{
		SpecCalc4 spec = new SpecCalc4(); //Spec for Test
		lalrTest(spec);
	}
	
	/**
	 * Unit Test for LALRGenerator
	 * @throws Exception
	 */
	@Test public void lalrTestSpecCalc1() throws Exception
	{
		SpecCalc1 spec = new SpecCalc1(); //Spec for Test
		lalrTest(spec);
	}
	
	/**
	 * Unit Test for LALRGenerator using the grammar for C
	 * @throws Exception
	 */
	@Test public void lalrTestSpecC() throws Exception
	{
		CUP2Specification spec = new TranslatedCSpec2(); //Spec for Test
		lalrTest(spec);
	}
	@Test public void lalrTestMiniJava() throws Exception
	{
		SpecMiniJava spec = new SpecMiniJava();
		lalrTest(spec);
	}

	@Test public void lalrTestJava() throws Exception
	{
		SpecJava14 spec = new SpecJava14();
		lalrTest(spec);
	}
	
	 	
	/**
	 * Unit Test for LALRGenerator with the PHP Spec
	 * @throws Exception
	 */
	@Test public void lalrTestSpecPHP() throws Exception
	{
		CUP2Specification spec = new TranslatedPHPSpec(); //Spec for Test
		lalrTest(spec);
	}
	
}
