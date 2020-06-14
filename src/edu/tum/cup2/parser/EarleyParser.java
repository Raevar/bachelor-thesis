package edu.tum.cup2.parser;

import edu.tum.cup2.generator.items.EarleyItem;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Symbol;
import edu.tum.cup2.parser.earleyparser.EarleyParseTableGenerator;
import edu.tum.cup2.parser.earleyparser.EarleyParseTreeGenerator;
import edu.tum.cup2.scanner.Scanner;
import edu.tum.cup2.scanner.ScannerToken;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Implementation of an LR parser.
 * <p>
 * It is initialized with a {@link Grammar} which is used over the whole
 * lifetime of this parser.
 * <p>
 * When a {@link Scanner} is given, the input is parsed accordingly.
 *
 * @author Maximillian Holzvoigt
 */
public class EarleyParser extends AParser implements Serializable {
    private static final long serialVersionUID = 1L;

    private final boolean DEBUG = false;

    private Scanner input = null;

    private Grammar grammar;
    private LinkedList<Symbol> sentence;
    private EarleyParseTableGenerator parseTable;
    private EarleyParseTreeGenerator parseTree;

    /**
     * This is the constructor of the Earley Parser. A object of the Earley Parser is to parse an input.
     *
     * @param grammar the grammar which is used the generate a sentence
     */
    public EarleyParser(Grammar grammar) {
        this.grammar = grammar;
    }

    /**
     * Parses the stream of symbols that can be read from the given
     * {@link Scanner}. A single parser-instance may not be used for
     * concurrent parsing!
     *
     * @param input    the input scanner containing the sentence to be parsed
     * @param initArgs can be ignored
     * @return if the sentence is valid, i.e. can be porse using the grammar, the parsetree is generated and the root item is returned. Otherwise null.
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    @Override
    public synchronized Object parse(Scanner input, Object... initArgs) throws IOException {
        parseTree = null;
        if (!initSentence(input)) {
            return null;
        }

        parseTable = new EarleyParseTableGenerator(grammar, sentence);

        Object ret = null;
        if (parseTable.generateParseTable()) {
            //At the moment the ActionPerformer is purely in the EarleyItems and the parser returns the ParseTree.
            //If the default should be to walk over the actions instead of returning the parseTree, the return statement beneath needs to be switched.
            //  return generateparseTree().performAction();
            ret = generateParseTree();
        }
        this.input = null;
        return ret;
    }

    /**
     * This method generates a parsetable and forfeits the generation of the parse tree. This parse table contains
     * incomplete {@link EarleyItem} since other implementations of the parse tree generator may need them.
     *
     * @param input the scanner containing the sentence to be parsed
     * @return the {@link EarleyParseTableGenerator} containing the set of nullable grammars and the Earley sets
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    public synchronized EarleyParseTableGenerator generateParseTable(Scanner input) throws IOException {
        if (!initSentence(input))
            return null;
        parseTable = new EarleyParseTableGenerator(grammar, sentence);
        parseTable.generateParseTable();
        this.input = null;
        return parseTable;
    }

    /**
     * can be called to generate the parse tree. If the {@Link parseTable} does
     * not exist already the {@Link parseTable} is created previously
     *
     * @return the root item of the parsing the
     */
    public EarleyItem generateParseTree() {
        if (parseTable == null) {
            parseTable = new EarleyParseTableGenerator(grammar, sentence);
            parseTable.generateParseTable();
        }
        parseTree = new EarleyParseTreeGenerator(grammar, parseTable, sentence);
        return parseTable.isValid() ? parseTree.generateParseTree() : null;
    }

    /**
     * reads the sentence from the scanner and puts the symbols in a linkedList
     *
     * @param input the scanner containing the sentence to be parsed
     * @throws IOException Re-thrown in case of the scanner throwing IOException
     */
    private synchronized boolean initSentence(Scanner input) throws IOException {
        if (this.input != null)
            return false;
        this.input = input;
        sentence = new LinkedList<>();

        ScannerToken<? extends Object> currentToken;
        while ((currentToken = input.readNextTerminal()).getSymbol() != SpecialTerminals.EndOfInputStream) {
            sentence.add(currentToken.getSymbol());
        }
        return true;
    }
}