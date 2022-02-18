package frontend;

public class ComboItems {
	private String key;
	private int value;
	
	public ComboItems(String key, int value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return key;
	}
	
	public String getKey() {
		return key;
	}
	
	public int getValue() {
		return value;
	}

}
