/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.controls;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mygame.interfaces.IObservable;
import mygame.interfaces.IObserver;
import mygame.javaclasses.Constants;
import mygame.javaclasses.Constants.ObserverPattern;
import mygame.javaclasses.Constants.UserData;
import mygame.javaclasses.MyArrayList;

/**
 *
 * This control wiil be used to detect if a object is close to the player
 *
 * @author Kelvin Oliveira
 *
 */
public class CollisionControl extends AbstractControl implements IObservable, PhysicsCollisionListener {

    public static final float MIN_DISTANCE = 4F;
    private MyArrayList<IObserver> observers;
    private BulletAppState bulletApp;
    private float maxDistance;

    /**
     * Create a brand new Collision Control :) IMPORTANT: this constructor will
     * attach a GhostControl to your characther, but will not espcify what do it
     * before you add this (CollisionControl)
     *
     * @param s The spatial that will receive the control
     * @param collisionShape The collision shape of the control (usually, is the
     * same of the physics)
     *
     *
     */
    public CollisionControl(Spatial s, float maxDistance, BulletAppState bulletApp) {
        this.spatial = s;
        this.bulletApp = bulletApp;
        this.maxDistance = maxDistance;
        this.observers = new MyArrayList<IObserver>();
        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            bulletApp.getPhysicsSpace().addCollisionListener(this);
        } else {
            bulletApp.getPhysicsSpace().removeCollisionListener(this);
        }
    }

    @Override
    protected void controlUpdate(float tpf) {

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
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

    public void collision(PhysicsCollisionEvent event) {
        Spatial player = null;
        if(event.getNodeA().getName().equals(UserData.PLAYER)){
            player = event.getNodeA();
        }
        else if(event.getNodeA().getName().equals(UserData.PLAYER)){
            player = event.getNodeB();
        }
        
       if(player != null){
           if(spatial.getLocalTranslation().distance(player.getLocalTranslation()) <= maxDistance){
               notifyAllObservers(ObserverPattern.COLLISION_PLAYER);
           }
       } 
    }
}
