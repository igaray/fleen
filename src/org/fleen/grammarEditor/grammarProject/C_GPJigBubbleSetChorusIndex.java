package org.fleen.grammarEditor.grammarProject;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.Command_Abstract;

public class C_GPJigBubbleSetChorusIndex extends Command_Abstract{

  protected void run(){
    if(GE.grammarproject.hasFocusJigBubbleDef())
      GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().setChorusIndex((Integer)param);}

}
