package org.fleen.grammarEditor.bubbleModelEditor;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.C_UIRefresh_Abstract;

/*
 * refresh the view
 * TODO this is crude and inefficient 
 * split this into several refresh commands, each for specific refresh scenarios
 */
public class C_BubbleModelEditorRefreshForModelChange extends C_UIRefresh_Abstract{

  protected void refresh(){
    GE.bubblemodeleditor.bubblemodeleditorcanvas.refreshDetails();
    GE.command(new C_BubbleModelEditorRefreshDetails());}

}
