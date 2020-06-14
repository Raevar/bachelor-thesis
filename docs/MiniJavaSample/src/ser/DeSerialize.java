package ser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import compiler.MiniJavaLexer;

import edu.tum.cup2.io.LRParserSerialization;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.exceptions.LRParserException;
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.ParserObserver;

public class DeSerialize
{

	/**
	 * @param args
	 * @throws IOException 
	 * @throws  
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		LRParserSerialization s = new LRParserSerialization("MiniJava.cup2");
		LRParser p = s.loadParser();
		if (p==null){
			System.err.println("Deserialization failed");
			System.exit(-1);
		}
		try {
			p.register(new ParserObserver(){
				@Override
				public void syntax_error(ErrorInformation error) {
					
				}
			});
			System.out.println(p.parse(new MiniJavaLexer(new FileReader("input.minijava"))));
		} catch (LRParserException e) {
			System.out.println(e);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
