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
    private Vector3f playerMove;
    
    private boolean nextToDoor;
    private boolean interact;
    private boolean confirm;
    private ArrayListSavable<IObserver> observers;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {

        // Receive and set valeus
        super.initialize(stateManager, app);
        this.nextToDoor = false;
        this.interact = false;
        this.confirm = false;
        this.simpleApp = (SimpleApplication) app;
        playerNode = (Node) this.simpleApp.getRootNode().getChild(UserData.PLAYER_NODE);
        player = playerNode.getChild(UserData.PLAYER);
        playerPhysics = player.getControl(BetterCharacterControl.class);
        playerControl = player.getControl(PlayerControl.class);
        observers = new ArrayListSavable<IObserver>();
        playerMove = Vector3f.ZERO;


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
        inputManager.addMapping(Mapping.RETURN,
                new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping(Mapping.KEY_I,
                new KeyTrigger(KeyInput.KEY_I));


        // Add listeners here
        inputManager.addListener(movement, Mapping.UP, Mapping.DOWN, Mapping.LEFT, Mapping.RIGHT,
                Mapping.RETURN, Mapping.KEY_I);


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
                    checkEnterDoor();
                } else if (name.equals(Mapping.KEY_I)) {
                    checkInteract();
                }


            }


            playerControl.setWalkDirection(playerMove);

            if (!playerControl.getWalkDirection().equals(Vector3f.ZERO)) {
                playerPhysics.setViewDirection(playerControl.getWalkDirection().divide(playerControl.getSpeed()));
            }

            playerPhysics.setWalkDirection(playerControl.getWalkDirection());
        }
    };

    @Override
    public void update(float tpf) {
    }

    public boolean checkInteract() {
        if (interact) {
            setEnabled(false);
            notifyAllObservers(ObserverPattern.SEE_CAMERA_OBJECT);
            return true;
        }
        return false;
    }

    public boolean checkEnterDoor() {

        if (nextToDoor) {
            Vector3f rayCastDir = ChangeRoomApp.getDoorPlayerIsUsing().getRayDirection().mult(-1f);
            if (rayCastDir.getX() == -0.0f) {
                rayCastDir.setX(0.0f);
            }
            if (rayCastDir.getZ() == -0.0f) {
                rayCastDir.setZ(0.0f);
            }
            if (rayCastDir.getY() == -0.0f) {
                rayCastDir.setY(0.0f);
            }

            if (rayCastDir.equals(playerPhysics.getViewDirection())) {
                notifyAllObservers(ObserverPattern.ENTERED_DOOR);
                nextToDoor = false;
                return true;
            }
        }
        return false;
    }

    public void subjectUpdate(String update) {
        if (update.equals(ObserverPattern.NEXT_DOOR)) {
            this.nextToDoor = true;
        } else if (update.equals(ObserverPattern.NOT_NEXT_DOOR)) {
            this.nextToDoor = false;
        } else if (update.equals(ObserverPattern.CLOSE_CAMERA_OBJECT)) {
            this.interact = true;
        } else if (update.equals(ObserverPattern.NOT_CLOSE_CAMERA_OBJECT)) {
            this.interact = false;
        }
        else if(update.equals(ObserverPattern.WAIT_PLAYER_CONFIRM)){
            
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
