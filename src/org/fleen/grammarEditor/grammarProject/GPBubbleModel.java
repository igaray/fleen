package org.fleen.grammarEditor.grammarProject;

import java.awt.geom.Path2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fleen.core.gKis.KVectorRDPath;
import org.fleen.core.gKis.KVertex;
import org.fleen.core.gKis.KVertexPath;
import org.fleen.grammarEditor.jigEditor.JigEditorBMListBubbleModelImage;
import org.fleen.grammarEditor.overview.OverviewGridBubbleModelImage;
import org.fleen.grammarEditor.overview.OverviewGridElement;

/*
 * store all vertex data as DVertices
 * convert to BubbleModelVectors on export
 */
public class GPBubbleModel implements Serializable,OverviewGridElement{
  
  /*
   * ################################
   * FIELDS
   * ################################
   */
  
  private static final long serialVersionUID=-238762044761078848L;
  
  public String id;
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  //create new
  public GPBubbleModel(String id){
    this.id=id;}
  
  //for import set everything manually
  public GPBubbleModel(){}
  
  /*
   * ################################
   * VALIDATION
   * the model must meet the following criteria
   * at least 3 vertices
   * each vertex colinear with it's neighbors
   * no duplicated vertices
   * no line intersections
   * ################################
   */
  
  private boolean valid=false;

  public boolean isValid(){
    return valid;}
  
  public void validate(){
    //at least 3 vertices
    int vertexcount=vertexpath.size();
    if(vertexcount<3){
      valid=false;
      System.out.println("too few vertices");
      return;}
    //each vertex colinear with it's neighbors
    int inext;
    for(int i=0;i<vertexcount;i++){
      inext=i+1;
      if(inext==vertexcount)inext=0;
      if(!vertexpath.get(i).isColinear(vertexpath.get(inext))){
        valid=false;
        System.out.println("noncolinear adjacent vertices");
        return;}}
    //no duplicated vertices
    for(int i0=0;i0<vertexcount;i0++){
      for(int i1=0;i1<vertexcount;i1++){
        if((i0!=i1)&&vertexpath.get(i0).equals(vertexpath.get(i1))){
          valid=false;
          System.out.println("dupe vertices");
          return;}}}
    //no line intersections
    //TODO
    valid=true;}
  
  /*
   * ################################
   * VERTEX PATH
   * ################################
   */
  
  public KVertexPath vertexpath=new KVertexPath();
  
  //no duplicated vertices
  //no noncolinear adjacent vertices
  //no path self-intersection//TODO
  public boolean addVertex(KVertex v){
    if(vertexpath.contains(v))return false;
    if((!vertexpath.isEmpty())&&(!v.isColinear(vertexpath.get(vertexpath.size()-1))))return false;
    vertexpath.add(v);
    //invalidate all cached stuff that depends on dvertexpath
    imagepath=null;
    //
    validate();
    return true;}
  
  public boolean removeVertex(KVertex v){
    if(!vertexpath.contains(v))return false;
    vertexpath.remove(v);
    //invalidate all cached stuff that depends on dvertexpath
    imagepath=null;
    //
    validate();
    return true;}
  
  public boolean hasVertices(){
    return !vertexpath.isEmpty();}
  
  public KVertexPath getVertexPath(){
    return vertexpath;}
  
  /*
   * ################################
   * IMAGE PATH
   * Use this for making images for icons and whatever
   * invalidate when any geometry data changes
   * ################################
   */
  
  private Path2D.Double imagepath=null;
  
  public Path2D.Double getImagePath(){
    if(imagepath==null)
      initImagePath();
    return imagepath;}
  
  public void initImagePath(){
    if(vertexpath.size()<3)
      throw new IllegalArgumentException("VERTEX COUNT IS <3");
    imagepath=new Path2D.Double();
    KVertex v=vertexpath.get(0);
    double[] p=v.getBasicPoint2D();
    imagepath.moveTo(p[0],p[1]);
    for(int i=1;i<vertexpath.size();i++){
      v=vertexpath.get(i);
      p=v.getBasicPoint2D();
      imagepath.lineTo(p[0],p[1]);}
    imagepath.closePath();}

  /*
   * ################################
   * JIGS
   * ################################
   */
  
  private static final String JIG_INIT_NAME="jig_";
  private static final int JIG_INIT_MAX_INDEX=1000;
  
