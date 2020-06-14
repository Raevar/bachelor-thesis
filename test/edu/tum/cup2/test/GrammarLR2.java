package edu.tum.cup2.test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;

import static edu.tum.cup2.test.GrammarLR2.NonTerminals.*;
import static edu.tum.cup2.test.GrammarLR2.Terminals.*;

/**
 * Grammar, taken from
 * https://smlweb.cpsc.ucalgary.ca/vital-stats.php?grammarfile=example-grammars/ll2-lr2-1.cfg
 * A copy is placed in
 * "Documentation/Other/EarleyParser - Example LR2.pdf"
 *
 * @author Andreas Wenger
 */
public class GrammarLR2 extends CUP2Specification {
    public enum Terminals implements Terminal {
        b, e, o, r, s
    }

    public enum NonTerminals implements NonTerminal {
        P, A, B, E, SL
    }

    public GrammarLR2() {
        grammar(getProductionsArray());
    }

    public static Production[] getProductionsArray() {
        Production[] prods = new Production[9];
        prods[0] = new Production(1, P, A);
        prods[1] = new Production(2, A, E, B, SL, E);
        prods[2] = new Production(3, A, b, e);
        prods[3] = new Production(4, B, b);
        prods[4] = new Production(5, B, o, r);
        prods[5] = new Production(6, E, e);
        prods[6] = new Production(7, E);
        prods[7] = new Production(8, SL, s, SL);
        prods[8] = new Production(9, SL, s);
        return prods;
    }
}
