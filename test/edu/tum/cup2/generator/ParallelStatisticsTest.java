package edu.tum.cup2.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.generator.states.LR1State;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.parser.tables.LRParsingTableTestTool;
import edu.tum.cup2.spec.CUP2Specification;

/**
 * Creates comparative statistics of the sequential and parallel parser generators
 * for LR(0), LR(1) and LALR(1) by performing multiple runs.
 * 
 * @author Johannes Schamburger
 */
public class ParallelStatisticsTest {
	
	CUP2Specification spec = new TranslatedPHPSpec();
	// the parallel algorithm is performed with those numbers of threads
	int[] numThreads = {2};

	// Set the specifications of the current run (these infos are needed for file output)
	String cpu = "Core 2 Duo E6600 @3.00 GHz";
	String ram = "2GB DDR2-800";
//	String cpu = "AMD Phenom II X4 955 @ 3.20 Ghz";
//	String ram = "4GB DDR3-1333";
//	String cpu = "AMD Turion @1.90 GHz";
//	String ram = "2GB DDR2-667";
	int numCores = Runtime.getRuntime().availableProcessors();
	String xms = "1024m";
	String xmx = "1024m";
//	String xms = "2048m";
//	String xmx = "2048m";
//	String xms = "3072m";
//	String xmx = "3072m";
	// if fileOutput is set to true, a cvs file with statistics is created
	boolean fileOutput = false;
	// file name looks like this: {prefix}{algorithm}{suffix}.cvs
	String prefix = "stats_c_";
	String suffix = ".csv";
	// after each run an equality check is performed on the sequential and the parallel automaton (only for LR1 and LALR1)
	boolean equalityCheck = true;
	
	int runsLR0 = 0;
	int runsLR1 = 0;
	int runsLALR1 = 10;

	long totalTime;
	HashMap<Integer, Long> totalTimeParallel;
	
	private void writeHeaders(StringBuffer buf, int runs, String algo) {
		buf.append("Grammar,"+spec.getClass().getName()+"\n");
		buf.append("CPU,"+cpu+"\n");
		buf.append("RAM,"+ram+"\n");
		buf.append("#Cores,"+numCores+"\n");
		buf.append("#Runs," + runs + "\n\n");
		buf.append("Xms," + xms + "\n");
		buf.append("Xmx," + xmx + "\n\n");
		buf.append("Run,Time "+algo);
		for(int n : numThreads) {
			buf.append(",Time "+algo+"Parallel ("+n+" Threads)");
		}
		buf.append("\n");
	}
	
	private void writeStats(StringBuffer buf, int runs, String algo) {

		buf.append(",sequential");
		for(int n : numThreads) {
			buf.append(",parallel ("+n+" Threads)");
		}
		buf.append("\n");
		
		System.out.println();
		System.out.println("after " + runs + " runs:");
		System.out.println(algo+": " + totalTime);
		buf.append("," + (double)totalTime/(double)runs);
		for(int n : numThreads) {
			System.out.println(algo+"Parallel ("+n+" Threads): " + totalTimeParallel.get(n)+
					"\t = "+ ((double) totalTimeParallel.get(n) / (double) totalTime) * 100);
			buf.append(","+((double)totalTimeParallel.get(n) / (double)runs));
		}
		buf.append("\nratio,");
		for(int n : numThreads) {
			buf.append(","+((double) totalTimeParallel.get(n) / (double) totalTime) * 100);
		}
		System.out.println();
		
	}
	
	private void initTimes() {
		totalTime = 0;
		totalTimeParallel = new HashMap<Integer, Long>();
		for(int n : numThreads) {
			totalTimeParallel.put(n, new Long(0));
		}
	}
	
