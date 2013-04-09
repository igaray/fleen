package org.fleen.core.bubbleTree;

import java.io.Serializable;

import org.fleen.core.g2D.G2D;
import org.fleen.core.gKis.KGeom;
import org.fleen.core.gKis.KVertex;

/*
 * This is a kisrhombille grid coordinate system
 * it's parameters are :
 * twist : rotary direction of direction indexing
 * fish : basic interval
 * origin : 0,0,0,0
 * foreward : the real 2d direction at direction index 0
 */

public class Grid implements Serializable{
  
  private static final long serialVersionUID=-7272582675212521562L;

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Grid(double[] origin,double foreward,boolean twist,double fish){
    this.origin=origin;
    this.foreward=foreward;
    this.twist=twist;
    this.fish=fish;}
  
  /*
   * Default
   */
  public Grid(){
    this(
      DEFAULT_ROOT_ORIGIN_2D,
      DEFAULT_ROOT_FOREWARD,
      DEFAULT_ROOT_TWIST,
      DEFAULT_ROOT_FISH);}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  //default params
  private static final double[] DEFAULT_ROOT_ORIGIN_2D={0,0};
  private static final double DEFAULT_ROOT_FOREWARD=0;
  private static final boolean DEFAULT_ROOT_TWIST=true;
  private static final double DEFAULT_ROOT_FISH=1.0;
  
  
  //intervals for use in point2d accquirement
  private static final double
    UINT_1=1.0,
    UINT_2=2.0,
    UINT_SQRT3=Math.sqrt(3.0),
    DIRECTION12UNIT=G2D.PI*2.0/12.0;
  
  //the origin for our diamond grid in 2d geom terms
  private double[] origin=null;
  //foreward (direction==0) for our diamond grid in 2d geom terms
  private double foreward;
  //we have 2 mirroring possibilities here
  //true means positive twist : direction indices go clockwise
  //false means negative twist : direction indices go counterclockwise
  private boolean twist;
  //the basic interval in the diamond coordinate system
  //goat is fish*SQRT3, hawk is fish*2.0
  private double fish;
  
  public double[] getOrigin(){
    return origin;}
  
  public double getForeward(){
    return foreward;}
  
  public boolean getTwist(){
    return twist;}
  
  public double getFish(){
    return fish;}

  /*
   * return the point corrosponding to the specified vertex on this grid
   */
  public double[] getPoint2D(KVertex v){
    int 
      ant=v.getAnt(),
      bat=v.getBat(),
      cat=v.getCat(),
      dog=v.getDog();
    //get the 2d coordinates of the v12 local to v assuming a standard diamond grid
    double[] pv12={(ant+bat)*UINT_SQRT3,cat*(UINT_1+UINT_2)};
    //convert to polar coordinates
    double 
      pv12dir=G2D.getDirection_2Points(0,0,pv12[0],pv12[1]),
      pv12dis=G2D.getDistance_2Points(0,0,pv12[0],pv12[1]);
    //scale it
    pv12dis*=fish;
    //adjust direction for foreward and twist
    if(twist==KGeom.TWIST_POSITIVE){
      pv12dir=G2D.normalizeDirection(foreward+pv12dir);
    }else{
      pv12dir=G2D.normalizeDirection(foreward-pv12dir);}
    //now we have the point in a form offset (p12dir,p12dis) from the hypothetical origin
    //get the actual v12 point
    pv12=G2D.getPoint_PointDirectionInterval(origin[0],origin[1],pv12dir,pv12dis);
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
    if(twist==KGeom.TWIST_POSITIVE){
      dir0=G2D.normalizeDirection(foreward+dir0);
    }else{
      dir0=G2D.normalizeDirection(foreward-dir0);}
    double[] pv=G2D.getPoint_PointDirectionInterval(pv12[0],pv12[1],dir0,dis0);
    return pv;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return "["+hashCode()+"]";}
  
}
