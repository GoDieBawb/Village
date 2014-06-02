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
    this.app = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.player = this.stateManager.getState(PlayerManager.class).player;
    initCamera();
    }
  
  //Creates camera
  public void initCamera(){
    //Creates a new chase cam and attached it to the player.model for the game
    cam = new ChaseCamera(this.app.getCamera(), player, this.app.getInputManager());
    cam.setMinDistance(3);
    cam.setMaxDistance(7);
    cam.setDragToRotate(false);
    cam.setRotationSpeed(5f);
    cam.setLookAtOffset(player.getLocalTranslation().add(0, 1f, 0));
    cam.setDefaultVerticalRotation(.145f);
    cam.setMaxVerticalRotation(.145f);
    cam.setMinVerticalRotation(.145f);
    cam.setDownRotateOnCloseViewOnly(false);
    }
  
  }
