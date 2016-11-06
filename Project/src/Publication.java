import java.util.ArrayList;
import java.util.HashMap;

public class Publication {
	
	private String publType;
	private ArrayList<String> authors = new ArrayList<String>();
	private String title;
	private int month, year;
	private HashMap<String,String> Attributes = new HashMap<String,String>();
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setPublType(String type){
		publType = type;
	}
	
	public void setMonth(int m){
		month = m;
	}
	
	public void setYear(int y){
		year = y;
	}
	
	public void addAuthor(String auth){
		authors.add(auth);
	}
	
	public void addAttr(String attr,String value){
		Attributes.put(attr,value);
	}
	
	public String getTitle(){
		return(title);
	}
	
	public boolean checkAttr(String key){
		return(Attributes.containsKey(key));
	}
	
	public String getAttr(String key){
		return(Attributes.get(key));
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	public String getType(){
		return publType;
	}

}