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
import com.jme3.asset.TextureKey;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.texture.Texture;
import tonegod.gui.controls.extras.android.Joystick;
import tonegod.gui.controls.windows.AlertBox;
import tonegod.gui.core.Screen;
import tonegod.gui.effects.Effect;

/**
 *
 * @author Bob
 */
public class GuiManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private AlertBox          alert;
  private Screen            screen;
  private Joystick          stick;
  private Player            player;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    player            = stateManager.getState(PlayerManager.class).player;
    screen            = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
    screen.setUseTextureAtlas(true,"tonegod/gui/style/atlasdef/atlas.png");
    screen.setUseMultiTouch(true);
    this.app.getGuiNode().addControl(screen);
    this.app.getInputManager().setSimulateMouse(true);
    initAlertBox();
    //initJoyStick();
    }
  
  private void initAlertBox(){
    alert = new AlertBox(screen, Vector2f.ZERO) {
    @Override
    public void onButtonOkPressed(MouseButtonEvent evt, boolean toggled) {
      hideWithEffect();
      }
    };
    
    alert.setWindowTitle("Welcome");
    alert.setMsg("Welcome to Townyville.");
    alert.setButtonOkText("Ok");
    alert.setLockToParentBounds(true);
    alert.setClippingLayer(alert);
    alert.setMinDimensions(new Vector2f(150,180));
    //alert.setDimensions(new Vector2f(150,180));
    alert.setIsResizable(true);
    screen.addElement(alert);
    }
  
  public void showAlert(String speaker, String text){
    alert.showWithEffect();
    alert.setWindowTitle(speaker);
    alert.setMsg(text);
    }

  private void initJoyStick(){
    stick = new Joystick(screen, Vector2f.ZERO, (int)70) {
    @Override
    public void onUpdate(float tpf, float deltaX, float deltaY) {
        float dzVal = .2f; // Dead zone threshold
            if (deltaX < -dzVal) {
                stateManager.getState(InteractionManager.class).up = false;
                stateManager.getState(InteractionManager.class).down = false;
                stateManager.getState(InteractionManager.class).left = true;
                stateManager.getState(InteractionManager.class).right = false;
            } else if (deltaX > dzVal) {
                stateManager.getState(InteractionManager.class).up = false;
                stateManager.getState(InteractionManager.class).down = false;
                stateManager.getState(InteractionManager.class).left = false;
                stateManager.getState(InteractionManager.class).right = true;
            } else if (deltaY < -dzVal) {
                stateManager.getState(InteractionManager.class).left = false;
                stateManager.getState(InteractionManager.class).right = false;
                stateManager.getState(InteractionManager.class).down = true;
                stateManager.getState(InteractionManager.class).up = false;
            } else if (deltaY > dzVal) {
                stateManager.getState(InteractionManager.class).left = false;
                stateManager.getState(InteractionManager.class).right = false;
                stateManager.getState(InteractionManager.class).down = false;
                stateManager.getState(InteractionManager.class).up = true;
            } else {
                stateManager.getState(InteractionManager.class).left = false;
                stateManager.getState(InteractionManager.class).right = false;
                stateManager.getState(InteractionManager.class).up = false;
                stateManager.getState(InteractionManager.class).down = false;
            }
            
          player.speedMult = FastMath.abs(deltaY);
          }
        };
      // getGUIRegion returns region info “x=0|y=0|w=50|h=50″, etc
      TextureKey key = new TextureKey("Textures/barrel/D.png", false);
      Texture tex = assetManager.loadTexture(key);
      stick.setTextureAtlasImage(tex, "x=20|y=20|w=120|h=35");
      stick.getThumb().setTextureAtlasImage(tex, "x=20|y=20|w=120|h=35");
      screen.addElement(stick, true);
      stick.setPosition(screen.getWidth()/10 - stick.getWidth()/2, screen.getHeight()/10 - stick.getHeight()/2);
      // Add some fancy effects
      Effect fxIn = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show,.5f);
      stick.addEffect(fxIn);
      Effect fxOut = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide,.5f);
      stick.addEffect(fxOut);
      stick.show();
      }
  
  }
