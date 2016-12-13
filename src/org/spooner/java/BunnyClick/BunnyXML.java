package org.spooner.java.BunnyClick;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BunnyXML {
	//members
	//constructors
	//methods
	public static final String[][] parse(String fileName, String tagName, int elementCount){
		Document doc=getDocument(fileName);
		NodeList nodes=doc.getElementsByTagName(tagName);
		int numNodes=nodes.getLength();
		//init x2 String array
		String[][] parsedElements=new String[numNodes][elementCount];
		for(int i=0;i<numNodes;i++){
			Node n=nodes.item(i);
			NodeList children=n.getChildNodes();
			int elementIndex=0;
			for(int j=0;j<children.getLength();j++){
				//for each child (ex: building, achievement, etc.)
				Node c=children.item(j);
				String content=c.getTextContent();
				//remove tabs and new lines
				content=content.trim();
				//don't add empty ones
				if(!content.isEmpty()){
					//this is one we want
					parsedElements[i][elementIndex]=content;
					//increment elementIndex
					elementIndex++;
				}
			}
		}
		return parsedElements;
	}
	
	private static final Document getDocument(String fileName){
		Document doc=null;
		try {
			DocumentBuilder dcb=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			if(BunnyClick.IS_TEST){
				doc = dcb.parse(new File(fileName));
			}
			else{
				doc = dcb.parse(ClassLoader.class.getResourceAsStream("/" + fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
		}
		return doc;
	}
}
