package org.fleen.core.diamondGrammar;

import java.io.Serializable;


/*
 * 
 * A vertex in a diamond grid
 * 
 */
public class DVertex implements Serializable{
  
  private static final long serialVersionUID=5215014207521492571L;

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
 
  /*
   * (ant,bat,cat) for local t12 
   * ant,bat,cat range [MININT,MAXINT]
   * dog for point relative to local t12
   * range [0,5]
   */
  public DVertex(int ant,int bat,int cat,int dog){
    coors=new int[]{ant,bat,cat,dog};}
  
  public DVertex(int[] params){
    this.coors=params;}
  
  public DVertex(){
    coors=new int[4];}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public int[] coors;
  
  public void setCoordinates(int a,int b,int c,int d){
    coors[0]=a;
    coors[1]=b;
    coors[2]=c;
    coors[3]=d;}
  
  public int getAnt(){
    return coors[0];}
  
  public int getBat(){
    return coors[1];}
  
  public int getCat(){
    return coors[2];}
  
  public int getDog(){
    return coors[3];}
  
  public static final int getAnt(int bat,int cat){
    return bat-cat;}
  
  public static final int getBat(int ant,int cat){
    return ant+cat;}
  
  public static final int getCat(int ant,int bat){
    return bat-ant;}
  
  public int getGeneralType(){
    switch(coors[3]){
    case 0:return MathDiamond.VERTEX_GTYPE_12;
    case 1:return MathDiamond.VERTEX_GTYPE_4;
    case 2:return MathDiamond.VERTEX_GTYPE_6;
    case 3:return MathDiamond.VERTEX_GTYPE_4;
    case 4:return MathDiamond.VERTEX_GTYPE_6;
    case 5:return MathDiamond.VERTEX_GTYPE_4;
    default:
      throw new IllegalArgumentException("VERTEX TYPE INVALID");}}
  
  //returns the direction from this vertex to v
  //returns DIRECTION_NULL if this vertex and v are not colinear
  public int getDirection(DVertex v){
    return MathDiamond.getDirection_VertexVertex(
      coors[0],coors[1],coors[2],coors[3],
      v.coors[0],v.coors[1],v.coors[2],v.coors[3]);}
  
  public double getDistance(DVertex v){
    return MathDiamond.getDistance_VertexVertex(
      coors[0],coors[1],coors[2],coors[3],
      v.coors[0],v.coors[1],v.coors[2],v.coors[3]);}
  
  //returns null if invalid
  public DVertex getVertexTransitionswise(int dir,int trans){
    int[] v1coor=new int[4];
    MathDiamond.getVertex_Transitionswise(
      coors[0],coors[1],coors[2],coors[3],dir,trans,v1coor);
    if(v1coor[3]==MathDiamond.DIRECTION_NULL)return null;
    return new DVertex(v1coor);}
  
  public DVertex getVertex_DirDis(int dir,double dis){
    int[] v1coor=new int[4];
    MathDiamond.getVertex_VertexVector(coors[0],coors[1],coors[2],coors[3],dir,dis,v1coor);
    if(v1coor[3]==MathDiamond.DIRECTION_NULL)return null;
    return new DVertex(v1coor);}
  
  /*
   * returns true if this vertex is colinear with the specified
   */
  public boolean isColinear(DVertex v){
    return MathDiamond.getColinear_VertexVertex(
      coors[0],coors[1],coors[2],coors[3],
      v.coors[0],v.coors[1],v.coors[2],v.coors[3]);}
  
  //returns point2d assuming a basic, unrotated, unscaled, unflipped grid
  public double[] getBasicPoint2D(){
    double[] c=new double[2];
    MathDiamond.getBasicPoint2D_Vertex(coors[0],coors[1],coors[2],coors[3],c);
    return c;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public boolean equals(Object a){
    DVertex b=(DVertex)a;
    return coors[0]==b.coors[0]&&coors[1]==b.coors[1]&&coors[2]==b.coors[2]&&coors[3]==b.coors[3];}
  
  public int hashCode(){
    int a=coors[0]*16+coors[1]*256+coors[2]*4096+coors[3]*65536;
    return a;}
  
  public String toString(){
    String s="["+coors[0]+","+coors[1]+","+coors[2]+","+coors[3]+"]";
    return s;}
  
  public Object clone(){
    return new DVertex(coors[0],coors[1],coors[2],coors[3]);}
  
}