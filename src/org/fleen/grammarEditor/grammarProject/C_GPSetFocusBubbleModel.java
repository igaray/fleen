package org.fleen.grammarEditor.grammarProject;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.Command_Abstract;

public class C_GPSetFocusBubbleModel extends Command_Abstract{

  /*
   * get the bubble model : m
   * set focusbubblemodel to m  
   */
  protected void run(){
    GPBubbleModel m=(GPBubbleModel)param;
    GE.grammarproject.focusbubblemodel=m;}

}
