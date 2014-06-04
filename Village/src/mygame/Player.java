/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class Player extends Node {
 
  public BetterCharacterControl playerPhys;
  public Node        model;
  public AnimControl animControl;
  public AnimChannel armChannel;
  public AnimChannel legChannel;
  public boolean     hasSwung;
  public long        lastSwing;
  public float       speedMult;
  
  public boolean     hasShovel;
  public boolean     hasShrooms;
  public boolean     axePerm;
  public boolean     hasAxe;
  public boolean     hasWood;
  public boolean     hamCooked;
  public boolean     hasHam;
  public boolean     hasDog;
  public boolean     gaveDog;
  public boolean     stealWarn;
  public boolean     hasMeat;
  public boolean     gaveMeat;
  public boolean     isDone = true;
  
    
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
  
  private void getItem(CollisionResults results, AppStateManager stateManager){
    
    Node item = results.getCollision(0).getGeometry().getParent().getParent();
    GuiManager gui = stateManager.getState(GuiManager.class);
    
    if (item.getName().equalsIgnoreCase("Shovel")){
      hasShovel = true;
      item.removeFromParent();
      gui.showAlert("Interact", "You take the shovel");
      }
    
    if (item.getName().equalsIgnoreCase("Mushrooms")){
      
      if (hasShovel){
        gui.showAlert("Interact", "With some effort you dig up the mushrooms...");
        hasShrooms = true;
        item.removeFromParent();
        
        } else {
        gui.showAlert("Interact", "You probably need something to dig these up...");
        }
      
      }

    if (item.getName().equalsIgnoreCase("Axe")) {
        
        if (hasAxe){
        gui.showAlert("Interact", "An empty chopping block..."); 
        }
        
        else if (axePerm) {
        gui.showAlert("Interact", "You take the axe"); 
        hasAxe = true;
        item.getChild(1).removeFromParent();
        
        } else {
        gui.showAlert("Interact", "You shouldn't steal someone's stuff"); 
        }
      
      }
    
    if (item.getName().equalsIgnoreCase("Tree")){
      
      if (hasAxe){
        gui.showAlert("Interact", "You cut down the tree and retrieve the wood");
        hasWood = true;
        item.removeFromParent();
        
        } else {
        gui.showAlert("Interact", "This seems to be the only tree left..."); 
        }  
        
      }
    
    if (item.getName().equalsIgnoreCase("Table")){
        
        if (hasHam){
        gui.showAlert("Interact", "You've taken all the food"); 
        }
        
        else if (hamCooked){
        gui.showAlert("Interact", "You take the ham... You did get the wood after all");
        hasHam    = true;
        hamCooked = false;
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
