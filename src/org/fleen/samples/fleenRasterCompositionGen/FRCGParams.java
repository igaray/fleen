package org.fleen.samples.fleenRasterCompositionGen;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URLDecoder;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;

import org.fleen.core.diamondGrammar.BubbleModel;
import org.fleen.core.diamondGrammar.Grammar;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_000;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_001;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_Abstract;
import org.fleen.samples.fleenRasterCompositionGen.symmetryControlFunction.SCF_Default;
import org.fleen.samples.fleenRasterCompositionGen.symmetryControlFunction.SymmetryControlFunction_Abstract;

public class FRCGParams{
    
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public FRCGParams(){
    initUIComponent_Grammar();
    initUIComponent_RootBubbleModelsList();
    initUIComponent_RootBubbleModel();
    initUIComponent_DetailSizeLimit();
    initUIComponent_SymmetryControlFunction();
    initUIComponent_RendererList();
    initUIComponent_Renderer();
    initUIComponent_ExportImageScale();
    initUIComponent_ExportDir();
    initUIComponent_GenExpImageCount();}
  
  /*
   * ################################
   * GRAMMAR
   * ################################
   */
  
  private static final String PATH_TO_DEFAULT_GRAMMAR="/org/fleen/samples/fleenRasterCompositionGen/nicecomposition.g";
  private Grammar grammar=null;
  private String grammarname;
  
  public Grammar getGrammar(){
  if(grammar==null)
    initGrammar();
  return grammar;}
  
  public String getGrammarName(){
  if(grammar==null)
    initGrammar();
  return grammarname;}
  
  public void setGrammar(){
    JFileChooser fc=new JFileChooser("Select Grammar");
    fc.setCurrentDirectory(getLocalDir());
    int r=fc.showOpenDialog(FleenRasterCompositionGen.frcg_instance.ui.frame);
    if(r!=JFileChooser.APPROVE_OPTION)
      return;
    File f=fc.getSelectedFile();
    setGrammar(f);}
  
  public void setGrammar(File f){
    grammar=extractGrammarFromFile(f);
    grammarname=f.getName();}

  private void initGrammar(){
    setGrammar(new File(getLocalDir()+PATH_TO_DEFAULT_GRAMMAR));}
  
  public void initUIComponent_Grammar(){
    FleenRasterCompositionGen.frcg_instance.ui.txtGrammar.setText(getGrammarName());}
  
  /*
   * ################################
   * ROOT BUBBLE MODEL
   * ################################
   */
  
  private BubbleModel rootbubblemodel=null;
  
  public BubbleModel getRootBubbleModel(){
    if(rootbubblemodel==null)
      initRootBubbleModel();
    return rootbubblemodel;}
  
  public void setRootBubbleModel(BubbleModel m){
    rootbubblemodel=m;}
  
  private void initRootBubbleModel(){
    Grammar g=getGrammar();
    BubbleModel m=g.getBubbleModels().get(0);
    setRootBubbleModel(m);}
  
  public void invalidateRootBubbleModel(){
    rootbubblemodel=null;}
  
  public void initUIComponent_RootBubbleModelsList(){
    List<BubbleModel> m=getGrammar().getBubbleModels();
    FleenRasterCompositionGen.frcg_instance.ui.cmbRootBubbleModel.setModel(
      new DefaultComboBoxModel(m.toArray(new BubbleModel[m.size()])));}
  
  public void initUIComponent_RootBubbleModel(){
    FleenRasterCompositionGen.frcg_instance.ui.cmbRootBubbleModel.getModel().setSelectedItem(getRootBubbleModel());}
  
  /*
   * ################################
   * DETAIL SIZE LIMIT
   * unscaled floor for cultivatable bubble detail size
   * ################################
   */
  
  private static final double DETAIL_SIZE_LIMIT_DEFAULT=0.03;
  private double detailsizelimit=-1;
  
  public double getDetailSizeLimit(){
    if(detailsizelimit==-1)
      detailsizelimit=DETAIL_SIZE_LIMIT_DEFAULT;
    return detailsizelimit;}
  
  public void setDetailSizeLimit(String s){
    try{
      Double d=new Double(s);
      setDetailSizeLimit(d);
    }catch(Exception e){}}
  
  public void setDetailSizeLimit(double d){
    if(d>0)
      detailsizelimit=d;}
  
  public void initUIComponent_DetailSizeLimit(){
    FleenRasterCompositionGen.frcg_instance.ui.txtDetailSizeLimit.setText(
      Double.toString(getDetailSizeLimit()));}
  
  /*
   * ################################
   * SYMMETRY CONTROL FUNTION
   * A function of detail size
   * Given bubble.detailsize, the function controls the probability of using the symmetric cultivation algorithm
   * TODO we're just using the default "all symmetric function" right now
   * what we need to do is offer a way to draw a curve. Specify curve control points and such.
   * ################################
   */
  
  private SymmetryControlFunction_Abstract symmetrycontrolfunction=null;
  
  public SymmetryControlFunction_Abstract getSymmetryControlFunction(){
    if(symmetrycontrolfunction==null)
      initSymmetryControlFunction();
    return symmetrycontrolfunction;}
  
  private void initSymmetryControlFunction(){
    symmetrycontrolfunction=new SCF_Default();}
  
