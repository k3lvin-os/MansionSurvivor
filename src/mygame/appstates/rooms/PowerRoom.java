/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.rooms;

import mygame.appstates.util.RoomScenario;
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
public class PowerRoom extends RoomScenario {

    public static final float DEFAULT_WIDTH = 36f;
    public static final float DEFAULT_HEIGHT = 20f;
    public static final float DEFAULT_SIZE = 18f;
    public static final Vector3f DEFAULT_POSITION = MaintenanceRoom.DEFAULT_POSITION
            .add(0f, 0f, -MaintenanceRoom.DEFAULT_SIZE);
    public static final Vector3f DEFAULT_PLAYER_POSITION = Vector3f.ZERO;
    private static final Vector3f CORRIDOR_DOOR_POS = CorridorRoom.POWER_DOOR_POS.add(new Vector3f(DoorControl.WALL_DISTANCE * 2f, 0f, 0f));
    protected Door corridorDoor;

    public PowerRoom() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_SIZE, DEFAULT_POSITION);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
                // Corridor Door
        DoorOrientation corridorDoorOrientation = new DoorOrientation(RayCastFace.PositiveAxis,
                Direction.Vertical);
        this.corridorDoor = new Door(constructionAssets, CORRIDOR_DOOR_POS,
                corridorDoorOrientation.getDoorDirection(), nodes.getDoorsNode());
        Geometry corridorDoorGeometry = this.corridorDoor.getPrototypeGeometry().getGeometry();
        DoorControl corridorDoorControl = new DoorControl(corridorDoorGeometry,
                Doors.POWER_TO_CORRIDOR, Doors.CORRIDOR_TO_POWER, this,
                corridorDoorOrientation, nodes, inputApp);
        corridorDoorGeometry.addControl(corridorDoorControl);
        
        setEnabled(false);
    }

    @Override
    public void OnEnabled() {
        super.OnEnabled();
        corridorDoor.setEnabled(true);
    }

    @Override
    public void OnDisabled() {
        super.OnDisabled();
        corridorDoor.setEnabled(false);
    }
}
