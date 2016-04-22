/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates.rooms;

import mygame.appstates.util.RoomAppState;
import mygame.appstates.rooms.MainCorridorRoom;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author GAMEOVER
 */
public class MaintenanceRoom extends RoomAppState {

    public static final float DEFAULT_WIDTH = 36F;
    public static final float DEFAULT_HEIGHT = 20F;
    public static final float DEFAULT_SIZE = 18f;
    public static final Vector3f DEFAULT_POSITION = MainCorridorRoom.DEFAULT_LOCATION
            .add(new Vector3f(MainCorridorRoom.DEFAULT_WIDTH, 0f, 0f));
    public static final Vector3f DEFAULT_PLAYER_POSITION = Vector3f.ZERO;

    public MaintenanceRoom() {
        super(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_SIZE, DEFAULT_POSITION);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        setEnabled(true);

    }
}
