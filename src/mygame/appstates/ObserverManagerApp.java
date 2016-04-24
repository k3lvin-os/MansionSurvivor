/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import mygame.controls.CollisionControl;
import mygame.controls.DoorControl;

/**
 *
 * @author GAMEOVER
 */
public class ObserverManagerApp extends AbstractAppState {

    private ChangeRoomApp changeRoomApp;
    private GUIApp guiApp;
    private InputApp inputApp;
    private NodesApp nodesApp;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.changeRoomApp = stateManager.getState(ChangeRoomApp.class);
        this.guiApp = stateManager.getState(GUIApp.class);
        this.inputApp = stateManager.getState(InputApp.class);
        this.nodesApp = stateManager.getState(NodesApp.class);
        
    }
    
    public DoorControl createDoorControl(){
        return null;
    }
    
    public CollisionControl createCollisionControl(){
        return null;
    }
}