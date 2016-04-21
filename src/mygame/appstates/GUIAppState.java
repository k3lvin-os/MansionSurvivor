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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.enumerations.TypeOfMessage;
import mygame.interfaces.IObserver;
import mygame.javaclasses.Constants;
import mygame.javaclasses.Constants.Updates;

/**
 *
 * @author GAMEOVER
 */
public class GUIAppState extends AbstractAppState implements IObserver {
    
    private static final String ENTER_DOOR = "Press ENTER to enter in the door";
    private static BitmapFont gameplayGUIFont;
    private static final float DEFAULT_MESSAGE_X = 0f;
    private static final float DEFAULT_MESSAGE_Y = 0f;
    private Node guiNode;
    private SimpleApplication simpleApp;
    private AssetManager assetManager;
    private HashMap<String, Integer> messagesOnScreen;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        messagesOnScreen = new HashMap<String, Integer>();
        simpleApp = (SimpleApplication) app;
        NodesAppState nodesAppState = stateManager.getState(NodesAppState.class);
        this.guiNode = nodesAppState.getGuiNode();
        this.assetManager = simpleApp.getAssetManager();
        gameplayGUIFont = assetManager.loadFont("Interface/Fonts/ArialBlack.fnt");
    }

    @Override
    public void update(float tpf) {
    }


    /*Put a message on screen on a predefined position
     * @param: message the message to show on the screen
     @param: type the type of the the message to show on screen (change the font also)*/
    public void putMessageOnScreen(String message, TypeOfMessage type) throws Exception {
        BitmapFont guiFont;
        if (type == TypeOfMessage.Gameplay) {
            guiFont = gameplayGUIFont;
        } else {
            throw new Exception("The font for information message is not ready yet.");
        }
        BitmapText hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.Blue);                             // font color
        hudText.setText(message);             // the text
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 0); // position
        int nodeId = guiNode.attachChild(hudText);
        messagesOnScreen.put(message, nodeId);
    }

    public void removeMessageOnScreen(String message) {
       int nodeId = messagesOnScreen.get(message);
       guiNode.detachChildAt(nodeId);
       messagesOnScreen.remove(message);
    }

    public void subjectUpdate(String update) {
        
        if(update.equals(Updates.NEXT_DOOR)){
            try {
                putMessageOnScreen(ENTER_DOOR, TypeOfMessage.Gameplay);
            } catch (Exception ex) {
                Logger.getLogger(GUIAppState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(update.equals(Updates.NOT_NEXT_DOOR)){
            removeMessageOnScreen(ENTER_DOOR);
        }
    }
}
