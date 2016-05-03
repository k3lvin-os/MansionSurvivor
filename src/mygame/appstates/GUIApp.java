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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.enumerations.TypeOfMessage;
import mygame.interfaces.IObserver;
import mygame.javaclasses.Constants;
import mygame.javaclasses.Constants.ObserverPattern;
import com.jme3.system.Timer;
import mygame.interfaces.IObservable;

/**
 *
 * @author GAMEOVER
 */
public class GUIApp extends AbstractAppState implements IObserver, IObservable {

    public static final long DEFAULT_LETTER_DELAY = 100l;
    public static final long DEFAULT_END_DELAY = 2000L;
    private static final ColorRGBA GREEN_COLOR = new ColorRGBA(22f / 255f, 110f / 255f, 12f / 255f, 1f);                            // guiFont colo
    private static final String ENTER_DOOR = "Press ENTER to enter in the door";
    private static final String CLOSE_CAM_OBJECT = "Press I to interact";
    private static final String INTERACT_KEY1 = "It seems that there is a key down of glass of this table.";
    private static final String INTERACT_KEY2 = "Do you want to take it?";
    private static final String TOOK_SOMETHING = "You tooked the ";
    private static final String TOOK_KEY = "Animal Cages Key";
    private static BitmapFont gameplayGUIFont;
    private boolean interactWithKey = false;
    private Node guiNode;
    private float timer;
    private SimpleApplication simpleApp;
    private AssetManager assetManager;
    private HashMap<String, Integer> messagesOnScreen;
    private boolean waitingForPlayerInput;
    private ArrayList<IObserver> observers;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        messagesOnScreen = new HashMap<String, Integer>();
        simpleApp = (SimpleApplication) app;
        NodesApp nodesApp = stateManager.getState(NodesApp.class);
        this.guiNode = nodesApp.getGuiNode();
        this.assetManager = simpleApp.getAssetManager();
        gameplayGUIFont = assetManager.loadFont("Interface/Fonts/ArialBlack.fnt");
        waitingForPlayerInput = false;
        observers = new ArrayList<IObserver>();
    }

    @Override
    public void update(float tpf) {
    }

    /*Put a message on screen on a predefined position
     * @param: message the message to show on the screen
     @param: type the type of the the message to show on screen (change the guiFont also)*/
    public void putMessageOnScreen(String message, TypeOfMessage type) {
        BitmapText text = getText(message, type);
        int nodeId = guiNode.attachChild(text);
        messagesOnScreen.put(message, nodeId);
    }

    public void removeMessageOnScreen(String message) {
        int nodeId = messagesOnScreen.get(message);
        guiNode.detachChildAt(nodeId - 1);
        messagesOnScreen.remove(message);
    }

    public void subjectUpdate(String update) {

        System.out.println("GUIApp: msg = " + update);

        if (update.equals(ObserverPattern.NEXT_DOOR)) {
            putMessageOnScreen(ENTER_DOOR, TypeOfMessage.Gameplay);

        } else if (update.equals(ObserverPattern.NOT_NEXT_DOOR)
                || update.equals(ObserverPattern.ENTERED_DOOR)) {
            removeMessageOnScreen(ENTER_DOOR);

        } else if (update.equals(ObserverPattern.CLOSE_CAMERA_OBJECT)) {
            putMessageOnScreen(CLOSE_CAM_OBJECT, TypeOfMessage.Gameplay);

        } else if (update.equals(ObserverPattern.NOT_CLOSE_CAMERA_OBJECT)) {
            removeMessageOnScreen(CLOSE_CAM_OBJECT);
        } else if (update.equals(ObserverPattern.SEE_CAMERA_OBJECT)) {
            removeMessageOnScreen(CLOSE_CAM_OBJECT);
            putMessageOnScreen(INTERACT_KEY1, TypeOfMessage.Gameplay);
            notifyAllObservers(ObserverPattern.WAIT_PLAYER_CONFIRM);
            waitingForPlayerInput = true;
            while(waitingForPlayerInput){}
            removeMessageOnScreen(INTERACT_KEY1);
            putMessageOnScreen(INTERACT_KEY2, TypeOfMessage.Gameplay);
        }

    }

    private BitmapText getText(String text, TypeOfMessage type) {
        BitmapFont guiFont;
        BitmapText hudText;
        if (type == TypeOfMessage.Gameplay) {
            guiFont = gameplayGUIFont;
            hudText = new BitmapText(guiFont, false);
            hudText.setSize(guiFont.getCharSet().getRenderedSize());
            hudText.setColor(GREEN_COLOR);
            hudText.setText(text);
            hudText.setLocalTranslation(300, hudText.getLineHeight(), 0);
        } else {
            throw new UnsupportedOperationException("Impossible to use the font" + type.toString());
        }
        return hudText;
    }

    public void addObserver(IObserver o) {
        observers.add(o);
    }

    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    public void notifyAllObservers(String update) {
        for(IObserver o : observers){
            o.subjectUpdate(update);
        }
    }
}
