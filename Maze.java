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

public class Maze extends JComponent{
	private JFrame frame;
	private JPanel pane;
	public Maze(int num) throws IOException{
		//use num later, randomized
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(750,750);
		frame.setVisible(true);
		Graphics g = frame.getGraphics();
		BufferedImage img = ImageIO.read(new File("T:\\APCS\\final project\\Final\\src\\maze1.png"));
		g.drawImage(img, 0, 0, null);
	}
	public int getMazeSize(){
		return frame.getHeight();
	}
	public void setMazeSize(int size){
		frame.setSize(750,750);
	}
	public static void main(String[] args) throws IOException{
		Maze test = new Maze(0);
	}
}
