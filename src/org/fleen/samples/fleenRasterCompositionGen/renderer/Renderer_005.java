package org.fleen.samples.fleenRasterCompositionGen.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import org.fleen.core.pGrammatic.GBubble;
import org.fleen.samples.fleenRasterCompositionGen.Composition;
import org.fleen.samples.fleenRasterCompositionGen.Log;


public class Renderer_005 extends Renderer_Abstract{
  
  private static final long serialVersionUID=-8294845813561601629L;

  private static final int ALPHA=255;
  
  static final Color COLOR_POLYGONEDGE=new Color(192,41,66,ALPHA);
  static final Color[] FOAMCOLORS={
    new Color(236,208,120,ALPHA),
    new Color(217,91,67,ALPHA),
    new Color(84,36,55,ALPHA),
    new Color(83,119,122,ALPHA)};
  
  public static final float 
    POLYGON_LINE_STROKE_WIDTH=1.0f;
  
  protected void render(Composition fleen,Graphics2D graphics,AffineTransform transform){
    if(fleen==null)return;
    //get scaled metrics
    float polygonstrokewidth=(float)(POLYGON_LINE_STROKE_WIDTH/transform.getScaleX());
    //render bubbles
    Path2D path;
    Color c;
    int bcount=0;
    for(GBubble bubble:fleen.getBubbles()){
      bcount++;
      if(bcount%4096==0)Log.m1(".");
      path=getPath2D(bubble);
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
        graphics.draw(path);}}}

}
