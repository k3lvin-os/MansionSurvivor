/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.List;
import mygame.javaclasses.Constants.Mapping;
import mygame.javaclasses.Constants.UserData;
import mygame.controls.PlayerControl;
import mygame.interfaces.IObservable;
import mygame.interfaces.IObserver;
import mygame.javaclasses.Constants.ObserverPattern;
import mygame.javaclasses.ArrayListSavable;

/**
 *
 * @author GAMEOVER
 */
public class InputApp extends AbstractAppState implements IObserver, IObservable {

    /*Gives access to the input of the game */
    private InputManager inputManager;
    /**
     * Gives access to player node (that contains the player and related)
     */
    private Node playerNode;
    /**
     * Give access to the cental simpleApp
     */
    private SimpleApplication simpleApp;
    /**
     * The player 3d model
     */
    private Spatial player;
    /**
     * Give acess to player physics (used in movement, for example)
     */
    private BetterCharacterControl playerPhysics;
    /**
     * Give access to player spatial and proprieties
     */
    private PlayerControl playerControl;
    /**
     * This is vector is necessary in the algorithm of the player move. With it
     * we can keep state of two buttons that make a diagonal movement
     */
    private Vector3f playerMove = new Vector3f();
    /**
     * List of player options that affect input check's
     */
    private ArrayListSavable<String> playerOptions;
    /**
     * This class use methods of GUIApp
     */
    private boolean nextToDoor;
    private ArrayListSavable<IObserver> observers;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {

        // Receive and set valeus
        super.initialize(stateManager, app);
        this.nextToDoor = false;
        this.simpleApp = (SimpleApplication) app;
        playerNode = (Node) this.simpleApp.getRootNode().getChild(UserData.PLAYER_NODE);
        player = playerNode.getChild(UserData.PLAYER);
        playerPhysics = player.getControl(BetterCharacterControl.class);
        playerControl = player.getControl(PlayerControl.class);
        observers = new ArrayListSavable<IObserver>();


        //Set mapping
        inputManager = app.getInputManager();
        inputManager.addMapping(Mapping.UP, new KeyTrigger(KeyInput.KEY_UP),
                new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(Mapping.DOWN, new KeyTrigger(KeyInput.KEY_DOWN),
                new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(Mapping.LEFT, new KeyTrigger(KeyInput.KEY_LEFT),
                new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(Mapping.RIGHT, new KeyTrigger(KeyInput.KEY_RIGHT),
                new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(Mapping.RETURN, new KeyTrigger(KeyInput.KEY_RETURN));

        // Add listeners here
        inputManager.addListener(movement, Mapping.UP, Mapping.DOWN, Mapping.LEFT, Mapping.RIGHT,
                Mapping.RETURN);


    }
    private ActionListener debugging = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (isEnabled()) {
                // Put verification's of debug input here
            }
        }
    };
    private ActionListener movement = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {

            if (isEnabled()) {
                if (name.equals(Mapping.RIGHT)) {
                    if (isPressed) {
                        playerMove.setX(playerControl.getSpeed());
                    } else {
                        playerMove.setX(0f);
                    }
                } else if (name.equals(Mapping.LEFT)) {
                    if (isPressed) {
                        playerMove.setX(playerControl.getSpeed() * -1f);
                    } else {
                        playerMove.setX(0f);
                    }
                } else if (name.equals(Mapping.UP)) {
                    if (isPressed) {
                        playerMove.setZ(playerControl.getSpeed() * -1f);
                    } else {
                        playerMove.setZ(0f);
                    }
                } else if (name.equals(Mapping.DOWN)) {
                    if (isPressed) {
                        playerMove.setZ(playerControl.getSpeed());
                    } else {
                        playerMove.setZ(0f);
                    }
                } else if (name.equals(Mapping.RETURN)) {

                    checkForPlayerActions();
                }

            }

            
            playerControl.setWalkDirection(playerMove);

            if (!playerControl.getWalkDirection().equals(Vector3f.ZERO)) {
                playerPhysics.setViewDirection(playerControl.getWalkDirection());
            }

            playerPhysics.setWalkDirection(playerControl.getWalkDirection());
        }
    };

    @Override
    public void update(float tpf) {
    }

    public void checkForPlayerActions() {

        if (nextToDoor) {
            notifyAllObservers(ObserverPattern.ENTERED_DOOR);
            nextToDoor = false;
        }
    }

    public void subjectUpdate(String update) {
        if (update.equals(ObserverPattern.NEXT_DOOR)) {
            this.nextToDoor = true;
        }

        if (update.equals(ObserverPattern.NOT_NEXT_DOOR)) {
            this.nextToDoor = false;
        }
    }

    public void addObserver(IObserver o) {
        observers.add(o);
    }

    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    public void notifyAllObservers(String update) {
        for (IObserver o : observers) {
            o.subjectUpdate(update);
        }
    }
}
