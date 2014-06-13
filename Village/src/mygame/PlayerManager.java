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
import com.jme3.scene.Spatial;
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
    player.playerPhys.setGravity(new Vector3f(0, -20.81f, 0));
    player.playerPhys.warp(new Vector3f(new Vector3f(45, 1, 38)));
    //player.playerPhys.warp(new Vector3f(new Vector3f(26, 1, -3)));
    player.scale(.3f, .35f, .3f);
    player.model.setMaterial(mat);
    player.attachChild(player.model);
    player.model.setLocalTranslation(0, -.1f, 0);
    
    player.level = 1;
    player.questStep = "Start";
    this.app.getRootNode().attachChild(player);
    }
  
  private void initLevelTwo(){
      
    app.getRootNode().detachAllChildren();
    app.getRootNode().attachChild(player);
    
    stateManager.getState(NpcManager.class).npcNode.detachAllChildren();
    stateManager.getState(SceneManager.class).initSceneTwo();
    app.getRootNode().attachChild(stateManager.getState(NpcManager.class).npcNode);
    stateManager.getState(NpcManager.class).initVictim();
    stateManager.getState(NpcManager.class).initHorse();
    
    physics.getPhysicsSpace().add(player.playerPhys);
    player.playerPhys.warp(new Vector3f(new Vector3f(-15, 1, -3)));
    player.model.setCullHint(Spatial.CullHint.Never);
    player.level = 2;
    
    TextureKey key     = new TextureKey("Models/Person/Priest.png", true);
    Texture tex        = assetManager.loadTexture(key);
    Material mat       = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    
    mat.setTexture("ColorMap", tex);
    player.model.setMaterial(mat);
    player.questStep = "Start";
    
    stateManager.getState(GuiManager.class).delayAlert("Work to do", "You've finally arrived...", 2);
    stateManager.getState(AudioManager.class).playSong(2);
    }
  
  public void initLevelThree(){
    app.getRootNode().detachAllChildren();
    app.getRootNode().attachChild(player);
    
    NpcManager npcManager = stateManager.getState(NpcManager.class);
    npcManager.npcNode.detachAllChildren();
    stateManager.getState(SceneManager.class).initSceneThree();
    app.getRootNode().attachChild(npcManager.npcNode);

    npcManager.initBlacksmith();
    
    Npc woodsMan = (Npc) npcManager.npcNode.getChild("Blacksmith");
    woodsMan.setName("Woodsman");
    woodsMan.npcPhys.warp(new Vector3f(25, 1.2f, 3f));
    
    physics.getPhysicsSpace().add(player.playerPhys);
    player.playerPhys.warp(new Vector3f(-15, 1, -3));
    player.model.setCullHint(Spatial.CullHint.Never);
    player.level = 3;
    
    TextureKey key     = new TextureKey("Models/Person/Person.png", true);
    Texture tex        = assetManager.loadTexture(key);
    Material mat       = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    
    mat.setTexture("ColorMap", tex);
    player.model.setMaterial(mat);
    
    player.questStep = "Start";
    
    stateManager.getState(GuiManager.class).delayAlert("Lost", "You're lost in the woods... ", 2);
    stateManager.getState(AudioManager.class).playSong(1);
    }
  
  public void initLevelFour(){
      
    app.getRootNode().detachAllChildren();
    app.getRootNode().attachChild(player);
    
    stateManager.getState(NpcManager.class).npcNode.detachAllChildren();
    stateManager.getState(SceneManager.class).initSceneFour();
    app.getRootNode().attachChild(stateManager.getState(NpcManager.class).npcNode);
    
    stateManager.getState(NpcManager.class).initBilly();
    stateManager.getState(NpcManager.class).initSpider();
    stateManager.getState(NpcManager.class).initChemist();
    
    physics.getPhysicsSpace().add(player.playerPhys);
    player.playerPhys.warp(new Vector3f(new Vector3f(6, 5, -75)));
    player.model.setCullHint(Spatial.CullHint.Never);
    player.level = 4;
    
    TextureKey key     = new TextureKey("Models/Person/Person.png", true);
    Texture tex        = assetManager.loadTexture(key);
    Material mat       = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    
    mat.setTexture("ColorMap", tex);
    player.model.setMaterial(mat);
    player.questStep = "Start";
    
    stateManager.getState(GuiManager.class).delayAlert("Final Task", "You arrive in a village...", 2);
    stateManager.getState(AudioManager.class).playSong(1);
    }
  
  private void playerFallCheck(){
    
    if (player.level == 1)  
    
    if (player.questStep.equals("isDone")){
      initLevelTwo();
        
      } else {
      player.playerPhys.warp(new Vector3f(45, 1, 38));
      stateManager.getState(GuiManager.class).showAlert("No Escape", "As you jump off the edge awaiting the sweet escape of death, you find yourself back where you started...");
      }
      
      if (player.level == 2)
      
      if (player.questStep.equals("LeftHouse")) {
      initLevelThree();   
      
      } else {
      stateManager.getState(GuiManager.class).showAlert("No Escape", "As you jump off the edge awaiting the sweet escape of death, you find yourself back where you started...");
      player.playerPhys.warp(new Vector3f(new Vector3f(-15, 1, -3))); 
      }
      
      if (player.level == 3){
        stateManager.getState(GuiManager.class).showAlert("No Escape", "As you jump off the edge awaiting the sweet escape of death, you find yourself back where you started...");
        player.playerPhys.warp(new Vector3f(-15, 1, -3));
        }

      if (player.level == 4){
        stateManager.getState(GuiManager.class).showAlert("No Escape", "As you jump off the edge awaiting the sweet escape of death, you find yourself back where you started...");
        player.playerPhys.warp(new Vector3f(6, 5, -75));
        }
      
    }
  
  @Override
  public void update(float tpf){
     
    System.out.println(player.getLocalTranslation());
      
    //Swing Check
    if (player.hasSwung && System.currentTimeMillis()/1000 - player.lastSwing > .1)
    player.hasSwung = false;
    
    //Check for the player jumping off the edge. A series of checks for level and step
    if (player.getWorldTranslation().y < -5)
    playerFallCheck();
    }
  
  }
