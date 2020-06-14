new ScannerToken<Integer>(NUMBER, 5); //create a token with value
new ScannerToken<Integer>(PLUS); //create a token with no value

new ScannerToken<Integer>(NUMBER, 5, 10, 25); //create a token with value, read on line 10 and column 25
new ScannerToken<Integer>(PLUS, 10, 28); //create a token with no value, read on line 10 and column 28
