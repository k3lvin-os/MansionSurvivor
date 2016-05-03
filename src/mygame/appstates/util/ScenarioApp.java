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
import mygame.appstates.NodesApp;
import mygame.appstates.ObserverManagerApp;
import mygame.controls.DoorControl;
import mygame.enumerations.Direction;
import mygame.javaclasses.Constants;
import mygame.javaclasses.ConstructionAssets;
import mygame.enumerations.RayCastFacing;
import mygame.javaclasses.Constants.UserData;

/**
 *
 * @author GAMEOVER
 */
public class ScenarioApp extends AbstractAppState {

    public static final Vector3f FLOOR_MEASURES = new Vector3f(200F, 0f, 200f);
    protected NodesApp nodes;
    protected AppStateManager stateManager;
    protected BulletAppState bulletAppState;
    protected AssetManager assetManager;
    protected Spatial player;
    protected ObserverManagerApp observerApp;    
     /**
     * This will be used by the subclasses in order to pass construction assets
     * easily
     */
    protected ConstructionAssets constructionAssets;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        SimpleApplication simpleApp = (SimpleApplication) app;
        this.nodes = stateManager.getState(NodesApp.class);
        this.bulletAppState = stateManager.getState(BulletAppState.class);
        this.stateManager = stateManager;
        this.assetManager = simpleApp.getAssetManager();
        Node playerNode = nodes.getPlayerNode();
        this.player = playerNode.getChild(Constants.UserData.PLAYER);
        this.constructionAssets = new ConstructionAssets(nodes.getRootNode(), assetManager, bulletAppState);
        this.observerApp = stateManager.getState(ObserverManagerApp.class);
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
