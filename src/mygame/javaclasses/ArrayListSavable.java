/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.javaclasses;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author GAMEOVER
 * Allows set the array list in spatial user data through the implementation of Savaable
 * IMPORTANT NOTE: this can be the use (PlayerListAcations) substitute by Queue structure
 * in order to use something something more proper
 */
public class ArrayListSavable<T> extends ArrayList<T> implements Savable {

    public void write(JmeExporter ex) throws IOException {
    }

    public void read(JmeImporter im) throws IOException {

    }

    
}
