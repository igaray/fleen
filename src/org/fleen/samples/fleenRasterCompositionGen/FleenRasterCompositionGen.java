package org.fleen.samples.fleenRasterCompositionGen;

import java.awt.image.BufferedImage;

import org.fleen.core.diamondGrammar.DGComposition;

/*
 * create a diamond composition
 * from that create a quilt
 * create a colormap too
 * render quilt and colormap to a bitmap image, stick the image in a directory
 * do that a bunch of times
 *  
 */
public class FleenRasterCompositionGen{
  
  private static final String VERSIONID="0.1 alpha";
  //
  
  private static final double DETAIL_LIMIT_DEFAULT=0.03;
  private static final int 
    IMAGE_COUNT_DEFAULT=3,
    IMAGE_WIDTH_DEFAULT=1300;
  private static final Renderer_000 renderer=new Renderer_000();
  private static FileWriterPNG filewriter=new FileWriterPNG();
  private static final String EXPORTDIR="/home/john/Desktop/generator_export/";
  
  public static final void main(String[] a){
    
    
    
    System.out.println("<><><><>GENERATOR STARTED<><><><>");
    System.out.println("   Creating ["+IMAGE_COUNT_DEFAULT+"] images");
    System.out.println("----------------------------------");
    
    for(int i=0;i<IMAGE_COUNT_DEFAULT;i++){
      System.out.println("COMPOSITION #"+i+" START");
      System.out.println("BUILDING");
      DGComposition dgc=new DGC0(DETAIL_LIMIT_DEFAULT);
      System.out.println("RENDERING");
      BufferedImage image=renderer.render(dgc,IMAGE_WIDTH_DEFAULT);
      System.out.println("EXPORTING");
      filewriter.write(image,EXPORTDIR,300);
      System.out.println("COMPOSITION #"+i+" END");}
    
    System.out.println("--+--+--GENERATOR FINISHED--+--+--");
    
  }

}