  public List<GPJig> jigs=new ArrayList<GPJig>();
  public GPJig focusjig=null;
  
  public GPJig createJig(){
    focusjig=new GPJig(this,getIDForNewJig());
    jigs.add(focusjig);
    return focusjig;}
  
  public void destroyJig(){
    int a=jigs.indexOf(focusjig);
    jigs.remove(a);
    if(jigs.isEmpty()){
      focusjig=null;
    }else{
      a--;
      if(a==-1)a=0;
      focusjig=jigs.get(a);}}
  
  public boolean containsJig(String id){
    for(GPJig a:jigs)
      if(a.id.equals(id))
        return true;
    return false;}
  
  private String getIDForNewJig(){
    String a=JIG_INIT_NAME;
    for(int i=0;i<JIG_INIT_MAX_INDEX;i++){
      a=JIG_INIT_NAME+i;
      if(!containsJig(a))return a;}
    throw new IllegalArgumentException("issue in new jig id accquisition");}
  
  /*
   * ################################
   * CLEAN GEOMETRY
   * orient vertex form to STANDARD FORMAT and positive twist
   * then lock it
   * ################################
   */
  
  public boolean locked=false;
  
  public void cleanGeometry(){
    if(locked)return;
    KVertexPath thisvertexpath=getVertexPath();
    System.out.println("VERTEX PATH 0 : "+thisvertexpath);
    if(thisvertexpath.size()<3)return;
    KVectorRDPath vectorpath=thisvertexpath.getVectorPath();
    System.out.println("VECTOR PATH 0 : "+vectorpath);
    if(!vectorpath.isClockwise())vectorpath.reverseDeltas();//cuz standard is clockwise
    KVertexPath newvertexpath=vectorpath.getVertexPath();//p0->p1 is moving north etc
    System.out.println("VERTEX PATH 1 : "+newvertexpath);
    System.out.println("VECTOR PATH 1 : "+newvertexpath.getVectorPath());
    if(newvertexpath==null){
      System.out.println("CLEAN FAIL");
    }else{
      System.out.println("CLEAN SUCCESS");
      locked=true;
      vertexpath=newvertexpath;}}
  
  /*
   * ################################
   * OVERVIEW
   * ################################
   */
  
  //IMAGE FOR GRID
  private OverviewGridBubbleModelImage imageforoverviewgrid=null;
  
  public OverviewGridBubbleModelImage getImageForOverviewGrid(){
    if(valid){
      if(imageforoverviewgrid==null)
        imageforoverviewgrid=new OverviewGridBubbleModelImage(this);
      return imageforoverviewgrid;
    }else{
      imageforoverviewgrid=null;
      return OverviewGridBubbleModelImage.IMAGE_FOR_INVALID_BUBBLEMODEL;}}
  
  /*
   * ################################
   * BUBBLEMODEL EDITOR
   * ################################
   */
  
  //CANVAS PARAMS
  static final KVertex BMEC_VIEWCENTER_DEFAULT=new KVertex(0,0,0,0);
  static final int BMEC_VIEWZOOM_DEFAULT=0;
  //we center on a v12
  //panning moves center to next v12 to the up down,left,right
  public KVertex bmecviewcenter=BMEC_VIEWCENTER_DEFAULT;
  //zoom - halves scale, zoom + doubles it
  public int bmecviewzoom=BMEC_VIEWZOOM_DEFAULT;
  
  /*
   * ################################
   * JIG EDITOR
   * ################################
   */
  
  public boolean tilt=false;
  
  public int getForeward(){
    return vertexpath.get(0).getDirection(vertexpath.get(1));
  }
  
  //IMAGE FOR BUBBLEMODEL LIST
  JigEditorBMListBubbleModelImage jigeditorbubblemodellistimage=null;
  
  public JigEditorBMListBubbleModelImage getJigEditorBMListImage(){
    if(valid){
      if(jigeditorbubblemodellistimage==null)
        jigeditorbubblemodellistimage=new JigEditorBMListBubbleModelImage(this);
      return jigeditorbubblemodellistimage;
    }else{
      jigeditorbubblemodellistimage=null;
      return JigEditorBMListBubbleModelImage.IMAGE_FOR_INVALID_BUBBLEMODEL;}}
  
}
