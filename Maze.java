import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**Maze.java
 * Joshua Sathyaraj and Tamsin Edwards
 * Period G
 * June 1, 2016
 * 
 * Creates and plays a maze game
 */
public class Maze extends JFrame implements Runnable {
	private JPanel panel;
	private Canvas canvas;
	private BufferStrategy bufferStrategy;
	private Image transparent;
	private Graphics g;
	private Random rand;
	private ArrayList<Integer> cols, rows;
	private Pixel[][] pixels;
	private int x, y, a, width, mazeSize, factor, dim, keyCount;
	private boolean running;
	private KeyAdapter l;
	private Timer timer;
	
	//constructor
	//creates the maze and draws the character initially
	public Maze() throws IOException{
		super("Maze");
		x=5; y=5; a=1; width=25; mazeSize=900; dim=20; factor=mazeSize/dim; keyCount=0;
		rand = new Random();
		timer = new Timer();
		fillPixelArray();
		fillRowColArr();
		running=true;
		panel = (JPanel) this.getContentPane();
		panel.setSize(mazeSize, mazeSize);
		panel.setVisible(true);
		//canvas stuff
		canvas = new Canvas();
		canvas.setSize(mazeSize, mazeSize);
		canvas.setIgnoreRepaint(true);
		canvas.setBackground(new Color(54, 179, 222));
		panel.add(canvas);
		l = new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				keyCount++;
				if (keyCount==1) timer.setStop(false);
				move(e);
			}
		};
		canvas.addKeyListener(l);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		canvas.requestFocus();
		canvas.setVisible(true);
		g = bufferStrategy.getDrawGraphics();
		g.drawRect(0, 0, mazeSize-1, mazeSize-1);
		makeMaze(0, 0, dim-2, dim-2);
		drawCharacter();
	}
	//method: boolean[] checkWall()
	//checks for contact with a wall on any side of the character
	//using the array of pixels filled when the maze is created
	public boolean[] checkWall(){
		boolean[] walls = {false,false,false,false};
		for (int i=0; i<width; i++) {
			if(pixels[x+i][y-1].isBlack()){ //top
				walls[0]=true;
				//System.out.println("wall up");
			}
			if(pixels[x-1][y+i].isBlack()){//left
				walls[1]=true;
				//System.out.println("wall left");
			}
			if(pixels[x+i][y+width+1].isBlack()){ //down
				walls[2]=true;
				//System.out.println("wall down");
			}
			if(pixels[x+width+1][y+i].isBlack()){ //right
				walls[3]=true;
				//System.out.println("wall right");
			}
		}
		return walls;
	}
	//method: void move(KeyEvent e)
	//moves the character depending on which arrow key 
	//is pressed by the user
	public void move(KeyEvent e) {
		boolean[] walls = new boolean[4];
		walls = checkWall();
		if (!walls[0]&&e.getKeyCode()==KeyEvent.VK_UP){ //top
			y-=a;
			//System.out.println("move");
		}
		if (!walls[1]&&e.getKeyCode()==KeyEvent.VK_LEFT){ //left
			x-=a;
			//System.out.println("move");
		}
		if (!walls[2]&&e.getKeyCode()==KeyEvent.VK_DOWN){ //down
			y+=a;
			//System.out.println("move");
		}
		if (!walls[3]&&e.getKeyCode()==KeyEvent.VK_RIGHT){ //right
			x+=a;
			//System.out.println("move");
		}
		checkVictory();
	}
	//method: void checkVictory()
	//checks whether the character has reached the end 
	//(the bottom right corner) of the maze each time
	//the character is moved
	public void checkVictory() {
		if (x+width==mazeSize-2&&y+width==mazeSize-2){
			running = false;
			timer.setStop(true);
			canvas.removeKeyListener(l);
			win();
		}
	}
	//method: void win()
	//delivers a random victory message when called 
	//upon reaching the end of the maze
	public void win(){
		String[] victoryMessages = {"You did it!", "You made it!",
									"Congratulations!",
									"Congratulations, you made it out!",
									"You win!","You are a winner!"};
		System.out.println(victoryMessages[rand.nextInt(victoryMessages.length)]);
	}
	//method: void drawCharacter()
	//takes user input for the character they would
	//like to play as, and then draws the character
	//on the canvas
	public void drawCharacter() throws IOException {
		BufferedImage[] characters = {ImageIO.read(new File("src\\clyde.png")),ImageIO.read(new File("src\\ghost.png")),
				ImageIO.read(new File("src\\inky.png")),ImageIO.read(new File("src\\blinky.png")),ImageIO.read(new File("src\\ghost2.png"))};
		int i = rand.nextInt(characters.length);
		transparent = characters[i];
		transparent = transparent.getScaledInstance(width, width, Image.SCALE_DEFAULT);
	}
	//method: void makeMaze(int minRowIndex, int minColIndex,
	//int maxRowIndex, int maxColIndex)
	//builds the maze by calling splutHorizontal or splitVertical, which
	//is randomized by the getSplitDir method
	public void makeMaze(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
		String split = getSplitDir(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
		if (split.equals("VERTICAL"))
			splitVertical(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
		else
			splitHorizontal(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
	}
	//method: void splitVertical(int minRowIndex, int minColIndex, 
	//int maxRowIndex, int maxColIndex)
	//splits the maze by drawing a vertical line at a random index
	//which correspond to 45 pixel intervals on the canvas
	public void splitVertical(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
		int colDif = maxColIndex-minColIndex;
		int rowDif = maxRowIndex-minRowIndex;
		if (minRowIndex>=0&&minColIndex>=0&&rowDif>0&&colDif>0) {
			int col = rand.nextInt(colDif)+minColIndex;
			g.drawLine(cols.get(col), rows.get(minRowIndex)-(factor-1), cols.get(col), rows.get(maxRowIndex)+factor-1);
			for (int i=rows.get(minRowIndex)-(factor-1); i<rows.get(maxRowIndex)+factor-1; i++) {
				pixels[cols.get(col)][i].setBlack(true);
			}
			int randomInt = rand.nextInt(rowDif)+minRowIndex;
			g.clearRect(cols.get(col), rows.get(randomInt), 1, factor);
			for (int i=rows.get(randomInt); i<=rows.get(randomInt)+factor; i++) {
				pixels[cols.get(col)][i].setBlack(false);
			}
			makeMaze(minRowIndex, minColIndex, maxRowIndex, col-1);
			makeMaze(minRowIndex, col+1, maxRowIndex, maxColIndex);
		}
	}
	//method: void splitHorizontal(int minRowIndex, int minColIndex, 
	//int maxRowIndex, int maxColIndex)
	//splits the maze by drawing a horizontal line at a random index
	//which correspond to 45 pixel intervals on the canvas
	public void splitHorizontal(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
		int rowDif = maxRowIndex-minRowIndex;
		int colDif = maxColIndex-minColIndex;
		if (minRowIndex>=0&&minColIndex>=0&&rowDif>0&&colDif>0) {
			int row = rand.nextInt(rowDif)+minRowIndex;
			g.drawLine(cols.get(minColIndex)-(factor-1), rows.get(row), cols.get(maxColIndex)+factor-1, rows.get(row));
			for (int i=cols.get(minColIndex)-(factor-1); i<cols.get(maxColIndex)+factor-1; i++) {
				pixels[i][rows.get(row)].setBlack(true);
			}
			int randomInt = rand.nextInt(colDif)+minColIndex;
			g.clearRect(cols.get(randomInt), rows.get(row), factor, 1);
			for (int i=cols.get(randomInt); i<=cols.get(randomInt)+factor; i++) {
				pixels[i][rows.get(row)].setBlack(false);
			}
			makeMaze(minRowIndex, minColIndex, row-1, maxColIndex);
			makeMaze(row+1, minColIndex, maxRowIndex, maxColIndex);
		}
	}
	//method: String getSplitDir(int minRowIndex, int minColIndex, 
	//int maxRowIndex, int maxColIndex)
	//determines whether to split the maze horizontally
	//or vertically
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
	//method: void fillRowColArr()
	//adds the possible intervals the splitting lines can be drawn at
	//to separate array lists for use in the split methods
	public void fillRowColArr() {
		cols = new ArrayList<Integer>();
		rows = new ArrayList<Integer>();
		for (int i=1; i<=dim; i++) {
			cols.add((factor*i)-1);
			rows.add((factor*i)-1);
		}
	}
	//method: void fillPixelArray()
	//fills the two dimensional array pixels with Pixel objects,
	//setting the isBlack value for the edges to true, and 
	//false for all other pixels 
	public void fillPixelArray() {
		pixels = new Pixel[mazeSize][mazeSize];
		for (int i=0; i<mazeSize; i++) {
			for (int j=0; j<mazeSize; j++) {
				if (i==0||j==0||i==899||j==899)
					pixels[i][j]=new Pixel(i, j, true);
				else
					pixels[i][j]=new Pixel(i, j, false);
			}
		}
	}
	//method: void paint()
	//repaints the character
	public void paint() throws IOException {
		g.clearRect(x, y, width, width);
		g.drawImage(transparent, x, y,null,null);
		bufferStrategy.show();
	}
	//method: void run()
	//runs the game
	@Override
	public void run() {
		while(running = true) {
			try {
				paint();
			} catch (IOException e1) {
			}
		}
	}
	//main
	public static void main(String[] args) throws IOException {
		Maze maze = new Maze();
		maze.run();
	}
}
