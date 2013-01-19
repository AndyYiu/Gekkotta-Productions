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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//This file was modified off the tutorial file from http://www.mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/ Credits to them for making the original file and teaching how to use XML in Java

public class ModifySaveSlotXML {

    private int slotNum;
    private String pName, newVal, pHacksBought, pHacksPower, pHacksDefense, pHacksUtility, pHacksPassive;
    private ReadSaveSlotsXML readSaves = new ReadSaveSlotsXML();
    private int pMoney;
    private int pLevel, pStage;
    private boolean newAcc;//True = subtract from slotsLeft

    /* lonely constructor
     * 
     * pre: nothing
     * post: does nothing
     */
    public ModifySaveSlotXML() {
    }

    /* saves account info
     * 
     * pre: save slot and names and levels
     * post: saves progress
     */
    public void save(int s, String name, int money, int level, int stage, boolean acc) {
        slotNum = s;
        pName = name;
        pMoney = money;
        pLevel = level;
        pStage = stage;
        newAcc = acc;
        try {
            String filepath = "C:\\SavedGames.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            // Get the root element
            Node savedGames = doc.getFirstChild();

            // Get the staff element , it may not working if tag has spaces, or
            // whatever weird characters in front...it's better to use
            // getElementsByTagName() to get it directly.
            // Node staff = company.getFirstChild();
            // Get the staff element by tag name directly
            Node slot = doc.getElementsByTagName("Slot").item(slotNum);

            NodeList list = slot.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                // get the salary element, and update the value
                if ("Name".equals(node.getNodeName())) {
                    node.setTextContent(pName);
                }
                if ("Money".equals(node.getNodeName())) {
                    node.setTextContent("" + pMoney);
                }
                if ("CurrentLevel".equals(node.getNodeName())) {
                    node.setTextContent("" + pLevel);
                }
                if ("CurrentStage".equals(node.getNodeName())) {
                    node.setTextContent("" + pStage);
                }
            }
            if (newAcc == true) {

                NodeList nList1 = doc.getElementsByTagName("SlotsLeft");
                for (int temp = 0; temp < nList1.getLength(); temp++) {

                    Node nNode = nList1.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String tempVar = (getTagValue("slots", eElement));
                        int sLeft = Integer.parseInt(tempVar);
                        sLeft = sLeft - 1;
                        this.newVal = ("" + sLeft);

                    }
                }

                Node slotsLeft = doc.getElementsByTagName("SlotsLeft").item(0);

                NodeList list2 = slotsLeft.getChildNodes();

                for (int i = 0; i < list2.getLength(); i++) {

                    Node node = list2.item(i);
                    if ("slots".equals(node.getNodeName())) {

                        node.setTextContent("" + newVal);
                    }
                }
                //Increase nextSlot by 1
                NodeList nList2 = doc.getElementsByTagName("NextSlot");
                for (int temp = 0; temp < nList2.getLength(); temp++) {

                    Node nNode = nList2.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String tempVar = getTagValue("slot", eElement);
                        int sLeft = Integer.parseInt(tempVar);
                        sLeft = sLeft + 1;
                        this.newVal = ("" + sLeft);

                    }
                }

                slotsLeft = doc.getElementsByTagName("NextSlot").item(0);

                list2 = slotsLeft.getChildNodes();

                for (int i = 0; i < list2.getLength(); i++) {
                    Node node = list2.item(i);
                    if ("slot".equals(node.getNodeName())) {

                        node.setTextContent("" + newVal);
                    }
                }

            }   // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            System.out.println("Done");
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    /* saves hacks
     * 
     * pre: save slot and hack
     * post: adds the hack in use
     */
    public void saveHacks(int s, String hack) {
        slotNum = s;
        pHacksBought = hack;

        try {
            String filepath = "C:\\SavedGames.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            // Get the root element
            Node savedGames = doc.getFirstChild();

            // Get the staff element , it may not working if tag has spaces, or
            // whatever weird characters in front...it's better to use
            // getElementsByTagName() to get it directly.
            // Node staff = company.getFirstChild();

            // Get the staff element by tag name directly
            Node slot = doc.getElementsByTagName("Slot").item(slotNum);

            NodeList list = slot.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                // get the salary element, and update the value
                if ("HacksBought".equals(node.getNodeName())) {
                    node.setTextContent(readSaves.getBoughtHacks(s) + " " + pHacksBought);
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            System.out.println("Bought Hacks Saved");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    /* saves hacks
     * 
     * pre: save slot and hack
     * post: adds the hack in use
     */
    public void savePower(int s, String hack) {
        slotNum = s;
        pHacksPower = hack;

        try {
            String filepath = "C:\\SavedGames.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            // Get the root element
            Node savedGames = doc.getFirstChild();

            // Get the staff element , it may not working if tag has spaces, or
            // whatever weird characters in front...it's better to use
            // getElementsByTagName() to get it directly.
            // Node staff = company.getFirstChild();

            // Get the staff element by tag name directly
            Node slot = doc.getElementsByTagName("Slot").item(slotNum);

            NodeList list = slot.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                // get the salary element, and update the value
                if ("HacksPower".equals(node.getNodeName())) {
                    node.setTextContent("" + pHacksPower);
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            System.out.println("Power Hack Saved");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    /* saves hacks
     * 
     * pre: save slot and hack
     * post: adds the hack in use
     */
    public void saveDefense(int s, String hack) {
        slotNum = s;
        pHacksDefense = hack;

        try {
            String filepath = "C:\\SavedGames.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            // Get the root element
            Node savedGames = doc.getFirstChild();

            // Get the staff element , it may not working if tag has spaces, or
            // whatever weird characters in front...it's better to use
            // getElementsByTagName() to get it directly.
            // Node staff = company.getFirstChild();

            // Get the staff element by tag name directly
            Node slot = doc.getElementsByTagName("Slot").item(slotNum);

            NodeList list = slot.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                // get the salary element, and update the value
                if ("HacksDefense".equals(node.getNodeName())) {
                    node.setTextContent("" + pHacksDefense);
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            System.out.println("Defense Hack Saved");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    /* saves hacks
     * 
     * pre: save slot and hack
     * post: adds the hack in use
     */
    public void saveUtility(int s, String hack) {
        slotNum = s;
        pHacksUtility = hack;

        try {
            String filepath = "C:\\SavedGames.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            // Get the root element
            Node savedGames = doc.getFirstChild();

            // Get the staff element , it may not working if tag has spaces, or
            // whatever weird characters in front...it's better to use
            // getElementsByTagName() to get it directly.
            // Node staff = company.getFirstChild();

            // Get the staff element by tag name directly
            Node slot = doc.getElementsByTagName("Slot").item(slotNum);

            NodeList list = slot.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                // get the salary element, and update the value
                if ("HacksUtility".equals(node.getNodeName())) {
                    node.setTextContent("" + pHacksUtility);
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            System.out.println("Utilities Hack Saved");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
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
