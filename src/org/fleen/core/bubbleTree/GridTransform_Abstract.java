package org.fleen.core.bubbleTree;

import org.fleen.core.g2D.G2D;
import org.fleen.core.gKis.KVertex;

/*
 * One way or another we glean the params of this transform
 * We might do params, we might derive from an ancestor bubble 
 * We might do a little of both (like we do in pGrammatic), 
 */
public abstract class GridTransform_Abstract extends BubbleTreeNode implements GridStackElement{
  
  private static final long serialVersionUID=-220936433111379307L;
  
  
  /*
   * ################################
   * ORIGIN TRANSFORM
   * a kvertex relative to origin of prior element 
   * ################################
   */
  
  public abstract KVertex getOriginTransform();
  
  /*
   * ################################
   * FOREWARD TRANSFORM
   * a direction offset in terms of the foreward and twist of the prior element 
   * ################################
   */
  
  public abstract int getForewardTransform();
  
  /*
   * ################################
   * TWIST TRANSFORM
   * a boolean. Either same twist as prior element (true, aka 'positive') or opposite (false, aka 'negative').
   * ################################
   */
  
  public abstract boolean getTwistTransform();
  
  /*
   * ################################
   * FISH TRANSFORM
   * a factor. Simply multiply by fish of prior element
   * ################################
   */
  
  public abstract double getFishTransform();
  
  /*
   * ################################
   * GRID
   * ################################
   */
  
  private static final double DIRECTION12UNIT=G2D.PI*2.0/12.0;
  
  //TODO cache + a purge method
  //for bubble too
  public Grid getGrid(){
    Grid priorgrid=getFirstAncestorGridStackElement().getGrid();
    double[] gridorigin=priorgrid.getPoint2D(getOriginTransform());
    double gridforeward=G2D.normalizeDirection(priorgrid.getForeward()+getForewardTransform()*DIRECTION12UNIT);
    boolean gridtwist=priorgrid.getTwist()&&getTwistTransform();
    double gridfish=priorgrid.getFish()*getFishTransform();
    return new Grid(
      gridorigin,
      gridforeward,
      gridtwist,
      gridfish);}

}
