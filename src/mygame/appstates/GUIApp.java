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
import mygame.javaclasses.Constants.ObserverPattern;

/**
 *
 * @author GAMEOVER
 */
public class GUIApp extends AbstractAppState implements IObserver {

    private static final ColorRGBA GREEN_COLOR = new ColorRGBA(22f / 255f, 110f / 255f, 12f / 255f, 1f);                            // font colo
    private static final String ENTER_DOOR = "Press ENTER to enter in the door";
    private static final String CLOSE_CAM_OBJECT = "Press I to interact";
    private static BitmapFont gameplayGUIFont;
    private Node guiNode;
    private SimpleApplication simpleApp;
    private AssetManager assetManager;
    private HashMap<String, Integer> messagesOnScreen;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        messagesOnScreen = new HashMap<String, Integer>();
        simpleApp = (SimpleApplication) app;
        NodesApp nodesAppState = stateManager.getState(NodesApp.class);
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
        hudText.setColor(GREEN_COLOR);
        hudText.setText(message);             // the text
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 0); // position
        int nodeId = guiNode.attachChild(hudText);
        System.out.println("At " + nodeId + " position");
        messagesOnScreen.put(message, nodeId);
    }

    public void removeMessageOnScreen(String message) {
        int nodeId = messagesOnScreen.get(message);
        System.out.println("Removing " + nodeId + " position");
        guiNode.detachChildAt(nodeId - 1);
        messagesOnScreen.remove(message);
    }

    public void subjectUpdate(String update) {

        System.out.println("GUIApp: msg = " + update);

        if (update.equals(ObserverPattern.NEXT_DOOR)) {
            try {
                putMessageOnScreen(ENTER_DOOR, TypeOfMessage.Gameplay);
            } catch (Exception ex) {
                Logger.getLogger(GUIApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (update.equals(ObserverPattern.NOT_NEXT_DOOR)
                || update.equals(ObserverPattern.ENTERED_DOOR)) {
            removeMessageOnScreen(ENTER_DOOR);
            
        } else if (update.equals(ObserverPattern.CLOSE_CAMERA_OBJECT)) {
            try {
                putMessageOnScreen(CLOSE_CAM_OBJECT, TypeOfMessage.Gameplay);
            } catch (Exception ex) {
                Logger.getLogger(GUIApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if (update.equals(ObserverPattern.NOT_CLOSE_CAMERA_OBJECT)) {
            removeMessageOnScreen(CLOSE_CAM_OBJECT);
        } else if (update.equals(ObserverPattern.SEE_CAMERA_OBJECT)) {
            removeMessageOnScreen(CLOSE_CAM_OBJECT);
            // do some more work here
        }

    }
}
