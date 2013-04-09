package org.fleen.core.bubbleTree;

import org.fleen.core.gKis.KVertex;

/*
 * A loop of vertices in this bubble's parent grid
 * a node in a bubbletree
 * It stores a list of vertices
 * It refers to the first Grid in it's ancestry to convert those vertices to point2ds.
 * It has an identifier : chorusindex.
 *   we differentiate bubbles into choruses to control symmetry
 */
public abstract class Bubble_Abstract extends BubbleTreeNode{
  
  private static final long serialVersionUID=7403675520824450721L;
  
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
    Grid grid=getFirstAncestorGridStackElement().getGrid();
    for(int i=0;i<s;i++)
      vertexpoints2d[i]=grid.getPoint2D(v[i]);}
  
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
    detailsize=v[gds_v0].getDistance(v[gds_v1])*getFirstAncestorGridStackElement().getGrid().getFish();}
  
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
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return getClass().getSimpleName()+"["+hashCode()+"]";}

}
