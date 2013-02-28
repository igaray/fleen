package org.fleen.grammarEditor;

import java.awt.Color;
import java.awt.RenderingHints;
import java.util.HashMap;

/*
 * GLOBAL CONSTANTS
 */
public class C{
  
  /*
   * ################################
   * GRAPHICS
   * ################################
   */
  
  //RENDERING HINTS
  public static final HashMap<RenderingHints.Key,Object> RENDERING_HINTS=
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
    
  /*
   * ################################
   * OVERVIEW GRID 
   * ################################
   */
    
  public static final int 
    OG_CELLSPAN=64,
    OG_ICONPADDING=5;
  
  public static final Color 
    //general grid colors
    OG_COLOR_BACKGROUND=new Color(192,192,192),
    OG_COLOR_FOCUSBUBBLEMODELROWBACKGROUND=new Color(255,249,181),
    OG_COLOR_FOCUSJIGCELLBACKGROUND=new Color(255,128,0), 
    //bubble model colors
    OG_COLOR_BUBBLEMODELIMAGEVALIDSTROKE=new Color(32,32,32),
    OG_COLOR_BUBBLEMODELIMAGEVALIDFILL=new Color(189,197,237),
    OG_COLOR_BUBBLEMODELIMAGEINVALIDSTROKE=new Color(0,0,0),
    OG_COLOR_BUBBLEMODELIMAGEINVALIDFILL=new Color(142,162,209),
    //jig colors
    OG_COLOR_JIGIMAGEVALIDSTROKE=new Color(32,32,32),
    OG_COLOR_JIGIMAGEVALIDFILL=Color.white;
  
  public static final double 
    OG_STROKEWIDTH_IMAGEBUBBLEMODELVALID=3.0,
    OG_STROKEWIDTH_IMAGEBUBBLEMODELINVALID=6.0;
    
  /*
   * ################################
   * JIG EDITOR
   * ################################
   */
  
  public static final int 
    JE_BMLISTCELLSPAN=64,
    JE_BMLISTCELLPADDING=5;
      
}
