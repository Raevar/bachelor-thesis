package edu.tum.cup2.test;

import edu.tum.cup2.generator.LALR1CPGenerator;
import edu.tum.cup2.generator.LALR1SCCGenerator;
import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.spec.CUP2Specification;


/**
 * Measure the time of generating a parser.
 * 
 * @author Andreas Wenger
 */
public class Time
{
	
	public static void measureTime(CUP2Specification spec)
	{
		//LR(0)
		try
		{
			long time = System.currentTimeMillis();
			new LR0Generator(spec, Verbosity.Sparse).getParsingTable();
			time = System.currentTimeMillis() - time;
			System.out.println("LR(0) TIME: " + time);
		}
		catch (GeneratorException ex)
		{
			System.out.println("No LR(0)");
		}
		System.out.println();
		//LR(1)
		try
		{
			long time = System.currentTimeMillis();
			new LR1Generator(spec, Verbosity.Sparse).getParsingTable();
			time = System.currentTimeMillis() - time;
			System.out.println("LR(1) TIME: " + time);
		}
		catch (GeneratorException ex)
		{
			System.out.println("No LR(1)");
		}
		System.out.println();
		//LR(1)-to-LALR
		try
		{
			long time = System.currentTimeMillis();
			new LR1toLALRGenerator(spec, Verbosity.Sparse).getParsingTable();
			time = System.currentTimeMillis() - time;
			System.out.println("LR(1)-to-LALR TIME: " + time);
		}
		catch (GeneratorException ex)
		{
			System.out.println("No LR(1)-to-LALR");
		}
		// LALR(1)CP
		try
		{
			long time = System.currentTimeMillis();
			new LALR1CPGenerator(spec, Verbosity.Sparse).getParsingTable();
			time = System.currentTimeMillis() - time;
			System.out.println("LALR(1)-CP TIME: " + time);
		}
		catch (GeneratorException ex)
		{
			System.out.println("No LALR(1)-CP");
		}
		// LALR(1) with SCC 
		try
		{
			long time = System.currentTimeMillis();
			new LALR1SCCGenerator(spec, Verbosity.Sparse).getParsingTable();
			time = System.currentTimeMillis() - time;
			System.out.println("LALR(1)-SCC TIME: " + time);
		}
		catch (GeneratorException ex)
		{
			System.out.println("No LALR(1)-SCC");
		}
		System.out.println();
	}

}
