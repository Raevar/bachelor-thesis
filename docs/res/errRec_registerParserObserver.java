//create the parser
LRParser parser = new LRParser(table);

// register an observer
parser.register(
    new ParserObserver()
    {
      public void syntax_error(ErrorInformation error) {
        if (error.isRecovered())
          System.err.println("A syntax error occured :\n"+error);
        else
          System.err.println("An unrecoverable syntax error occured :\n"+error);
      }
    }
);

// parse the input
Object result = parser.parse( input );