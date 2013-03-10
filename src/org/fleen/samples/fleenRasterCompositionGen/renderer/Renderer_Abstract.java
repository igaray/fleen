package org.fleen.samples.fleenRasterCompositionGen.renderer;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.fleen.core.diamondGrammar.DGComposition;

public abstract class Renderer_Abstract{
  
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
    
  protected abstract BufferedImage render(DGComposition composition,double scale);
  
  public String toString(){
    return this.getClass().getSimpleName();
  }

}
