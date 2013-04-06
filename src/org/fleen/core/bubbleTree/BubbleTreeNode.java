package org.fleen.core.bubbleTree;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*
 * a treenode basically
 * implemented by Bubble, Grid and Foam
 */
public class BubbleTreeNode implements Serializable{

  private static final long serialVersionUID=6049549726585045831L;
  
  /*
   * ################################
   * PARENT
   * ################################
   */
  
  BubbleTreeNode parent=null;
  
  public BubbleTreeNode getParent(){
    return parent;}
  
  public void setParent(BubbleTreeNode n){
    parent=n;}
  
  /*
   * ################################
   * CHILDREN
   * ################################
   */
  
  BubbleTreeNode[] children=new BubbleTreeNode[]{};

  public List<BubbleTreeNode> getChildren(){
    return Arrays.asList(children);}
  
  public BubbleTreeNode getChild(int index){
    if(index<children.length)
      return children[index];
    else 
      return null;}

  public int getChildCount(){
    return children.length;}
  
  public void setChildren(List<BubbleTreeNode> c){
    children=c.toArray(new BubbleTreeNode[c.size()]);}
  
  /*
   * ################################
   * ANALYSIS
   * ################################
   */
  
  int childindex=-1;
  
  public boolean isRoot(){
    return parent==null;}
  
  public boolean isLeaf(){
    return children.length==0;}
  
  /**
   * @return The index of this node within it's parent's child array. 
   *  Exception if this node has no parent (is root).
   *  Exception if parent array does not hold a reference to this node.
   */
  public int getSiblingIndex(){
    if(parent==null)
      throw new IllegalArgumentException("This node has no parent");
    //init. we cache. efficient.
    if(childindex==-1){
      SEEK:for(childindex=0;childindex<parent.children.length;childindex++)
        if(parent.children[childindex].equals(this))
          break SEEK;
    throw new IllegalArgumentException("Parent's child array holds no reference to this node");}
    return childindex;}
  
  /**
   * @return The next child (ascending indexwise) in this node's parent's child array. 
   * null if there is no next child. 
   * Exception if this node has no parent.
   */
  public BubbleTreeNode getNextSibling(){
    if(parent==null)
      throw new IllegalArgumentException("This node has no parent");
    return parent.getChild(getSiblingIndex()+1);}
  
  public boolean isLastSibling(){
    return getSiblingIndex()==getParent().getChildCount()-1;}
  
  /**
   * @return This number of nodes encountered when traversing the tree from this node to the root.
   */
  public int getDepth(){
    int c=0;
    BubbleTreeNode n=this;
    while(n!=null){
      n=n.getParent();
      if(n!=null)c++;}
    return c;}
  
  /**
   * @return The number of bubbles encountered when traversing the tree from this node to the root.
   */
  public int getBubbleDepth(){
    int c=0;
    BubbleTreeNode n=this;
    while(n!=null){
      n=n.getFirstAncestorBubble();
      if(n!=null)c++;}
    return c;}
  
  /**
   * @return The number of foams encountered when traversing the tree from this node to the root.
   */
  public int getFoamDepth(){
    int c=0;
    BubbleTreeNode n=this;
    while(n!=null){
      n=n.getFirstAncestorFoam();
      if(n!=null)c++;}
    return c;}
  
  /**
   * @return The number of grids encountered when traversing the tree from this node to the root.
   */
  public int getGridDepth(){
    int c=0;
    BubbleTreeNode n=this;
    while(n!=null){
      n=n.getFirstAncestorGrid();
      if(n!=null)c++;}
    return c;}
  
  /*
   * ################################
   * RELATIVE NODE ACCESS
   * ################################
   */
  
  public Bubble_Abstract getFirstAncestorGridStackElement(){
    BubbleTreeNode n=getParent();
    while(!(n instanceof GridStackElement)){
      if(n==null)return null;
      n=n.getParent();}
    return (Bubble_Abstract)n;}
  
  public Bubble_Abstract getFirstAncestorBubble(){
    BubbleTreeNode n=getParent();
    while(!(n instanceof Bubble_Abstract)){
      if(n==null)return null;
      n=n.getParent();}
    return (Bubble_Abstract)n;}
  
  public Foam getFirstAncestorFoam(){
    BubbleTreeNode n=getParent();
    while(!(n instanceof Foam)){
      if(n==null)return null;
      n=n.getParent();}
    return (Foam)n;}
  
  public Grid getFirstAncestorGrid(){
    BubbleTreeNode n=getParent();
    while(!(n instanceof Grid)){
      if(n==null)return null;
      n=n.getParent();}
    return (Grid)n;}
  
  /**
   * @return Leaves of the branch rooted at this node
   * The leaves in a bubbletree are, as a rule, bubbles
   */
  public Iterator<? extends BubbleTreeNode> getLeafIterator(){
    return new NodeIterator_Abstract(this){
      protected boolean filter(BubbleTreeNode node){
        return node.isLeaf();}};}
  
  /**
   * @return Bubbles in the branch rooted at this node
   */
  public Iterator<? extends BubbleTreeNode> getBubbleIterator(){
    return new NodeIterator_Abstract(this){
      protected boolean filter(BubbleTreeNode node){
        return node instanceof Bubble_Abstract;}};}
  
  /**
   * @return Foams in the branch rooted at this node
   */
  public Iterator<? extends BubbleTreeNode> getFoamIterator(){
    return new NodeIterator_Abstract(this){
      protected boolean filter(BubbleTreeNode node){
        return node instanceof Foam;}};}
  
  /**
   * @return Grids in the branch rooted at this node
   */
  public Iterator<? extends BubbleTreeNode> getGridIterator(){
    return new NodeIterator_Abstract(this){
      protected boolean filter(BubbleTreeNode node){
        return node instanceof Grid;}};}
  
}
