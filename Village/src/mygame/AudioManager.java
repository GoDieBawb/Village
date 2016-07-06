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
import com.jme3.audio.AudioNode;

/**
 *
 * @author Bob
 */
public class AudioManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private AudioNode         music1;
  private AudioNode         music2;
  private AudioNode         laughSound;
  private AudioNode         doorSound;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    initAudio();
    playSong(1);
    }
  
  private void initAudio(){
      
    
    music1 = new AudioNode(assetManager, "Sounds/Song.ogg", false);
    music1.setLooping(true);
    music1.setPositional(false);
    music1.setVolume(.1f);
    this.app.getRootNode().attachChild(music1);
    
    
    //music2 = new AudioNode(assetManager, "Sounds/Song2.ogg", false);
    music2 = new AudioNode(assetManager, "Sounds/Song.ogg", false);
    music2.setLooping(true);
    music2.setPositional(false);
    music2.setVolume(.1f);
    this.app.getRootNode().attachChild(music2);

    laughSound = new AudioNode(assetManager, "Sounds/laugh.wav", false);
    laughSound.setName("Laugh");
    laughSound.setLooping(false);
    laughSound.setPositional(false);
    laughSound.setVolume(.1f);
    this.app.getRootNode().attachChild(laughSound);
 
    doorSound = new AudioNode(assetManager, "Sounds/Door.wav", false);
    doorSound.setLooping(false);
    doorSound.setPositional(false);
    doorSound.setVolume(.1f);
    this.app.getRootNode().attachChild(doorSound);
  
  }
  
  public void playSong(int song){
      
    if (song == 1){
        music2.stop();
        music1.play();
    } else {
        music1.stop();
        music2.play();
    }
    
    }
  
  public void playSound(String sound) {
      
    if (sound.equalsIgnoreCase("Laugh")){
      laughSound.playInstance();
      }
    if (sound.equalsIgnoreCase("Door")){
      doorSound.playInstance();
      }
    
    }
  
  }
