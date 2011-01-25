package pt.ipv.estv.stio.mobileBenchmark;

public class ParseResult {
	
	private long time;
	private long requestSize;
	
	public void setRequestSize(long requestSize) {
		this.requestSize = requestSize;
	}
	
	public long getRequestSize() {
		return requestSize;
	}
	
	public void setTime(long l) {
		this.time = l;
	}
	
	public long getTime() {
		return time;
	}
		

}
