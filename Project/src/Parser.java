import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	private List<String> level1 = new ArrayList<String>(Arrays.asList("article","inproceedings","proceedings","book","incollection","phdthesis","mastersthesis","www"));
	private List<String> level2 = new ArrayList<String>(Arrays.asList("author","editor","title","booktitle","pages","year","address","journal","volume","number","month","url","ee","cdrom","cite","publisher","note","crossref","isbn","series","school","chapter","publnr"));
	private List<Person> Authors = new ArrayList<Person>();
	private List<Person> Query2Result = new ArrayList<Person>();
	private Map<String,Integer> Authorvsnoofpub = new HashMap<String,Integer>(); 
	private List<Publication> Query1aResult = new ArrayList<Publication>();
	private List<Publication> Query1bResult = new ArrayList<Publication>();
	
	public void Initialize(){
		boolean www = false;
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
							www = true;
						}
						else{
							www = false;
						}
					}
					else if(lev1.equals("author")){ 		// if level 2
						lev2 = startElement.getName().getLocalPart();
						//System.out.print(lev2 + " : ");
						while(xmlEventReader.hasNext()){					//taking up all level2
							if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(lev2)){       // skipped level 3
								break;
							}
							else if(xmlEvent.isCharacters() && www){
								person.addName(xmlEvent.asCharacters().getData());
							}
							else if(xmlEvent.isCharacters() && !www){
								String val = xmlEvent.asCharacters().getData();
								if(Authorvsnoofpub.containsKey(val)){
									int no = Authorvsnoofpub.get(val);
									Authorvsnoofpub.remove(val);
									Authorvsnoofpub.put(val,no + 1);
								}
								else{
									Authorvsnoofpub.put(val,1);
								}
							}
							xmlEvent = xmlEventReader.nextEvent();
						}
					}
				}
				else if(xmlEvent.isEndElement()){
					EndElement endElement = xmlEvent.asEndElement();
					if(endElement.getName().getLocalPart().equals("www")){
						if(www){
							Authors.add(person);
							//System.out.println("here" + Authors.size());
							www = false;
						}
					}
				}
			}
		}catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
		System.out.println("Size : " + Authors.size());
		System.out.println("All Authors Saved Successfully! :D");
		System.out.println("HashMap Created");
	}
	
	public List<Person> getAuthors(){
		return Authors;
	}
	
	
	
	public void Query2(int k){
		for(Person p : Authors){
			if(Authorvsnoofpub.containsKey(p.getPrimName())){
				p.addnoPubl(Authorvsnoofpub.get(p.getPrimName()));
			}
			ArrayList<String> names = p.getNames();
			for(String name : names){
				if(Authorvsnoofpub.containsKey(name)){
					p.addnoPubl(Authorvsnoofpub.get(name));
				}
			}
			if(p.getNoOfPubl() > k){
				Query2Result.add(p);
			}
		}
		System.out.println(Query2Result.size());
	}
	
	public void Query1(String choice, String tag, String Sorter, int y1, int y2){
		boolean publtype = false;
		boolean aquery = false;
		boolean tquery = false;
		Person Auth = new Person();
		if(choice.equals("author")){
			for(Person per : Authors){
				if(per.ifSame(tag)){
					Auth = per;
					//System.out.println("Person Found !");
				}
			}
		}
		try{
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));     // XML Event Reader
			while(xmlEventReader.hasNext()){
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				if(xmlEvent.isStartElement()){
					StartElement startElement = xmlEvent.asStartElement();
					lev1 = startElement.getName().getLocalPart();
					if(level1.contains(lev1)){
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = xmlEvent.asStartElement().getAttributes();
						Attribute at;
						at = attributes.next();
						while(attributes.hasNext() && !(at.getName().equals("key"))){
							at = attributes.next();
						}	
						String val = at.getValue();
						if(val.contains("homepages/") && lev1.equals("www")){
							publtype = false;
						}
						else{
							publtype = true;
							pub = new Publication();
							pub.setPublType(lev1);
						}
						//Publ Type
					}
					if(level2.contains(lev1)){
						lev2 = startElement.getName().getLocalPart();
						while(xmlEventReader.hasNext()){					//taking up all level2
							if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(lev2)){       // skipped level 3
								break;
							}
							else if(xmlEvent.isCharacters() && lev2.equals("author") && publtype){
								String val = xmlEvent.asCharacters().getData();
								if(choice.equals("author") && Auth.ifSame(val)){
									aquery = true;
								}
								pub.addAttr(lev2, val);
							}
							else if(xmlEvent.isCharacters() && lev2.equals("title") && publtype){
								String val = xmlEvent.asCharacters().getData();
								if(choice.equals("title") && val.contains(tag)){
									tquery = true;
								}
								pub.addAttr(lev2, val);
							}
							else if(xmlEvent.isCharacters() && publtype){
								String val = xmlEvent.asCharacters().getData();
								pub.addAttr(lev2, val);
							}
							xmlEvent = xmlEventReader.nextEvent();
						}
					}
				}
				else if(xmlEvent.isEndElement()){		//end level1
					EndElement endElement = xmlEvent.asEndElement();
					if(level1.contains(endElement.getName().getLocalPart())){
						if(publtype && aquery){
							Query1aResult.add(pub);
							aquery = false;
							publtype = false;
						}
						else if(publtype && tquery){
							Query1bResult.add(pub);
							tquery = false;
							publtype = false;
						}
					}
				}
			}
		}catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
		Publication.setRelevanceWord(tag);
		if(choice.equals("author")){
			List<Publication> temp = new ArrayList<Publication>();
			if(Sorter.equals("Date")){
				Collections.sort(Query1aResult);
			}
			else if(Sorter.equals("Relevance")){
				Collections.sort(Query1aResult,Publication.SortByRelevance);
			}
			else if(Sorter.equals("Year")){
				for(Publication p : Query1aResult){
					if(p.givenYear(y1)){
						temp.add(p);
					}
				}
				Query1aResult = temp;
			}
			else if(Sorter.equals("BetweenYear")){
				for(Publication p : Query1aResult){
					if(p.inBetweenYears(y1, y2)){
						temp.add(p);
					}
				}
				Query1aResult = temp;
			}
			System.out.println(Query1aResult.size());
			for(Publication p : Query1aResult){
				System.out.println(p);
			}
		}
		else{
			List<Publication> temp = new ArrayList<Publication>();
			if(Sorter.equals("Date")){
				Collections.sort(Query1bResult);
			}
			else if(Sorter.equals("Relevance")){
				Collections.sort(Query1bResult,Publication.SortByRelevance);
			}
			else if(Sorter.equals("Year")){
				for(Publication p : Query1bResult){
					if(p.givenYear(y1)){
						temp.add(p);
					}
				}
				Query1bResult = temp;
			}
			else if(Sorter.equals("BetweenYear")){
				for(Publication p : Query1bResult){
					if(p.inBetweenYears(y1, y2)){
						temp.add(p);
					}
				}
				Query1bResult = temp;
			}
			System.out.println(Query1bResult.size());
			for(Publication p : Query1bResult){
				System.out.println(p);
			}
		}
		
	}
}


		