package org.fleen.samples.diamondCompositionInspector.faceView;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.fleen.core.diamondGrammar.Bubble;
import org.fleen.core.diamondGrammar.DGComposition;
import org.fleen.samples.diamondCompositionInspector.Util;


public class Renderer_Bubble1 extends Renderer_Abstract{

  private static HashMap<RenderingHints.Key,Object> RENDERING_HINTS=
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
  
  static final Color
    COLOR_POLYGONEDGE=new Color(255,0,0),
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
  
  protected BufferedImage getImage(){
    //get fleen, transform, scale, image
    DGComposition fleen=viewer.fleen;
    if(fleen==null)return null;
    AffineTransform transform=viewer.transform;
    double scale=transform.getScaleX();
    BufferedImage image=new BufferedImage(
      viewer.getWidth(),viewer.getHeight(),BufferedImage.TYPE_INT_ARGB);
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

}
