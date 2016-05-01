/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.javaclasses;

import com.jme3.bullet.BulletAppState;
import mygame.enumerations.Direction;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.controls.DoorControl;
import mygame.interfaces.IEnable;

/**
 *
 * @author GAMEOVER
 */
public class Door implements IEnable {

    private Wall prototypeGeometry;
    private boolean enabled;
    private static Node doorsNode;
    private static BulletAppState bulletAppState;
    private static Node rootNode;

    public Wall getPrototypeGeometry() {
        return prototypeGeometry;
    }
    public static final float DEFAULT_DOOR_WIDTH = 2.5F;
    public static final float DEFAULT_DOOR_HEIGHT = 5f;

    public Door(ConstructionAssets constructionAssets, Vector3f position, Direction dir, Node doorsNode) {
        Door.doorsNode = doorsNode;
        bulletAppState = constructionAssets.getBulletAppState();
        rootNode = constructionAssets.getRootNode();
        prototypeGeometry = new Wall(constructionAssets, DEFAULT_DOOR_WIDTH, DEFAULT_DOOR_HEIGHT, position, dir);
        prototypeGeometry.getGeometry().getMaterial().setColor("Color", ColorRGBA.Brown);
    }

    public Door(ConstructionAssets constructionAssets, Vector3f position, Direction dir, Node doorsNode, boolean doubleDoors) {
        Door.doorsNode = doorsNode;
        bulletAppState = constructionAssets.getBulletAppState();
        rootNode = constructionAssets.getRootNode();
        float customWidth;
        if (doubleDoors) {
            customWidth = DEFAULT_DOOR_WIDTH * 2f;
        } else {
            customWidth = DEFAULT_DOOR_WIDTH;
        }
        prototypeGeometry = new Wall(constructionAssets, customWidth, DEFAULT_DOOR_HEIGHT, position, dir);
        prototypeGeometry.getGeometry().getMaterial().setColor("Color", ColorRGBA.Brown);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            rootNode.attachChild(prototypeGeometry.getGeometry());
            bulletAppState.getPhysicsSpace().add(prototypeGeometry.getPhysics());
            if (getPrototypeGeometry().getGeometry().getControl(DoorControl.class) != null) {
                getPrototypeGeometry().getGeometry().getControl(DoorControl.class).setEnabled(true);
            }
        } else {
            doorsNode.attachChild(prototypeGeometry.getGeometry());
            bulletAppState.getPhysicsSpace().remove(prototypeGeometry.getPhysics());
            if (getPrototypeGeometry().getGeometry().getControl(DoorControl.class) != null) {
                getPrototypeGeometry().getGeometry().getControl(DoorControl.class).setEnabled(false);
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }
}
