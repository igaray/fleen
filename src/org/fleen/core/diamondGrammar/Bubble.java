package org.fleen.core.diamondGrammar;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

/*
 * A loop of vertices in this bubble's parent grid
 * It's geometry is defined by 
 *   a bubble model
 *   2 vertices (on it's parent grid)
 *   twist
 * it also has a parentgrid, childgrid, foam, chorusindex and multipurpose data object
 */
public class Bubble implements DNode{
  
  /*
   * ################################
   * FIELDS
   * ################################
   */

  private static final long serialVersionUID=6592903112927637121L;
  
  public Grid parentgrid=null,childgrid=null;
  //location, orientation and scale independent form of this bubble's geometry
  //it's "geometric class"
  public BubbleModel model;
  //from these 2 vertices we get location, foreward and scale
  public DVertex v0,v1;
  //positive or negative twist (true or false, respectively)
  //which side of the mirror?
  //twist==true means same as parent grid, false means opposite
  public boolean twist;
  //the foam to which this bubble belongs
  public Foam foam=null;
  //an arbitrary group assignment. used for symmetry control. 
  //bubbles with the same index get similar treatment
  public int chorusindex=0;
  //general purpose data object
  public Object data=null;
  public static final int TYPE_UNDEFINED=-1,TYPE_SHARD=0,TYPE_RAFT=1;
  public int type=TYPE_UNDEFINED;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */

  public Bubble(
    Grid parentgrid,
    BubbleModel model,
    int type,
    DVertex v0,
    DVertex v1,
    boolean twist,
    Foam foam,
    int chorusindex){
    this.parentgrid=parentgrid;
    parentgrid.childbubbles.add(this);
    this.model=model;
    this.type=type;
    this.v0=v0;
    this.v1=v1;
    this.twist=twist;
    this.foam=foam;
    this.chorusindex=chorusindex;
    //
    foam.addBubble(this);}
  
  /*
   * create a new bubble in standard format (v0=origin, d0=0, twist=positive).
   * scale the bubblemodel to whatever works, thus gleaning v0 and v1
   * twist is positive
   * foam is a new foam
   * chorusindex is 0
   * we use this as a root bubble for testing
   */
  public Bubble(
    Grid parentgrid,
    BubbleModel model){
    this.parentgrid=parentgrid;
    parentgrid.childbubbles.add(this);
    this.model=model;
    DVertexPath p=model.getVectorPath().getVertexPath();
    type=TYPE_RAFT;
    v0=p.get(0);
    v1=p.get(1);
    twist=MathDiamond.TWIST_POSITIVE;
    foam=new Foam();
    chorusindex=0;}
  
  public Bubble(){}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  double[] centroid2d=null;
  Double area2d=null;
  
  public double[] getCentroid2D(){
    if(centroid2d==null)initCentroid2D();
    return centroid2d;}
  
  private void initCentroid2D() {
    double[][] vp=getVertexPoints2D();
    double cx=0.0,cy=0.0;
    int inext;
    for(int i=0;i<vp.length;i++){
      inext=i+1;
      if(inext==vp.length)inext=0;
      cx=cx+(vp[i][0]+vp[inext][0])*(vp[i][1]*vp[inext][0]-vp[i][0]*vp[inext][1]);
      cy=cy+(vp[i][1]+vp[inext][1])*(vp[i][1]*vp[inext][0]-vp[i][0]*vp[inext][1]);}
    double area=getArea2D();
    cx/=(6.0* area);
    cy/=(6.0*area);
    centroid2d=new double[]{cx,cy};}
  
  public double getArea2D(){
    if(area2d==null)initArea2D();
    return area2d;}
  
  public void initArea2D(){
    double[][] vp=getVertexPoints2D();
    double sum=0.0;
    int inext;
    for(int i=0;i<vp.length;i++){
      inext=i+1;
      if(inext==vp.length)inext=0;
      sum=sum+(vp[i][0]*vp[inext][1])-(vp[i][1]*vp[inext][0]);}
    //discard the sign
    area2d=Math.abs(0.5*sum);}
  
  /*
   * some geometry data is derived from ancestors in the tree
   * when ancestor geometry changes (cuz we're doing an animation or something) the cached 
   * data becomes invalid, so we flush it so it can be recalculated.
   */
  public void flushBranchRealGeometryCache(){
    vertexpoints2d=null;
    detailsize=null;
    centroid2d=null;
    area2d=null;
    if(childgrid!=null)
      childgrid.flushBranchRealGeometryCache();}
  
  public void flushBranchDiamondGeometryCache(){
    vertices=null;
    if(childgrid!=null)
      childgrid.flushBranchDiamondGeometryCache();}
  
