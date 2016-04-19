package mygame;

import mygame.appstates.BeginGameAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    /*Tells if should show the statistics and the FPS that help the developer
     to debug the game*/
    public static final boolean SHOW_DEBUG_INFO = false;

    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        app.setSettings(settings);
        app.start();
    }


    @Override
    public void simpleInitApp() {
        this.setDisplayFps(SHOW_DEBUG_INFO);
        this.setDisplayStatView(SHOW_DEBUG_INFO);
        stateManager.attach(new BeginGameAppState());
    }

    @Override
    public void simpleUpdate(float tpf) {

    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
