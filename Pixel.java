/**Pixel.java
 * Joshua Sathyaraj and Tamsin Edwards
 * Period G
 * June 1, 2016
 * 
 * The Pixel object used in Maze.java
 */
public class Pixel {
	private int x, y; private boolean isBlack;
	//constructor
	//creates a pixel object
	public Pixel(int x, int y, boolean isBlack) {this.x=x; this.y=y; this.isBlack=isBlack;}
	//returns value of x
	public int getX() {return x;}
	//sets the value of x to the new value
	public void setX(int x) {this.x=x;}
	//returns the value of y
	public int getY() {return y;}
	//sets the value of y to the new value
	public void setY(int y) {this.y=y;}
	//sets the value of isBlack to the new value
	public void setBlack(boolean isBlack) {this.isBlack=isBlack;}
	//returns the value of isBlack
	public boolean isBlack() {return isBlack;}
}
