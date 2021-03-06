/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class Npc extends Node {

  public Node        model;
  public AnimControl animControl;
  public AnimChannel armChannel;
  public AnimChannel legChannel;
  public BetterCharacterControl npcPhys;
  public boolean      hasSpoken;
  public long         lastSpoke;
  public int          questStep;
    
  public void swing() {
    armChannel.setAnim("ArmSwing");
    armChannel.setSpeed(2);
    armChannel.setLoopMode(LoopMode.DontLoop);
  }
  
  public void run(){
    if (!armChannel.getAnimationName().equals("ArmRun")){
      armChannel.setAnim("ArmRun");
    }
    
    if (!legChannel.getAnimationName().equals("LegRun")){
      legChannel.setAnim("LegRun");
    }
    
  }
  
  public void idle(){

    if (!armChannel.getAnimationName().equals("ArmIdle")){
      armChannel.setAnim("ArmIdle");
    } 
    
  }
  
  public String speak(AppStateManager stateManager){
    
    Player player = stateManager.getState(PlayerManager.class).player;
    lastSpoke = System.currentTimeMillis()/1000;
    hasSpoken = true;
    String text = new String();
    
    if (this.getName().equals("Blacksmith")){
        blacksmithQuest(player, stateManager);
        text = setBlackSmithSpeech();
    }
    
    else if (this.getName().equals("InnKeeper")){
        innKeeperQuest(player, stateManager);
        text = setInnKeeperSpeech();
    }
    
    else if (this.getName().equals("Priest")){
        priestQuest(player, stateManager);
        text = setPriestSpeech();
    }
    
    else if (this.getName().equals("Dog")){
        dogQuest(player, stateManager);
        text = setDogSpeech();
    }
    
    else if (this.getName().equals("Victim")){
        victimQuest(player, stateManager);
        text =  setVictimSpeech();
    }
    
    else if (this.getName().equals("Horse")){
        text = setHorseSpeech(player, stateManager);
    }
    
    else if (this.getName().equals("Woodsman")){
        text = woodsmanQuest(player, stateManager);
    }
    
    else if (this.getName().equals("Billy")){
        text = billyQuest(player, stateManager);
    }

    else if (this.getName().equals("Chemist")){
        text = chemistQuest(player, stateManager);
    }

    else if (this.getName().equals("Spider")){
        text = spiderQuest(player, stateManager);
    }
    
    else {
        System.out.println(this.getName());
    }
    
    return text;
    } 
  
  private String setHorseSpeech(Player player, AppStateManager stateManager){
      
    String text;
    
    if (player.questStep.equals("LeftHouse"))
        text = "The horse is fleeing in terror"; 
    
    else
        text = "This horse looks ready to go... Too bad he didn't leave in time.";   
    
    return text;
    
  }
  
  private void victimQuest(Player player, AppStateManager stateManager){
    
    if (player.questStep.equals("Murder")) {
        model.rotate(-89.5f, 0, 0);
        animControl.setEnabled(false);
        questStep = 2;
        player.questStep = "FindCrate";
    }
    
    else if (player.questStep.equals("FindCrate") ^ player.questStep.equals("FindSaw"))
        questStep = 3;

    else if (player.questStep.equals("HideBody")){
        questStep = 4; 
        this.removeFromParent();
    }
    
    else if (player.questStep.equals("LeaveHouse"))
        questStep = 5;    

  }
  
  private void blacksmithQuest(Player player, AppStateManager stateManager){
      
    if (player.questStep.equals("Start")){
      questStep = 2;
    }
      
    else if (player.questStep.equals("hasShrooms")){
      questStep = 3;
      player.questStep = "axePerm";
    }
    
    else if (player.questStep.equals("isDone")){
      questStep = 4;
    }
    
  }
  
  private void priestQuest(Player player, AppStateManager stateManager){

    if (player.questStep.equals("hasDog")) {
        questStep = 3;
        stateManager.getState(GuiManager.class).delayAlert("Noise", "You hear a commotion at the inn...", 5);
        player.questStep = "gaveDog";
    }
    
    if (player.questStep.equals("stealWarn")) {
        questStep = 4;
        player.questStep = "hasMeat";
    }
    
    if (player.questStep.equals("gaveMeat")) {
        AudioNode sound = (AudioNode) player.getParent().getChild("Laugh");
        questStep = 6;
        player.questStep = "isDone";
        sound.playInstance();
    }
     
  }
  
  private void innKeeperQuest(Player player, AppStateManager stateManager){
      
    if(player.questStep.equals("hasWood")){
        questStep = 3; 
        player.questStep = "hamCooked";
    }
    
    if (player.questStep.equals("gaveDog")){
        questStep = 4;
        stateManager.getState(GuiManager.class).delayAlert("Noise", "The cry of a dog in the distance...", 5);
        player.questStep = "stealWarn";
    }
    
    if (player.questStep.equals("stealWarn")) {
        questStep = 4;
    }
    
    if(player.questStep.equals("hasMeat")) {
        player.questStep = "gaveMeat";
        questStep = 5;
    }
    
    if(player.questStep.equals("isDone")){
        stateManager.getState(GuiManager.class).delayAlert("Guilt", "With the overwhelming guilt of the of act you just comitted... Suicide is the only answer...", 5);
        questStep = 6;
    }
        
  }
  
  private void dogQuest(Player player, AppStateManager stateManager) {
      
    if (player.questStep.equals("hasHam")) {
        player.questStep = "hasDog";
        questStep = 2;      
    }
    
  }
  
  private String woodsmanQuest(Player player, AppStateManager stateManager){
    
    String text = new String();  
    
    if (player.questStep.equals("Start")){
      text = "I found a horse in the woods... It's cargo must have dropped."; 
        player.questStep = "findBottle";
      
    } 
    
    else if (player.questStep.equals("findBottle")) {
        text =  "Go out in the woods... See if that horse dropped some liquor, I need a drink"; 
      
    } 
    
    else if (player.questStep.equals("hasBottle")) {
        text =  "Aha! That's exactly what I needed..."; 
        player.questStep = "findBook";
      
    } 
    
    else if (player.questStep.equals("findBook")) {
        text =  "See if you can find some evidence of whose cart this was... I'm sure theres something the horse dropped"; 
          
    } 
    
    else if (player.questStep.equals("hasBook")) {
        text =  "A priest... THAT PRIEST?! HE'S HERE? "; 
        player.questStep = "findAxe";
      
    } 
    
    else if (player.questStep.equals("findAxe")) {
        text =  "Go and find a weapon immediately... Bring it back";
          
    } 
    
    else if (player.questStep.equals("hasAxe")) {
        text =  "Thanks... this would be perfect to kill you with...";
        stateManager.getState(PlayerManager.class).initLevelThree();
    }
    
    return text;
    
  }
  
  private String billyQuest(Player player, AppStateManager stateManager){
    String text;
    
    if (player.questStep.equals("Start")){
      player.questStep = "getPoison";
      text = "There's a huge spider upstairs! Go get some poison from the chemist to kill it!";
    }
    
    else if (player.questStep.equals("getPoison")){
      text = "Go get some poison from the chemist";    
    }

    else {
      text = "I don't care what ingredients he needs just go get them!";      
    }
    
    return text;
    }
  
  private String spiderQuest(Player player, AppStateManager stateManager){
    String text;
    
    if (player.questStep.equals("isDone")){
      text = "The End?";
      player.getParent().detachAllChildren();
    }
    
    else {
      text = "The spider eats your stupid face off... Why would you do that?";
      stateManager.getState(PlayerManager.class).initLevelFour();
    }
    
    return text;
    
  }
  
  private String chemistQuest(Player player, AppStateManager stateManager) {
    String text = new String();
    
    if (player.questStep.equals("Start")){
      text = "Go see what's going on over at Billy's house... He's been screaming all morning";
    }
    
    else if (player.questStep.equals("getPoison")){
      text = "A spider eh? Well I need some ingredients... First I'll need some acorns";
      player.questStep = "getAcorns";
    }
    
    else if (player.questStep.equals("getAcorns")){
      text = "Well those acorns aren't going to get themselves... Hurry up!";
    }
    
    else if (player.questStep.equals("hasAcorns")){
      player.questStep = "getShovel";
      text = "Those acorns are exactly what I needed! I'll need some red mushrooms next.";
    }
    
    else if (player.questStep.equals("getShovel")){
      text = "Go dig me up some red mushrooms! Hurry up.";
    }
 
    else if (player.questStep.equals("getMushrooms")){
      text = "Well you have the shovel... Where's the mushrooms?";  
    }

    else if (player.questStep.equals("hasMushrooms")){
      player.questStep = "getSilkfoot";
      text = "Those are the red mushrooms I needed, next I'll need some large brown mushrooms.";
    }

    else if (player.questStep.equals("getSilkfoot")){
      text = "I need large brown mushrooms... You'll find them somewhere around here.";
    }

    else if (player.questStep.equals("hasSilkfoot")){
      player.questStep = "getCone";
      text = "Ah... Those are some good looking mushrooms! The final ingredient is some cone top mushrooms...";
    }

    else if (player.questStep.equals("getCone")){
       text = "Conetop mushrooms are hard to find... I'm sure you'll come through";
    }
    
    else if (player.questStep.equals("hasCone")){
      player.questStep = "hasPoison";
      text = "There's the poison... That's enough to kill that spider and everyone in this village";
    }

    else if (player.questStep.equals("hasPoison")){
      text = "That's enough poison to kill that spider 10 times over...";
    }
    
    return text;
    
  }
  
  private String setVictimSpeech(){
    String text;
    
      switch (questStep) {
          case 2:
              text = "Your victim screams as the scythe cuts him...";
              break;
          case 3:
              text = "The body stares from across the room...";
              break;
          case 4:
              text = "Dismembered body parts roll at your feet";
              break;
          case 5:
              text = "Blood leaks from the crate";
              break;
          default:
              text = "I figured you'd come for me";
              break;
      }
    
    return text;
    
  }
  
  private String setDogSpeech(){
    String text;
    
      switch (questStep) {
          case 0:
              text = "This text is skipped on start";
              questStep++;
              break;
          case 1:
              text = "This dog seems to be quite nervous.";
              break;
          case 2:
              text = "The dog is happily following you";
              break;
          default:
              text = "The dog is not happy";
              break;
      }
    
    return text;
    
  }
  
  private String setBlackSmithSpeech(){
    String text = new String();
    
      switch (questStep) {
          case 0:
              text = "Hello I'm the BlackSmith";
              questStep++;
              break;
          case 1:
              text = "Hello I'm the BlackSmith";
              questStep++;
              break;
          case 2:
              text = "I could use some Mushrooms";
              break;
          case 3:
              text = "Thanks for bringing me those mushrooms! Feel free to borrow my tools.";
              break;
          case 4:
              text = "Remember... Never go out in the graveyard, strange demons haunt that place.";
              break;
          default:
              break;
      }
    
    return text;
    }

  private String setPriestSpeech(){
    String text = new String();
    
      switch (questStep) {
          case 0:
              text = "Text is skipped";
              questStep++;
              break;
          case 1:
              text = "Hello I'm the Priest";
              questStep++;
              break;
          case 2:
              text = "My dog is missing";
              break;
          case 3:
              text = "Thanks I thought I would never see my dog again";
              break;
          case 4:
              text = "It's a shame that you had to take the innkeeper's ham... here, take this meat to the innkeeper";
              questStep = 5;
              break;
          case 5:
              text = "Take that meat to the InnKeeper.... He'll really like it";
              break;
          case 6:
              text = "The priest has disappeared... You hear faint laughing.";
              this.removeFromParent();
              break;
          default:
              break;
      }
    
    return text;
    }

  private String setInnKeeperSpeech(){
    String text = new String();
    
      switch (questStep) {
          case 0:
              text = "Welcome to Townyville";
              questStep++;
              break;
          case 1:
              text = "Hello I'm the InnKeeper";
              questStep++;
              break;
          case 2:
              text = "I could really use some wood";
              break;
          case 3:
              text = "My ham has just finished cooking, I need to finish cleaning before I eat.";
              break;
          case 4:
              text = "Someone has stolen my ham! Who would do this?";
              break;
          case 5:
              text = "That was some strange tasting meat... Be sure to thank whoever cooked it!";
              break;
          case 6:
              text = "A priest? There's no priest in this village... By the way, have you seen my dog?";
              break;
          default:
              break;
      }
    
    return text;
    }
  
}