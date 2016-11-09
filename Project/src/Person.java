import java.util.ArrayList;

public class Person {

	private String PrimName;
	private ArrayList<String> names = new ArrayList<String>();
	private int NoPubl;
	
	public void setPrimName(String primName){
		this.PrimName = primName;
	}
	
	public void publFound(){
		NoPubl++;
	}
	
	public void addName(String name){
		names.add(name);
	}
	
	public String getPrimName(){
		return(PrimName);
	}
	
	public int getNoOfPubl(){
		return(NoPubl);
	}
	
	public boolean ifSame(String name){
		return(names.contains(name));
	}
	
}
