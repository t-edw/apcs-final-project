import java.awt.Canvas;
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

public class Maze extends JFrame implements Runnable {
	private JPanel panel;
	private Canvas canvas;
	private BufferStrategy bufferStrategy;
	private Image transparent;
	private Graphics g;
	private Random rand;
	private ArrayList<Integer> cols, rows;
	private Pixel[][] pixels;
	private int x, y, a, width, mazeSize, factor, dim;
	private boolean running;
	
	public Maze() throws IOException{
		super("Maze");
		x=5; y=5; a=1; width=25; mazeSize=900; dim=20; factor=mazeSize/dim;
		rand = new Random();
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
		panel.add(canvas);
		canvas.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				move(e);
			}
		});
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
	//done
	public boolean[] checkWall(){
		boolean[] walls = {false,false,false,false};
		for (int i=0; i<width; i++) {
			if(pixels[x+i][y-1].isBlack()){ //top
				walls[0]=true;
				System.out.println("wall up");
			}
			if(pixels[x-1][y+i].isBlack()){//left
				walls[1]=true;
				System.out.println("wall left");
			}
			if(pixels[x+i][y+width+1].isBlack()){ //down
				walls[2]=true;
				System.out.println("wall down");
			}
			if(pixels[x+width+1][y+i].isBlack()){ //right
				walls[3]=true;
				System.out.println("wall right");
			}
		}
		return walls;
	}
	//done
	public void move(KeyEvent e) {
		boolean[] walls = new boolean[4];
		walls = checkWall();
		if (!walls[0]&&e.getKeyCode()==KeyEvent.VK_UP){ //top
			y-=a;
			System.out.println("move");
		}
		if (!walls[1]&&e.getKeyCode()==KeyEvent.VK_LEFT){ //left
			x-=a;
			System.out.println("move");
		}
		if (!walls[2]&&e.getKeyCode()==KeyEvent.VK_DOWN){ //down
			y+=a;
			System.out.println("move");
		}
		if (!walls[3]&&e.getKeyCode()==KeyEvent.VK_RIGHT){ //right
			x+=a;
			System.out.println("move");
		}
		checkVictory();
	}
	//done
	public void checkVictory() {
		if (x+width==mazeSize-2&&y+width==mazeSize-2)
			win();
	}
	//done
	public void win(){
		running = false;
		String[] victoryMessages = {"You did it!", "You made it!",
									"Congratulations!",
									"Congratulations, you made it out!",
									"You win!","You are a winner!"};
		System.out.println(victoryMessages[rand.nextInt(victoryMessages.length)]);
	}
	//done
	public void drawCharacter() throws IOException {
		Scanner q = new Scanner(System.in);
		BufferedImage[] characters = {ImageIO.read(new File("src\\character.fw.png")),ImageIO.read(new File("src\\ghost.png"))};
		System.out.print("Select a character, 1 or 2. ");
		int i = q.nextInt()-1;
		transparent = characters[i];
		transparent = transparent.getScaledInstance(width, width, Image.SCALE_DEFAULT);
	}
	//done
	public void makeMaze(int minRowIndex, int minColIndex, int maxRowIndex, int maxColIndex) {
		String split = getSplitDir(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
		if (split.equals("VERTICAL"))
			splitVertical(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
		else
			splitHorizontal(minRowIndex, minColIndex, maxRowIndex, maxColIndex);
	}
	//done
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
	//done
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
	//done
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
	//done
	public void fillRowColArr() {
		cols = new ArrayList<Integer>();
		rows = new ArrayList<Integer>();
		for (int i=1; i<=dim; i++) {
			cols.add((factor*i)-1);
			rows.add((factor*i)-1);
		}
	}
	//done
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
	//done
	public void paint() throws IOException {
		g.clearRect(x, y, width, width);
		g.drawImage(transparent, x, y,null,null);
		bufferStrategy.show();
	}
	//done
	@Override
	public void run() {
		while(running = true) {
			try {
				paint();
			} catch (IOException e1) {
			}
            try {
                Thread.sleep(0, 1);
            } catch (InterruptedException e) {
            }
		}
	}
	//main
	public static void main(String[] args) throws IOException {
		Maze maze = new Maze();
		new Thread(maze).start();
	}
}