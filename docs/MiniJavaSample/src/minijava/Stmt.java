package minijava;
import java.util.List;

import minijava.util.Ident;
public abstract class Stmt {
    public static class Loop extends Stmt {
	public Stmt body;
	public Cond cond;
	public Loop(Cond c, Stmt t){
	    cond = c;
	    body= t;
	}
	public String toString(){
	    return "while ("+cond+")"+ body+"\n"+Ident.get();
	}
	public void accept(MiniJavaVisitor v){
	    if (!v.preVisit(this)) return;
	    cond.accept(v);
	    body.accept(v);
	    v.postVisit(this);
	}
    }
    public static Stmt whileloop(Cond c, Stmt s){
	return new Loop(c,s);
    }
    public static class IfThenElse extends Stmt {
	public Stmt then;
	public Stmt els;
	public Cond cond;
	public IfThenElse(Cond c, Stmt t, Stmt e){
	    cond = c;
	    then = t;
	    els  = e;
	}
	public String toString(){
	    return "if("+cond+")"+ then + ((els==null)?"":("else "+els))+"\n"+Ident.get();
	}
	public void accept(MiniJavaVisitor v){
	    if (!v.preVisit(this)) return;
	    cond.accept(v);
	    then.accept(v);
	    if (els!=null) els.accept(v);
	    v.postVisit(this);
	}
    }
    public static Stmt ifthenelse(Cond c, Stmt t, Stmt e){
	return new IfThenElse(c,t,e);
    }
    public static Stmt ifthen(Cond c, Stmt t){
	return new IfThenElse(c,t,null);
    }
    public static class Write extends Stmt {
	public Expr e;
	public Write(Expr e){
	    this.e = e;
	}
	public String toString(){
	    return "write("+e+");\n"+Ident.get();
	}
	public void accept(MiniJavaVisitor v){
	    if (!v.preVisit(this)) return;
	    e.accept(v);
	    v.postVisit(this);
	}
    }
    public static Stmt write(Expr e){
	return new Write(e);
    }
    public static class Read extends Stmt {
	public String lhs;
	public Read(String s){
	    lhs = s;
	}
	public String toString(){
	    return lhs+"=read();\n"+Ident.get();
	}
	public void accept(MiniJavaVisitor v){
	    v.visit(this);
	}
    }
    public static Stmt read(String s){
	return new Read(s);
    }
    public static class Assign extends Stmt {
	public String lhs;
	public Expr rhs;
	public Assign(String s, Expr e){
	    lhs = s;
	    rhs = e;
	}
	public String toString(){
	    return lhs+"="+rhs+";\n"+Ident.get();
	}
	public void accept(MiniJavaVisitor v){
	    v.preVisit(this);
	    rhs.accept(v);
	    v.postVisit(this);
	}
    }
    public static Stmt assign(String i, Expr e){
	return new Assign(i,e);
    }
    public static class Compound extends Stmt {
	public List<Stmt> ls;
	public Compound(List<Stmt> l){
	    ls = l;
	}
	public String toString(){
		Ident.inc();
	    String ret= "{\n"+Ident.get();
	    for (Stmt s : ls) ret += s;
	    Ident.dec();
	    return ret.substring(0,ret.length()-2)+"}";
	}
	public void accept(MiniJavaVisitor v){
	    if (!v.preVisit(this)) return;
	    for (Stmt s: ls) s.accept(v);
	    v.postVisit(this);
	}
    }
    public static Stmt compound(List<Stmt> l){
	return new Compound(l);
    }
    public static class Empty extends Stmt {
	public Empty(){
	}
	public void accept(MiniJavaVisitor v){
	    v.visit(this);
	}
	@Override
		public String toString() {
			return ";";
		}
    }
    public static Stmt empty(){
	return new Empty();
    }
    public abstract void accept(MiniJavaVisitor v);

}
