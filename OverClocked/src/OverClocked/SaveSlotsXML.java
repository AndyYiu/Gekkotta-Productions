/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

/** Author: Stanley Fung
 * Date:
 * Teacher:
 * Description:
 * 
 */
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
//This file was modified off the tutorial file from http://www.mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/ Credits to them for making the original file and teaching how to use XML in Java

public class SaveSlotsXML {

    public String fileLocation;

    /* gets the file
     * 
     * pre: nothing
     * post: gets the location
     */
    public String getFileLocation() {
        return fileLocation;
    }

    /* constructor
     * 
     * pre: nothing
     * post: sets the location
     */
    public SaveSlotsXML() {
        fileLocation = "C:\\SavedGames.xml";
    }

    /* checks if first time loading the file
     * 
     * pre: nothing
     * post: checks if the file is there
     */
    public boolean firstTime() throws ParserConfigurationException, SAXException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc;
        try {
            doc = docBuilder.parse(fileLocation);

            return false;
        } catch (IOException ex) {

            return true;
        }


    }

    /* creates the xml
     * 
     * pre: nothing
     * post: makes the xml
     */
    public void createSlots() {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("SavedGames");
            doc.appendChild(rootElement);

            //SLOTS REMAINGNG: 
            Element slotsLeft = doc.createElement("SlotsLeft");
            rootElement.appendChild(slotsLeft);
            Element name = doc.createElement("slots");
            name.appendChild(doc.createTextNode("3"));
            slotsLeft.appendChild(name);

            //SLOTS REMAINGNG: 
            Element nextSlot = doc.createElement("NextSlot");
            rootElement.appendChild(nextSlot);
            name = doc.createElement("slot");
            name.appendChild(doc.createTextNode("0"));
            nextSlot.appendChild(name);

            //SLOTS//////////
            // staff elements
            Element slotOne = doc.createElement("Slot");
            rootElement.appendChild(slotOne);

            // set attribute to slotOne element
            Attr attr = doc.createAttribute("id");
            attr.setValue("1");
            slotOne.setAttributeNode(attr);

            // shorten way
            // slotOne.setAttribute("id", "1");

            //name elements
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("????????"));
            slotOne.appendChild(name);


            // MONEY elements
            Element money = doc.createElement("Money");
            money.appendChild(doc.createTextNode("0"));
            slotOne.appendChild(money);

            // MONEY elements
            Element level = doc.createElement("CurrentLevel");
            level.appendChild(doc.createTextNode("0"));
            slotOne.appendChild(level);

            Element stage = doc.createElement("CurrentStage");
            stage.appendChild(doc.createTextNode("0"));
            slotOne.appendChild(stage);
            
            Element hbought = doc.createElement("HacksBought");
            hbought.appendChild(doc.createTextNode("z"));
            slotOne.appendChild(hbought);
            
            Element hp = doc.createElement("HacksPower");
            hp.appendChild(doc.createTextNode("z"));
            slotOne.appendChild(hp);
            
            Element hd = doc.createElement("HacksDefense");
            hd.appendChild(doc.createTextNode("z"));
            slotOne.appendChild(hd);
            
            Element hu = doc.createElement("HacksUtility");
            hu.appendChild(doc.createTextNode("z"));
            slotOne.appendChild(hu);

            //SLOT 2////////////////////////////////////////////////////////////////////////////////////////////////
            // staff elements
            Element slotTwo = doc.createElement("Slot");
            rootElement.appendChild(slotTwo);

            // set attribute to slotOne element
            attr = doc.createAttribute("id");
            attr.setValue("2");
            slotTwo.setAttributeNode(attr);

            // shorten way
            // slotOne.setAttribute("id", "1");

            //name elements
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("????????"));
            slotTwo.appendChild(name);

            // MONEY elements
            money = doc.createElement("Money");
            money.appendChild(doc.createTextNode("0"));
            slotTwo.appendChild(money);

            // MONEY elements
            level = doc.createElement("CurrentLevel");
            level.appendChild(doc.createTextNode("0"));
            slotTwo.appendChild(level);

            stage = doc.createElement("CurrentStage");
            stage.appendChild(doc.createTextNode("0"));
            slotTwo.appendChild(stage);
            
            hbought = doc.createElement("HacksBought");
            hbought.appendChild(doc.createTextNode("z"));
            slotTwo.appendChild(hbought);
            
            hp = doc.createElement("HacksPower");
            hp.appendChild(doc.createTextNode("z"));
            slotTwo.appendChild(hp);
            
            hd = doc.createElement("HacksDefense");
            hd.appendChild(doc.createTextNode("z"));
            slotTwo.appendChild(hd);
            
            hu = doc.createElement("HacksUtility");
            hu.appendChild(doc.createTextNode("z"));
            slotTwo.appendChild(hu);
            
            //SLOT 3////////////////////////////////////////////////////////////////////////////////////////////////
            // staff elements
            Element slotThree = doc.createElement("Slot");
            rootElement.appendChild(slotThree);

            // set attribute to slotOne element
            attr = doc.createAttribute("id");
            attr.setValue("3");
            slotThree.setAttributeNode(attr);

            // shorten way
            // slotOne.setAttribute("id", "1");

            //name elements
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("????????"));
            slotThree.appendChild(name);

            // MONEY elements
            money = doc.createElement("Money");
            money.appendChild(doc.createTextNode("0"));
            slotThree.appendChild(money);

            // MONEY elements
            level = doc.createElement("CurrentLevel");
            level.appendChild(doc.createTextNode("0"));
            slotThree.appendChild(level);

            stage = doc.createElement("CurrentStage");
            stage.appendChild(doc.createTextNode("0"));
            slotThree.appendChild(stage);
            
            hbought = doc.createElement("HacksBought");
            hbought.appendChild(doc.createTextNode("z"));
            slotThree.appendChild(hbought);
            
            hp = doc.createElement("HacksPower");
            hp.appendChild(doc.createTextNode("z"));
            slotThree.appendChild(hp);
            
            hd = doc.createElement("HacksDefense");
            hd.appendChild(doc.createTextNode("z"));
            slotThree.appendChild(hd);
            
            hu = doc.createElement("HacksUtility");
            hu.appendChild(doc.createTextNode("z"));
            slotThree.appendChild(hu);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\SavedGames.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("Save File Created due to java.io.FileNotFoundException!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
