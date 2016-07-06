/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.ChaseCamera;
import com.jme3.math.Vector3f;

/**
*
* @author Bob
*/
public class CameraManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private Player            player;
  public  ChaseCamera       cam;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app           = (SimpleApplication) app;
    this.stateManager  = this.app.getStateManager();
    this.player        = this.stateManager.getState(PlayerManager.class).player;
    initCamera();
    }
  
  //Creates camera
  public void initCamera(){
    //Creates a new chase cam and attached it to the player.model for the game
    cam = new ChaseCamera(this.app.getCamera(), player, this.app.getInputManager());
    cam.setMinDistance(0.5f);
    cam.setMaxDistance(5);
    cam.setDefaultDistance(3);
    cam.setDragToRotate(false);
    cam.setRotationSpeed(5f);
    cam.setLookAtOffset(player.getLocalTranslation().add(0, 1.2f, 0));
    cam.setDefaultVerticalRotation(.145f);
    cam.setMaxVerticalRotation(.145f);
    cam.setMinVerticalRotation(.145f);
  }
  
  private void chaseCamMove() {
    
      if (cam.getDistanceToTarget() < 1){
        cam.setMinVerticalRotation(0f);
        cam.setMaxVerticalRotation(5f);
    }
    
    else {
        cam.setMaxVerticalRotation(.145f);
        cam.setMinVerticalRotation(.145f); 
    }
    
  }
  
  private void topDownCamMove(){
    app.getCamera().setLocation(player.getLocalTranslation().addLocal(0,30,0));
    app.getCamera().lookAt(player.getLocalTranslation().multLocal(1,0,1), new Vector3f(0,1,0));
  }
  
  @Override 
  public void update(float tpf) {
      
    boolean topDown = stateManager.getState(InteractionManager.class).topDown;
    
    if (topDown) {
        topDownCamMove();
        cam.setEnabled(false);
    }
    
    else {   
        
        chaseCamMove();
        
        if (!cam.isEnabled())
            cam.setDragToRotate(false);   
        
        cam.setEnabled(true);
        
    }
    
    }
  }