import java.awt.Font;

import javax.swing.JLabel;
/**Timer.java
 * Joshua Sathyaraj and Tamsin Edwards
 * Period G
 * June 1, 2016
 * 
 * Creates a timer that counts up from zero and only starts counting when stop is set to false
 */
@SuppressWarnings("serial")
public class Timer extends JLabel implements Runnable {
	private boolean stop;
	private int min, sec;
	private Thread thread;
	//constructor
	//creates Timer object
	public Timer() {
		super("time");
		thread = new Thread(this);
		stop=true; min=0; sec=0;
		this.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
	}
	//sets value of stop to new value
	public void setStop(boolean stop) {
		this.stop=stop;
		if(!stop)
			thread.start();
	}
	//returns the time as a string
	public String getTimeString() {
		String t="";
		if (min<10)
			t="0"+min;
		else
			t+=min;
		t+=":";
		if (sec<10)
			t+="0"+sec;
		else
			t+=sec;
		
		return t;
	}
	//makes sure the minutes change as well
	public void fixOverlap() {
		if (sec>=60) {
			min+=sec/60;
			sec=sec%60;
		}
	}
	//run method from Interface Runnable
	@Override
	public void run() {
		while(!stop) {
			this.setText(getTimeString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			sec++;
			fixOverlap();
		}
	}
}
