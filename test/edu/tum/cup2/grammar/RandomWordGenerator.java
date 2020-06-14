package edu.tum.cup2.grammar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.tum.cup2.generator.GrammarInfo;
import edu.tum.cup2.generator.LALR1Generator;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.exceptions.LRParserException;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.SpecJava14;


/**
 * This class generates a random word from a given grammar.
 * 
 * @author Andreas Wenger
 */
public class RandomWordGenerator
{
	
	private final Grammar grammar;
	private final GrammarInfo grammarInfo;
	
	private final HashMap<NonTerminal, List<Terminal>> shortestWords =
		new HashMap<NonTerminal, List<Terminal>>();
	
	
	public RandomWordGenerator(Grammar grammar)
	{
		this.grammar = grammar;
		this.grammarInfo = new GrammarInfo(grammar);
		//check grammar. each non-terminal must be reachable and productive
		CheckedGrammar checked = new CheckedGrammar(grammar);
		//TODO: how to use that class?
		
		//compute shortest words for each non-terminal (ok, not really the shortest in all cases,
		//but quite short words, this is enough for us!)
		//(this algorithm was not taken from any book, but constructed by the author of the class)
		LinkedList<NonTerminal> queue = new LinkedList<NonTerminal>(grammar.getNonTerminals());
		while (!queue.isEmpty())
		{
			//for each non-terminal, where we still don't know a shortest word,
			//look, if there is a production with only terminals and already known non-terminals
			//at the RHS
			Iterator<NonTerminal> unknownNTIt = queue.iterator();
			while (unknownNTIt.hasNext())
			{
				NonTerminal unknownNT = unknownNTIt.next();
				for (Production prod : grammarInfo.getProductionsFrom(unknownNT))
				{
					//look at RHS: only terminals and known symbols?
					boolean ok = true;
					for (Symbol s : prod.getRHS())
					{
						if (!(s instanceof Terminal) && shortestWords.get(s) == null)
						{
							//RHS contains still unknown nonterminal
							ok = false;
							break;
						}
					}
					if (ok)
					{
						//we had success. collect the terminals for the shortest word.
						LinkedList<Terminal> shortestWord = new LinkedList<Terminal>();
						for (Symbol s : prod.getRHS())
						{
							if (s instanceof Terminal)
							{
								if (s != SpecialTerminals.Epsilon)
									shortestWord.add((Terminal) s);
							}
							else
							{
								shortestWord.addAll(shortestWords.get(s));
							}
						}
						shortestWords.put(unknownNT, shortestWord);
						System.out.println(unknownNT + " -->  " + shortestWord); //TEST
						//remove from the queue
						unknownNTIt.remove();
						break;
					}
				}
			}
		}
	}
	
	
	public LinkedList<Terminal> createRandomWord()
	{
		LinkedList<Symbol> queue = new LinkedList<Symbol>();
		LinkedList<Terminal> ret = new LinkedList<Terminal>();
		//begin with start non-terminal
		queue.add(grammar.getStartProduction().getLHS());
		boolean findAnEnd = false;
		while (!queue.isEmpty())
		{
			//if queue/word is already longer than 1000 symbols, restart
			//(we are possibly lost in the depth of the grammar) 
			if (!findAnEnd && (queue.size() > 1000 || ret.size() > 1000))
			{
				findAnEnd = true;
			}
			//dequeue first symbol. if terminal, copy to ret.
			//if non-terminal, take a random production with this LHS
			//and add it in front of the queue.
			Symbol symbol = queue.removeFirst();
			if (symbol instanceof Terminal)
			{
				if (symbol != SpecialTerminals.Epsilon)
					ret.addLast((Terminal) symbol);
			}
			else
			{
				NonTerminal nonTerminal = (NonTerminal) symbol;
				if (!findAnEnd)
				{
					//random production
					List<Production> productions = grammarInfo.getProductionsFrom(nonTerminal);
					Production production = productions.get((int) (Math.random() * productions.size()));
					LinkedList<Symbol> newQueue = new LinkedList<Symbol>(production.getRHS());
					newQueue.addAll(queue);
					queue = newQueue;
				}
				else
				{
					//use shortest word to come to an end soon
					ret.addAll(shortestWords.get(nonTerminal));	
				}
			}
		}
		return ret;
	}
	
	
	public static void main(String[] args)
		throws Exception
	{
		//create random Java 1.4 word
		CUP2Specification spec = new SpecJava14();
		Grammar grammar = spec.getGrammar();
		RandomWordGenerator gen = new RandomWordGenerator(grammar);
		LRParsingTable table = new LALR1Generator(spec).getParsingTable();
		int i = 0;
		while (true)
		{
			LinkedList<Terminal> word = gen.createRandomWord();
			if (i > 1000000)
			{
				//System.out.println(word);
				break;
			}
			//word must be parseable
			try
			{
				Terminal[] t = new Terminal[word.size()];
				word.toArray(t);
				LRParser parser = new LRParser(table);
				parser.parse(new TestScanner(t));				
			}
			catch (LRParserException ex)
			{
				System.out.println("FAIL for: " + word);
				System.out.println("FAIL!!! Correct word could not be parsed: " + ex.getMessage());
				return;
			}
			//now, add some terminals at any place. if CYK algorithm tells us that
			//the word is still valid, the parser must accept it, otherwise it must reject it
			int insertCount = 5 + (int) (Math.random() * 5);
			for (int i2 = 0; i2 < insertCount; i2++)
			{
				word.add((int) (Math.random() * word.size()),
					grammar.getTerminals().get((int) (Math.random() * grammar.getTerminals().size())));
			}
			try
			{
				Terminal[] t = new Terminal[word.size()];
				word.toArray(t);
				LRParser parser = new LRParser(table);
				parser.parse(new TestScanner(t));
				System.out.println("FAIL for: " + word);
				System.out.println("FAIL!!! Incorrect word was declared as valid by the parser.");
				return;
			}
			catch (LRParserException ex)
			{
			}
			System.out.println(++i);
		}
	}
	
	
	
	

}
