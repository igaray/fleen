package org.fleen.core.diamondGrammar;

import java.io.Serializable;


/*
 * diamond vector with relative direction
 * direction and distance
 * direction is integer in range [-5,5].
 * distance is double. A sum of integers (1s and 2s) or integer multiples of sqrt3.
 */
public class DVectorRD implements Serializable{
  
  private static final long serialVersionUID=1794434316990821717L;

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public DVectorRD(){}
  
  public DVectorRD(int direction,double distance){
    this.directiondelta=direction;
    this.distance=distance;}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public int directiondelta;
  public double distance;
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public boolean equals(Object a){
    DVectorRD b=(DVectorRD)a;
    return directiondelta==b.directiondelta&&distance==b.distance;}
  
  public int hashCode(){
    int a=directiondelta*65536+(int)distance;
    return a;}
  
  public String toString(){
    String s="["+directiondelta+","+distance+"]";
    return s;}
  
}
