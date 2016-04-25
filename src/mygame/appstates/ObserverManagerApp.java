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
import mygame.controls.CollisionControl;
import mygame.controls.DoorControl;
import mygame.interfaces.IObservable;
import mygame.interfaces.IObserver;
import mygame.javaclasses.ArrayListSavable;
import mygame.javaclasses.DoorOrientation;

/**
 *
 * @author GAMEOVER
 */
public class ObserverManagerApp extends AbstractAppState implements IObserver, IObservable {

    private ChangeRoomApp changeRoomApp;
    private GUIApp guiApp;
    private InputApp inputApp;
    private boolean isAllSetted;
    private ArrayListSavable<IObserver> observers;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        changeRoomApp = stateManager.getState(ChangeRoomApp.class);
        guiApp = stateManager.getState(GUIApp.class);
        inputApp = stateManager.getState(InputApp.class);
        isAllSetted = false;
        observers = new ArrayListSavable<IObserver>();
    }

    @Override
    public void update(float tpf) {
        if (!isAllSetted) {
            isAllSetted = true;
            giveReferenceAllObservables();
            addAllObservers();
        }
    }

    public DoorControl createDoorControl(Spatial door, String doorName, String symetricDoorName, RoomScenario doorRoom,
            DoorOrientation orientation, NodesApp nodes) {
        DoorControl doorControl = new DoorControl(door, doorName, symetricDoorName, doorRoom, orientation, nodes);
        doorControl.addObserver(this);
        return doorControl;
    }

    public CollisionControl createCollisionControl(Spatial s, float maxDistance, BulletAppState bulletApp) {
        CollisionControl collisionControl = new CollisionControl(s, maxDistance, bulletApp);
        collisionControl.addObserver(this);
        return collisionControl;
    }

    private void giveReferenceAllObservables() {
        inputApp.addObserver(changeRoomApp);
        inputApp.addObserver(guiApp);
    }

    private void addAllObservers() {
        addObserver(inputApp);
        addObserver(guiApp);
        addObserver(changeRoomApp);
    }

    public void subjectUpdate(String update) {
        notifyAllObservers(update);
    }

    public void addObserver(IObserver o) {
        observers.add(o);
    }

    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    public void notifyAllObservers(String update) {
        if (observers != null) {
            for (IObserver o : observers) {
                o.subjectUpdate(update);
            }
        }
    }
}