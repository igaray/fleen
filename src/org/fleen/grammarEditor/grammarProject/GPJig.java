package org.fleen.grammarEditor.grammarProject;

import java.awt.geom.Path2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.fleen.core.grammar.DVectorRDPath;
import org.fleen.core.grammar.DVertex;
import org.fleen.core.grammar.DVertexPath;
import org.fleen.core.grammar.Grammar;
import org.fleen.core.grammar.Jig;
import org.fleen.core.grammar.JigBubbleDef;
import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.overview.OverviewGridElement;
import org.fleen.grammarEditor.overview.OverviewGridJigImage;

public class GPJig implements Serializable,OverviewGridElement{

  /*
   * ################################
   * FIELDS
   * ################################
   */ 
 
  private static final long serialVersionUID=-5202464595203792387L;
  
  public GPBubbleModel bubblemodel;
  //
  public String id;
  public int type=Jig.TYPE_SPLITTER;
  
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */ 
  
  //create in editor
  public GPJig(GPBubbleModel bubblemodel,String id){
    this.id=id;
    this.bubblemodel=bubblemodel;}
  
  //set everything manually for import
  public GPJig(){}
  
  /*
   * ################################
   * VALIDATION
   * ################################
   */
  
  public boolean valid=false;
  
  public void validate(){
    if(bubbledefs.isEmpty()){
      valid=false;
      return;}
    //
    valid=true;
//    for(GPJigBubbleDef bd:bubbledefs){
//      if(!bd.valid)
//    }
    
  }
  
  /*
   * ################################
   * GRID DENSITY AND FISH FACTOR
   * ################################
   */ 
  
  int griddensity=1;
  
  public int getGridDensity(){
    return griddensity;}
  
  public void setGridDensity(int a){
    if(a<1)a=1;
    griddensity=a;}
  
  public double getFishFactor(){
    return 1.0/(double)griddensity;}
  
  /*
   * ################################
   * BUBBLE DEFS
   * ################################
   */ 
  
  public List<GPJigBubbleDef> bubbledefs=new ArrayList<GPJigBubbleDef>();
  public GPJigBubbleDef focusbubbledef=null;
  
  public void createBubbleDef(){
    focusbubbledef=new GPJigBubbleDef(getUniqueIDForBubbleDef());
    bubbledefs.add(focusbubbledef);
    imageforoverviewgrid=null;}
  
  public void destroyBubbleDef(){
    int a=bubbledefs.indexOf(focusbubbledef);
    bubbledefs.remove(a);
    if(bubbledefs.isEmpty()){
      focusbubbledef=null;
    }else{
      a--;
      if(a==-1)a=0;
      focusbubbledef=bubbledefs.get(a);}
    imageforoverviewgrid=null;}
  
  public int getBubbleDefCount(){
    return bubbledefs.size();}
  
  public boolean hasBubbleDefs(){
    return !bubbledefs.isEmpty();}
  
  public GPJigBubbleDef getFocusBubbleDef(){
    return focusbubbledef;}
  
  public void setFocusBubbleDef(GPJigBubbleDef b){
    focusbubbledef=b;}
  
  public GPJigBubbleDef getBubbleDef(int index){
    return bubbledefs.get(index);}
  
  public int getFocusBubbleDefIndex(){
    if(bubbledefs.isEmpty())return 0;
    return bubbledefs.indexOf(focusbubbledef);}
  
  static final int BUBBLEDEF_ID_MAX=1000;
  
  public int getUniqueIDForBubbleDef(){
    int id=-1;
    boolean idfound=true;
    while(idfound){
      id++;
      idfound=false;
      for(GPJigBubbleDef b:bubbledefs){
        if(b.id==id){
          idfound=true;
          break;}}}
    return id;}
  
  /*
   * ################################
   * JIG EDITOR CANVAS PARAMS
   * ################################
   */
  
  static final DVertex JEC_VIEWCENTER_DEFAULT=new DVertex(0,0,0,0);
  static final int JEC_VIEWZOOM_DEFAULT=0;
  //we center on a v12
  //panning moves center to next v12 to the up down,left,right
  public DVertex jecviewcenter=JEC_VIEWCENTER_DEFAULT;
  //zoom - halves scale, zoom + doubles it
  public int jecviewzoom=JEC_VIEWZOOM_DEFAULT;
  
  /*
   * ################################
   * JIG EDITOR BUBBLEMODEL VERTEXPATH
   * ################################
   */
  
  private static final DVertex ORIGIN=new DVertex(0,0,0,0);
  
  /*
   * get the vector path from the bubblemodel
   * get a scaled standard-format vertexpath from it
   */
  public DVertexPath getBubbleModelVertexPathForJigEditor(){
    DVectorRDPath vectorpath=GE.grammarproject.focusbubblemodel.getVertexPath().getVectorPath();
    return vectorpath.getVertexPath(ORIGIN,0,griddensity);}
  
  /*
   * ################################
   * JIG FOR EXPORT
   * ################################
   */
  
  public Jig getJigForExport(Grammar grammar){
    List<JigBubbleDef> jbds=new ArrayList<JigBubbleDef>();
    JigBubbleDef jbd;
    for(GPJigBubbleDef gpjbd:bubbledefs){
      jbd=new JigBubbleDef(gpjbd.bubblemodel.id,gpjbd.v0,gpjbd.v1,gpjbd.twist,gpjbd.foamindex,gpjbd.chorusindex);
      jbds.add(jbd);}
    Jig j=new Jig(grammar,id,bubblemodel.id,getGridDensity(),jbds);
    return j;}
  
  /*
   * ################################
   * IMAGE PATH
   * Use this for making images for icons and whatever
   * invalidate when any geometry data changes
   * it's a path comprised of all of the bubbledef paths appended together
   * ################################
   */
  
  private Path2D.Double imagepath=null;
  
  public Path2D.Double getImagePath(){
    if(imagepath==null)
      initImagePaths();
    return imagepath;}
  
  public void initImagePaths(){
    imagepath=new Path2D.Double();
    Path2D.Double p;
    for(GPJigBubbleDef bd:bubbledefs){
      p=bd.getImagePath();
      if(p!=null)
        imagepath.append(p,false);}}
  
  /*
   * ################################
   * IMAGE FOR OVERVIEW GRID
   * ################################
   */
  
  private OverviewGridJigImage imageforoverviewgrid=null;
  
  public OverviewGridJigImage getImageForOverviewGrid(){
    validate();
    if(valid){
      if(imageforoverviewgrid==null)
        imageforoverviewgrid=new OverviewGridJigImage(this);
      return imageforoverviewgrid;
    }else{
      imageforoverviewgrid=null;
      return OverviewGridJigImage.IMAGE_FOR_INVALID_JIG;}}
  
}
