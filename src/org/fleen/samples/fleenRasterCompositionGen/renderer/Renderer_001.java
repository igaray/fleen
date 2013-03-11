package org.fleen.samples.fleenRasterCompositionGen.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.fleen.core.diamondGrammar.Bubble;
import org.fleen.core.diamondGrammar.DGComposition;
import org.fleen.samples.diamondCompositionInspector.Util;
import org.fleen.samples.fleenRasterCompositionGen.Composition;


public class Renderer_001 extends Renderer_Abstract{

  static final Color
    COLOR_POLYGONEDGE=new Color(110,110,110),
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
  
  static final Color[] FOAMCOLORS={
    new Color(135,197,249,ALPHA),
    new Color(187,223,233,ALPHA),
    new Color(227,237,215,ALPHA),
    new Color(255,212,97,ALPHA),
    new Color(234,255,119,ALPHA)};

  public static final float 
    POLYGON_LINE_STROKE_WIDTH=1.0f;
  
  public BufferedImage render(Composition fleen,double scale){
    Rectangle2D.Double dgcb=getRootBubbleBounds(fleen);
    //transform
    double
      offx=-dgcb.getMinX(),
      offy=-dgcb.getMinY();
    AffineTransform transform=new AffineTransform();
    transform.scale(scale,scale);
    transform.translate(offx,offy);
    //init image
    BufferedImage image=new BufferedImage(
      (int)(dgcb.getWidth()*scale),(int)(dgcb.getHeight()*scale),BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics=image.createGraphics();
    graphics.addRenderingHints(RENDERING_HINTS);
    graphics.setTransform(transform);
    //get scaled metrics
    float polygonstrokewidth=(float)(POLYGON_LINE_STROKE_WIDTH/scale);
    //render bubbles
    Path2D path;
    Color c;
    for(Bubble bubble:fleen.getBubbles()){
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
  
  Rectangle2D.Double getRootBubbleBounds(DGComposition dgc){
    Bubble a=dgc.getRootGrid().getChildBubbles().get(0);
    double[][] vp=a.getVertexPoints2D();
    double maxx=Double.MIN_VALUE,maxy=maxx,minx=Double.MAX_VALUE,miny=minx;
    for(int i=0;i<vp.length;i++){
      if(minx>vp[i][0])minx=vp[i][0];
      if(miny>vp[i][1])miny=vp[i][1];
      if(maxx<vp[i][0])maxx=vp[i][0];
      if(maxy<vp[i][1])maxy=vp[i][1];}
    return new Rectangle2D.Double(minx,miny,maxx-minx,maxy-miny);}

}
