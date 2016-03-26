/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author GAMEOVER
 */
public class SimpleChaseControl extends AbstractControl implements IMeasures {

    public final static float SPEED = 1f;
    public static final boolean CHASE = true;
    private boolean initialized = false;

    public SimpleChaseControl(Spatial spatial, PlayerControl player) {
        this.spatial = spatial;
        setPlayerControl(player);
        setSpeed(SPEED);
        setChase(CHASE);

    }

    public Measures getMeasures() {
        return spatial.getUserData(UserData.MEASURES);
    }

    public void setPlayerControl(PlayerControl player) {
        spatial.setUserData("PlayerControl", player);
    }

    private PlayerControl getPlayerControl() {
        return spatial.getUserData("PlayerControl");
    }

    public void setSpeed(float speed) {
        spatial.setUserData(UserData.SPEED, speed);
    }

    public float getSpeed() {
        return spatial.getUserData(UserData.SPEED);
    }

    public void setChase(boolean chase) {
        spatial.setUserData(UserData.CHASE, chase);
    }

    public boolean getChase() {
        return spatial.getUserData(UserData.CHASE);
    }

    @Override
    protected void controlUpdate(float tpf) {

        if (getChase()) {
            float zDiff = getPlayerControl().getSpatial().getLocalTranslation().getZ() - spatial.getLocalTranslation().getZ();
            float xDiff = getPlayerControl().getSpatial().getLocalTranslation().getX() - spatial.getLocalTranslation().getX();
            float moveX = 0f, moveZ = 0f;

            if (zDiff >= getMeasures().getZ() / 2f) {
                moveZ = getSpeed();
            } else if (zDiff < -getMeasures().getZ() / 2f) {
                moveZ = -1 * getSpeed();
            }
            if (xDiff >= getMeasures().getX() / 2f) {
                moveX = getSpeed();
            } else if (xDiff < getMeasures().getX() / 2f) {
                moveX = -1f * getSpeed();
            }
            

            spatial.getControl(BetterCharacterControl.class).setWalkDirection(new Vector3f(moveX, 0f, moveZ));
            System.out.println(spatial.getControl(BetterCharacterControl.class ).getWalkDirection() );
            spatial.getControl(BetterCharacterControl.class).setViewDirection(
                    spatial.getControl(BetterCharacterControl.class).
                    getWalkDirection().mult(new Vector3f(-1f, 1f, -1f)));
            // IS NECESSARY MULTIPLY BY THE INVERSOR VECTOR BECAUSE THE NINJA MODEL
            // IS NOT IN THE CORRECT POSITION BY DEFAULT
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}