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
public interface Health {

    public void setHealth();

    public float getHealth();

    public void loseHealth(float damage, boolean boss);
}
