package org.fleen.core.bubbleTree;

import java.util.ArrayList;
import java.util.List;

import org.fleen.core.g2D.DGeom;
import org.fleen.core.gKis.DVertex;
import org.fleen.core.gKis.KGeom;

/*
 * This is a kisrhombille grid coordinate system
 * it's parameters are :
 * twist : rotary direction of direction indexing
 * fish : basic interval
 * origin : 0,0,0,0
 * foreward : the real 2d direction at direction index 0
 * 
 * We define it in terms either absolute (2d geom) or relative (ancestry of bubbles and grids)
 * 
 */

public class Grid extends BubbleTreeNode_Abstract{
  
  private static final long serialVersionUID=-7272582675212521562L;

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  /* 
   * Define the grid in relative terms
   * Created in diamond tree cultivation process
   * specify origin in terms of grandparent grid
   * derive foreward,fish and twist from ancestry 
   */
  public Grid(double fishfactor){
    this.fishfactor=fishfactor;}

  /*
   * Define the grid in absolute terms
   * A root needs absolute terms because it has no parents from which to derive relative terms
   */
  public Grid(double[] origin,double forward,double fish,boolean twist){
    originabs=origin;
    forwardabs=forward;
    fishabs=fish;
    twistabs=twist;}
  
  /*
   * Define the grid in default absolute terms
   * we use this for a default root grid
   */
  public Grid(){
    this(
      DEFAULT_ROOT_ORIGIN_2D,
      DEFAULT_ROOT_FOREWARD,
      DEFAULT_ROOT_FISH,
      DEFAULT_ROOT_TWIST);}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  //DEFAULT ROOT PARAM CONSTANTS
  private static final double[] DEFAULT_ROOT_ORIGIN_2D={0,0};
  private static final double DEFAULT_ROOT_FOREWARD=0;
  private static final double DEFAULT_ROOT_FISH=1.0;
  private static final boolean DEFAULT_ROOT_TWIST=true;
  
  //GRID DEFINED IN ABSOLUTE TERMS
  //the basic interval in the diamond coordinate system : fish
  public double fishabs;
  //the origin for our diamond grid in 2d geom terms
  public double[] originabs=null;
  //foreward (direction==0) for our diamond grid in 2d geom terms
  public double forwardabs;
  //we have 2 mirroring possibilities here
  //true means positive twist : direction indices go clockwise
  //false means negative twist : direction indices go counterclockwise
  public boolean twistabs;
  
  //GRID DEFINED IN RELATIVE TERMS
  //foreward and origin are derived from parent bubble
  //origin : v0
  //foreward : dir(v0,v1)
  //the value of fish in this grid in terms of parentbubble.grid.fish
  //it is a fraction. 1/integer.
  public double fishfactor;
  
  /*
   * Either we use the param or we derive directly from parentbubble
   */
  public boolean getTwist(){
    if(isRoot()){
      return twistabs;
    }else{
      return parentbubble.getCompoundedTwist();}}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * GEOMETRY 2D
   * ++++++++++++++++++++++++++++++++
   */

  public double[] getOrigin2D(){
    if(isRoot()){
      //origin is a specified 2d point
      return originabs;
    }else{
      //origin is the 2d point of the parentbubble's v0
      double[] a=parentbubble.getVertexPoints2D()[0];
      return a;}}
  
  public double getForeward2D(){
    if(isRoot()){
      return forwardabs;
    }else{
      double[][] p=parentbubble.getVertexPoints2D();
      double a=DGeom.getDirection_2Points(p[0][0],p[0][1],p[1][0],p[1][1]);
      return a;}}
  
  //cache it.
  //TODO maybe make the whole tree immutable
  private Double fishrelative=null;
  
  public double getFish(){
    if(isRoot()){
      return fishabs;
    }else{
      if(fishrelative==null){
        fishrelative=getParentBubble().getFish();
        fishrelative*=fishfactor;}
      return fishrelative;}}
  
  //some basic intervals for use in point2d accquirement
  private static final double
    UINT_1=1.0,
    UINT_2=2.0,
    UINT_SQRT3=Math.sqrt(3.0);
  
  private static final double DIRECTION12UNIT=DGeom.PI*2.0/12.0;

  /*
   * return the point corrosponding to the specified vertex on this grid
   */
  public double[] getPoint2D(DVertex v){
    int 
      ant=v.getAnt(),
      bat=v.getBat(),
      cat=v.getCat(),
      dog=v.getDog();
    //get the 2d coordinates of the v12 local to v assuming a standard diamond grid
    double[] pv12={(ant+bat)*UINT_SQRT3,cat*(UINT_1+UINT_2)};
    //convert to polar coordinates
    double 
      pv12dir=DGeom.getDirection_2Points(0,0,pv12[0],pv12[1]),
      pv12dis=DGeom.getDistance_2Points(0,0,pv12[0],pv12[1]);
    //scale it
    double fish=getFish();
    pv12dis*=fish;
    //adjust direction for foreward and twist
    double f=getForeward2D();
    boolean twist=getTwist();
    if(twist==KGeom.TWIST_POSITIVE){
      pv12dir=DGeom.normalizeDirection(f+pv12dir);
    }else{
      pv12dir=DGeom.normalizeDirection(f-pv12dir);}
    //now we have the point in a form offset (p12dir,p12dis) from the hypothetical origin
    //get the actual v12 point
    double[] origin=getOrigin2D();
    pv12=DGeom.getPoint_PointDirectionInterval(origin[0],origin[1],pv12dir,pv12dis);
    //get the point for v by working from pv12, accounting for foreward, twist and fishscale
    double dir0,dis0;
    if(dog==0){
      dir0=0;//if our vertex is a v12 then do nothing
      dis0=0;
    }else if(dog==1){
      dir0=-DIRECTION12UNIT;
      dis0=KGeom.EDGESLV_GOAT;
    }else if(dog==2){
      dir0=0;
      dis0=KGeom.EDGESLV_HAWK;
    }else if(dog==3){
      dir0=DIRECTION12UNIT;
      dis0=KGeom.EDGESLV_GOAT;
    }else if(dog==4){
      dir0=DIRECTION12UNIT*2.0;
      dis0=KGeom.EDGESLV_HAWK;
    }else if(dog==5){
      dir0=DIRECTION12UNIT*3.0;
      dis0=KGeom.EDGESLV_GOAT;
    }else{
      throw new IllegalArgumentException("invalid dog : "+dog);}
    dis0*=fish;
    f=getForeward2D();
    if(twist==KGeom.TWIST_POSITIVE){
      dir0=DGeom.normalizeDirection(f+dir0);
    }else{
      dir0=DGeom.normalizeDirection(f-dir0);}
    double[] pv=DGeom.getPoint_PointDirectionInterval(pv12[0],pv12[1],dir0,dis0);
    return pv;}
  
  /*
   * ################################
   * BUBBLE TREE ELEMENT
   * ################################
   */
  
  public Bubble getParentBubble(){
    return getFirstAncestorBubble();}
  
//  public List<Bubble> getChildBubbles(){
//    return childbubbles;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return "["+hashCode()+"]";}
  
}
