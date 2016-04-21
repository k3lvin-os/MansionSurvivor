/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.controls;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.List;
import mygame.javaclasses.Constants.UserData;
import mygame.javaclasses.MyArrayList;

/**
 *
 * @author GAMEOVER
 */
public class PlayerControl extends AbstractControl {

    private static final float DEFAULT_SPEED = 20f;

    private void setSpeed(float speed) {
        spatial.setUserData(UserData.SPEED, speed);
    }

    public float getSpeed() {
        return spatial.getUserData(UserData.SPEED);
    }

    public void setWalkDirection(Vector3f walkDirection) {
        spatial.setUserData(UserData.WALK_DIRECTION, walkDirection);
    }

    public Vector3f getWalkDirection() {
        return spatial.getUserData(UserData.WALK_DIRECTION);
    }
    
    public void setPosition(Vector3f pos){
        spatial.getControl(BetterCharacterControl.class).setEnabled(false);
        spatial.setLocalTranslation(pos);
        spatial.getControl(BetterCharacterControl.class).setEnabled(true);
    }
    
    public PlayerControl(Spatial s) {
        this.spatial = s;
        setSpeed(DEFAULT_SPEED);
        setWalkDirection(new Vector3f(-1f,0,-1f));
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }


}
