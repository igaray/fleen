package org.fleen.samples.fleenRasterCompositionGen.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.fleen.core.diamondGrammar.Bubble;
import org.fleen.samples.diamondCompositionInspector.Util;
import org.fleen.samples.fleenRasterCompositionGen.Composition;
import org.fleen.samples.fleenRasterCompositionGen.Log;


public class Renderer_000 extends Renderer_Abstract{
  
  private static final long serialVersionUID=-8294845813561601629L;

  static final Color
    COLOR_POLYGONEDGE=new Color(128,128,128),
    COLOR_POLYGONFILL_SHARDFOAM=new Color(238,238,238,192),
    COLOR_POLYGONFILL_LAKEFOAM=new Color(255,226,143,192);
  
  private static final int ALPHA=255;
  
//  static final Color[] FOAMCOLORS={
//    new Color(230,150,150,ALPHA),
//    new Color(230,190,150,ALPHA),
//    new Color(229,230,150,ALPHA),
//    new Color(190,230,150,ALPHA),
//    new Color(150,229,230,ALPHA),
//    new Color(150,190,230,ALPHA),
//    new Color(150,150,230,ALPHA),
//    new Color(190,150,230,ALPHA),
//    new Color(230,150,229,ALPHA),
//    new Color(230,150,190,ALPHA)};
  
//  static final Color[] FOAMCOLORS={
//    new Color(135,197,249,ALPHA),
//    new Color(187,223,233,ALPHA),
//    new Color(227,237,215,ALPHA),
//    new Color(255,212,97,ALPHA),
//    new Color(234,255,119,ALPHA)};
  
  static final Color[] FOAMCOLORS={
    new Color(0,0,0,ALPHA),
    new Color(255,255,255,ALPHA)};

  public static final float 
    POLYGON_LINE_STROKE_WIDTH=1.0f;
  
  public BufferedImage getImage(Composition fleen,Rectangle2D.Double bounds,AffineTransform transform){
    //init image
    double scale=transform.getScaleX();
    int w=(int)(bounds.getWidth()*scale),h=(int)(bounds.getHeight()*scale);
    BufferedImage image=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics=image.createGraphics();
    graphics.addRenderingHints(RENDERING_HINTS);
    graphics.setTransform(transform);
    //get scaled metrics
    float polygonstrokewidth=(float)(POLYGON_LINE_STROKE_WIDTH/scale);
    //render bubbles
    Path2D path;
    Color c;
    int bcount=0;
    for(Bubble bubble:fleen.getBubbles()){
      bcount++;
      if(bcount%512==0)Log.mp();
      path=Util.getBubbleData(bubble).getPath2D();
      //FILL POLYGON
      if(path!=null){
        c=FOAMCOLORS[bubble.foam.getLevel()%FOAMCOLORS.length];
        graphics.setPaint(c);
        graphics.setStroke(new BasicStroke(polygonstrokewidth));
        graphics.fill(path);}
      //STROKE POLYGON
      if(path!=null){
        graphics.setPaint(COLOR_POLYGONEDGE);
        graphics.setStroke(new BasicStroke(polygonstrokewidth));
        graphics.draw(path);}
      }
    return image;}

}
