package org.fleen.grammarEditor.overview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.fleen.grammarEditor.C;
import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.bubbleModelEditor.C_BubbleModelEditorShow;
import org.fleen.grammarEditor.grammarProject.C_GPCreateBubbleModel;
import org.fleen.grammarEditor.grammarProject.C_GPCreateJig;
import org.fleen.grammarEditor.grammarProject.C_GPDestroyBubbleModel;
import org.fleen.grammarEditor.grammarProject.C_GPDestroyJig;
import org.fleen.grammarEditor.grammarProject.C_GPSetFocusBubbleModel;
import org.fleen.grammarEditor.grammarProject.C_GPSetFocusJig;
import org.fleen.grammarEditor.grammarProject.GPBubbleModel;
import org.fleen.grammarEditor.grammarProject.GPJig;
import org.fleen.grammarEditor.jigEditor.C_JigEditorShow;

/*
 * this is because the jtable is just too bullshit
 * make grid of icon images
 * column 0 is the bubblemodel icons, all the rest are rows of jig icons
 * icon dimensions are constant. locate everything by math.
 */
@SuppressWarnings("serial")
public class OverviewGrid extends JPanel{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public OverviewGrid(){
    addMouseListener(new ML0());
    setFocusable(true);
    addHierarchyBoundsListener(new HierarchyBoundsListener0());}
  
  /*
   * ################################
   * CELLS
   * ################################
   */
  
  int cellviewoffsetx=0,cellviewoffsety=0;
  
  /*
   * return element at cell specified by coordinates
   * off grid address returns null
   */
  public OverviewGridElement getElement(int x,int y){
    if(x<0||y<0)return null;
    if(y>=GE.grammarproject.bubblemodels.size()){
      return null;
    }else{
      GPBubbleModel m=GE.grammarproject.bubblemodels.get(y);
      int jigindex=x-1;
      if(x==0){
        return m;
      }else if(jigindex<m.jigs.size()){
        return m.jigs.get(jigindex);
      }else{
        return null;}}}
  
  //returns value in range [0,n]
  //returns -1 on fail
  public int getFocusBubbleModelRow(){
    if(!GE.grammarproject.hasFocusBubbleModel())return -1;
    return GE.grammarproject.bubblemodels.indexOf(GE.grammarproject.focusbubblemodel);}
  
  //returns value in range [1,n]
  //returns -1 on fail
  public int getFocusJigCol(){
    if(!GE.grammarproject.hasFocusJig())return -1;
    return GE.grammarproject.focusbubblemodel.jigs.indexOf(GE.grammarproject.focusbubblemodel.focusjig)+1;}
  
  /*
   * returns the width of the grid in terms of cells
   */
  public int getGridWidth(){
    int maxjigs=0,s;
    for(GPBubbleModel m:GE.grammarproject.bubblemodels){
      s=m.jigs.size();
      if(s>maxjigs)maxjigs=s;}
    return maxjigs+1;}
  
  /*
   * returns the height of the grid in terms of cells 
   */
  public int getGridHeight(){
    return GE.grammarproject.bubblemodels.size();}
  
  public int[] getCellAtPixel(int x,int y){
    x-=C.OG_ICONPADDING;
    x/=(C.OG_CELLSPAN+C.OG_ICONPADDING);
    y-=C.OG_ICONPADDING;
    y/=(C.OG_CELLSPAN+C.OG_ICONPADDING);
    y+=cellviewoffsetx;
    y+=cellviewoffsety;
    return new int[]{x,y};}
  
  /*
   * ################################
   * MOUSE CLICK VERTEX
   * ################################
   */
  
  private static final int 
    CLICK_BUBBLEMODEL=0,
    CLICK_JIG=1,
    CLICK_NULL=2;
  
  private class ML0 extends MouseAdapter{
    
