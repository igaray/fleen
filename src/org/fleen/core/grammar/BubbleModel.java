package org.fleen.core.grammar;

import java.io.Serializable;

import org.fleen.core.kGeom.DVectorRD;
import org.fleen.core.kGeom.DVectorRDPath;
import org.fleen.core.kGeom.DVertexPath;

/*
 * A loop of vectors describing a loop of vertices in a diamond grid in a 
 *    location, scale and orientation independent way.
 *    
 * Our vectors are RVector(directiondelta,distanceproportion)
 * 
 * directiondelta is in terms of prior direction. The relative directional offset. Left turn is negative, right
 *    turn is positive. Ultimately dependent upon vector0.direction which is specified in the bubble
 *    that uses this model.
 *    For example : If a vector's directiondelta is -3 then the direction describes a 90 degree left turn
 *        relative to the vector just before it.
 *    
 * distanceproportion is in terms of vector0.distance in the bubble that uses this model.
 *    For example : If a vector's distanceproportion is 2.0 then it's length is twice that of vector0.
 * 
 * vector0 is undefined within this model. It is defined within the bubble that uses this model.
 * Defining vector0 for this model gives us a loop of actual vertices on a grid. 
 * 
 * When deriving the actual vertices for the bubble that uses this model, if the bubble's twist is negative  
 *    then reverse the signs on all the directiondeltas 
 *    
 *    
 * baselength is the hypothetical distance from v0 to v1 on a diamondgrid if fish is 1.0
 * we compare it with the distance from v0 to v1 on the bubble to accquire local fish
 *
 */
public class BubbleModel implements Serializable{

  /*
   * ################################
   * STATIC FIELDS
   * ################################
   */
  
  private static final long serialVersionUID=6534312053810670170L;
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public BubbleModel(String id,DVectorRDPath vectorpath){
    setID(id);
    setVectorPath(vectorpath);}
  
  public BubbleModel(String id,DVertexPath vertexpath){
    this(id,new DVectorRDPath(vertexpath));}
  
  public BubbleModel(){}
  
  /*
   * ################################
   * ID 
   * ################################
   */
  
  public String id="";//unique within grammar
  
  public void setID(String a){
    if(a==null)return;
    id=a;}
  
  /*
   * ################################
   * VECTORS 
   * ################################
   */
  
  private DVectorRD[] vectors; 
  
  public void setVectorPath(DVectorRDPath v){
    vectors=v.toArray(new DVectorRD[v.size()]);}
  
  public DVectorRDPath getVectorPath(){
    return new DVectorRDPath(vectors);}
  
  public DVectorRD getVector(int index){
    return vectors[index];}
  
  public int getVectorCount(){
    return vectors.length;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return id;}
  
}
