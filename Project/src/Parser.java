import java.io.*;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class Parser{
	
	public String file = "/Users/Sagar/Desktop/dblp.xml";
	public XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();													//XML Factory
	
	public void parseOne(){
		int i;
		String publication = "";
		try {
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));     // XML Event Reader
			//for(i = 0; i < 5000; i++){
			while(xmlEventReader.hasNext()){
				if(xmlEventReader.hasNext()){
					XMLEvent xmlEvent = xmlEventReader.nextEvent();
					if(xmlEvent.isStartElement()){
						StartElement startElement = xmlEvent.asStartElement();
						String[] level1 = {"article","inproceedings","proceedings","book","incollection","phdthesis","mastersthesis","www","person","data"};
						String[] level2 = {"author","editor","title","booktitle","pages","year","address","journal","volume","number","month","url","ee","cdrom","cite","publisher","note","crossref","isbn","series","school","chapter","publnr"};
						//String[] level3 = {"sub","sup","i","tt","ref"};
						for(String ele:level1){                                      // if first level
							//System.out.println(ele);
							if(startElement.getName().getLocalPart().equals(ele)){
								System.out.println("Publication : " + ele);
								publication = ele;
								break;
							}
						}
						for(String el:level2){										// if second level
							//System.out.println(el);
							if(startElement.getName().getLocalPart().equals(el)){
								System.out.print(el + " : ");
								while(xmlEventReader.hasNext()){
									if(xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(el)){
										break;
									}
									else if(xmlEvent.isCharacters()){
										System.out.println(xmlEvent.asCharacters().getData());
									}
									xmlEvent = xmlEventReader.nextEvent();
								}
							break;
							}
						}
					}
					else if(xmlEvent.isEndElement()){
						EndElement endElement = xmlEvent.asEndElement();
						if(endElement.getName().getLocalPart().equals(publication)){
							System.out.println("");
						}
					}
				}
			}
		//  }
		}catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
	}
}
		