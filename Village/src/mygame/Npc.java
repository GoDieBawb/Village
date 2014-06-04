/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
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
  
  public String speak(Player player){
    lastSpoke = System.currentTimeMillis()/1000;
    hasSpoken = true;
    String text = new String();
    
    if (this.getName().equals("Blacksmith")){
    blacksmithQuest(player);
    text = setBlackSmithSpeech();
    }
    
    if (this.getName().equals("InnKeeper")){
    innKeeperQuest(player);
    text = setInnKeeperSpeech();
    }
    
    if (this.getName().equals("Priest")){
    priestQuest(player);
    text = setPriestSpeech();
    }
    
    if (this.getName().equals("Dog")){
    dogQuest(player);
    text = setDogSpeech();
    }
    
    if (this.getName().equals("Victim")){
    victimQuest(player);
    text =  setVictimSpeech();
    }
    
    return text;
    }
  
  
  private void victimQuest(Player player){
    
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
  
  private void blacksmithQuest(Player player){
      
    if (player.hasShrooms){
      questStep = 3;
      player.axePerm = true;
      }
    
    if (player.isDone){
      questStep = 4;
      }
    
    }
  
  private void priestQuest(Player player){

    if (player.hasDog) {
    questStep = 3;
    player.gaveDog = true;
    player.hasDog  = false;
    }
    
    if (player.stealWarn) {
    questStep = 4;
    player.stealWarn = false;
    player.hasMeat = true;
    }
    
    if (player.gaveMeat) {
    AudioNode sound = (AudioNode) player.getParent().getChild("Laugh");
    questStep = 6;
    player.isDone = true;
    sound.playInstance();
    }
     
    }
  
  private void innKeeperQuest(Player player){
      
    if(player.hasWood){
    questStep = 3; 
    player.hamCooked = true;
    }
    
    if (player.gaveDog && !player.stealWarn){
    questStep = 4;
    player.stealWarn = true;
    }
    
    if (player.stealWarn) {
    questStep = 4;
    }
    
    if(player.hasMeat ^ player.gaveMeat) {
    player.hasMeat = false;
    player.gaveMeat = true;
    questStep = 5;
    }
    
    if(player.isDone)
    questStep = 6;
        
    }
  
  private void dogQuest(Player player){
    if (player.hasHam && !player.gaveDog) {
      player.hasDog = true;
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