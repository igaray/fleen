package org.fleen.grammarEditor.overview;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.C_UIShow_Abstract;

public class C_OverviewShow extends C_UIShow_Abstract{

  protected void show(){
    GE.uimain.showOverview();
    C_OverviewRefreshAll.refreshAll();}

}
