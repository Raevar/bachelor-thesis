package edu.tum.cup2.parser.earleyParser;

import edu.tum.cup2.generator.items.EarleyItem;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.parser.EarleyParser;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.test.GrammarExpression;
import org.junit.BeforeClass;

import java.io.IOException;

import static edu.tum.cup2.test.GrammarExpression.Terminals.n;
import static edu.tum.cup2.test.GrammarExpression.Terminals.plus;
import static org.junit.Assert.assertNotNull;

public class ActionWalkerTest {

    /**
     * This is a manual test, determining whether or not actions are working properly.
     * An automated test cannot be done as the console cannot be accessed during automated tests.
     *
     * @author Maximillian Holzvoigt
     */

    private static EarleyParser parser;

    @BeforeClass
    public static void initParser() {
        Grammar grammar = new GrammarExpression().getGrammar();
        parser = new EarleyParser(grammar);
    }

    // This is a manual test if the Parser can correctly walk the parsaTree and walk over its actions
    public static void main(String args[]) throws IOException {
        initParser();
        EarleyItem parsingTree = (EarleyItem) parser.parse(new TestScanner(n, plus, n));
        System.out.println(parsingTree.performAction());
        assertNotNull(parsingTree);
    }
}
