package org.fleen.grammarEditor.jigEditor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.fleen.grammarEditor.C;
import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.grammarProject.C_GPSetJigBubbleDefBubbleModel;
import org.fleen.grammarEditor.grammarProject.GPBubbleModel;

@SuppressWarnings("serial")
public class JigEditorBubbleModelList extends JList{
  
  /*
   * ################################
   * CONSTRUCTOR 
   * ################################
   */
  
  public JigEditorBubbleModelList(){
    setLayoutOrientation(HORIZONTAL_WRAP);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setFixedCellHeight(C.JE_BMLISTCELLSPAN);
    setFixedCellWidth(C.JE_BMLISTCELLSPAN);
    setVisibleRowCount(0);//turns on autoformatting
    setBackground(COLOR_BACKGROUND_DEFAULT);
    setModel(new LM0());
    setCellRenderer(new CR0());
    addListSelectionListener(new LSL0());}
  
  /*
   * ################################
   * SELECTION 
   * ################################
   */
  
  GPBubbleModel newlyselected;
  boolean suppresslistselectionlistener=false;
  
  class LSL0 implements ListSelectionListener{
    public void valueChanged(ListSelectionEvent e) {
      if(!suppresslistselectionlistener){
        Object[] sv=getSelectedValues();
        if(sv.length==0)return;
        GPBubbleModel a=(GPBubbleModel)sv[0];
        if(!a.equals(newlyselected)){
          newlyselected=a;
          GE.command(new C_GPSetJigBubbleDefBubbleModel(),newlyselected);
          GE.command(new C_JigEditorRefreshAll());
        }else{
          newlyselected=null;}}}}
  
  /*
   * ################################
   * LIST MODEL
   * ################################
   */

  //seems rather bunk but it works
  public void refreshForModelChange(){
    suppresslistselectionlistener=true;
    clearSelection();
    firePropertyChange("model",null,getModel());
    int a=GE.grammarproject.bubblemodels.indexOf(
      GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().bubblemodel);
    setSelectedIndex(a);
    ensureIndexIsVisible(a);
    suppresslistselectionlistener=false;
    }
  
  //START LIST MODEL CLASS
  class LM0 extends AbstractListModel{

    public int getSize(){
      if(GE.grammarproject==null){
        return 0;
      }else{
        return GE.grammarproject.bubblemodels.size();}}
    
    public Object getElementAt(int index){
      if(GE.grammarproject==null){
        return null;
      }else{
        return GE.grammarproject.bubblemodels.get(index);}}}
  //END LIST MODEL CLASS

  /*
   * ################################
   * CELL RENDERER
   * ################################
   */

  private static final Color 
    COLOR_BACKGROUND_SELECTED=new Color(255,255,0),
    COLOR_BACKGROUND_DEFAULT=new Color(224,224,224);
  
  //START CELL RENDERER CLASS
  class CR0 extends JLabel implements ListCellRenderer{
    
    public CR0(){
      setOpaque(true);
      setHorizontalAlignment(SwingConstants.CENTER);
      setVerticalAlignment(SwingConstants.CENTER);}
    
    public Component getListCellRendererComponent(
      JList list,Object object,int index,boolean isselected,boolean cellHasFocus){
      setIcon(new ImageIcon(((GPBubbleModel)object).getJigEditorBMListImage()));
      if(isselected){
        setBackground(COLOR_BACKGROUND_SELECTED);
      }else{
        setBackground(COLOR_BACKGROUND_DEFAULT);
        }
      return this;}}
  //END CELL RENDERER CLASS
  
}
