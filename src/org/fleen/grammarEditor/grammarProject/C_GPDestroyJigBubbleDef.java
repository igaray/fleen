package org.fleen.grammarEditor.grammarProject;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.Command_Abstract;

public class C_GPDestroyJigBubbleDef extends Command_Abstract{

  protected void run(){
    GE.grammarproject.focusbubblemodel.focusjig.destroyBubbleDef();}

}
