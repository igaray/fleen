package org.fleen.grammarEditor.jigEditor;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.C_UIRefresh_Abstract;

public class C_JigEditorRefreshCanvas extends C_UIRefresh_Abstract{

  protected void refresh(){
    refreshCanvas();}
  
  static final void refreshCanvas(){
    GE.jigeditor.panjigcanvas.refreshAll();
    
  }

  

}
