package edu.tum.cup2.parser.earleyParser;

import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.parser.EarleyParser;
import edu.tum.cup2.parser.earleyparser.EarleyParseTableGenerator;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.test.GrammarExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static edu.tum.cup2.test.GrammarExpression.Terminals.n;
import static edu.tum.cup2.test.GrammarExpression.Terminals.plus;
import static org.junit.Assert.assertTrue;

/**
 * this test determines whether or not the correct parse table can be generated using the Earley Parser as implemented.
 * Therefore the {@link GrammarExpression} contains a method with a manually generated parse table.
 * The test determines whether or not the programmatically generated parse table is equal to the manually generated one.
 *
 * @author Maximillian Holzvoigt
 */
public class ParseTableTest {

    private static EarleyParser parser;

    /**
     * This method initializes the grammar and the Earley Parser used for later tests
     */
    @BeforeClass
    public static void initParser() {
        Grammar grammar = new GrammarExpression().getGrammar();
        parser = new EarleyParser(grammar);
    }

    /**
     * This method tests a small valid sentence
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    @Test
    public void testGeneratedParseTableEqualsManuallyCreated() throws IOException {
        EarleyParseTableGenerator parsingTable = parser.generateParseTable(new TestScanner(n, plus, n));
        assertTrue(GrammarExpression.validifyParsingTableWithManualGeneratedParsingTable(parsingTable));
    }
}