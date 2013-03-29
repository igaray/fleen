package org.fleen.grammarEditor.grammarProject;

import java.awt.geom.Path2D;
import java.io.Serializable;
import java.util.List;

import org.fleen.core.kGeom.DVectorRDPath;
import org.fleen.core.kGeom.DVertex;
import org.fleen.core.kGeom.DVertexPath;
import org.fleen.core.kGeom.KGeom;

public class GPJigBubbleDef implements Serializable{

  /*
   * ################################
   * FIELDS
   * ################################
   */
  
  private static final long serialVersionUID=5037372259331846015L;
  
  public int id;//id# for editor
  public GPBubbleModel bubblemodel;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  //for bubbledef created in editor
  public GPJigBubbleDef(int id){
    this.id=id;}
  
  //for import set everything manually
  public GPJigBubbleDef(){}
  
  /*
   * ################################
   * V0 AND V1
   * ################################
   */

  public DVertex 
    v0=null,
    v1=null;
  
  public String getV0String(){
    if(v0==null){
      return "null";
    }else{
      return v0.toString();}}
  
  public String getV1String(){
    if(v1==null){
      return "null";
    }else{
      return v1.toString();}}
  
  public void touchVertex(DVertex v){
    if(v0!=null&&v0.equals(v)){
      v0=null;
      return;}
    if(v1!=null&&v1.equals(v)){
      v1=null;
      return;}
    if(v0==null){
      v0=v;
      return;}
    if(v0!=null&&v1==null){
      v1=v;
      return;}}
  
  /*
   * ################################
   * TWIST
   * ################################
   */
  
  private static String 
    TWIST_POS="POS",
    TWIST_NEG="NEG";
  
  public boolean twist=true;
  
  public void flipTwist(){
    twist=!twist;}
  
  public String getTwistString(){
    if(twist){
      return TWIST_POS;
    }else{
      return TWIST_NEG;}}
  
  /*
   * ################################
   * FOAM INDEX
   * ################################
   */
  
  public int foamindex=0;
  
  public void setFoamIndex(int a){
    if(a>-1)foamindex=a;}
  
  /*
   * ################################
   * CHORUS INDEX
   * ################################
   */
  
  public int chorusindex=0;
  
  public void setChorusIndex(int a){
    if(a>-1)chorusindex=a;}
  
  /*
   * ################################
   * IMAGE PATH
   * Use this for making images and icons
   * ################################
   */
  
  private Path2D.Double imagepath=null;
  
  public Path2D.Double getImagePath(){
    if(imagepath==null)
      initImagePath();
    return imagepath;}
  
  private void initImagePath(){
    //fail if any of our params are null
    if(bubblemodel==null||v0==null||v1==null){
      imagepath=null;
      return;}
    //
    imagepath=null;
    try{
      DVertexPath p0=bubblemodel.getVertexPath();
      DVectorRDPath p1=p0.getVectorPath();
      if(twist==KGeom.TWIST_NEGATIVE)
        p1.reverseDeltas();
      p0=p1.getVertexPath(v0,v1);
      List<double[]> points=p0.getBasicPoint2Ds();
      imagepath=new Path2D.Double();
      double[] p=points.get(0);
      imagepath.moveTo(p[0],p[1]);
      for(int i=1;i<points.size();i++){
        p=points.get(i);
        imagepath.lineTo(p[0],p[1]);}
      imagepath.closePath();
    }catch(Exception e){
      //fail on exception
      imagepath=null;}}
  
}
