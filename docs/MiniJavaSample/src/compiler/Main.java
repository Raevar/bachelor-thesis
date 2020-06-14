package compiler;

import edu.tum.cup2.generator.LALR1SCCGenerator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

import javax.sound.midi.SysexMessage;


import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.exceptions.LRParserException;
import edu.tum.cup2.scanner.InsertedScannerToken;
import edu.tum.cup2.scanner.InsertionScanner;
import edu.tum.cup2.scanner.Scanner;
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.ParserObserver;
import minijava.*;


/**
 * Class containing the main method.
 * 
 * @author Michael Petter
 * @author Andreas Wenger
 */
public class Main
{

	private final static boolean DEBUG = true;
	
	/**
	 * Runs the parser on an input file.
	 *
	 * @param argv   the command line, argv[1] is the filename to run
	 *               the parser on. argv[0] is "-jvm", "-syntax" oder "-cfg".
	 * @throws GeneratorException 
	 */
	public static void main(String argv[])
		throws GeneratorException
	{
		
		/**
		 * 	Transformation from arguments to variables.
		 * 
		 *	Modes : 	clean 	(parse w/o output)
		 *				help	(help page)
		 *				-jvm
		 *				-syntax
		 *				-cfg
		 *
		 *	Trigger :	-ins	(insert tokens)
		 *	
		 **/
		String 	fileName 		= null;
		boolean	doInsertions	= false;
		String	mode			= "help";
		
		for( int i=0; i<argv.length; i++ )
		{
			if ("-ins".equals(argv[i]))
			{
				doInsertions = true;
				if (mode=="help")
					mode = "clean";
			}
			else if ("-jvm".equals(argv[i]))
				mode = argv[i];
			else if ("-syntax".equals(argv[i]))
				mode = argv[i];
			else if ("-cfg".equals(argv[i]))
				mode = argv[i];
			else if (argv[i].charAt(0) == '-')
				System.err.println("Invalid argument : "+argv[i]);
			else if (fileName==null)
				fileName = argv[i];
			else
				System.err.println("Ignored : "+argv[i]);
		}
		if (fileName==null || mode.equals("help"))
		{
			System.err.println("Usage : java Main -<mode> [-ins] <inputfile>");
			System.exit(2);
		}
		
		/** O.K. let's start **/
		
		Scanner scanner = null;
		try
		{
			scanner = new MiniJavaLexer(new java.io.FileReader(fileName));
		}
		catch (java.io.FileNotFoundException e)
		{
			System.err.println("File not found : \"" + fileName + "\"");
			System.exit(1);
		}/*
		catch (java.io.IOException e)
		{
			System.err.println("Error opening file \"" + fileName + "\"");
			System.exit(1);
		}*/
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println("Usage : java Main -<option> <inputfile>");
			System.exit(1);
		}

		try
		{
			LALR1SCCGenerator gen = new LALR1SCCGenerator(new MiniJavaSpec());
			LRParser p = new LRParser(gen.getParsingTable());

			/** Find insertions **/
			if (doInsertions) {
				scanner = new InsertionScanner(
					gen.getParsingTable(),
					scanner
				);
			}
			
			/** DEBUG : Errors **/
			if (DEBUG)
				p.register(new ParserObserver(){
					public void syntax_error(ErrorInformation error) {
						System.err.println(error);
					}
				});

			/** Register observer for error-output **/
			ErrorWriter errWriter = new ErrorWriter(fileName);
			p.register(errWriter);
			
			Program result = (Program) p.parse(scanner);
			
			errWriter.flush();
			
			if (mode.equals("-jvm"))
			{
				MiniJVMGenerator jvm = new MiniJVMGenerator(argv[1]);
				result.accept(jvm);
				jvm.flush();
			}
			if (mode.equals("-syntax"))
			{
				SyntaxTreeGenerator syntax = new SyntaxTreeGenerator(argv[1]);
				result.accept(syntax);
				syntax.flush();
			}
			if (mode.equals("-cfg"))
			{
				CFGGenerator syntax = new CFGGenerator(argv[1]);
				result.accept(syntax);
				syntax.flush();
			}
			//System.out.println(result.toString());
		}
		catch (java.io.IOException e)
		{
			System.err.println("An I/O error occured while parsing : \n" + e);
		} catch (LRParserException e) {
			System.err.println("The parser gave up : \n" + e);
		}
	}
}
