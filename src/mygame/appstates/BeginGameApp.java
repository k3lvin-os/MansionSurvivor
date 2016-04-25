/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import mygame.appstates.rooms.OutdoorRoom;
import mygame.appstates.rooms.MaintenanceRoom;
import mygame.appstates.rooms.CorridorRoom;
import mygame.appstates.rooms.OfficeRoom;
import mygame.appstates.rooms.EntranceRoom;
import mygame.appstates.rooms.PowerRoom;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import mygame.appstates.rooms.CagesRoom;
import mygame.controls.DoorControl;
import mygame.controls.PlayerControl;
import mygame.javaclasses.Constants.UserData;
import mygame.tests.LightForAllEnvironment;

/**
 *
 * @author GAMEOVER
 */
public class BeginGameApp extends AbstractAppState {

    OutdoorRoom prototypeRoom1AppState;
    NodesApp nodesAppState;
    CharactersApp charactersAppState;
    AppStateManager stateManager;
    InputApp gameplayInputAppState;
    CameraApp cameraAppState;
    AssetManager assetManager;
    BulletAppState bulletAppState;
    EntranceRoom mansionEntranceAppState;
    LightForAllEnvironment lightForAllEnvironment;
    ChangeRoomApp changeRoomAppState;
    CorridorRoom mainCorridorAppState;
    OfficeRoom officeAppState;
    CagesRoom animalCagesAppState;
    MaintenanceRoom maintenanceRoom;
    PowerRoom powerGeneratorRoomAppState;
    GUIApp guiAppState;
    ObserverManagerApp observerManagerApp;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        SimpleApplication simpleApp = (SimpleApplication) app;
        assetManager = simpleApp.getAssetManager();

        observerManagerApp = new ObserverManagerApp();
        this.stateManager.attach(observerManagerApp);

        bulletAppState = new BulletAppState();
        this.stateManager.attach(bulletAppState);

        nodesAppState = new NodesApp();
        this.stateManager.attach(nodesAppState);

        charactersAppState = new CharactersApp();
        this.stateManager.attach(charactersAppState);

        cameraAppState = new CameraApp();
        this.stateManager.attach(cameraAppState);

        mansionEntranceAppState = new EntranceRoom();
        this.stateManager.attach(mansionEntranceAppState);

        prototypeRoom1AppState = new OutdoorRoom();
        this.stateManager.attach(prototypeRoom1AppState);

        lightForAllEnvironment = new LightForAllEnvironment();
        this.stateManager.attach(lightForAllEnvironment);

        changeRoomAppState = new ChangeRoomApp();
        this.stateManager.attach(changeRoomAppState);

        gameplayInputAppState = new InputApp();
        this.stateManager.attach(gameplayInputAppState);

        mainCorridorAppState = new CorridorRoom();
        this.stateManager.attach(mainCorridorAppState);

        officeAppState = new OfficeRoom();
        this.stateManager.attach(officeAppState);

        animalCagesAppState = new CagesRoom();
        this.stateManager.attach(animalCagesAppState);

        maintenanceRoom = new MaintenanceRoom();
        this.stateManager.attach(maintenanceRoom);

        powerGeneratorRoomAppState = new PowerRoom();
        this.stateManager.attach(powerGeneratorRoomAppState);

        guiAppState = new GUIApp();
        this.stateManager.attach(guiAppState);

    }
}
