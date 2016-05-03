/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.controls;

import com.jme3.export.Savable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;
import mygame.appstates.CameraApp;
import mygame.interfaces.IObservable;
import mygame.interfaces.IObserver;
import mygame.javaclasses.ArrayListSavable;
import mygame.javaclasses.Constants;
import mygame.javaclasses.Constants.ObserverPattern;
import mygame.javaclasses.Constants.UserData;
import mygame.javaclasses.TargetSight;

/**
 *
 * @author Kelvin Oliveira
 */
public class FocusControl extends AbstractControl implements IObserver, IObservable{

    private CameraApp cameraApp;
    private ArrayList<IObserver> observers;
    
    public FocusControl(Spatial s, CameraApp cameraApp, TargetSight targetSight){
       this.setSpatial(s);
       this.cameraApp = cameraApp;
       this.observers = new ArrayList<IObserver>();
       this.spatial.setUserData(UserData.TARGET_SIGHT, targetSight);
    }
    
    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void subjectUpdate(String update) {
        
        if(update.equals(ObserverPattern.COLLISION_PLAYER)){
            notifyAllObservers(ObserverPattern.CLOSE_CAMERA_OBJECT);
        }
        
        if(update.equals(ObserverPattern.NOT_COLLISION_PLAYER)){
            notifyAllObservers(ObserverPattern.NOT_CLOSE_CAMERA_OBJECT);
        }
        
        if(update.equals(ObserverPattern.SEE_CAMERA_OBJECT)){
           
           cameraApp.setTarget(spatial);
        }
    }

    public void addObserver(IObserver o) {
        observers.add(o);
    }

    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    public void notifyAllObservers(String update) {
        if(observers.isEmpty()){
            throw new UnsupportedOperationException("Use ObserverManegerApp to create a CameraControl with observers and observing observables");
        }
        
        for(IObserver o : observers){
            o.subjectUpdate(update);
        }
    }
    
}
