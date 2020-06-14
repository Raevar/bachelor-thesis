package edu.tum.cup2.parser;

import edu.tum.cup2.parser.tables.LRParsingTable;

/**
 * Little Wrapper for an LRParser used for testing Serialization
 * 
 * @author Michael Hausmann
 *
 */
@SuppressWarnings("serial")
public class LRParserTestWrapper extends LRParser {
	
	/**
	 * Creates a new TestParser, using the given {@link LRParsingTable}.
	 */
	public LRParserTestWrapper(LRParsingTable table)
	{
		super(table);
	}
	
	/**
	 * Create a TestParser from a "normal" LRParser
	 * @param p reference to the LRParser whose ParsingTable is used to create the TestParser
	 */
	public LRParserTestWrapper(LRParser p)
	{
		super(p.table);
	}
	
	/**
	 * returns the LRParsingTable
	 * @return LRParsingTable
	 */
	public LRParsingTable getParsingTable()
	{
		return super.table;
	}
}
