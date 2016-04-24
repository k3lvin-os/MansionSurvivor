/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Quaternion;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import mygame.javaclasses.Constants.UserData;
import mygame.javaclasses.TargetSight;

/**
 *
 * @author GAMEOVER
 */
public class CameraAppState extends AbstractAppState {

    public static final boolean FLY_CAM_ENABLED = false;
    private Camera cam;
    private FlyByCamera flyCam;
    private TargetSight targetSight;
    private Spatial target;

    /**
     * Set a targetSight based in user data of the passed spatial
     */
    public void setTarget(Spatial target) {
        this.targetSight = target.getUserData(UserData.TARGET_SIGHT);
        this.target = target;
    }

    /**
     * This method provides the correct way to set target sight to null
     */
    public void removeTarget() {
        this.targetSight = null;
        this.target = null;
    }

    public FlyByCamera getFlyByCamera() {
        return flyCam;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        SimpleApplication simpleApp = (SimpleApplication) app;
        cam = simpleApp.getCamera();
        flyCam = simpleApp.getFlyByCamera();
        flyCam.setEnabled(FLY_CAM_ENABLED);
    }

    @Override
    public void update(float tpf) {

        if (!flyCam.isEnabled()) { // This verification is only checked in stage of development

            if (targetSight != null && target != null) {
                this.cam.setLocation(this.targetSight.getCamPositionOffset().
                        add( this.targetSight.getTargetFollowFactor().mult(this.target.getLocalTranslation()) ));
                this.cam.lookAt(this.target.getLocalTranslation().add(this.targetSight.getDirectionOffset()), 
                        this.targetSight.getWorldUpVector());

            }

        }

    }
}
