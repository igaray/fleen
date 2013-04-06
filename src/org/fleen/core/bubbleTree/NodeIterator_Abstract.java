package org.fleen.core.bubbleTree;

import java.util.Iterator;

/*
 * This is a crawler-collector for the bubbletree 
 * We address every node in the tree rooted at the specified root, conditionally gathering
 * our gathering logic is at the abstract method doGather(Node)
 */
public abstract class NodeIterator_Abstract implements Iterator<BubbleTreeNode>{
  
  BubbleTreeNode 
    root,
    wormhead,
    wormtail=null;
  
  public NodeIterator_Abstract(BubbleTreeNode root){
    this.root=root;
    wormhead=root;
    if(!filter(wormhead))gleanNextNode();}
  
  public boolean hasNext(){
    return wormhead!=null;}

  public BubbleTreeNode next(){
    BubbleTreeNode n=wormhead;
    gleanNextNode();
    return n;}

  //nope
  public void remove(){}
  
  public void gleanNextNode(){
    if(wormhead==null)return;
    do{
      advanceWorm();
      testFinished();
      if(filter(wormhead))return;
    }while(wormhead!=null);}
  
  /**
   * @param node The node getting filtered
   * @return true if node passes the filter, false if it is rejected.
   */
  protected abstract boolean filter(BubbleTreeNode node);
  
  //if we're finished then set wormhead to null
  private void testFinished(){
    if(wormhead==root&&wormtail.isLastSibling())
      wormhead=null;}
  
  private void advanceWorm(){
    //if we just started
    if(wormhead==root&&wormtail==null){
      wormhead=root.getChild(0);
      wormtail=root;
    //we're in mid traverse
    }else{
      //if the worm is pointing up
      if(wormhead==wormtail.getParent()){
        //if we just left the last sibling then go up 
        if(wormtail.isLastSibling()){
          wormtail=wormhead;
          wormhead=wormhead.getParent();
        //otherwise flip the worm down and address the next sibling
        }else{
          wormhead=wormtail.getNextSibling();
          wormtail=wormhead.getParent();}
      //the worm is pointing down
      }else{
        //if we hit a leaf then flip the worm up
        if(wormhead.isLeaf()){
          wormtail=wormhead;
          wormhead=wormhead.getParent();
        //head on down
        }else{
          wormhead=wormhead.getChild(0);
          wormtail=wormhead.getParent();}}}}
  
}
