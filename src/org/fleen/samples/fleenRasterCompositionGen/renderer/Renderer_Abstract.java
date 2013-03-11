package org.fleen.samples.fleenRasterCompositionGen.renderer;

import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.fleen.core.diamondGrammar.Bubble;
import org.fleen.core.diamondGrammar.DGComposition;
import org.fleen.samples.fleenRasterCompositionGen.Composition;
import org.fleen.samples.fleenRasterCompositionGen.Viewer;

public abstract class Renderer_Abstract{
      
  /*
   * ################################
   * RENDER
   * ################################
   */
  
  protected static HashMap<RenderingHints.Key,Object> RENDERING_HINTS=
    new HashMap<RenderingHints.Key,Object>();
    
  static{
    RENDERING_HINTS.put(
      RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    RENDERING_HINTS.put(
      RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_DITHERING,RenderingHints.VALUE_DITHER_DEFAULT);
    RENDERING_HINTS.put(
      RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    RENDERING_HINTS.put(
      RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY); 
    RENDERING_HINTS.put(
      RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);}
  
  public BufferedImage renderForExport(Composition composition,double scale){
    Rectangle2D.Double bounds=getRootBubbleBounds(composition);
    double
      offx=-bounds.getMinX(),
      offy=-bounds.getMinY();
    AffineTransform transform=new AffineTransform();
    transform.scale(scale,scale);
    transform.translate(offx,offy);
    return getImage(composition,bounds,transform);}
  
  public BufferedImage renderForViewer(Composition composition,Viewer viewer){
    Rectangle2D.Double bounds=getRootBubbleBounds(composition);
    double scale=viewer.getHeight()/bounds.getHeight();
    double
      offx=-bounds.getMinX(),
      offy=-bounds.getMinY();
    AffineTransform transform=new AffineTransform();
    transform.scale(scale,scale);
    transform.translate(offx,offy);
    return getImage(composition,bounds,transform);}
  
  protected abstract BufferedImage getImage(
    Composition composition,Rectangle2D.Double bounds,AffineTransform transform);
  
  private Rectangle2D.Double getRootBubbleBounds(DGComposition dgc){
    Bubble a=dgc.getRootGrid().getChildBubbles().get(0);
    double[][] vp=a.getVertexPoints2D();
    double maxx=Double.MIN_VALUE,maxy=maxx,minx=Double.MAX_VALUE,miny=minx;
    for(int i=0;i<vp.length;i++){
      if(minx>vp[i][0])minx=vp[i][0];
      if(miny>vp[i][1])miny=vp[i][1];
      if(maxx<vp[i][0])maxx=vp[i][0];
      if(maxy<vp[i][1])maxy=vp[i][1];}
    return new Rectangle2D.Double(minx,miny,maxx-minx,maxy-miny);}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return this.getClass().getSimpleName();}

}
