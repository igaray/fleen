package org.fleen.core.bubbleTree;

import java.util.ArrayList;
import java.util.List;

import org.fleen.core.grammar.GBubble;

/*
 * Conveniently manages a tree of bubbles and grids
 * various concrete and abstract methods for searching the tree, accessing grids and bubbles, cultivating.
 */
public class BubbleTree{
  
  /*
   * ################################
   * GRIDS, BUBBLES AND FOAMS
   * Grids and Bubbles are arranged in a tree alternatinglywise
   * Foams are groups of bubbles, also in a tree
   * Child bubbles are derived from parent bubbles via Jigs
   * Parent foams encompass child foams geometrically : like a pond encompasses a raft
   * 
   * TODO 
   * I'm thinking that this class will be a central addressing service for all elements in the tree
   * bubbles, grids and foams
   * it will get updated at every creation/assignation step
   * it's service will be speed
   * 
   * otherwise we really don't need this class. we could just refer to everything through a root grid
   * ################################
   */
  
  Grid rootgrid=null;
  
  public void setRootGrid(Grid g){
    rootgrid=g;}
  
  public Grid getRootGrid(){
    return rootgrid;}
  
  /*
   * we usaually have just 1 bubble on the root grid, thus a root bubble
   * if we have more than 1 root bubble then probably isn't so useful
   */
  public GBubble getRootBubble(){
    return rootgrid.getChildBubbles().get(0);}
  
  public List<GBubble> getBubbles(){
    List<GBubble> bubbles=new ArrayList<GBubble>();
    for(GBubble b:rootgrid.childbubbles)
      bubbles.addAll(b.getBranchBubbles());
    return bubbles;}
  
//  class BubbleCollector{
//    
//    BubbleCollector(DNode n,List<Bubble> bubbles){
//      if(n instanceof Bubble)bubbles.add((Bubble)n);
//      @SuppressWarnings("unchecked")
//      Enumeration<DNode> e=n.children();
//      while(e.hasMoreElements())
//        new BubbleCollector(e.nextElement(),bubbles);}}
  
  public List<GBubble> getLeaves(){
    List<GBubble> b=getBubbles();
    List<GBubble> leaves=new ArrayList<GBubble>();
    for(GBubble a:b)
      if(a.isLeaf())
        leaves.add(a);
    return leaves;}
 
}