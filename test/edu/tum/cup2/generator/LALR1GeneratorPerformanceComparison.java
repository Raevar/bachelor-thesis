package edu.tum.cup2.generator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.spec.CUP2Specification;
import static java.lang.System.*;
import org.junit.*;


public class LALR1GeneratorPerformanceComparison
{

	private final int fRepetitions = 1;
	private List<String> fSpecificationNames = new LinkedList<String>();
	private HashMap<String, CUP2Specification> fSpecifications = new HashMap<String, CUP2Specification>();
	private HashMap<String, Integer> fTimeLALR1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> fTimeLR1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> fTimeWeak = new HashMap<String, Integer>();

	@Test
	public void testJava14()
	{
		main(new String[]{
				"edu.tum.cup2.test.SpecJava14"
		});
	}
	
	@Test
	public void testCSpec()
	{
		main(new String[]{
				"edu.tum.cup2.generator.TranslatedCSpec2"
		});
	}
	
	@Test
	public void testPHP()
	{	
		main(new String[]{
				"edu.tum.cup2.generator.TranslatedPHPSpec"
		});
	}
	
	@Test
	public void testSmall()
	{	
		main(new String[]{
				"edu.tum.cup2.test.SpecCalc1; edu.tum.cup2.test.SpecCalc4; edu.tum.cup2.test.SpecMiniJava"
		});
	}
		

	public static void main(String[] args)
	{
		LALR1GeneratorPerformanceComparison c = new LALR1GeneratorPerformanceComparison();
		c.init(args[0]);
	
		try
		{
			c.execute();
		}
		catch (Exception e)
		{
			out.println("An exception of type " + e.getClass().getName() + " occured:"
				+ e.getMessage());
		}
		c.outputTimeLists();
		out.println("Generator Performance Comparison ended.");

	}

	private void init(String specs)
	{
		//parse spec names
		String[] arrSpecs = specs.split(";");
		for (String strSpec : arrSpecs)
		{
			strSpec = strSpec.trim();
			this.fSpecificationNames.add(strSpec);
		}
	}


	/**
	 * execute a performance measurement
	 * @throws GeneratorException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void execute()
		throws GeneratorException, ClassNotFoundException, InstantiationException,
		IllegalAccessException
	{
		try
		{
			instantiateSpec();
		}
		catch (Exception e)
		{
			out.println("Instantiation the Specifications failed.");
			return;
		}

		//initialize parsing times for the specs 
		initTimeList();

		for (int i = 0; i < this.fRepetitions; i++)
		{
			for (String strSpec : this.fSpecificationNames)
			{
				CUP2Specification spec = this.fSpecifications.get(strSpec);

				//LALR1
				
				//start time
				long lStart = System.currentTimeMillis();
				//for(int j=0; j < 10; j++)
				{
					LALR1Generator lalr1 = new LALR1Generator(spec);
				}
				//end time
				long lEnd = System.currentTimeMillis();

				long lalr1Time = this.fTimeLALR1.remove(strSpec);
				lalr1Time += (lEnd - lStart) * 1000;
				this.fTimeLALR1.put(strSpec, (int)lalr1Time);

				//LR1
				
				//start time
				lStart = System.currentTimeMillis();
				//for(int j=0; j < 10; j++)
				{
					LR1Generator lr1 = new LR1Generator(spec);
				}
				//end time
				lEnd = System.currentTimeMillis();

				long lr1Time = this.fTimeLR1.remove(strSpec);
				lr1Time += (lEnd - lStart) * 1000;
				this.fTimeLR1.put(strSpec, (int)lr1Time);
				
				long weakTime = this.fTimeWeak.remove(strSpec);
				weakTime += (lEnd - lStart) * 1000;
				this.fTimeWeak.put(strSpec, (int)weakTime);

			}
		}
		
		//scale (divide) parsing times
		for (String strSpec : this.fSpecificationNames)
		{
			Integer lr1Time = this.fTimeLR1.remove(strSpec);
			lr1Time /= this.fRepetitions;
			this.fTimeLR1.put(strSpec, lr1Time);
			
			Integer lalr1Time = this.fTimeLALR1.remove(strSpec);
			lalr1Time /= this.fRepetitions;
			this.fTimeLALR1.put(strSpec, lalr1Time);
			
			Integer weakTime = this.fTimeWeak.remove(strSpec);
			weakTime /= this.fRepetitions;
			this.fTimeWeak.put(strSpec, weakTime);
		}		
	}

	private void instantiateSpec()
		throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		for (String strSpec : this.fSpecificationNames)
		{
			Class cSpec = Class.forName(strSpec);
			CUP2Specification mySpec = (CUP2Specification) cSpec.newInstance();
			this.fSpecifications.put(strSpec, mySpec);
		}
	}


	private void initTimeList()
	{
		for (String strSpec : this.fSpecificationNames)
		{
			this.fTimeLALR1.put(strSpec, 0);
			this.fTimeLR1.put(strSpec, 0);
			this.fTimeWeak.put(strSpec, 0);
		}
	}


	private void outputTimeLists()
	{
		out.println("Results:");
		for (String strSpec : this.fSpecificationNames)
		{
			out.print(strSpec + ":    \t LALR1: " + Double.toString(this.fTimeLALR1.get(strSpec)/1000000.) + " sec"
				+ "\t LR1: " + Double.toString(this.fTimeLR1.get(strSpec)/1000000.) + "sec"
				+ "\t Weak: " + Double.toString(this.fTimeWeak.get(strSpec)/1000000.) + "sec"
				);
			out.println();
		}
	}
}
