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
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.fleen.core.diamondGrammar.BubbleModel;
import org.fleen.core.diamondGrammar.Grammar;
import org.fleen.samples.fleenRasterCompositionGen.gre.GRECommandManager;
import org.fleen.samples.fleenRasterCompositionGen.renderer.Renderer_Abstract;

public class UI{

  public JFrame frame;
  public Viewer panViewer;
  public JTextField txtGrammar;
  public JComboBox cmbRootBubbleModel;
  public JTextField txtDetailSizeLimit;
  public JLabel lblTxtSymmetryControlFunction;
  public  JComboBox cmbRenderer;
  public JTextField txtExportImageScale;
  public  JTextField txtExportDir;
  public  JSpinner spiGenExpImageCount;
  public MessageTextPane txtMessage;
  
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
    frame.setBounds(100,100,506,504);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle(FRCG.TITLE+" "+FRCG.VERSION);
    //init layout
    JPanel panControl = new JPanel();
    panViewer = new Viewer();
    panViewer.setBackground(Color.YELLOW);
    GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addContainerGap()
          .addComponent(panControl, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panViewer, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
          .addGap(10))
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
          .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
            .addComponent(panControl, GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
            .addGroup(groupLayout.createSequentialGroup()
              .addContainerGap()
              .addComponent(panViewer, GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)))
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
    txtGrammar.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        try{
          Grammar g=FRCG.instance.params.getGrammar();
          FRCG.instance.params.setGrammar();
          if(g!=FRCG.instance.params.getGrammar()){
            FRCG.instance.params.initUIComponent_Grammar();
            FRCG.instance.params.invalidateRootBubbleModel();
            FRCG.instance.params.initUIComponent_RootBubbleModelsList();
            FRCG.instance.params.initUIComponent_RootBubbleModel();}
        }catch(Exception x){}}});
    
    //COMBOBOX ROOTBUBBLEMODEL
    cmbRootBubbleModel = new JComboBox();
    cmbRootBubbleModel.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    cmbRootBubbleModel.setToolTipText("Root Bubble Model");
    //selection changed listener
    //when we do it, update the params
    cmbRootBubbleModel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        FRCG.instance.params.setRootBubbleModel(
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
        FRCG.instance.params.setDetailSizeLimit(txtDetailSizeLimit.getText());
        txtDetailSizeLimit.setText(
          Double.toString(FRCG.instance.params.getDetailSizeLimit()));}});
    
    //LABEL SYMMETRYCONTROLFUNCTION
    //this is a temporary dummy. We'll do this with some kind of graphical curve-drawing gadget later
    //right now we just use a "function class" and do some kind of constant thing with no interaction here.
    lblTxtSymmetryControlFunction = new JLabel("symmetry control function null");
    lblTxtSymmetryControlFunction.setToolTipText("Symmetry Control Function");
    lblTxtSymmetryControlFunction.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    
    //COMBO RENDERER
    cmbRenderer = new JComboBox();
    cmbRenderer.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    cmbRenderer.setToolTipText("Renderer");
    //selection changed listener
    //when we do it, update the params
    cmbRenderer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        try{
          FRCG.instance.params.setRenderer(
            (Renderer_Abstract)cmbRenderer.getModel().getSelectedItem());
        }catch(Exception x){}}});
    
    //TEXTFIELD EXPORT IMAGE SCALE
    txtExportImageScale = new JTextField();
    txtExportImageScale.setToolTipText("Export Image Scale");
    txtExportImageScale.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    txtExportImageScale.setText("export image scale null");
    txtExportImageScale.setColumns(10);
    //focus lost listener
    //update and validate
    txtExportImageScale.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        FRCG.instance.params.setExportImageScale(txtExportImageScale.getText());
        txtExportImageScale.setText(
          Double.toString(FRCG.instance.params.getExportImageScale()));}});
    
    //TEXTFIELD EXPORT DIR
    txtExportDir = new JTextField();
    txtExportDir.setBackground(new Color(135, 206, 250));
    txtExportDir.setEditable(false);
    txtExportDir.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    txtExportDir.setToolTipText("Export Dir");
    txtExportDir.setText("export dir null");
    txtExportDir.setColumns(10);
    //mouse click listener
    //set export dir. 
    txtExportDir.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e){
        FRCG.instance.params.setExportDir();
        FRCG.instance.params.initUIComponent_ExportDir();}});
    
    //BUTTON GENERATE
    JButton btnGenerate = new JButton("Generate");
    btnGenerate.setForeground(new Color(0, 0, 0));
    btnGenerate.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
    btnGenerate.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        try{
          GRECommandManager.generate();
        }catch(Exception x){
          System.out.println("exception in generate");}}});
    
    //BUTTON EXPORT
    JButton btnExport = new JButton("Export");
    btnExport.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
    btnExport.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        try{
          GRECommandManager.renderForExport();
          GRECommandManager.export();
        }catch(Exception x){
          System.out.println("exception in export");}}});
    
    //BUTTON GENERATE AND EXPORT
    JButton btnGenExp = new JButton("Gen & Exp");
    btnGenExp.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
    btnGenExp.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        try{
          GRECommandManager.generateAndExport();
        }catch(Exception x){
          System.out.println("exception in generate and export");}}});
    
    //SPINNER GENEXP IMAGE COUNT
    spiGenExpImageCount = new JSpinner();
    spiGenExpImageCount.setModel(new SpinnerNumberModel(new Integer(3), new Integer(1), null, new Integer(1)));
    spiGenExpImageCount.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    spiGenExpImageCount.setToolTipText("Gen & Exp Image Count");
    //update and validate param on state change
    spiGenExpImageCount.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e){
        try{
          FRCG.instance.params.setGenExpImageCount(
          (Integer)spiGenExpImageCount.getModel().getValue());
        }catch(Exception x){}
        spiGenExpImageCount.getModel().setValue(
          FRCG.instance.params.getGenExpImageCount());}});
    
    //BUTTON ABOUT
    JButton btnInfo = new JButton("Info");
    btnInfo.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    btnInfo.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        try{
          FRCG.instance.postMessage(FRCG.ABOUT_TEXT);
        }catch(Exception x){
          System.out.println("exception in about");}}});
    
    //MESSAGE TEXT PANE AND SCROLLPANE
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    
    GroupLayout gl_panControl = new GroupLayout(panControl);
    gl_panControl.setHorizontalGroup(
      gl_panControl.createParallelGroup(Alignment.TRAILING)
        .addGroup(gl_panControl.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panControl.createParallelGroup(Alignment.TRAILING)
            .addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
            .addComponent(txtGrammar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
            .addComponent(cmbRenderer, 0, 228, Short.MAX_VALUE)
            .addComponent(txtExportImageScale, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
            .addComponent(txtDetailSizeLimit, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
            .addComponent(lblTxtSymmetryControlFunction, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
            .addComponent(cmbRootBubbleModel, 0, 228, Short.MAX_VALUE)
            .addComponent(txtExportDir, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
            .addComponent(btnGenerate, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
            .addComponent(btnExport, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
            .addGroup(gl_panControl.createSequentialGroup()
              .addComponent(btnGenExp, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(spiGenExpImageCount, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
            .addComponent(btnInfo, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
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
            .addComponent(btnGenExp)
            .addComponent(spiGenExpImageCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(btnInfo))
    );
    
    txtMessage = new MessageTextPane();
    txtMessage.setForeground(Color.WHITE);
    txtMessage.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
    txtMessage.setEditable(false);
    txtMessage.setBackground(Color.GRAY);
    scrollPane.setViewportView(txtMessage);
    panControl.setLayout(gl_panControl);
    frame.getContentPane().setLayout(groupLayout);
  }
}
