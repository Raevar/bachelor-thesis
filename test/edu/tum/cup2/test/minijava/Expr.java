package edu.tum.cup2.test.minijava;


/**
 * @author Michael Petter
 */
public abstract class Expr
	implements Constants
{

	public Expr()
	{
	}

	public static class Priority
		extends Expr
	{

		public Expr e;


		public Priority(Expr e)
		{
			this.e = e;
		}


		public String toString()
		{
			return "(" + e + ")";
		}


		public void accept(MinijavaVisitor v)
		{
			if (!v.preVisit(this))
				return;
			e.accept(v);
			v.postVisit(this);
		}

	}


	public static Expr priority(Expr e)
	{
		return new Priority(e);
	}

	public static class Binex
		extends Expr
	{

		public Expr e1, e2;
		public int op;


		public Binex(Expr e1, int op, Expr e2)
		{
			this.e1 = e1;
			this.e2 = e2;
			this.op = op;
		}


		public String toString()
		{
			String operator = null;
			switch (op)
			{
				case Constants.Unknown: operator = " o "; break;
				case Constants.PLUS: operator = "+"; break;
				case Constants.MINUS: operator = "-"; break;
				case Constants.MULT: operator = "*"; break;
				case Constants.DIV: operator = "/"; break;
				case Constants.MOD: operator = "%"; break;
			}
			return e1 + "" + operator + e2;
		}


		public void accept(MinijavaVisitor v)
		{
			if (!v.preVisit(this))
				return;
			e1.accept(v);
			e2.accept(v);
			v.postVisit(this);
		}

	}


	public static Expr binop(Expr e1, int op, Expr e2)
	{
		return new Binex(e1, op, e2);
	}

	public static class Unex
		extends Expr
	{

		public Expr e1;


		public Unex(Expr e1)
		{
			this.e1 = e1;
		}


		public String toString()
		{
			return "-" + e1;
		}


		public void accept(MinijavaVisitor v)
		{
			if (!v.preVisit(this))
				return;
			e1.accept(v);
			v.postVisit(this);
		}
	}


	public static Expr unop(Expr e)
	{
		return new Unex(e);
	}

	public static class IntConst
		extends Expr
	{

		public int i;


		public IntConst(int i)
		{
			this.i = i;
		}


		public String toString()
		{
			return i + "";
		}


		public void accept(MinijavaVisitor v)
		{
			v.visit(this);
		}
	}


	public static Expr intconst(int i)
	{
		return new IntConst(i);
	}

	public static class Identifier
		extends Expr
	{

		public String i;


		public Identifier(String i)
		{
			this.i = i;
		}


		public String toString()
		{
			return i;
		}


		public void accept(MinijavaVisitor v)
		{
			v.visit(this);
		}
	}


	public static Expr ident(String s)
	{
		return new Identifier(s);
	}


	public abstract void accept(MinijavaVisitor v);
}
