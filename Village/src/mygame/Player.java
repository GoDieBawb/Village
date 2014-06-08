/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

public class Player extends Node {
 
  public BetterCharacterControl playerPhys;
  public Node        model;
  public AnimControl animControl;
  public AnimChannel armChannel;
  public AnimChannel legChannel;
  public boolean     hasSwung;
  public long        lastSwing;
  public float       speedMult; 
  public int         level;
  public String      questStep;
    
  public void swing(AppStateManager stateManager) {
    
    if (!hasSwung){
    armChannel.setAnim("ArmSwing");
    armChannel.setSpeed(2);
    armChannel.setLoopMode(LoopMode.DontLoop);
    lastSwing = System.currentTimeMillis()/1000;
    hasSwung  = true;
    
    
    Node itemNode = (Node) stateManager.getState(SceneManager.class).scene.getParent().getChild("ItemNode");
    CollisionResults results = new CollisionResults();
    itemNode.collideWith(this.model.getWorldBound(), results);
          
    if (results.size() > 0)
    getItem(results, stateManager);
    
    else
    stateManager.getState(GuiManager.class).showAlert("Interact", "Nothing interesting here...");
    
    }
  }
  
  private void getItem(CollisionResults results, AppStateManager stateManager) {
    
    if (level == 3)
    getLevelThreeItems(results, stateManager); 
    else if (level == 2)
    getLevelTwoItems(results, stateManager);
    else
    getLevelOneItems(results, stateManager);
    }    
  
