package org.fleen.grammarEditor.overview;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.fleen.grammarEditor.C;
import org.fleen.grammarEditor.grammarProject.GPBubbleModel;

public class OverviewGridBubbleModelImage extends BufferedImage{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  //for valid
  public OverviewGridBubbleModelImage(GPBubbleModel m){
    super(
      C.OG_CELLSPAN,
      C.OG_CELLSPAN,
      BufferedImage.TYPE_INT_ARGB_PRE);
    render(m);}
  
  //for invalid
  private OverviewGridBubbleModelImage(){
    super(
      C.OG_CELLSPAN,
      C.OG_CELLSPAN,
      BufferedImage.TYPE_INT_ARGB_PRE);
    render();}
  
  /*
   * ################################
   * RENDER VALID BUBBLEMODEL
   * ################################
   */
  
  private void render(GPBubbleModel bubblemodel){
    //init image and metrics
    Path2D polygon=bubblemodel.getImagePath();
    Rectangle2D pbounds=polygon.getBounds2D();
    double pw=pbounds.getWidth(),ph=pbounds.getHeight(),scale;
    int polygonimagespan=C.OG_CELLSPAN-C.OG_ICONPADDING-C.OG_ICONPADDING;
    Graphics2D g=createGraphics();
    g.setRenderingHints(C.RENDERING_HINTS);
    //scale and center
    scale=(pw>ph)?polygonimagespan/pw:polygonimagespan/ph;
    AffineTransform t=new AffineTransform();
    t.scale(scale,-scale);//note y flip
    double 
      xoffset=-pbounds.getMinX()+(((C.OG_CELLSPAN/scale)-pbounds.getWidth())/2.0),
      yoffset=-pbounds.getMaxY()-(((C.OG_CELLSPAN/scale)-pbounds.getHeight())/2.0);
    t.translate(xoffset,yoffset);
    g.transform(t);
    //fill it
    g.setColor(C.OG_COLOR_BUBBLEMODELIMAGEVALIDFILL);
    g.fill(polygon);
    //stroke it
    g.setColor(C.OG_COLOR_BUBBLEMODELIMAGEVALIDSTROKE);
    g.setStroke(new BasicStroke(
      (float)(C.OG_STROKEWIDTH_IMAGEBUBBLEMODELVALID/scale),
      BasicStroke.CAP_SQUARE,
      BasicStroke.JOIN_ROUND,
      0,null,0));
    g.draw(polygon);}
  
  /*
   * ################################
   * RENDER INVALID BUBBLEMODEL
   * ################################
   */
  
  public static final OverviewGridBubbleModelImage IMAGE_FOR_INVALID_BUBBLEMODEL=
    new OverviewGridBubbleModelImage();
  
  private void render(){
    Graphics2D g=createGraphics();
    g.setStroke(new BasicStroke(
      (float)C.OG_STROKEWIDTH_IMAGEBUBBLEMODELINVALID,
      BasicStroke.CAP_SQUARE,
      BasicStroke.JOIN_ROUND,
      0,null,0));
    //do box
    Path2D p=new Path2D.Double();
    p.moveTo(C.OG_CELLSPAN*0.2,C.OG_CELLSPAN*0.2);
    p.lineTo(C.OG_CELLSPAN*0.8,C.OG_CELLSPAN*0.2);
    p.lineTo(C.OG_CELLSPAN*0.8,C.OG_CELLSPAN*0.8);
    p.lineTo(C.OG_CELLSPAN*0.2,C.OG_CELLSPAN*0.8);
    p.closePath();
    g.setColor(C.OG_COLOR_BUBBLEMODELIMAGEINVALIDFILL);
    g.fill(p);
    g.setColor(C.OG_COLOR_BUBBLEMODELIMAGEINVALIDSTROKE);
    g.draw(p); 
    //do X
    p=new Path2D.Double();
    p.moveTo(C.OG_CELLSPAN*0.4,C.OG_CELLSPAN*0.4);
    p.lineTo(C.OG_CELLSPAN*0.6,C.OG_CELLSPAN*0.6);
    p.moveTo(C.OG_CELLSPAN*0.4,C.OG_CELLSPAN*0.6);
    p.lineTo(C.OG_CELLSPAN*0.6,C.OG_CELLSPAN*0.4);
    g.draw(p);}

}
