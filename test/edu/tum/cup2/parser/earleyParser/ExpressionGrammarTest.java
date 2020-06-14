package edu.tum.cup2.parser.earleyParser;

import edu.tum.cup2.generator.items.EarleyItem;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.parser.EarleyParser;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.test.GrammarExpression;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static edu.tum.cup2.test.GrammarExpression.Terminals.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * This test is used to determine whether or not the Earley Parse functions correctly using the Expression grammar.
 * As later tests build up on the Expression grammar, a correct behaviour within is critical
 *
 * @author Maximillian Holzvoigt
 */
public class ExpressionGrammarTest {


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
     * This method tests a valid sentence
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    @Test
    public void runExpressionGrammarWithValidWord1() throws IOException {
        EarleyItem parsingTree = (EarleyItem) parser.parse(new TestScanner(n, plus, n));
        assertNotNull(parsingTree);
    }

    /**
     * This method tests a valid sentence
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    @Test
    public void runExpressionGrammarWithValidWord2() throws IOException {
        EarleyItem parsingTree = (EarleyItem) parser.parse(new TestScanner(n, plus, minus, n));
        assertNotNull(parsingTree);
    }

    /**
     * This method tests an invalid sentence
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    @Test
    public void runExpressionGrammarWithInvalidWord1() throws IOException {
        EarleyItem parsingTree = (EarleyItem) parser.parse(new TestScanner(n, plus, n, plus));
        assertNull(parsingTree);
    }
}

