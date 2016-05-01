/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import mygame.appstates.util.RoomScenario;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;
import mygame.controls.DoorControl;
import mygame.controls.PlayerControl;
import mygame.interfaces.IObserver;
import mygame.javaclasses.Constants.ObserverPattern;
import mygame.javaclasses.Constants.UserData;

/**
 *
 */
public class ChangeRoomApp extends AbstractAppState implements IObserver {

    public static final float OFFSET_NEW_DOOR = 2f;
    private static Node playerNode;
    private static Node rootNode;
    private static Node doorsNode;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        NodesApp nodesAppState = stateManager.getState(NodesApp.class);
        rootNode = nodesAppState.getRootNode();
        playerNode = nodesAppState.getPlayerNode();
        doorsNode = nodesAppState.getDoorsNode();
    }

    /**
     * Change the room based in the door that player is using
     */
    private void changeRoom() {
            DoorControl playerUsingDoor = getDoorPlayerIsUsing();
            playerUsingDoor.setPlayerUsingDoor(false);
            RoomScenario currentRoom = playerUsingDoor.getDoorRoomAppState();
            DoorControl symetricDoorControl = getDoorControlWithName(playerUsingDoor.getSymetricDoorName());
            RoomScenario nextRoom = symetricDoorControl.getDoorRoomAppState();
            currentRoom.setEnabled(false);
            rootNode.detachChild(playerNode);
            nextRoom.setEnabled(true);
            Vector3f playerPosition = symetricDoorControl.getSpatial().getLocalTranslation()
                    .add(symetricDoorControl.getRayDirection().mult(OFFSET_NEW_DOOR));
            playerNode.getChild(UserData.PLAYER).getControl(PlayerControl.class)
                    .setPosition(playerPosition);
            rootNode.attachChild(playerNode);
    }

    private DoorControl getDoorControlWithName(String name) {
        
        for(Spatial child: doorsNode.getChildren()){
            if(child.getName().equals(name)){
                return child.getControl(DoorControl.class);
            }
        }
        
        
        for (Spatial child : rootNode.getChildren()) {
            if (child.getName().equals(name)) {
                return child.getControl(DoorControl.class);
            }
        }
        
        return null;
      
    }

    public static DoorControl getDoorPlayerIsUsing() {
        for (Spatial child : rootNode.getChildren()) {
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
