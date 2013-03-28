package org.fleen.grammarEditor.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import org.fleen.core.grammar.DVertex;
import org.fleen.core.grammar.Math2D;
import org.fleen.core.grammar.MathDiamond;
import org.fleen.grammarEditor.GE;

/*
 * Handles low level rendering and interaction with a diamond grid
 */
@SuppressWarnings("serial")
public abstract class DiamondCanvas_Abstract extends JPanel{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public DiamondCanvas_Abstract(){
    addKeyListener(new KeyListener0());
    addMouseListener(new MouseListener0());
//    addMouseMotionListener(new MouseMotionListener0());
    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    setFocusable(true);
    addHierarchyBoundsListener(new HierarchyBoundsListener0());}
  
  /*
   * ################################
   * GRAPHIC INTERVAL
   * Our unit of length via which we control graphical scale 
   * zoom is an integer, from it we derive our basic unit of length, the "graphic interval"
   * values range [0,maxint]
   * graphicinterval=CONSTANT*(2^zoom)
   * graphic interval corrosponds to the diamond FISH value
   * ################################
   */
  
  static final int BASIC_GRAPHIC_INTERVAL=20;
 
  private int getGraphicInterval(){
    int i=BASIC_GRAPHIC_INTERVAL;
    int z=getZoom();
    if(z>0){
      i=(int)(BASIC_GRAPHIC_INTERVAL/Math.pow(2,z));
    }else{
      i=(int)(BASIC_GRAPHIC_INTERVAL*Math.pow(2,-z));}
    if(i<0)i=1;
    return i;}
  
  /*
   * ################################
   * MOUSE CLICK VERTEX
   * ################################
   */
  
  private class MouseListener0 extends MouseAdapter{
    
    public void mouseReleased(MouseEvent e){
      int[] a=getClosestDVertexCoorToMouse(e.getX(),e.getY());
      if(a!=null)
        mouseClickVertex(a);}}
  
  protected abstract void mouseClickVertex(int[] v);
  
  /*
   * ################################
   * MOUSE OVER VERTEX
   * ################################
   */
  
  //MOUSE MOTION LISTENER 
//  private static final long MOUSEMOTIONDELAY=100;
//  long lastmousemotion=0;
//  
//  private class MouseMotionListener0 extends MouseAdapter{
//    
//    public void mouseMoved(MouseEvent e){
//      long t=System.currentTimeMillis();
//      if(t-lastmousemotion>MOUSEMOTIONDELAY){
//        lastmousemotion=t;
//        int[] a=getClosestDVertexCoorToMouse(e.getX(),e.getY());
//        if(a!=null)
//          mouseOverVertex(a);
//        }}}
  
  
  /*
   * ################################
   * VIEW CONTROL BY KEY
   * ################################
   */
  
  class KeyListener0 extends KeyAdapter{
    
    char kc;
    int kcc;
    
    public void keyPressed(KeyEvent e){
//      e.consume();
      kc=e.getKeyChar();
      if(kc=='+'){
        zoomOut();
        return;
      }else if(kc=='-'){
        zoomIn();
        return;
      }else{
        kcc=e.getKeyCode();
        switch(kcc){
        case KeyEvent.VK_UP:
          panNorth();
          return;
        case KeyEvent.VK_RIGHT:
          panEast();
          return;
        case KeyEvent.VK_DOWN:
          panSouth();
          return;
        case KeyEvent.VK_LEFT:
          panWest();
          return;
        default:
          return;}}}};
          
          
  //ZOOM CONTROL
  private void zoomIn(){
    int a=getZoom();
    a++;
    setZoom(a);
    refreshAll();}
  
  private void zoomOut(){
    int a=getZoom();
    a--;
    if(a<0)return;
    setZoom(a);
    refreshAll();}
  
  //VIEWCENTER CONTROL
  //viewcenter is always a v12. we traverse nsew to the next v12
  
  private void panNorth(){
    //TODO
  }
  
  private void panSouth(){
  //TODO
  }
  
  private void panWest(){
  //TODO
  }
  
  private void panEast(){
  //TODO
  }
       
  /*
   * ################################
   * REFRESH FOR RESIZE
   * on HierarchyBoundsListener0.resize event 
   *   update lastresizeeventtime
   *   if we don't have a RefreshForResize thread running then create one and start it
   * RefreshForResize waits till specified delay past lastresizeevent has elapsed then executes refresh method
   * ################################
   */
          
