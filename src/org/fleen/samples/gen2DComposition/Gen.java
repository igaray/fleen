package org.fleen.samples.gen2DComposition;

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
public class Gen{
  private static final double DETAILLIMIT=0.03;
  private static final int 
    IMAGECOUNT=3,
    IMAGEWIDTH=1300;
  private static final Renderer_000 renderer=new Renderer_000();
  private static FileWriterPNG filewriter=new FileWriterPNG();
  private static final String EXPORTDIR="/home/john/Desktop/generator_export/";
  
  public static final void main(String[] a){
    
    System.out.println("<><><><>GENERATOR STARTED<><><><>");
    System.out.println("   Creating ["+IMAGECOUNT+"] images");
    System.out.println("----------------------------------");
    
    for(int i=0;i<IMAGECOUNT;i++){
      System.out.println("COMPOSITION #"+i+" START");
      System.out.println("BUILDING");
      DGComposition dgc=new DGC0(DETAILLIMIT);
      System.out.println("RENDERING");
      BufferedImage image=renderer.render(dgc,IMAGEWIDTH);
      System.out.println("EXPORTING");
      filewriter.write(image,EXPORTDIR,300);
      System.out.println("COMPOSITION #"+i+" END");}
    
    System.out.println("--+--+--GENERATOR FINISHED--+--+--");
    
  }

}
