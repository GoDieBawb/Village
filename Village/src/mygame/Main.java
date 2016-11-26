package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
* test
* @author normenhansen
*/
public class Main extends SimpleApplication {
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        this.setShowSettings(false);
        this.setDisplayFps(false);
        this.setDisplayStatView(false);
        this.getStateManager().attach(new SceneManager());
        this.getStateManager().attach(new PlayerManager());
        this.getStateManager().attach(new InteractionManager());
        this.getStateManager().attach(new CameraManager());
        this.getStateManager().attach(new NpcManager());
        this.getStateManager().attach(new AudioManager());
        this.getStateManager().attach(new GuiManager());
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }
    
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
