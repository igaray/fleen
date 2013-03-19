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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

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
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.fleen.core.diamondGrammar.BubbleModel;
import org.fleen.samples.fleenRasterCompositionGen.command.CQ;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_Abstract;

public class UINew{

  public JFrame frame;
  public Viewer panView;
  public JLabel lblStatus;//TODO
  public JComboBox cmbRootBubbleModel;
  public JTextField txtGrammarPath;
  public JTextField txtExportDir;
  public JTextField txtDetailLimit;
  public JComboBox cmbSymConFun;
  public JComboBox cmbRenderer;
  public JTextField txtExpScale;
  public JSpinner spiGenExpCount;

  /**
   * Launch the application.
   */
  public static void main(String[] args){
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        try{
          UINew window=new UINew();
          window.frame.setVisible(true);
        }catch(Exception e){
          e.printStackTrace();}}});}

  public UINew(){
    //LOOK AND FEEL
    try{
      for(LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){
        if("Nimbus".equals(info.getName())){
          UIManager.setLookAndFeel(info.getClassName());
          break;}}
    }catch(Exception e){}//go with default
    
    //FRAME
    frame=new JFrame();
    frame.setBounds(100,100,506,504);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        FRCG.terminateInstance();}});
    frame.setTitle(FRCG.TITLE+" "+FRCG.VERSION);
    
    //TOP
    JPanel panTop = new JPanel();
    
    //VIEW
    panView = new Viewer();
    panView.setBackground(new Color(255, 0, 255));
    
    //STATUS 
    lblStatus = new JLabel("status status ...+... foo foo");
    lblStatus.setFont(new Font("Dialog", Font.BOLD, 16));
    
    //FRAME CONTENTPANE LAYOUT
    GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
          .addContainerGap()
          .addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
          .addGap(2))
        .addGroup(groupLayout.createSequentialGroup()
          .addGap(21)
          .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
            .addComponent(panView, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
            .addComponent(panTop, Alignment.LEADING, 0, 0, Short.MAX_VALUE))
          .addGap(18))
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
          .addComponent(panTop, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panView, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(lblStatus)
          .addContainerGap())
    );
    
    //ROOT BUBBLE MODEL
    cmbRootBubbleModel = new JComboBox();
    //selection changed listener
    //when we do it, update the params
    cmbRootBubbleModel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        FRCG.instance.config.setRootBubbleModel(
          (BubbleModel)cmbRootBubbleModel.getModel().getSelectedItem());}});
    
    //GRAMMAR FILE
    txtGrammarPath = new JTextField("~/foo/bar/null/grammarfoo.g");
    txtGrammarPath.setEditable(false);
    txtGrammarPath.setBackground(new Color(154, 205, 50));
    txtGrammarPath.setFont(new Font("Dialog", Font.BOLD, 14));
    //mouse click listener
    //set grammar. If success then update relevant stuff 
    txtGrammarPath.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        try{
          File gf=FRCG.instance.config.getGrammarFile();
          FRCG.instance.config.setGrammarFile();
          if(gf!=FRCG.instance.config.getGrammarFile()){
            FRCG.instance.config.initUIComponent_Grammar();
            FRCG.instance.config.invalidateRootBubbleModel();
            FRCG.instance.config.initUIComponent_RootBubbleModelsList();
            FRCG.instance.config.initUIComponent_RootBubbleModel();}
        }catch(Exception x){}}});
    
    //EXPORT DIR
    txtExportDir = new JTextField("~/foo/bar/null/frcgexport");
    txtExportDir.setBackground(new Color(255, 182, 193));
    txtExportDir.setEditable(false);
    txtExportDir.setFont(new Font("Dialog", Font.BOLD, 14));
    //mouse click listener
    //set export dir. 
    txtExportDir.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e){
        FRCG.instance.config.setExportDir();
        FRCG.instance.config.initUIComponent_ExportDir();}});
    
    //DETAIL LIMIT
    txtDetailLimit = new JTextField();
    txtDetailLimit.setFont(new Font("Dialog", Font.BOLD, 14));
    txtDetailLimit.setText("0.123");
    txtDetailLimit.setColumns(10);
    //focus lost listener
    //update and validate
    txtDetailLimit.addFocusListener(new FocusAdapter() {
      public void focusLost(FocusEvent e) {
        FRCG.instance.config.setDetailSizeLimit(txtDetailLimit.getText());
        txtDetailLimit.setText(
          Double.toString(FRCG.instance.config.getDetailSizeLimit()));}});
    
    //SYMMETRY CONTROL FUNCTION
    //TODO
    cmbSymConFun = new JComboBox();
    
    //RENDERER
    cmbRenderer = new JComboBox();
    //selection changed listener
    //when we do it, update the params
    cmbRenderer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        try{
          FRCG.instance.config.setRenderer(
            (Renderer_Abstract)cmbRenderer.getModel().getSelectedItem());
        }catch(Exception x){}}});
    
    //EXPORT SCALE
    txtExpScale = new JTextField();
    txtExpScale.setFont(new Font("Dialog", Font.BOLD, 14));
    txtExpScale.setColumns(10);
    //focus lost listener
    //update and validate
    txtExpScale.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        FRCG.instance.config.setExportImageScale(txtExpScale.getText());
        txtExpScale.setText(
          Double.toString(FRCG.instance.config.getExportImageScale()));}});
    
    //GENERATE AND EXPORT COUNT
    spiGenExpCount = new JSpinner();
    spiGenExpCount.setFont(new Font("Dialog", Font.BOLD, 14));
    //update and validate param on state change
    spiGenExpCount.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e){
        try{
          FRCG.instance.config.setGenExpImageCount(
          (Integer)spiGenExpCount.getModel().getValue());
        }catch(Exception x){}
        spiGenExpCount.getModel().setValue(
          FRCG.instance.config.getGenExpImageCount());}});
    
    //GENERATE
    JButton btnGenerate = new JButton("Generate");
    btnGenerate.setFont(new Font("Dialog", Font.BOLD, 14));
    //mouse click listener
    btnGenerate.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        try{
          CQ.generate();
        }catch(Exception x){
          x.printStackTrace();}}});
    
    //EXPORT
    JButton btnExp = new JButton("Export");
    btnExp.setFont(new Font("Dialog", Font.BOLD, 14));
    //mouse click listener
    btnExp.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        try{
          CQ.renderForExport();
          CQ.export();
        }catch(Exception x){
          x.printStackTrace();}}});
    
    //GENERATE AND EXPORT
    JButton btnGenAndExp = new JButton("G&E");
    btnGenAndExp.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
    //mouse click listener
    btnGenAndExp.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        try{
          CQ.generateAndExport();
        }catch(Exception x){
          x.printStackTrace();}}});
    
    //TOP LAYOUT
    GroupLayout gl_panTop = new GroupLayout(panTop);
    gl_panTop.setHorizontalGroup(
      gl_panTop.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panTop.createSequentialGroup()
          .addGroup(gl_panTop.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panTop.createSequentialGroup()
              .addComponent(txtDetailLimit, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(cmbSymConFun, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(cmbRenderer, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE))
            .addGroup(gl_panTop.createSequentialGroup()
              .addComponent(btnGenerate)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(btnExp)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(txtExpScale, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(btnGenAndExp)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(spiGenExpCount, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
            .addComponent(txtGrammarPath, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
            .addComponent(txtExportDir, GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(cmbRootBubbleModel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
    );
    gl_panTop.setVerticalGroup(
      gl_panTop.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panTop.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panTop.createParallelGroup(Alignment.LEADING)
            .addComponent(cmbRootBubbleModel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
            .addGroup(gl_panTop.createSequentialGroup()
              .addComponent(txtGrammarPath)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(txtExportDir)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addGroup(gl_panTop.createParallelGroup(Alignment.BASELINE)
                .addComponent(txtDetailLimit, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbSymConFun, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbRenderer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(ComponentPlacement.RELATED)
              .addGroup(gl_panTop.createParallelGroup(Alignment.BASELINE)
                .addComponent(btnGenerate)
                .addComponent(btnExp)
                .addComponent(btnGenAndExp)
                .addComponent(txtExpScale, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                .addComponent(spiGenExpCount, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))))
          .addContainerGap())
    );
    panTop.setLayout(gl_panTop);
    frame.getContentPane().setLayout(groupLayout);
  }
}