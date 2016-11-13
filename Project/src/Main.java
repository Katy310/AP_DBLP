import java.util.ArrayList;

public class Main {

	private static ArrayList<Publication> publications = new ArrayList<Publication>();
	private static ArrayList<Person> persons = new ArrayList<Person>();

	public static void main(String[] args) {
		//new GUI();
		// TODO Auto-generated method stub
		Parser pars = new Parser();
		pars.parseAndSave();
		showAllPublications();
		showAllPersons();
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
}
