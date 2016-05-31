
public class Pixel {
	private int x, y; private boolean isBlack;
	
	public Pixel(int x, int y, boolean isBlack) {
		this.x=x; this.y=y; this.isBlack=isBlack;
	}
	
	public void setBlack(boolean isBlack) {
		this.isBlack=isBlack;
	}
	
	public boolean isBlack() {
		return isBlack;
	}
}
