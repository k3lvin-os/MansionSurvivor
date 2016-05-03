/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.javaclasses;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;
import mygame.enumerations.RayCastFacing;
import mygame.enumerations.Direction;

/**
 *
 * @author GAMEOVER
 */
public class DoorOrientation implements Savable{
   
    private RayCastFacing doorType;
    private Direction doorDirection;
    
    public DoorOrientation(RayCastFacing doorType, Direction doorDirection){
       this.doorType = doorType;
       this.doorDirection = doorDirection;
    }
    
    public DoorOrientation(DoorOrientation doorOrientation){
        this.doorType = doorOrientation.getDoorType();
        this.doorDirection = doorOrientation.getDoorDirection();
    }
    
    public RayCastFacing getDoorType(){
        return doorType;
    }
    
    public Direction getDoorDirection(){
        return doorDirection;
    }

    public void write(JmeExporter ex) throws IOException {

    }

    public void read(JmeImporter im) throws IOException {

    }
}
