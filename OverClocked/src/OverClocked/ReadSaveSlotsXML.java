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
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
//This file was modified off the tutorial file from http://www.mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/ Credits to them for making the original file and teaching how to use XML in Java

public class ReadSaveSlotsXML {

    private String[] name;
    private String[] money;
    private String[] level;
    private String[] slot;
    private String[] stage;
    private String[] hacksBought;
    private String[] hacksPower;
    private String[] hacksDefense;
    private String[] hacksUtility;
    private String slotsLeft, nextSlot;

    /* constructor
     * 
     * pre: nothing
     * post: makes the arrays
     */
    public ReadSaveSlotsXML() {
        name = new String[3];
        money = new String[3];
        level = new String[3];
        slot = new String[3];
        stage = new String[3];
        hacksPower = new String[3];
        hacksDefense = new String[3];
        hacksUtility = new String[3];
        hacksBought = new String[3];
        readData();

    }
    
    /* get hacks
     * 
     * pre: save slot
     * post: get's the hacks
     */
    public String getBoughtHacks(int slot){
        return hacksBought[slot];
    }
    
    /* get hacks
     * 
     * pre: save slot
     * post: get's the hacks
     */
    public String getPowerHacks(int slot){
        return hacksPower[slot];
    }
    
    /* get hacks
     * 
     * pre: save slot
     * post: get's the hacks
     */
    public String getDefenseHacks(int slot){
        return hacksDefense[slot];
    }
    
    /* get hacks
     * 
     * pre: save slot
     * post: get's the hacks
     */
    public String getUtilityHacks(int slot){
        return hacksUtility[slot];
    }

    /* get money
     * 
     * pre: save slot
     * post: get's the moolah
     */
    public int getMoney(int slot) {
        return Integer.parseInt(money[slot]);
    }

    /* get name
     * 
     * pre: save slot
     * post: get's the name
     */
    public String getName(int slot) {
        return name[slot];
    }

    
    /* get level
     * 
     * pre: save slot
     * post: get's the leve
     */
    public int getCurrentLevel(int slot) {
        return Integer.parseInt(level[slot]);
    }

    /* get stage
     * 
     * pre: save slot
     * post: get's the stage
     */
    public int getCurrentStage(int slot) {
        return Integer.parseInt(stage[slot]);
    }

    /* get slots left
     * 
     * pre: save slot
     * post: get's the amount of save slots available
     */
    public int getSlotsLeft() {
        return Integer.parseInt(slotsLeft);
    }

    /* get next availabe slot
     * 
     * pre: save slot
     * post: get's the next slot
     */
    public int getNextSlot() {
        return Integer.parseInt(nextSlot);
    }

    /* read the xml
     * 
     * pre: save slot
     * post: get's the information for arrays
     */
    public void readData() {

        try {

            File fXmlFile = new File("C:\\SavedGames.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList1 = doc.getElementsByTagName("SlotsLeft");
            for (int temp = 0; temp < nList1.getLength(); temp++) {

                Node nNode = nList1.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    slotsLeft = getTagValue("slots", eElement);
                }
            }

            NodeList nList3 = doc.getElementsByTagName("NextSlot");
            for (int temp = 0; temp < nList3.getLength(); temp++) {

                Node nNode = nList3.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    nextSlot = getTagValue("slot", eElement);
                }
            }
            NodeList nList2 = doc.getElementsByTagName("Slot");
            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList2.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    slot[temp] = (eElement.getAttribute("id"));
                    name[temp] = (getTagValue("Name", eElement));
                    money[temp] = (getTagValue("Money", eElement));
                    level[temp] = (getTagValue("CurrentLevel", eElement));
                    stage[temp] = (getTagValue("CurrentStage", eElement));
                    
                    hacksBought[temp] = (getTagValue("HacksBought", eElement));
                    hacksPower[temp] = (getTagValue("HacksPower", eElement));
                    hacksDefense[temp] = (getTagValue("HacksDefense", eElement));
                    hacksUtility[temp] = (getTagValue("HacksUtility", eElement));
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
