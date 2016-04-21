/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import mygame.appstates.rooms.Prototype1Room;
import mygame.appstates.rooms.MaintenanceRoom;
import mygame.appstates.rooms.MainCorridorRoom;
import mygame.appstates.rooms.OfficeRoom;
import mygame.appstates.rooms.MansionEntranceRoom;
import mygame.appstates.rooms.PowerGeneratorRoom;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import mygame.appstates.rooms.AnimalCagesRoom;
import mygame.controls.DoorControl;
import mygame.controls.PlayerControl;
import mygame.javaclasses.Constants.UserData;
import mygame.tests.LightForAllEnvironment;

/**
 *
 * @author GAMEOVER
 */
public class BeginGameAppState extends AbstractAppState {

    private boolean isObserverPatternSet;
    
    Prototype1Room prototypeRoom1AppState;
    NodesAppState nodesAppState;
    CharactersAppState charactersAppState;
    AppStateManager stateManager;
    GameplayInputAppState gameplayInputAppState;
    CameraAppState cameraAppState;
    AssetManager assetManager;
    BulletAppState bulletAppState;
    MansionEntranceRoom mansionEntranceAppState;
    LightForAllEnvironment lightForAllEnvironment;
    ChangeRoomAppState changeRoomAppState;
    MainCorridorRoom mainCorridorAppState;
    OfficeRoom officeAppState;
    AnimalCagesRoom animalCagesAppState;
    MaintenanceRoom maintenanceRoom;
    PowerGeneratorRoom powerGeneratorRoomAppState;
    GUIAppState guiAppState;


    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        isObserverPatternSet = false;
        this.stateManager = stateManager;
        SimpleApplication simpleApp = (SimpleApplication) app;
        assetManager = simpleApp.getAssetManager();

        bulletAppState = new BulletAppState();
        this.stateManager.attach(bulletAppState);

        nodesAppState = new NodesAppState();
        this.stateManager.attach(nodesAppState);

        charactersAppState = new CharactersAppState();
        this.stateManager.attach(charactersAppState);

        cameraAppState = new CameraAppState();
        this.stateManager.attach(cameraAppState);

        prototypeRoom1AppState = new Prototype1Room();
        this.stateManager.attach(prototypeRoom1AppState);

        mansionEntranceAppState = new MansionEntranceRoom();
        this.stateManager.attach(mansionEntranceAppState);

        lightForAllEnvironment = new LightForAllEnvironment();
        this.stateManager.attach(lightForAllEnvironment);
        
        changeRoomAppState = new ChangeRoomAppState();
        this.stateManager.attach(changeRoomAppState);
        
        gameplayInputAppState = new GameplayInputAppState();
        this.stateManager.attach(gameplayInputAppState);

        mainCorridorAppState = new MainCorridorRoom();
        this.stateManager.attach(mainCorridorAppState);

        officeAppState = new OfficeRoom();
        this.stateManager.attach(officeAppState);

        animalCagesAppState = new AnimalCagesRoom();
        this.stateManager.attach(animalCagesAppState);

        maintenanceRoom = new MaintenanceRoom();
        this.stateManager.attach(maintenanceRoom);

        powerGeneratorRoomAppState = new PowerGeneratorRoom();
        this.stateManager.attach(powerGeneratorRoomAppState);
        
        guiAppState = new GUIAppState();
        this.stateManager.attach(guiAppState);
    }
    
    
    @Override
    public void update(float tpf){
        if(!isObserverPatternSet){
            // Set observers here
            
            
            isObserverPatternSet = true;
        }
    }

}
