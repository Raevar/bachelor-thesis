package docugen;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;


/**
 * Reads the given lines from a text file.
 * 
 * @author Andreas Wenger
 */
public class FileReader
{
	
	public static String read(File file, Integer fromLine, Integer toLine)
		throws IOException
	{
		StringBuilder ret = new StringBuilder();
		BufferedReader in = new BufferedReader(new java.io.FileReader(file));
		String line;
		int i = 1;
		while ((line = in.readLine()) != null)
		{
			if (toLine != null && i > toLine)
				break;
			else if (fromLine == null || i >= fromLine)
				ret.append(line + "\n");
			i++;
    }
		return ret.toString();
	}

}
