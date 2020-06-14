package ide.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import compiler.MiniJavaLexer;

import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.exceptions.EndOfInputstreamException;
import edu.tum.cup2.parser.exceptions.MissingErrorRecoveryException;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.InsertedScannerToken;
import edu.tum.cup2.scanner.InsertionScanner;
import edu.tum.cup2.scanner.Scanner;
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.ParserObserver;

/** 
 * A thread which is able to validate a file using
 * a given table. Supports insertion. *
 **/

public class Validator
	extends Thread
{

	public final static int mode_doNothing	  				= 0x00; 
	public final static int mode_getErrors   				= 0x01;
	public final static int mode_getInsertions 				= 0x02; 
	public final static int mode_getErrorsAfterInsertion   	= 0x06;

	private ValidationObserver	observer;
	private LRParsingTable	t;
	private int	mode;
	private String	fileName;
	
	private final ParserObserver	obst = new ParserObserver(){
		@Override
		public void syntax_error(ErrorInformation error) {
			observer.validationResult(error);
		}
	};
	
	private static LinkedList<Validator> validators = new LinkedList<Validator>();

	public Validator(ValidationObserver observer, LRParsingTable table, 
		String fileName, int mode
	) {
		this.observer = observer;
		this.mode = mode;
		t = table;
		this.fileName = fileName;
		synchronized(validators){
			validators.add(this);
		}
		this.start();
	}

	@Override
	public void run() {	
		Object z = null;	
		try {
			if ((mode&mode_getErrors)!=0){
				/** Real parse ... **/
				try {
					MiniJavaLexer scanner;
					scanner = new MiniJavaLexer(new java.io.FileReader(fileName));
					if (scanner==null) throw new Exception("Could not create scanner!");
					LRParser p = new LRParser(t);
					p.register(new ParserObserver(){
						@Override
						public void syntax_error(ErrorInformation error) {
							observer.validationResult(error);
						}
					});
					z = p.parse(scanner);
				} 
				catch (EndOfInputstreamException e) {}
				catch (MissingErrorRecoveryException e) {}
			}
			
			if ((mode&mode_getInsertions)!=0) {
				InsertionScanner scanner = null;
				try {
					MiniJavaLexer mjl = new MiniJavaLexer(new java.io.FileReader(fileName));
					scanner= new InsertionScanner( t, mjl );
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (scanner==null) throw new Exception("Could not create scanner!");
				if ((mode&mode_getErrorsAfterInsertion)!=0){
					/** Real parse with adapted scanner **/
					try {
						LRParser p = new LRParser(t);
						p.register(obst);
						z = p.parse(scanner);
					} 
					catch (EndOfInputstreamException e) {}
					catch (MissingErrorRecoveryException e) {}
				}else{
					/** Parse for insertions only **/
					InsertedScannerToken<?>[] s = scanner.scan4allInsertions();
					for (InsertedScannerToken<?> t : s) {
						observer.validationResult(t.getErrorInformation());
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			observer.validationFailed();
			synchronized (validators) {
				validators.remove(this);
			}
			return;
		}
		finally
		{
			synchronized (validators) {
				validators.remove(this);
			}
		}
		observer.validationDone(z);
	}

	public static void stopAll() {
		synchronized (validators) {
			if (validators.size()>0)
				System.out.println("Stopping "+validators.size()+" validatiors...");
			for (int i=0; i<validators.size(); i++)
			{
				Validator v = validators.get(i);
				synchronized (v) {
					v.stop();
					validators.remove(i--);
				}
			}
		}
		Thread.yield();
	}

	public static boolean validatorsActive() {
		synchronized (validators) {
			return validators.size()!=0;
		}
	}
	
}
