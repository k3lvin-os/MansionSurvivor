/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.util;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.io.IOException;
import mygame.appstates.NodesAppState;
import mygame.controls.DoorControl;
import mygame.enumerations.Direction;
import mygame.javaclasses.Constants;
import mygame.javaclasses.ConstructionAssets;
import mygame.enumerations.RayCastFace;

/**
 *
 * @author GAMEOVER
 */
public class ScenarioAppState extends AbstractAppState {

    public static final Vector3f FLOOR_MEASURES = new Vector3f(200F, 0f, 200f);
    protected static NodesAppState nodes;
    protected static AppStateManager stateManager;
    protected static BulletAppState bulletAppState;
    protected static AssetManager assetManager;
    protected static Spatial player;
    
    /** This will be used by the subclasses in order to pass construction assets easily */
    protected static ConstructionAssets constructionAssets;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        SimpleApplication simpleApp = (SimpleApplication) app;
        nodes = stateManager.getState(NodesAppState.class);
        bulletAppState = stateManager.getState(BulletAppState.class);
        ScenarioAppState.stateManager = stateManager;
        assetManager = simpleApp.getAssetManager();
        Node playerNode = nodes.getPlayerNode();
        player = playerNode.getChild(Constants.UserData.PLAYER);
        ScenarioAppState.constructionAssets = new ConstructionAssets(nodes.getRootNode(), assetManager, bulletAppState);
    }

    /* */
    protected static Geometry createGameFloor(AssetManager assetManager, Vector3f pos) {
        Box floorBox = new Box(FLOOR_MEASURES.x, FLOOR_MEASURES.y, FLOOR_MEASURES.z);
        Geometry floor = new Geometry("floor", floorBox);
        Material orangeMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        orangeMat.setColor("Color", ColorRGBA.Orange);
        floor.setMaterial(orangeMat);
        floor.setLocalTranslation(pos);
        BoxCollisionShape floorShape = new BoxCollisionShape(new Vector3f(FLOOR_MEASURES.x,
                FLOOR_MEASURES.y, FLOOR_MEASURES.z));
        RigidBodyControl floorPhysics = new RigidBodyControl(floorShape, 0.0f);
        floor.addControl(floorPhysics);
        bulletAppState.getPhysicsSpace().add(floorPhysics);
        nodes.getRootNode().attachChild(floor);
        return floor;
    }

    /**
     * Create a flat with the specified measures. This method in specific don't
     * attach in the root node
     */
    protected static Geometry createAFlat(AssetManager assetManager, float width, float height, float size, Vector3f pos) {
        Box floorBox = new Box(width, height, size);
        Geometry floor = new Geometry("floor", floorBox);
        Material orangeMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        orangeMat.setColor("Color", ColorRGBA.Orange);
        floor.setMaterial(orangeMat);
        floor.setLocalTranslation(pos);
        BoxCollisionShape floorShape = new BoxCollisionShape(new Vector3f(width, height, size));
        RigidBodyControl floorPhysics = new RigidBodyControl(floorShape, 0.0f);
        floor.addControl(floorPhysics);
        bulletAppState.getPhysicsSpace().add(floorPhysics);
        nodes.getRootNode().attachChild(floor);
        return floor;
    }

    /**
     * Create a wall. There's a useful NOTE: - the wall will "walk" negatively
     * starting by the origin. This means that if you are in a negative z axis
     * coordinate view, the wall will grow foward :)
     */
    protected static Geometry createWall(AssetManager assetManager, float width, float height, Vector3f pos, Direction d) {
        Box wallShape;
        BoxCollisionShape wallCollisionShape;


        if (d == Direction.Horizontal) {
            wallShape = new Box(width / 2f, height / 2f, 0f);
            wallCollisionShape = new BoxCollisionShape(new Vector3f(width / 2f, height / 2f, 0f));

        } else {
            wallShape = new Box(0f, height / 2f, width / 2f);
            wallCollisionShape = new BoxCollisionShape(new Vector3f(0f, height / 2f, width / 2f));
        }

        Geometry wall = new Geometry("wall", wallShape);
        Material wallMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        wallMat.setColor("Color", ColorRGBA.White);
        wall.setMaterial(wallMat);

        if (d == Direction.Horizontal) {
            wall.setLocalTranslation(pos.add(new Vector3f(width / 2f, height / 2f, 0f)));
        } else {
            wall.setLocalTranslation(pos.add(new Vector3f(0f, height / 2f, -width / 2f)));
        }

        RigidBodyControl wallPhysics = new RigidBodyControl(wallCollisionShape, 0.0f);
        wall.addControl(wallPhysics);
        bulletAppState.getPhysicsSpace().add(wallPhysics);
        nodes.getRootNode().attachChild(wall);
        return wall;
    }

    /**
     * Create and attach a room in the rootNode
     */
    protected static Node createARoom(AssetManager assetManager, float width, float height, float size, Vector3f leftExtreme) {


        Geometry bottomWall = createWall(assetManager, width, height, leftExtreme, Direction.Horizontal);

        Geometry topWall = createWall(assetManager, width, height,
                leftExtreme.add(new Vector3f(0f, 0f, -size)), Direction.Horizontal);


        Geometry leftWall = createWall(assetManager, size, height, leftExtreme, Direction.Vertical);

        Geometry rightWall = createWall(assetManager, size, height,
                leftExtreme.add(new Vector3f(width, 0f, 0f)),
                Direction.Vertical);

        // Geometry floor = createAFlat(assetManager, width, 1f, size, leftExtreme.add(new Vector3f(width / 2f, -1f, -size / 2f)));

        bottomWall.setName("bottomWall");
        topWall.setName("topWall");
        leftWall.setName("leftWall");
        rightWall.setName("rightWall");
        //floor.setName("floor");

        Node room = new Node();
        room.attachChild(bottomWall);
        room.attachChild(topWall);
        room.attachChild(leftWall);
        room.attachChild(rightWall);
        //room.attachChild(floor);

        return room;
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