    public void mouseClicked(MouseEvent e){
      int  
        buttonclicked=e.getButton(),
        clickcount=e.getClickCount();
      int[] cell=getCellAtPixel(e.getX(),e.getY());
      OverviewGridElement clickelement=getElement(cell[0],cell[1]);
      System.out.println("cell : "+cell[0]+", "+cell[1]);
      int clicktype=CLICK_NULL;
      if(clickelement instanceof GPBubbleModel){
        clicktype=CLICK_BUBBLEMODEL;
      }else if(clickelement instanceof GPJig){
        clicktype=CLICK_JIG;}
      //GENERAL CLICK
      if(clicktype==CLICK_BUBBLEMODEL){
        GE.command(new C_GPSetFocusBubbleModel(),clickelement);
        GE.command(new C_OverviewRefreshAll());
      }else if(clicktype==CLICK_JIG){
        GE.command(new C_GPSetFocusJig(),clickelement);
        GE.command(new C_OverviewRefreshAll());}
      //LEFT DOUBLECLICK
      if(buttonclicked==MouseEvent.BUTTON1&&clickcount==2){
        if(!e.isConsumed())e.consume();
        if(clicktype==CLICK_BUBBLEMODEL){
          GE.command(new C_BubbleModelEditorShow());
        }else if(clicktype==CLICK_JIG){
          GE.command(new C_JigEditorShow());}
      //RIGHT CLICK
      }else if(buttonclicked==MouseEvent.BUTTON3){
        showPopupMenu(e.getX(),e.getY(),0,0);}}};//TODO
        
  /*
   * ################################
   * POPUP MENU
   * ################################
   */
  
  private JPopupMenu popupmenu=null;
  
  private void showPopupMenu(int x,int y,int cellx,int celly){
    if(cellx==-1||celly==-1){
      showPopupMenuForEmpty(x,y);
    }else if(celly==0){
      showPopupMenuForBM(x,y);
    }else{
      showPopupMenuForJig(x,y);}}
  
  //menu for unpopulated table
  private void showPopupMenuForEmpty(int x,int y){
    popupmenu=new JPopupMenu();
    popupmenu.add(new JMenuItem(actioncreatebubblemodel));
    popupmenu.show(GE.overview.grid,x,y);}
  
  //menu for bubblemodel cell
  private void showPopupMenuForBM(int x,int y){
    popupmenu=new JPopupMenu();
    popupmenu.add(new JMenuItem(actioncreatebubblemodel));
    popupmenu.add(new JMenuItem(actioncreatejig));
    popupmenu.add(new JMenuItem(actiondestroybubblemodel));
    popupmenu.add(new JMenuItem(actiondestroyjig));
    popupmenu.show(GE.overview.grid,x,y);
  }
  
  //menu for jigcell
  private void showPopupMenuForJig(int x,int y){
    popupmenu=new JPopupMenu();
    popupmenu.add(new JMenuItem(actioncreatebubblemodel));
    popupmenu.add(new JMenuItem(actioncreatejig));
    popupmenu.add(new JMenuItem(actiondestroybubblemodel));
    popupmenu.add(new JMenuItem(actiondestroyjig));
    popupmenu.show(GE.overview.grid,x,y);
  }
  
  private static Action actioncreatebubblemodel=new AbstractAction(){

    String name="Create BubbleModel";
    
    public Object getValue(String key){
      if(key.equals("Name"))
        return name;
      return super.getValue(key);}
    
    public void actionPerformed(ActionEvent e){
      GE.command(new C_GPCreateBubbleModel());
      GE.command(new C_OverviewRefreshForCreateDestroy());}};
      
  private static Action actioncreatejig=new AbstractAction(){

    String name="Create Jig";
    
    public Object getValue(String key){
      if(key.equals("Name"))
        return name;
      return super.getValue(key);}
    
    public void actionPerformed(ActionEvent e){
      GE.command(new C_GPCreateJig());
      GE.command(new C_OverviewRefreshForCreateDestroy());}};
      
  private static Action actiondestroybubblemodel=new AbstractAction(){

    String name="Destroy BubbleModel";
    
    public Object getValue(String key){
      if(key.equals("Name"))
        return name;
      return super.getValue(key);}
    
    public void actionPerformed(ActionEvent e){
      GE.command(new C_GPDestroyBubbleModel());
      GE.command(new C_OverviewRefreshForCreateDestroy());}};
      
