package ide.markers;

import ide.Marker;

import java.util.Arrays;

import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.semantics.ErrorInformation;

/**
 *	A marker for insertions.
 *
 *  TODO : Move GUI-painting to this class, too 
 *  
 ***/

public class InsertMarker extends Marker
{

	public int	x;
	public int	y;
	public final String[]	proposals;
	public final Terminal[]	expectedTerminals;
	public ErrorInformation	error;
	private int	line;
	private int	column;
	
	public InsertMarker(ErrorInformation error, String errorText, int x, int y, Object[] proposals,
		Terminal[] expectedTerminals
	) {
		setText( errorText );
		this.error = error;
		this.x = x;
		this.y = y;
		this.line = error.getCrashLine()-1;
		this.column = error.getCrashColumn()-1;
		if (proposals==null)
			this.proposals = new String[0];
		else {
			this.proposals = new String[proposals.length];
			int i = 0;
			for (Object o : proposals)
				this.proposals[i++] = (String) o;
		}
		if (expectedTerminals==null)
			this.expectedTerminals = new Terminal[0];
		else
			this.expectedTerminals = expectedTerminals;
	}
	
	@Override
	public void moveBy(int line, int column, int numChars, int dif) {
		if (this.line == line && this.column >= column) {
			this.column += numChars;
			this.x += dif;
		}
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
	
}
