register( new ParserInterface() {
  public int getErrorSyncSize() {
    return 0; // recover immediately after error, i.e. do never combine errors.
  }
});

grammar( // your specification here