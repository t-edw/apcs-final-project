import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Action;

public class Maze extends JFrame{
	private JFrame frame;
	private JPanel pane;
	private BufferedImage img = null;
	private Graphics g;
	public Maze() throws IOException{
		Random rand = new Random();
		//int num = rand.nextInt(6);
		int num=3;
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(950,950);
		frame.setVisible(true);
		g = frame.getGraphics();
		int width=0;
		int offset=0;
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
			//WHY
			img = ImageIO.read(new File("src\\maze5.png"));
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
		start(offset,width);
		
	}
	public void start(int offset, int width){
		int r=0,c=0;
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
					g.fillOval(r+width+offset,c+offset+width,width/2,width/2);
//					System.out.println("start found");
					row=img.getWidth()*10;
					col=img.getHeight()*10;
				}
			}
		}
		run();
	}
	public void run(){
		boolean wall=false;
		if(!wall){
			//move
		}
		else
			System.out.println("You cannot go that way!");
		if(!this.victory())
			run();
		win();
	}
	public boolean victory(){
		Color myRed = new Color(237,28,36);
		if(img.getRGB(0/*location again*/,0)==myRed.getRGB())
			return true;
		else
			return false;
	}
	public void win(){
		String[] victoryMessages = {"You did it!", "You made it!",
									"Congratulations!",
									"Congratulations, you made it out!",
									"You win!","You are a winner!"};
		Random r = new Random();
		System.out.println(victoryMessages[r.nextInt(6)]);
	}
	public boolean checkWall(){
		if(img.getRGB(0/*wherever the character is*/, 0)==Color.white.getRGB())
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
	public static void main(String[] args) throws IOException{
		Maze test = new Maze();
	}
}
