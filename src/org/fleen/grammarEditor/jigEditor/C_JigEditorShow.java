package org.fleen.grammarEditor.jigEditor;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.C_UIShow_Abstract;

public class C_JigEditorShow extends C_UIShow_Abstract{

  protected void show(){
    GE.uimain.showJigEditor();
    C_JigEditorRefreshAll.refreshAll();}

}
