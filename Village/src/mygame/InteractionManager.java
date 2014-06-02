/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;

/**
*
* @author Bob
*/
public class InteractionManager extends AbstractAppState implements ActionListener {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private InputManager      inputManager;
  private Player            player;
  private Vector3f walkDirection = new Vector3f();
  private Vector3f camDir = new Vector3f();
  private Vector3f camLeft = new Vector3f();
  public boolean inv = false, left = false, right = false, up = false, down = false, click = false;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    this.inputManager = this.app.getInputManager();
    this.player = this.stateManager.getState(PlayerManager.class).player;
    setUpKeys();
    }
  
  //Sets up key listeners for the action listener
  private void setUpKeys(){
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addMapping("Inventory", new KeyTrigger(KeyInput.KEY_E));
    inputManager.addListener(this, "Up");
    inputManager.addListener(this, "Down");
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Click");
    inputManager.addListener(this, "Inventory");
    }

  public void onAction(String binding, boolean isPressed, float tpf) {
    
    if (binding.equals("Inventory")) {
      inv = isPressed;
      if (isPressed)
      inputManager.setCursorVisible(true);
      else
      inputManager.setCursorVisible(false);
      }  
      
    if (binding.equals("Click") && !isPressed) {
      
      if (!player.hasSwung)
      player.swing(stateManager);
      }

    if (binding.equals("Left")) {
        
      left = isPressed;
      /**if (isPressed)
      player.run();
      else
      player.idle();
      **/
    } else if (binding.equals("Right")) {
  
      right = isPressed;
      /**if (isPressed)
      player.run();
      else
      player.idle();
      **/
      }
    if (binding.equals("Down")) {
      
      down = isPressed;
      if (isPressed)
      player.run();
      else
      player.idle();
    
    } else if (binding.equals("Up")) {
        
      up = isPressed;
      if (isPressed)
      player.run();
      else
      player.idle();
    }
        
  }
  
  @Override
  public void update(float tpf){
        camDir.set(this.app.getCamera().getDirection()).multLocal(10.0f, 0.0f, 10.0f);
        camLeft.set(this.app.getCamera().getLeft()).multLocal(10.0f);
        walkDirection.set(0, 0, 0);
        
        
        if (up) {
            walkDirection.addLocal(camDir);
            player.run();
        }
        else if (down) {
            walkDirection.addLocal(camDir.negate());
            player.run();
        }
        else if (left) {
            walkDirection.addLocal(camLeft);
            player.run();
        }
        else if (right) {
            walkDirection.addLocal(camLeft.negate());
            player.run();
        
        } else {
            player.idle();
        }
       player.playerPhys.setWalkDirection(walkDirection.mult(1));
       player.playerPhys.setViewDirection(camDir);

    }
  
  }
