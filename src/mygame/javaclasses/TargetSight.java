/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.javaclasses;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.io.IOException;

/**
 *
 * @author GAMEOVER
 */
public class TargetSight implements Savable {

    private Vector3f worldUpVector;
    private Vector3f camPositionOffset;
    private Vector3f directionOffset;
    private Vector3f targetFollowFactor;

    public TargetSight(Vector3f camPositionOffset, Vector3f targetFollowFactor, Vector3f worldUpVector) {
        this.worldUpVector = worldUpVector;
        this.camPositionOffset = camPositionOffset;
        this.targetFollowFactor = targetFollowFactor;
        this.directionOffset = Vector3f.ZERO;
    }

    public TargetSight(Vector3f camPositionOffset, Vector3f targetFollowFactor, Vector3f worldUpVector, Vector3f directionOffset) {
        this.worldUpVector = worldUpVector;
        this.camPositionOffset = camPositionOffset;
        this.targetFollowFactor = targetFollowFactor;
        this.directionOffset = directionOffset;
    }

    public Vector3f getWorldUpVector() {
        return worldUpVector;
    }

    public Vector3f getCamPositionOffset() {
        return camPositionOffset;
    }
    
    public Vector3f getDirectionOffset(){
        return directionOffset;
    }
    
    public Vector3f getTargetFollowFactor(){
        return this.targetFollowFactor;
    }


    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
