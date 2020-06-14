
import java.lang.reflect.Method;
/**
 * Action Interface for creating an assload of generic Closures
 */
interface IAction {} 
class Action implements IAction {
  static<S1,T1> M1<S1,T1>  m(M1<S1,T1> m) { return m; };
  static<S1,T1,T2> M2<S1,T1,T2>  m(M2<S1,T1,T2> m) { return m; };
  static<S1,T1,T2,T3> M3<S1,T1,T2,T3>  m(M3<S1,T1,T2,T3> m) { return m; };
  static<S1,T1,T2,T3,T4> M4<S1,T1,T2,T3,T4>  m(M4<S1,T1,T2,T3,T4> m) { return m; };
  static<S1,T1,T2,T3,T4,T5> M5<S1,T1,T2,T3,T4,T5>  m(M5<S1,T1,T2,T3,T4,T5> m) { return m; };
  static<S1,T1,T2,T3,T4,T5,T6> M6<S1,T1,T2,T3,T4,T5,T6>  m(M6<S1,T1,T2,T3,T4,T5,T6> m) { return m; };
  static<S1,T1,T2,T3,T4,T5,T6,T7> M7<S1,T1,T2,T3,T4,T5,T6,T7>  m(M7<S1,T1,T2,T3,T4,T5,T6,T7> m) { return m; };
  static<S1,T1,T2,T3,T4,T5,T6,T7,T8> M8<S1,T1,T2,T3,T4,T5,T6,T7,T8>  m(M8<S1,T1,T2,T3,T4,T5,T6,T7,T8> m) { return m; };
  static<S1,T1,T2,T3,T4,T5,T6,T7,T8,T9> M9<S1,T1,T2,T3,T4,T5,T6,T7,T8,T9>  m(M9<S1,T1,T2,T3,T4,T5,T6,T7,T8,T9> m) { return m; };
  static<S1,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> M10<S1,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10>  m(M10<S1,T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> m) { return m; };

  
  static Action create(IAction ia) { 
      Action a = new Action();
      a.init(ia.getClass(),ia);
      return a;
  };
	protected transient Method method; //can not be final as it has to be set after de-serializing the Action object
	protected transient int paramsCount; //can not be final as it has to be set after de-serializing the Action object
	protected transient boolean returnsVoid; //can not be final as it has to be set after de-serializing the Action object
	protected transient Object closure;
	protected Class<? extends IAction> actionSubclass; //this field is actually used for serialization 
	@SuppressWarnings("unchecked")
	public Action()
	{
		//init((Class<? extends IAction>) getClass());
	}
	
	protected void init(Class<? extends IAction> myclass,IAction ia)
	{
		closure = ia;
		actionSubclass = myclass;
		if(myclass == null) return;
		for (Method m : myclass.getMethods())
		{
			if (m.getName().equals("a"))
			{
				this.method = m;
				this.method.setAccessible(true);
				this.paramsCount = this.method.getParameterTypes().length;
				//we are not allowed to call a public method of an inner class,
				//but we can circumvent that
				this.returnsVoid = method.getReturnType().equals(Void.TYPE);
				return;
			}
		}
		//throw new IllegalSpecException("Action has no method called \"a\"");
		throw new RuntimeException("Action has no method called \"a\"");
	}
  
	/**
	 * Executes the Action of this class. By default a method a(..) is searched
	 * over reflections and called with the current parameters. Don't forget to
	 * set the parameters previously!
	 * 
	 * @return
	 */
	public Object doAction(Object[] parameters) throws Exception {
		Method method = getMethod();
		Object ret = method.invoke(closure, parameters);
		if (isVoidReturn())
			//return NoValue;
			return null;
		else
			return ret;
	}
	/**
	 * Gets the method assigned to this semantic action.
	 */
	public Method getMethod()
	{
		return method;
	}
	
	/**
	 * Gets the number of parameters the method accepts.
	 */
	public int getParamsCount()
	{
		return paramsCount;
	}
	
	/**
	 * Returns true, if the result type of the method is void.
	 */
	public boolean isVoidReturn()
	{
		return returnsVoid;
	}
	
	/**
	 * Returns the Name of the Action class
	 */
	protected String getActionSubclassName()
	{
		if(null == this.actionSubclass) return null;
		return this.actionSubclass.getName();
	}
	
// 	private transient LRParser parserInstance = null; //associated parser instance
// 	/**
// 	 * Returns the associated parser instance
// 	 */
// 	public LRParser getParser()
// 	{
// 		return this.parserInstance;
// 	}
// 	
// 	/**
// 	 * Sets the associated parser instance
// 	 */
// 	public void setParser(LRParser p)
// 	{
// 		this.parserInstance = p;
// 	}



  interface M1<R,P1> extends IAction {
    R a(P1 p1);
  }
  interface M2<R,P1,P2> extends IAction {
    R a(P1 p1,P2 p2);
  }
  interface M3<R,P1,P2,P3> extends IAction {
    R a(P1 p1,P2 p2,P3 p3);
  }
  interface M4<R,P1,P2,P3,P4> extends IAction {
    R a(P1 p1,P2 p2,P3 p3,P4 p4);
  }
  interface M5<R,P1,P2,P3,P4,P5> extends IAction {
    R a(P1 p1,P2 p2,P3 p3,P4 p4,P5 p5);
  }
  interface M6<R,P1,P2,P3,P4,P5,P6> extends IAction {
    R a(P1 p1,P2 p2,P3 p3,P4 p4,P5 p5,P6 p6);
  }
  interface M7<R,P1,P2,P3,P4,P5,P6,P7> extends IAction {
    R a(P1 p1,P2 p2,P3 p3,P4 p4,P5 p5,P6 p6,P7 p7);
  }
  interface M8<R,P1,P2,P3,P4,P5,P6,P7,P8> extends IAction {
    R a(P1 p1,P2 p2,P3 p3,P4 p4,P5 p5,P6 p6,P7 p7,P8 p8);
  }
  interface M9<R,P1,P2,P3,P4,P5,P6,P7,P8,P9> extends IAction {
    R a(P1 p1,P2 p2,P3 p3,P4 p4,P5 p5,P6 p6,P7 p7,P8 p8,P9 p9);
  }
  interface M10<R,P1,P2,P3,P4,P5,P6,P7,P8,P9,P10> extends IAction {
    R a(P1 p1,P2 p2,P3 p3,P4 p4,P5 p5,P6 p6,P7 p7,P8 p8,P9 p9,P10 p10);
  }
}

public class Closures {
  public static void main(String[] args) throws Exception {
    IAction m = Action.m((String s)->  s+" processed");
    IAction n = Action.m((Integer i, Double j) -> "Fucker");
    Action.M1 m1= Action.m((String i) -> i+i);
    m1.a(Action.m((Integer i, Double j) -> "Fucker").a(5,5.5));

    Action.create(m).doAction(new Object[]{"Hallo"});

  }
}