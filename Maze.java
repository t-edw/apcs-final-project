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

public class Maze extends JComponent{
	private JFrame frame;
	private JPanel pane;
	public Maze() throws IOException{
		Random rand = new Random();
		int num = rand.nextInt(6);
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(750,750);
		frame.setVisible(true);
		Graphics g = frame.getGraphics();
		BufferedImage img = null;
		if(num==0){
			img = ImageIO.read(new File("src\\maze1.png"));
		}
		else if(num==1){
			img = ImageIO.read(new File("src\\maze2.png"));
		}
		else if(num==2){
			img = ImageIO.read(new File("src\\maze3.png"));
		}
		else if(num==3){
			img = ImageIO.read(new File("src\\maze4.png"));
		}
		else if(num==4){
			img = ImageIO.read(new File("src\\maze5.png"));
		}
		else{
			img = ImageIO.read(new File("src\\maze6.png"));
		}
		g.drawImage(img, 0, 0, 750, 750, null);
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
