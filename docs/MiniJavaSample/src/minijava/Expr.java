package minijava;

import static minijava.Constants.*;


public abstract class Expr
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


		public void accept(MiniJavaVisitor v)
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
			if (op == Constants.PLUS)
				operator = "+";
			if (op == Constants.MINUS)
				operator = "-";
			if (op == Constants.MULT)
				operator = "*";
			if (op == Constants.DIV)
				operator = "/";
			if (op == Constants.MOD)
				operator = "\\%";
			return e1 + "" + operator + e2;
		}


		public void accept(MiniJavaVisitor v)
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


		public void accept(MiniJavaVisitor v)
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


		public void accept(MiniJavaVisitor v)
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


		public void accept(MiniJavaVisitor v)
		{
			v.visit(this);
		}
	}


	public static Expr ident(String s)
	{
		return new Identifier(s);
	}


	public abstract void accept(MiniJavaVisitor v);
}
