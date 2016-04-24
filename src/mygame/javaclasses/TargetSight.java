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
    private Vector3f targetPositionOffset;
    private Vector3f camPositionOffset;
    
    public TargetSight(Vector3f camPosition, Vector3f targetPosition, Vector3f worldUpVector ){
        this.worldUpVector = worldUpVector;
        this.targetPositionOffset = targetPosition;
        this.camPositionOffset = camPosition;
    }
    
    public Vector3f getWorldUpVector(){
        return worldUpVector;
    }

    public Vector3f getTargetPositionOffset(){
        return targetPositionOffset;
    }
    
    public Vector3f getCamPositionOffset(){
        return camPositionOffset;
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
