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
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import jme3tools.optimize.GeometryBatchFactory;

/**
 *
 * @author Bob
 */
public class SceneManager extends AbstractAppState{

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  public  Node              scene;
  public  BulletAppState    physics;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    physics           = new BulletAppState();
    stateManager.attach(physics);
    initScene();
    makeUnshaded();
    }
  
  private void initScene(){
    Material mat        = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    scene               = (Node) assetManager.loadModel("Scenes/Village.j3o");
    RigidBodyControl scenePhys = new RigidBodyControl(0f);
    
    //GeometryBatchFactory.optimize(scene, true);
    
    //scene.setMaterial(mat);
    Node walls = (Node) scene.getChild("Walls");

    
    scene.addControl(scenePhys);
    physics.getPhysicsSpace().add(scenePhys);
    
    this.app.getRootNode().attachChild(scene);
    }
  
  private void makeUnshaded(){
      
    SceneGraphVisitor sgv = new SceneGraphVisitor() {
 
    public void visit(Spatial spatial) {
      System.out.println(spatial);
 
      if (spatial instanceof Geometry) {
        
        Geometry geom = (Geometry) spatial;
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        System.out.println(geom.getMaterial().getTextureParam("DiffuseMap"));
        mat.setTexture("ColorMap", geom.getMaterial().getTextureParam("DiffuseMap").getTextureValue());
        geom.setMaterial(mat);
       
        }
      
      }
    };
    
    app.getRootNode().depthFirstTraversal(sgv);   
    
  }

  
  }