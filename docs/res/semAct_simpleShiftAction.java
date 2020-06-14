grammar(
  // expr -> expr PLUS [$a] expr
  prod(
    S,rhs(expr,SpecialTerminals.$a,expr),

    // inner action modifying symbol a 
    new Action() {
    public Integer a(Integer a)
    {
        System.out.println("expr -> expr . PLUS expr");
        System.out.println("  a = "+a);
        return Math.abs(a);
    }},

    // semantic REDUCE action processing a,b
    // and the result of the inner action
    new Action() {
    public Integer a(Integer a, Integer resultOfInnerAction, Integer b)
    {
        System.out.println("expr -> expr PLUS expr.");
        System.out.println("  a                       = "+a);
        System.out.println("  result of inner-action  = "+resultOfInnerAction);
        System.out.println("  b                       = "+b);
        return resultOfInnerAction+ Math.abs(c); // returns "|a|+|b|"
     }}
  )


  // expr -> expr PLUS expr
  prod(
    expr,rhs(expr,PLUS,expr),
    new Action(){
    public Integer a(Integer a, Integer b)
    {
        System.out.println("expr -> expr PLUS expr");
        System.out.println("  a = "+a);
        System.out.println("  b = "+b);
        return a+b;
    }}
  )
);