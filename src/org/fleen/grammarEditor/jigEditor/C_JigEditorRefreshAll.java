package org.fleen.grammarEditor.jigEditor;

import org.fleen.grammarEditor.util.C_UIRefresh_Abstract;

public class C_JigEditorRefreshAll extends C_UIRefresh_Abstract{

  protected void refresh(){
    refreshAll();}
  
  static final void refreshAll(){
    C_JigEditorRefreshCanvas.refreshCanvas();
    C_JigEditorRefreshMainDetails.refreshGridDetails();
    C_JigEditorRefreshBubbleDetails.refreshBubbleDetails();}

}
