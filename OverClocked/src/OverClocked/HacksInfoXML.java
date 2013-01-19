/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

/**
 *
 * @author Andrew Yiu
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
// This file was modified off the tutorial file from http://www.mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/ Credits to them for making the original file and teaching how to use XML in Java

public class HacksInfoXML {
    
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
    public HacksInfoXML() {
        fileLocation = "C:\\HackInfo.xml";
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
    public void createInfo() {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Attr attribute, attr;
            Element hack, description, cost;
            
            // Root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("HackInfo");
            doc.appendChild(rootElement);
            
            //Defense
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Shield");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Creates a Shield around you to protect you" + "\nfrom damage."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("750"));
            hack.appendChild(cost);
            
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Battle Grieves");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Walk on spikes without taking damage for" + "\n10 seconds."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("750"));
            hack.appendChild(cost);
            
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Invincible");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Become invincible."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("100000"));
            hack.appendChild(cost);
            
            //Power   
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Shoop");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Shoops da Whoops."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("750"));
            hack.appendChild(cost);

            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Energy Orbs");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Shoots orbs of antimatter."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("1000"));
            hack.appendChild(cost);

            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Piercing Shot");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Bullets pierce through enemies."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("500"));
            hack.appendChild(cost);
            
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Storm");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Eliminates everything you see on screen."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("1500"));
            hack.appendChild(cost);
            
            //Utility
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Time Freeze");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Freezes time and everything around you," + "\nexcept you. Lasts for 30 seconds."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("750"));
            hack.appendChild(cost);
            
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Fly");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Fly instead of walk."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("50000"));
            hack.appendChild(cost);
            
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("No Clip");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Walk through walls."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("750"));
            hack.appendChild(cost);
            
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Teleport");
            hack.setAttributeNode(attr);
            
            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Right click anywhere to teleport there."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("25000"));
            hack.appendChild(cost);
            
            //Passive
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Hp Increase");
            hack.setAttributeNode(attr);
            
            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Increases character maximum health by 50%."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("1000"));
            hack.appendChild(cost);
            
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Haste");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Raises character speed up."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("1000"));
            hack.appendChild(cost);
            
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Sharp Eyes");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Shows health and ammo."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("300"));
            hack.appendChild(cost);

            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Stance");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Will not be knocked back by enemy attacks."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("999"));
            hack.appendChild(cost);
            
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Power Guard");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Reduces damage dealt from bosses by 30%."));
            hack.appendChild(description);
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("2000"));
            hack.appendChild(cost);

            
            hack = doc.createElement("Hack");
            rootElement.appendChild(hack); 
            attr = doc.createAttribute("id");
            attr.setValue("Infinite Ammo");
            hack.setAttributeNode(attr);

            description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Infinite ammo."));
            hack.appendChild(description);     
            
            cost = doc.createElement("Cost");
            cost.appendChild(doc.createTextNode("50000"));
            hack.appendChild(cost);     
            
            // Write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\HackInfo.xml"));
            
            transformer.transform(source, result);

            System.out.println("Save File Created due to java.io.FileNotFoundException!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}

