package ide;

/** A marker inside the text. **/

public abstract class Marker
{
	public boolean	old = false;
	private String	text;

	public abstract int getColumn();
	public abstract int getLine();
	public abstract void moveBy(int line, int column, int numChars, int xDif);
	public String getText() { return text; }
	public void setText(String text) {
		this.text = text;
	}
}
