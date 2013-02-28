package org.fleen.grammarEditor.jigEditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.grammarProject.C_GPSetFocusJigBubbleDef;
import org.fleen.grammarEditor.grammarProject.GPJigBubbleDef;

@SuppressWarnings("serial")
public class JigEditorBubbleDefList extends JList{
  
  /*
   * ################################
   * CONSTRUCTOR 
   * ################################
   */
  
  public JigEditorBubbleDefList(){
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
    setVisibleRowCount(0);//turns on autoformatting
    setBackground(COLOR_BACKGROUND_DEFAULT);
    setFont(new Font("DejaVu Sans Mono", Font.BOLD, 16));
    setModel(new LM0());
    setCellRenderer(new CR0());
    addListSelectionListener(new LSL0());}
  
  /*
   * ################################
   * SELECTION 
   * The selected item is the focus jigbubble
   * ################################
   */
  
  GPJigBubbleDef newlyselected;
  
  class LSL0 implements ListSelectionListener{
    public void valueChanged(ListSelectionEvent e) {
      Object[] sv=getSelectedValues();
      if(sv.length==0)return;
      GPJigBubbleDef bubble=(GPJigBubbleDef)sv[0];
      if(!bubble.equals(newlyselected)){
        newlyselected=bubble;
        GE.command(new C_GPSetFocusJigBubbleDef(),newlyselected);
        GE.command(new C_JigEditorRefreshAll());
      }else{
        newlyselected=null;}}}
  
  /*
   * ################################
   * LIST MODEL
   * ################################
   */

  public void refreshForModelChange(){
    clearSelection();
    firePropertyChange("model",null,getModel());
    int a=GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDefIndex();
    setSelectedIndex(a);
    ensureIndexIsVisible(a);}
  
  //START LIST MODEL CLASS
  class LM0 extends AbstractListModel{

    public int getSize(){
      if(GE.grammarproject.hasFocusJig()){
        return GE.grammarproject.focusbubblemodel.focusjig.getBubbleDefCount();
      }else{
        return 0;}}
    
    public Object getElementAt(int index){
      if(GE.grammarproject.focusbubblemodel.focusjig.hasBubbleDefs()){
        return GE.grammarproject.focusbubblemodel.focusjig.getBubbleDef(index);
      }else{
        return null;}}}
  //END LIST MODEL CLASS

  /*
   * ################################
   * CELL RENDERER
   * ################################
   */

  private static final Color 
    COLOR_BACKGROUND_DEFAULT=new Color(192,192,192),
    COLOR_BACKGROUND_SELECTED=new Color(255,255,0),
    COLOR_FOREGROUND_DEFAULT=new Color(0,0,0);
  
  private static final Font CELLFONT=new Font("DejaVu Sans Mono",Font.PLAIN,20);
  
  //START CELL RENDERER CLASS
  class CR0 extends JLabel implements ListCellRenderer{
    
    public CR0(){
      setHorizontalAlignment(SwingConstants.CENTER);
      setFont(CELLFONT);
      setForeground(COLOR_FOREGROUND_DEFAULT);
      setOpaque(true);}
    
    public Component getListCellRendererComponent(
      JList list,Object a,int index,boolean isselected,boolean hasfocus){
      GPJigBubbleDef bubble=(GPJigBubbleDef)a;
      setText(getIDString(bubble));
      if(isselected){
        setBackground(COLOR_BACKGROUND_SELECTED);
      }else{
        setBackground(COLOR_BACKGROUND_DEFAULT);}
      return this;}}
  //END CELL RENDERER CLASS
  
  private String getIDString(GPJigBubbleDef b){
    if(b.id<10){
      return "00"+b.id;
    }else if(b.id<100){
      return "0"+b.id;
    }else{
      return String.valueOf(b.id);}}
  
}
