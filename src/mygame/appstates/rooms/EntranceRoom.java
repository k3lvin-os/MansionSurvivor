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
import mygame.appstates.ObserverManagerApp;
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
public class EntranceRoom extends RoomScenario {

    public static final float DEFAULT_WIDTH = 36f;
    public static final float DEFAULT_HEIGHT = 20f;
    public static final float DEFAULT_SIZE = 18f;
    public static final Vector3f OUTDOOR_DOOR_POS = new Vector3f(18f, 0f, -0.1f);
    public static final Vector3f DEFAULT_POSITION = Vector3f.ZERO;
    public static final Vector3f CORRIDOR_DOOR_POS = new Vector3f(18f, 0f, -DEFAULT_SIZE + DoorControl.WALL_DISTANCE);
    protected Door outdoorDoor;
    protected Door corridorDoor;
    protected DoorControl outdoorDoorControl;
    private boolean isWithWolfBone;

    public EntranceRoom() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_SIZE, DEFAULT_POSITION);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        isWithWolfBone = false;
        boolean doubleDoor = true;

        // Corridor Door
        DoorOrientation corridorDoorOrientation = new DoorOrientation(RayCastFace.NegativeAxis, Direction.Horizontal);
        this.corridorDoor = new Door(constructionAssets, CORRIDOR_DOOR_POS, corridorDoorOrientation.getDoorDirection(),
                nodes.getDoorsNode(), doubleDoor);
        Geometry corridorDoorGeometry = this.corridorDoor.getPrototypeGeometry().getGeometry();
        DoorControl corridorDoorControl = observerApp.createDoorControl(corridorDoorGeometry,
                Doors.ENTRANCE_TO_CORRIDOR, Doors.CORRIDOR_TO_ENTRANCE, this,
                corridorDoorOrientation, nodes);
        corridorDoorGeometry.addControl(corridorDoorControl);

        // Outdoor Door
        DoorOrientation outdoorDoorOrientation = new DoorOrientation(RayCastFace.PositiveAxis, Direction.Horizontal);
        outdoorDoor = new Door(constructionAssets, OUTDOOR_DOOR_POS,
                outdoorDoorOrientation.getDoorDirection(), nodes.getDoorsNode(), doubleDoor);
        Geometry outdoorDoorGeometry = outdoorDoor.getPrototypeGeometry().getGeometry();
        outdoorDoorControl = observerApp.createDoorControl(outdoorDoorGeometry, Doors.ENTRANCE_TO_COUNTRYARD,
                Doors.COUNTRYARD_TO_ENTRANCE, this, outdoorDoorOrientation, nodes);
        outdoorDoorGeometry.addControl(outdoorDoorControl);

        setEnabled(false);
    }

    @Override
    public void OnDisabled() {
        super.OnDisabled();
        outdoorDoor.setEnabled(false);
        this.corridorDoor.setEnabled(false);

    }

    @Override
    public void OnEnabled() {
        super.OnEnabled();
        outdoorDoor.setEnabled(true);
        this.corridorDoor.setEnabled(true);
        if (!isWithWolfBone) {
            this.outdoorDoorControl.setEnabled(false);
        } else {
            this.outdoorDoorControl.setEnabled(true);
        }
    }
}
