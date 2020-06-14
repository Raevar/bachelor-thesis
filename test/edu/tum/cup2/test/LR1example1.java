package edu.tum.cup2.test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;

import static edu.tum.cup2.test.LR1example1.NonTerminals.*;
import static edu.tum.cup2.test.LR1example1.Terminals.*;

public class LR1example1 extends CUP2Specification {
    public enum Terminals implements Terminal {
        and, or, x, y, _true, _false
    }

    public enum NonTerminals implements NonTerminal {
        S, E, A, B, V
    }

    public LR1example1() {
        grammar(getProductionsArray());
    }

    public static Production[] getProductionsArray() {
        Production[] prods = new Production[10];
        prods[0] = new Production(1, S, E);
        prods[1] = new Production(2, E, A);
        prods[2] = new Production(3, E, B);
        prods[3] = new Production(4, E, V);
        prods[4] = new Production(5, A, E, and, E);
        prods[5] = new Production(6, B, E, or, E);
        prods[6] = new Production(7, V, x);
        prods[7] = new Production(8, V, y);
        prods[8] = new Production(9, V, _true);
        prods[9] = new Production(10, V, _false);
        return prods;
    }
}
