/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.javaclasses;

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
import mygame.javaclasses.Constants.UserData;

/**
 *
 * @author GAMEOVER
 */
public class Floor implements IEnable, Savable {
    
    private Geometry floor;
    private boolean enabled;
    private Node rootNode;
    private BulletAppState bulletAppState;
    
    public Geometry getGeometry(){
        return floor;
    }

    public Floor(ConstructionAssets assets, float width, float size, Vector3f leftExtreme) {
        this.rootNode = assets.getRootNode();
        this.bulletAppState = assets.getBulletAppState();
        Box floorBox = new Box(width / 2f, 0f, size /2f);
        floor = new Geometry(UserData.FLOOR, floorBox);
        Material orangeMat = new Material(assets.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        orangeMat.setColor("Color", ColorRGBA.Orange);
        floor.setMaterial(orangeMat);
        floor.setLocalTranslation(leftExtreme.add(width / 2f, 0f, -size / 2f));
        BoxCollisionShape floorShape = new BoxCollisionShape(new Vector3f(width / 2f, 0f, size / 2f));
        RigidBodyControl floorPhysics = new RigidBodyControl(floorShape, 0.0f);
        floor.addControl(floorPhysics);
        bulletAppState.getPhysicsSpace().add(floorPhysics);    
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if(enabled){
             this.rootNode.attachChild(floor);
             this.bulletAppState.getPhysicsSpace().add(floor);
        }
        else{
             this.rootNode.detachChild(floor);
             this.bulletAppState.getPhysicsSpace().remove(floor);
        }
    }

    public void write(JmeExporter ex) throws IOException {

    }

    public void read(JmeImporter im) throws IOException {

    }
    
    
}
