package org.fleen.grammarEditor.bubbleModelEditor;

import org.fleen.grammarEditor.util.C_UIRefresh_Abstract;

public class C_BubbleModelEditorRefreshAll extends C_UIRefresh_Abstract{

  protected void refresh(){
    refreshAll();}
  
  static final void refreshAll(){
    C_BubbleModelEditorRefreshCanvas.refreshCanvas();
    C_BubbleModelEditorRefreshDetails.refreshDetails();}

}
