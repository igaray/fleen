package org.fleen.grammarEditor.grammarProject;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.Command_Abstract;

public class C_GPSetFocusJig extends Command_Abstract{

  /*
   * get the jig j
   * get the jig's bubblemodel : bm
   * set focusbubblemodel to bm
   * set the focusbubblemodel's focusjig to j
   */
  protected void run(){
    GPJig j=(GPJig)param;
    GE.grammarproject.focusbubblemodel=j.bubblemodel;
    GE.grammarproject.focusbubblemodel.focusjig=j;}

}
