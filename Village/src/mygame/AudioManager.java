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
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    initAudio();
    }
  
  private void initAudio(){
    AudioNode music = new AudioNode(assetManager, "Sounds/Song.ogg", false);
    music.setLooping(true);
    music.setPositional(false);
    music.setVolume(.1f);
    this.app.getRootNode().attachChild(music);
    music.play();
    }
  
  }
