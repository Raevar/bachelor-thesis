grammar(
    // S -> \epsilon
    prod(S,     rhs(SpecialTerminals.Epsilon),
        new Action() { public String a() { return ""; }}
    ),
    // S -> EXPR ; S
    prod(S,       rhs(expr, semicolon, S),
        new Action() { public String a(String e, String s)
            { return e+";\n"+s; }}
    ),
    // NEW error-catching production : s -> error SEMI s
    prod(s,       rhs(SpecialTerminals.Error, SEMI, s),
          new Action() {
              public String a(ErrorInformation x, String s)
              { 
                  String location = x.getLine()+"/"+x.getColumn();
                  return "some bad expression at "+location+";\n"+s; 
              }
          }
    ),
    // EXPR -> plus EXPR EXPR
    prod(EXPR,   rhs(plus, EXPR, EXPR),
          new Action() { public String a(String e1, String e2)
              { return "("+e1+" + "+e2+")"; }}
        ),
    // EXPR -> NUMBER
    prod(EXPR,   rhs(number),
          new Action() { public String a(String n)
              { return n; }}
        )
);