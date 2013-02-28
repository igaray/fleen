package org.fleen.samples.diamondCompositionInspector;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.fleen.samples.diamondCompositionInspector.faceView.FaceView;
import org.fleen.samples.diamondCompositionInspector.treeView.TreeView;

public class UI extends JFrame{

  private JPanel contentPane;
  public FaceView panel_faceview;
  public TreeView panel_treeview;

  /**
   * Launch the application.
   */
  public static void main(String[] args){
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        try{
          UI a=new UI();
          a.setTitle("@@@@@@@@@@@@ NONFUNCTIONING TEST @@@@@@@@@@@@@@@@@@@");
          
        }catch(Exception e){
          e.printStackTrace();
        }
      }
    });
  }

  
  public UI(){
    initialize();
    setVisible(true);
    setTitle("_FLEEN_CORE_TEST_");
    panel_faceview.setFocusable(true);
    panel_treeview.setFocusable(true);
  }
  
  /**
   * Create the frame.
   */
  void initialize(){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(MAXIMIZED_BOTH);
    //init panels
    contentPane=new JPanel();
    setContentPane(contentPane);
    panel_faceview=new FaceView();
    panel_treeview=new TreeView();
    //key listeners for everybody
    addKeyListener(UIKeyListener);
    contentPane.addKeyListener(UIKeyListener);
    panel_faceview.addKeyListener(UIKeyListener);
    panel_treeview.addKeyListener(UIKeyListener);
    //
    GroupLayout gl_contentPane = new GroupLayout(contentPane);
    gl_contentPane.setHorizontalGroup(
      gl_contentPane.createParallelGroup(Alignment.LEADING)
        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
          .addContainerGap()
          .addComponent(panel_treeview, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(panel_faceview, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
          .addGap(0))
    );
    gl_contentPane.setVerticalGroup(
      gl_contentPane.createParallelGroup(Alignment.LEADING)
        .addComponent(panel_faceview, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
        .addComponent(panel_treeview, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
    );
    contentPane.setLayout(gl_contentPane);}
  
  public FaceView getFaceview() {
    return panel_faceview;}
  
  public TreeView getTreeview() {
    return panel_treeview;}
  
  //START CLASS UIKEYLISTENER
  private KeyListener UIKeyListener=new KeyAdapter(){
    
    public void keyTyped(KeyEvent e) {
      char k=e.getKeyChar();
      if(k=='c'){
        TestDiamondCompositionInspector.create();
      }else{
        System.out.println("unbound char : "+k);
      }}};
  //END CLASS UIKEYLISTENER
      
}
