package edu.tum.cup2.test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;

import static edu.tum.cup2.test.GrammarNullable.NonTerminals.*;
import static edu.tum.cup2.test.GrammarNullable.Terminals.a;

public class GrammarNullable extends CUP2Specification {

    public enum Terminals implements Terminal {
        a, times, dividedBy,
    }

    public enum NonTerminals implements NonTerminal {
        S, A, B, C
    }

    public GrammarNullable() {
        grammar(getProductionsArray());
    }

    public static Production[] getProductionsArray() {

        Production[] prods = new Production[4];
        prods[0] = new Production(1, S, A, B, C);
        prods[1] = new Production(1, A, a);
        prods[2] = new Production(1, B);
        prods[3] = new Production(1, C, a);
        return prods;
    }
}
