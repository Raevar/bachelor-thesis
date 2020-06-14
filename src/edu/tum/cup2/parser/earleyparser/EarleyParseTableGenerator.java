package edu.tum.cup2.parser.earleyparser;

import edu.tum.cup2.generator.items.EarleyItem;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Symbol;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Implementation of the Earley Parse Table Generator.
 * <p>
 * It is initialized with the grammar and the sentence which shall be parsed and is used over the whole lifetime of a parse.
 * It contains the parse table
 *
 * @author Maximillian Holzvoigt
 */

public class EarleyParseTableGenerator implements Serializable {
    public final long serialVersionUID = 1L;

    private final boolean DEBUG = false;

    private final Grammar grammar;

    private List<EarleyItem>[] earleySets;
    private List<Symbol> word;
    private Set<Symbol> nullableSymbols;

    /**
     * this is the constructor of the Earley Parse Table Generator
     *
     * @param grammar  the grammar used to parse
     * @param sentence the sentence to be parsed
     */
    public EarleyParseTableGenerator(Grammar grammar, List<Symbol> sentence) {
        if (DEBUG) {
            System.out.println("initialising parsetable");
        }
        this.grammar = grammar;
        this.word = sentence;
        initializeEarleySets();
        generateNullableRules();
    }

    /**
     * This is the mainloop used the generate the parse table.
     * @return if the sentence can be parsed using the grammar it returns true. Otherwise, false
     */
    public boolean generateParseTable() {
        if (DEBUG) {
            System.out.println("start of recognizer");
        }
        for (int i = 0; i < earleySets.length; i++) {
            Set<Symbol> predictionSet = new HashSet<>();

            //every stack has concurrent modifications, therefore while and not for
            int x = 0;
            while (x < earleySets[i].size()) {
                EarleyItem cur_item = earleySets[i].get(x);
                if (cur_item.isComplete()) {
                    for (EarleyItem new_item : cur_item.complete(earleySets)) {
                        if (!earleySets[i].contains(new_item))
                            earleySets[i].add(new_item);
                    }
                } else {
                    if (cur_item.isTerminalNext()) {
                        if (i + 1 != earleySets.length && cur_item.getNextSymbol().equals(word.get(i))) {
                            earleySets[i + 1].add(cur_item.scan());
                        }
                    } else {
                        if (predictionSet.add(cur_item.getNextSymbol())) {
                            for (EarleyItem new_item : cur_item.predict(grammar, nullableSymbols, i)) {
                                if (!earleySets[i].contains(new_item))
                                    earleySets[i].add(new_item);
                            }
                        }
                    }
                }
                x++;
            }
        }
        return isValid();
    }

    /**
     * This method initializes the necessary Earley sets. Therefore, for every symbol in the sentence + 1 sets are created.
     * Furthermore, an Earley item containing the unbegun startProduction of the {@link Grammar} is added to the zeroth set.
     */
    private void initializeEarleySets() {
        earleySets = new LinkedList[word.size() + 1];
        for (int i = 0; i < earleySets.length; i++) {
            earleySets[i] = new LinkedList<EarleyItem>();
        }
        for (Production production : grammar.getProductions()) {
            if (production.getLHS().equals(grammar.getStartProduction().getLHS())) {
                earleySets[0].add(new EarleyItem(new Production(EarleyItem.getGlobalProductionIdentifier(), production.getLHS(), production.getRHS(), production.getReduceAction(), production.getPrecedenceTerminal()), 0, 0));
            }
        }
    }

    /**
     * This method calculates all nullable symbols in a grammar
     */
    private void generateNullableRules() {
        nullableSymbols = new HashSet<>();
        nullableSymbols.add(SpecialTerminals.Epsilon);
        int delta;
        do {
            delta = nullableSymbols.size();
            for (Production production : grammar.getProductions()) {
                boolean nullable = true;
                for (Symbol s : production.getRHS()) {
                    if (!nullableSymbols.contains(s)) {
                        nullable = false;
                        break;
                    }
                }
                if (nullable) {

                    if (nullableSymbols.add(production.getLHS()) && DEBUG) {
                        System.out.println("Productions with " + production.getLHS() + " as startSymbol are nullable");
                    }
                }
            }
        } while (delta != nullableSymbols.size());
    }

    /**
     * this method returns whether or not a sentence can be parsed within a grammar
     * @return if the sentence can be parsed within the given grammar true is returned. Otherwise, false.
     */
    public boolean isValid() {
        for (EarleyItem production : earleySets[earleySets.length - 1]) {
            if (production.getSetIndicator() == 0 && production.getStart().equals(grammar.getStartProduction().getLHS()) && production.isComplete()) {
                if (DEBUG) {
                    System.out.println("The completed Startproduction " + production.toString() + " was found in the last stack. The word is parseable");
                }
                return true;
            }
        }
        if (DEBUG) {
            System.out.println(grammar.getStartProduction().toString() + " was not found in lastStack. Word is not parseable");
        }
        return false;
    }

    /**
     * this method deletes all incomplete items from the parsing table. It is used in the initial step of the parse tree implementation.
     */
    public void deleteUnfinishedItems() {
        if (DEBUG) {
            System.out.println("Unfinished EarleyItems in Stacks get deleted");
        }
        for (List<EarleyItem> set : earleySets) {
            int x = set.size() - 1;
            while (x >= 0) {
                EarleyItem item = set.get(x);
                if (!item.isComplete()) {
                    set.remove(item);
                    if (DEBUG) {
                        System.out.println("Deletion of " + item.toString());
                    }
                }
                x--;
            }

        }
    }

    /**
     * this method calculates the number of nullable symbols on the left side of the marker
     * @param cur_item the item over which the calculation is necessary
     * @return the number of nullable symbols on the left side of the marker
     */
    public int calcNumberOfNullableSymbolsPrevious(EarleyItem cur_item) {
        int x = 0;
        for (int i = 0; i < cur_item.getPosition(); i++) {
            if (nullableSymbols.contains(cur_item.getProduction().getRHS().get(i)))
                x++;
        }
        return x;
    }

    public List<EarleyItem>[] getEarleySets() {
        return earleySets;
    }

    public List<EarleyItem> getLastSet() {
        return earleySets[earleySets.length - 1];
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof EarleyParseTableGenerator) {
            List<EarleyItem>[] otherStacks = ((EarleyParseTableGenerator) other).getEarleySets();
            if (otherStacks.length != earleySets.length)
                return false;
            for (int i = 0; i < earleySets.length; i++) {
                if (otherStacks[i].size() != earleySets[i].size())
                    return false;
                for (EarleyItem production : otherStacks[i]) {
                    if (!earleySets[i].contains(production))
                        return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
