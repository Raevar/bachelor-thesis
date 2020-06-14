package ide.dropdown;

import ide.EditorWindow;
import ide.Marker;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/** Custom drop-down menu. **/

public class DropDownMenu
	extends JPanel
	implements MouseListener, MouseMotionListener
{
	private FontMetrics		fm;
	private Action[]		entries;
	private int				x;
	private int				y;
	public DropDownMenu		next;
	private EditorWindow	editorWindow;
	private Marker			im;
	private int				selected	= -1;
	private JLabel			label;
	private List<Integer>	nulled		= new ArrayList<Integer>();

	public DropDownMenu(EditorWindow editorWindow, Marker im, int x2, int y2,
		Action[] entries
	) {		
		this.editorWindow = editorWindow;
		this.im = im;
		this.entries = entries;
		this.x = x2;
		this.y = y2;

		EventQueue.invokeLater(new Runnable() { public void run() {
			DropDownMenu.this.fm	= getFontMetrics(getFont());
			
			int longest = 10;
			int h = DropDownMenu.this.entries.length*2;
			if (h<1) h = 1;
			int i=0;
			for (Action p:DropDownMenu.this.entries)
			{
				if(p==null || p.text == null){
					h--;
					nulled.add(i);
				}else{
					int w=fm.stringWidth(p.text);
					if (w>longest) longest = w;
				}
				i++;
			}
			
			DropDownMenu.this.setForeground(Color.BLACK);
			DropDownMenu.this.setBackground(Color.WHITE);
			DropDownMenu.this.setBounds(x, y, longest+20, ((h*fm.getHeight())/2)+10);
			DropDownMenu.this.setBorder(BorderFactory.createLineBorder(Color.decode("#000090"),1));
			DropDownMenu.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			DropDownMenu.this.setVisible(true);
			//try{DropDownMenu.this.editorWindow.add(DropDownMenu.this);}catch(NullPointerException $){System.err.println("oops");}
			
			String t = DropDownMenu.this.im.getText(); 
			if (t!=null){
				int lh = fm.getHeight()+2;
				int lw = fm.stringWidth(t)+5;
				int lx = x;
				int ly = y-lh-2;
				if (ly<0)
					ly = y+getHeight()+2;

				label = new JLabel(t);
				/*label.setAlignmentX(SwingConstants.CENTER);
				label.setAlignmentY(SwingConstants.CENTER);*/
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setFont(new Font(getFont().getName(),Font.BOLD,getFont().getSize()));
				label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				label.setForeground(Color.RED);
				label.setOpaque(true);
				try{getParent().add(label);}catch(NullPointerException $){}
				label.setVisible(true);
				label.setBounds(lx,ly,lw,lh);
			}
			DropDownMenu.this.addMouseListener(DropDownMenu.this);
			DropDownMenu.this.addMouseMotionListener(DropDownMenu.this);
		}});
	}
	
	@Override
	public void paint(Graphics g) {
		/*System.out.println(this+" : "+x+","+y);*/
		super.paint(g);
		if (next!=null)
		{
			if (next.x<this.x+this.getWidth())
			{
				next.x = this.x+this.getWidth();
				next.setLocation( next.x , next.y );
				next.repaint();
			}
		}
		int y = 5;
		int i=0;
		for (Action p:DropDownMenu.this.entries)
		{
			if(p==null || p.text == null)
			{
				int ny = y+fm.getHeight()/4;
				g.drawLine(0, ny, this.getWidth(), ny);
				y+=fm.getHeight()/2;
			}
			else
			{
				if (selected==i){
					Color oldg = g.getColor();
					//Color old = getBackground();
					g.setColor(Color.decode("#F0F9FF"));
					g.fillRect( 5, y, this.getWidth()-10,fm.getHeight() );
					//this.setBackground(old);
					
					g.setColor(Color.decode("#99CCFF"));
					g.drawRect( 5, y, this.getWidth()-10,fm.getHeight() );
					g.setColor(oldg);
				}
				y+=fm.getHeight();
				g.drawString(p.text, 10, y-fm.getHeight()/4);
			}
			i++;
		}
	}

	public synchronized void delete()
	{
		EventQueue.invokeLater(new Runnable() {public void run() {
			Container p = DropDownMenu.this.getParent();
			if (p==null) return;
			p.remove(DropDownMenu.this);
			if (label!=null)
				p.remove(label);
			p.repaint();
		}});
	}
	
	/** mouse listener **/

	@Override
	public synchronized void mouseClicked(MouseEvent e) {
		int i = calcIndex(e.getY());
		Action p = entries[i];
		if(p!=null && p.text != null && !(p instanceof NoOpAction))
		{
			if (p instanceof InsertAction)
				editorWindow.insertString(p.text,im.getLine(),im.getColumn(),im);
			else if (p instanceof DeleteAction)
			{
				DeleteAction a= (DeleteAction)p;
				editorWindow.deleteString(a.bC,a.bL,a.eC,a.eL);
			}
			delete();
		}
	}

	private synchronized int calcIndex(int y2) {
		int oldt = 0; int t = 0;
		int h = 0;
		for (int n:nulled)
		{
			oldt = t;
			int tmp = h + (n-t)*fm.getHeight();
			t=n;
			if (tmp>=y2)
				return oldt+(y2-h)/fm.getHeight();
			h=tmp-fm.getHeight()/2;
		}
		t = t+(y2-h)/fm.getHeight();
		if (t>=entries.length) t = entries.length-1;
		if (t<=0) t=0;
		return t;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public synchronized void mouseExited(MouseEvent e) { selected = -1; repaint(); }

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {	
	}
	@Override
	public synchronized void mouseMoved(MouseEvent e) {
		int t = calcIndex(e.getY());
		if (t!=selected)
		{
			selected = t;
			repaint();
		}
	}
	
}
