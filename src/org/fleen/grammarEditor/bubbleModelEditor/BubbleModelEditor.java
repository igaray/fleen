package org.fleen.grammarEditor.bubbleModelEditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.fleen.grammarEditor.GE;
import org.fleen.grammarEditor.grammarProject.C_GPCleanBubbleModelGeometry;
import org.fleen.grammarEditor.overview.C_OverviewShow;

@SuppressWarnings("serial")
public class BubbleModelEditor extends JPanel{

  public BubbleModelEditorCanvas bubblemodeleditorcanvas;
  JTextArea txtID;
  JTextArea txtVectors;
  
  /**
   * Create the panel.
   */
  public BubbleModelEditor(){
    
    bubblemodeleditorcanvas = new BubbleModelEditorCanvas();
    bubblemodeleditorcanvas.setBackground(Color.MAGENTA);
    
    JPanel panDetails = new JPanel();
    panDetails.setBackground(Color.LIGHT_GRAY);
    GroupLayout groupLayout = new GroupLayout(this);
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addComponent(bubblemodeleditorcanvas, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panDetails, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE))
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
          .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
            .addComponent(panDetails, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
            .addComponent(bubblemodeleditorcanvas, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
          .addGap(1))
    );
    
    JScrollPane scrollPane = new JScrollPane();
    
    JScrollPane scrollPane_2 = new JScrollPane();
    scrollPane_2.setViewportBorder(null);
    
    JButton btnCleanGeometry = new JButton("CLEAN GEOMETRY");
    btnCleanGeometry.setFocusable(false);
    btnCleanGeometry.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e){
        GE.command(new C_GPCleanBubbleModelGeometry());}});
    
    btnCleanGeometry.setForeground(new Color(30, 144, 255));
    
    JButton btnOverview = new JButton("overview");
    btnOverview.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e){
        GE.command(new C_OverviewShow());}});
    
    GroupLayout gl_panDetails = new GroupLayout(panDetails);
    gl_panDetails.setHorizontalGroup(
      gl_panDetails.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panDetails.createSequentialGroup()
          .addContainerGap()
          .addGroup(gl_panDetails.createParallelGroup(Alignment.LEADING)
            .addComponent(scrollPane_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
            .addComponent(btnCleanGeometry, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
            .addComponent(btnOverview))
          .addContainerGap())
    );
    gl_panDetails.setVerticalGroup(
      gl_panDetails.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panDetails.createSequentialGroup()
          .addContainerGap()
          .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(btnCleanGeometry)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(btnOverview)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
          .addContainerGap())
    );
    
    txtVectors = new JTextArea();
    txtVectors.setFont(new Font("Dialog", Font.BOLD, 16));
    txtVectors.setBackground(Color.LIGHT_GRAY);
    txtVectors.setLineWrap(true);
    txtVectors.setForeground(new Color(0, 100, 0));
    txtVectors.setEditable(false);
    txtVectors.setText("nullvectors");
    scrollPane_2.setViewportView(txtVectors);
    
    txtID = new JTextArea();
    txtID.setForeground(new Color(0, 0, 255));
    txtID.setLineWrap(true);
    txtID.setText("nullid");
    scrollPane.setViewportView(txtID);
    
    JLabel lblId = new JLabel("ID");
    scrollPane.setColumnHeaderView(lblId);
    panDetails.setLayout(gl_panDetails);
    setLayout(groupLayout);
    
    txtID.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e){
        GE.grammarproject.focusbubblemodel.id=txtID.getText();}});

  }
}
