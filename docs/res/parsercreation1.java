try
{
    LALR1Generator generator = new LALR1Generator(new your.path.SampleSpec()); //we want to use LALR(1)
    LRParsingTable table = generator.getParsingTable(); //get the resulting parsing table
    LRParser parser = new LRParser(table); //create a new LR parser using our table
    Object result = parser.parse(new MyScanner("25+17*2;")); //apply parser to a token stream
}
catch (GeneratorException ex)
{
    //creation failed. evaluate ex for more details.
}
catch(Exception ex)
{
    //parsing failed. evaluate ex for more details
}