  /* 
   * Twist is the direction of direction indexing in diamond
   * positive means clockwise indexing, negative means counterclockwise. Thus we have mirror diamonds.
   * we are working with nesting grids, however, so within a negative-twist grid negative is positive. 
   * Mirror of mirror is nonmirror, and so on.
   * negative is basically the reverse of whatever the twist of the present grid is
   * If our bubble's twist is positive then we return the twist of it's parent grid
   * If our bubble's twist is negative then we return the opposite of the parent grid twist
   * Compounded twist is the twist at this bubble in absolute terms as derived from the twists of it's ancestors
   */
  public boolean getCompoundedTwist(){
    if(twist==MathDiamond.TWIST_POSITIVE){
      return parentgrid.getTwist();
    }else{
      return !parentgrid.getTwist();}}
  
  /*
   * fish is our local unit interval
   * we derive it by comparing the v0 v1 of this bubble with that of it's bubblemodel
   * it is scale, basically.
   */
  public double getFish(){
    double[] 
      p0=parentgrid.getPoint2D(v0),
      p1=parentgrid.getPoint2D(v1);
    double a=Math2D.getDistance_2Points(p0[0],p0[1],p1[0],p1[1]);
    double fish=a/model.getVector(0).distance;
    return fish;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * VERTEX POINTS 2D
   * this bubble's dvertexpath translated into 2d geometry
   * we derive any foreward, scale and translation context from ancestry
   * ++++++++++++++++++++++++++++++++
   */
  
  private double[][] vertexpoints2d=null;
  
  public double[][] getVertexPoints2D(){
    if(vertexpoints2d==null)
      initVertexPoints2D();
    return vertexpoints2d;}
  
  private void initVertexPoints2D(){
    DVertex[] v=getVertices();
    int s=v.length;
    vertexpoints2d=new double[s][2];
    for(int i=0;i<s;i++)
      vertexpoints2d[i]=parentgrid.getPoint2D(v[i]);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * DETAIL SIZE
   * Detail size tells us how big this bubble is, more or less, graphically speaking
   * return the distance between the 2 adjacent points on the vertex polygon that are 
   * furthest from each other
   * ++++++++++++++++++++++++++++++++
   */
  
  private int gds_v0=-1,gds_v1;
  private Double detailsize=null;
  
  public double getDetailSize(){
    if(detailsize==null)
      initDetailSize();
    return detailsize;}
  
  private void initDetailSize(){
    if(gds_v0==-1)
      initIndicesOfFurthestAdjacentVerticesForGetDetailSize();
    DVertex[] v=getVertices();
    detailsize=v[gds_v0].getDistance(v[gds_v1])*parentgrid.getFish();
    
  }
  
  private void initIndicesOfFurthestAdjacentVerticesForGetDetailSize(){
    DVertex[] v=getVertices();
    int inext;
    double dtest,dfurthest=Double.MIN_VALUE;
    for(int i=0;i<v.length;i++){
      inext=i+1;
      if(inext==v.length)inext=0;
      dtest=v[i].getDistance(v[inext]);
      if(dtest>dfurthest){
        dfurthest=dtest;
        gds_v0=i;
        gds_v1=inext;}}}
  
//  private void initIndicesOfClosestAdjacentVerticesForGetDetailSize(){
//    DVertex[] v=getVertices();
//    int inext;
//    double dtest,dclosest=Double.MAX_VALUE;
//    for(int i=0;i<v.length;i++){
//      inext=i+1;
//      if(inext==v.length)inext=0;
//      dtest=v[i].getDistance(v[inext]);
//      if(dtest<dclosest){
//        dclosest=dtest;
//        gds_v0=i;
//        gds_v1=inext;}}}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * VERTICES
   * ++++++++++++++++++++++++++++++++
   */
  
  private DVertex[] vertices=null;
  
  public DVertex[] getVertices(){
    if(vertices==null)initVertices();
    return vertices;}
  
  private void initVertices(){
    DVectorRD vector=new DVectorRD(getBaseForeward(),0);
    int vectorcount=model.getVectorCount();
    double scale=v0.getDistance(v1)/model.getVector(0).distance;
    vertices=new DVertex[vectorcount];
    vertices[0]=v0;
    vertices[1]=v1;
    int directiondelta;
    for(int i=1;i<vectorcount-1;i++){
      directiondelta=model.getVector(i).directiondelta;
      if(twist==MathDiamond.TWIST_NEGATIVE)directiondelta*=-1;
      vector.directiondelta=(vector.directiondelta+directiondelta+12)%12;
      vector.distance=scale*model.getVector(i).distance;
      vertices[i+1]=MathDiamond.getVertex_VertexVector(vertices[i],vector);
      if(vertices[i+1]==null)
        throw new IllegalArgumentException("BAD GEOMETRY. "+vertices[i+1]+" , "+vector);}}
  
  private int getBaseForeward(){
    int f=MathDiamond.getDirection_VertexVertex(
      v0.getAnt(),v0.getBat(),v0.getCat(),v0.getDog(),
      v1.getAnt(),v1.getBat(),v1.getCat(),v1.getDog());
    if(f==MathDiamond.DIRECTION_NULL)
      throw new IllegalArgumentException("local foreward is direction null "+v0+" "+v1);
    return f;}
  
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
    if(childgrid!=null)
      childgrid.capBranch();}
  
  public void uncapBranch(){
    capped=false;
    if(childgrid!=null)
      childgrid.uncapBranch();}
  
  public boolean isCapped(){
    return capped;}
  
  /*
   * ################################
   * LOCAL BUBBLE FAMILY
   * a bubble's immediate parent and child are grids
   * just beyond these, in the tree, we have 0,1 parent and 0..n children, with
   * whom this bubble has a geometric association 
   * so those are the parent bubble and child bubbles
   * we also access the sibling bubbles
   * ################################
   */
  
  public Bubble getParentBubble(){
    return parentgrid.parentbubble;}
  
  public List<Bubble> getChildBubbles(){
    if(childgrid==null)return new ArrayList<Bubble>(0);
    return childgrid.childbubbles;}
  
  public List<Bubble> getSiblingBubbles(){
    List<Bubble> a=new ArrayList<Bubble>(parentgrid.childbubbles);
    a.remove(this);
    return a;}
  
  /*
   * ################################
   * SIGNATURE
   * this is the composed of bubblemodel ids and chorusindices tracing back to the root
   * a string of values. comparing these signatures we are guided in our symmetry control
   * bubbles with matching signatures are locally and contextually isometric (we assume). 
   * ################################
   */
  
  private BubbleSignature signature=null;
  
  public BubbleSignature getSignature(){
    if(signature==null)
      signature=new BubbleSignature(this);
    return signature;}

  /*
   * ################################
   * TREE NODE STUFF + IMPLEMENTATION OF TreeNode
   * ################################
   */
  
  public Enumeration<DNode> children(){
    return new ChildEnumeration();}
  
  //START CHILD ENUMERATION CLASS
  //a bubble has 0 or 1 child, a Grid
  private class ChildEnumeration implements Enumeration<DNode>{

    boolean hasmoreelements=childgrid!=null;
    
    public boolean hasMoreElements(){
      return hasmoreelements;}

    public DNode nextElement(){
      hasmoreelements=false;
      return childgrid;}
    
  }//END CHILD ENUMERATION CLASS

  public boolean getAllowsChildren(){
    return true;}

  //a bubble has 0 or 1 children. The child is a Grid
  public Grid getChildAt(int i){
    if(i!=0)return null;
    return childgrid;}

  //if it has a child then 1, if it doesn't then 0
  public int getChildCount(){
    if(childgrid!=null)return 1;
    return 0;}

  //a bubble always has a parent grid (but a grid might not have a parent bubble)
  public int getIndex(TreeNode arg0){
    return parentgrid.childbubbles.indexOf(this);}

  public Grid getParent(){
    return parentgrid;}

  public boolean isLeaf(){
    return childgrid==null;}
  
  public boolean isRoot(){
    return false;}//yes, a bubble can never be root
  
  public int getDepth(){
    return parentgrid.getDepth()+1;}
  
  public List<Bubble> getBranchLeafBubbles(){
    List<Bubble> a=new ArrayList<Bubble>();
    if(isLeaf()){
      a.add(this);
      return a;
    }else{
      a.addAll(childgrid.getBranchLeafBubbles());}
    return a;}
  
  public List<Bubble> getBranchBubbles(){
    List<Bubble> a=new ArrayList<Bubble>();
    a.add(this);
    if(childgrid!=null)
      a.addAll(childgrid.getBranchBubbles());
    return a;}
  
  /*
   * ################################
   * TYPE, RAFT, SHARD
   * ################################
   */
  
  //how many levels down do we have to go to get the prior raft
  public int getRaftLevel(){
   if(type==TYPE_RAFT||parentgrid.parentbubble==null){
     return 0;
   }else{
     return getParentBubble().getRaftLevel()+1;}}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return getClass().getSimpleName()+"["+hashCode()+"]";}
  
}
