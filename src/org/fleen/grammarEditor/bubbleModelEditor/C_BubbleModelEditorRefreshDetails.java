package org.fleen.grammarEditor.bubbleModelEditor;

import org.fleen.core.gKis.KVectorRDPath;
import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.C_UIRefresh_Abstract;

/*
 * refresh the view
 * TODO this is crude and inefficient 
 * split this into several refresh commands, each for specific refresh scenarios
 */
public class C_BubbleModelEditorRefreshDetails extends C_UIRefresh_Abstract{

  protected void refresh(){
    refreshDetails();}
  
  static final void refreshDetails(){
    GE.bubblemodeleditor.txtID.setText(GE.grammarproject.focusbubblemodel.id);
    setVectorDetails();}
  
  private static void setVectorDetails(){
    if(GE.grammarproject.focusbubblemodel.locked){
      KVectorRDPath p=GE.grammarproject.focusbubblemodel.getVertexPath().getVectorPath();
      GE.bubblemodeleditor.txtVectors.setText("VECTORS : "+p.toString());}}

}
