package org.fleen.core.bubbleTree;

import java.util.ArrayList;

/*
 * This is a crawler-collector for the bubbletree 
 * We address every node in the tree rooted at the specified root, conditionally gathering
 * our gathering logic is at the abstract method doGather(Node)
 */
@SuppressWarnings("serial")
public abstract class NodeGatherer_Abstract extends ArrayList<BubbleTreeNode_Abstract>{
  
  BubbleTreeNode_Abstract root,wormhead=null,wormtail=null;
  
  public NodeGatherer_Abstract(BubbleTreeNode_Abstract root){
    this.root=root;
    //if root is a leaf
    if(root.isLeaf()){
      add(root);
      return;}
    //
    wormhead=root;
    while(!finished()){
      if(doGather(wormhead))add(wormhead);
      advanceWorm();}}
  
  protected abstract boolean doGather(BubbleTreeNode_Abstract n);
  
  private boolean finished(){
    return wormhead==root&&wormtail.getSiblingIndex()==root.getChildCount()-1;}
  
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
