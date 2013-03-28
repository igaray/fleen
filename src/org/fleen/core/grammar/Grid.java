package org.fleen.core.grammar;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

/*
 * This is a diamond grid coordinate system (aka "Hextakis" or "Bisected Hexagonal")
 * it's parameters are :
 * twist : rotary direction of direction indexing
 * fish : basic interval
 * origin : 0,0,0,0
 * foreward : the real 2d direction at direction index 0
 * 
 * We define it in terms either absolute (2d geom) or relative (ancestry of bubbles and grids)
 * 
 */

public class Grid implements DNode{
  
  /*
   * ################################
   * FIELDS
   * ################################
   */
  
  //for serialization
  private static final long serialVersionUID=5745470340167306395L;
  
  //DEFAULT ROOT PARAM CONSTANTS
  static final double[] DEFAULT_ROOT_ORIGIN_2D={0,0};
  static final double DEFAULT_ROOT_FOREWARD=0;
  static final double DEFAULT_ROOT_FISH=1.0;
  static final boolean DEFAULT_ROOT_TWIST=true;
  
  //tree node params
  //a grid has 0 or 1 parent bubble and 0..n child bubbles
  public Bubble parentbubble=null;
  public List<Bubble> childbubbles=new ArrayList<Bubble>();
  
  //GRID DEFINED IN ABSOLUTE TERMS
  //the basic interval in the diamond coordinate system : fish
  public double fishabs;
  //the origin for our diamond grid in 2d geom terms
  public double[] originabs=null;
  //foreward (direction==0) for our diamond grid in 2d geom terms
  public double forewardabs;
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
  public Grid(Bubble parentbubble,double fishfactor){
    this.parentbubble=parentbubble;
    parentbubble.childgrid=this;
    this.fishfactor=fishfactor;}

  /*
   * Define the grid in absolute terms
   * A root needs absolute terms because it has no parents from which to derive relative terms
   */
  public Grid(double[] origin,double foreward,double fish,boolean twist){
    setParamsAbsolute(origin,foreward,fish,twist);}
  
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
   * define grid in terms of just fishfactor
   */
  public Grid(double fishfactor){
    this.fishfactor=fishfactor;}
  
  /*
   * ################################
   * PARAMS ACCESS
   * TODO we don't need these 2 methods
   * ################################
   */
  
  /*
   * We are looking at some kind of continuous generation and trimming process.
   * In this process the root will be discarded and we will set up a new grid as root
   * That's what this is for
   */
  public void setParamsAbsolute(double[] origin,double foreward,double fish,boolean twist){
    originabs=origin;
    forewardabs=foreward;
    fishabs=fish;
    twistabs=twist;}
  
  public void setFishAbsolute(double f){
    fishabs=f;}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  /*
   * Either we use the param or we derive directly from parentbubble
   */
  public boolean getTwist(){
    if(isRoot()){
      return twistabs;
    }else{
      return parentbubble.getCompoundedTwist();}}
  
  public void flushBranchRealGeometryCache(){
    for(Bubble b:childbubbles)
      b.flushBranchRealGeometryCache();}
  