  private static Action actiondestroyjig=new AbstractAction(){

    String name="Destroy Jig";
    
    public Object getValue(String key){
      if(key.equals("Name"))
        return name;
      return super.getValue(key);}
    
    public void actionPerformed(ActionEvent e){
      GE.command(new C_GPDestroyJig());
      GE.command(new C_OverviewRefreshForCreateDestroy());}};
  
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
   * REFRESH 
   * ################################
   */
    
    public void refreshAll(){
      image=null;
      new RenderThread().start();}
    
    public void refreshForCreateDestroy(){
      image=null;
      Dimension d=getImageDimension();
      setPreferredSize(d);
      setSize(d);
      new RenderThread().start();}
  
  /*
   * ################################
   * IMAGE CONTROL
   * ################################
   */
  
  private void validateImage(){
    if((getWidth()<=0)||(getHeight()<=0))return;
    if(image==null)
      createImage();}
  
  private class RenderThread extends Thread{
    
    public void run(){
      validateImage();
      repaint();}}
  
  public void paint(Graphics g){
    super.paint(g);
    if((getWidth()<=0)||(getHeight()<=0))return;
    ((Graphics2D)g).drawImage(image,null,null);}
  
  /*
   * ################################
   * RENDER
   * ################################
   */
  
  private BufferedImage image=null;
  
  private void createImage(){
    Dimension d=getImageDimension();
    int 
      w=d.width,
      h=d.height;
    if(w<=0||h<=0)return;
    int 
      vcw=getGridWidth(),
      vch=getGridHeight();
    image=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics=image.createGraphics();
    renderBackground(graphics,w,h);
    renderForeground(graphics,vcw,vch);}
  
  private Dimension getImageDimension(){
    int 
      w=getGridWidth()*(C.OG_CELLSPAN+C.OG_ICONPADDING)+C.OG_ICONPADDING,
      h=getGridHeight()*(C.OG_CELLSPAN+C.OG_ICONPADDING)+C.OG_ICONPADDING;
    return new Dimension(w,h);}
  
  /*
   * fill the whole image with background color
   * paint a horizontal bar at the selected bubblemodel cell row
   * paint a box at the selected cell
   */
  private void renderBackground(Graphics2D graphics,int w,int h){
    //fill the whole image with background color
    graphics.setPaint(C.OG_COLOR_BACKGROUND);
    graphics.fillRect(0,0,w,h);
    //fill the focus bubblemodel cell row rectangle
    int focusrow=getFocusBubbleModelRow();
    if(focusrow!=-1){
      graphics.setPaint(C.OG_COLOR_FOCUSBUBBLEMODELROWBACKGROUND);
      graphics.fillRect(
        0,
        (cellviewoffsetx+focusrow)*(C.OG_CELLSPAN+C.OG_ICONPADDING),
        w,
        C.OG_CELLSPAN+C.OG_ICONPADDING+C.OG_ICONPADDING);}
    //fill the focus jig cell square
    int focuscol=getFocusJigCol();
    if(focuscol!=-1){
      graphics.setPaint(C.OG_COLOR_FOCUSJIGCELLBACKGROUND);
      graphics.fillRect(
        focuscol*(C.OG_CELLSPAN+C.OG_ICONPADDING),
        (cellviewoffsetx+focusrow)*(C.OG_CELLSPAN+C.OG_ICONPADDING),
        C.OG_CELLSPAN+C.OG_ICONPADDING+C.OG_ICONPADDING,
        C.OG_CELLSPAN+C.OG_ICONPADDING+C.OG_ICONPADDING);}}
  
  private void renderForeground(Graphics2D graphics,int vcw,int vch){
    BufferedImage image;
    for(int x=cellviewoffsetx;x<cellviewoffsetx+vcw;x++){
      for(int y=cellviewoffsety;y<cellviewoffsety+vch;y++){
        image=getCellForegroundImage(x,y);
        if(image!=null)
          graphics.drawImage(image,null,C.OG_ICONPADDING+x*(C.OG_CELLSPAN+C.OG_ICONPADDING),C.OG_ICONPADDING+y*(C.OG_CELLSPAN+C.OG_ICONPADDING));}}}
  
  private BufferedImage getCellForegroundImage(int x,int y){
    OverviewGridElement e=getElement(x,y);
    if(e==null)return null;
    return e.getImageForOverviewGrid();}
  
}
