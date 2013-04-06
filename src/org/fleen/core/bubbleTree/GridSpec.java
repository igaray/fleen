package org.fleen.core.bubbleTree;

public class GridSpec{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public GridSpec(double[] origin,double foreward,boolean twist,double fish){
    this.origin=origin;
    this.foreward=foreward;
    this.twist=twist;
    this.fish=fish;}
  
  /*
   * ################################
   * ORIGIN
   * coordinates of kisrhimbille grid origin in 2d space
   * ################################
   */
  
  double[] origin;
  
  public double[] getOrigin(){
    return origin;}
  
  /*
   * ################################
   * FOREWARD
   * foreward direction in 2d space
   * ################################
   */
  
  double foreward;
  
  public double getForeward(){
    return foreward;}
  
  /*
   * ################################
   * TWIST
   * direction of indexing for last coordinate in k space coordinates. 
   * Positive (clockwise) or negative (counterclockwise).
   * ################################
   */
  
  boolean twist;
  
  public boolean getTwist(){
    return twist;}
  
  /*
   * ################################
   * FISH
   * smallest of our 3 fundamental intervals in a k grid. Also the scale.
   * ################################
   */
  
  double fish;
  
  public double getFish(){
    return fish;}

}
