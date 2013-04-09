package org.fleen.core.pGrammatic;

import java.util.ArrayList;
import java.util.List;

import org.fleen.core.bubbleTree.Bubble_Abstract;


/*
 * The ancestry of a bubble in terms of bubblemodel ids and chorus indices
 * It's a way of distinguishing bubbles in a contextual way
 * We use it for controlling symmetry
 * It is immutable
 */
public class BubbleSignature{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public BubbleSignature(GBubble bubble){
    Bubble_Abstract pb=bubble.getParentBubble();
    if(pb!=null){
      BubbleSignature s0=bubble.getParentBubble().getSignature();
      components.addAll(s0.components);}
    components.add(0,new SigComponent(bubble));
    
//    Bubble b=bubble;
//    while(b!=null){
//      components.add(new SigComponent(b));
//      b=b.getParentBubble();}
    }
  
  /*
   * ################################
   * SIGNATURE DATA
   * ################################
   */
  
  private List<SigComponent> components=new ArrayList<SigComponent>();
  
  private class SigComponent{
    
    String bubblemodelid;
    int chorusindex;
    
    public SigComponent(GBubble b){
      bubblemodelid=b.model.id;
      chorusindex=b.chorusindex;}
    
    public boolean equals(Object a){
      SigComponent b=(SigComponent)a;
      boolean e=
        b.bubblemodelid.equalsIgnoreCase(bubblemodelid)&&
        b.chorusindex==chorusindex;
      return e;}}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public Integer hashcode=null;
  
  public int hashCode(){
    if(hashcode==null)initHashCode();
    return hashcode;}
  
  //sum of component chorus indices
  private void initHashCode(){
    hashcode=new Integer(0);
    for(SigComponent c:components)
      hashcode+=c.chorusindex;}
  
  public boolean equals(Object a){
    BubbleSignature s0=(BubbleSignature)a;
    int 
      c0=s0.components.size(),
      c1=components.size();
    if(c0!=c1)return false;
    SigComponent g0,g1;
    for(int i=0;i<c0;i++){
      g0=s0.components.get(i);
      g1=components.get(i);
      if(!g0.equals(g1))return false;}
    return true;}
  
  private String objectstring=null;
  
  public String toString(){
    if(objectstring==null)initObjectString();
    return objectstring;}
  
  private void initObjectString(){
    objectstring="BubbleSignature[";
    int s=components.size()-1;
    for(int i=0;i<s;i++)
      objectstring+=components.get(i)+",";
    objectstring+=components.get(s)+"]";}
  
}
