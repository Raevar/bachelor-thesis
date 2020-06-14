package edu.tum.cup2.parser.earleyParser;

import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.parser.EarleyParser;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.test.GrammarLR2;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static edu.tum.cup2.test.GrammarLR2.Terminals.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * This test is used to determine whether or not the Earley parse as implemented is able to parse sentences in grammars higher ranking than LR(1).
 * The used grammar is LR(2) and originites from https://smlweb.cpsc.ucalgary.ca/vital-stats.php?grammarfile=example-grammars/ll2-lr2-1.cfg
 *
 * @author Maximillian Holzvoigt
 */
public class LR2Test {

    private static EarleyParser parser;

    /**
     * This method initializes the grammar and the Earley Parser used for later tests
     */
    @BeforeClass
    public static void initParser() {
        Grammar grammar = new GrammarLR2().getGrammar();
        parser = new EarleyParser(grammar);
    }

    /**
     * This method tests a small valid sentence
     *
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    @Test
    public void testValidInput() throws IOException {
        assertNotNull(parser.parse(new TestScanner(b, s, s)));
    }

    /**
     * This method tests a small valid sentence
     *
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    @Test
    public void testInvalidInput() throws IOException {
        assertNull(parser.parse(new TestScanner(b, o, e, r, s, e)));
    }
}
