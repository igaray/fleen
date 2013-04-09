package org.fleen.grammarEditor.grammarProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.fleen.core.pGrammatic.BubbleModel;
import org.fleen.core.pGrammatic.Grammar;
import org.fleen.core.pGrammatic.Jig;
import org.fleen.core.pGrammatic.JigBubbleDef;
import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.overview.C_OverviewShow;

/*
 * All the data associated with a Grammar class object in a conveniently manipulable form
 * Methods for import and export
 * Methods for data validation
 */
public class GrammarProject implements Serializable{
  
  /*
   * ################################
   * MISC FIELDS
   * ################################
   */
  
  private static final long serialVersionUID=-6186539286916219317L;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */

  //we use this for all purposes, both create and import
  //we might do something with it later
  public GrammarProject(){}
  
  /*
   * ################################
   * BASIC GRAMMAR INFO
   * ################################
   */
  
  //grammar name
  static final String DEFAULTGRAMMARNAME="NEW_GRAMMAR";
  public String name=DEFAULTGRAMMARNAME;
  //path to a grammarproject file
  public File filepath=null;
  
  /*
   * ################################
   * BUBBLEMODELS
   * ################################
   */

  static final String BUBBLE_MODEL_INIT_NAME="bm_";
  static final int BUBBLE_MODEL_INIT_MAX_INDEX=1000;
  
  public List<GPBubbleModel> bubblemodels=new ArrayList<GPBubbleModel>();
  public GPBubbleModel focusbubblemodel=null;
  
  public boolean containsBubbleModel(String id){
    for(GPBubbleModel a:bubblemodels)
      if(a.id.equals(id))
        return true;
    return false;}
  
  public GPBubbleModel createBubbleModel(){
    focusbubblemodel=new GPBubbleModel(getIDForNewBubbleModel());
    bubblemodels.add(focusbubblemodel);
    return focusbubblemodel;}
  
  public void destroyFocusBubbleModel(){
    int a=bubblemodels.indexOf(focusbubblemodel);
    bubblemodels.remove(a);
    a--;
    if(a==-1)a=0;
    if(bubblemodels.isEmpty()){
      focusbubblemodel=null;
    }else{
      focusbubblemodel=bubblemodels.get(a);}}
  
  public String getIDForNewBubbleModel(){
    String a=BUBBLE_MODEL_INIT_NAME;
    for(int i=0;i<BUBBLE_MODEL_INIT_MAX_INDEX;i++){
      a=BUBBLE_MODEL_INIT_NAME+i;
      if(!containsBubbleModel(a))return a;}
    throw new IllegalArgumentException("issue in new bubblemodel id accquisition");}
  
  public int getFocusBubbleModelIndex(){
    if(bubblemodels.isEmpty())return 0;
    return bubblemodels.indexOf(focusbubblemodel);}
  
  //returns the bubblemodel in this gpgrammar specified by id
  public GPBubbleModel getBubbleModel(String id){
    for(GPBubbleModel a:bubblemodels)
      if(a.id.equals(id))
        return a;
    return null;}
  
  /*
   * ################################
   * UTIL
   * ################################
   */
  
  public boolean hasFocusBubbleModel(){
    return focusbubblemodel!=null;}
  
  public boolean hasFocusJig(){
    return hasFocusBubbleModel()&&(focusbubblemodel.focusjig!=null);}
  
  public boolean hasFocusJigBubbleDef(){
    return hasFocusJig()&&(focusbubblemodel.focusjig.getFocusBubbleDef()!=null);}
  
  public int getTotalJigCount(){
    return -1;}
  
  /*
   * ################################
   * IMPORT GRAMMAR
   * ################################
   */
  
  public static final void importGrammar(){
    File grammarfile=getGrammarFileForImport();
    if(grammarfile==null)return;
    Grammar grammar=extractGrammarFromFileForImport(grammarfile);
    if(grammar==null){
      System.out.println("GRAMMAR IMPORT FAILED");
      return;}
    //we've got the Grammar class object, now process it into a GrammarProject class object 
    GrammarProject newgrammarproject=new GrammarProject();
    newgrammarproject.filepath=grammarfile;
    newgrammarproject.name=grammarfile.getName();
    int importedbubblemodelscount=importBubbleModels(grammar,newgrammarproject);
    int importedjigscount=0;
    if(importedbubblemodelscount>0)
      importedjigscount=importJigs(grammar,newgrammarproject);
    //done
    System.out.println("importedbubblemodelscount="+importedbubblemodelscount);
    System.out.println("importedjigscount="+importedjigscount);
    //maybe print out the import counts in an info bar somewhere
    GE.grammarproject=newgrammarproject;
    GE.command(new C_OverviewShow());}
  
