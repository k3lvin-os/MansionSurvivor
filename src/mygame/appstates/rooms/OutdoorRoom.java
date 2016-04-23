/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.rooms;

import mygame.appstates.util.RoomAppState;
import mygame.appstates.rooms.EntranceRoom;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import mygame.appstates.rooms.CorridorRoom;
import mygame.controls.DoorControl;
import mygame.javaclasses.Constants;
import mygame.enumerations.Direction;
import mygame.javaclasses.Door;
import mygame.javaclasses.DoorOrientation;
import mygame.enumerations.RayCastFace;
import mygame.javaclasses.Constants.Doors;

/**
 *
 * @author GAMEOVER
 */
public class OutdoorRoom extends RoomAppState {

    public static final Vector3f ENTRANCE_DOOR_POS = new Vector3f(18f, 0f, 0.1f);
    protected Door mansionEntranceDoor;
    protected DoorControl entranceDoorControl;
    protected Geometry floor;
    protected RigidBodyControl floorPhysics;
    EntranceRoom mansionEntranceAppState;

    public DoorControl getEntranceDoorControl() {
        return entranceDoorControl;
    }

    public OutdoorRoom() {
        super(0f, 0f, 0f, Vector3f.ZERO); // Outdoor measures
    }

    @Override
    public void OnEnabled() {
        super.OnEnabled();
        mansionEntranceDoor.setEnabled(true);
        mansionEntranceAppState.setEnabled(true);
      
    }

    @Override
    public void OnDisabled() {
        super.OnDisabled();
        mansionEntranceDoor.setEnabled(false);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.mansionEntranceAppState = stateManager.getState(EntranceRoom.class);

        floor = createGameFloor(assetManager, new Vector3f(0f, 0f, 10f));


        DoorOrientation doorOrientation = new DoorOrientation(RayCastFace.NegativeAxis, Direction.Horizontal);
        boolean doubleDoor = true;

        // Left door
        mansionEntranceDoor = new Door(constructionAssets, ENTRANCE_DOOR_POS, Direction.Horizontal, nodes.getDoorsNode(), doubleDoor);
        Geometry entranceDoorGeometry = mansionEntranceDoor.getPrototypeGeometry().getGeometry();
      
        entranceDoorControl = new DoorControl(entranceDoorGeometry, Doors.COUNTRYARD_TO_ENTRANCE,
                Doors.ENTRANCE_TO_COUNTRYARD, this, doorOrientation,nodes, inputApp);
        entranceDoorGeometry.addControl(entranceDoorControl);

        setEnabled(true);
    }
}
