import java.util.ArrayList;

public class Main {

	private static ArrayList<Publication> publications = new ArrayList<Publication>(); 
	//private static ArrayList<Person> persons = new ArrayList<Person>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parser pars = new Parser();
		pars.parseAndSave();
		showAllPublications();
	}
	
	public static void addPublications(Publication pub){
		publications.add(pub);
	}
	
	public static void showAllPublications(){
		for(Publication pub:publications){
			System.out.println(pub.toString());
		}
	}

}
