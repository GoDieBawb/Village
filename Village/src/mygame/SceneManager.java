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
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

/**
 *
 * @author Bob
 */
public class SceneManager extends AbstractAppState{

  private SimpleApplication app;
  private AssetManager      assetManager;
  public  Node              scene;
  public  BulletAppState    physics;
  public Node               roofNode;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.assetManager = this.app.getAssetManager();
    physics           = new BulletAppState();
    roofNode          = new Node();
    stateManager.attach(physics);
    initScene();
    makeUnshaded();
    //addLight();
  }
  
  private void initScene(){
    scene               = (Node) assetManager.loadModel("Scenes/Village.j3o");
    RigidBodyControl scenePhys = new RigidBodyControl(0f);
    
    scene.addControl(scenePhys);
    physics.getPhysicsSpace().add(scenePhys);
    
    this.app.getRootNode().attachChild(scene);
  }
  
  public void initSceneTwo(){
    physics.getPhysicsSpace().removeAll(scene);
    scene               = (Node) assetManager.loadModel("Scenes/SceneTwo.j3o");
    RigidBodyControl scenePhys = new RigidBodyControl(0f);
    
    scene.addControl(scenePhys);
    physics.getPhysicsSpace().add(scenePhys);
    
    this.app.getRootNode().attachChild(scene);
    makeUnshaded();
  }
  
  public void initSceneThree(){
    physics.getPhysicsSpace().removeAll(scene);
    scene                      = (Node) assetManager.loadModel("Scenes/SceneThree.j3o");
    RigidBodyControl scenePhys = new RigidBodyControl(0f);
    
    scene.addControl(scenePhys);
    physics.getPhysicsSpace().add(scenePhys);
    
    this.app.getRootNode().attachChild(scene);
    makeUnshaded();
  }
  
  public void initSceneFour(){
    physics.getPhysicsSpace().removeAll(scene);
    scene                      = (Node) assetManager.loadModel("Scenes/SceneFour.j3o");
    RigidBodyControl scenePhys = new RigidBodyControl(0f);
    
    scene.addControl(scenePhys);
    physics.getPhysicsSpace().add(scenePhys);
    
    this.app.getRootNode().attachChild(scene);
    makeUnshaded();
  }
  
  private void makeUnshaded(){
      
    SceneGraphVisitor sgv = new SceneGraphVisitor() {
 
    @Override
    public void visit(Spatial spatial) {
 
      if (spatial instanceof Geometry) {
        
        Geometry geom = (Geometry) spatial;
        Material mat  = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material tat  = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
        
        if (geom.getMaterial().getTextureParam("DiffuseMap_1") != null) {
            
          String     alTexPath  = geom.getMaterial().getTextureParam("AlphaMap").getTextureValue().getName().substring(1);
          TextureKey alkey      = new TextureKey(alTexPath, false);
          Texture    alTex      = assetManager.loadTexture(alkey);     
          
          tat.setTexture("Alpha", alTex);
          
          if (geom.getMaterial().getTextureParam("DiffuseMap") != null) {
           
            String     d1TexPath  = geom.getMaterial().getTextureParam("DiffuseMap").getTextureValue().getName();
            TextureKey d1key      = new TextureKey(d1TexPath, false);
            Texture    d1Tex      = assetManager.loadTexture(d1key);             
            
            tat.setTexture("Tex1", d1Tex);
            tat.getTextureParam("Tex1").getTextureValue().setWrap(Texture.WrapMode.Repeat);
            tat.setFloat("Tex1Scale", Float.valueOf(geom.getMaterial().getParam("DiffuseMap_0_scale").getValueAsString()));
          
          }
        
          if (geom.getMaterial().getTextureParam("DiffuseMap_1") != null) {
              
            String     d2TexPath  = geom.getMaterial().getTextureParam("DiffuseMap_1").getTextureValue().getName();
            TextureKey d2key      = new TextureKey(d2TexPath, false);
            Texture    d2Tex      = assetManager.loadTexture(d2key);                     
                        
            tat.setTexture("Tex2", d2Tex);
            tat.getTextureParam("Tex2").getTextureValue().setWrap(Texture.WrapMode.Repeat);
            tat.setFloat("Tex2Scale", Float.valueOf(geom.getMaterial().getParam("DiffuseMap_1_scale").getValueAsString()));
          
          }
        
          if (geom.getMaterial().getTextureParam("DiffuseMap_2") != null) {
              
            String     d3TexPath  = geom.getMaterial().getTextureParam("DiffuseMap_2").getTextureValue().getName();
            TextureKey d3key      = new TextureKey(d3TexPath, false);
            Texture    d3Tex      = assetManager.loadTexture(d3key);              
            
            tat.setTexture("Tex3", d3Tex);
            tat.getTextureParam("Tex3").getTextureValue().setWrap(Texture.WrapMode.Repeat);
            tat.setFloat("Tex3Scale", Float.valueOf(geom.getMaterial().getParam("DiffuseMap_2_scale").getValueAsString()));
          
          }

          geom.setMaterial(tat);
          
        }        
        
        
        else if (geom.getMaterial().getTextureParam("DiffuseMap") != null) {
          mat.setTexture("ColorMap", geom.getMaterial().getTextureParam("DiffuseMap").getTextureValue());
          geom.setMaterial(mat);
        
        /**if (geom.getName().contains("roof")) {
          Vector3f spot       = geom.getWorldTranslation();
          System.out.println(spot);
          Quaternion rotation = findHouseParent(geom).getLocalRotation();
          roofNode.attachChild(geom);
          geom.setLocalTranslation(spot);
          geom.setLocalRotation(rotation);
          }**/
        
          }
       
        }
      
      }
    };
    
    app.getRootNode().depthFirstTraversal(sgv);   
    
  }
  
  private Node findHouseParent(Spatial spatial) {
      
    Node house = new Node();
    
    if (spatial.getParent() instanceof Node)
        house = spatial.getParent();
    
    else {
        System.out.println(spatial.getParent().getName() + " Isn't a house");    
        findHouseParent(spatial.getParent());
    }
    
    return house;
  
  }
  
}
