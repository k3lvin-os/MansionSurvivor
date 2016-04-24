/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.javaclasses;

import mygame.enumerations.Direction;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import mygame.appstates.util.ScenarioApp;
import mygame.interfaces.IEnable;
import mygame.javaclasses.Constants.UserData;

/**
 *
 * @author GAMEOVER
 */
public class Room implements IEnable {

    private Wall bottomWall, topWall, leftWall, rightWall;
    private Floor floor;
    private Node room;
    private boolean enabled;

    public Node getNode() {
        return room;
    }

    public Room(ConstructionAssets constructionAssets,
            float width, float height, float size, Vector3f leftExtreme) {

        bottomWall = new Wall(constructionAssets, width, height, leftExtreme,
                Direction.Horizontal);
        topWall = new Wall(constructionAssets, width, height,
                leftExtreme.add(new Vector3f(0f, 0f, -size)), Direction.Horizontal);
        leftWall = new Wall(constructionAssets, size, height, leftExtreme, Direction.Vertical);
        rightWall = new Wall(constructionAssets, size, height,
                leftExtreme.add(new Vector3f(width, 0f, 0f)),
                Direction.Vertical);
        floor = new Floor(constructionAssets, width, size, leftExtreme);


        bottomWall.getGeometry().setName(UserData.BOTTOM_WALL);
        topWall.getGeometry().setName(UserData.TOP_WALL);
        leftWall.getGeometry().setName(UserData.LEFT_WALL);
        rightWall.getGeometry().setName(UserData.RIGHT_WALL);
        floor.getGeometry().setName(UserData.FLOOR);

        room = new Node();
        room.setUserData(UserData.BOTTOM_WALL, bottomWall);
        room.setUserData(UserData.TOP_WALL, topWall);
        room.setUserData(UserData.LEFT_WALL, leftWall);
        room.setUserData(UserData.RIGHT_WALL, rightWall);
        room.setUserData(UserData.FLOOR, floor);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            bottomWall.setEnabled(true);
            topWall.setEnabled(true);
            leftWall.setEnabled(true);
            rightWall.setEnabled(true);
            floor.setEnabled(true);
        } else {
            bottomWall.setEnabled(false);
            topWall.setEnabled(false);
            leftWall.setEnabled(false);
            rightWall.setEnabled(false);
            floor.setEnabled(false);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }
}
