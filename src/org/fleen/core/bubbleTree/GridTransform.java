package org.fleen.core.bubbleTree;

import org.fleen.core.gKis.KVertex;

public class GridTransform implements GridStackElementParam{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public GridTransform(KVertex origintransform,int forewardtransform,boolean twisttransform,double fishtransform){
    this.origintransform=origintransform;
    this.forewardtransform=forewardtransform;
    this.twisttransform=twisttransform;
    this.fishtransform=fishtransform;}
  
  /*
   * ################################
   * ORIGIN TRANSFORM
   * a kvertex relative to origin of prior element 
   * ################################
   */
  
  KVertex origintransform=null;
  
  public KVertex getOriginTransform(){
    return origintransform;}
  
  /*
   * ################################
   * FOREWARD TRANSFORM
   * a direction offset in terms of the foreward and twist of the prior element 
   * ################################
   */
  
  int forewardtransform=0;
  
  public int getForewardTransform(){
    return forewardtransform;}
  
  /*
   * ################################
   * TWIST TRANSFORM
   * a boolean. Either same twist as prior element (true, aka 'positive') or opposite (false, aka 'negative').
   * ################################
   */
  
  boolean twisttransform=true;
  
  public boolean getTwistTransform(){
    return twisttransform;}
  
  /*
   * ################################
   * FISH TRANSFORM
   * a factor. Simply multiply by fish of prior element
   * ################################
   */
  
  double fishtransform=1.0;
  
  public double getFishTransform(){
    return fishtransform;}

}