  public void flushBranchDiamondGeometryCache(){
    for(Bubble b:childbubbles)
      b.flushBranchDiamondGeometryCache();}
  
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
      return forewardabs;
    }else{
      double[][] p=parentbubble.getVertexPoints2D();
      double a=Math2D.getDirection_2Points(p[0][0],p[0][1],p[1][0],p[1][1]);
      return a;}}
  
  //cache it.
  //TODO maybe make the whole tree immutable
  private Double fishrelative=null;
  
  public double getFish(){
    if(isRoot()){
      return fishabs;
    }else{
      if(fishrelative==null){
        fishrelative=parentbubble.getFish();
        fishrelative*=fishfactor;}
      return fishrelative;}}
  
  //some basic intervals for use in point2d accquirement
  private static final double
    UINT_1=1.0,
    UINT_2=2.0,
    UINT_SQRT3=Math.sqrt(3.0);
  
  private static final double DIRECTION12UNIT=Math2D.PI*2.0/12.0;

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
      pv12dir=Math2D.getDirection_2Points(0,0,pv12[0],pv12[1]),
      pv12dis=Math2D.getDistance_2Points(0,0,pv12[0],pv12[1]);
    //scale it
    double fish=getFish();
    pv12dis*=fish;
    //adjust direction for foreward and twist
    double f=getForeward2D();
    boolean twist=getTwist();
    if(twist==MathDiamond.TWIST_POSITIVE){
      pv12dir=Math2D.normalizeDirection(f+pv12dir);
    }else{
      pv12dir=Math2D.normalizeDirection(f-pv12dir);}
    //now we have the point in a form offset (p12dir,p12dis) from the hypothetical origin
    //get the actual v12 point
    double[] origin=getOrigin2D();
    pv12=Math2D.getPoint_PointDirectionInterval(origin[0],origin[1],pv12dir,pv12dis);
    //get the point for v by working from pv12, accounting for foreward, twist and fishscale
    double dir0,dis0;
    if(dog==0){
      dir0=0;//if our vertex is a v12 then do nothing
      dis0=0;
    }else if(dog==1){
      dir0=-DIRECTION12UNIT;
      dis0=MathDiamond.EDGESLV_GOAT;
    }else if(dog==2){
      dir0=0;
      dis0=MathDiamond.EDGESLV_HAWK;
    }else if(dog==3){
      dir0=DIRECTION12UNIT;
      dis0=MathDiamond.EDGESLV_GOAT;
    }else if(dog==4){
      dir0=DIRECTION12UNIT*2.0;
      dis0=MathDiamond.EDGESLV_HAWK;
    }else if(dog==5){
      dir0=DIRECTION12UNIT*3.0;
      dis0=MathDiamond.EDGESLV_GOAT;
    }else{
      throw new IllegalArgumentException("invalid dog : "+dog);}
    dis0*=fish;
    f=getForeward2D();
    if(twist==MathDiamond.TWIST_POSITIVE){
      dir0=Math2D.normalizeDirection(f+dir0);
    }else{
      dir0=Math2D.normalizeDirection(f-dir0);}
    double[] pv=Math2D.getPoint_PointDirectionInterval(pv12[0],pv12[1],dir0,dis0);
    return pv;}
  
  /*
   * ################################
   * CULTIVATION
   * ################################
   */
  
  private boolean capped=false;
  
  /*
   * when a bubble is capped it's a flag for the cultivation logic saying that this element should 
   * not be cultivated anymore
   */
  public void capBranch(){
    capped=true;
    if(!childbubbles.isEmpty())
      for(Bubble b:childbubbles)
        b.capBranch();}
  
  public void uncapBranch(){
    capped=true;
    if(!childbubbles.isEmpty())
      for(Bubble b:childbubbles)
        b.uncapBranch();}
  
  public boolean isCapped(){
    return capped;}
  
  /*
   * ################################
   * TREE STUFF
   * includes TreeNode implementation
   * ################################
   */
  
  public List<Bubble> getChildBubbles(){
    return childbubbles;}
  
  public Enumeration<DNode> children(){
    return new ChildEnumeration();}
  
  //START CHILD ENUMERATION CLASS
  private class ChildEnumeration implements Enumeration<DNode>{

    int i=0;
    
    public boolean hasMoreElements(){
      return i<childbubbles.size();}

    public DNode nextElement(){
      Bubble b=childbubbles.get(i);
      i++;
      return b;}
    
  }//END CHILD ENUMERATION CLASS

  public boolean getAllowsChildren(){
    return true;}

  public Bubble getChildAt(int i){
    return childbubbles.get(i);}

  public int getChildCount(){
    return childbubbles.size();}

  //if this grid has a parent then it's index within it's parent's list of children is always 0
  //a bubble, if it has a child, has just 1 child : a single grid.
  public int getIndex(TreeNode arg0){
    return 0;}

  public Bubble getParent(){
    return parentbubble;}

  //this will only return true in errors and tests
  public boolean isLeaf(){
    return childbubbles==null||childbubbles.isEmpty();}
  
  public boolean isRoot(){
    return parentbubble==null;}
  
  public int getDepth(){
    if(isRoot())return 0;
    return parentbubble.getDepth()+1;}
  
  public List<Bubble> getBranchLeafBubbles(){
    List<Bubble> a=new ArrayList<Bubble>();
    for(Bubble b:childbubbles)
      a.addAll(b.getBranchLeafBubbles());
    return a;}
  
  public List<Bubble> getBranchBubbles(){
    List<Bubble> a=new ArrayList<Bubble>();
    for(Bubble b:childbubbles)
      a.addAll(b.getBranchBubbles());
    return a;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return "["+hashCode()+"]";}
  
}
