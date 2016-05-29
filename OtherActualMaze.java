import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OtherActualMaze {
	JFrame frame;
	int mazeX=0, mazeY=0, mazeSize=900;
	int x = 0, y = 0, xa = 1, ya=1;
	Canvas canvas;
	BufferStrategy bufferStrategy;
	boolean running = true;
	BufferedImage img;
	Random rand = new Random();
	private int side=0;
	private int offsetV=0;
	private int offsetH=0;
	Image transparent;
	Graphics g;
	ArrayList<Integer> rows = new ArrayList<Integer>();
	ArrayList<Integer> cols = new ArrayList<Integer>();
	
	public OtherActualMaze() {
		for (int i=1; i<15; i++) {
			rows.add(60*i);
			cols.add(60*i);
		}
		frame = new JFrame("Maze");
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setSize(900, 900);
		canvas = new Canvas();
		canvas.setBounds(0, 0, 900, 900);
		canvas.setIgnoreRepaint(true);
		panel.add(canvas);
		canvas.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				move(e);
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		canvas.requestFocus();
		canvas.setVisible(true);
		//g = bufferStrategy.getDrawGraphics();
		g = canvas.getGraphics();
		makeMaze(0, 0, 13, 13);
	}

	protected void move(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void makeMaze(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
//		int width = maxCol-minCol; int height = maxRow-minRow;
		String split = getSplitDir();
		if (split.equals("VERTICAL")) {
			splitVertical(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
		}
		else
			splitHorizontal(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
	}
	public void splitVertical(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
		int col = rand.nextInt(maxColIndex-minColIndex)+minColIndex;
		g.drawLine(cols.get(col), rows.get(minRowIndex)-60, cols.get(col), rows.get(maxRowIndex)+60);
		makeMaze(minRowIndex, minColIndex, maxRowIndex, col-1);
		makeMaze(minRowIndex, col+1, maxRowIndex, maxColIndex);
	}
	public void splitHorizontal(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
		int row = rand.nextInt(maxRowIndex-minRowIndex)+minRowIndex;
		g.drawLine(cols.get(minColIndex)-60, rows.get(row), cols.get(maxColIndex)+60, rows.get(row));
		makeMaze(minRowIndex, minColIndex, row-1, maxColIndex);
		makeMaze(row+1, minColIndex, maxRowIndex, maxColIndex);
	}
	
	public String getSplitDir() {
		/*if (width>height) {
			return "VERTICAL";
		}
		else if (width<height) {
			return "HORIZONTAL";
		}*/
		//else {
			int n = rand.nextInt(2);
			if (n==0)
				return "VERTICAL";
			else
				return "HORIZONTAL";
			
		//}
	}
	
	public static void main(String[] args) {
		OtherActualMaze maze = new OtherActualMaze();
	}
}