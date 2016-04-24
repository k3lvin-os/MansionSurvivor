/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.rooms;

import mygame.appstates.util.RoomScenario;
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
import mygame.javaclasses.Constants.UserData;
import mygame.javaclasses.Floor;

/**
 *
 * @author GAMEOVER
 */
public class OutdoorRoom extends RoomScenario {

    public static final float WIDTH = 36f;
    public static final float HEIGHT = 20f;
    public static final float SIZE = 18f;
    public static final Vector3f DEFAULT_POSITION = EntranceRoom.DEFAULT_POSITION
            .add(0f, 0f, SIZE);
    public static final Vector3f ENTRANCE_DOOR_POS = new Vector3f(18f, 0f, 0.1f);
    protected Door entranceDoor;
    private EntranceRoom entranceAppState;

    public OutdoorRoom() {
        super(WIDTH, HEIGHT, SIZE, DEFAULT_POSITION); // Outdoor measures
    }

    @Override
    public void OnEnabled() {
        // Note that i'm not calling the super class in order to not show walls
        entranceDoor.setEnabled(true);
        entranceAppState.setEnabled(true); 
        Floor floor = room.getNode().getUserData(UserData.FLOOR);
        floor.setEnabled(true);


    }

    @Override
    public void OnDisabled() {
        entranceDoor.setEnabled(false);
        Floor floor = room.getNode().getUserData(UserData.FLOOR);
        floor.setEnabled(false);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.entranceAppState = stateManager.getState(EntranceRoom.class);



        DoorOrientation doorOrientation = new DoorOrientation(RayCastFace.NegativeAxis, Direction.Horizontal);
        boolean doubleDoor = true;

        // Left door
        entranceDoor = new Door(constructionAssets, ENTRANCE_DOOR_POS, Direction.Horizontal, nodes.getDoorsNode(), doubleDoor);
        Geometry entranceDoorGeometry = entranceDoor.getPrototypeGeometry().getGeometry();

        DoorControl entranceDoorControl = new DoorControl(entranceDoorGeometry, Doors.COUNTRYARD_TO_ENTRANCE,
                Doors.ENTRANCE_TO_COUNTRYARD, this, doorOrientation, nodes, inputApp);
        entranceDoorGeometry.addControl(entranceDoorControl);

        setEnabled(true);
    }
}
