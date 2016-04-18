/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.rooms;

import mygame.appstates.util.RoomAppState;
import mygame.appstates.rooms.MansionEntranceRoom;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author GAMEOVER
 */
public class MainCorridorRoom extends RoomAppState {

    public static final float DEFAULT_WIDTH = 18f;
    public static final float DEFAULT_HEIGHT = 20f;
    public static final float DEFAULT_SIZE = 36f;
    public static final Vector3f DEFAULT_LOCATION = MansionEntranceRoom.DEFAULT_POSITION
            .add(new Vector3f(9f, 0f, -DEFAULT_SIZE / 2f));

    
    public MainCorridorRoom() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_SIZE, DEFAULT_LOCATION);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }
}