  private static final long REFRESHFORRESIZEDELAY=1000;
  private long lastresizeeventtime;
  private RefreshForResizeThread refreshforresizethread=null;
  
  class HierarchyBoundsListener0 implements HierarchyBoundsListener{

    public void ancestorMoved(HierarchyEvent e){}

    public void ancestorResized(HierarchyEvent e){
      lastresizeeventtime=System.currentTimeMillis();
      if(refreshforresizethread==null){
        refreshforresizethread=new RefreshForResizeThread();
        refreshforresizethread.start();}}}
  
  class RefreshForResizeThread extends Thread{
    public void run(){
      setPriority(MIN_PRIORITY);
      while(System.currentTimeMillis()<lastresizeeventtime+REFRESHFORRESIZEDELAY){}//wait
      refreshforresizethread=null;
      refreshAll();}}
          
  /*
   * ################################
   * VIEW CONTROL FIELDS INTERFACE
   * control perspective with keys : +,-,up down,left,right
   * address vertices by mouseover and click
   * view control field storage is handled by subclasses 
   * ################################
   */
    
  protected abstract DVertex getViewCenter();
  
  protected abstract void setViewCenter(DVertex v);
  
  protected abstract int getZoom();
  
  protected abstract void setZoom(int z);
    
  /*
   * ################################
   * REFRESH 
   * ################################
   */
    
    public void refreshAll(){
      baseimage=null;
      gridimage=null;
      detailsimage=null;
      new RenderThread().start();}
    
    public void refreshDetails(){
      baseimage=null;
//      clearBaseImage();
      detailsimage=null;
      new RenderThread().start();}
  
  /*
   * ################################
   * IMAGE
   * ################################
   */
    
  private static final Color 
    COLOR_GRIDLINE_DEFAULT=Color.white,
    COLOR_GRIDBACKGROUND_DEFAULT=Color.red;
  
  private static final int THICKNESS_GRIDLINE_DEFAULT=2;
    
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
  
  private void validateCompoundedImage(){
    if((getWidth()<=0)||(getHeight()<=0))return;
    if(baseimage==null){
      createBaseImage();
      if(gridimage==null)
        createGridImage();
      if(detailsimage==null)
        createDetailsImage();  
      Graphics2D g=baseimage.createGraphics();
      g.drawImage(gridimage,null,null);
      g.drawImage(detailsimage,null,null);}}
  
  private class RenderThread extends Thread{
    
    public void run(){
      validateCompoundedImage();
      repaint();}}
  
  public void paint(Graphics g){
    super.paint(g);
    if((getWidth()<=0)||(getHeight()<=0))return;
    ((Graphics2D)g).drawImage(baseimage,null,null);}
  
  //override these 3 methods if we wanna change the look of the grid and background
  protected int getGridLineThickness(){
    return THICKNESS_GRIDLINE_DEFAULT;}
  
  protected Color getGridLineColor(){
    return COLOR_GRIDLINE_DEFAULT;}
  
  protected Color getGridBackgroundColor(){
    return COLOR_GRIDBACKGROUND_DEFAULT;}
  
  /*
   * ################################
   * BASE IMAGE
   * a blank background image upon which we paint our grid and details images
   * ################################
   */
  
  private BufferedImage baseimage=null;
  
  private void createBaseImage(){
    int w=getWidth(),h=getHeight();
    baseimage=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics=baseimage.createGraphics();
    graphics.setPaint(getGridBackgroundColor());
    graphics.fillRect(0,0,w,h);}
  
  /*
   * ################################
   * DETAILS IMAGE
   * we define an empty image here. 
   * subclasses will override this method and define an image filled with vertex squares, strokes, glyphs etc.
   * this image goes over the grid image
   * ################################
   */
  
  protected BufferedImage detailsimage=null;
  private static final double GRID_2D_DIR_INCREMENT=(Math2D.PI*2.0)/12.0;
  //TODO we have variable foreward functionality but we don't need it. Remove it for efficiency.
  private static final int GRIDFOREWARD=0;
  
