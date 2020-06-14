package compiler;

import minijava.*;
import java.io.*;


/**
 * Creates a control flow graph (CFG) in Metapost format.
 * 
 * @author Michael Petter
 */
public class CFGGenerator
	extends MiniJavaVisitor
{

	PrintWriter writer;


	public CFGGenerator(String filename)
	{
		try
		{
			writer = new PrintWriter(filename + ".cfg.mp");
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}


	public void flush()
	{
		writer.flush();
		writer.close();
	}

	private int boxcounter = 1;


	public boolean preVisit(Program p)
	{
		writer.println("verbatimtex");
		writer.println("%&latex");
		writer.println("\\documentclass{article}");
		writer.println("\\usepackage{graphics}");
		writer.println("\\usepackage{color}");
		writer.println("\\definecolor{dgreen}{rgb}{0.1,0.5,0.1}");
		writer.println("\\begin{document}");
		writer.println("etex");
		writer.println("input expressg;");

		writer.println("beginfig(1);");
		writer.println("z0=(0,0);");
		writer.println("drawovalbox(0,40,17)(btex \\tt start etex);");
		return true;
	}


	public void postVisit(Program p)
	{
		writer.println("z" + boxcounter + "=z" + (boxcounter - 1) + "c-(0," + 30 + ");");
		writer.println("drawovalbox(" + boxcounter + ",40,17)(btex \\tt stop etex);");
		boxcounter++;
		writer
			.println("drawnormalCA(" + (boxcounter - 2) + "bm," + (boxcounter - 1) + "tm);");

		writer.println("endfig;");
		writer.println("end;");
	}


	public boolean preVisit(Stmt.Assign i)
	{
		String text = " " + i.toString();
		int length = (text.length() + 1) * 5;
		writer.println("z" + boxcounter + "c=z" + (boxcounter - 1) + "c-(0," + 30 + ");");
		writer.println("drawENT(" + boxcounter++ + "," + length + ",17)(btex \\tt " + text
			+ " etex);");
		writer
			.println("drawnormalCA(" + (boxcounter - 2) + "bm," + (boxcounter - 1) + "tm);");
		return true;
	}


	public boolean preVisit(Stmt.Write i)
	{
		String text = i.toString();
		int length = 5 + (text.length() + 1) * 5;
		writer.println("z" + boxcounter + "c=z" + (boxcounter - 1) + "c-(0," + 30 + ");");
		writer.println("drawLEVENT(" + boxcounter++ + "," + length + ",17)(btex \\tt " + i
			+ " etex);");
		writer
			.println("drawnormalCA(" + (boxcounter - 2) + "bm," + (boxcounter - 1) + "tm);");
		return true;
	}


	public void visit(Stmt.Read i)
	{
		String text = i.toString();
		int length = 5 + (text.length() + 1) * 5;
		writer.println("z" + boxcounter + "c=z" + (boxcounter - 1) + "c-(0," + 30 + ");");
		writer.println("drawLEVENT(" + boxcounter++ + "," + length + ",17)(btex \\tt " + i
			+ " etex);");
		writer
			.println("drawnormalCA(" + (boxcounter - 2) + "bm," + (boxcounter - 1) + "tm);");
	}


	public boolean preVisit(Stmt.IfThenElse i)
	{
		String text = i.cond.toString();
		int length = 10 + (text.length() + 1) * 5;
		int ifhead = boxcounter;
		writer.println("z" + boxcounter + "c=z" + (boxcounter - 1) + "c-(0," + 30 + ");");
		writer.println("drawdiamondbox(" + boxcounter++ + "," + length + ",25)(btex \\tt "
			+ text + " etex);");
		writer
			.println("drawnormalCA(" + (boxcounter - 2) + "bm," + (boxcounter - 1) + "tm);");
		writer.println("z" + boxcounter + "bm=z" + ifhead + "  shifted (50,0);");
		writer.println("z" + boxcounter + "c=z" + (boxcounter) + "bm;");
		writer.println("label(btex {\\color{dgreen}\\tt yes} etex,z" + boxcounter + "c);");
		writer.println("drawnormal(" + ifhead + "mr," + (boxcounter) + "bm);");
		boxcounter++;
		i.then.accept(this);
		int thenend = boxcounter - 1;
		writer.println("z" + boxcounter + "bm=z" + ifhead + "  shifted (-50,0);");
		writer.println("z" + boxcounter + "c=z" + (boxcounter) + "bm;");
		writer.println("label(btex {\\color{red}\\tt no} etex,z" + boxcounter + "c);");
		writer.println("drawnormal(" + ifhead + "ml," + (boxcounter) + "bm);");
		boxcounter++;
		if (i.els != null)
			i.els.accept(this);

		writer
			.println("z" + boxcounter + "=1/2[z" + thenend + ",z" + (boxcounter - 1) + "];");
		boxcounter++;
		//        writer.println("drawnormal("+thenend+"bm,"+(boxcounter-2)+"c);");
		//        writer.println("drawnormal("+(boxcounter-4)+"bm,"+(boxcounter-3)+"c);");

		writer.println("z" + boxcounter + "=(x" + thenend + "-x" + thenend + ",min(y"
			+ thenend + ",y" + (boxcounter - 1) + ")-30);");
		boxcounter++;
		writer.println("z" + boxcounter + "=(x" + (boxcounter - 3) + "-x" + thenend
			+ ",min(y" + thenend + ",y" + (boxcounter - 2) + ")-30);");
		boxcounter++;

		writer.println("z" + boxcounter + "bm=(x" + (boxcounter - 1) + "-x" + (thenend)
			+ ",min(y" + thenend + ",y" + (boxcounter - 3) + ")-30);");
		writer.println("z" + boxcounter + "c=z" + (boxcounter) + "bm;");
		writer.println("drawnormalthree(" + thenend + "bm," + (boxcounter - 2) + ","
			+ (boxcounter) + "c);");
		writer.println("drawnormalthree(" + (boxcounter - 4) + "bm," + (boxcounter - 1) + ","
			+ (boxcounter) + "c);");
		boxcounter++;

		return false;
	}


	public boolean preVisit(Stmt.Loop i)
	{
		String text = i.cond.toString();
		int length = 10 + (text.length() + 1) * 5;
		int loophead = boxcounter;
		writer.println("z" + boxcounter + "=z" + (boxcounter - 1) + "c-(0," + 30 + ");");
		writer.println("drawdiamondbox(" + boxcounter++ + "," + length + ",25)(btex \\tt "
			+ text + " etex);");
		writer
			.println("drawnormalCA(" + (boxcounter - 2) + "bm," + (boxcounter - 1) + "tm);");
		writer.println("z" + boxcounter + "bm=z" + (boxcounter - 1) + "  shifted (50,0);");
		writer.println("z" + boxcounter + "c=z" + (boxcounter) + "bm;");
		writer.println("label(btex {\\color{dgreen}\\tt yes} etex,z" + boxcounter + "c);");
		writer.println("drawnormal(" + (boxcounter - 1) + "mr," + (boxcounter) + "bm);");
		boxcounter++;
		i.body.accept(this);
		writer.println("z" + boxcounter + "=z" + (boxcounter - 1) + "c shifted (-50,0);");
		writer.println("drawnormalthreeCA(" + (boxcounter - 1) + "ml," + boxcounter + ","
			+ (loophead) + "bm);");
		boxcounter++;
		writer.println("z" + boxcounter + "bm=z" + loophead + " shifted (-50,0);");
		writer.println("z" + boxcounter + "c=z" + (boxcounter) + "bm;");
		writer.println("drawnormal(" + loophead + "ml," + (boxcounter) + "bm);");
		writer.println("label(btex {\\color{red}\\tt no} etex,z" + boxcounter + "c);");
		boxcounter++;
		return false;
	}
}
