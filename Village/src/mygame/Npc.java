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
    
    if (this.getName().equals("InnKeeper")){
    innKeeperQuest(player, stateManager);
    text = setInnKeeperSpeech();
    }
    
    if (this.getName().equals("Priest")){
    priestQuest(player, stateManager);
    text = setPriestSpeech();
    }
    
    if (this.getName().equals("Dog")){
    dogQuest(player, stateManager);
    text = setDogSpeech();
    }
    
    if (this.getName().equals("Victim")){
    victimQuest(player, stateManager);
    text =  setVictimSpeech();
    }
    
    return text;
    } 
  
  private void victimQuest(Player player, AppStateManager stateManager){
    
    if (player.questStep.equals("Murder")) {
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
    player.questStep = "gaveDog";
    }
    
    if (player.questStep.equals("stealWarn")) {
    questStep = 4;
    stateManager.getState(GuiManager.class).delayAlert("Noise", "You hear a commotion at the inn...", 5);
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
    
    if(player.questStep.equals("isDone"))
    questStep = 6;
        
    }
  
  private void dogQuest(Player player, AppStateManager stateManager){
    if (player.questStep.equals("hasHam")) {
      player.questStep = "hasDog";
      questStep = 2;      
      }
    }
  
  private String setVictimSpeech(){
    String text = new String();
    
    if (questStep == 2)
    text = "Your victim screams as the scythe cuts him...";
    
    else if (questStep == 3)
    text = "The body stares from across the room...";

    else if (questStep == 4)
    text = "Dismembered body parts roll at your feet";
    
    else if (questStep == 5)
    text = "Blood leaks from the crate";
    
    else
    text = "I figured you'd come for me";
    return text;
    }
  
  private String setDogSpeech(){
    String text = new String();
    
    if (questStep == 0){
      text = "This text is skipped on start";
      questStep++;
      }
    
    else if (questStep == 1) {
      text = "This dog seems to be quite nervous.";
      }

    else if (questStep == 2) {
      text = "The dog is happily following you";
      }
    
    else {
      text = "The dog is not happy";
      }
    
    return text;
    }
  
  private String setBlackSmithSpeech(){
    String text = new String();
    
    if (questStep == 0) {
      text = "Hello I'm the BlackSmith";
      questStep++;
      }
    
    else if (questStep == 1) {
      text = "Hello I'm the BlackSmith";
      questStep++;
      }
    
    else if (questStep == 2) {
      text = "I could use some Mushrooms";
      }
    
    else if (questStep == 3) {
      text = "Thanks for bringing me those mushrooms! Feel free to borrow my tools.";
      }

    else if (questStep == 4) {
      text = "Remember... Never go out in the graveyard, strange demons haunt that place.";
      }
    
    return text;
    }

  private String setPriestSpeech(){
    String text = new String();
    
    if (questStep == 0) {
      text = "Text is skipped";
      questStep++;
      }
    
    else if (questStep == 1) {
      text = "Hello I'm the Priest";
      questStep++;
      }
    
    else if (questStep == 2) {
      text = "My dog is missing";
      }
    
    else if (questStep == 3) {
      text = "Thanks I thought I would never see my dog again";
      }

    else if (questStep == 4) {
      text = "It's a shame that you had to take the innkeeper's ham... here, take this meat to the innkeeper";
      questStep = 5;
      }

    else if (questStep == 5) {
      text = "Take that meat to the InnKeeper.... He'll really like it";
      }

    else if (questStep == 6) {
      text = "The priest has disappeared... You hear faint laughing.";
      this.removeFromParent();
      }
    
    return text;
    }

  private String setInnKeeperSpeech(){
    String text = new String();
    
    if (questStep == 0) {
      text = "Welcome to Townyville";
      questStep++;
      }
    
    else if (questStep == 1) {
      text = "Hello I'm the InnKeeper";
      questStep++;
      }
    
    else if (questStep == 2) {
      text = "I could really use some wood";
      }

    else if (questStep == 3) {
      text = "My ham has just finished cooking, I need to finish cleaning before I eat.";
      }

    else if (questStep == 4) {
      text = "Someone has stolen my ham! Who would do this?";
      }

    else if (questStep == 5) {
      text = "That was some strange tasting meat... Be sure to thank whoever cooked it!";
      }

    else if (questStep == 6) {
      text = "A priest? There's no priest in this village... By the way, have you seen my dog?";
      }
    
    return text;
    }
  
}