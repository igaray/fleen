package org.fleen.grammarEditor.jigEditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.util.List;

import org.fleen.core.grammar.DVectorRDPath;
import org.fleen.core.grammar.DVertex;
import org.fleen.core.grammar.DVertexPath;
import org.fleen.core.grammar.MathDiamond;
import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.grammarProject.C_GPFocusBubbleModelTouchVertex;
import org.fleen.grammarEditor.grammarProject.GPJigBubbleDef;
import org.fleen.grammarEditor.util.DiamondCanvas_Abstract;

@SuppressWarnings("serial")
public class JigEditorCanvas extends DiamondCanvas_Abstract{

  /*
   * ################################
   * MOUSE CLICK VERTEX
   * ################################
   */
  
  //if we have a focus bubble def this adds or removes a v0 or v1
  protected void mouseClickVertex(int[] v){
    GE.command(new C_GPFocusBubbleModelTouchVertex(),new DVertex(v[0],v[1],v[2],+v[3]));
    GE.command(new C_JigEditorRefreshAll());
    }
  
  /*
   * ################################
   * VIEW CONTROL FIELDS INTERFACE
   * ################################
   */
    
  private static final DVertex VIEWCENTER_DEFAULT=new DVertex(0,0,0,0);
  private static final int GRIDZOOM_DEFAULT=0;
  
  protected DVertex getViewCenter(){
    if(GE.grammarproject.hasFocusJig()){
      return GE.grammarproject.focusbubblemodel.focusjig.jecviewcenter;
    }else{
      return VIEWCENTER_DEFAULT;}}
  
  protected void setViewCenter(DVertex v){
    if(GE.grammarproject.hasFocusJig())
      GE.grammarproject.focusbubblemodel.focusjig.jecviewcenter=v;}

  protected int getZoom(){
    if(GE.grammarproject.hasFocusJig()){
      return GE.grammarproject.focusbubblemodel.focusjig.jecviewzoom;
    }else{
      return GRIDZOOM_DEFAULT;}}
  
  protected void setZoom(int z){
    if(GE.grammarproject.hasFocusJig())
      GE.grammarproject.focusbubblemodel.focusjig.jecviewzoom=z;}

  /*
   * ################################
   * GRID IMAGE
   * ################################
   */
  
  private static final Color 
    COLOR_GRIDLINE=new Color(164,182,127),
    COLOR_GRIDBACKGROUND=new Color(188,209,146);

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
//  COLOR_STROKE_INVALIDBUBBLEMODELPATH=new Color(255,0,0,255),
  COLOR_FILL_VALIDBUBBLEMODELPATH=new Color(255,255,255,64),
  COLOR_STROKE_VALIDBUBBLEMODELPATH=new Color(255,255,255,128),
  COLOR_STROKE_BUBBLEDEFDEFAULT=new Color(0,255,255,128),
  COLOR_STROKE_BUBBLEDEFFOCUS=new Color(255,0,255,128),
  COLOR_VERTEX_BUBBLEDEFFOCUSV0=new Color(0,255,0,128),
  COLOR_VERTEX_BUBBLEDEFFOCUSV1=new Color(255,0,0,128),
  COLOR_STROKE_ORIGIN=new Color(64,64,255,128);

  private static final int 
    THICKNESS_BUBBLEMODELPATH=6,
    
    THICKNESS_ORIGINBOX=4;

  private static final Stroke 
    STROKE_ORIGINBOX=new BasicStroke(THICKNESS_ORIGINBOX),
    STROKE_PATHBUBBLEMODEL=new BasicStroke(
      THICKNESS_BUBBLEMODELPATH,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0),
    STROKE_PATHBUBBLEDEF=new BasicStroke(
      THICKNESS_BUBBLEMODELPATH,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);

  private static final int
    SPAN_VERTEX_DEFAULT=14,
    SPAN_ORIGINBOX=18;

  private static final DVertex ORIGIN=new DVertex(0,0,0,0);
  
