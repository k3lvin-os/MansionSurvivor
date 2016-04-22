/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.rooms;

import mygame.appstates.util.RoomAppState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import mygame.controls.DoorControl;
import mygame.enumerations.Direction;
import mygame.enumerations.RayCastFace;
import mygame.javaclasses.Constants.Doors;
import mygame.javaclasses.Door;
import mygame.javaclasses.DoorOrientation;

/**
 *
 * @author GAMEOVER
 */
public class MansionEntranceRoom extends RoomAppState {

    public static final float DEFAULT_WIDTH = 36f;
    public static final float DEFAULT_HEIGHT = 20f;
    public static final float DEFAULT_SIZE = 18f;
    public static final Vector3f COUNTRYARD_DOOR_POS = new Vector3f(18f, 0f, -0.1f);
    public static final Vector3f DEFAULT_POSITION = Vector3f.ZERO;
    public static final Vector3f CORRIDOR_DOOR_POS =  new Vector3f(18f, 0f,  -DEFAULT_SIZE + DoorControl.WALL_DISTANCE);
    protected Door countryardDoor;
    protected Door corridorDoor;

    public MansionEntranceRoom() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_SIZE, DEFAULT_POSITION);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        boolean doubleDoor = true;
        
        // Corridor Door
        DoorOrientation corridorDoorOrientation = new DoorOrientation(RayCastFace.NegativeAxis, Direction.Horizontal);
        this.corridorDoor = new Door(constructionAssets, CORRIDOR_DOOR_POS, corridorDoorOrientation.getDoorDirection(),
                nodes.getDoorsNode(), doubleDoor);
        Geometry corridorDoorGeometry = this.corridorDoor.getPrototypeGeometry().getGeometry();
        DoorControl corridorDoorControl = new DoorControl(corridorDoorGeometry, Doors.ENTRANCE_TO_CORRIDOR, Doors.CORRIDOR_TO_ENTRANCE,
               this,corridorDoorOrientation, nodes,inputApp);
        corridorDoorGeometry.addControl(corridorDoorControl);
        
        // Contryard Door
        DoorOrientation countryardDoorOrientation = new DoorOrientation(RayCastFace.PositiveAxis, Direction.Horizontal);
        countryardDoor = new Door(constructionAssets, COUNTRYARD_DOOR_POS,
                countryardDoorOrientation.getDoorDirection(), nodes.getDoorsNode(), doubleDoor);
        Geometry countryardDoorGeometry = countryardDoor.getPrototypeGeometry().getGeometry();
        DoorControl countryardDoorControl = new DoorControl(countryardDoorGeometry, Doors.ENTRANCE_TO_COUNTRYARD,
                Doors.COUNTRYARD_TO_ENTRANCE, this, countryardDoorOrientation, nodes, inputApp);
        countryardDoorGeometry.addControl(countryardDoorControl);
        

        setEnabled(true);
    }

    @Override
    public void OnDisabled() {
        super.OnDisabled();
        countryardDoor.setEnabled(false);
        this.corridorDoor.setEnabled(false);

    }

    @Override
    public void OnEnabled() {
        super.OnEnabled();
        countryardDoor.setEnabled(true);
        this.corridorDoor.setEnabled(true);
    }
}
