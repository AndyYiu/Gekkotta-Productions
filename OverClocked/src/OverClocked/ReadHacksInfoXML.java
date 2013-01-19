/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

/**
 *
 * @author Andrew Yiu
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
//This file was modified off the tutorial file from http://www.mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/ Credits to them for making the original file and teaching how to use XML in Java

public class ReadHacksInfoXML {
    
    private String[] hack;
    private String[] description;
    public String fileLocation;
    private String[] cost;

    /* constructor
     * 
     * pre: nothing
     * post: makes the arrays
     */
    public ReadHacksInfoXML() {
        hack = new String[17];
        description = new String[17];
        cost = new String[17];
        fileLocation = "C:\\HackInfo.xml";
        readData();
    }
    
    /* get cost
     * 
     * pre: hack slot
     * post: get's the cost
     */
    public int getCost(int slot){
        return Integer.parseInt(cost[slot]);
    }

    /* get hacks
     * 
     * pre: hack slot
     * post: get's the hacks
     */
    public String getHack(int slot) {
        return hack[slot];
    }
    
    /* get description
     * 
     * pre: hack slot
     * post: get's the description
     */
    public String getDescription(int slot){
        return description[slot];
    }

    /* reads the xml
     * 
     * pre: nothing
     * post: get's the information for arrays
     */
    public void readData() {
        try {
            File fXmlFile = new File(fileLocation);
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            
            NodeList nList = doc.getElementsByTagName("Hack");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    hack[i] = (eElement.getAttribute("id"));
                    description[i] = (getTagValue("Description", eElement));
                    cost[i] = (getTagValue("Cost", eElement));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* get tag value
     * 
     * pre: tag name and the element
     * post: get's the value of tag
     */
    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }
}

