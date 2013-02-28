package org.fleen.samples.diamondCompositionInspector;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.fleen.core.diamondGrammar.Bubble;

/*
 * misc data associated with a bubble
 */
public class BubbleGeometryDataCache{
  
  Bubble bubble;
  
  /*
   * ################################
   * CONSTRUCTOR 
   * ################################
   */
  
  public BubbleGeometryDataCache(Bubble b){
    this.bubble=b;}
  
  /*
   * ################################
   * FLUSH ALL DATA SO IT CAN BE RECALCULATED
   * ################################
   */
  
  public void flush(){
    path2d=null;
    bounds2d=null;
    detailsize=null;}
  
  /*
   * ################################
   * PATH 2D
   * ################################
   */
  
  private Path2D.Double path2d=null;
  
  public Path2D.Double getPath2D(){
    if(path2d==null)initPath2D();
    return path2d;}
  
  private void initPath2D(){
    path2d=new Path2D.Double();
    double[][] lp=bubble.getVertexPoints2D();
    path2d.moveTo(lp[0][0],lp[0][1]);
    //
    for(int i=1;i<lp.length;i++)
      path2d.lineTo(lp[i][0],lp[i][1]);
    path2d.closePath();}
  
  /*
   * ################################
   * BOUNDS 2D
   * ################################
   */
  
  Rectangle2D bounds2d=null;
  
  public Rectangle2D getBounds2D(){
    if(bounds2d==null)initBounds2D();
    return bounds2d;}
  
  private void initBounds2D(){
    bounds2d=getPath2D().getBounds2D();}
  
  /*
   * ################################
   * DETAIL SIZE
   * ################################
   */
  
  Double detailsize=null;
  
  public double getDetailSize(){
    if(detailsize==null)detailsize=bubble.getDetailSize();
    return detailsize;}
 
}
