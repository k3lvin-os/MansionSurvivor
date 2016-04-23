/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.javaclasses;

import mygame.enumerations.Direction;
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
import com.jme3.scene.shape.Box;
import java.io.IOException;
import mygame.interfaces.IEnable;

/**
 *
 * @author GAMEOVER
 */
public class Wall implements IEnable, Savable {

    private RigidBodyControl wallPhysics;
    private Geometry wallGeometry;
    private Node rootNode;
    private BulletAppState bulletAppState;
    private boolean enabled;

    public RigidBodyControl getPhysics() {
        return wallPhysics;
    }

    public Geometry getGeometry() {
        return wallGeometry;
    }

    public Wall(ConstructionAssets constructionAssets, float width, float height, Vector3f pos, Direction d) {
        this.rootNode = constructionAssets.getRootNode();
        this.bulletAppState = constructionAssets.getBulletAppState();
        AssetManager assetManager = constructionAssets.getAssetManager();
        Box wallShape;
        BoxCollisionShape wallCollisionShape;



        if (d == Direction.Horizontal) {
            wallShape = new Box(width / 2f, height / 2f, 0f);
            wallCollisionShape = new BoxCollisionShape(new Vector3f(width / 2f, height / 2f, 0f));

        } else {
            wallShape = new Box(0f, height / 2f, width / 2f);
            wallCollisionShape = new BoxCollisionShape(new Vector3f(0f, height / 2f, width / 2f));
        }

        wallGeometry = new Geometry("wall", wallShape);
        Material wallMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        wallMat.setColor("Color", ColorRGBA.White);
        wallGeometry.setMaterial(wallMat);

        if (d == Direction.Horizontal) {
            wallGeometry.setLocalTranslation(pos.add(new Vector3f(width / 2f, height / 2f, 0f)));
        } else {
            wallGeometry.setLocalTranslation(pos.add(new Vector3f(0f, height / 2f, -width / 2f)));
        }

        wallPhysics = new RigidBodyControl(wallCollisionShape, 0.0f);
        wallGeometry.addControl(wallPhysics);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            bulletAppState.getPhysicsSpace().add(wallPhysics);
            rootNode.attachChild(wallGeometry);
        } else {
            bulletAppState.getPhysicsSpace().remove(wallPhysics);
            rootNode.detachChild(wallGeometry);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
