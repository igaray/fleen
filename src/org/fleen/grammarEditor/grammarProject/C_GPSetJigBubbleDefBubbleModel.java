package org.fleen.grammarEditor.grammarProject;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.Command_Abstract;

public class C_GPSetJigBubbleDefBubbleModel extends Command_Abstract{

  protected void run(){
    if(GE.grammarproject.hasFocusBubbleModel()){
      GPBubbleModel m=(GPBubbleModel)param;
      if(m.isValid())
        GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().bubblemodel=(GPBubbleModel)param;}}

}
