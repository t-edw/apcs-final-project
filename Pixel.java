/**Pixel.java
 * Joshua Sathyaraj and Tamsin Edwards
 * Period G
 * June 1, 2016
 * 
 * The Pixel object used in Maze.java
 */
public class Pixel {
	private int x, y; private boolean isBlack;
	
	//contructor
	//creates a pixel object
	public Pixel(int x, int y, boolean isBlack) {
		this.x=x; this.y=y; this.isBlack=isBlack;
	}
	//method: void setBlack(boolean isBlack)
	//changes the value of isBlack for the pixel object
	public void setBlack(boolean isBlack) {
		this.isBlack=isBlack;
	}
	//method: boolean isBlack()
	//returns the value of isBlack for the current pixel
	public boolean isBlack() {
		return isBlack;
	}
}
