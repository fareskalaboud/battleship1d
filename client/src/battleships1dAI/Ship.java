package battleships1dAI;

public class Ship {
	private String name;
	private int size;
	private boolean isHorizontal;
	
	
	public Ship(String name, int size, boolean isHorizontal){
		this.name = name;
		this.size = size;
		this.isHorizontal = isHorizontal;
	}

	public boolean isHorizontal(){
		return isHorizontal;
	}
	
	public String getName(){
		return name;
	}
	
	public int getSize(){
		return size;
	}
}