  //if the bubblemodel is valid then we render it and the bubbledefs.
  //if it isn't then we do some kind of invalidity icon
  protected void createDetailsImage(){
    super.createDetailsImage();
    if(!GE.grammarproject.hasFocusBubbleModel())return;
    Graphics2D graphics=detailsimage.createGraphics();
    graphics.setRenderingHints(RENDERING_HINTS);
    //render origin
    graphics.setPaint(COLOR_STROKE_ORIGIN);
    graphics.setStroke(STROKE_ORIGINBOX);
    double[] p=getPoint2D(ORIGIN);
    graphics.drawRect(
      (int)(p[0]-SPAN_ORIGINBOX/2),
      (int)(p[1]-SPAN_ORIGINBOX/2),
      SPAN_ORIGINBOX,
      SPAN_ORIGINBOX);
    //RENDER BUBBLEMODEL
    if(!GE.grammarproject.hasFocusJig())return;
    List<DVertex> vertices=GE.grammarproject.focusbubblemodel.focusjig.getBubbleModelVertexPathForJigEditor();
    if(vertices.isEmpty())return;
    List<double[]> points=getPoint2Ds(vertices);
    //render invalid bubblemodel icon if necessary
    if(!GE.grammarproject.focusbubblemodel.isValid()){
      renderInvalidBubbleModelIcon();
      return;}
    //render valid bubblemodel
    renderBubbleModelPath(graphics,vertices,points);
    //RENDER BUBBLE DEFS
    renderBubbleDefPaths(graphics);
    renderFocusBubbleDefV0V1(graphics);}
  
  private void renderInvalidBubbleModelIcon(){
    //TODO
  }
  
  private void renderBubbleModelPath(Graphics2D graphics,List<DVertex> vertices,List<double[]> points){
    int vertexcount=vertices.size();
    Path2D.Double path=new Path2D.Double();
    double[] p=points.get(0);
    path.moveTo(p[0],p[1]);
    for(int i=1;i<vertexcount;i++){
      p=points.get(i);
      path.lineTo(p[0],p[1]);}
    path.closePath();
    graphics.setPaint(COLOR_FILL_VALIDBUBBLEMODELPATH);
    graphics.fill(path);
    graphics.setPaint(COLOR_STROKE_VALIDBUBBLEMODELPATH);
    graphics.setStroke(STROKE_PATHBUBBLEMODEL);
    graphics.draw(path);}
  
  private void renderBubbleDefPaths(Graphics2D graphics){
    List<GPJigBubbleDef> bubbledefs=GE.grammarproject.focusbubblemodel.focusjig.bubbledefs;
    if(bubbledefs.isEmpty())return;
    GPJigBubbleDef focusbubbledef=GE.grammarproject.focusbubblemodel.focusjig.focusbubbledef;
    Path2D path;
    graphics.setStroke(STROKE_PATHBUBBLEDEF);
    for(GPJigBubbleDef bd:bubbledefs){
      if(bd.equals(focusbubbledef)){
        graphics.setPaint(COLOR_STROKE_BUBBLEDEFFOCUS);
      }else{
        graphics.setPaint(COLOR_STROKE_BUBBLEDEFDEFAULT);}
      path=getBubbleDefPath(bd);
      if(path!=null)
        graphics.draw(path);}}
  
  private Path2D.Double getBubbleDefPath(GPJigBubbleDef bd){
    Path2D.Double path=null;
    if(bd.bubblemodel==null||bd.v0==null||bd.v1==null)return null;
    try{
      DVertexPath p0=bd.bubblemodel.getVertexPath();
      DVectorRDPath p1=p0.getVectorPath();
      if(bd.twist==MathDiamond.TWIST_NEGATIVE)
        p1.reverseDeltas();
      p0=p1.getVertexPath(bd.v0,bd.v1);
      List<double[]> points=getPoint2Ds(p0);
      path=new Path2D.Double();
      double[] p=points.get(0);
      path.moveTo(p[0],p[1]);
      for(int i=1;i<points.size();i++){
        p=points.get(i);
        path.lineTo(p[0],p[1]);}
      path.closePath();
    }catch(Exception e){
      System.out.println("GET BUBBLE DEF PATH FAILED");
      return null;}
    return path;}
  
  private void renderFocusBubbleDefV0V1(Graphics2D graphics){
    GPJigBubbleDef focusbubbledef=GE.grammarproject.focusbubblemodel.focusjig.focusbubbledef;
    if(focusbubbledef==null)return;
    double[] p;
    if(focusbubbledef.v0!=null){
      p=getPoint2D(focusbubbledef.v0);
      graphics.setColor(COLOR_VERTEX_BUBBLEDEFFOCUSV0);
      graphics.fillRect(
        (int)(p[0]-SPAN_VERTEX_DEFAULT/2),
        (int)(p[1]-SPAN_VERTEX_DEFAULT/2),
        SPAN_VERTEX_DEFAULT,
        SPAN_VERTEX_DEFAULT);}
    if(focusbubbledef.v1!=null){
      p=getPoint2D(focusbubbledef.v1);
      graphics.setColor(COLOR_VERTEX_BUBBLEDEFFOCUSV1);
      graphics.fillRect(
        (int)(p[0]-SPAN_VERTEX_DEFAULT/2),
        (int)(p[1]-SPAN_VERTEX_DEFAULT/2),
        SPAN_VERTEX_DEFAULT,
        SPAN_VERTEX_DEFAULT);}}
  
}
