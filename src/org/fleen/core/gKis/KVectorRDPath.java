package org.fleen.core.gKis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * diamond vector relative direction path
 */
public class KVectorRDPath extends ArrayList<KVectorRD> implements Serializable{

  /*
   * ################################
   * STATIC FIELDS
   * ################################
   */
  
  private static final long serialVersionUID=-6370690145581661669L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public KVectorRDPath(){}
  
  public KVectorRDPath(int size){
    super.size();}
  
  public KVectorRDPath(List<KVectorRD> vectors){
    super(vectors);}
  
  public KVectorRDPath(KVectorRD... vectors){
    this(Arrays.asList(vectors));}
  
  public KVectorRDPath(KVertexPath vertexpath){
    super(vertexpath.getVectorPath());}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public boolean isClockwise(){
    int a=0;
    for(KVectorRD v:this)
      a+=v.directiondelta;
    return a>0;}
  
  public void reverseDeltas(){
    for(KVectorRD v:this)
      v.directiondelta=(v.directiondelta*-1);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * DERIVE VERTEX PATH
   * ++++++++++++++++++++++++++++++++
   */
  
  /*
   * returns a scaled vertex path corrosponding to this vector path
   * it starts at the specified vertex0 and v0 to v1 direction 
   * returns null on fail
   */
  public KVertexPath getVertexPath(KVertex vertex,int direction,double scale){
    KVertexPath p=new KVertexPath();
    p.add(vertex);
    KVectorRD vector=get(0);
    int vcount=size();
    double distance=scale*vector.distance;
    for(int i=1;i<vcount;i++){
      vertex=vertex.getVertex_DirDis(direction,distance);
      if(vertex==null)return null;
      p.add(vertex);
      vector=get(i);
      distance=scale*vector.distance;
      direction=(direction+vector.directiondelta+12)%12;}
    return p;}
  
  /*
   * same as above with scale=1.0
   */
  public KVertexPath getVertexPath(KVertex v0,int d0){
    return getVertexPath(v0,d0,1.0);}
  
  /*
   * returns a vertex path corrosponding to this vector path
   * v0 is specified. 
   * v0 to v1 direction is dir(v0,v1)
   * scale is dis(v0,v1)/vector0.distance
   */
  public KVertexPath getVertexPath(KVertex v0,KVertex v1){
    int dir=v0.getDirection(v1);
    double scale=v0.getDistance(v1)/get(0).distance;
    return getVertexPath(v0,dir,scale);}
  
  private static final int MAXSTANDARDFORMATVERTEXPATHINITTRANSITIONS=200;
  private static final KVertex ORIGIN=new KVertex(0,0,0,0);
  
  /*
   * returns a vertex path corrosponding to this vector path
   * vertex path is of minimal scale and in STANDARD FORMAT 
   *   (scale is minimal, v0 is ORIGIN, initial direction is direction0)
   * Only the initial v0 to v1 distance is in question, so we try a bunch
   * 
   * Our task is to glean the scale for a minimal working form
   * we're starting at origin [0,0,0,0] and, initially, heading in direction0.
   * try scaling the first vector to fit a distance of 2 (the minimal distance 
   *   from origin to the next vertex in that direction).
   * if we successfully create a dvertexpath then we're done.
   * if not then increment the initial distance (and corrosponding scale) and try again.
   * increments  go 2,1,1,2,1,1,2,1,1,etc
   * on fail return null.
   */
  public KVertexPath getVertexPath(){
    int initdistance=0,transitioncount=0;
    KVertexPath newvertexpath=null;
    while(newvertexpath==null&&transitioncount<MAXSTANDARDFORMATVERTEXPATHINITTRANSITIONS){
      switch(transitioncount%4){
      case 0:
        initdistance+=2;
        break;
      case 1:
        initdistance+=1;
        break;
      case 2:
        initdistance+=1;
        break;
      case 3:
        initdistance+=2;
        break;}
      newvertexpath=getVertexPathWithInitialDistance(initdistance);
      transitioncount++;}
    if(newvertexpath==null)
      System.out.println(">>STANDARD FORMAT VERTEX PATH ACCQUIREMENT FAILED<<");
    return newvertexpath;}
  
  /*
   * given this vectorpath and the specified initialdistance, get the scale required to create a vertexpath
   * with that initialdistance.
   * then create that vertexpath and return it, be it null or not.
   */
  private KVertexPath getVertexPathWithInitialDistance(double initdistance){
    double a=get(0).distance;
    double scale=initdistance/a;
    KVertexPath p=getVertexPath(ORIGIN,0,scale);
    return p;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    if(isEmpty())return"[]";
    StringBuffer a=new StringBuffer();
    a.append("["+get(0));
    if(size()>1){
      KVectorRD v;
      for(int i=1;i<size();i++){
        v=get(i);
        a.append(","+v);}
      a.append("]");}
    return a.toString();}
  
}
