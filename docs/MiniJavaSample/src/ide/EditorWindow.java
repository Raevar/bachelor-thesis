package ide;

import ide.dropdown.Action;
import ide.dropdown.DeleteAction;
import ide.dropdown.DropDownMenu;
import ide.dropdown.InsertAction;
import ide.dropdown.NoOpAction;
import ide.markers.ErrorMarker;
import ide.markers.InsertMarker;
import ide.markers.UnrecoverableMarker;
import ide.util.MiniJavaSpec$Terminal2Strings;
import ide.util.ValidationObserver;
import ide.util.Validator;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter.HighlightPainter;

import compiler.MiniJavaSpec;

import edu.tum.cup2.generator.LALR1SCCGenerator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.semantics.ErrorInformation;

/** The editor's window.
 * 
 * 	Implements GUI-update and validation logic.
 * 
 *	TODO : Separate logic from GUI in source-code
 ***/

public class EditorWindow
	extends JFrame
	implements ActionListener, ValidationObserver, HighlightPainter,
	MouseListener, ItemListener, WindowListener
{
	/** parser stuff **/
	private LRParsingTable				table;

	/** communication with specification **/
	private static LinkedList<String>	errorTxts		= new LinkedList<String>();

	/** GUI elements **/
	private JEditorPane					text			= new JEditorPane();
	private Button						btn_save		= new Button("Save");
	private Button						btn_load		= new Button("Load");
	private Button						btn_validate	= new Button("Format");
	private JCheckBox					chkBox_Ins		= new JCheckBox(
															"insert token(s)", true);
	private Button						btn_jvmCode		= new Button("Create JVM code");

	/** content **/
	private String						fileName		= null;
	private String						origTxt;
	private LinkedList<Marker>			markers			= null;
	private FontMetrics					fm				= text.getFontMetrics(text
															.getFont());

	/** validation **/
	ValidIt								validIt			= null;
	private final boolean				DEBUG			= false;
	private boolean						format			= false;							// format text after validation?
	private boolean						doIns			= true;							// insert tokens during validation?

	public EditorWindow() {

		/** initialize grammar-features **/
		try {
			table = (new LALR1SCCGenerator(new MiniJavaSpec())).getParsingTable();
		} catch (Exception e) {
			if (e instanceof GeneratorException)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			RuntimeException e2 = new RuntimeException("Could not init parser-table : ");
			e2.initCause(e);
			throw e2;
		}

		/** init gui **/
		EventQueue.invokeLater(new Runnable() {

			private final String	startText =
				"int sum a, b;\n" +
				"c, d;\n" +
				"2<sum) {\n" +
				"  sum = sum +1\n" +
				"  c = (-(c));\n" +
				"  d = read;"
			;

			public void run() {
				/** window **/
				setSize(600, 500);
				setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				EditorWindow.this.addWindowListener(EditorWindow.this);
				setVisible(true);
				setLocationRelativeTo(null);
				/** layout **/
				GridBagLayout gridbag = new GridBagLayout();
				GridBagConstraints c = new GridBagConstraints();
				setLayout(gridbag);
				c.fill = GridBagConstraints.BOTH;
				/** row 0 **/
				/** load button **/
				add(btn_save);
				c.weightx = 0.2;
				gridbag.setConstraints(btn_save, c);
				/** save button **/
				add(btn_load);
				c.weightx = 0.2;
				gridbag.setConstraints(btn_load, c);
				/** row 1 **/
				/** validation button **/
				add(btn_validate);
				c.weightx = 0.2;
				gridbag.setConstraints(btn_validate, c);
				/** generate button **/
				add(btn_jvmCode );
				c.weightx = 0.2;
				c.gridwidth = GridBagConstraints.REMAINDER; //end row
				gridbag.setConstraints(btn_jvmCode, c);
				/** main view **/
				/** text input pane **/
				text.setText(startText);
				Font f = new Font("Monospaced", Font.PLAIN, 16);
				if (f == null)
					f = new Font("Tahoma", Font.PLAIN, 16);
				if (f == null)
					f = getFont();
				text.setFont(f);
				add(text);
				text.setLayout(null);
				c.weightx = 1.0;
				c.weighty = 1.0; // full view
				c.gridwidth = GridBagConstraints.REMAINDER; //end row
				gridbag.setConstraints(text, c);
				/** row N **/
				add(chkBox_Ins);
				c.weightx = 1.0;
				gridbag.setConstraints(text, c);

				/** update GUI**/
				fm = text.getFontMetrics(text.getFont());
			}
		});
		btn_load.addActionListener(this);
		btn_save.addActionListener(this);
		btn_jvmCode.addActionListener(this);
		btn_validate.addActionListener(this);
		text.addMouseListener(this);
		chkBox_Ins.addItemListener(this);
		//text.setHighlighter(new BasicTextUI.BasicHighlighter());
		try {
			text.getHighlighter().addHighlight(0, 0, this);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/** output fonts **/
		/*
		for(Font y:GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts())
			System.out.println(y);
			*/
	}

	/** Button Listener **/

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_load) {		
			// TODO : choose filename
			fileName = "saved.minijava";
			load(fileName);
		} else
		if (e.getSource() == btn_save) {			
			// TODO : choose filename
			fileName = "saved.minijava";
			save(fileName);
		} else
		if (e.getSource() == btn_validate) {
			synchronized (this) {
				format = true;
				vali();	
			}
		} else if (e.getSource()== btn_jvmCode){
			save("tmp_ide_jvm.minijava");
			try {
				compiler.Main.main(new String[]{"-jvm","tmp_ide_jvm.minijava"});
			} catch (GeneratorException e1) {
				e1.printStackTrace();
			}
		}
	}

	private boolean vali() {
		save("tmp_ide.minijava");
		/*if (Validator.validatorsActive())
			return false;*/
		clearValidationResults();
		new Validator(this, table, "tmp_ide.minijava", doIns?Validator.mode_getErrorsAfterInsertion:Validator.mode_getErrors );
		return true;
	}

	/** File functions **/

	private void load( String fileName ) {
		if (fileName == null) return;
		clearValidationResults();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(new File(fileName)));
			//in.
			StringBuilder sb = new StringBuilder();
			try {
				while (true) {
					String s = in.readLine();
					if (s == null)
						break;
					sb.append(s + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			origTxt = sb.toString(); // TODO : Remove this line?
			text.setText(origTxt);
		} catch (FileNotFoundException e) {
			// TODO : Inform GUI !!
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		vali();	
	}

	private void save( String fileName ) {
		if (fileName == null) return;
		PrintStream fo = null;
		try {
			fo = new PrintStream(new FileOutputStream(new File(fileName)));
			fo.print(origTxt);
		} catch (FileNotFoundException e) {
			// TODO : Inform GUI !!
			e.printStackTrace();
		} finally {
			if (fo != null) {
				fo.flush();
				fo.close();
			}
		}
	}

	/** ValiditionObserver impl. **/

	@Override
	public void validationResult(ErrorInformation e) {
		//System.out.println(e);
		/** inform gui **/
		if (markers==null) markers = new LinkedList<Marker>();
		synchronized (markers) {
			int l = e.getCrashLine()-1;
			int c = e.getCrashColumn()-1;
			Point xy = convLCtoXY(l,c);
			try{
				Marker m = markers.getFirst();
				if ( m.old && (
					m.getLine()<l||
					(
						m.getLine()==l &&
						m.getColumn()<=c
					)
				))
					markers.removeFirst();
				text.repaint();
			}catch(NoSuchElementException ex){}
			if (e.getProposals()!=null) // TBER?
				markers.add(new InsertMarker(
					e,null,
					xy.x, xy.y,
					e.getProposals(), e.getExpectedTerminals()
				));
			else // PBER
			{
				Point end_xy = convLCtoXY(e.getEndLine()-1,e.getEndColumn()-1);
				Point start_xy = convLCtoXY(e.getBeginLine()-1,e.getBeginColumn()-1);
				markers.add(new ErrorMarker(
					e,null,
					xy.x, xy.y,
					start_xy.x, start_xy.y,
					end_xy.x, end_xy.y,
					e.getExpectedTerminals())
				);
				if (!e.isRecovered()){
					markers.add(new UnrecoverableMarker(end_xy.x, end_xy.y));
				}
			}
		}
		text.repaint();
		/*try { Thread.sleep(100); } catch (InterruptedException e1) { }*/
	}

	private Point convLCtoXY(int l, int c) {
		String oT;
		String[] splt = origTxt.split("\n");
		if (splt.length <= l || l<0)
			oT = "";
		else if (splt[l].length()<c)
			oT = splt[l];
		else if (c<0)
			oT = "";
		else
			oT = splt[l].substring(0, c);
		int xpos = fm.stringWidth(oT);
		if (oT.length() > 0 && oT.charAt(oT.length() - 1) == ' ')
			xpos -= 2;
		int ypos = fm.getHeight() * l;
		return new Point(xpos,ypos);
	}

	@Override
	public void validationFailed() {
		markers.clear();
		text.repaint();
		//System.out.println("\t\t\t\t---- :(");
	}

	@Override
	public void validationDone(Object r) {
		if (markers!=null) synchronized (markers) {
			for (int i=0; i<markers.size(); i++)
			{
				Marker m = markers.get(i);
				if (m.old) {markers.remove(m);i--;}
				else if (m instanceof ErrorMarker)
				{ try{ m.setText(errorTxts.removeFirst()); }catch(NoSuchElementException $){} }
			}
		};
		if (format && (markers==null || markers.size()==0))
		{
			format = false;
			if (r!=null)
			{
				text.setContentType("text/plain");
				text.setText(r.toString());
			}
		}
		//System.out.println("\t\t\t\t---- :)");
		text.repaint();
	}

	/** error-display **/

	private void clearValidationResults() {
		synchronized (errorTxts) {
			errorTxts.clear();
		}
		if (markers!=null){
			synchronized (markers) {
				Validator.stopAll();
				//markers.clear();
				for (Marker m : markers)
					m.old = true;
				text.removeAll();
			}
			//System.out.println("\t\t\t\t---- clear");
		}
	}
	
	class ValidIt extends Thread {		
		/** logic**/
		private long	endTime = Long.MIN_VALUE;
		public ValidIt() {
			resetTimer();
			if (DEBUG) System.out.println("Validation-thread started. Waiting...");
		}
		public void run() {
			do{
				while (endTime>System.currentTimeMillis()) {
					//System.out.println("zZz");
					try { Thread.sleep(endTime-System.currentTimeMillis()); } catch (InterruptedException e1) { }
				}
				//System.out.println("validation start..?");
				synchronized (this) {
					if (endTime==Long.MIN_VALUE) { if (DEBUG) System.out.println("Validation-thread down.");return; }
					if (DEBUG) System.out.println("Validation-thread updating...");
					format = false;
					if (
						text.getComponentCount()!=0 ||
						!vali()
					) {
						endTime = System.currentTimeMillis()+100;
						//System.out.println("wait");
					}else{
						EditorWindow.this.validIt=null;
						if (DEBUG) System.out.println("Validation-thread done.");
						return;
					}
				}
			}while(true);
		}
		public synchronized void resetTimer() {
			endTime = System.currentTimeMillis()+500; }
		public void stopNow() {
			synchronized (this) {
				endTime = Long.MIN_VALUE;	
			}
			if (DEBUG) System.out.println("Stopping validation-thread ...");
			this.interrupt();
		}
	}
	
	@Override
	public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
		if (origTxt==null || !origTxt.equals(text.getText()))
		{
			/** refresh required **/
			/** remove all labels **//*
			for (int i =0; i<text.getComponents().length; i++)
			{
				if (text.getComponents()[i] instanceof JLabel)
					text.remove(i--);
			}*/
			/*EventQueue.invokeLater(new Runnable(){public void run() {
				origTxt = text.getText().replace('\t', ' ');
				text.setText(origTxt);
			}});*/
			origTxt = text.getText();
			do{
				if (validIt==null)
				{
					validIt = new ValidIt();
					synchronized (validIt) {
						validIt.start();
					}
				} else
					synchronized (validIt) {
						if (validIt==null)
							continue;
						validIt.resetTimer();
					}
			}while(false);
		}
		
		/** paint markers **/
		if (markers==null)
			return;
		else
		{
			synchronized (markers) {
				if (markers.size() == 0)
				{
					markers = null;
					c.repaint();
					return;
				}
				/*g.clearRect(bounds.getBounds().x, bounds.getBounds().y, 
					bounds.getBounds().width, bounds.getBounds().height);*/
				for (Marker m : this.markers) {
					if (m instanceof UnrecoverableMarker)
					{
						UnrecoverableMarker im = (UnrecoverableMarker)m;
						g.setColor(Color.decode("#AA0000"));
						g.fillRect(
							bounds.getBounds().x + im.x, 
							bounds.getBounds().y + im.y+fm.getHeight()*3/4, 
							10, 8
						);
					}
					if (m instanceof InsertMarker)
					{
						g.setColor(Color.decode("#50A000"));
						InsertMarker im = ((InsertMarker)m);
						int xPos = bounds.getBounds().x + im.x;
						int yPos = bounds.getBounds().y + im.y + 10;
						g.drawLine(xPos - 4, yPos - 6, xPos, yPos);
						g.drawLine(xPos, yPos, xPos + 4, yPos - 6);
						g.drawLine(xPos + 4, yPos - 6, xPos - 4, yPos - 6);
					}
					else if (m instanceof ErrorMarker)
					{
						ErrorMarker im = ((ErrorMarker)m);
						int xPos = bounds.getBounds().x + im.x - 2;
						int yPos = bounds.getBounds().y + im.y + fm.getHeight();
						int xPosEnd = bounds.getBounds().x + im.xEnd;
						int yPosEnd = bounds.getBounds().y + im.yEnd + fm.getHeight();
						int xPosStart = bounds.getBounds().x + im.xStart-2;
						int yPosStart = bounds.getBounds().y + im.yStart + fm.getHeight();
						
						g.setColor(Color.decode("#DDCC99"));
						if (yPosStart<yPos)
						{
							g.drawLine(xPos, yPos, bounds.getBounds().x-2 , yPos );
							int y=yPos-fm.getHeight();
							while (yPosStart<y) {
								g.drawLine( bounds.getBounds().x-2, y, bounds.getBounds().width+2, y);
								y-=fm.getHeight();
							}
							g.drawLine(xPosStart, y, bounds.getBounds().width+2 , y);
						}
						else
							g.drawLine(xPos, yPos, xPosStart , yPos );
						g.setColor(Color.decode("#DD5500"));
						g.drawLine(xPos, yPos, xPos, yPos-fm.getHeight()+1);
						if (yPosEnd>yPos)
						{
							g.drawLine(xPos, yPos, bounds.getBounds().width+2 , yPos );
							int y=yPos+fm.getHeight();
							while (yPosEnd>y) {
								g.drawLine( bounds.getBounds().x-2, y, bounds.getBounds().width+2, y);
								y+=fm.getHeight();
							}
							g.drawLine( bounds.getBounds().x-2, y, xPosEnd, y);
						}
						else
							g.drawLine(xPos, yPos, xPosEnd , yPos );
					}
				}
			}
		}
	}

	/** Mouse Listener **/
	@Override
	public void mouseClicked(MouseEvent e) {
		if (text.getComponentCount()>0)
		{
			text.removeAll();
			text.repaint();
		}
		if ((e.getButton()&MouseEvent.BUTTON2)==0) {return;}

		if (markers==null) return;
		int x = e.getPoint().x;
		int y = e.getPoint().y;
		DropDownMenu last = null;
		synchronized (markers) {
			for (Marker m:markers)
			{
				if (m instanceof InsertMarker)
				{
					InsertMarker im = ((InsertMarker)m);
					if ( im.x-6<=x && im.x+10>=x &&
					     im.y<=y && im.y+fm.getHeight()>=y )
					{
						e.consume();
						ArrayList<Action> all = new ArrayList<Action>();
						for (String s : im.proposals)
							all.add(new InsertAction(s));
						all.add(null);
						for (Terminal t:im.expectedTerminals)
						{
							Collection<String> ieiae2 = MiniJavaSpec$Terminal2Strings.get(t);
							if(ieiae2!=null) {
								List<String> ieiae = new ArrayList<String>(ieiae2);
								for (String s : ieiae)
								{
									InsertAction act = new InsertAction(s);
									if (!all.contains(act))
										all.add(act);
								}
							}
							else all.add(new NoOpAction(t.toString()));
						}
						DropDownMenu nu = new DropDownMenu(
							this,im,im.x,im.y, 
							all.toArray(new Action[all.size()]));
						nu.setFont(text.getFont());
						text.add(nu);
						Thread.yield();
						if (last!=null)
							last.next=nu;
						last = nu;
					}
				}
				else if (m instanceof ErrorMarker)
				{
					e.consume();
					ErrorMarker im = ((ErrorMarker)m);
					if ( im.x-8<=x && im.x+8>=x &&
					     im.y<=y && im.y+fm.getHeight()>=y )
					{
						ArrayList<Action> all = new ArrayList<Action>();
						if (im.beginColumn!=im.endColumn && im.beginLine!=im.endColumn)
							all.add(new DeleteAction("remove",im.beginLine,im.beginColumn,im.endLine,im.endColumn));
						all.add(null);
						for (Terminal t:im.expectedTerminals)
						{
							Collection<String> ieiae2 = MiniJavaSpec$Terminal2Strings.get(t);
							if(ieiae2!=null) {
								List<String> ieiae = new ArrayList<String>(ieiae2);
								ieiae.removeAll(all);
								for (String s : ieiae)
									all.add(new InsertAction(s));
							}
							else all.add(new NoOpAction(t.toString()));
						}
						DropDownMenu nu = new DropDownMenu(
							this,im,im.x,im.y, 
							all.toArray(new Action[all.size()]));
						nu.setFont(text.getFont());
						text.add(nu);
						Thread.yield();
						if (last!=null)
							last.next=nu;
						last = nu;
					}
				}
			}
		}
		text.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	/** text modification **/


	public void deleteString(int bc, int bl, int ec, int el) {
		try{
			synchronized (markers) {
				String[] splt = origTxt.split("\n");
				String line;
				line = splt[bl];
				if (bl==el)
				{
					line = line.substring(0,bc)+line.substring(ec);
					if (line.trim().equals(""))
						splt[bl] = null;
					else
						splt[bl] = line;
				}
				else{
					line = line.substring(0,bc);
					if (line.trim().equals(""))
						splt[bl] = null;
					else
						splt[bl] = line;
					while(++bl<el)
						splt[bl] = null;
					line = splt[el].substring(ec);
					if (line.trim().equals(""))
						splt[bl] = null;
					else
						splt[bl] = line;
				}
				String txt ="";
				for (String s:splt)
					if (s!=null)
						txt += s+"\n";
				text.setText(txt);
				text.repaint();
			}
		}catch (IndexOutOfBoundsException e){System.err.println("Could not delete... ");}
	}
	
	public void insertString( String string, int crashLine, int crashColumn, Marker im) {
		try{
			synchronized (markers) {
				string =" "+string+" ";
				String[] splt = origTxt.split("\n");
				String line;
				String txt ="";
				if (crashLine>=splt.length){
					txt="";
					for (String s:splt)
						txt += s+"\n";
					txt += string;
					crashLine = splt.length;
				}else{
					line = splt[crashLine];
					if (line.length()<=crashColumn)
						splt[crashLine] += string;
					else
						splt[crashLine] = line.substring(0, crashColumn)+string+line.substring(crashColumn);
					txt="";
					for (String s:splt)
						txt += s+"\n";
				}
				text.setText(txt);
				boolean doNotifySame=false;
				for (int i=0; i<markers.size(); i++)
				{
					Marker m = markers.get(i);
					if (doNotifySame || crashLine!=m.getLine() || crashColumn!=m.getColumn())
					{
						m.moveBy(crashLine,crashColumn,string.length(),fm.stringWidth(string));
					}else if (m==im){
						markers.remove(i--);
						doNotifySame=true;
					}
				}
			}
						
			text.repaint();
		}catch (IndexOutOfBoundsException e){System.err.println("Could not insert "+string);}
	}
	
	/** checkbox **/

	@Override
	public void itemStateChanged(ItemEvent e) {
		doIns = (e.getStateChange()==ItemEvent.SELECTED);
		vali();
	}
	
	/** window **/

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		/** stop all updaters **/
		if (validIt!=null) synchronized (validIt) {
			validIt.stopNow();
			validIt = null;
		}
		this.dispose(); /** close the window **/
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) { }

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void addErrorText(String string) {
		synchronized (errorTxts ) {
			errorTxts.add(string);
		}
	}

	/** other... **/

}
