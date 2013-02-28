package org.fleen.grammarEditor.bubbleModelEditor;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.C_UIShow_Abstract;

public class C_BubbleModelEditorShow extends C_UIShow_Abstract{

  protected void show(){
    GE.uimain.showBubbleModelEditor();
    C_BubbleModelEditorRefreshAll.refreshAll();}

}
