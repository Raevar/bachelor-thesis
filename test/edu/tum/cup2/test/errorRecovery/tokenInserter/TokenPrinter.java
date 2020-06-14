package edu.tum.cup2.test.errorRecovery.tokenInserter;

import java.io.IOException;

import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.scanner.RandomTokenCreator;
import edu.tum.cup2.scanner.Scanner;
import edu.tum.cup2.scanner.ScannerToken;

public class TokenPrinter
	implements Scanner
{

	private Scanner	s;
	private StringBuilder sb = new StringBuilder();

	public TokenPrinter(Scanner s) {
		this.s = s;
	}

	public ScannerToken<? extends Object> readNextTerminal() throws IOException {
		if (s==null)
			return new ScannerToken<String>(SpecialTerminals.EndOfInputStream,"$TokenPrinter$");
		ScannerToken<? extends Object> t = s.readNextTerminal();
		if (t.getSymbol()==SpecialTerminals.EndOfInputStream)
		{
			System.out.println("TokenPrinter : "+ sb.toString());
			s = null;
		}
		else
			sb.append(t+" ");
		return t;
	}

}