	@Test public void testStatsLR0() {

		int runs = runsLR0;
		initTimes();

		String algo = "LR0";
		FileWriter stats = null;
		if (runs == 0) {
			return;
		}
		if(fileOutput) {
			try {
				stats = new FileWriter(prefix+algo+suffix);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		StringBuffer buf = new StringBuffer();
		writeHeaders(buf, runs, algo);
		
		for (int i = 0; i < runs; i++) {
			System.out.println("Run "+i+":");
			try {
				long time = System.currentTimeMillis();
				new LR0Generator(spec, Verbosity.Sparse);
				time = System.currentTimeMillis() - time;
				totalTime += time;

				buf.append(i + "," + time);
				System.out.println("LR(0) TIME: " + time);

				for(int n : numThreads) {
					LRGenerator.setNumThreads(n);
					time = System.currentTimeMillis();
					new LR0ParallelGenerator(spec, Verbosity.Sparse);
					time = System.currentTimeMillis() - time;
					totalTimeParallel.put(n, totalTimeParallel.get(n) + time);

					buf.append("," + time);
					System.out.println("LR(0) Parallel TIME ("+n+" Threads): " + time);
					
				}

				buf.append("\n");

			} catch (GeneratorException ex) {
				System.out.println("No LR(0)");
			}
		}
		
		writeStats(buf, runs, algo);
		String str = new String(buf);
		if(fileOutput) {
			try {
				stats.write(str);
				stats.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Test public void testStatsLR1() {
		
		LR1Generator LR1 = null;
		LR1ParallelGenerator LR1Parallel = null;
		
		initTimes();
		int runs = runsLR1;
		String algo = "LR1";
		FileWriter stats = null;
		if (runs == 0) {
			return;
		}
		if(fileOutput) {
			try {
				stats = new FileWriter(prefix+algo+suffix);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		StringBuffer buf = new StringBuffer();
		writeHeaders(buf, runs, algo);

		
		for (int i = 0; i < runs; i++) {
			System.out.println("Run "+i+":");
			try {
				long time = System.currentTimeMillis();
				// we keep the generators only if necessary
				if(equalityCheck)
					LR1 = new LR1Generator(spec, Verbosity.Sparse);
				else
					new LR1Generator(spec, Verbosity.Sparse);
				time = System.currentTimeMillis() - time;
				totalTime += time;
				
				buf.append(i + "," + time);
				System.out.println("LR(1) TIME: " + time);
				
				for (int n : numThreads) {
					LRGenerator.setNumThreads(n);
					time = System.currentTimeMillis();
					if(equalityCheck)
						LR1Parallel = new LR1ParallelGenerator(spec, Verbosity.Sparse);
					else
						new LR1ParallelGenerator(spec, Verbosity.Sparse);
					time = System.currentTimeMillis() - time;
					totalTimeParallel.put(n, totalTimeParallel.get(n) + time);
					
					buf.append("," + time);
					System.out.println("LR(1) Parallel TIME ("+n+" Threads): " + time);
				}
				buf.append("\n");

			} catch (GeneratorException ex) {
				System.out.println("No LR(1)");
			}
		}

		writeStats(buf, runs, algo);
		String str = new String(buf);
		if(fileOutput) {
			try {
				stats.write(str);
				stats.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (equalityCheck)
			checkEquality(LR1, LR1Parallel);

	}

	
	@Test public void testStatsLALR1() {
		LALR1CPGenerator LALR1 = null;
		LALR1ParallelGenerator LALR1Parallel = null;
		
		initTimes();		
		int runs = runsLALR1;
		String algo = "LALR1";
		FileWriter stats = null;
		String file = prefix+algo+suffix;
		if (runs == 0) {
			return;
		}
		if(fileOutput) {
			try {
				stats = new FileWriter(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		StringBuffer buf = new StringBuffer();
		writeHeaders(buf, runs, algo);

		for (int i = 0; i < runs; i++) {
			System.out.println("Run "+i+":");
			try {
				long time = System.currentTimeMillis();
				// we keep the generators only if necessary
				if(equalityCheck)
					LALR1 = new LALR1CPGenerator(spec, Verbosity.Sparse);
				else
					new LALR1CPGenerator(spec, Verbosity.Sparse);
				time = System.currentTimeMillis() - time;
				totalTime += time;
				
				buf.append(i + "," + time);
				System.out.println("LALR(1) TIME: " + time);

				for(int n : numThreads) {
					LRGenerator.setNumThreads(n);
					time = System.currentTimeMillis();
					if(equalityCheck)
						LALR1Parallel = new LALR1ParallelGenerator(spec, Verbosity.Sparse);
					else
						new LALR1ParallelGenerator(spec, Verbosity.Sparse);
					time = System.currentTimeMillis() - time;
					totalTimeParallel.put(n, totalTimeParallel.get(n) + time);
					
					buf.append("," + time);
					System.out.println("LALR(1) Parallel TIME ("+n+" Threads): " + time);
				}
				
				buf.append("\n");

				if (equalityCheck)
					checkEquality(LALR1, LALR1Parallel);
				
			} catch (GeneratorException ex) {
				System.out.println("No LALR(1)");
			}
		}

		writeStats(buf, runs, algo);
		String str = new String(buf);
		if(fileOutput) {
			try {
				stats.write(str);
				stats.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void checkEquality(LRGenerator<LR1Item, LR1State> generator,
			LRGenerator<LR1Item, LR1State> generatorParallel) {
		Automaton<LR1Item, LR1State> auto = generator.getAutomaton();
		Automaton<LR1Item, LR1State> autoParallel = generatorParallel.getAutomaton();
		
 		Assert.assertEquals(auto.getStates().size(), autoParallel.getStates().size());
		Assert.assertEquals(auto.getEdges().size(), autoParallel.getEdges().size());
			
		AutomatonTestTool.testEquals(auto, autoParallel);
		
		LRParsingTable tbl = generator.getParsingTable();
		new LRParser(tbl);
//		LRParsingTableDump.dumpToHTML(tbl, new File("sequential.html"));
		
		LRParsingTable tblParallel = generatorParallel.getParsingTable();
		new LRParser(tbl);
//		LRParsingTableDump.dumpToHTML(tblParallel, new File("parallel.html"));
		
		LRParsingTableTestTool.assertEquals(tbl, tblParallel);
		
		System.out.println("automatons are equal.");
	}

}
