package org.fleen.grammarEditor.jigEditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.fleen.grammarEditor.C;
import org.fleen.grammarEditor.grammarProject.GPBubbleModel;

public class JigEditorBMListBubbleModelImage extends BufferedImage{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  //for valid
  public JigEditorBMListBubbleModelImage(GPBubbleModel m){
    super(
      C.JE_BMLISTCELLSPAN,
      C.JE_BMLISTCELLSPAN,
      BufferedImage.TYPE_INT_ARGB_PRE);
    render(m);}
  
  //for invalid
  private JigEditorBMListBubbleModelImage(){
    super(
      C.JE_BMLISTCELLSPAN,
      C.JE_BMLISTCELLSPAN,
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
    int polygonimagespan=C.JE_BMLISTCELLSPAN-C.JE_BMLISTCELLPADDING-C.JE_BMLISTCELLPADDING;
    Graphics2D g=createGraphics();
    g.setRenderingHints(C.RENDERING_HINTS);
    //scale and center
    scale=(pw>ph)?polygonimagespan/pw:polygonimagespan/ph;
    AffineTransform t=new AffineTransform();
    t.scale(scale,-scale);//note y flip
    double 
      xoffset=-pbounds.getMinX()+(((C.JE_BMLISTCELLSPAN/scale)-pbounds.getWidth())/2.0),
      yoffset=-pbounds.getMaxY()-(((C.JE_BMLISTCELLSPAN/scale)-pbounds.getHeight())/2.0);
    t.translate(xoffset,yoffset);
    g.transform(t);
    //fill it
    g.setColor(C.OG_COLOR_BUBBLEMODELIMAGEVALIDFILL);
    g.fill(polygon);
    //stroke it
    g.setColor(C.OG_COLOR_BUBBLEMODELIMAGEVALIDSTROKE);
    g.setStroke(new BasicStroke((float)(C.OG_STROKEWIDTH_IMAGEBUBBLEMODELVALID/scale)));
    g.draw(polygon);
    //render v0 and v1
    renderV0V1(g,bubblemodel,scale);}
  
  static final double VERTEXSPAN=8;
  
  static final Rectangle2D.Double SQUARE=new Rectangle2D.Double();
  
  private void renderV0V1(Graphics2D graphics,GPBubbleModel bubblemodel,double scale){
    double[] 
      v0=bubblemodel.vertexpath.get(0).getBasicPoint2D(),
      v1=bubblemodel.vertexpath.get(1).getBasicPoint2D();
    SQUARE.setFrame(
      v0[0]-(VERTEXSPAN/scale)/2.0,
      v0[1]-(VERTEXSPAN/scale)/2.0,
      VERTEXSPAN/scale,
      VERTEXSPAN/scale);
    graphics.setColor(Color.green);
    graphics.fill(SQUARE);
    graphics.setColor(Color.red);
    SQUARE.setFrame(
      v1[0]-(VERTEXSPAN/scale)/2.0,
      v1[1]-(VERTEXSPAN/scale)/2.0,
      VERTEXSPAN/scale,
      VERTEXSPAN/scale);
    graphics.fill(SQUARE);}
  
  /*
   * ################################
   * RENDER INVALID BUBBLEMODEL
   * ################################
   */
  
  public static final JigEditorBMListBubbleModelImage IMAGE_FOR_INVALID_BUBBLEMODEL=
    new JigEditorBMListBubbleModelImage();
  
  private void render(){
    Graphics2D g=createGraphics();
    g.setStroke(new BasicStroke((float)C.OG_STROKEWIDTH_IMAGEBUBBLEMODELINVALID));
    //do box
    Path2D p=new Path2D.Double();
    p.moveTo(C.JE_BMLISTCELLSPAN*0.2,C.JE_BMLISTCELLSPAN*0.2);
    p.lineTo(C.JE_BMLISTCELLSPAN*0.8,C.JE_BMLISTCELLSPAN*0.2);
    p.lineTo(C.JE_BMLISTCELLSPAN*0.8,C.JE_BMLISTCELLSPAN*0.8);
    p.lineTo(C.JE_BMLISTCELLSPAN*0.2,C.JE_BMLISTCELLSPAN*0.8);
    p.closePath();
    g.setColor(C.OG_COLOR_BUBBLEMODELIMAGEINVALIDFILL);
    g.fill(p);
    g.setColor(C.OG_COLOR_BUBBLEMODELIMAGEINVALIDSTROKE);
    g.draw(p); 
    //do X
    p=new Path2D.Double();
    p.moveTo(C.JE_BMLISTCELLSPAN*0.4,C.JE_BMLISTCELLSPAN*0.4);
    p.lineTo(C.JE_BMLISTCELLSPAN*0.6,C.JE_BMLISTCELLSPAN*0.6);
    p.moveTo(C.JE_BMLISTCELLSPAN*0.4,C.JE_BMLISTCELLSPAN*0.6);
    p.lineTo(C.JE_BMLISTCELLSPAN*0.6,C.JE_BMLISTCELLSPAN*0.4);
    g.draw(p);}

}
