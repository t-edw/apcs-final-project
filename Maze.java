import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

public class Maze extends JFrame{
	private JFrame frame;
	private BufferedImage img = null;
	private Graphics g;
	private Scanner q = new Scanner(System.in);
	private Random rand = new Random();
	private int[] loc = new int[2];
	private int width=0;
	private int offset=0;
	public Maze() throws IOException{
		int num = rand.nextInt(6);
//		int num=3;
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(950,950);
		frame.setVisible(true);
		g = frame.getGraphics();
		
		//int width=0;
		//int offset=0;
		if(num==0){
			img = ImageIO.read(new File("src\\maze1.png"));
			width=32;
			offset=5;
		}
		else if(num==1){
			img = ImageIO.read(new File("src\\maze2.png"));
			width=30;
			offset=10;
		}
		else if(num==2){
			img = ImageIO.read(new File("src\\maze3.png"));
			width=25;
			offset=10;
		}
		else if(num==3){
			img = ImageIO.read(new File("src\\maze4.png"));
			width=30;
			offset=15;
		}
		else if(num==4){
			//fix the offset !!
			img = ImageIO.read(new File("src\\maze5.bmp"));
			width=10;
		}
		else{
			img = ImageIO.read(new File("src\\maze6.png"));
			width=56;
			offset=-35;
		}
		g.drawImage(img, 10, 30, 900, 900, null);
//		if(img.getRGB(1, 1)!=0)
//			System.out.println("wall");
		
	}
	public void start() throws IOException{
		int r=0,c=0;
		BufferedImage[] characters = {ImageIO.read(new File("src\\character.fw.png")),ImageIO.read(new File("src\\ghost.png"))};
		//BufferedImage transparent = ImageIO.read(new File("src\\character.fw.png"));
		System.out.print("Select a character, 1 or 2. ");
		int i = q.nextInt()-1;
		Image transparent = characters[i];
		transparent = transparent.getScaledInstance(width, width, Image.SCALE_DEFAULT);
		Color myBlue = new Color(63,72,204);
		for(int row=0;row<img.getWidth();row++){
			for(int col=0;col<img.getHeight();col++){
				if(img.getRGB(row, col)==myBlue.getRGB()){
					r=row;
					c=col;
//					while(img.getRGB(row+38+offset,col+38+offset)!=Color.WHITE.getRGB()){
//						offset++;
//					}
					g.setColor(Color.GREEN);
					g.drawImage(transparent,r+width+offset,c+offset+width,null,null);
					//g.fillOval(r+width+offset,c+offset+width,width/2,width/2);
//					System.out.println("start found");
					row=img.getWidth()*10;
					col=img.getHeight()*10;
				}
			}
		}
		run(checkWall());
	}
	public void run(boolean wall){
		if(!wall){
			//move
		}
		else
			System.out.println("You cannot go that way!");
		if(!this.victory()){
			//wall=checkWall();
			run(checkWall());
		}
		win();
	}
	public boolean victory(){
		Color myRed = new Color(237,28,36);
		if(img.getRGB(loc[0]+width,loc[1]+width)!=myRed.getRGB())
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
		if(img.getRGB(loc[0], loc[1])==Color.white.getRGB())
			return false;
		else
			return true;
	}
	public int getMazeSize(){
		return frame.getHeight();
	}
	public void setMazeSize(int size){
		frame.setSize(750,750);
	}
	public void close(){
		frame.dispose();
	}
	
	public static void main(String[] args) throws IOException{
		boolean cont = true;
		Maze test;
		Scanner q = new Scanner(System.in);
		while(cont){
			test = new Maze();
			test.start();
			System.out.println("Play again? ");
			if(q.nextLine().indexOf("y")==-1)
				cont=false;
			else
				test.close();
		}
	}
}
