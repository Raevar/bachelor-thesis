package docugen;

import java.io.File;

import org.w3c.dom.Document;


public class Main
{
	
	
	public static void main(String[] args)
	{
		
		//first parameter must be XML file
		if (args.length < 1)
		{
			errFatal("First parameter must be the input file");
		}
		File file = new File(args[0]);
		if (!file.exists())
		{
			errFatal("File not found");
		}
		Document doc = null;
		try
		{
			doc = XMLReader.readFile(file);
		}
		catch (Exception ex)
		{
			errFatal("Invalid XML file: " + ex.getMessage());
		}
		
		//transform document
		Factory factory = new Factory(doc, file.getParentFile());
		
		//save it at the same path as output.html
		File outFile = new File(file.getParent(), "output.html");
		try
		{
			XMLWriter.writeFile(factory.getDocument(), outFile);
		}
		catch (Exception ex)
		{
			errFatal("Error when writing file: " + ex.getMessage());
		}
	}
	
	
	public static void err(String message)
	{
		System.err.println("Error: " + message);
	}
	
	
	public static void errFatal(String message)
	{
		err(message);
		System.exit(0);
	}
	
}
