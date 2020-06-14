Expression result = (Expression)parser.parse(
	new GeneratedScanner(new FileReader("TestInput.txt"))); //contains "13+25*23+4/2*((4+3)*2)*5"
//result must be 728
assertEquals(result.evaluate(), 728);
