package org.fleen.grammarEditor.overview;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.C_UIRefresh_Abstract;

public class C_OverviewRefreshForCreateDestroy extends C_UIRefresh_Abstract{

  protected void refresh(){
    refreshAll();}
  
  static void refreshAll(){
    GE.overview.grid.refreshForCreateDestroy();
  }

}