  public void setSymmetryControlFunction(SymmetryControlFunction_Abstract scf){
    symmetrycontrolfunction=scf;}
  
  public void initUIComponent_SymmetryControlFunction(){
    FleenRasterCompositionGen.frcg_instance.ui.lblTxtSymmetryControlFunction.setText(
      getSymmetryControlFunction().toString());}
  
  /*
   * ################################
   * RENDERER
   * ################################
   */
  
  private static final Renderer_Abstract[] RENDERERS={
    new Renderer_000(),
    new Renderer_001()};
  private Renderer_Abstract renderer=null;
  
  public Renderer_Abstract getRenderer(){
    if(renderer==null)
      initRenderer();
    return renderer;}
  
  private void initRenderer(){
    renderer=new Renderer_000();}
  
  public void setRenderer(Renderer_Abstract r){
    renderer=r;}
  
  public void initUIComponent_RendererList(){
    FleenRasterCompositionGen.frcg_instance.ui.cmbRenderer.setModel(new DefaultComboBoxModel(RENDERERS));}
  
  public void initUIComponent_Renderer(){
    FleenRasterCompositionGen.frcg_instance.ui.cmbRenderer.getModel().setSelectedItem(getRenderer());}
  
  /*
   * ################################
   * EXPORT IMAGE SCALE
   * we generally scale up
   * ################################
   */
   
  private static final double EXPORT_IMAGE_SCALE_DEFAULT=200;
  private double exportimagescale=-1;
  
  public double getExportImageScale(){
    if(exportimagescale==-1)
      initExportImageScale();
    return exportimagescale;}
  
  private void initExportImageScale(){
    exportimagescale=EXPORT_IMAGE_SCALE_DEFAULT;}
  
  public void setExportImageScale(String s){
    try{
      Double d=new Double(s);
      setExportImageScale(d);
    }catch(Exception e){}}
  
  public void setExportImageScale(double d){
    if(d<=0)return;
    exportimagescale=d;}
  
  public void initUIComponent_ExportImageScale(){
    FleenRasterCompositionGen.frcg_instance.ui.txtExportImageScale.setText(
      Double.toString(getExportImageScale()));}
  
  /*
   * ################################
   * EXPORT DIR
   * path to the directory to which we export our generated fleen raster composition PNG files
   * ################################
   */
  
  //created at the directory where this app is run if necessary
  private static final String EXPORT_DIR_DEFAULT="/FRCGExport";
  private File exportdir=null;
  
  public File getExportDir(){
    if(exportdir==null)
      initExportDir();
    return exportdir;}
  
  private void initExportDir(){
    File a=getLocalDir();
    String exportdirpath=a.getPath()+EXPORT_DIR_DEFAULT;
    exportdir=new File(exportdirpath);
    if(!exportdir.isDirectory())
      exportdir.mkdir();}
  
  public void setExportDir(){
    JFileChooser fc=new JFileChooser();
    fc.setCurrentDirectory(getLocalDir());
    int r=fc.showOpenDialog(FleenRasterCompositionGen.frcg_instance.ui.frame);
    if(r!=JFileChooser.APPROVE_OPTION)
      return;
    File f=fc.getSelectedFile();
    setExportDir(f);}
  
  public void setExportDir(File f){
    if(f.isDirectory())
      exportdir=f;}
  
  public void initUIComponent_ExportDir(){
    FleenRasterCompositionGen.frcg_instance.ui.txtExportDir.setText(getExportDir().toString());}
  
  /*
   * ################################
   * GENEXP IMAGE COUNT
   * ################################
   */
  
  private static final int GENEXP_IMAGE_COUNT_DEFAULT=3;
  private int genexpimagecount=-1;
  
  public int getGenExpImageCount(){
    if(genexpimagecount==-1)
      initGenExpImageCount();
    return genexpimagecount;}
  
  private void initGenExpImageCount(){
    genexpimagecount=GENEXP_IMAGE_COUNT_DEFAULT;}
  
  public void setGenExpImageCount(int i){
    if(i>0)
      genexpimagecount=i;}
  
  public void initUIComponent_GenExpImageCount(){
    FleenRasterCompositionGen.frcg_instance.ui.spiGenExpImageCount.setValue(getGenExpImageCount());}

  /*
   * ################################
   * UTIL
   * ################################
   */
  
  private File getLocalDir(){
    String path=FleenRasterCompositionGen.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    String decodedpath;
    try{
      decodedpath=URLDecoder.decode(path,"UTF-8");
    }catch(Exception e){
      throw new IllegalArgumentException(e);}
    File f=new File(decodedpath);
    if(!f.isDirectory())f=f.getParentFile();
    return f;}
  
  private Grammar extractGrammarFromFile(File file){
    FileInputStream fis;
    ObjectInputStream ois;
    Grammar grammar=null;
    try{
      fis=new FileInputStream(file);
      ois=new ObjectInputStream(fis);
      grammar=(Grammar)ois.readObject();
      ois.close();
    }catch(Exception e){
      System.out.println("#^#^# EXCEPTION IN EXTRACT GRAMMAR FROM FILE #^#^#");
      e.printStackTrace();
      return null;}
    return grammar;}
  
}
