import java.util.ArrayList;

public class Person {

	private String PrimName;
	private ArrayList<String> names = new ArrayList<String>();
	private int NoPubl;
	
	public Person(){
		PrimName = "";
		NoPubl = 0;
	}
	
	public ArrayList<String> getNames(){
		return names;
	}
	
	public void setPrimName(String primName){
		this.PrimName = primName;
	}
	
	public void publFound(){
		NoPubl++;
	}
	
	public void addName(String name){
		if(names.size() == 0 && PrimName.equals("")){
			PrimName = name;
		}
		names.add(name);
	}
	
	public String getPrimName(){
		return(PrimName);
	}
	
	public int getNoOfPubl(){
		return(NoPubl);
	}
	
	public boolean ifSame(String name){
		boolean x = (names.contains(name) || PrimName.equals(name));
		return(x);
	}
	
	public String toString(){
		String obj;
		obj = "Primary Name : " + PrimName + "\n";
		for(String auth : names){
			obj += "Author : " + auth + "\n";
		}
		obj += NoPubl + "\n";
		return(obj);
	}
	
	public void addnoPubl(int n){
		NoPubl += n;
	}
	
}
