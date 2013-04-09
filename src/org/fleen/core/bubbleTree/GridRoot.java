package org.fleen.core.bubbleTree;

public class GridRoot extends BubbleTreeNode implements GridStackElement{
  
  private static final long serialVersionUID=-6976402647903611804L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public GridRoot(double[] origin,double foreward,boolean twist,double fish){
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
  
  /*
   * ################################
   * GRID
   * ################################
   */
  
  public Grid getGrid(){
    return new Grid(origin,foreward,twist,fish);}

}
