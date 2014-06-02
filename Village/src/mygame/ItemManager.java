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
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class ItemManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private Node              scene;
  public  Node              itemNode;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    scene             = stateManager.getState(SceneManager.class).scene;
    itemNode          = new Node();
    this.app.getRootNode().attachChild(itemNode);
    itemNode.setName("ItemNode");
    initItems();
    }
  
  private void initItems() {
    Node shroomNode = (Node) scene.getChild("Mushrooms");
    Node spadeNode  = (Node) scene.getChild("Shovel");
    Node axeNode    = (Node) scene.getChild("Axe");
    Node tableNode  = (Node) scene.getChild("Table");
    Node treeNode   = (Node) assetManager.loadModel("Models/tree/tree.j3o");
    Material mat    = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    Material mat1   = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.Green);
    mat1.setColor("Color", ColorRGBA.Brown);
    
    Node treeModel = (Node) treeNode.getChild(0);
    treeModel.getChild(0).setMaterial(mat);
    treeModel.getChild(1).setMaterial(mat1);
    
    treeNode.setName("Tree");
    shroomNode.setName("Mushrooms");
    spadeNode.setName("Shovel");
    axeNode.setName("Axe");
    tableNode.setName("Table");
    itemNode.attachChild(shroomNode);
    itemNode.attachChild(spadeNode);
    itemNode.attachChild(axeNode);
    itemNode.attachChild(tableNode);
    itemNode.attachChild(treeNode);
    treeNode.setLocalTranslation(5, 0, 10);
    }
  
  }
