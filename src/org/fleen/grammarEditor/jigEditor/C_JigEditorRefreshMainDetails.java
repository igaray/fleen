package org.fleen.grammarEditor.jigEditor;

import org.fleen.core.grammar.Jig;
import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.C_UIRefresh_Abstract;

public class C_JigEditorRefreshMainDetails extends C_UIRefresh_Abstract{

  protected void refresh(){
    refreshGridDetails();}
  
  static final void refreshGridDetails(){
    GE.jigeditor.lblBubbleModelID.setText(GE.grammarproject.focusbubblemodel.id);
    GE.jigeditor.txtJigID.setText(GE.grammarproject.focusbubblemodel.focusjig.id);
    GE.jigeditor.lblGridScaleValue.setText(
      String.valueOf(GE.grammarproject.focusbubblemodel.focusjig.getGridDensity()));
    if(GE.grammarproject.hasFocusJig()){
      if(GE.grammarproject.focusbubblemodel.focusjig.type==Jig.TYPE_SPLITTER){
        GE.jigeditor.comboType.getModel().setSelectedItem("Splitter");    
      }else{
        GE.jigeditor.comboType.getModel().setSelectedItem("Boiler");}}
    
    }

}
