package edu.tum.cup2.generator.items;

import edu.tum.cup2.grammar.*;
import edu.tum.cup2.semantics.ActionPerformer;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class EarleyItem extends LR0Item implements Cloneable {

    private static final long serialVersionUID = 1L;

    private final static boolean DEBUG = false;

    private static int productionIdentifier = 0;

    private int setIndicator;

    protected boolean unbegun;
    protected Symbol previousSymbol;

    private LinkedList<EarleyItem> children;

    public EarleyItem(Production production, int position, int setIndicator) {
        super(production, position);
        this.setIndicator = setIndicator;
        this.unbegun = (position == 0);
        if (!this.unbegun) {
            previousSymbol = production.getRHS().get(position - 1);
        } else {
            previousSymbol = null;
        }
        children = new LinkedList<>();
    }

    public static int getGlobalProductionIdentifier() {
        return productionIdentifier++;
    }

    public List<EarleyItem> predict(Grammar grammar, Set<Symbol> nullableSymbols, int i) {
        if (DEBUG) {
            System.out.println("Start of prediction of production " + toString());
        }

        LinkedList<EarleyItem> productions = new LinkedList<>();
        NonTerminal nextSymbol = (NonTerminal) production.getRHS().get(position);

        for (Production prod : grammar.getProductions()) {
            if (!prod.getLHS().equals(nextSymbol)) {
                continue;
            }
            EarleyItem earleyItem = new EarleyItem(new Production(productionIdentifier++, prod.getLHS(), prod.getRHS(), prod.getReduceAction(), prod.getPrecedenceTerminal()), 0, i);
            productions.add(earleyItem);
            if (DEBUG) {
                System.out.println("Prediction of " + earleyItem.toString());
            }

        }
        if (nullableSymbols.contains(production.getLHS())) {
            EarleyItem item = shift();
            productions.add(item);
            if (DEBUG) {
                System.out.println("As the production is nullable " + item.toString() + " is added");
            }
        }
        if (DEBUG) {
            System.out.println("End of prediction of production " + toString());
        }
        return productions;
    }

    public EarleyItem scan() {
        EarleyItem item = new EarleyItem(new Production(productionIdentifier++, production.getLHS(), production.getRHS(), production.getReduceAction(), production.getPrecedenceTerminal()), position + 1, setIndicator);
        if (DEBUG) {
            System.out.println("Scan produced " + item.toString() + " from " + toString());
        }
        return item;
    }

    public List<EarleyItem> complete(List<EarleyItem>[] earleySets) {
        if (DEBUG) {
            System.out.println("Start of completion of production " + toString());
        }
        LinkedList<EarleyItem> items = new LinkedList<>();
        for (EarleyItem earleyItem : earleySets[getSetIndicator()]) {
            if (earleyItem.isShiftable() && production.getLHS().equals(earleyItem.getNextSymbol())) {
                EarleyItem completedItem = earleyItem.shift();
                items.add(completedItem);
                if (completedItem.isComplete()) {
                    items.addAll(completedItem.complete(earleySets));
              }
                if (DEBUG) {
                    System.out.println("Completion of" + earleyItem.toString());
                }
            }
        }
        if (DEBUG) {
            System.out.println("End of prediction of production " + toString());
        }
        return items;
    }


    public void shiftLeft() {
        position--;
        this.unbegun = (position == 0);//|| position > 0 && production.getRHS().get(position - 1).equals(SpecialTerminals.Epsilon);
        if (!this.unbegun) {
            previousSymbol = production.getRHS().get(position - 1);
        } else {
            previousSymbol = null;
        }
    }

    @Override
    public EarleyItem shift(){
        return new EarleyItem(production, position + 1, this.getSetIndicator());
    }

    public Object performAction() {
        Stack<Object> valueStack = new Stack<>();
        performAction(valueStack);
        return valueStack.peek();
    }

    protected void performAction(Stack<Object> valueStack) {
        for (EarleyItem child : children) {
            child.performAction(valueStack);
        }
        if (production.getReduceAction() != null) {
            Object tmp = (ActionPerformer.perform(production.getReduceAction(), valueStack, calcNumberOfNonTerminals()));
            for (int i = 0; i < calcNumberOfNonTerminals(); i++)
                valueStack.removeElementAt(valueStack.size() - 1);
            valueStack.add(tmp);
        }
    }


    private int calcNumberOfNonTerminals() {
        int x = 0;
        for (Symbol symbol : production.getRHS()) {
            if (symbol instanceof NonTerminal)
                x++;
        }
        return x;
    }

    public void addChild(EarleyItem child) {
        children.add(child);
    }

    public boolean isUnbegun() {
        return unbegun;
    }

    public boolean isEmpty() {
        return isShiftable() && isUnbegun();
    }

    public boolean isTerminalNext() {
        return nextSymbol != null && nextSymbol instanceof Terminal;
    }

    public boolean isTerminalPrevious() {
        return previousSymbol != null && previousSymbol instanceof Terminal;
    }

    public int getSetIndicator() {
        return setIndicator;
    }

    public NonTerminal getStart() {
        return production.getLHS();
    }

    public Symbol getPreviousSymbol() {
        return previousSymbol;
    }

    public List<Symbol> getRightside() {
        return production.getRHS();
    }

    public LinkedList<EarleyItem> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object other) {

        if (other instanceof EarleyItem) {
            return production.equals(((EarleyItem) other).production)
                    && ((EarleyItem) other).getPosition() == position;
        }
        return false;
    }

    @Override
    public String toString() {
        return production.toString(position) + ", " + setIndicator;
    }

    public LinkedList<EarleyItem> treeToList() {
        LinkedList<EarleyItem> list = new LinkedList<>();
        list.add(this);
        for (EarleyItem child : children) {
            list.addAll(child.treeToList());
        }
        return list;
    }

}