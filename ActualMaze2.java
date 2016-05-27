import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;




@SuppressWarnings("serial")
public class ActualMaze2 implements Runnable {
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
	Pixel[][] pix;
	
	public ActualMaze2() {
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
	}

	public void move(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void drawMaze(Graphics g, int minCol, int maxCol, int minRow, int maxRow) {
		int width=maxCol-minCol;
		int height=maxRow-minRow;
		String split;
		
		if (width>2&&height>2) {
			
		}
	}
	
	public void splitVertical(Graphics g, int col) {
		
	}
	
	public void splitHorizontal(Graphics g, int row) {
		
	}
	
	public String splitDirection() {
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
