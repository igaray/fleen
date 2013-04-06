package org.fleen.core.bubbleTree;

import org.fleen.core.gKis.KVertex;

/*
 * A loop of vertices in this bubble's parent grid
 * a node in a bubbletree
 * it's parent or grandparent is a grid
 * it's parent is either a foam or a grid
 * it has 0 or 1 child, a grid 
 * it is immutable, more or less
 */
public abstract class Bubble_Abstract extends BubbleTreeNode implements GridStackElement{
  
  private static final long serialVersionUID=7403675520824450721L;
  
  /*
   * ################################
   * BUBBLETREE
   * ################################
   */
  
  public Grid getParentGrid(){
    return getFirstAncestorGrid();}
  
  public Grid getChildGrid(){
    return (Grid)getChild(0);}
  
  public Bubble_Abstract getParentBubble(){
    return getFirstAncestorBubble();}
  
//  public List<Bubble> getChildBubbles(){
//    Grid g=getChildGrid();
//    if(g==null)return new ArrayList<Bubble>(0);
//    return g.getChildBubbles();}
//  
//  public List<Bubble> getSiblingBubbles(){
//    List<Bubble> a=new ArrayList<Bubble>(getParentGrid().getChildBubbles());
//    a.remove(this);
//    return a;}
  
  /*
   * ################################
   * CHORUS INDEX
   * a contextual signifier. groups bubbles into chorusses
   * ################################
   */
  
  private int chorusindex=0;
  
  public int getChorusIndex(){
    return chorusindex;}
  
  public void setChorusIndex(int i){
    chorusindex=i; }
  
  /*
   * ################################
   * GEOM KIS
   * ################################
   */
  
  public abstract KVertex[] getVertices();
  
  /*
   * ################################
   * GEOM 2D
   * ################################
   */
  
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
    KVertex[] v=getVertices();
    int s=v.length;
    vertexpoints2d=new double[s][2];
    Grid parentgrid=getParentGrid();
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
    KVertex[] v=getVertices();
    detailsize=v[gds_v0].getDistance(v[gds_v1])*getParentGrid().getFish();}
  
  private void initIndicesOfFurthestAdjacentVerticesForGetDetailSize(){
    KVertex[] v=getVertices();
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
  
  /*
   * ################################
   * GRID STACK ELEMENT
   * ################################
   */
  
  GridStackElementParam gseparam;
  
  public GridStackElementParam getGSEParam(){
    return gseparam;}

  public void setGSEParam(GridStackElementParam gseparam){
    this.gseparam=gseparam;}

  public double[] getStackOrigin(){
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getStackForeward(){
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean getStackTwist(){
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public double getStackFish(){
    // TODO Auto-generated method stub
    return 0;
  }  
  
  
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return getClass().getSimpleName()+"["+hashCode()+"]";}

}
