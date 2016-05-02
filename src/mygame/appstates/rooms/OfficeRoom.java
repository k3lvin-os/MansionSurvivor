/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.rooms;

import com.bulletphysics.dynamics.RigidBody;
import mygame.appstates.util.RoomScenario;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import mygame.appstates.CameraApp;
import mygame.controls.CameraControl;
import mygame.appstates.ObserverManagerApp;
import mygame.controls.CollisionControl;
import mygame.controls.DoorControl;
import mygame.enumerations.Direction;
import mygame.enumerations.RayCastFace;
import mygame.javaclasses.Constants;
import mygame.javaclasses.Constants.Doors;
import mygame.javaclasses.Door;
import mygame.javaclasses.DoorOrientation;
import mygame.javaclasses.TargetSight;

/**
 *
 * @author GAMEOVER
 */
public class OfficeRoom extends RoomScenario {

    public static final float DEFAULT_WIDTH = 36f;
    public static final float DEFAULT_HEIGHT = 20f;
    public static final float DEFAULT_SIZE = 18f;
    public static final Vector3f DEFAULT_POSITION = CorridorRoom.DEFAULT_LOCATION
            .add(new Vector3f(-DEFAULT_WIDTH, 0f, 0f));
    public static final Vector3f CORRIDOR_DOOR_POS = CorridorRoom.OFFICE_DOOR_POS
            .add(DoorControl.WALL_DISTANCE * -2f, 0f, 0f);
    protected Door corridorDoor;
    protected Node deskWithKey;
    protected Geometry cagesKey;
    protected CollisionControl deskCollisionControl;
    protected CameraControl keyCamControl;
    private boolean playerTookKey;

    public OfficeRoom() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_SIZE, DEFAULT_POSITION);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        CameraApp cameraApp = stateManager.getState(CameraApp.class);
        playerTookKey = false;

        // Corridor Door
        DoorOrientation corridorDoorOrientation = new DoorOrientation(RayCastFace.NegativeAxis, Direction.Vertical);
        this.corridorDoor = new Door(constructionAssets, CORRIDOR_DOOR_POS,
                corridorDoorOrientation.getDoorDirection(), nodes.getDoorsNode());
        Geometry corridorDoorGeometry = this.corridorDoor.getPrototypeGeometry().getGeometry();
        DoorControl corridorDoorControl = observerApp.createDoorControl(corridorDoorGeometry,
                Doors.OFFICE_TO_CORRIDOR, Doors.CORRIDOR_TO_OFFICE, this,
                corridorDoorOrientation, nodes);
        corridorDoorGeometry.addControl(corridorDoorControl);

        // Cages Key
        this.cagesKey = (Geometry) assetManager.loadModel("Models/key/key.j3o");
        this.cagesKey.setLocalTranslation(-10f, 0f, -24f);
        this.cagesKey.scale(0.125f);
        this.cagesKey.rotate(FastMath.DEG_TO_RAD * 90F, 0F, 0F);
        TargetSight keySight = new TargetSight(new Vector3f(0f, 5f, 0f), new Vector3f(1f, 0f, 1f), new Vector3f(0f, 0f, -1f));
        keyCamControl =  observerApp.createCameraControl(cagesKey, cameraApp,keySight);
        this.cagesKey.addControl(keyCamControl);


        // DeskWithKey
        this.deskWithKey = (Node) assetManager.loadModel("Models/StylishDesk/StylishDesk.j3o");
        this.deskWithKey.setLocalTranslation(-10f, 0f, -24f);
        CollisionShape deskCollisionShape = CollisionShapeFactory.createBoxShape(deskWithKey);
        RigidBodyControl deskRigidBodyControl = new RigidBodyControl(deskCollisionShape, 0.0f);
        deskWithKey.addControl(deskRigidBodyControl);
        deskCollisionControl = new CollisionControl(deskWithKey, 4f, bulletAppState);
        deskWithKey.addControl(deskCollisionControl);
        deskCollisionControl.addObserver(keyCamControl);


        setEnabled(false);

    }

    @Override
    public void OnEnabled() {
        super.OnEnabled();
        corridorDoor.setEnabled(true);
        nodes.getRootNode().attachChild(cagesKey);
        nodes.getRootNode().attachChild(deskWithKey);
        bulletAppState.getPhysicsSpace().add(deskWithKey.getControl(RigidBodyControl.class));
        if (!playerTookKey) {
            nodes.getRootNode().attachChild(deskWithKey);
            deskCollisionControl.setEnabled(true);
            keyCamControl.setEnabled(true);
        }
    }

    @Override
    public void OnDisabled() {
        super.OnDisabled();
        corridorDoor.setEnabled(false);
        nodes.getRootNode().detachChild(cagesKey);
        nodes.getRootNode().detachChild(deskWithKey);
        bulletAppState.getPhysicsSpace().remove(deskWithKey.getControl(RigidBodyControl.class));
        if (!playerTookKey) {
            nodes.getRootNode().detachChild(deskWithKey);
            deskCollisionControl.setEnabled(false);
            keyCamControl.setEnabled(false);
        }
    }
}
