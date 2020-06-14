LRParsingTable table = new LR1Generator(new SampleSpec()).getParsingTable(); //here, we use the LR(1) generator
LRParser parser = new LRParser(table);
