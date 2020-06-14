package edu.tum.cup2.test;

import edu.tum.cup2.generator.items.EarleyItem;
import edu.tum.cup2.grammar.*;
import edu.tum.cup2.parser.earleyparser.EarleyParseTableGenerator;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.spec.CUP2Specification;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static edu.tum.cup2.test.GrammarExpression.NonTerminals.*;
import static edu.tum.cup2.test.GrammarExpression.Terminals.*;

public class GrammarExpression extends CUP2Specification {

    public enum Terminals implements Terminal {
        n, plus, minus, times, over, bracketOpen, bracketClose,
    }

    public enum NonTerminals implements NonTerminal {
        S, E, T, F
    }

    public GrammarExpression() {
        grammar(getProductionsArray());
    }

    public static Grammar generateGrammar() {
        Grammar grammar = new Grammar(Terminals.values(), NonTerminals.values(), getProductionsArray());
        return grammar;
    }

    public static Production[] getProductionsArray() {


        Production[] prods = new Production[11];
        prods[0] = new Production(1, S, Arrays.asList(new Symbol[]{E}));
        prods[1] = new Production(2, E, Arrays.asList(new Symbol[]{E, plus, T}), new Action() {
            public int a(int a, int b) {
                return a + b;
            }
        });
        prods[2] = new Production(3, E, Arrays.asList(new Symbol[]{E, minus, T}), new Action() {
            public int a(int a, int b) {
                return a + b;
            }
        });
        prods[3] = new Production(4, E, Arrays.asList(new Symbol[]{T}));
        prods[4] = new Production(5, T, Arrays.asList(new Symbol[]{T, times, F}), new Action() {
            public int a(int a, int b) {
                return a * b;
            }
        });
        prods[5] = new Production(6, T, Arrays.asList(new Symbol[]{T, over, F}), new Action() {
            public int a(int a, int b) {
                return a / b;
            }
        });
        prods[6] = new Production(7, T, F);
        prods[7] = new Production(8, F, Arrays.asList(new Symbol[]{n}), new Action() {
            public int a() {
                System.out.println("Insert number:");
                Scanner myInput = new Scanner(System.in);
                return myInput.nextInt();
            }
        });
        prods[8] = new Production(9, F, Arrays.asList(new Symbol[]{plus, F}), new

                Action() {
                    public int a(int a) {
                        return a;
                    }
                });

        prods[9] = new Production(10, F, Arrays.asList(new Symbol[]{minus, F}), new

                Action() {
                    public int a(int a) {
                        return -a;
                    }
                });
        prods[10] = new Production(11, F, Arrays.asList(new Symbol[]{bracketOpen, E, bracketClose}), new Action() {
            public int a(int a) {
                return a;
            }
        });
        return prods;
    }



