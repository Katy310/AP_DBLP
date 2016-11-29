import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Publication implements Comparable<Publication>{
	
	private String publType;
	private ArrayList<String> authors = new ArrayList<String>();
	private String title;
	private String month;
	private int year;
	private HashMap<String,String> Attributes = new HashMap<String,String>();
	private static String RelevanceWord;
	
	@Override
	public int compareTo(Publication o) {
		if (o.getYear() > this.getYear())
			{
				return 1;
			}
			else if (o.getYear() < this.getYear())
			{
				return -1;
			}
			else	
			{
				return 0;
			}
	}

	public void setTitle(String title){
		this.title = title;
	}
	
	public void setPublType(String type){
		publType = type;
	}
	
	public void setMonth(String m){
		month = m;
	}
	
	public void setYear(int y){
		year = y;
	}
	
	public void addAuthor(String auth){
		authors.add(auth);
	}
	
	public String getFuckingAuthors(){
		String obj = "";
		for(String a : authors){
			obj += a;
			obj += "\n";
		}
		return(obj);
	}
	
	public String getURL(){
		if(Attributes.containsKey("url")){
			return Attributes.get("url");
		}
		else{
			return("");
		}
	}
	
	public String getPages(){
		if(Attributes.containsKey("pages")){
			return Attributes.get("pages");
		}
		else{
			return("");
		}
	}
	
	public String getVolume(){
		if(Attributes.containsKey("volume")){
			return Attributes.get("volume");
		}
		else{
			return("");
		}
	}
	
	public String getJournalBook(){
		if(Attributes.containsKey("journal")){
			return Attributes.get("journal");
		}
		else if(Attributes.containsKey("booktitle")){
			return Attributes.get("booktitle");
		}
		else{
			return ("");
		}
	}
	
	public void addAttr(String attr,String value){
		if(attr.equals("author")){
			this.addAuthor(value);
		}
		else if(attr.equals("title")){
			this.setTitle(value);
		}
		else if(attr.equals("year")){
			this.setYear(Integer.parseInt(value));
		}
		else if(attr.equals("month")){
			this.setMonth(value);
		}
		else{
			Attributes.put(attr,value);
		}
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
	
	public String getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	public String getType(){
		return publType;
	}
	
	public boolean checkAuthor(String auth){
		return(authors.contains(auth));
	}
	
	public String toString(){
		String obj;
		obj = "\nPublication : " + publType;
		for(String auth:authors){
			obj += "\nAuthor : " + auth;
		}
		obj += "\nTitle : " + title;
		obj += "\nYear : " + year;
		for(String key:Attributes.keySet()){
			obj += "\n" + key + " : " + Attributes.get(key);
		}
		obj += "\n";
		return(obj);
	}
	
	public boolean givenYear(int y){
		return(year >= y);
	}
	
	public boolean inBetweenYears(int y1, int y2){
		return(y1 >= year && year >= y2);
	}

	public static void setRelevanceWord(String word){
		RelevanceWord = word;
	}
	
	public ArrayList<String> getAuthors(){
		return authors;
	}
	
	public static Comparator<Publication> SortByRelevanceTitle = new Comparator<Publication>() {

		public int compare(Publication pub1, Publication pub2) {
			
			int rel1 = 0, rel2 = 0;
			String[] splited1 = pub1.getTitle().split(" ");
			String[] splited2 = pub1.getTitle().split(" ");
			for(String s: splited1){
				if(s.contains(RelevanceWord)){
					rel1++;
				}
			}
			for(String s: splited2){
				if(s.contains(RelevanceWord)){
					rel2++;
				}
			}
			return(rel1 - rel2);	
		}	
	};
	
	public static Comparator<Publication> SortByRelevanceAuthor = new Comparator<Publication>() {

		public int compare(Publication pub1, Publication pub2) {
			
			int rel1 = 0, rel2 = 0;
			for(String s: pub1.getAuthors()){
				if(s.contains(RelevanceWord)){
					rel1++;
				}
			}
			for(String s: pub2.getAuthors()){
				if(s.contains(RelevanceWord)){
					rel2++;
				}
			}
			return(rel1 - rel2);	
		}	
	};

}