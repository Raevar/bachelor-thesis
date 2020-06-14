package edu.tum.cup2.scanner;

import java.util.List;

/**
 * Extended BufferedScanner for Unit testing.
 * 
 * @author Michael Hausmann
 */

public class BufferedScannerForTesting extends BufferedScanner {

	public BufferedScannerForTesting(Scanner usedScanner)
			throws IllegalArgumentException {
		super(usedScanner);
	}
	
	public int getBufferLength()
	{
		return this.fNumberOfBufferedTokens;
	}

	public List<ScannerToken<?>> getBuffer()
	{
		return this.fTokenQueue;
	}
}
