/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.util;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.appstates.InputApp;
import mygame.appstates.util.ScenarioApp;
import mygame.javaclasses.ConstructionAssets;
import mygame.javaclasses.Room;

/**
 *
 * @author GAMEOVER
 */
public abstract class RoomScenario extends ScenarioApp implements Savable{

    protected float roomWidth;
    protected float roomSize;
    protected float roomHeight;
    protected Room room;
    protected Vector3f roomLeftExtreme;
    protected InputApp inputApp;

    public RoomScenario(float roomWidth, float roomHeight, float roomSize, Vector3f roomLeftExtreme) {
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        this.roomSize = roomSize;
        this.roomLeftExtreme = roomLeftExtreme;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.inputApp = stateManager.getState(InputApp.class);
        room = new Room(constructionAssets, roomWidth, roomHeight, roomSize, roomLeftExtreme);
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (initialized) {
            if (enabled) {
                OnEnabled();
            }
            else{
                OnDisabled();
            }
        }
    }
    
    @Override
    public void stateDetached(AppStateManager stateManager) {
        setEnabled(false);
    }
    
    
    protected void OnDisabled(){
        room.setEnabled(false);
    }
    
    protected void OnEnabled(){
        room.setEnabled(true);
    }
    
    
    @Override
    public void cleanup(){
        setEnabled(false);
    }
}
