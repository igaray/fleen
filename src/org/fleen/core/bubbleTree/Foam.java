package org.fleen.core.bubbleTree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import org.fleen.core.grammar.GBubble;


/*
 * A Foam is 1..n contiguous masses of bubbles. These masses may be pierced.
 * parents enclose children, and we can have multiple levels of nesting
 * a foam is a node in the bubbletree. It implements bubbletreeelement.
 * 
 * Every bubble has a foam. Is part of a foam. A Bubble's foam is the first nonnull Bubble.foam in it's ancestry.
 * 
 * A foam has 1..n Bubbles. Is composed of Bubbles. A Foam's Bubbles are all Bubbles in the branch/es rooted
 * at the Foam's root Bubble/s - up to the Bubbles with nonnull Foams, then a new Foam starts. This new Foam
 * is a child of this Foam. 
 */
public class Foam extends BubbleTreeNode_Abstract{
  
  private static final long serialVersionUID=-128342895299754440L;
  
  
  
  
  /*
   * ################################
   * TREENODE STUFF
   * ################################
   */
  
  public Foam parent=null;
  //a foam has 0..n children
  //the first bubble describes the outer edge of the foam
  public ArrayList<Foam> children=new ArrayList<Foam>();
  
  public Enumeration<Foam> children(){
    return new ChildEnumeration();}
  
  //START CHILD ENUMERATION CLASS
  private class ChildEnumeration implements Enumeration<Foam>{
    int index=0;
    public boolean hasMoreElements(){
      return index<children.size();}
    public Foam nextElement(){
      index++;
      return children.get(index-1);}
  }//END CHILD ENUMERATION CLASS

  public boolean getAllowsChildren(){
    return true;}

  public Foam getChildAt(int i){
    return children.get(i);}

  public int getChildCount(){
    return children.size();}

  public int getIndex(TreeNode a){
    return children.indexOf(a);}

  public Foam getParentBubble(){
    return parent;}

  public boolean isLeaf(){
    return children.isEmpty();}
  
  public boolean isRoot(){
    return parent==null;}
  
  public int getLevel(){
    int l=0;
    Foam f=this;
    while(!f.isRoot()){
      l++;
      f=f.parent;}
    return l;}
  
  /*
   * ################################
   * BUBBLES
   * A foam contains 1..n bubbles
   * ################################
   */
  
  private ArrayList<GBubble> bubbles=new ArrayList<GBubble>(0);
  
  public void addBubble(GBubble b){
    bubbles.add(b);
    bubbles.trimToSize();}//TODO inefficient? do an array and copy thing?
  
}
