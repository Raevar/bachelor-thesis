package edu.tum.cup2.parser.earleyparser;

import edu.tum.cup2.generator.items.EarleyItem;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.Symbol;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * The implementation of the Parse Tree Generator
 * <p>
 * It is initialized with a {@link Grammar} which is used to parse sentence,
 * {@link EarleyParseTableGenerator} which is the parsetable of the sentence to be parsed
 * and the sentence to be parsed.
 * <p>
 * After the initialization the parse tree generator parses the tree accordingly
 *
 * @author Maximillian Holzvoigt
 */
public class EarleyParseTreeGenerator implements Serializable {
    public final long serialVersionUID = 1L;

    private final boolean DEBUG = false;

    private final Grammar grammar;
    private final List<Symbol> word;

    private EarleyParseTableGenerator parsingTable;

    /**
     * This is the constructor of the Earley Parse Tree Generator.
     *
     * @param grammar      the grammar used to parse the sentence
     * @param parsingTable the parsing table generated with the grammar and the sentence
     * @param sentence     the sentence to be parsed
     */
    public EarleyParseTreeGenerator(Grammar grammar, EarleyParseTableGenerator parsingTable, List<Symbol> sentence) {
        if (DEBUG)
            System.out.println("Initializing Parse Tree Generator");
        this.grammar = grammar;
        this.parsingTable = parsingTable;
        parsingTable.deleteUnfinishedItems();
        this.word = sentence;
    }

    /**
     * the method is used to generate the parse tree
     *
     * @return the root node of the parse tree
     */
    public EarleyItem generateParseTree() {
        int setIndicator = word.size();
        int itemIndicator = 0;
        List<EarleyItem> parseList = new LinkedList<>();
        if (DEBUG)
            System.out.println("Adding complete start item to parse list");
        for (EarleyItem production : parsingTable.getLastSet()) {
            if (production.getStart().equals(grammar.getStartProduction().getLHS()) && production.getRightside().equals(grammar.getStartProduction().getRHS())) {
                parseList.add(production);
                break;
            }
        }
        if (DEBUG)
            System.out.println("start of recursion");
        recGenerateParseTree(setIndicator, itemIndicator, parseList);
        if (DEBUG)
            System.out.println("Parsing Tree completed!");

        return parseList.get(0);
    }

    /**
     * a recursive helper method needed to generate the parse tree
     *
     * @param setIndicator  indicates the active set in the parse table
     * @param itemIndicator indicates the active element in the parse list
     * @param parseList     contains elements which result in a possible parse tree of the sentence
     * @return returns if a possible solution is found
     */
    private boolean recGenerateParseTree(int setIndicator, int itemIndicator, List<EarleyItem> parseList) {
        if (DEBUG)
            System.out.println("recursive parse tree generation with set " + setIndicator + " and " + parseList.get(itemIndicator).toString() + "as active item. List contains " + parseList.toString());

        while (setIndicator >= 0) {
            EarleyItem cur_item = parseList.get(itemIndicator);
            if (!cur_item.isUnbegun() && !cur_item.isTerminalPrevious()) //Backward Predictor
            {
                if (DEBUG)
                    System.out.println("backward predictor is called");
                //backward_completer();
                boolean isValidSubTree = false;
                for (EarleyItem loop_item : parsingTable.getEarleySets()[setIndicator]) {
                    if (isPossibleChild(cur_item, loop_item)) {
                        if (DEBUG)
                            System.out.println(loop_item + " is possible child of " + cur_item + ". initializing next level of recursion");

                        parseList.add(itemIndicator + 1, loop_item);
                        if (recGenerateParseTree(setIndicator, itemIndicator + 1, parseList)) {
                            isValidSubTree = true;
                            cur_item.addChild(loop_item);
                            if(DEBUG)
                                System.out.println(loop_item.toString() + " gets added as " + cur_item.toString());

                        }
                        parseList.remove(itemIndicator + 1);
                    }
                }
                if (DEBUG) {
                    if (isValidSubTree)
                        System.out.println("a valid subtree was found.");
                    else
                        System.out.println("no valid subtree was found.");
                }

                return isValidSubTree;
            }
            if (!cur_item.isUnbegun() && cur_item.isTerminalPrevious()) //Backward Scanner
            {
                if (DEBUG)
                    System.out.println("calling backward scanner. Current item get shifted left. Setindicator gets decremented.");

                if (cur_item.getPreviousSymbol().equals(word.get(setIndicator - 1))) {
                    cur_item.shiftLeft();
                    setIndicator--;
                }
            }
            if (cur_item.isUnbegun()) { //Backward Completer
                itemIndicator--;
                if (itemIndicator < 0) {
                    return false;
                }
                cur_item = parseList.get(itemIndicator);
                if(DEBUG)
                    System.out.println("calling backward completer. itemindicator gets decremented. Current item is now " + cur_item.toString());
                cur_item.shiftLeft();
            }
            cur_item = parseList.get(itemIndicator);
            if (cur_item.isUnbegun() && cur_item.getSetIndicator() == 0 && cur_item.getStart().equals(grammar.getStartProduction().getLHS()) && cur_item.getRightside().equals(grammar.getStartProduction().getRHS())) {
                if(DEBUG)
                    System.out.println("unbegun start item is found. Prediction of parse tree is complete");
                return true;
            }
        }
        return false;
    }

    /**
     * this method determines whether or not a {@link EarleyItem} is a possible child of another one in the given context
     *
     * @param cur_item  the possible parent item
     * @param loop_item the possible child item
     * @return the method returns true if the items are possibly parent and child and false otherwise
     */
    private boolean isPossibleChild(EarleyItem cur_item, EarleyItem loop_item) {
        return loop_item.getStart().equals(cur_item.getPreviousSymbol())
                && ((cur_item.getPosition() == 1 && cur_item.getSetIndicator() == loop_item.getSetIndicator())
                || (cur_item.getPosition() > 1 && loop_item.getSetIndicator() - cur_item.getSetIndicator() >= cur_item.getPosition() - 1 - parsingTable.calcNumberOfNullableSymbolsPrevious(cur_item)));
    }
}