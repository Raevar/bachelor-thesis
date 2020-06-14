package compiler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.ParserObserver;

public class ErrorWriter implements ParserObserver{
	
	PrintStream out = null;
	
	private final String	fileName;

	public ErrorWriter(String fileName) {
		this.fileName = fileName;
		try {
			out = new PrintStream(new FileOutputStream(fileName + ".err"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		out.flush();
		out.close();
	}
	@Override
	public void syntax_error(ErrorInformation error) {
		if (out!=null)
			out.println(error.toString());
	}
	public void flush(){
		out.flush();
		out.close();
	}
}