  private static final int importJigs(Grammar grammar,GrammarProject newgrammarproject){
    List<Jig> jigs;
    GPJig gpjig;
    GPJigBubbleDef gpjbd;
    int count=0;
    //for every bubblemodel get the jigs
    for(GPBubbleModel gpbm:newgrammarproject.bubblemodels){
      jigs=grammar.getJigs(gpbm.id);
      for(Jig jig:jigs){
        count++;
        //create a gpjig
        gpjig=new GPJig(); 
        gpbm.jigs.add(gpjig);
        gpjig.id=jig.id;
        gpjig.type=jig.type;
        gpjig.bubblemodel=gpbm;
        gpjig.griddensity=jig.getGridDensity();
        //do the bubbledefs
        for(JigBubbleDef jbd:jig.bubbledefs){
          gpjbd=new GPJigBubbleDef();
          gpjig.bubbledefs.add(gpjbd);
          gpjbd.id=gpjig.getUniqueIDForBubbleDef();
          gpjbd.bubblemodel=newgrammarproject.getBubbleModel(jbd.bubblemodelid);
          gpjbd.v0=jbd.v0;
          gpjbd.v1=jbd.v1;
          gpjbd.twist=jbd.twist;
          gpjbd.foamindex=jbd.foamindex;
          gpjbd.chorusindex=jbd.chorusindex;}
        //set focus bubbledef for this jig
        if(!gpjig.bubbledefs.isEmpty())
          gpjig.focusbubbledef=gpjig.bubbledefs.get(0);}
      //set focus jig for this gpbubblemodel
      if(!gpbm.jigs.isEmpty())
        gpbm.focusjig=gpbm.jigs.get(0);}
    return count;}
  
  private static final int importBubbleModels(Grammar grammar,GrammarProject newgrammarproject){
    int c=0;
    for(Grammar.ModelAndJigs mj:grammar.modelsandjigs){
      GPBubbleModel m=new GPBubbleModel();
      m.id=mj.model.id;
      m.vertexpath=mj.model.getVectorPath().getVertexPath();
      m.validate();
      if(m.isValid()){
        newgrammarproject.bubblemodels.add(m);
        c++;}}
    if(c>0)
      newgrammarproject.focusbubblemodel=newgrammarproject.bubblemodels.get(0);
    return c;}
  
  private static final File getGrammarFileForImport(){
    JFileChooser fc=new JFileChooser();
    fc.setCurrentDirectory(GE.getLocalDir());
    int r=fc.showOpenDialog(GE.uimain);
    if(r!=JFileChooser.APPROVE_OPTION)
      return null;
    return fc.getSelectedFile();}
  
  private static final Grammar extractGrammarFromFileForImport(File file){
    FileInputStream fis;
    ObjectInputStream ois;
    Grammar grammar=null;
    try{
      fis=new FileInputStream(file);
      ois=new ObjectInputStream(fis);
      grammar=(Grammar)ois.readObject();
      ois.close();
    }catch(Exception e){
      System.out.println("#^#^# EXCEPTION IN EXTRACT GRAMMAR FROM FILE FOR IMPORT #^#^#");
      e.printStackTrace();
      return null;}
    return grammar;}
  
  /*
   * ################################
   * EXPORT GRAMMAR
   * ################################
   */
  
  public static final void exportGrammar(){
    JFileChooser fc=new JFileChooser();
    fc.setCurrentDirectory(GE.grammarproject.filepath);
    if(fc.showSaveDialog(GE.uimain)!=JFileChooser.APPROVE_OPTION)return;
    GE.grammarproject.filepath=fc.getSelectedFile();
    //
    FileOutputStream fos;
    ObjectOutputStream oot;
    try{
      fos=new FileOutputStream(GE.grammarproject.filepath);
      oot=new ObjectOutputStream(fos);
      oot.writeObject(getGrammarForExport());
      oot.close();
    }catch(IOException ex){
      System.out.println("%-%-% EXCEPTION IN EXPORT GRAMMAR %-%-%");
      ex.printStackTrace();}}
  
  private static final Grammar getGrammarForExport(){
    Grammar grammar=new Grammar();
    BubbleModel bm;
    Jig jig;
    JigBubbleDef jbd;
    for(GPBubbleModel gpbm:GE.grammarproject.bubblemodels){
      if(gpbm.isValid()){
        //do bubblemodel
        bm=new BubbleModel();
        grammar.addBubbleModel(bm);
        bm.setID(gpbm.id);
        bm.setVectorPath(gpbm.vertexpath.getVectorPath());
        //do jigs
        for(GPJig gpjig:gpbm.jigs){
          jig=new Jig();
          grammar.addJig(gpbm.id,jig);
          jig.grammar=grammar;
          jig.id=gpjig.id;
          jig.type=gpjig.type;
          jig.targetbubblemodelid=gpbm.id;
          jig.griddensity=gpjig.griddensity;
          for(GPJigBubbleDef gpjbd:gpjig.bubbledefs){
            jbd=new JigBubbleDef();
            jig.bubbledefs.add(jbd);
            jbd.bubblemodelid=gpjbd.bubblemodel.id;
            jbd.v0=gpjbd.v0;
            jbd.v1=gpjbd.v1;
            jbd.twist=gpjbd.twist;
            jbd.foamindex=gpjbd.foamindex;
            jbd.chorusindex=gpjbd.chorusindex;}}}}
    return grammar;}
  
}
