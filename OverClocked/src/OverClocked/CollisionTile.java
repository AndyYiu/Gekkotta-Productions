/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.entity.Entity;

/** Author: Stanley Fung
 * Date:
 * Teacher:
 * Description:
 * 
 */
public class CollisionTile extends Entity {

    private float mapX, mapY;
    public final String TYPE = "CollisionTile";

    /*constructor
     *
     *pre: get's the coordinates of the tile
     *post:set the collision tile made in "Tiled" to be a collision tile in the game
     */
    public CollisionTile(float x, float y) {
	super(x, y);
	mapX = x;
	mapY = y;
	setHitBox(0, 0, 10, 10);
	addType(TYPE);
    }
}
