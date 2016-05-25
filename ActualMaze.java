import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ActualMaze implements Runnable {
	
	JFrame frame;
	int mazeX=0, mazeY=0, mazeSize=900;
	int x = 0, y = 0, xa = 1, ya=1, side=20;
	Canvas canvas;
	BufferStrategy bufferStrategy;
	boolean running = true;
	BufferedImage img;
	Random rand = new Random();
	private int width=0;
	private int offsetV=0;
	private int offsetH=0;
	Image transparent;
	
	public ActualMaze() throws IOException {
		int num = rand.nextInt(6);
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
		num=0;
		if(num==0){
			img = ImageIO.read(new File("src\\maze1.png"));
			width=20;
			offsetV=15;
			offsetH=72;
		}
		else if(num==1){
			img = ImageIO.read(new File("src\\maze2.png"));
			width=25;
			offsetV=10;
			offsetH=offsetV;
		}
		else if(num==2){
			img = ImageIO.read(new File("src\\maze3.png"));
			width=20;
			offsetV=12;
			offsetH=offsetV;
		}
		else if(num==3){
			img = ImageIO.read(new File("src\\maze4.png"));
			width=25;
			offsetV=15;
			offsetH=offsetV;
		}
		else if(num==4){
			//fix the offset !!
			img = ImageIO.read(new File("src\\maze5.bmp"));
			width=15;
			offsetV=250;
			offsetH=22;
		}
		else{
			img = ImageIO.read(new File("src\\maze6.png"));
			width=35;
			offsetV=-24;
			offsetH=offsetV;
		}
		bufferStrategy.getDrawGraphics().drawImage(img, 0, 0, mazeSize, mazeSize, null);
		start();
	}
	public void start() throws IOException{
		Scanner q = new Scanner(System.in);
		BufferedImage[] characters = {ImageIO.read(new File("src\\character.fw.png")),ImageIO.read(new File("src\\ghost.png"))};
		//BufferedImage transparent = ImageIO.read(new File("src\\character.fw.png"));
		System.out.print("Select a character, 1 or 2. ");
		int i = q.nextInt()-1;
		transparent = characters[i];
		transparent = transparent.getScaledInstance(width, width, Image.SCALE_DEFAULT);
		Color myBlue = new Color(63,72,204);
		for(int row=0;row<img.getWidth();row++){
			for(int col=0;col<img.getHeight();col++){
				if(img.getRGB(row, col)==myBlue.getRGB()){
					x=row;
					y=col;
//					while(img.getRGB(row+38+offset,col+38+offset)!=Color.WHITE.getRGB()){
//						offset++;
//					}
					//g.setColor(Color.GREEN);
					//g.fillOval(r+width+offset,c+offset+width,width/2,width/2);
//					System.out.println("start found");
//					row=img.getWidth()*10;
//					col=img.getHeight()*10;
				}
			}
		}
		run();
	}
	public boolean victory(){
		Color myRed = new Color(237,28,36);
		if(img.getRGB(x+width,y+width)==myRed.getRGB())
			return true;
		else
			return false;
	}
	public void win(){
		String[] victoryMessages = {"You did it!", "You made it!",
									"Congratulations!",
									"Congratulations, you made it out!",
									"You win!","You are a winner!"};
		System.out.println(victoryMessages[rand.nextInt(6)]);
	}
	public boolean[] checkWall(){
		boolean[] walls = {false,false,false,false};
		Color myWhite = new Color(255,255,255);
//		//top
//		for(int r=x;r<=x+width;r++){
//			if(img.getRGB(r, y)!=myWhite.getRGB())
//				walls[0]=true;
//		}
//		//right
//		for(int c=y;c<=y+width;c++){
//			if(img.getRGB(x+width, c)!=myWhite.getRGB())
//				walls[3]=true;
//		}
//		//left
//		for(int c=y;c<=y+width;c++){
//			if(img.getRGB(x, c)!=myWhite.getRGB())
//				walls[1]=true;
//		}
//		//bottom
//		for(int r=x;r<=x+width;r++){
//			if(img.getRGB(r, y+width)!=myWhite.getRGB())
//				walls[2]=true;
//		}
		if(img.getRGB(x+width/2-offsetH, y)!=myWhite.getRGB()){ //top
			walls[0]=true;
			System.out.println("wall up");
		}
		if(img.getRGB(x-offsetH, y+width/2-offsetV)!=myWhite.getRGB()){
			walls[1]=true;
			System.out.println("wall left");
		}
		if(img.getRGB(x+width/2-offsetH, y+width-offsetV)!=myWhite.getRGB()){
			walls[2]=true;
			System.out.println("wall down");
		}
		if(img.getRGB(x+width-offsetH, y+width/2-offsetV)!=myWhite.getRGB()){
			walls[3]=true;
			System.out.println("wall right");
		}
		return walls;
	}
	public void move(KeyEvent e) {
		bufferStrategy.getDrawGraphics().clearRect(0, 0, 900, 900);
		bufferStrategy.getDrawGraphics().drawImage(img, 0, 0, mazeSize, mazeSize, null);
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
	}
	@Override
	public void run() {
		while(running = true) {
			try {
				paint();
			} catch (IOException e1) {
			}
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
            }
		}
	}
	
	public void paint() throws IOException {
		bufferStrategy.getDrawGraphics().drawImage(transparent, x, y,null,null);
		bufferStrategy.show();
	}
	
	public static void main(String[] args) throws IOException {
		ActualMaze dot = new ActualMaze();
		new Thread(dot).start();
	}
	
	
}
