package org.fleen.samples.fleenRasterCompositionGen.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import org.fleen.core.pGrammatic.GBubble;
import org.fleen.samples.fleenRasterCompositionGen.Composition;
import org.fleen.samples.fleenRasterCompositionGen.Log;


public class Renderer_006 extends Renderer_Abstract{
  
  private static final long serialVersionUID=-8294845813561601629L;

  private static final int ALPHA=255;
  
  static final Color COLOR_POLYGONEDGE=new Color(82,195,137,ALPHA);
  static final Color[] FOAMCOLORS={
    new Color(93,82,195,ALPHA),
    new Color(116,107,192,ALPHA),
    new Color(139,133,190,ALPHA),
    new Color(165,163,188,ALPHA)};
  
  public static final float 
    POLYGON_LINE_STROKE_WIDTH=2.0f;
  
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
