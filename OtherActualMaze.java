import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OtherActualMaze implements Runnable {
	JFrame frame;
	int mazeX=0, mazeY=0, mazeSize=900;
	int x = 860, y = 860, xa = 1, ya=1;
	Canvas canvas;
	BufferStrategy bufferStrategy;
	boolean running = true;
	BufferedImage img;
	Random rand = new Random();
	private int width=25;
	private int offsetV=0;
	private int offsetH=0;
	Image transparent;
	Graphics g, b;
	ArrayList<Integer> rows = new ArrayList<Integer>();
	ArrayList<Integer> cols = new ArrayList<Integer>();
	Pixel[][] pixels = new Pixel[mazeSize][mazeSize];
	
	public OtherActualMaze() throws IOException {
		for (int i=1; i<=20; i++) {
			rows.add((45*i)-1);
			cols.add((45*i)-1);
		}
		frame = new JFrame("Maze");
		//frame.setSize(900, 900);
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setSize(900, 900);
		canvas = new Canvas();
		canvas.setSize(900, 900);
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
		//g = canvas.getGraphics();
		g = bufferStrategy.getDrawGraphics();
		g.drawRect(0, 0, 899, 899);
		fillPixelArray();
		makeMaze(0, 0, 18, 18);
		start();
	}
	public void fillPixelArray() {
		for (int i=0; i<pixels.length; i++) {
			for (int j=0; j<pixels[0].length; j++) {
				pixels[i][j]=new Pixel(i, j, false);
			}
		}
	}
	public void start() throws IOException {
		Scanner q = new Scanner(System.in);
		BufferedImage[] characters = {ImageIO.read(new File("src\\character.fw.png")),ImageIO.read(new File("src\\ghost.png"))};
		System.out.print("Select a character, 1 or 2. ");
		int i = q.nextInt()-1;
		transparent = characters[i];
		transparent = transparent.getScaledInstance(width, width, Image.SCALE_DEFAULT);
	}
	public boolean[] checkWall(){
		boolean[] walls = {false,false,false,false};
		for (int i=0; i<width; i++) {
			if(pixels[x+i][y-1].isBlack()){ //top
				walls[0]=true;
				System.out.println("wall up");
			}
			if(pixels[x-1][y+i].isBlack()){
				walls[1]=true;
				System.out.println("wall left");
			}
			if(pixels[x+i][y+width+1].isBlack()){
				walls[2]=true;
				System.out.println("wall down");
			}
			if(pixels[x+width+1][y+i].isBlack()){
				walls[3]=true;
				System.out.println("wall right");
			}
		}
		return walls;
	}
	public void move(KeyEvent e) {
		boolean[] walls = new boolean[4];
		walls = checkWall();
		if (!walls[0]&&e.getKeyCode()==KeyEvent.VK_UP){ //top
			y-=ya;
			System.out.println("move");
		}
		if (!walls[1]&&e.getKeyCode()==KeyEvent.VK_LEFT){ //left
			x-=xa;
			System.out.println("move");
		}
		if (!walls[2]&&e.getKeyCode()==KeyEvent.VK_DOWN){ //down
			y+=ya;
			System.out.println("move");
		}
		if (!walls[3]&&e.getKeyCode()==KeyEvent.VK_RIGHT){ //right
			x+=xa;
			System.out.println("move");
		}
		checkVictory();
	}
	public void checkVictory() {
		if (x+width==899&&y+width==899)
			win();
	}
	public void win(){
		String[] victoryMessages = {"You did it!", "You made it!",
									"Congratulations!",
									"Congratulations, you made it out!",
									"You win!","You are a winner!"};
		System.out.println(victoryMessages[rand.nextInt(6)]);
	}
	public void paint() throws IOException {
		g.clearRect(x, y, width, width);
		g.drawImage(transparent, x, y,null,null);
		bufferStrategy.show();
	}
	
	public void makeMaze(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
//		int width = maxCol-minCol; int height = maxRow-minRow;
		String split = getSplitDir(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
		if (split.equals("VERTICAL"))
			splitVertical(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
		else
			splitHorizontal(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
	}
	public void splitVertical(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
		int colDif = maxColIndex-minColIndex;
		int rowDif = maxRowIndex-minRowIndex;
		if (minRowIndex>=0&&minColIndex>=0&&rowDif>0&&colDif>0) {
			int col = rand.nextInt(colDif)+minColIndex;
			g.drawLine(cols.get(col), rows.get(minRowIndex)-44, cols.get(col), rows.get(maxRowIndex)+44);
			for (int i=rows.get(minRowIndex)-44; i<rows.get(maxRowIndex)+44; i++) {
				pixels[cols.get(col)][i].setBlack(true);
			}
			int randomInt = rand.nextInt(rowDif)+minRowIndex;
			g.clearRect(cols.get(col), rows.get(randomInt), 1, 45);
			for (int i=rows.get(randomInt); i<=rows.get(randomInt)+45; i++) {
				pixels[cols.get(col)][i].setBlack(false);
			}
			makeMaze(minRowIndex, minColIndex, maxRowIndex, col-1);
			makeMaze(minRowIndex, col+1, maxRowIndex, maxColIndex);
		}
	}
	public void splitHorizontal(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
		int rowDif = maxRowIndex-minRowIndex;
		int colDif = maxColIndex-minColIndex;
		if (minRowIndex>=0&&minColIndex>=0&&rowDif>0&&colDif>0) {
			int row = rand.nextInt(rowDif)+minRowIndex;
			g.drawLine(cols.get(minColIndex)-44, rows.get(row), cols.get(maxColIndex)+44, rows.get(row));
			for (int i=cols.get(minColIndex)-44; i<cols.get(maxColIndex)+44; i++) {
				pixels[i][rows.get(row)].setBlack(true);
			}
			int randomInt = rand.nextInt(colDif)+minColIndex;
			g.clearRect(cols.get(randomInt), rows.get(row), 45, 1);
			for (int i=cols.get(randomInt); i<=cols.get(randomInt)+45; i++) {
				pixels[i][rows.get(row)].setBlack(false);
			}
			makeMaze(minRowIndex, minColIndex, row-1, maxColIndex);
			makeMaze(row+1, minColIndex, maxRowIndex, maxColIndex);
		}
	}
	
	public String getSplitDir(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
		if (maxColIndex-minColIndex>maxRowIndex-minRowIndex) {
			return "VERTICAL";
		}
		else if (maxColIndex-minColIndex<maxRowIndex-minRowIndex) {
			return "HORIZONTAL";
		}
		else {
			int n = rand.nextInt(2);
			if (n==0)
				return "VERTICAL";
			else
				return "HORIZONTAL";
			
		}
	}

	@Override
	public void run() {
		while(running = true) {
			try {
				paint();
			} catch (IOException e1) {
			}
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
		}
	}
	
	public static void main(String[] args) throws IOException {
		OtherActualMaze maze = new OtherActualMaze();
		new Thread(maze).start();
	}
}
