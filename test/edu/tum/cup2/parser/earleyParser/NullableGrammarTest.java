package edu.tum.cup2.parser.earleyParser;

import edu.tum.cup2.parser.EarleyParser;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.test.GrammarNullable;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static edu.tum.cup2.test.GrammarNullable.Terminals.a;
import static org.junit.Assert.*;

/**
 * This test is used to determine whether or not the Earley Parser functions correctly while parsing a sentence within a nullable grammar.
 *
 * @author Maximillian Holzvoigt
 */
public class NullableGrammarTest {

    private static EarleyParser parser;

    /**
     * This method initializes the grammar and the Earley Parser used for later tests
     */
    @BeforeClass
    public static void initParser() {
        Grammar grammar = new GrammarNullable().getGrammar();
        parser = new EarleyParser(grammar);
    }

    /**
     * This method tests a valid sentence
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    @Test
    public void testValidInput() throws IOException {
        assertNotNull(parser.parse(new TestScanner(a, a)));
    }

    /**
     * This method tests an invalid sentence
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    @Test
    public void testInvalidInput() throws IOException {
        assertNull(parser.parse(new TestScanner(a, a, a)));
    }
}
