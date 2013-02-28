package org.fleen.grammarEditor.jigEditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import org.fleen.core.diamondGrammar.Jig;
import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.grammarProject.C_GPCreateJigBubbleDef;
import org.fleen.grammarEditor.grammarProject.C_GPDestroyJigBubbleDef;
import org.fleen.grammarEditor.grammarProject.C_GPJigBubbleFlipTwist;
import org.fleen.grammarEditor.grammarProject.C_GPJigBubbleSetChorusIndex;
import org.fleen.grammarEditor.grammarProject.C_GPJigBubbleSetFoamIndex;
import org.fleen.grammarEditor.grammarProject.C_GPJigSetGridDensity;
import org.fleen.grammarEditor.grammarProject.C_GPJigSetType;
import org.fleen.grammarEditor.overview.C_OverviewShow;

@SuppressWarnings("serial")
public class JigEditor extends JPanel{

  JigEditorCanvas panjigcanvas;
  JTextField txtJigID;
  JLabel lblBubbleModelID;
  JLabel lblGridScaleValue;
  JigEditorBubbleDefList lstBubbles;
  JLabel lblV0Val;
  JLabel lblV1Val;
  JLabel lblTwistVal;
  JLabel lblFoamIndexVal;
  JLabel lblChorusIndexVal;
  JigEditorBubbleModelList lstbubblemodels;
  JComboBox comboType;
  