    public static boolean validifyParsingTableWithManualGeneratedParsingTable(EarleyParseTableGenerator parsingTable) {

        List<EarleyItem>[] earleySets = new LinkedList[4];

        if (parsingTable.getEarleySets().length != earleySets.length)
            return false;

        earleySets[0] = new LinkedList();
        earleySets[0].add( new EarleyItem(new Production(0, S, Arrays.asList(E)), 0, 0));
        earleySets[0].add( new EarleyItem(new Production(1, E, Arrays.asList(E,plus,T)), 0, 0));
        earleySets[0].add( new EarleyItem(new Production(2, E, Arrays.asList(E,minus,T)), 0, 0));
        earleySets[0].add( new EarleyItem(new Production(3, E, Arrays.asList(T)), 0, 0));
        earleySets[0].add( new EarleyItem(new Production(4, T, Arrays.asList(T,times,F)), 0, 0));
        earleySets[0].add( new EarleyItem(new Production(5, T, Arrays.asList(T,over,F)), 0, 0));
        earleySets[0].add( new EarleyItem(new Production(6, T, Arrays.asList(F)), 0, 0));
        earleySets[0].add( new EarleyItem(new Production(7, F, Arrays.asList(n)), 0, 0));
        earleySets[0].add( new EarleyItem(new Production(8, F, Arrays.asList(plus,F)), 0, 0));
        earleySets[0].add( new EarleyItem(new Production(9, F, Arrays.asList(minus,F)), 0, 0));
        earleySets[0].add( new EarleyItem(new Production(10, F, Arrays.asList(bracketOpen,E,bracketClose)), 0, 0));
        earleySets[1] = new LinkedList();
        earleySets[1].add( new EarleyItem(new Production(11, F, Arrays.asList(n)), 1, 0));
        earleySets[1].add( new EarleyItem(new Production(12, T, Arrays.asList(F)), 1, 0));
        earleySets[1].add( new EarleyItem(new Production(13, E, Arrays.asList(T)), 1, 0));
        earleySets[1].add( new EarleyItem(new Production(14, S, Arrays.asList(E)), 1, 0));
        earleySets[1].add( new EarleyItem(new Production(15, E, Arrays.asList(E,plus,T)), 1, 0));
        earleySets[1].add( new EarleyItem(new Production(16, E, Arrays.asList(E,minus,T)), 1, 0));
        earleySets[1].add( new EarleyItem(new Production(17, T, Arrays.asList(T,times,F)), 1, 0));
        earleySets[1].add( new EarleyItem(new Production(18, T, Arrays.asList(T,over,F)), 1, 0));
        earleySets[2] = new LinkedList();
        earleySets[2].add( new EarleyItem(new Production(19, E, Arrays.asList(E,plus,T)), 2, 0));
        earleySets[2].add( new EarleyItem(new Production(20, T, Arrays.asList(T,times,F)), 0, 2));
        earleySets[2].add( new EarleyItem(new Production(21, T, Arrays.asList(T,over,F)), 0, 2));
        earleySets[2].add( new EarleyItem(new Production(22, T, Arrays.asList(F)), 0, 2));
        earleySets[2].add( new EarleyItem(new Production(23, F, Arrays.asList(n)), 0, 2));
        earleySets[2].add( new EarleyItem(new Production(24, F, Arrays.asList(plus,F)), 0, 2));
        earleySets[2].add( new EarleyItem(new Production(25, F, Arrays.asList(minus,F)), 0, 2));
        earleySets[2].add( new EarleyItem(new Production(26, F, Arrays.asList(bracketOpen,E,bracketClose)), 0, 2));
        earleySets[3] = new LinkedList();
        earleySets[3].add( new EarleyItem(new Production(27, F, Arrays.asList(n)), 1, 2));
        earleySets[3].add( new EarleyItem(new Production(28, T, Arrays.asList(F)), 1, 2));
        earleySets[3].add( new EarleyItem(new Production(29, E, Arrays.asList(E,plus,T)), 3, 0));
        earleySets[3].add( new EarleyItem(new Production(30, S, Arrays.asList(E)), 1, 0));
        earleySets[3].add( new EarleyItem(new Production(31, E, Arrays.asList(E,plus,T)), 1, 0));
        earleySets[3].add( new EarleyItem(new Production(32, E, Arrays.asList(E,minus,T)), 1, 0));
        earleySets[3].add( new EarleyItem(new Production(33, T, Arrays.asList(T,times,F)), 1, 2));
        earleySets[3].add( new EarleyItem(new Production(34, T, Arrays.asList(T,over,F)), 1, 2));


        for (int i = 0; i < earleySets.length; i++) {
            List<EarleyItem> programmaticallyGeneratedStack = parsingTable.getEarleySets()[i];
            List<EarleyItem> validationStack = earleySets[i];
            if (programmaticallyGeneratedStack.size() != validationStack.size())
                return false;
            for (int j = 0; j < validationStack.size(); j++) {
                if (!validationStack.contains(programmaticallyGeneratedStack.get(j)))
                    return false;
            }
        }
      return true;
    }

    public static boolean validifyParsingTableWithManualGeneratedParseTree(EarleyItem parseTreeRoot) {

        LinkedList<EarleyItem> automaticallyGeneratedParseTreeList = parseTreeRoot.treeToList();
        LinkedList<EarleyItem> manuallyGeneratedParseTreeList = new LinkedList<>();

        manuallyGeneratedParseTreeList.add((new EarleyItem(new Production(0, S, Arrays.asList(E)), 0, 0)));
        manuallyGeneratedParseTreeList.add((new EarleyItem(new Production(0, E, Arrays.asList(E, plus, T)), 0, 0)));
        manuallyGeneratedParseTreeList.add((new EarleyItem(new Production(0, E, Arrays.asList(T)), 0, 0)));
        manuallyGeneratedParseTreeList.add((new EarleyItem(new Production(0, T, Arrays.asList(F)), 0, 0)));
        manuallyGeneratedParseTreeList.add((new EarleyItem(new Production(0, F, Arrays.asList(n)), 0, 0)));
        manuallyGeneratedParseTreeList.add((new EarleyItem(new Production(0, T, Arrays.asList(F)), 0, 2)));
        manuallyGeneratedParseTreeList.add((new EarleyItem(new Production(0, F, Arrays.asList(n)), 0, 2)));
        if (automaticallyGeneratedParseTreeList.size() != manuallyGeneratedParseTreeList.size())
            return false;
        for (int i = 0; i < automaticallyGeneratedParseTreeList.size(); i++) {
            if (!automaticallyGeneratedParseTreeList.contains(manuallyGeneratedParseTreeList.get(i)))
                return false;
        }
        return true;
    }
}
