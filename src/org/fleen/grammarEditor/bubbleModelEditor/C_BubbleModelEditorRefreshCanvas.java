package org.fleen.grammarEditor.bubbleModelEditor;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.C_UIRefresh_Abstract;

/*
 * refresh the view
 * TODO this is crude and inefficient 
 * split this into several refresh commands, each for specific refresh scenarios
 */
public class C_BubbleModelEditorRefreshCanvas extends C_UIRefresh_Abstract{

  protected void refresh(){
    refreshCanvas();}
  
  static final void refreshCanvas(){
    //invalidate all images
//    GE.bubblemodeleditor.panbubblemodeleditorcanvas.imagemain=null;
//    GE.bubblemodeleditor.panbubblemodeleditorcanvas.imagelayergrid=null;
//    GE.bubblemodeleditor.panbubblemodeleditorcanvas.imagelayerdetails=null;
    //rebuild images
    GE.bubblemodeleditor.bubblemodeleditorcanvas.refreshAll();}
  
}
