package org.fleen.core.bubbleTree;

/*
 * The grid stack is a grid specification followed by 0..n grid transforms
 * A grid stack element has a param : either a GridSpec  or a GridTransform
 * At each grid stack element we may get the stack summation. 
 *   That is, the root GridSpec + all involved GridTransforms
 */
public interface GridStackElement{
  
  /*
   * ################################
   * LOCAL PARAM
   * ################################
   */
  
  GridStackElementParam getGSEParam();
  
  void setGSEParam(GridStackElementParam gseparam);
  
  /*
   * ################################
   * GRID STACK SUMMATION
   * These 4 methods give us our grid defining parameters summed for the stack from the root to this node
   * At the root this would just be 4 params.
   * Elsewhere it would be the root params transformed through 1..n grid transforms
   * ################################
   */
  
  //coordinates of kisrhimbille grid origin in 2d space
  double[] getStackOrigin();
  
  //foreward direction in 2d space
  double getStackForeward();
  
  //direction of indexing for last coordinate in k space coordinates. Positive (clockwise) or negative (counterclockwise).
  boolean getStackTwist();
  
  //smallest of our 3 fundamental intervals in a k grid. Also the scale.
  double getStackFish();
  
}
