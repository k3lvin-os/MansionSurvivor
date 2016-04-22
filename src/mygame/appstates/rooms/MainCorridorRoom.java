/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.rooms;

import mygame.appstates.util.RoomAppState;
import mygame.appstates.rooms.MansionEntranceRoom;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import mygame.controls.DoorControl;
import mygame.enumerations.Direction;
import mygame.enumerations.RayCastFace;
import mygame.javaclasses.Constants;
import mygame.javaclasses.Constants.Doors;
import mygame.javaclasses.Door;
import mygame.javaclasses.DoorOrientation;

/**
 *
 * @author GAMEOVER
 */
public class MainCorridorRoom extends RoomAppState {

    public static final float DEFAULT_WIDTH = 18f;
    public static final float DEFAULT_HEIGHT = 20f;
    public static final float DEFAULT_SIZE = 36f;
    public static final Vector3f DEFAULT_LOCATION = MansionEntranceRoom.DEFAULT_POSITION
            .add(new Vector3f(9f, 0f, -DEFAULT_SIZE / 2f));
    public static final Vector3f ENTRANCE_DOOR_POS = MansionEntranceRoom.CORRIDOR_DOOR_POS.add
            (new Vector3f(0f, 0f, DoorControl.WALL_DISTANCE * -2f));
    protected Door officeDoor;
    protected Door entranceDoor;
    public static final Vector3f OFFICE_DOOR_POS = DEFAULT_LOCATION.add(new Vector3f(0f, 0f, 4.5f));

    public MainCorridorRoom() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_SIZE, DEFAULT_LOCATION);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        boolean doubleDoor = true;
        
        // Entrance door
        DoorOrientation entranceDoorOrientation = new DoorOrientation(RayCastFace.PositiveAxis, Direction.Horizontal);
        this.entranceDoor = new Door(constructionAssets, ENTRANCE_DOOR_POS, 
                entranceDoorOrientation.getDoorDirection(),nodes.getDoorsNode(), doubleDoor);
        Geometry entranceDoorGeometry = this.entranceDoor.getPrototypeGeometry().getGeometry();
        DoorControl entranceDoorControl = new DoorControl(entranceDoorGeometry, Doors.CORRIDOR_TO_ENTRANCE, Doors.ENTRANCE_TO_CORRIDOR,
                this, entranceDoorOrientation, nodes, inputApp);
        entranceDoorGeometry.addControl(entranceDoorControl);
        
        // Office Door
        DoorOrientation officeDoorOrientation = new DoorOrientation(RayCastFace.PositiveAxis, Direction.Vertical);
        this.officeDoor = new Door(constructionAssets, OFFICE_DOOR_POS, 
                officeDoorOrientation.getDoorDirection(),nodes.getDoorsNode());
        Geometry officeDoorGeometry = officeDoor.getPrototypeGeometry().getGeometry();
        DoorControl officeDoorControl = new DoorControl(officeDoorGeometry, Doors.CORRIDOR_TO_OFFICE, Doors.OFFICE_TO_CORRIDOR, this,
                officeDoorOrientation, nodes, inputApp);
        officeDoorGeometry.addControl(officeDoorControl);
        
        setEnabled(true);
    }

    @Override
    public void OnDisabled() {
        super.OnDisabled();
        officeDoor.setEnabled(false);
        entranceDoor.setEnabled(false);
    }

    @Override
    public void OnEnabled() {
        super.OnEnabled();
        officeDoor.setEnabled(true);
        entranceDoor.setEnabled(true);
    }
}
