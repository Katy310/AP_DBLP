import java.util.ArrayList;

public class Main {

	private static ArrayList<Publication> publications = new ArrayList<Publication>();
	private static ArrayList<Person> persons = new ArrayList<Person>();
	private static String Sorter = "";
	
	public static void main(String[] args) {
		Parser pars = new Parser();
		pars.Initialize();
		pars.Query1("author","Hans Ulrich Simon","BetweenYear",1996, 2010);
		System.exit(0);
	}

	public static void addPublications(Publication pub){
		publications.add(pub);
	}
	
	public static void addPerson(Person pers){
		persons.add(pers);
	}

	public static void showAllPublications(){
		for(Publication pub:publications){
			System.out.println(pub.toString());
		}
	}
	
	public static void showAllPersons(){
		for(Person pers:persons){
			System.out.println(pers.toString());
		}
	}
	
	public static ArrayList<Person> getAllAuthors(){
		return(persons);
	}
	
	public static ArrayList<Publication> getAllPublications(){
		return(publications);
	}
	
	public static void setPersons(ArrayList<Person> auths){
		persons = auths;
	}
}
