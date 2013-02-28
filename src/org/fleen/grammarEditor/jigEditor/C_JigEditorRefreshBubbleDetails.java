package org.fleen.grammarEditor.jigEditor;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.util.C_UIRefresh_Abstract;

public class C_JigEditorRefreshBubbleDetails extends C_UIRefresh_Abstract{

  protected void refresh(){
    refreshBubbleDetails();}
  
  static final void refreshBubbleDetails(){
    GE.jigeditor.lstBubbles.refreshForModelChange();
    if(GE.grammarproject.hasFocusJigBubbleDef()){
      GE.jigeditor.lblV0Val.setText(
        GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().getV0String());
      GE.jigeditor.lblV1Val.setText(
        GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().getV1String());
      GE.jigeditor.lblTwistVal.setText(
        GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().getTwistString());
      GE.jigeditor.lblFoamIndexVal.setText(String.valueOf(
        GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().foamindex));
      GE.jigeditor.lblChorusIndexVal.setText(String.valueOf(
        GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().chorusindex));
      GE.jigeditor.lstbubblemodels.refreshForModelChange();
    }else{
      //set all nulls TODO
    }
  }

}
