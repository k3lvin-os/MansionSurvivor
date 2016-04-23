/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import mygame.appstates.util.RoomAppState;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.controls.DoorControl;
import mygame.controls.PlayerControl;
import mygame.enumerations.Direction;
import mygame.enumerations.RayCastFace;
import mygame.interfaces.IObserver;
import mygame.javaclasses.Constants;
import mygame.javaclasses.Constants.ObserverPattern;
import mygame.javaclasses.Constants.UserData;
import mygame.javaclasses.DoorOrientation;

/**
 *
 * @author GAMEOVER
 */
public class ChangeRoomAppState extends AbstractAppState implements IObserver {

    private Node playerNode;
    private Node rootNode;
    private Node doorsNode;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        NodesAppState nodesAppState = stateManager.getState(NodesAppState.class);
        this.rootNode = nodesAppState.getRootNode();
        this.playerNode = nodesAppState.getPlayerNode();
        this.doorsNode = nodesAppState.getDoorsNode();
    }

    /**
     * Change the room based in the door that player is using
     */
    public void changeRoom() {
            DoorControl playerUsingDoor = getDoorPlayerIsUsing();
            playerUsingDoor.setPlayerUsingDoor(false);
            RoomAppState currentRoom = playerUsingDoor.getDoorRoomAppState();
            DoorControl symetricDoorControl = getDoorControlWithName(playerUsingDoor.getSymetricDoorName());
            RoomAppState nextRoom = symetricDoorControl.getDoorRoomAppState();
            currentRoom.setEnabled(false);
            rootNode.detachChild(playerNode);
            nextRoom.setEnabled(true);
            Vector3f playerPosition = symetricDoorControl.getSpatial().getLocalTranslation()
                    .add(symetricDoorControl.getRayDirection());
            playerNode.getChild(UserData.PLAYER).getControl(PlayerControl.class)
                    .setPosition(playerPosition);
            rootNode.attachChild(playerNode);
    }

    public DoorControl getDoorControlWithName(String name) {
        
        for(Spatial child: this.doorsNode.getChildren()){
            if(child.getName().equals(name)){
                return child.getControl(DoorControl.class);
            }
        }
        
        
        for (Spatial child : this.rootNode.getChildren()) {
            if (child.getName().equals(name)) {
                return child.getControl(DoorControl.class);
            }
        }
        
        return null;
      
    }

    private DoorControl getDoorPlayerIsUsing() {
        for (Spatial child : this.rootNode.getChildren()) {
            if (child.getControl(DoorControl.class) != null) {
                if (child.getControl(DoorControl.class).isPlayerUsingDoor()) {
                    return child.getControl(DoorControl.class);
                }
            }
        }
        return null;
    }

    @Override
    public void update(float tpf) {
    }

    public void subjectUpdate(String update) {
        if(update.equals(ObserverPattern.ENTERED_DOOR)){
            changeRoom();
        }
    }
}
