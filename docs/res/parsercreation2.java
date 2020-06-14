//create and save parsing table
try
{
	LALR1Generator generator = new LALR1Generator(new your.path.SampleSpec()); //we want to use LALR(1)
	LRParsingTable table = generator.getParsingTable(); //get the resulting parsing table
	LRParser parser = new LRParser(table); //create a new LR parser using our table
	LRParserSerialization serial = new LRParserSerialization("myfilename"); //create serializer
	serial.saveParser(parser); //serialize parser
}
catch (GeneratorException ex)
{
    //creation failed. evaluate ex for more details.
}
catch(Exception ex)
{
    //parsing failed. evaluate ex for more details
}

//... later/elsewhere ...

//anywhere else (also on other machine, where the file "myfilename" is available)
LRParserSerialization serial = new LRParserSerialization("myfilename"); //create serializer
LRParser parser = serial.loadParser(); //have fun!
try{
  Object result = parser.parse(new MyScanner("34-2*13;"));
}
catch(Exception ex)
{
    //parsing failed. evaluate ex for more details
}