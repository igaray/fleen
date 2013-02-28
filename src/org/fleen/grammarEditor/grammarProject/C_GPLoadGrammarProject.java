package org.fleen.grammarEditor.grammarProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.Command_Abstract;

public class C_GPLoadGrammarProject extends Command_Abstract{

  protected void run(){
    JFileChooser fc=new JFileChooser();
    fc.setCurrentDirectory(new File(GE.TESTGRAMMARDIR));
    int r=fc.showOpenDialog(GE.uimain);
    if(r!=JFileChooser.APPROVE_OPTION)
      return;
    //
    File selectedfile=fc.getSelectedFile();
    FileInputStream fis;
    ObjectInputStream ois;
    try{
      fis=new FileInputStream(selectedfile);
      ois=new ObjectInputStream(fis);
      GE.grammarproject=(GrammarProject)ois.readObject();
      ois.close();
    }catch(Exception e){
      System.out.println("#^#^# EXCEPTION IN LOAD GRAMMAR PROJECT #^#^#");
      e.printStackTrace();}
    //
    GE.grammarproject.filepath=selectedfile;
    GE.grammarproject.name=selectedfile.getName();}

}
