package org.fleen.core.gKis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KVertexPath extends ArrayList<KVertex> implements Serializable{

  /*
   * ################################
   * STATIC FIELDS
   * ################################
   */
  
  private static final long serialVersionUID=-7629682211684455666L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public KVertexPath(){}
  
  public KVertexPath(int size){
    super.size();}
  
  public KVertexPath(List<KVertex> vectors){
    super(vectors);}
  
  public KVertexPath(KVertex... vectors){
    this(Arrays.asList(vectors));}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  /*
   * returns a DVectorRelativePath comprised of directiondeltas and distances from v0 to v1, v1 to v2 etc
   * returns null on fail
   */
  public KVectorRDPath getVectorPath(){
    int vertexcount=size();
    KVertex[] v=toArray(new KVertex[vertexcount]);
    KVectorRDPath bvr=new KVectorRDPath ();
    double distance;
    int delta,iprior,inext,d0,d1;
    for(int i=0;i<vertexcount;i++){
      iprior=i-1;
      if(iprior==-1)iprior=vertexcount-1;
      inext=i+1;
      if(inext==vertexcount)inext=0;
      distance=v[i].getDistance(v[inext]);
      d0=v[iprior].getDirection(v[i]);
      d1=v[i].getDirection(v[inext]);
      delta=KGeom.getDirectionDelta(d0,d1);
      bvr.add(new KVectorRD(delta,distance));}
    return bvr;}
  
  public List<double[]> getBasicPoint2Ds(){
    List<double[]> p=new ArrayList<double[]>();
    for(KVertex v:this)
      p.add(v.getBasicPoint2D());
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
      KVertex v;
      for(int i=1;i<size();i++){
        v=get(i);
        a.append(","+v);}
      a.append("]");}
    return a.toString();}
  
}

