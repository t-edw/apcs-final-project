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
	int x = 20, y = 20, xa = 1, ya=1, side=20;
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
		panel.setSize(950, 950);
		canvas = new Canvas();
		canvas.setBounds(0, 0, 950, 950);
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
		if(num==0){
			img = ImageIO.read(new File("src\\maze1.png"));
			width=32;
			offsetV=5;
			offsetH=offsetV;
		}
		else if(num==1){
			img = ImageIO.read(new File("src\\maze2.png"));
			width=30;
			offsetV=10;
			offsetH=offsetV;
		}
		else if(num==2){
			img = ImageIO.read(new File("src\\maze3.png"));
			width=25;
			offsetV=12;
			offsetH=offsetV;
		}
		else if(num==3){
			img = ImageIO.read(new File("src\\maze4.png"));
			width=30;
			offsetV=15;
			offsetH=offsetV;
		}
		else if(num==4){
			//fix the offset !!
			img = ImageIO.read(new File("src\\maze5.bmp"));
			width=20;
			offsetV=250;
			offsetH=22;
		}
		else{
			img = ImageIO.read(new File("src\\maze6.png"));
			width=40;
			offsetV=-24;
			offsetH=offsetV;
		}
		bufferStrategy.getDrawGraphics().drawImage(img, 10, 0, 900, 900, null);
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
					row=img.getWidth()*10;
					col=img.getHeight()*10;
				}
			}
		}
		play(checkWall());
	}
	public void play(boolean wall){
		if(!wall){
			//move
		}
		else
			System.out.println("You cannot go that way!");
		if(!this.victory()){
			//wall=checkWall();
			play(checkWall());
		}
		else
			win();
	}
	public boolean victory(){
		Color myRed = new Color(237,28,36);
		if(img.getRGB(x+width,y+width)!=myRed.getRGB())
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
	public boolean checkWall(){
		if(img.getRGB(x, y)==Color.white.getRGB()&&img.getRGB(x+width, y)==Color.white.getRGB()&&img.getRGB(x, y+width)==Color.white.getRGB()&&img.getRGB(x+width, y+width)==Color.white.getRGB())
			return false;
		else
			return true;
	}
	public void move(KeyEvent e) {
		bufferStrategy.getDrawGraphics().clearRect(0, 0, 900, 900);
		bufferStrategy.getDrawGraphics().drawImage(img, 0, 0, 900, 900, null);
		if (!checkWall()) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP : y-=ya; break;
			case KeyEvent.VK_DOWN : y+=ya; break;
			case KeyEvent.VK_LEFT : x-=xa; break;
			case KeyEvent.VK_RIGHT : x+=xa; break;
			}
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
		bufferStrategy.getDrawGraphics().drawImage(transparent,x+width+offsetV,y+offsetH+width,null,null);
		bufferStrategy.show();
	}
	
	public static void main(String[] args) throws IOException {
		ActualMaze dot = new ActualMaze();
		new Thread(dot).start();
	}
	
	
}