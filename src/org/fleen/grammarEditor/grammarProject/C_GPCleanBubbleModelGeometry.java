package org.fleen.grammarEditor.grammarProject;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.bubbleModelEditor.C_BubbleModelEditorRefreshAll;
import org.fleen.grammarEditor.util.Command_Abstract;

public class C_GPCleanBubbleModelGeometry extends Command_Abstract{

  /*
   * orient model so that 
   *   dis(v0,v1)=0
   *   twist=positive
   *   scale is minimal
   * then lock the canvas so it cannot be edited any more.
   */
  protected void run(){
    GE.grammarproject.focusbubblemodel.cleanGeometry();
    GE.command(new C_BubbleModelEditorRefreshAll());}

}
