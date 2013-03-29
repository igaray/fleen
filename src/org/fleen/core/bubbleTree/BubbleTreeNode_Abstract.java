package org.fleen.core.bubbleTree;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/*
 * a treenode basically
 * implemented by Bubble, Grid and Foam
 */
public abstract class BubbleTreeNode_Abstract implements Serializable{

  private static final long serialVersionUID=6049549726585045831L;
  
  /*
   * ################################
   * PARENT
   * ################################
   */
  
  private BubbleTreeNode_Abstract parent=null;
  
  public BubbleTreeNode_Abstract getParent(){
    return parent;}
  
  public void setParent(BubbleTreeNode_Abstract n){
    parent=n;}
  
  /*
   * ################################
   * CHILDREN
   * ################################
   */
  
  BubbleTreeNode_Abstract[] children=new BubbleTreeNode_Abstract[]{};

  public List<BubbleTreeNode_Abstract> getChildren(){
    return Arrays.asList(children);}
  
  public BubbleTreeNode_Abstract getChild(int index){
    if(index<children.length)
      return children[index];
    else 
      return null;}

  public int getChildCount(){
    return children.length;}
  
  public void setChildren(List<BubbleTreeNode_Abstract> c){
    children=c.toArray(new BubbleTreeNode_Abstract[c.size()]);}
  
  /*
   * ################################
   * ANALYSIS
   * ################################
   */
  
  public boolean isRoot(){
    return parent==null;}
  
  public boolean isLeaf(){
    return children.length==0;}
  
  /**
   * @return The number of bubbles encountered when traversing the tree from this node to the root.
   */
  public int getBubbleLevel(){
    int c=0;
    BubbleTreeNode_Abstract n=this;
    while(n!=null){
      n=getFirstAncestorBubble();
      if(n!=null)c++;}
    return c;}
  
  /**
   * @return The number of foams encountered when traversing the tree from this node to the root.
   */
  public int getFoamLevel(){
    int c=0;
    BubbleTreeNode_Abstract n=this;
    while(n!=null){
      n=getFirstAncestorFoam();
      if(n!=null)c++;}
    return c;}
  
  /**
   * @return The number of grids encountered when traversing the tree from this node to the root.
   */
  public int getGridLevel(){
    int c=0;
    BubbleTreeNode_Abstract n=this;
    while(n!=null){
      n=getFirstAncestorGrid();
      if(n!=null)c++;}
    return c;}
  
  /*
   * ################################
   * RELATIVE ACCESS
   * for our convenience
   * so far we just have these first ancestor methods
   * maybe more later
   * ################################
   */
  
  public Bubble getFirstAncestorBubble(){
    BubbleTreeNode_Abstract n=getParent();
    while(!(n instanceof Bubble)){
      if(n==null)return null;
      n=n.getParent();}
    return (Bubble)n;}
  
  public Foam getFirstAncestorFoam(){
    BubbleTreeNode_Abstract n=getParent();
    while(!(n instanceof Foam)){
      if(n==null)return null;
      n=n.getParent();}
    return (Foam)n;}
  
  public Grid getFirstAncestorGrid(){
    BubbleTreeNode_Abstract n=getParent();
    while(!(n instanceof Grid)){
      if(n==null)return null;
      n=n.getParent();}
    return (Grid)n;}

}