  protected void createDetailsImage(){
    int w=getWidth(),h=getHeight();
    detailsimage=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);}
  
  protected final List<double[]> getPoint2Ds(List<DVertex> vertices){
    List<double[]> a=new ArrayList<double[]>(vertices.size());
    for(DVertex v:vertices)
      a.add(getPoint2D(v));
    return a;}
  
  //return the point in image plane corrosponding to the specified vertex
  protected final double[] getPoint2D(DVertex v){
    //get basic 2d points for the vertex and the viewcenter vertex
    double[] p=v.getBasicPoint2D();
    double[] pcenter;
    if(GE.grammarproject.hasFocusBubbleModel()){
      pcenter=GE.grammarproject.focusbubblemodel.bmecviewcenter.getBasicPoint2D();
    }else{
      pcenter=new double[]{0,0};}
    //adjust vertex coors for for view center vertex coors
    p[0]-=pcenter[0];
    p[1]-=pcenter[1];
    //adjust for foreward
    int f=GRIDFOREWARD;
    if(f!=0){
      double dis=Math2D.getDistance_2Points(0,0,p[0],p[1]);
      double dir=Math2D.getDirection_2Points(0,0,p[0],p[1]);
      dir=Math2D.normalizeDirection(dir+(GRID_2D_DIR_INCREMENT*f));
      p=Math2D.getPoint_PointDirectionInterval(0,0,dir,dis);}
    //adjust for scale
    double gi=getGraphicInterval();
    p[0]*=gi;
    p[1]*=gi;
    //adjust y for flipped coors
    p[1]=getHeight()-p[1];
    //adjust for image center
    int w=getWidth(),h=getHeight();
    p[0]+=w/2;
    p[1]-=h/2;
    //
    return p;}
  
  /*
   * ################################
   * DIAMOND GRID IMAGE
   * We define a diamond hexagon slightly larger than the viewport
   * draw lines between all colinear vertices on the hexagon edge
   * this image goes over the base image
   * ################################
   */
  
  private static final double HEXRAD_FILLFACTOR=0.87;
  
  private BufferedImage gridimage=null;
  
  private void createGridImage(){

    //get the hexagon radius
    int hexrad=getHexRad();
    //get the loop of vertices
    List<DVertex> hexloopvertices;
    hexloopvertices=getHexLoopVertices(hexrad);
    //get the pairs
    Set<VertexPair> vertexpairs;
    vertexpairs=getVertexPairs(hexrad,hexloopvertices);
    //init the image
    int w=getWidth(),h=getHeight();
    gridimage=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics=gridimage.createGraphics();
    graphics.setRenderingHints(RENDERING_HINTS);
    //draw lines between vertex pairs
    int gridlinethickness=getGridLineThickness();
    Path2D.Double path=new Path2D.Double();
    double[] p0,p1;
    graphics.setPaint(getGridLineColor());
    graphics.setStroke(new BasicStroke(gridlinethickness));
    for(VertexPair pair:vertexpairs){
      path.reset();
      p0=getPoint2D(pair.v0);
      p1=getPoint2D(pair.v1);
      path.moveTo(p0[0],p0[1]);
      path.lineTo(p1[0],p1[1]);
      graphics.draw(path);}}
  
  /*
   * returns the diamond grid hexagon radius
   */
  public int getHexRad(){
    int w=getWidth(),h=getHeight(),gui=getGraphicInterval();
    //get greater dimension in terms of diamond intervals
    int di;
    if(w>h){
      di=(int)(w/(gui*Math2D.SQRT3));
    }else{
      di=(int)(w/(gui*2.0/3.0));}
    di*=HEXRAD_FILLFACTOR;
    di+=(4-di%4);
    return di;}
  
  /*
   * For every vertex in the list
   * consider it's index
   * consider it's s_index (index along it's side, distance from the previous kink vertex in transitions)
   * every vertex is paired with other vertices constant over a function that considers type, index and s_index
   */
  
  private Set<VertexPair> getVertexPairs(int hexrad,List<DVertex> hlv){
    Set<VertexPair> pairs=new HashSet<VertexPair>();
    int s=hexrad*6,s_index;
    DVertex v;
    for(int index=0;index<s;index++){
      s_index=index%hexrad;
      v=hlv.get(index);
      DOGSWITCH:switch(v.getDog()){
      case MathDiamond.VERTEX_12:
        if(s_index==0){
          pairs.add(new VertexPair(v,hlv.get((index+hexrad*3)%s)));
          pairs.add(new VertexPair(v,hlv.get((index+hexrad*4)%s)));
          pairs.add(new VertexPair(v,hlv.get((index+hexrad*5)%s)));
        }else{
          pairs.add(new VertexPair(v,hlv.get((index+hexrad*6-(s_index*2))%s)));
          pairs.add(new VertexPair(v,hlv.get((index+hexrad*6-(hexrad+s_index*2))%s)));
          pairs.add(new VertexPair(v,hlv.get((index+hexrad*6-(hexrad*2+s_index*2))%s)));}
        break DOGSWITCH;
      case MathDiamond.VERTEX_4A:
        pairs.add(new VertexPair(v,hlv.get((index+hexrad*6-(hexrad*2+s_index*2))%s)));
        break DOGSWITCH;
      case MathDiamond.VERTEX_6A:
        pairs.add(new VertexPair(v,hlv.get((index+hexrad*6-(hexrad+s_index*2))%s)));
        pairs.add(new VertexPair(v,hlv.get((index+hexrad*6-(hexrad*3+s_index*2))%s)));
        break DOGSWITCH;
      case MathDiamond.VERTEX_4B:
        pairs.add(new VertexPair(v,hlv.get((index+hexrad*6-(hexrad*2+s_index*2))%s)));
        break DOGSWITCH;
      case MathDiamond.VERTEX_6B:
        pairs.add(new VertexPair(v,hlv.get((index+hexrad*6-(hexrad+s_index*2))%s)));
        pairs.add(new VertexPair(v,hlv.get((index+hexrad*6-(hexrad*3+s_index*2))%s)));
        break DOGSWITCH;
      case MathDiamond.VERTEX_4C:
        pairs.add(new VertexPair(v,hlv.get((index+hexrad*6-(hexrad*2+s_index*2))%s)));
        break DOGSWITCH;
      default:
        break DOGSWITCH;}}
    //
    return pairs;}
  
  //START CLASS VERTEX PAIR
  class VertexPair{
    
    VertexPair(DVertex v0,DVertex v1){
      this.v0=v0;
      this.v1=v1;}
    
    DVertex v0,v1;
    
    public boolean equals(Object a){
      VertexPair b=(VertexPair)a;
      return 
        (b.v0.equals(v0)&&b.v1.equals(v1))||
        (b.v1.equals(v0)&&b.v0.equals(v1));}
    
  }//END CLASS VERTEX PAIR
  
  /*
   * returns a hexagonal loop of diamond vertices centered at vertex(0,0,0,0)
   */
  private List<DVertex> getHexLoopVertices(int hexrad){
    int[] v={0,0,0,0};
    MathDiamond.getVertex_Transitionswise(0,0,0,0,0,hexrad,v);
    List<DVertex> vertices=new ArrayList<DVertex>();
    for(int i=0;i<hexrad;i++){
      vertices.add(new DVertex(v[0],v[1],v[2],v[3]));
      MathDiamond.getVertexAdjacent(v[0],v[1],v[2],v[3],4,v);}
    for(int i=0;i<hexrad;i++){
      vertices.add(new DVertex(v[0],v[1],v[2],v[3]));
      MathDiamond.getVertexAdjacent(v[0],v[1],v[2],v[3],6,v);}
    for(int i=0;i<hexrad;i++){
      vertices.add(new DVertex(v[0],v[1],v[2],v[3]));
      MathDiamond.getVertexAdjacent(v[0],v[1],v[2],v[3],8,v);}
    for(int i=0;i<hexrad;i++){
      vertices.add(new DVertex(v[0],v[1],v[2],v[3]));
      MathDiamond.getVertexAdjacent(v[0],v[1],v[2],v[3],10,v);}
    for(int i=0;i<hexrad;i++){
      vertices.add(new DVertex(v[0],v[1],v[2],v[3]));
      MathDiamond.getVertexAdjacent(v[0],v[1],v[2],v[3],0,v);}
    for(int i=0;i<hexrad;i++){
      vertices.add(new DVertex(v[0],v[1],v[2],v[3]));
      MathDiamond.getVertexAdjacent(v[0],v[1],v[2],v[3],2,v);}
    return vertices;}
  
  /*
   * ################################
   * MOUSE POINTER XY TO VERTEX ABCD TRANSLATION  
   * ################################
   */
  
  //performance thing
  //we use this method alot
  //to avoid redeclaring variables we just reuse these 
  private int[] mp_abcd=new int[4];
  
  /*
   * given the specified mouse coordinates, 
   * return the coors of the closest dvertex
   */
  private int[] getClosestDVertexCoorToMouse(int x,int y){
    //translate mouse coordinates to account for nonzero foreward
    int f=GRIDFOREWARD;
    if(f!=0){
      int cx=getWidth()/2;
      int cy=getHeight()/2;
      double dis=Math2D.getDistance_2Points(cx,cy,x,y);
      double dir=Math2D.getDirection_2Points(cx,cy,x,y);
      dir=Math2D.normalizeDirection(dir+(GRID_2D_DIR_INCREMENT*f));
      double[] p=Math2D.getPoint_PointDirectionInterval(cx,cy,dir,dis);
      x=(int)p[0];
      y=(int)p[1];}
    //make the coordinates proper cartesian with origin at view center
    x=x-(getWidth()/2);
    y=-(y-(getHeight()/2));
    //get the dimensions of our grid cell box
    double 
      gi=getGraphicInterval(),
      gcbw=gi*Math.sqrt(3.0),
      gcbh=gi*3.0;
    //get the box coordinates
    int 
      bx=(int)Math.floor((((double)x)/gcbw)),
      by=(int)Math.floor((((double)y)/gcbh)); 
    //get the intra-box coordinates
    int 
      ibx=(int)((x-(bx*gcbw))/(gcbw/4)),
      iby=(int)((y-(by*gcbh))/(gcbh/12));
    //we have 2 kinds of boxes
    //get the actual vertex coordinates
    int ka,kb,kc,kd;
    //GET COORDINATES FOR BOX TYPE A
    SEEK:if(Math.abs(bx%2)==Math.abs(by%2)){
      //get the key v12 vertex coors
      ka=(bx-by)/2;//beware those rounding integers. bx/2 - by/2 is not what we want
      kc=by;
      kb=ka+kc;
      //analyze position within box grid : (ibx,iby)
      if(ibx==0){//0 (see notes)
        if(iby==0||iby==1){
          kd=0;
          break SEEK;
        }else if(iby==6||iby==7||iby==8||iby==9){//4
          kd=2;
          break SEEK;
        }else if(iby==10||iby==11){//5
          ka=ka-1;
          kc=kc+1;
          kd=5;
          break SEEK;
        }else{//ambiguous
          return null;}
      }else if(ibx==1||ibx==2){
        if(iby==4||iby==5||iby==6||iby==7){//3
          kd=3;
          break SEEK;
        }else{//ambiguous
          return null;}
      }else{//ibx==3
        if(iby==0||iby==1){//1
          kd=5;
          break SEEK;
        }else if(iby==2||iby==3||iby==4||iby==5){//2
          kd=4;
          break SEEK;
        }else if(iby==10||iby==11){//6
          kb=kb+1;
          kc=kc+1;
          kd=0;
          break SEEK;
        }else{//ambiguous
          return null;}}
    //GET COORDINATES FOR BOX TYPE B
    }else{
      //get the key v12 vertex coors
      ka=(bx-by+1)/2;
      kc=by;
      kb=ka+kc;
      //analyze position within box grid : (ibx,iby)
      if(ibx==0){
        if(iby==0||iby==1){//1
          ka=ka-1;
          kb=kb-1;
          kd=5;
          break SEEK;
        }else if(iby==2||iby==3||iby==4||iby==5){//2
          ka=ka-1;
          kb=kb-1;
          kd=4;
          break SEEK;
        }else if(iby==10||iby==11){//6
          ka=ka-1;
          kc=kc+1;
          kd=0;
          break SEEK;
        }else{//ambiguous
          return null;}
      }else if(ibx==1||ibx==2){
        if(iby==4||iby==5||iby==6||iby==7){//3
          kd=1;
          break SEEK;
        }else{//ambiguous
          return null;}
      }else{//ibx==3
        if(iby==0||iby==1){//0
          kd=0;
          break SEEK;
        }else if(iby==6||iby==7||iby==8||iby==9){//4
          kd=2;
          break SEEK;
        }else if(iby==10||iby==11){//5
          ka=ka-1;
          kc=kc+1;
          kd=5;
          break SEEK;
        }else{//ambiguous
          return null;}}}
      //
      mp_abcd[0]=ka;
      mp_abcd[1]=kb;
      mp_abcd[2]=kc;
      mp_abcd[3]=kd;
      return mp_abcd;}
  
}
