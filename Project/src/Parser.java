import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.Attribute;


public class Parser{
	
	private String file = "/Users/Sagar/Desktop/dblp.xml";
	private XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();													//XML Factory
	private Person person = new Person();
	private Publication pub = new Publication();
	private String lev1,lev2;
	private ArrayList<String> level1 = new ArrayList<String>(Arrays.asList("article","inproceedings","proceedings","book","incollection","phdthesis","mastersthesis","www","person","data"));
	private ArrayList<String> level2 = new ArrayList<String>(Arrays.asList("author","editor","title","booktitle","pages","year","address","journal","volume","number","month","url","ee","cdrom","cite","publisher","note","crossref","isbn","series","school","chapter","publnr"));
	
	
	
	
	public void getAllAuthors(){
		try{
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));     // XML Event Reader
			while(xmlEventReader.hasNext()){
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				if(xmlEvent.isStartElement()){
					StartElement startElement = xmlEvent.asStartElement();
					lev1 = startElement.getName().getLocalPart();
					if(lev1.equals("www")){
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = xmlEvent.asStartElement().getAttributes();
						Attribute at;
						at = attributes.next();
						while(attributes.hasNext() && !(at.getName().equals("key"))){
							at = attributes.next();
						}
						String val = at.getValue();
						if(val.contains("homepages/")){
							person = new Person();
						}
					}
					else if(level2.contains(lev1)){
						lev2 = startElement.getName().getLocalPart();
						while(xmlEventReader.hasNext()){					//taking up all level2
							if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(lev2)){       // skipped level 3
								break;
							}
							else if(xmlEvent.isCharacters() && lev2.equals("author")){
									person.addName(xmlEvent.asCharacters().getData());
								}
						xmlEvent = xmlEventReader.nextEvent();
						}
					}
				}
				else if(xmlEvent.isEndElement()){
					EndElement endElement = xmlEvent.asEndElement();
					if(endElement.getName().getLocalPart().equals("www")){
						Main.addPerson(person);
					}
				}
			}
		}catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }	
		System.out.println("All Authors Saved Successfully! :D");
	}
	
	public void parseAndSave(){
	
		char type = 'x';
		Publication pub = new Publication();
		Person person = new Person();
		try {
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));     // XML Event Reader
			//for(int i = 0; i < 5000; i++){
			while(xmlEventReader.hasNext()){
				if(xmlEventReader.hasNext()){
					XMLEvent xmlEvent = xmlEventReader.nextEvent();
					if(xmlEvent.isStartElement()){
						StartElement startElement = xmlEvent.asStartElement();
						if(level1.contains(startElement.getName().getLocalPart())){         // if level 1
							lev1 = startElement.getName().getLocalPart();
							Iterator<Attribute> attributes = xmlEvent.asStartElement().getAttributes();
							Attribute at;
							//System.out.println("Publication : " + lev1);
							at = attributes.next();
							while(attributes.hasNext() && !(at.getName().equals("key"))){
								at = attributes.next();
							}
							String val = at.getValue();
//							if(at.getName().equals("key")){
							//System.out.println("key : " + val);
//							}
							if(lev1.equals("person") | lev1.equals("data")){
								type = 'a'; //type is person
							}else if(lev1.equals("www") && val.contains("homepages/")){       // Person Records
									type = 'b';
									System.out.println("Person");
									person = new Person();
							}else{
								type = 'p'; //type is publication
								//System.out.println("Publication");
								//pub = new Publication();
								//pub.setPublType(lev1);
							}
						}
						if(level2.contains(startElement.getName().getLocalPart())){ 		// if level 2
							lev2 = startElement.getName().getLocalPart();
							//System.out.print(lev2 + " : ");
							while(xmlEventReader.hasNext()){					//taking up all level2
								if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(lev2)){       // skipped level 3
									break;
								}
								else if(xmlEvent.isCharacters()){
									String val = xmlEvent.asCharacters().getData();
									//System.out.println(val);
									if(type == 'p'){   					//if publication
										//pub.addAttr(lev2, val);
									}else if(type == 'b' && lev2.equals("author")){
										person.addName(val);
									}
								}
								xmlEvent = xmlEventReader.nextEvent();
							}
						}
					}
					else if(xmlEvent.isEndElement()){		//end level1									
						EndElement endElement = xmlEvent.asEndElement();
						if(endElement.getName().getLocalPart().equals(lev1)){
							//System.out.println("");
							if(type == 'p'){   			//if publication
								//Main.addPublications(pub);
							}else if(type == 'b'){
								Main.addPerson(person);
							}
						}
					}
				}
			}
		}catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
	}
}
		