  private void getLevelThreeItems(CollisionResults results, AppStateManager stateManager){
    Node item = results.getCollision(0).getGeometry().getParent();  
    GuiManager gui = stateManager.getState(GuiManager.class);  
    
    if (item.getName().equals("Table")){
      gui.showAlert(item.getName(), "An empty table...");
      }
    
    if (item.getName().equals("Bottle")){
      
      if (questStep.equals("Start")){
        gui.showAlert(item.getName(), "Looks like somebody dropped something...");
        }
      
      else if (questStep.equals("findBottle")){
        gui.showAlert(item.getName(), "You grab the bottle of vodka");
        item.getChild("Bottle").removeFromParent();
        questStep = "hasBottle";
        }
      
      else {
        gui.showAlert(item.getName(), "The rest looks like junk...");
        }
      
      }
    
    if (item.getName().equals("Axe")){
        
      if (questStep.equals("Start")){
        gui.showAlert(item.getName(), "Looks like somebody dropped something...");
        }
      
      else if (questStep.equals("findAxe")){
        gui.showAlert(item.getName(), "This will make a good weapon");
        item.getChild("Axe").removeFromParent();
        TextureKey key     = new TextureKey("Models/Person/Priest.png", true);
        Texture tex        = stateManager.getApplication().getAssetManager().loadTexture(key);
        Material mat       = new Material(stateManager.getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    
        mat.setTexture("ColorMap", tex);
        Npc priest = (Npc) stateManager.getState(NpcManager.class).npcNode.getChild("Woodsman");
        priest.model.setMaterial(mat);
        questStep = "hasAxe";
        }
      
      else if (questStep.equals("hasAxe")){
        gui.showAlert(item.getName(), "Get back to the black smith");
        }
      
      else {
        gui.showAlert(item.getName(), "This doesn't look interesting at the moment");
        }
        
      }
    
    if (item.getName().equals("Book")){
        
      if (questStep.equals("Start")){
        gui.showAlert(item.getName(), "Looks like somebody dropped something...");
        }
      
      else if (questStep.equals("findBook")){
        gui.showAlert(item.getName(), "The priest... He's coming...");
        questStep = "hasBook";
        }

      else if (questStep.equals("hasBook")){
        gui.showAlert(item.getName(), "Maybe you should tell the Woodsman about this priest...");
        }

      else {
        gui.showAlert(item.getName(), "This doesn't look interesting at the moment");
        }
        
      }
    
    if (item.getName().equals("Pen")){
      gui.showAlert(item.getName(), "That horse looks pretty panicked...");
      }
    
    if (item.getName().equals("Cart")){
      gui.showAlert(item.getName(), "This cart looks like it's seen better days...");
      }
    
    if (item.getName().equals("Tree")){
      gui.showAlert(item.getName(), "Looks like a tree");
      }
  
    }
  
  private void getLevelTwoItems(CollisionResults results, AppStateManager stateManager){      
    
    Node item = results.getCollision(0).getGeometry().getParent();  
    GuiManager gui = stateManager.getState(GuiManager.class);   
    
    if (item.getName().equals("Shovel2")){
      
    if (questStep.equals("FindScythe")){
        gui.showAlert(item.getName(), "This wouldn't do the job...");
        }
      
      else if (questStep.equals("Murder")){
        gui.showAlert(item.getName(), "You have work to do");
        }
      
      else if (questStep.equals("FindCrate")){
        gui.showAlert(item.getName(), "Best not to go outside to hide the body");
        }

      else if (questStep.equals("FindSaw")){
        gui.showAlert(item.getName(), "This won't help the body fit in the crate"); 
        }

      else if (questStep.equals("HideBody")){
        gui.showAlert(item.getName(), "The body can't be hid here"); 
        }

      else if (questStep.equals("LeaveHouse")){
        gui.showAlert(item.getName(), "You should leave before someone shows up");  
        }
      
      }
    
    if (item.getName().equals("Table2")){
      
    if (questStep.equals("FindScythe")){
        gui.showAlert("Table", "This is no time to eat...");   
        }
      
      else if (questStep.equals("Murder")){
        gui.showAlert(item.getName(), "This is no time to eat..."); 
        }
      
      else if (questStep.equals("FindCrate")){
        gui.showAlert(item.getName(), "This is no time to eat..."); 
        }

      else if (questStep.equals("FindSaw")){
        gui.showAlert(item.getName(), "This is no time to eat..."); 
        }

      else if (questStep.equals("HideBody")){
        gui.showAlert(item.getName(), "This is no time to eat..."); 
        }

      else if (questStep.equals("LeaveHouse")){
        gui.showAlert(item.getName(), "This is no time to eat..."); 
        }
        
      }

    if (item.getName().equals("SawTable")){
       
    if (questStep.equals("FindScythe")){
        gui.showAlert(item.getName(), "A saw would be too messy...");   
        }
      
      else if (questStep.equals("Murder")){
        gui.showAlert(item.getName(), "You have what you need..."); 
        }
      
      else if (questStep.equals("FindCrate")){
        gui.showAlert(item.getName(), "This may be useful soon...");   
        }

      else if (questStep.equals("FindSaw")){
        gui.showAlert(item.getName(), "You take the saw...");
        questStep = "HideBody";
        item.getChild("Saw").removeFromParent();
        }

      else if (questStep.equals("HideBody")){
        gui.showAlert(item.getName(), "The table is empty");  
        }

      else if (questStep.equals("LeaveHouse")){
        gui.showAlert(item.getName(), "The table is empty"); 
        }
        
      }

    if (item.getName().equals("Scythe")){
      
     if (questStep.equals("FindScythe")){
        gui.showAlert(item.getName(), "This is perfect... What a fitting item to complete your task"); 
        item.removeFromParent();
        questStep = "Murder";
        }
      
      else if (questStep.equals("Murder")){
        gui.showAlert(item.getName(), "Nothing here...");   
        }
      
      else if (questStep.equals("FindCrate")){
        gui.showAlert(item.getName(), "Nothing here...");
        }

      else if (questStep.equals("FindSaw")){
        gui.showAlert(item.getName(), "Nothing here...");
        }

      else if (questStep.equals("HideBody")){
        gui.showAlert(item.getName(), "Nothing here...");
        }

      else if (questStep.equals("LeaveHouse")){
        gui.showAlert(item.getName(), "Nothing here...");
        }
        
      }
    
    if (item.getName().equals("FirePlace")){
        
      gui.showAlert(item.getName(), "You probably shouldn't make a fire...");  
      
      }
    
    if (item.getName().equals("Axe")) {
        
      if (item.getChild("Axe") != null) {
          
        gui.showAlert(item.getName(), "You take the axe..."); 
        item.getChild("Axe").removeFromParent();
        questStep = "ChopDoor";
        
        } else {
        gui.showAlert(item.getName(), "An empty chopping block..."); 
        }
      }
    
    if (item.getName().equals("Board")){
        
      if (questStep.equals("FindAxe")){
        gui.showAlert(item.getName(), "The door is boarded up tightly..."); 
       
        } else {
        gui.showAlert(item.getName(), "You chop the planks off the door...");
        stateManager.getState(SceneManager.class).scene.getChild("Board").removeFromParent();
        questStep = "OpenDoor";
        }
      
      }

    if (item.getName().equals("Door")){
        
    if (questStep.equals("FindAxe")){
      gui.showAlert(item.getName(), "The door is boarded up tightly...");    
      }
      
    else if (questStep.equals("ChopDoor")){
      gui.showAlert(item.getName(), "You chop the planks off the door...");
      stateManager.getState(SceneManager.class).scene.getChild("Board").removeFromParent();
      questStep = "OpenDoor";
      }

    else if (questStep.equals("OpenDoor")){
      playerPhys.warp(new Vector3f(new Vector3f(-5, 1, -3)));
      stateManager.getState(GuiManager.class).showAlert("Reaper", "You close the door behind you...");
      stateManager.getState(AudioManager.class).playSound("Door");
      questStep = "FindScythe";
       }
        
      else if (questStep.equals("FindScythe")){
        gui.showAlert(item.getName(), "You still have work to do...");
        }
      
      if (questStep.equals("Murder")){
        gui.showAlert(item.getName(), "You still have work to do...");
        }
      
      if (questStep.equals("FindCrate")){
        gui.showAlert(item.getName(), "You must hide the body...");
        }

      if (questStep.equals("FindSaw")){
        gui.showAlert(item.getName(), "You must hide the body...");
        }

      if (questStep.equals("HideBody")){
        gui.showAlert(item.getName(), "You must hide the body...");
        }

      if (questStep.equals("LeaveHouse")){
        gui.showAlert(item.getName(), "As you leave the house... You feel like you're not the same as when you walked in...");
        playerPhys.warp(new Vector3f(new Vector3f(-7, 1, -3)));
        questStep = "LeftHouse";
        }
      
      if (questStep.equals("LeftHouse")){
        gui.showAlert(item.getName(), "You don't to go back in there...");
        }
      
      }

    if (item.getName().equals("Painting")){
      gui.showAlert(item.getName(), "A quite interesting painting...");  
      }

    if (item.getName().equals("Crate")){
        
      if (questStep.equals("FindScythe")){
        gui.showAlert(item.getName(), "Find something to finish the job...");
        }
      
      else if (questStep.equals("Murder")){
        gui.showAlert(item.getName(), "You have what you need...");
        }
      
      else if (questStep.equals("FindCrate")){
        gui.showAlert(item.getName(), "A large empty crate... But the body is just too big to fit.");
        questStep = "FindSaw";
        }

      else if (questStep.equals("FindSaw")){
        gui.showAlert(item.getName(), "Maybe something can help you fit the body here.");
        }

      else if (questStep.equals("HideBody")){
        gui.showAlert(item.getName(), "You place the dismembered parts into the crate...");
        questStep = "LeaveHouse";
        }

      else if (questStep.equals("LeaveHouse")){
        gui.showAlert(item.getName(), "A crate full of dismembered body parts");
        }
        
      }

    if (item.getName().equals("Chair")){
      gui.showAlert(item.getName(), "Now is no time to relax...");  
        
      }
      
      }
    
  private void getLevelOneItems(CollisionResults results, AppStateManager stateManager){
  
    Node item = results.getCollision(0).getGeometry().getParent().getParent();
    
    GuiManager gui = stateManager.getState(GuiManager.class);
    
    if (item.getName().equalsIgnoreCase("Shovel")){
      questStep = "hasShovel";
      item.removeFromParent();
      gui.showAlert("Interact", "You take the shovel");
      }
    
    if (item.getName().equalsIgnoreCase("Mushrooms")){
      
      if (questStep.equals("hasShovel")){
        gui.showAlert("Interact", "With some effort you dig up the mushrooms...");
        questStep = "hasShrooms";
        item.removeFromParent();
        
        } else {
        gui.showAlert("Interact", "You probably need something to dig these up...");
        }
      
      }

    if (item.getName().equalsIgnoreCase("Axe")) {
        
        if (questStep.equals("hasAxe")){
        gui.showAlert("Interact", "An empty chopping block..."); 
        }
        
        else if (questStep.equals("axePerm")) {
        gui.showAlert("Interact", "You take the axe"); 
        questStep = "hasAxe";
        item.getChild(1).removeFromParent();
        
        } else {
        gui.showAlert("Interact", "You shouldn't steal someone's stuff"); 
        }
      
      }
    
    if (item.getName().equalsIgnoreCase("Tree")){
      
      if (questStep.equals("hasAxe")){
        gui.showAlert("Interact", "You cut down the tree and retrieve the wood");
        questStep = "hasWood";
        item.removeFromParent();
        
        } else {
        gui.showAlert("Interact", "This seems to be the only tree left... You'll need an axe to cut it"); 
        }  
        
      }
    
    if (item.getName().equalsIgnoreCase("Table")){
        
        if (questStep.equals("hasHam")){
        gui.showAlert("Interact", "You've taken all the food"); 
        }
        
        else if (questStep.equals("hamCooked")){
        gui.showAlert("Interact", "You take the ham... You did get the wood after all");
        questStep = "hasHam";
        Node bla = (Node) item.getChild(0);
        bla.getChild(2).removeFromParent();
        } else {
        gui.showAlert("Interact", "This ham is raw...");
        }
    }
  }  
  
  public void run(){
    if (!armChannel.getAnimationName().equals("ArmRun") && !hasSwung){
      armChannel.setAnim("ArmRun");
      }
    
    if (!legChannel.getAnimationName().equals("LegRun")){
      legChannel.setAnim("LegRun");
      }
    
    }
  
  public void idle(){

    if (!armChannel.getAnimationName().equals("ArmIdle") && !hasSwung){
      armChannel.setAnim("ArmIdle");
      }
    
    if (!legChannel.getAnimationName().equals("LegsIdle")){
      legChannel.setAnim("LegsIdle");
      }
    
    }
  
}
