package ide.markers;

import ide.Marker;

public class UnrecoverableMarker
	extends Marker
{

	public int	x;
	public int	y;

	public UnrecoverableMarker(int x, int y) {
		this.x=x;
		this.y=y;
	}

	@Override
	public int getColumn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void moveBy(int line, int column, int numChars, int dif) {
		// TODO Auto-generated method stub

	}

}
