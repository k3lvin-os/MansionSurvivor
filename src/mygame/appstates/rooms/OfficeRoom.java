/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.rooms;

import mygame.appstates.util.RoomAppState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
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
public class OfficeRoom extends RoomAppState {

    public static final float DEFAULT_WIDTH = 36f;
    public static final float DEFAULT_HEIGHT = 20f;
    public static final float DEFAULT_SIZE = 18f;
    public static final Vector3f DEFAULT_POSITION = CorridorRoom.DEFAULT_LOCATION
            .add(new Vector3f(-DEFAULT_WIDTH, 0f, 0f));
    public static final Vector3f CORRIDOR_DOOR_POS = CorridorRoom.OFFICE_DOOR_POS
            .add(DoorControl.WALL_DISTANCE * -2f, 0f, 0f);
    protected Door corridorDoor;
    protected Geometry deskWithKey;
    protected Geometry cagesKey;

    public OfficeRoom() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_SIZE, DEFAULT_POSITION);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        // Corridor Door
        DoorOrientation corridorDoorOrientation = new DoorOrientation(RayCastFace.NegativeAxis, Direction.Vertical);
        this.corridorDoor = new Door(constructionAssets, CORRIDOR_DOOR_POS,
                corridorDoorOrientation.getDoorDirection(), nodes.getDoorsNode());
        Geometry corridorDoorGeometry = this.corridorDoor.getPrototypeGeometry().getGeometry();
        DoorControl corridorDoorControl = new DoorControl(corridorDoorGeometry,
                Doors.OFFICE_TO_CORRIDOR, Doors.CORRIDOR_TO_OFFICE, this,
                corridorDoorOrientation, nodes, inputApp);
        corridorDoorGeometry.addControl(corridorDoorControl);

        // Cages Key
        this.cagesKey = (Geometry) assetManager.loadModel("Models/key/key.j3o");
        this.cagesKey.setLocalTranslation(-10f,0f,-22f);
        this.cagesKey.scale(0.125f);
        this.cagesKey.rotate(FastMath.DEG_TO_RAD * 90F, 0F, 0F);

        setEnabled(false);

    }

    @Override
    public void OnEnabled() {
        super.OnEnabled();
        corridorDoor.setEnabled(true);
        nodes.getRootNode().attachChild(cagesKey);
    }

    @Override
    public void OnDisabled() {
        super.OnDisabled();
        corridorDoor.setEnabled(false);
        nodes.getRootNode().detachChild(cagesKey);
    }
}
