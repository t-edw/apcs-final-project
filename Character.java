import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Character implements Runnable {
	
	JFrame frame;
	int x = 20, y = 20, xa = 1, ya=1, side=20;
	Canvas canvas;
	BufferStrategy bufferStrategy;
	boolean running = true;
	BufferedImage img;
	
	public Character() throws IOException {
		frame = new JFrame("Maze");
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setSize(800, 800);
		canvas = new Canvas();
		canvas.setBounds(0, 0, 800, 800);
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
		img = ImageIO.read(new File("src\\maze1.png"));
		bufferStrategy.getDrawGraphics().drawImage(img, 20, 30, 800, 800, null);
	}
	public void move(KeyEvent e) {
		bufferStrategy.getDrawGraphics().clearRect(x, y, side, side);
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP : y-=ya; break;
		case KeyEvent.VK_DOWN : y+=ya; break;
		case KeyEvent.VK_LEFT : x-=xa; break;
		case KeyEvent.VK_RIGHT : x+=xa; break;
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
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.drawImage(ImageIO.read(new File("src\\ghost.png")), x, y, side, side, null);
		bufferStrategy.show();
	}
	
	public static void main(String[] args) throws IOException {
		Character dot = new Character();
		new Thread(dot).start();
	}
	
	
}