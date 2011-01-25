package pt.ipv.estv.stio.mobileBenchmark;

public class ParseResult {
	
	private int time;
	private long requestSize;
	
	public void setRequestSize(long requestSize) {
		this.requestSize = requestSize;
	}
	
	public long getRequestSize() {
		return requestSize;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}
		

}
