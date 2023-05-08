import java.io.Serializable;
import java.util.ArrayList;

class Info implements Serializable{
	ArrayList<Integer> specific;
	ArrayList<String> active;
	String message;
	
	public Info() {
		
	}
	
	public String toString() {
		return message;
	}
	
	public boolean isSpecific() {
		if(specific == null) {
			return false;
		}
		return !(specific.isEmpty());
	}
}