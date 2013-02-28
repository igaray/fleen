package org.fleen.core.diamondGrammar;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;


/*
 * a contiguous system of bubbles
 * parents enclose children, and we can multiple levels of nesting of course
 * a foam is a node in a tree of foams
 */
public class Foam implements DNode{
  
  //serialization
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

  public Foam getParent(){
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
  
  private ArrayList<Bubble> bubbles=new ArrayList<Bubble>(0);
  
  public void addBubble(Bubble b){
    bubbles.add(b);
    bubbles.trimToSize();}//TODO inefficient? do an array and copy thing?
  
  /*
   * ################################
   * TAGS
   * Identified by key string.
   * Either they're there or they're not
   * totally mutable
   * ################################
   */
  
//  private List<String> tags=new ArrayList<String>();
//  
//  public void addTag(String tag){
//    tags.add(tag);}
//  
//  public boolean removeTag(String tag){
//    return tags.remove(tag);}
//  
//  public boolean hasTag(String tag){
//    return tags.contains(tag);}
  
}
