	public enum Terminals
		implements Terminal
	{
		SEMICOLON,
		COMMA,
		LPAR,
    ...
		COMP,
		BBINOP,
		IDENT;
		public Insertable	insert	= null; // <- tweak
	}

	public SpecMiniJava() {

		precedences(left(ELSE, UNOP, BINOP, BUNOP, BBINOP));
    /** **/
		/** Tokens to be inserted. Highest precedence : TYPE **/
    insert( TYPE          , new String[] { "int" }, 0 );
    insert( COMP          , new String[] { "<",">","==","<=",">=","!=" }, Constants.Unknown );
    insert( COMMA         , new String[] { "," } );
    insert( SEMICOLON, 1  , new String[] { ";" } ); // ";" must not be inserted one after another
    insert( BINOP         , new String[] { "+","-","*","/" }, Constants.Unknown );
    insert( ASSIGN        , new String[] {" = "} );
    insert( IF            , new String[] { "if", "while" } ); // if/while differ mainly in semantics.
    insert( RPAR          , new String[] { ")" } );
    insert( LPAR, 1       , new String[] { "(" } ); // "(" must not be inserted one after another
      
    /** Start of grammar **/
    grammar(
      ...
  }
  
  public Object repairAndParse(Scanner inputScanner) throws GeneratorException, IOException, LRParserException
  {
      LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();
      Scanner inserter = new InsertionScanner(table,inputScanner);
      return (new LRParser(table)).parse(table,inserter);
  }
  
  public void displayRepairs(Scanner inputScanner) throws GeneratorException, IOException
  {
      LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();
      InsertionScanner inserter = new InsertionScanner(table,inputScanner);
      for (InsertedScannerToken<?> tkn : inserter.scan4allInsertions())
        System.out.println(tkn.getErrorInformation());
  }
  
  public void displaySolution(Scanner inputScanner, int bufSize, int maxOversize, int maxPossibilities )
  {
      LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();
      Scanner inserter = new InsertionScanner(table,inputScanner,bufSize,maxOversize,maxPossibilities);
      ScannerToken<Object> t;
      do {
          t = inserter.readNextTerminal();
          System.out.print(t+" ");
      } while ( t.getSymbol()!=SpecialTerminals.EndOfInputStream );
  }
  