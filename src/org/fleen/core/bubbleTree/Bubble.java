package org.fleen.core.bubbleTree;

import java.util.ArrayList;
import java.util.List;

import org.fleen.core.kGeom.DVertex;

/*
 * A loop of vertices in this bubble's parent grid
 * a node in a bubbletree
 * it's parent or grandparent is a grid
 * it's parent is either a foam or a grid
 * it has 0 or 1 child, a grid 
 * it is immutable, more or less
 */
public class Bubble extends BubbleTreeNode_Abstract{
  
  private static final long serialVersionUID=7403675520824450721L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */

  public Bubble(DVertex[] vertices,int chorusindex){
    this.vertices=vertices;
    this.chorusindex=chorusindex;}
  
  public Bubble(){}
  
  /*
   * ################################
   * BUBBLETREE
   * ################################
   */
  
  public Grid getParentGrid(){
    return getFirstAncestorGrid();}
  
  public Grid getChildGrid(){
    return (Grid)getChild(0);}
  
  public Bubble getParentBubble(){
    return getFirstAncestorBubble();}
  
  public List<Bubble> getChildBubbles(){
    Grid g=getChildGrid();
    if(g==null)return new ArrayList<Bubble>(0);
    return g.getChildBubbles();}
  
  public List<Bubble> getSiblingBubbles(){
    List<Bubble> a=new ArrayList<Bubble>(getParentGrid().getChildBubbles());
    a.remove(this);
    return a;}
  
  /*
   * ################################
   * CHORUS INDEX
   * a contextual signifier. groups bubbles
   * ################################
   */
  
  protected int chorusindex=0;
  
  public int getChorusIndex(){
    return chorusindex;}
  
  /*
   * ################################
   * KGEOM
   * ################################
   */
  
  protected DVertex[] vertices=null;
  
  /*
   * This is the most basic way to do this of course
   * In the grammar we do it with bubblemodels
   */
  public DVertex[] getVertices(){
    return vertices;}
  
  /*
   * ################################
   * DGEOM
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
    DVertex[] v=getVertices();
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
    DVertex[] v=getVertices();
    detailsize=v[gds_v0].getDistance(v[gds_v1])*getParentGrid().getFish();}
  
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
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return getClass().getSimpleName()+"["+hashCode()+"]";}
  
}
