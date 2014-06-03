/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

/**
 *
 * @author Bob
 */
public class PlayerManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private BulletAppState    physics;
  public  Player            player;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    this.physics      = this.stateManager.getState(SceneManager.class).physics;
    initPlayer();
    }
  
  private void initPlayer(){
    player             = new Player();
    player.model       = (Node) assetManager.loadModel("Models/Person/Person.j3o");
    player.animControl = player.model.getChild("Person").getControl(AnimControl.class);
    player.armChannel  = player.animControl.createChannel();
    player.legChannel  = player.animControl.createChannel();
    player.playerPhys  = new BetterCharacterControl(.4f, 1.3f, 100f);
    
    TextureKey key     = new TextureKey("Models/Person/Person.png", true);
    Texture tex        = assetManager.loadTexture(key);
    Material mat       = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    
    mat.setTexture("ColorMap", tex);

    player.addControl(player.playerPhys);
    
    player.armChannel.addFromRootBone("TopSpine");
    player.legChannel.addFromRootBone("BottomSpine");
    player.armChannel.setAnim("ArmIdle");
    player.legChannel.setAnim("LegsIdle");
    
    physics.getPhysicsSpace().add(player.playerPhys);
    player.playerPhys.warp(new Vector3f(new Vector3f(45, 1, 38)));
    player.scale(.3f, .35f, .3f);
    player.model.setMaterial(mat);
    player.attachChild(player.model);
    player.model.setLocalTranslation(0, -.1f, 0);
        
    this.app.getRootNode().attachChild(player);
    }
  
  @Override
  public void update(float tpf){

    if (!player.playerPhys.isOnGround() && player.getWorldTranslation().y > 1) {
     // player.playerPhys.warp(player.getWorldTranslation().subtract(0, .005f, 0));
      }
    
    if (player.hasSwung && System.currentTimeMillis()/1000 - player.lastSwing > .1)
    player.hasSwung = false;
      
    if (player.getLocalTranslation().y < -5){
      player.playerPhys.warp(new Vector3f(45, 1, 38));
      stateManager.getState(GuiManager.class).showAlert("No Escape", "As you jump off the edge awaiting the sweet escape of death, you find yourself back where you started...");
      }
    
    }
  
   
  
  }
