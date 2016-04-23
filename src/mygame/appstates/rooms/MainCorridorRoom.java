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
            .add(new Vector3f(9f, 0f, -18F));
    public static final Vector3f ENTRANCE_DOOR_POS = DEFAULT_LOCATION.add
            (new Vector3f(DEFAULT_WIDTH / 2f, 0f, -DoorControl.WALL_DISTANCE));
    public static final Vector3f OFFICE_DOOR_POS = DEFAULT_LOCATION.add(new Vector3f(DoorControl.WALL_DISTANCE,
            0f,-DEFAULT_SIZE * 0.25f));
    public static final Vector3f MAINTENANCE_DOOR_POS = DEFAULT_LOCATION.add(
            new Vector3f(DEFAULT_WIDTH - DoorControl.WALL_DISTANCE , 0f, -DEFAULT_SIZE * 0.25f));
    public static final Vector3f CAGES_DOOR_POS = DEFAULT_LOCATION.add(DoorControl.WALL_DISTANCE,
            0f, -DEFAULT_SIZE * 0.75f);
    protected Door cagesDoor;
    protected Door maintenanceDoor;
    protected Door officeDoor;
    protected Door entranceDoor;

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
        
        // Maintenance Door
        DoorOrientation maintenanceDoorOrientation = new DoorOrientation(RayCastFace.NegativeAxis, Direction.Vertical);
        this.maintenanceDoor = new Door(constructionAssets,MAINTENANCE_DOOR_POS,
                maintenanceDoorOrientation.getDoorDirection(),nodes.getDoorsNode());
        Geometry maintenanceDoorGeometry = this.maintenanceDoor.getPrototypeGeometry().getGeometry();
        DoorControl maintenanceDoorControl = new DoorControl(maintenanceDoorGeometry, Doors.CORRIDOR_TO_MAINTENANCE,
                Doors.MAINTENANCE_TO_CORRIDOR, this, maintenanceDoorOrientation, nodes, inputApp);
        maintenanceDoorGeometry.addControl(maintenanceDoorControl);
        
        // Animal Cages Door
        DoorOrientation cagesDoorOrientation = new DoorOrientation(RayCastFace.PositiveAxis, 
                Direction.Vertical);
        this.cagesDoor = new Door(constructionAssets, CAGES_DOOR_POS
                ,cagesDoorOrientation.getDoorDirection(),nodes.getDoorsNode());
        Geometry cagesDoorGeometry = this.cagesDoor.getPrototypeGeometry().getGeometry();
        DoorControl cagesDoorControl = new DoorControl(cagesDoorGeometry,Doors.CORRIDOR_TO_CAGES,
                Doors.CAGES_TO_CORRIDOR,this,cagesDoorOrientation,nodes,inputApp);
        cagesDoorGeometry.addControl(cagesDoorControl);
        
        setEnabled(false);
    }

    @Override
    public void OnDisabled() {
        super.OnDisabled();
        officeDoor.setEnabled(false);
        entranceDoor.setEnabled(false);
        maintenanceDoor.setEnabled(false);
        cagesDoor.setEnabled(false);
    }

    @Override
    public void OnEnabled() {
        super.OnEnabled();
        officeDoor.setEnabled(true);
        entranceDoor.setEnabled(true);
        maintenanceDoor.setEnabled(true);
        cagesDoor.setEnabled(true);
    }
}
