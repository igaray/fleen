package org.fleen.grammarEditor.bubbleModelEditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.util.List;

import org.fleen.core.diamondGrammar.DVertex;
import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.grammarProject.C_GPAddVertexToFocusBubbleModel;
import org.fleen.grammarEditor.util.DiamondCanvas_Abstract;

@SuppressWarnings("serial")
public class BubbleModelEditorCanvas extends DiamondCanvas_Abstract{

  /*
   * ################################
   * MOUSE CLICK VERTEX
   * ################################
   */
  
  protected void mouseClickVertex(int[] v){
    GE.command(new C_GPAddVertexToFocusBubbleModel(),new DVertex(v[0],v[1],v[2],+v[3]));
    GE.command(new C_BubbleModelEditorRefreshForModelChange());}
  
  /*
   * ################################
   * VIEW CONTROL FIELDS INTERFACE
   * ################################
   */
    
  protected DVertex getViewCenter(){
    return GE.grammarproject.focusbubblemodel.bmecviewcenter;}
  
  protected void setViewCenter(DVertex v){
    GE.grammarproject.focusbubblemodel.bmecviewcenter=v;}

  protected int getZoom(){
    if(GE.grammarproject.hasFocusBubbleModel()){
      return GE.grammarproject.focusbubblemodel.bmecviewzoom;
    }else{
      return 0;}}
  
  protected void setZoom(int z){
    GE.grammarproject.focusbubblemodel.bmecviewzoom=z;}

  /*
   * ################################
   * GRID IMAGE
   * ################################
   */
  
  private static final Color 
    COLOR_GRIDLINE=new Color(105,128,155),
    COLOR_GRIDBACKGROUND=new Color(139,169,205);

  private static final int THICKNESS_GRIDLINE=2;
  
  protected int getGridLineThickness(){
    return THICKNESS_GRIDLINE;}
  
  protected Color getGridLineColor(){
    return COLOR_GRIDLINE;}
  
  protected Color getGridBackgroundColor(){
    return COLOR_GRIDBACKGROUND;}
  
  /*
   * ################################
   * DETAILS IMAGE
   * ################################
   */
  
  private static final Color 
    COLOR_PATH_VALID=new Color(255,235,48,255),
    COLOR_PATH_INVALID=new Color(255,255,255,128),
    COLOR_PATH_FILL=new Color(222,255,222,96),
    COLOR_VERTEX_V0=new Color(64,220,64,128),
    COLOR_VERTEX_V1=new Color(200,0,0,128),
    COLOR_VERTEX_DEFAULT=new Color(255,255,255,128),
    COLOR_ORIGIN=new Color(64,64,255,128);
  
  private static final int 
    THICKNESS_PATH=6,
    THICKNESS_ORIGINBOX=4;
  
  private static final Stroke 
    STROKE_ORIGINBOX=new BasicStroke(THICKNESS_ORIGINBOX),
    STROKE_PATHDEFAULT=new BasicStroke(
     THICKNESS_PATH,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0),
    STROKE_PATHCONNECT=new BasicStroke(
      THICKNESS_PATH,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,0,new float[]{12,8},0);
  
  private static final int
    SPAN_VERTEX_DEFAULT=14,
    SPAN_VERTEX_SPECIAL=14,
    SPAN_ORIGINBOX=18;
  
  private static final DVertex ORIGIN=new DVertex(0,0,0,0);
  
  protected void createDetailsImage(){
    super.createDetailsImage();
    if(!GE.grammarproject.hasFocusBubbleModel())return;
    Graphics2D graphics=detailsimage.createGraphics();
    graphics.setRenderingHints(RENDERING_HINTS);
    //render origin
    graphics.setPaint(COLOR_ORIGIN);
    graphics.setStroke(STROKE_ORIGINBOX);
    double[] p=getPoint2D(ORIGIN);
    graphics.drawRect(
      (int)(p[0]-SPAN_ORIGINBOX/2),
      (int)(p[1]-SPAN_ORIGINBOX/2),
      SPAN_ORIGINBOX,
      SPAN_ORIGINBOX);
    //render model form and associated details
    List<DVertex> vertices=GE.grammarproject.focusbubblemodel.getVertexPath();
    if(vertices.isEmpty())return;
    List<double[]> points=getPoint2Ds(vertices);
//    renderGlyphs();//origin location foreward and twist. model foreward and twist
    renderPaths(graphics,vertices,points);
    renderVertices(graphics,vertices,points);}
  
  private void renderVertices(Graphics2D graphics,List<DVertex> vertices,List<double[]> points){
    double[] p;
    int span;
    Color color;
    for(int i=0;i<points.size();i++){
      p=points.get(i);
      if(i==0){
        span=SPAN_VERTEX_SPECIAL;
        color=COLOR_VERTEX_V0;
      }else if(i==1){
        span=SPAN_VERTEX_SPECIAL;
        color=COLOR_VERTEX_V1;
      }else{
        span=SPAN_VERTEX_DEFAULT;
        color=COLOR_VERTEX_DEFAULT;}
      graphics.setPaint(color);
      graphics.fillRect(((int)p[0])-span/2,((int)p[1])-span/2,span,span);}
    
    
    }
  
  private void renderPaths(Graphics2D graphics,List<DVertex> vertices,List<double[]> points){
    int vertexcount=vertices.size();
    if(vertexcount<2)return;
    DVertex vlast=vertices.get(vertices.size()-1),vfirst=vertices.get(0);
    boolean colinearends=vlast.isColinear(vfirst);
    //RENDER INVALID FORM
    if(vertexcount==2||(!colinearends)){
      Path2D.Double path=new Path2D.Double();
      double[] p=points.get(0);
      path.moveTo(p[0],p[1]);
      for(int i=1;i<vertexcount;i++){
        p=points.get(i);
        path.lineTo(p[0],p[1]);}
      graphics.setPaint(COLOR_PATH_INVALID);
      graphics.setStroke(STROKE_PATHDEFAULT);
      graphics.draw(path);
    //RENDER VALID FORM
    //vertex count is >2 and the ends are colinear
    }else{
      //draw path for vertices
      Path2D.Double path0=new Path2D.Double();
      double[] p=points.get(0);
      path0.moveTo(p[0],p[1]);
      for(int i=1;i<vertexcount;i++){
        p=points.get(i);
        path0.lineTo(p[0],p[1]);}
      graphics.setPaint(COLOR_PATH_VALID);
      graphics.setStroke(STROKE_PATHDEFAULT);
      graphics.draw(path0);
      //draw path connecting end vertices
      Path2D.Double path1=new Path2D.Double();
      p=points.get(0);
      double[] p0=points.get(vertexcount-1);
      path1.moveTo(p[0],p[1]);
      path1.lineTo(p0[0],p0[1]);
      graphics.setStroke(STROKE_PATHCONNECT);
      graphics.draw(path1);
      //fill it
      path0.closePath();
      graphics.setPaint(COLOR_PATH_FILL);
      graphics.fill(path0);}}
  
}
