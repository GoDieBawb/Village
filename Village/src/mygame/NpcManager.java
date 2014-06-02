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
public class NpcManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private Node              npcNode;
  private BulletAppState    physics;
  private Player            player;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    this.player       = this.stateManager.getState(PlayerManager.class).player;
    npcNode           = new Node();
    physics           = this.stateManager.getState(SceneManager.class).physics;
    this.app.getRootNode().attachChild(npcNode);
    initDog();
    initPriest();
    initBlacksmith();
    initInnKeeper();
    }
  
  private void initPriest(){
    Npc priest         = new Npc();
    priest.model       = (Node) assetManager.loadModel("Models/Person/Person.j3o");
    TextureKey key     = new TextureKey("Models/Person/Person.png", true);
    Texture tex        = assetManager.loadTexture(key);
    Material mat       = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setTexture("ColorMap", tex);

    priest.npcPhys = new BetterCharacterControl(.5f, 1.5f, 100f);
    priest.animControl = priest.model.getChild("Person").getControl(AnimControl.class);
    priest.armChannel  = priest.animControl.createChannel();
    priest.legChannel  = priest.animControl.createChannel();
      
    priest.addControl(priest.npcPhys);
    priest.model.setMaterial(mat);
      
    priest.armChannel.setAnim("ArmIdle");
    priest.legChannel.setAnim("LegsIdle");
    
    priest.scale(.3f, .35f, .3f);
    priest.setName("Priest");
            
    priest.attachChild(priest.model);
    physics.getPhysicsSpace().add(priest.npcPhys);
    priest.npcPhys.warp(new Vector3f(-51, 3, -56));
    npcNode.attachChild(priest);
    }
  
  private void initBlacksmith(){
    Npc blackSmith         = new Npc();
    blackSmith.model       = (Node) assetManager.loadModel("Models/Person/Person.j3o");
    TextureKey key         = new TextureKey("Models/Person/Person.png", true);
    Texture tex            = assetManager.loadTexture(key);
    Material mat           = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setTexture("ColorMap", tex);

    blackSmith.npcPhys = new BetterCharacterControl(.5f, 1.5f, 100f);
    blackSmith.animControl = blackSmith.model.getChild("Person").getControl(AnimControl.class);
    blackSmith.armChannel  = blackSmith.animControl.createChannel();
    blackSmith.legChannel  = blackSmith.animControl.createChannel();
      
    blackSmith.addControl(blackSmith.npcPhys);
    blackSmith.model.setMaterial(mat);
      
    blackSmith.armChannel.setAnim("ArmIdle");
    blackSmith.legChannel.setAnim("LegsIdle");
    
    blackSmith.scale(.3f, .35f, .3f); 
    blackSmith.setName("Blacksmith");
    
    blackSmith.attachChild(blackSmith.model);
    physics.getPhysicsSpace().add(blackSmith.npcPhys);
    blackSmith.npcPhys.warp(new Vector3f(26, 1, -3));
    npcNode.attachChild(blackSmith);
    }
  
  private void initInnKeeper(){
    Npc innKeeper      = new Npc();
    innKeeper.model    = (Node) assetManager.loadModel("Models/Person/Person.j3o");
    TextureKey key     = new TextureKey("Models/Person/Person.png", true);
    Texture tex        = assetManager.loadTexture(key);
    Material mat       = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setTexture("ColorMap", tex);

    innKeeper.npcPhys     = new BetterCharacterControl(.5f, 1.5f, 100f);
    innKeeper.animControl = innKeeper.model.getChild("Person").getControl(AnimControl.class);
    innKeeper.armChannel  = innKeeper.animControl.createChannel();
    innKeeper.legChannel  = innKeeper.animControl.createChannel();
      
    innKeeper.addControl(innKeeper.npcPhys);
    innKeeper.model.setMaterial(mat);
      
    innKeeper.armChannel.setAnim("ArmIdle");
    innKeeper.legChannel.setAnim("LegsIdle");
    
    innKeeper.attachChild(innKeeper.model);
    innKeeper.scale(.3f, .35f, .3f);
    innKeeper.setName("InnKeeper");
    
    physics.getPhysicsSpace().add(innKeeper.npcPhys);
    innKeeper.npcPhys.warp(new Vector3f(40, 1, 38));
    npcNode.attachChild(innKeeper);

    }
  
  private void initDog(){
    Npc dog         = new Npc();
    dog.model       = (Node) assetManager.loadModel("Models/dog.j3o");
    TextureKey key  = new TextureKey("Models/D.png", true);
    Texture tex     = assetManager.loadTexture(key);
    Material mat    = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    dog.npcPhys     = new BetterCharacterControl(.5f, 1.5f, 100f);
    dog.animControl = dog.model.getControl(AnimControl.class);
    dog.armChannel  = dog.animControl.createChannel();
    
    dog.armChannel.addAllBones();
    
    mat.setTexture("ColorMap", tex);
    dog.model.setMaterial(mat);
    dog.addControl(dog.npcPhys);
    
    dog.armChannel.setAnim("stand_idle");    
    physics.getPhysicsSpace().add(dog.npcPhys);
    dog.npcPhys.warp(new Vector3f(55, 1, -55));
    
    dog.attachChild(dog.model);
    dog.setName("Dog");
    npcNode.attachChild(dog);
    }
  
  @Override
  public void update(float tpf){
    

      
    for (int i = 0; i < npcNode.getQuantity(); i++) {
      
      Npc npc = (Npc) npcNode.getChild(i);
      Vector3f playerDir = player.model.getWorldTranslation().subtract(npc.model.getWorldTranslation());
      
      if (player.model.getWorldTranslation().distance(npc.model.getWorldTranslation()) < 3) {
        npc.npcPhys.setViewDirection(playerDir);
        
         if (!npc.hasSpoken){
          stateManager.getState(GuiManager.class).showAlert(npc.getName(), npc.speak(player));
          }

        }
      
      if (System.currentTimeMillis()/1000 - npc.lastSpoke > 5) {
        npc.hasSpoken = false;
        }
      
      }
      
    }
  
  }