/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

public class HacksInfo {

    private ReadHacksInfoXML readHacks = new ReadHacksInfoXML();
    public String dHack, uHack, pHack, paHack;

    /* constructor
     * 
     * pre: state id, container
     * post: get pictures
     */
    public HacksInfo() {
        super();
    }

    /* get's the descriptions for hacks
     * 
     * pre: id of hack
     * post: hack name, description, and cost
     */
    public String getDefense(int id) {
        switch (id) {
            case 0:
                dHack = readHacks.getHack(id) + "\n" + readHacks.getDescription(id) + "\nCost:" + readHacks.getCost(id);
                break;
            case 1:
                dHack = readHacks.getHack(id) + "\n" + readHacks.getDescription(id) + "\nCost:" + readHacks.getCost(id);
                break;
            case 2:
                dHack = readHacks.getHack(id) + "\n" + readHacks.getDescription(id) + "\nCost:" + readHacks.getCost(id);
                break;
        }
        return dHack;
    }

    /* get's the descriptions for hacks
     * 
     * pre: id of hack
     * post: hack name, description, and cost
     */
    public String getPower(int id) {
        switch (id) {
            case 0:
                pHack = readHacks.getHack(id + 3) + "\n" + readHacks.getDescription(id + 3) + "\nCost:" + readHacks.getCost(id + 3);
                break;
            case 1:
                pHack = readHacks.getHack(id + 3) + "\n" + readHacks.getDescription(id + 3) + "\nCost:" + readHacks.getCost(id + 3);
                break;
            case 2:
                pHack = readHacks.getHack(id + 3) + "\n" + readHacks.getDescription(id + 3) + "\nCost:" + readHacks.getCost(id + 3);
                break;
            case 3:
                pHack = readHacks.getHack(id + 3) + "\n" + readHacks.getDescription(id + 3) + "\nCost:" + readHacks.getCost(id + 3);
                break;
        }
        return pHack;
    }

    /* get's the descriptions for hacks
     * 
     * pre: id of hack
     * post: hack name, description, and cost
     */
    public String getUtility(int id) {
        switch (id) {
            case 0:
                uHack = readHacks.getHack(id + 7) + "\n" + readHacks.getDescription(id + 7) + "\nCost:" + readHacks.getCost(id + 7);
                break;
            case 1:
                uHack = readHacks.getHack(id + 7) + "\n" + readHacks.getDescription(id + 7) + "\nCost:" + readHacks.getCost(id + 7);
                break;
            case 2:
                uHack = readHacks.getHack(id + 7) + "\n" + readHacks.getDescription(id + 7) + "\nCost:" + readHacks.getCost(id + 7);
                break;
            case 3:
                uHack = readHacks.getHack(id + 7) + "\n" + readHacks.getDescription(id + 7) + "\nCost:" + readHacks.getCost(id + 7);
                break;
        }
        return uHack;
    }

    /* get's the descriptions for hacks
     * 
     * pre: id of hack
     * post: hack name, description, and cost
     */
    public String getPassive(int id) {
        switch (id) {
            case 0:
                paHack = readHacks.getHack(id + 11) + "\n" + readHacks.getDescription(id + 11) + "\nCost:" + readHacks.getCost(id + 11);
                break;
            case 1:
                paHack = readHacks.getHack(id + 11) + "\n" + readHacks.getDescription(id + 11) + "\nCost:" + readHacks.getCost(id + 11);
                break;
            case 2:
                paHack = readHacks.getHack(id + 11) + "\n" + readHacks.getDescription(id + 11) + "\nCost:" + readHacks.getCost(id + 11);
                break;
            case 3:
                paHack = readHacks.getHack(id + 11) + "\n" + readHacks.getDescription(id + 11) + "\nCost:" + readHacks.getCost(id + 11);
                break;
            case 4:
                paHack = readHacks.getHack(id + 11) + "\n" + readHacks.getDescription(id + 11) + "\nCost:" + readHacks.getCost(id + 11);
                break;
            case 5:
                paHack = readHacks.getHack(id + 11) + "\n" + readHacks.getDescription(id + 11) + "\nCost:" + readHacks.getCost(id + 11);
                break;
        }
        return paHack;
    }
}
