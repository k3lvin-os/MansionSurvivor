/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
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
    private PlayerControl playerControl;
    private Node guiNode;
    private SimpleApplication simpleApp;
    private AssetManager assetManager;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        simpleApp = (SimpleApplication) app;
        NodesAppState nodesAppState = stateManager.getState(NodesAppState.class);
        this.guiNode = nodesAppState.getGuiNode();
        Node playerNode = nodesAppState.getPlayerNode();
        Spatial player = playerNode.getChild(UserData.PLAYER);
        this.playerControl = player.getControl(PlayerControl.class);
        this.assetManager = simpleApp.getAssetManager();
    }

    @Override
    public void update(float tpf) {
    }

    public void putMessageOnScreen(String message) {
        BitmapText guiText; // This  code is not ready to work
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.Blue);                             // font color
        hudText.setText(message);             // the text
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 0); // position
        guiNode.attachChild(hudText);
    }
}
