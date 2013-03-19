package org.fleen.samples.fleenRasterCompositionGen;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;

import org.fleen.core.diamondGrammar.BubbleModel;
import org.fleen.core.diamondGrammar.Grammar;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_000;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_Abstract;
import org.fleen.samples.fleenRasterCompositionGen.symmetryControlFunction.SCF_Default;
import org.fleen.samples.fleenRasterCompositionGen.symmetryControlFunction.SymmetryControlFunction_Abstract;

/*
 * Configuration for Fleen Raster Composition Generator
 * get the local directory
 * look there for the config object file
 *   if it's there then load it
 *   if it isn't then create a new one
 * init the various ui to reflect the new config
 *  
 * 
 */
public class FRCGConfig implements Serializable{
  
  private static final long serialVersionUID=8345027552779619871L;

  /*
   * ################################
   * GRAMMAR
   * We store the grammar file. Extract as necessary.
   * ################################
   */
  
  private static final String 
    DEFAULT_GRAMMAR_FILE_NAME="defaultgrammar.g",
    DEFAULT_GRAMMAR_FILE_SUFFIX=".g";
  
  private File grammarfile;
  
  public File getGrammarFile(){
    if(grammarfile==null)
      initGrammarFile();
    return grammarfile;}
  
  public Grammar getGrammar(){
  return extractGrammarFromFile(getGrammarFile());}
  
  public String getGrammarName(){
  if(grammarfile==null)
    initGrammarFile();
  return grammarfile.getName();}
  
  /*
   * get defaultgrammar.g from the local directory
   * if it isn't there then try every .g file in the local directory
   * if that fails then throw exception
   */
  private void initGrammarFile(){
    setGrammarFile(getDefaultGrammarFile());}
  
  private File getDefaultGrammarFile(){
    File localdir=FRCG.getLocalDir();
    File grammarfile=null;
    Grammar testgrammar=null;
    //try to get the default grammar file from the local directory
    File[] localfiles=localdir.listFiles(new FileFilter(){
      public boolean accept(File a){
        return a.getName().equals(DEFAULT_GRAMMAR_FILE_NAME);}});
    if(localfiles.length>0){
      grammarfile=localfiles[0];
      try{
        testgrammar=extractGrammarFromFile(grammarfile);
      }catch(Exception x){}}
    //if that didn't work then try to get any grammar file from the local directory
    if(testgrammar==null){
      localfiles=localdir.listFiles(new FileFilter(){
        public boolean accept(File a){
          return a.getName().endsWith(DEFAULT_GRAMMAR_FILE_SUFFIX);}});
      for(File b:localfiles){
        grammarfile=b;
        try{
          testgrammar=extractGrammarFromFile(grammarfile);
        }catch(Exception x){}
        if(testgrammar!=null)break;}}
    //if we have a working grammar then return that file, otherwise we have a null grammar file.
    if(testgrammar==null)
      Log.m2("We appear to be missing a default grammar file in the local directory. Grammar set to null.");
    return grammarfile;}
  
  public void setGrammarFile(){
    JFileChooser fc=new JFileChooser("Select Grammar");
    fc.setCurrentDirectory(FRCG.getLocalDir());
    int r=fc.showOpenDialog(FRCG.instance.ui.frame);
    if(r!=JFileChooser.APPROVE_OPTION)
      return;
    File f=fc.getSelectedFile();
    setGrammarFile(f);}
  
  public void setGrammarFile(File f){
    grammarfile=f;}

  public void initUIComponent_Grammar(){
    FRCG.instance.ui.txtGrammar.setText(getGrammarName());}
  
  private Grammar extractGrammarFromFile(File file){
    FileInputStream fis;
    ObjectInputStream ois;
    Grammar grammar=null;
    try{
      fis=new FileInputStream(file);
      ois=new ObjectInputStream(fis);
      grammar=(Grammar)ois.readObject();
      ois.close();
    }catch(Exception x){
      throw new IllegalArgumentException(x);}
    return grammar;}
  
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
    FRCG.instance.ui.cmbRootBubbleModel.setModel(
      new DefaultComboBoxModel(m.toArray(new BubbleModel[m.size()])));}
  
  public void initUIComponent_RootBubbleModel(){
    FRCG.instance.ui.cmbRootBubbleModel.getModel().setSelectedItem(getRootBubbleModel());}
  
  /*
   * ################################
   * DETAIL SIZE LIMIT
   * unscaled floor for cultivatable bubble detail size
   * ################################
   */
  
  private static final double DETAIL_SIZE_LIMIT_DEFAULT=0.07;
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
    FRCG.instance.ui.txtDetailSizeLimit.setText(
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
    FRCG.instance.ui.lblTxtSymmetryControlFunction.setText(
      getSymmetryControlFunction().toString());}
  
  /*
   * ################################
   * RENDERER
   * We'll have a few of these
   * A simple one that we can create multiple versions of and set the colors for
   * A couple of fancy ones. A smart colorspace thing. A curve smoothing thing.
   * ################################
   */
  
  private static final Renderer_Abstract[] RENDERERS={
    new Renderer_000(),
//    new Renderer_001()
    };
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
    FRCG.instance.ui.cmbRenderer.setModel(new DefaultComboBoxModel(RENDERERS));}
  
  public void initUIComponent_Renderer(){
    FRCG.instance.ui.cmbRenderer.getModel().setSelectedItem(getRenderer());}
  
  /*
   * ################################
   * EXPORT IMAGE SCALE
   * we generally scale up
   * ################################
   */
   
  private static final double EXPORT_IMAGE_SCALE_DEFAULT=800.0;
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
    FRCG.instance.ui.txtExportImageScale.setText(
      Double.toString(getExportImageScale()));}
  
  /*
   * ################################
   * EXPORT DIR
   * path to the directory to which we export our generated fleen raster composition PNG files
   * init to default working directory
   * ################################
   */
  
  private File exportdir=null;
  
  public File getExportDir(){
    if(exportdir==null)
      initExportDir();
    return exportdir;}
  
  private void initExportDir(){
    exportdir=FRCG.getLocalDir();}
  
  public void setExportDir(){
    JFileChooser fc=new JFileChooser();
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fc.setCurrentDirectory(FRCG.getLocalDir());
    int r=fc.showOpenDialog(FRCG.instance.ui.frame);
    if(r!=JFileChooser.APPROVE_OPTION)
      return;
    File f=fc.getSelectedFile();
    setExportDir(f);}
  
  public void setExportDir(File f){
    if(f.isDirectory())
      exportdir=f;}
  
  public void initUIComponent_ExportDir(){
    String s=getExportDir().toString();
    FRCG.instance.ui.txtExportDir.setText(s);
    FRCG.instance.ui.txtExportDir.setToolTipText("Export Dir : "+s);}
  
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
    FRCG.instance.ui.spiGenExpImageCount.setValue(getGenExpImageCount());}
  
  /*
   * ################################
   * UTIL
   * ################################
   */
  
  public void initUIComponents(){
    Log.m1("Initializing ui components.");
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

}
