/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicalsession;

/**
 *
 * @author mzemroun
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseDom {
	public static void affFamily(Document document){
		final Element racine = document.getDocumentElement();
		final NodeList racineNoeuds = racine.getChildNodes();
		final int nbRacineNoeuds = racineNoeuds.getLength();

		for (int i = 0; i < nbRacineNoeuds; i++) {
			if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
				final Element family = (Element) racineNoeuds.item(i);

				System.out.println("\n*************Family************");
				final Element name = (Element) family.getElementsByTagName("name").item(0);
				final Element bdate = (Element) family.getElementsByTagName("bdate").item(0);
				final Element height = (Element) family.getElementsByTagName("height").item(0);
                                final Element weight = (Element) family.getElementsByTagName("Weight").item(0);
                                final Element image = (Element) family.getElementsByTagName("image").item(0);
                                
				System.out.println("name : " + name.getTextContent());
				System.out.println("bdate : " + bdate.getTextContent());
				System.out.println("height : " + height.getTextContent());
                                System.out.println("weight : " + weight.getTextContent());
                                System.out.println("image : " + image.getTextContent());
			}
		}
	}
	public static void addDuration(Document document){
		final Element racine = document.getDocumentElement();
		final NodeList racineNoeuds = racine.getChildNodes();
		final int nbRacineNoeuds = racineNoeuds.getLength();

		for (int i = 0; i < nbRacineNoeuds; i++) {
			if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
				final Element family = (Element) racineNoeuds.item(i);
                                final Element history = (Element) family.getElementsByTagName("history").item(0);
                                final Element trips = (Element) history.getElementsByTagName("trip").item(0);
                                trips.setAttribute("duration", "PT15H");
                                                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = null;
                        try {
                            transformer = transformerFactory.newTransformer();
                        } catch (TransformerConfigurationException ex) {
                            Logger.getLogger(ParseDom.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        DOMSource source = new DOMSource(document);
                        StreamResult result = new StreamResult(new File("Family.xml"));
                        try {
                            transformer.transform(source, result);
                        } catch (TransformerException ex) {
                            Logger.getLogger(ParseDom.class.getName()).log(Level.SEVERE, null, ex);
                        }
			}
		}
	}
        public static void DateParser(Document document) throws ParseException{
		final Element racine = document.getDocumentElement();
		final NodeList racineNoeuds = racine.getChildNodes();
		final int nbRacineNoeuds = racineNoeuds.getLength();
                for (int i = 0; i < nbRacineNoeuds; i++) {
                    if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			final Element family = (Element) racineNoeuds.item(i);
                        final Element bdate = (Element) family.getElementsByTagName("bdate").item(0);
                        String pattern = "yyyy/MM/dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        Date date = simpleDateFormat.parse(bdate.getTextContent());
                        String pattern1 = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
                        String date1 = simpleDateFormat1.format(date);
                        bdate.setTextContent(date1);
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = null;
                        try {
                            transformer = transformerFactory.newTransformer();
                        } catch (TransformerConfigurationException ex) {
                            Logger.getLogger(ParseDom.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        DOMSource source = new DOMSource(document);
                        StreamResult result = new StreamResult(new File("Family.xml"));
                        try {
                            transformer.transform(source, result);
                        } catch (TransformerException ex) {
                            Logger.getLogger(ParseDom.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }    
                }    
	}
        public static String encode(String uRL){
            String outPut="";
            try{
                File fichier=new File(uRL);
                FileInputStream imageInFile = new FileInputStream(fichier);
                byte imageData[] = new byte[(int) fichier.length()];
		imageInFile.read(imageData);
		outPut = Base64.getEncoder().encodeToString(imageData);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ParseDom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ParseDom.class.getName()).log(Level.SEVERE, null, ex);
            }
            return outPut;
        }
        public static void addPicture(String namePerson,String uRL,Document document){
            final Element racine = document.getDocumentElement();
            final NodeList racineNoeuds = racine.getChildNodes();	
            final int nbRacineNoeuds = racineNoeuds.getLength();
            for (int i = 0; i < nbRacineNoeuds; i++) {
                if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
	            final Element family = (Element) racineNoeuds.item(i);
                    final Element name = (Element) family.getElementsByTagName("name").item(0);
                    final Element image = (Element) family.getElementsByTagName("image").item(0);
                    if(name.getTextContent().equals(namePerson)){
                        image.setTextContent(encode(uRL));
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = null;
                        try {
                            transformer = transformerFactory.newTransformer();
                        } catch (TransformerConfigurationException ex) {
                            Logger.getLogger(ParseDom.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        DOMSource source = new DOMSource(document);
                        StreamResult result = new StreamResult(new File("Family.xml"));
                        try {
                            transformer.transform(source, result);
                        } catch (TransformerException ex) {
                            Logger.getLogger(ParseDom.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }               
        }
	public static void main(final String[] args) throws ParseException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);

		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new File("Family.xml"));
			
                        addPicture("ssss", "test.png", document);
                        addDuration(document);
			affFamily(document);
                        DateParser(document);
                        affFamily(document);
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}

	}
}