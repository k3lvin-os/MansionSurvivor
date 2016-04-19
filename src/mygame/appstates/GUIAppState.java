/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import mygame.controls.PlayerControl;
import mygame.javaclasses.Constants.UserData;

/**
 *
 * @author GAMEOVER
 */
public class GUIAppState extends AbstractAppState {

    private static final float DEFAULT_MESSAGE_X = 0f;
    private static final float DEFAULT_MESSAGE_Y = 0f;
    private static final boolean DEBUG = false;
    private PlayerControl playerControl;
    private Node guiNode;
    private SimpleApplication simpleApp;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        simpleApp = (SimpleApplication) app;
        NodesAppState nodesAppState = stateManager.getState(NodesAppState.class);
        this.guiNode = nodesAppState.getGuiNode();
        Node playerNode = nodesAppState.getPlayerNode();
        Spatial player = playerNode.getChild(UserData.PLAYER);
        this.playerControl = player.getControl(PlayerControl.class);
        
        if(!DEBUG){
           simpleApp.setDisplayFps(false);
           simpleApp.setDisplayStatView(false);
           simpleApp.restart();
        }
    }

    @Override
    public void update(float tpf) {
    }
    
    public void putMessageOnScreen(String message){
    
    }
}
