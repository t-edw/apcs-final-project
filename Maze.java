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
	public Maze(int num){
		//use num later, randomized
		frame = new JFrame();
		frame.setSize(750,750);
		frame.setVisible(true);
		Graphics g = frame.getGraphics();
		//Image img = new BufferedImage(750,750,1,TYPE_BYTE_INDEXED);
		
	}
	public int getMazeSize(){
		return frame.getHeight();
	}
	public void setMazeSize(int size){
		frame.setSize(750,750);
	}
	public static void main(String[] args){
		Maze test = new Maze(0);
	}
}
