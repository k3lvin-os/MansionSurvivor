/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Spatial;
import mygame.appstates.util.RoomScenario;
import mygame.controls.CameraControl;
import mygame.controls.CollisionControl;
import mygame.controls.DoorControl;
import mygame.interfaces.IObservable;
import mygame.interfaces.IObserver;
import mygame.javaclasses.ArrayListSavable;
import mygame.javaclasses.DoorOrientation;
import mygame.javaclasses.TargetSight;

/**
 *
 * @author GAMEOVER
 */
public class ObserverManagerApp extends AbstractAppState {

    private ChangeRoomApp changeRoomApp;
    private GUIApp guiApp;
    private InputApp inputApp;
    private boolean isAllSetted;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        changeRoomApp = stateManager.getState(ChangeRoomApp.class);
        guiApp = stateManager.getState(GUIApp.class);
        inputApp = stateManager.getState(InputApp.class);
        isAllSetted = false;
    }

    @Override
    public void update(float tpf) {
        if (!isAllSetted) {
            isAllSetted = true;
            configureAppStates();
        }
    }

    public DoorControl createDoorControl(Spatial door, String doorName, String symetricDoorName, RoomScenario doorRoom,
        DoorOrientation orientation, NodesApp nodes) {
        DoorControl doorControl = new DoorControl(door, doorName, symetricDoorName, doorRoom, orientation, nodes);
        doorControl.addObserver(inputApp);
        doorControl.addObserver(guiApp);
        return doorControl;
    }
    
    public CameraControl createCameraControl(Spatial object, CameraApp cameraApp, TargetSight targetSight){
        CameraControl cameraControl = new CameraControl(object, cameraApp,targetSight );
        cameraControl.addObserver(guiApp);
        cameraControl.addObserver(inputApp);
        inputApp.addObserver(cameraControl);
        return cameraControl;
    }
    
    

    private void configureAppStates() {
        inputApp.addObserver(guiApp);
        inputApp.addObserver(changeRoomApp);
    }
}