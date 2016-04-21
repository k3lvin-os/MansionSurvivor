/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.controls;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mygame.appstates.InputAppState;
import mygame.appstates.NodesAppState;
import mygame.appstates.util.RoomAppState;
import mygame.javaclasses.Constants.UserData;
import mygame.enumerations.Direction;
import mygame.enumerations.DoorType;
import mygame.javaclasses.DoorOrientation;
import mygame.javaclasses.Constants.PlayerOptions;

/**
 *
 * @author GAMEOVER
 */
public class DoorControl extends AbstractControl {

    /**
     * Max distance to be able to enter in the door
     */
    public static final float MAX_DISTANCE = 3f;
    /**
     * Store the results of collision of the ray
     */
    private CollisionResults collisionResults;
    /**
     * Tells at what directino the ray will go
     */
    private Vector3f rayDirection;
    /**
     * Ray that will be dispared in order to detect objects next at door
     */
    private Ray ray;
    /**
     * The player node is the only node that contains things that can enter in
     * the room
     */
    private Node playerNode;

    /**
     * Set if the player is using this door
     */
    public void setPlayerUsingDoor(boolean playerUsing) {
        spatial.setUserData(UserData.PLAYER_USING_DOOR, playerUsing);
    }

    /**
     * Points to the direction that the door is looking for the player
     */
    public Vector3f getRayDirection() {
        return rayDirection;
    }

    /**
     * Return if the player is using this door or not *
     */
    public boolean isPlayerUsingDoor() {
        return spatial.getUserData(UserData.PLAYER_USING_DOOR);
    }

    /**
     * Set the room that this door pertains*
     */
    private void setDoorRoomAppState(RoomAppState room) {
        spatial.setUserData(UserData.ROOM_APP, room);
    }

    /**
     * Get the room that this door pertains
     */
    public RoomAppState getDoorRoomAppState() {
        return spatial.getUserData(UserData.ROOM_APP);
    }

    private void setDoorOrienation(DoorOrientation doorOrientation) {
        spatial.setUserData(UserData.DOOR_ORIENTATION, doorOrientation);
    }

    public DoorOrientation getDoorOrienation() {
        return spatial.getUserData(UserData.DOOR_ORIENTATION);
    }


    private void setSymetricDoorName(String name) {
        spatial.setUserData(UserData.SYMETRIC_DOOR_NAME, name);
    }

    public String getSymetricDoorName() {
        return spatial.getUserData(UserData.SYMETRIC_DOOR_NAME);
    }

    /**
     * Create a door control
     *
     * @param door spatial that the control will be added
     * @param doorRoom the room of the current door
     * @param orientation gives the orientation for where the ray will be
     * launched
     * @param playerNode receive a reference of the player node in order to
     * check the player pos
     *
     */
    public DoorControl(Geometry door, String doorName, String symetricDoorName, RoomAppState doorRoom,
            DoorOrientation orientation, NodesAppState nodes, InputAppState inputApp) {
        this.spatial = door;
        this.spatial.setName(doorName);
        collisionResults = new CollisionResults();
        DoorOrientation doorOrientation = new DoorOrientation(orientation);
        setDoorOrienation(doorOrientation);
        setPlayerUsingDoor(false);
        setSymetricDoorName(symetricDoorName);
        setDoorRoomAppState(doorRoom);
        this.playerNode = nodes.getPlayerNode();
        rayDirection = new Vector3f();

        if (doorOrientation.getDoorDirection() == Direction.HORIZONTAL) {

            if (doorOrientation.getDoorType() == DoorType.INDOOR) {
                rayDirection.setZ(-1f);
            } else {
                rayDirection.setZ(1f);
            }
        } else {
            if (doorOrientation.getDoorType() == DoorType.INDOOR) {
                rayDirection.setX(1f);
            } else {
                rayDirection.setZ(-1f);
            }
        }

    }
    Integer test = null;

    @Override
    protected void controlUpdate(float tpf) {
        if (enabled) {
            ray = new Ray(spatial.getLocalTranslation().add(new Vector3f(0f, -1f, 0f)), rayDirection);
            playerNode.collideWith(ray, collisionResults);
            if (collisionResults.getClosestCollision() != null) {
                if (collisionResults.getClosestCollision().getDistance() <= MAX_DISTANCE) {
                    if (!isPlayerUsingDoor()) {
                        setPlayerUsingDoor(true);
                        playerNode.getChild(UserData.PLAYER).getControl(PlayerControl.class).getListOfPlayerOptions()
                                .add(PlayerOptions.OPEN_DOOR);
                    }
                }
            }

            if (isPlayerUsingDoor()) {
                if (collisionResults.getClosestCollision() == null
                        || collisionResults.getClosestCollision().getDistance() > MAX_DISTANCE) {
                    setPlayerUsingDoor(false);
                    playerNode.getChild(UserData.PLAYER).getControl(PlayerControl.class).getListOfPlayerOptions()
                            .remove(PlayerOptions.OPEN_DOOR);
                }
            }


            collisionResults.clear();
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
