/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.tests;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.appstates.NodesApp;

/**
 *
 * @author GAMEOVER
 */
public class LightForAllEnvironment extends AbstractAppState {

    private static Node rootNode;
    private static PointLight light;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        rootNode = stateManager.getState(NodesApp.class).getRootNode();
        PutLightForAllEnvironment();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        rootNode.removeLight(light);
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (initialized) {
            if (enabled) {
                rootNode.addLight(light);
            } else {
                rootNode.removeLight(light);
            }
        }


    }

    public static void PutLightForAllEnvironment() {

        light = new PointLight();
        light.setColor(ColorRGBA.White);
        light.setRadius(Float.POSITIVE_INFINITY);
        light.setPosition(Vector3f.ZERO);
        rootNode.addLight(light);
    }
}
