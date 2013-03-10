package org.fleen.samples.fleenRasterCompositionGen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.fleen.core.diamondGrammar.BubbleModel;
import org.fleen.core.diamondGrammar.Grammar;

public class UI{

  JFrame frame;
  JTextField txtGrammar;
  JComboBox cmbRootBubbleModel;
  JTextField txtDetailSizeLimit;
  JLabel lblTxtSymmetryControlFunction;
  JComboBox cmbRenderer;
  JTextField txtExportImageScale;
  JTextField txtExportDir;
  JSpinner spiGenExpImageCount;
  
  /**
   * TEST
   */
  public static void main(String[] args){
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        try{
          UI window=new UI();
          window.frame.setVisible(true);
        }catch(Exception e){
          e.printStackTrace();}}});}

  /**
   * Create the application.
   */
  public UI(){
    //set l&f
    try{
      for(LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){
        if("Nimbus".equals(info.getName())){
          UIManager.setLookAndFeel(info.getClassName());
          break;}}
    }catch(Exception e){}//go with default
    //init frame
    frame=new JFrame();
    frame.setBounds(100,100,800,600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle(FleenRasterCompositionGen.TITLE+" "+FleenRasterCompositionGen.VERSION);
    //init layout
    JPanel panControl = new JPanel();
    JPanel panel = new JPanel();
    panel.setBackground(Color.YELLOW);
    GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addContainerGap()
          .addComponent(panControl, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panel, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
          .addGap(10))
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
          .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
            .addComponent(panControl, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
            .addGroup(groupLayout.createSequentialGroup()
              .addContainerGap()
              .addComponent(panel, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)))
          .addContainerGap())
    );
    
    //TEXTFIELD GRAMMAR
    txtGrammar = new JTextField();
    txtGrammar.setBackground(new Color(135, 206, 250));
    txtGrammar.setEditable(false);
    txtGrammar.setToolTipText("Grammar");
    txtGrammar.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    txtGrammar.setText("grammar null");
    txtGrammar.setColumns(10);
    //mouse click listener
    //set grammar. If success then update relevant stuff 
    txtGrammar.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e){
        Grammar g=FleenRasterCompositionGen.frcg_instance.params.getGrammar();
        FleenRasterCompositionGen.frcg_instance.params.setGrammar();
        if(g!=FleenRasterCompositionGen.frcg_instance.params.getGrammar()){
          FleenRasterCompositionGen.frcg_instance.params.initUIComponent_Grammar();
          FleenRasterCompositionGen.frcg_instance.params.invalidateRootBubbleModel();
          FleenRasterCompositionGen.frcg_instance.params.initUIComponent_RootBubbleModelsList();
          FleenRasterCompositionGen.frcg_instance.params.initUIComponent_RootBubbleModel();}}});
    
    //COMBOBOX ROOTBUBBLEMODEL
    cmbRootBubbleModel = new JComboBox();
    cmbRootBubbleModel.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    cmbRootBubbleModel.setToolTipText("Root Bubble Model");
    //selection changed listener
    //when we do it, update the params
    cmbRootBubbleModel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        FleenRasterCompositionGen.frcg_instance.params.setRootBubbleModel(
          (BubbleModel)cmbRootBubbleModel.getModel().getSelectedItem());}});
    
    //TEXTFIELD DETAILSIZELIMIT
    txtDetailSizeLimit = new JTextField();
    txtDetailSizeLimit.setToolTipText("Detail Size Limit");
    txtDetailSizeLimit.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    txtDetailSizeLimit.setText("detail size limit null");
    txtDetailSizeLimit.setColumns(10);
    //focus lost listener
    //update and validate
    txtDetailSizeLimit.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        FleenRasterCompositionGen.frcg_instance.params.setDetailSizeLimit(txtDetailSizeLimit.getText());
        txtDetailSizeLimit.setText(
          Double.toString(FleenRasterCompositionGen.frcg_instance.params.getDetailSizeLimit()));}});
    
    //LABEL SYMMETRYCONTROLFUNCTION
    //this is a temporary dummy. We'll do this with some kind of graphical curve-drawing gadget later
    //right now we just use a "function class" and do some kind of constant thing with no interaction here.
    lblTxtSymmetryControlFunction = new JLabel("symmetry control function null");
    lblTxtSymmetryControlFunction.setToolTipText("Symmetry Control Function");
    lblTxtSymmetryControlFunction.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    
    cmbRenderer = new JComboBox();
    cmbRenderer.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    cmbRenderer.setToolTipText("Renderer");
    
    txtExportImageScale = new JTextField();
    txtExportImageScale.setToolTipText("Export Image Scale");
    txtExportImageScale.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    txtExportImageScale.setText("export image scale null");
    txtExportImageScale.setColumns(10);
    
    txtExportDir = new JTextField();
    txtExportDir.setBackground(new Color(135, 206, 250));
    txtExportDir.setEditable(false);
    txtExportDir.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    txtExportDir.setToolTipText("Export Dir");
    txtExportDir.setText("export dir null");
    txtExportDir.setColumns(10);
    
    JButton btnGenerate = new JButton("Generate");
    btnGenerate.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    
    JButton btnExport = new JButton("Export");
    btnExport.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    
    JButton btnGenexp = new JButton("Gen & Exp");
    btnGenexp.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    
    spiGenExpImageCount = new JSpinner();
    spiGenExpImageCount.setModel(new SpinnerNumberModel(new Integer(3), new Integer(1), null, new Integer(1)));
    spiGenExpImageCount.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    spiGenExpImageCount.setToolTipText("Gen & Exp Image Count");
    
    GroupLayout gl_panControl = new GroupLayout(panControl);
    gl_panControl.setHorizontalGroup(
      gl_panControl.createParallelGroup(Alignment.LEADING)
        .addGroup(Alignment.TRAILING, gl_panControl.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panControl.createParallelGroup(Alignment.TRAILING)
            .addComponent(txtGrammar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(cmbRenderer, 0, 211, Short.MAX_VALUE)
            .addComponent(txtExportImageScale, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(txtDetailSizeLimit, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(lblTxtSymmetryControlFunction, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(cmbRootBubbleModel, 0, 211, Short.MAX_VALUE)
            .addComponent(txtExportDir, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(btnGenerate, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(btnExport, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addGroup(gl_panControl.createSequentialGroup()
              .addComponent(btnGenexp, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(spiGenExpImageCount, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)))
          .addContainerGap())
    );
    gl_panControl.setVerticalGroup(
      gl_panControl.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panControl.createSequentialGroup()
          .addContainerGap()
          .addComponent(txtGrammar, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(cmbRootBubbleModel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(txtDetailSizeLimit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(lblTxtSymmetryControlFunction)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(cmbRenderer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(txtExportImageScale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(txtExportDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(btnGenerate)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(btnExport)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panControl.createParallelGroup(Alignment.BASELINE)
            .addComponent(btnGenexp)
            .addComponent(spiGenExpImageCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
          .addContainerGap(248, Short.MAX_VALUE))
    );
    panControl.setLayout(gl_panControl);
    frame.getContentPane().setLayout(groupLayout);
  }
}