package edu.tum.cup2.test;

import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;

import static edu.tum.cup2.test.GrammarAmbiguous.NonTerminals.*;
import static edu.tum.cup2.test.GrammarAmbiguous.Terminals.x;

public class GrammarAmbiguous extends CUP2Specification {

    public enum Terminals implements Terminal {
        x
    }

    public enum NonTerminals implements NonTerminal {
        P, S
    }

    public GrammarAmbiguous() {
        grammar(getProductionsArray());
    }

    public static Grammar generateGrammar() {
        Grammar grammar = new Grammar(Terminals.values(), NonTerminals.values(), getProductionsArray());
        return grammar;
    }

    public static Production[] getProductionsArray() {
        Production[] prods = new Production[3];
        prods[0] = new Production(0, P, S);
        prods[1] = new Production(1, S, S, S);
        prods[2] = new Production(2, S, x);
        return prods;
    }
}