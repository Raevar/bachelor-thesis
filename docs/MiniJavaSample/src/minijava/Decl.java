package minijava;
import java.util.List;
public class Decl {
    public List<String> varlist;
    public Decl(List<String> l){
	varlist = l;
    }
    public String toString(){
	String ret = "int "+varlist.get(0);
	for (int i = 1; i < varlist.size(); i++) ret += ","+varlist.get(i);
	return ret+";\n";
    }
    public void accept(MiniJavaVisitor v){
	v.preVisit(this);
	v.postVisit(this);
    }
}
