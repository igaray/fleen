package org.fleen.grammarEditor.grammarProject;

import org.fleen.core.gKis.KVertex;
import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.Command_Abstract;

public class C_GPAddVertexToFocusBubbleModel extends Command_Abstract{

  protected void run(){
    GE.grammarproject.focusbubblemodel.addVertex((KVertex)param);}

}
