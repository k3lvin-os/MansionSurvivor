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
import java.util.ArrayList;
import mygame.appstates.InputApp;
import mygame.appstates.NodesApp;
import mygame.appstates.util.RoomScenario;
import mygame.javaclasses.Constants.UserData;
import mygame.enumerations.Direction;
import mygame.enumerations.RayCastFace;
import mygame.interfaces.IObservable;
import mygame.interfaces.IObserver;
import mygame.javaclasses.DoorOrientation;
import mygame.javaclasses.Constants.ObserverPattern;
import mygame.javaclasses.ArrayListSavable;

/**
 *
 * @author GAMEOVER
 */
public class DoorControl extends AbstractControl implements IObservable {

    /**
     * Max distance to be able to enter in the door
     */
    public static final float MAX_DISTANCE = 3f;
    /**
     * Store the results of collision of the ray
     */
    private CollisionResults collisionResults;
    public static final float WALL_DISTANCE = 0.1f;
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
    private ArrayList<IObserver> observers;

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
    private void setDoorRoomAppState(RoomScenario room) {
        spatial.setUserData(UserData.ROOM_APP, room);
    }

    /**
     * Get the room that this door pertains
     */
    public RoomScenario getDoorRoomAppState() {
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
     * @param doorName current door name
     * @param symetricDoorName symetric door name - opposite room
     * @param doorRoom current door room
     * @param orientation door direction and side that are facing (raycast)
     * @param nodes game nodes to get doorsNode and playerNode
     * @param inputApp inputApp used in observer pattern logic
     *
     */
    public DoorControl(Spatial door, String doorName, String symetricDoorName, RoomScenario doorRoom,
            DoorOrientation orientation, NodesApp nodes) {
        this.spatial = door;
        this.spatial.setName(doorName);
        this.collisionResults = new CollisionResults();
        DoorOrientation doorOrientation = new DoorOrientation(orientation);
        setDoorOrienation(doorOrientation);
        setPlayerUsingDoor(false);
        setSymetricDoorName(symetricDoorName);
        setDoorRoomAppState(doorRoom);
        this.playerNode = nodes.getPlayerNode();
        rayDirection = new Vector3f();
        this.observers = new ArrayList<IObserver>();

        defineRayCast();
    }

    private void defineRayCast() {
        if (this.getDoorOrienation().getDoorDirection() == Direction.Horizontal) {

            if (this.getDoorOrienation().getDoorType() == RayCastFace.PositiveAxis) {
                rayDirection.setZ(-1f);
            } else {
                rayDirection.setZ(1f);
            }
        } else {
            if (this.getDoorOrienation().getDoorType() == RayCastFace.PositiveAxis) {
                rayDirection.setX(1f);
            } else {
                rayDirection.setX(-1f);
            }
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (enabled) {
            ray = new Ray(spatial.getLocalTranslation().add(new Vector3f(0f, -1f, 0f)), rayDirection);
            playerNode.collideWith(ray, collisionResults);
            if (collisionResults.getClosestCollision() != null) {
                if (collisionResults.getClosestCollision().getDistance() <= MAX_DISTANCE) {
                    if (!isPlayerUsingDoor()) {
                        setPlayerUsingDoor(true);
                        notifyAllObservers(ObserverPattern.NEXT_DOOR);
                    }
                }
            }

            if (isPlayerUsingDoor()) {
                if (collisionResults.getClosestCollision() == null
                        || collisionResults.getClosestCollision().getDistance() > MAX_DISTANCE) {
                    setPlayerUsingDoor(false);
                    this.notifyAllObservers(ObserverPattern.NOT_NEXT_DOOR);
                }
            }


            collisionResults.clear();
        }
    }

    public void addObserver(IObserver o) {
        this.observers.add(o);
    }

    public void removeObserver(IObserver o) {
        this.observers.remove(o);
    }

    public void notifyAllObservers(String update) {
        for (IObserver o : this.observers) {
            o.subjectUpdate(update);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
