package org.fleen.core.diamondGrammar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * The grammar is a collection of BubbleModels and Jigs
 * The BubbleModels are the operands and the Jigs are the operators
 * Each BubbleModel has a unique id, Each Jig has a unique id and the unique id of the BubbleModel
 * (or rather, the Bubbles with that BubbleModel) with which they are compatible.
 *  
 */
public class Grammar implements Serializable{
 
  /*
   * ################################
   * STATIC FIELDS
   * ################################
   */
  
  private static final long serialVersionUID=2305187247493194046L;
  
  /*
   * ################################
   * MODELS AND JIGS DATA
   * ################################
   */
  
  public List<ModelAndJigs> modelsandjigs=new ArrayList<ModelAndJigs>();
  
  //CLASS MODELANDJIGS
  //here we store a bubblemodel and a list of jigs associatedly
  public class ModelAndJigs implements Serializable{
    
    private static final long serialVersionUID=1550692588018186240L;
    
    public ModelAndJigs(BubbleModel model){
      this.model=model;}
    
    public BubbleModel model;
    public List<Jig> jigs=new ArrayList<Jig>();}
  //
  
  private ModelAndJigs getModelAndJigs(String modelid){
    for(ModelAndJigs a:modelsandjigs)
      if(a.model.id.equals(modelid))
        return a;
    return null;}
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Grammar(){}

  /*
   * ################################
   * BUBBLE MODELS
   * ################################
   */
  
  public List<BubbleModel> getBubbleModels(){
    List<BubbleModel> a=new ArrayList<BubbleModel>();
    for(ModelAndJigs b:modelsandjigs)
      a.add(b.model);
    return a;}
  
  /*
   * Return the BubbleModel specified by id
   * Returns null if no such BubbleModel exists within this Grammar.
   */
  public BubbleModel getBubbleModel(String modelid){
    ModelAndJigs a=getModelAndJigs(modelid);
    if(a==null)
      return null;
    return a.model;}
  
  /*
   * Add the specified BubbleModel to this Grammar
   * Returns true on success
   * Returns false if that BubbleModel was not actually added (it's id was already in use)
   */
  public boolean addBubbleModel(BubbleModel bubblemodel){
    BubbleModel m=getBubbleModel(bubblemodel.id);
    if(m==null){
      modelsandjigs.add(new ModelAndJigs(bubblemodel));
      return true;
    }else{
      return false;}}
  
  /*
   * Remove the specified BubbleModel and it's associated list of Jigs from this Grammar
   * Returns true on success
   * Returns false if that BubbleModel was not actually removed (no such BubbleModel id found)
   */
  public boolean removeBubbleModel(String modelid){
    Iterator<ModelAndJigs> i=modelsandjigs.iterator();
    ModelAndJigs a;
    while(i.hasNext()){
      a=i.next();
      if(a.model.id.equals(modelid)){
        i.remove();
        return true;}}
    return false;}
  
  public boolean containsBubbleModel(String modelid){
    for(ModelAndJigs a:modelsandjigs)
      if(a.model.id.equals(modelid))
        return true;
    return false;}
  
  /*
   * ################################
   * JIGS
   * ################################
   */
  
  /*
   * Return the specified Jig for the specified BubbleModel
   * Returns null if there is no such Bubblemodel or no such Jig.
   */
  public Jig getJig(String modelid,String jigid){
    ModelAndJigs a=getModelAndJigs(modelid);
    if(a==null){
      return null;
    }else{
      for(Jig b:a.jigs)
        if(b.id.equals(jigid))
          return b;
      return null;}}
  
  /*
   * Return the list of Jigs associated with the BubbleModel specified by id
   * Returns null if a BubbleModel with the specified id does not exist within this Grammar
   * Returns empty list if the specified BubbleModel has no Jigs 
   */
  public List<Jig> getJigs(String modelid){
    ModelAndJigs a=getModelAndJigs(modelid);
    if(a==null){
      return null;
    }else{
      return new ArrayList<Jig>(a.jigs);}}
  
  /*
   * Add the specified Jig to the list of Jigs for the specified BubbleModel
   * Returns true on success
   * Returns false if the operation failed because either the specified BubbleModel does not exist or
   * the Jig's id was already in use.
   */
  public boolean addJig(String modelid,Jig jig){
    ModelAndJigs a=getModelAndJigs(modelid);
    if(a==null)return false;
    return a.jigs.add(jig);}
  
  /*
   * Remove the specified Jig from the jiglist for the specified bubblemodel
   * Returns true on success
   * Returns false if no such bubblemodel exists of no such jig exists
   */
  public boolean removeJig(String modelid,String jigid){
    ModelAndJigs a=getModelAndJigs(modelid);
    if(a==null)return false;
    Iterator<Jig> i=a.jigs.iterator();
    Jig b;
    while(i.hasNext()){
      b=i.next();
      if(b.id.equals(jigid)){
        i.remove();
        return true;}}
    return false;}
  
  /*
   * Remove all Jigs associated with the specified BubbleModel
   * Returns true on success
   * Returns false if no such bubblemodel exists or the bubblemodel has no jigs
   */
  public boolean removeJigs(String modelid){
    ModelAndJigs a=getModelAndJigs(modelid);
    if(a==null)return false;
    if(a.jigs.isEmpty())return false;
    a.jigs.clear();
    return true;}
  
  /*
   * returns the number of jigs associated with the specified bubblemodel
   * returns -1 if no such bubblemodel exists with that id
   */
  public int getJigCount(String modelid){
    ModelAndJigs a=getModelAndJigs(modelid);
    if(a==null)return -1;
    return a.jigs.size();}
  
}
