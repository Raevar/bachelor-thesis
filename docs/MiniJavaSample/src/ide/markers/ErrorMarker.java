package ide.markers;

import ide.Marker;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.semantics.ErrorInformation;

/**
 *	A marker for normal parse-errors.
 *
 *  TODO : Move GUI-painting to this class, too 
 *  
 ***/

public class ErrorMarker extends Marker
{

	public int				x;
	public int				y;
	public int				beginLine;
	public int				beginColumn;
	public int				endLine;
	public int				endColumn;
	public Terminal[]		expectedTerminals;
	public int				xEnd;
	public int				yEnd;
	public int				xStart;
	public int				yStart;
	public ErrorInformation	error;
	private int				column;
	private int				line;

	public ErrorMarker(ErrorInformation error, String errorText, int xpos, int ypos,
		int xStart, int yStart, int xEnd, int yEnd, Terminal[] expectedTerminals)
	{
		this.line = error.getCrashLine()-1;
		setText( errorText );
		this.column = error.getCrashColumn()-1;
		this.error = error;
		this.beginLine = error.getBeginLine()-1;
		this.beginColumn = error.getBeginColumn()-1;
		this.endLine = error.getEndLine()-1;
		this.endColumn = error.getEndColumn()-1;
		this.x = xpos;
		this.y = ypos;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.xStart = xStart;
		this.yStart = yStart;
		if (expectedTerminals==null)
			this.expectedTerminals = new Terminal[0];
		else
			this.expectedTerminals = expectedTerminals;
	}

	@Override
	public int getColumn() {
		// TODO Auto-generated method stub
		return column;
	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return line;
	}

	@Override
	public void moveBy(int line, int column, int numChars, int dif) {
		if (beginLine == line && beginColumn >= column) {
			this.beginColumn += numChars;
			this.xStart += dif;
		}
		if (endLine == line && beginColumn >= column) {
			this.endColumn += numChars;
			this.xEnd += dif;
		}
		if (this.line == line && this.column >= column) {
			this.column += numChars;
			this.x += dif;
		}
	}

}