  /**
   * Create the panel.
   */
  public JigEditor(){
    
    panjigcanvas = new JigEditorCanvas();
    panjigcanvas.setBackground(Color.BLUE);
    
    JPanel panDetails = new JPanel();
    panDetails.setBackground(new Color(211, 211, 211));
    GroupLayout groupLayout = new GroupLayout(this);
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
          .addComponent(panjigcanvas, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panDetails, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE))
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
          .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
            .addComponent(panDetails, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 299, Short.MAX_VALUE)
            .addComponent(panjigcanvas, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
          .addGap(1))
    );
    
    lblBubbleModelID = new JLabel("bubblemodelnull");
    lblBubbleModelID.setForeground(new Color(165, 42, 42));
    lblBubbleModelID.setBackground(new Color(255, 255, 255));
    lblBubbleModelID.setOpaque(true);
    lblBubbleModelID.setFont(new Font("Dialog", Font.BOLD, 16));
    
    txtJigID = new JTextField();
    txtJigID.setForeground(new Color(30, 144, 255));
    txtJigID.setBackground(Color.WHITE);
    txtJigID.setFont(new Font("Dialog", Font.BOLD, 16));
    txtJigID.setText("jignull");
    txtJigID.setColumns(10);
    txtJigID.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e){
        GE.grammarproject.focusbubblemodel.focusjig.id=txtJigID.getText();}});
    
    JLabel lblBubbleDefs = new JLabel("BUBBLEDEFS");
    lblBubbleDefs.setBackground(new Color(152, 251, 152));
    lblBubbleDefs.setForeground(new Color(34, 139, 34));
    lblBubbleDefs.setHorizontalAlignment(SwingConstants.CENTER);
    lblBubbleDefs.setFont(new Font("Dialog", Font.BOLD, 18));
    lblBubbleDefs.setOpaque(true);
    
    JButton btnCreateBubble = new JButton("Create");
    btnCreateBubble.setFocusable(false);
    btnCreateBubble.setFont(new Font("Dialog", Font.BOLD, 14));
    btnCreateBubble.setForeground(new Color(255, 140, 0));
    btnCreateBubble.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e){
        GE.command(new C_GPCreateJigBubbleDef());
        GE.command(new C_JigEditorRefreshBubbleDetails());}});
    
    JButton btnDestroyBubble = new JButton("Destroy");
    btnDestroyBubble.setFocusable(false);
    btnDestroyBubble.setForeground(new Color(199, 21, 133));
    btnDestroyBubble.setFont(new Font("Dialog", Font.BOLD, 14));
    btnDestroyBubble.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e){
        GE.command(new C_GPDestroyJigBubbleDef());
        GE.command(new C_JigEditorRefreshBubbleDetails());}});
    
    JScrollPane scrollPane = new JScrollPane();
    
    JScrollPane scrollPane_1 = new JScrollPane();
    
    JButton btnGridScaleIncrease = new JButton("+");
    btnGridScaleIncrease.setFocusable(false);
    btnGridScaleIncrease.setMargin(new Insets(0, 0, 0, 0));
    btnGridScaleIncrease.setForeground(new Color(46, 139, 87));
    btnGridScaleIncrease.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    btnGridScaleIncrease.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        GE.command(
          new C_GPJigSetGridDensity(),
          GE.grammarproject.focusbubblemodel.focusjig.getGridDensity()+1);
        GE.command(new C_JigEditorRefreshMainDetails());
        GE.command(new C_JigEditorRefreshCanvas());}});
    
    JButton btnGridScaleDecrease = new JButton("-");
    btnGridScaleDecrease.setFocusable(false);
    btnGridScaleDecrease.setMargin(new Insets(0, 0, 0, 0));
    btnGridScaleDecrease.setForeground(new Color(178, 34, 34));
    btnGridScaleDecrease.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    btnGridScaleDecrease.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        GE.command(
          new C_GPJigSetGridDensity(),
          GE.grammarproject.focusbubblemodel.focusjig.getGridDensity()-1);
        GE.command(new C_JigEditorRefreshMainDetails());
        GE.command(new C_JigEditorRefreshCanvas());}});
    
    JLabel lblGridScale = new JLabel("GRID DENSITY");
    lblGridScale.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    
    lblGridScaleValue = new JLabel("null");
    lblGridScaleValue.setOpaque(true);
    lblGridScaleValue.setBackground(new Color(255, 255, 0));
    lblGridScaleValue.setForeground(new Color(0, 100, 0));
    lblGridScaleValue.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
    
    JPanel panel = new JPanel();
    panel.setBackground(Color.LIGHT_GRAY);
    
    JButton btnOverview = new JButton("overview");
    btnOverview.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e){
        GE.command(new C_OverviewShow());}});
    
    comboType = new JComboBox();
    comboType.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String a = (String)cb.getSelectedItem();
        int newtype=Jig.TYPE_SPLITTER;
        if(a=="Boiler")
          newtype=Jig.TYPE_BOILER;
        GE.command(new C_GPJigSetType(),newtype);
        GE.command(new C_JigEditorRefreshMainDetails());}});
    
    comboType.setModel(new DefaultComboBoxModel(new String[] {"Splitter", "Boiler"}));
    
    GroupLayout gl_panDetails = new GroupLayout(panDetails);
    gl_panDetails.setHorizontalGroup(
      gl_panDetails.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panDetails.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panDetails.createParallelGroup(Alignment.LEADING)
            .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
            .addComponent(txtJigID, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
            .addComponent(lblBubbleModelID, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
            .addComponent(lblBubbleDefs, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
            .addGroup(gl_panDetails.createSequentialGroup()
              .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addGroup(gl_panDetails.createParallelGroup(Alignment.TRAILING, false)
                .addGroup(gl_panDetails.createSequentialGroup()
                  .addComponent(btnCreateBubble, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(btnDestroyBubble)
                  .addGap(5))
                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)))
            .addGroup(gl_panDetails.createSequentialGroup()
              .addGroup(gl_panDetails.createParallelGroup(Alignment.TRAILING, false)
                .addComponent(comboType, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblGridScale, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
              .addPreferredGap(ComponentPlacement.RELATED)
              .addGroup(gl_panDetails.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panDetails.createSequentialGroup()
                  .addComponent(btnGridScaleIncrease, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                  .addGap(9)
                  .addComponent(btnGridScaleDecrease, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(lblGridScaleValue, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                .addGroup(Alignment.TRAILING, gl_panDetails.createSequentialGroup()
                  .addComponent(btnOverview)
                  .addGap(14)))))
          .addContainerGap())
    );
    gl_panDetails.setVerticalGroup(
      gl_panDetails.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panDetails.createSequentialGroup()
          .addContainerGap()
          .addComponent(lblBubbleModelID)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(txtJigID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panDetails.createParallelGroup(Alignment.TRAILING)
            .addComponent(lblGridScale)
            .addGroup(gl_panDetails.createParallelGroup(Alignment.LEADING)
              .addGroup(gl_panDetails.createParallelGroup(Alignment.BASELINE)
                .addComponent(btnGridScaleDecrease, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblGridScaleValue, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
              .addComponent(btnGridScaleIncrease, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panDetails.createParallelGroup(Alignment.TRAILING)
            .addComponent(comboType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(btnOverview))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(lblBubbleDefs)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panDetails.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_panDetails.createSequentialGroup()
              .addGroup(gl_panDetails.createParallelGroup(Alignment.BASELINE)
                .addComponent(btnCreateBubble)
                .addComponent(btnDestroyBubble, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(panel, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
          .addContainerGap())
    );
    
    JLabel lblV0Title = new JLabel("V0");
    lblV0Title.setForeground(Color.BLUE);
    lblV0Title.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    
    JLabel lblV1Title = new JLabel("V1");
    lblV1Title.setForeground(Color.BLUE);
    lblV1Title.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    
    lblV0Val = new JLabel("[0,1,2,3]");
    lblV0Val.setBackground(Color.WHITE);
    lblV0Val.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 18));
    
    lblV1Val = new JLabel("[0,1,2,3]");
    lblV1Val.setBackground(Color.WHITE);
    lblV1Val.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 18));
    
    JLabel lbl0 = new JLabel("Twist");
    lbl0.setForeground(new Color(148, 0, 211));
    lbl0.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    
    lblTwistVal = new JLabel("POS");
    //mouse click to flip twist value
    lblTwistVal.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        GE.command(new C_GPJigBubbleFlipTwist());
        GE.command(new C_JigEditorRefreshBubbleDetails());}});
    lblTwistVal.setBackground(new Color(255, 255, 255));
    lblTwistVal.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 18));
    lblTwistVal.setForeground(new Color(220, 20, 60));
    
    JLabel lblFi = new JLabel("Fi");
    lblFi.setForeground(new Color(60, 179, 113));
    lblFi.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    
    JButton btnFoamIndexIncrease = new JButton("+");
    btnFoamIndexIncrease.setMargin(new Insets(0, 0, 0, 0));
    btnFoamIndexIncrease.setForeground(new Color(46, 139, 87));
    btnFoamIndexIncrease.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    btnFoamIndexIncrease.setFocusable(false);
    btnFoamIndexIncrease.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        GE.command(
          new C_GPJigBubbleSetFoamIndex(),
          GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().foamindex+1);
        GE.command(new C_JigEditorRefreshBubbleDetails());}});
    
    JButton btnFoamIndexDecrease = new JButton("-");
    btnFoamIndexDecrease.setMargin(new Insets(0, 0, 0, 0));
    btnFoamIndexDecrease.setForeground(new Color(178, 34, 34));
    btnFoamIndexDecrease.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    btnFoamIndexDecrease.setFocusable(false);
    btnFoamIndexDecrease.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        GE.command(
          new C_GPJigBubbleSetFoamIndex(),
          GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().foamindex-1);
        GE.command(new C_JigEditorRefreshBubbleDetails());}});
    
    lblFoamIndexVal = new JLabel("null");
    lblFoamIndexVal.setOpaque(true);
    lblFoamIndexVal.setForeground(new Color(0, 100, 0));
    lblFoamIndexVal.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
    lblFoamIndexVal.setBackground(Color.YELLOW);
    
    JLabel lblCi = new JLabel("Ci");
    lblCi.setForeground(new Color(0, 128, 0));
    lblCi.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    
    JButton btnChorusIndexIncrease = new JButton("+");
    btnChorusIndexIncrease.setMargin(new Insets(0, 0, 0, 0));
    btnChorusIndexIncrease.setForeground(new Color(46, 139, 87));
    btnChorusIndexIncrease.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    btnChorusIndexIncrease.setFocusable(false);
    btnChorusIndexIncrease.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        GE.command(
          new C_GPJigBubbleSetChorusIndex(),
          GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().chorusindex+1);
        GE.command(new C_JigEditorRefreshBubbleDetails());}});
    
    JButton btnChorusIndexDecrease = new JButton("-");
    btnChorusIndexDecrease.setMargin(new Insets(0, 0, 0, 0));
    btnChorusIndexDecrease.setForeground(new Color(178, 34, 34));
    btnChorusIndexDecrease.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
    btnChorusIndexDecrease.setFocusable(false);
    btnChorusIndexDecrease.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        GE.command(
          new C_GPJigBubbleSetChorusIndex(),
          GE.grammarproject.focusbubblemodel.focusjig.getFocusBubbleDef().chorusindex-1);
        GE.command(new C_JigEditorRefreshBubbleDetails());}});
    
    lblChorusIndexVal = new JLabel("null");
    lblChorusIndexVal.setOpaque(true);
    lblChorusIndexVal.setForeground(new Color(0, 100, 0));
    lblChorusIndexVal.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
    lblChorusIndexVal.setBackground(Color.YELLOW);
    GroupLayout gl_panel = new GroupLayout(panel);
    gl_panel.setHorizontalGroup(
      gl_panel.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panel.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel.createSequentialGroup()
              .addComponent(lblV0Title)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(lblV0Val, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
            .addGroup(gl_panel.createSequentialGroup()
              .addComponent(lblV1Title, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(lblV1Val, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
            .addGroup(gl_panel.createSequentialGroup()
              .addComponent(lbl0)
              .addPreferredGap(ComponentPlacement.UNRELATED)
              .addComponent(lblTwistVal))
            .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
              .addGroup(gl_panel.createSequentialGroup()
                .addComponent(lblCi, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(2)
                .addComponent(btnChorusIndexIncrease, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(btnChorusIndexDecrease, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(lblChorusIndexVal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
              .addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
                .addComponent(lblFi, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(3)
                .addComponent(btnFoamIndexIncrease, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                .addGap(2)
                .addComponent(btnFoamIndexDecrease, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(lblFoamIndexVal, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))))
          .addContainerGap())
    );
    gl_panel.setVerticalGroup(
      gl_panel.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panel.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
            .addComponent(lblV0Title)
            .addComponent(lblV0Val))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
            .addComponent(lblV1Title, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
            .addComponent(lblV1Val, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
            .addComponent(lbl0)
            .addComponent(lblTwistVal))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
            .addComponent(lblFi, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
            .addComponent(btnFoamIndexIncrease, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
            .addComponent(btnFoamIndexDecrease, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
            .addComponent(lblFoamIndexVal, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
            .addComponent(lblCi)
            .addComponent(btnChorusIndexIncrease, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
            .addComponent(btnChorusIndexDecrease, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
            .addComponent(lblChorusIndexVal, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)))
    );
    panel.setLayout(gl_panel);
    
    lstBubbles = new JigEditorBubbleDefList();
    lstBubbles.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 16));
    lstBubbles.setVisibleRowCount(0);
    scrollPane.setViewportView(lstBubbles);
    
    JLabel lblBubbleIndex = new JLabel("Bubbles");
    scrollPane.setColumnHeaderView(lblBubbleIndex);
    
    lstbubblemodels = new JigEditorBubbleModelList();
    scrollPane_1.setViewportView(lstbubblemodels);
    
    JLabel lblBubbleModels = new JLabel("BubbleModels");
    scrollPane_1.setColumnHeaderView(lblBubbleModels);
    panDetails.setLayout(gl_panDetails);
    setLayout(groupLayout);

  }
}
