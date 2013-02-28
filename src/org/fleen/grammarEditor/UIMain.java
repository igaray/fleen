package org.fleen.grammarEditor;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.fleen.grammarEditor.grammarProject.C_GPGrammarCreate;
import org.fleen.grammarEditor.grammarProject.C_GPGrammarImport;
import org.fleen.grammarEditor.grammarProject.C_GPGrammarExport;

@SuppressWarnings("serial")
public class UIMain extends JFrame{
  
  private JPanel contentPane;

  /**
   * Launch the application.
   */
  public static void main(String[] args){
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        try{
          UIMain frame=new UIMain();
          frame.setVisible(true);
        }catch(Exception e){
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public UIMain(){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100,100,450,300);
    
    JMenuBar menuBar = new JMenuBar();
    
    setJMenuBar(menuBar);
    
    JMenu mnGrammar = new JMenu("Grammar");
    menuBar.add(mnGrammar);
    
    JMenuItem mntmImportGrammar = new JMenuItem("Import");
    mntmImportGrammar.addActionListener(actionlistener);
    mnGrammar.add(mntmImportGrammar);
    
    JMenuItem mntmExportGrammar = new JMenuItem("Export");
    mntmExportGrammar.addActionListener(actionlistener);
    mnGrammar.add(mntmExportGrammar);
    
    JMenuItem mntmCreateGrammar = new JMenuItem("Create");
    mntmCreateGrammar.addActionListener(actionlistener);
    mnGrammar.add(mntmCreateGrammar);
    
    JMenu mnBubblemodel = new JMenu("BubbleModel");
    menuBar.add(mnBubblemodel);
    
    JMenu mnJig = new JMenu("Jig");
    menuBar.add(mnJig);
    contentPane=new JPanel();
    contentPane.setBorder(new EmptyBorder(5,5,5,5));
    setContentPane(contentPane);
    contentPane.setLayout(new CardLayout(0, 0));
    
    contentPane.add(GE.overview, "overview");
    contentPane.add(GE.bubblemodeleditor, "bubblemodeleditor");
    contentPane.add(GE.jigeditor, "jigeditor");
  }
  
  /*
   * ################################
   * ACTION LISTENER FOR MENUS
   * ################################
   */
  
  private static ActionListener actionlistener=new ActionListener(){

    public void actionPerformed(ActionEvent e){
      String a=e.getActionCommand();
      if(a.equals("Create")){
        GE.command(new C_GPGrammarCreate());
      }else if(a.equals("Import")){
        GE.command(new C_GPGrammarImport());
      }else if(a.equals("Export")){
        GE.command(new C_GPGrammarExport());
      }}};
  
  /*
   * ################################
   * SHOW
   * ################################
   */
  
  public void showOverview(){
    Container c=getContentPane();
    CardLayout a=(CardLayout)c.getLayout();
    a.show(c,"overview");}
  
  public void showBubbleModelEditor(){
    Container c=getContentPane();
    CardLayout a=(CardLayout)c.getLayout();
    a.show(c,"bubblemodeleditor");}
  
  public void showJigEditor(){
    Container c=getContentPane();
    CardLayout a=(CardLayout)c.getLayout();
    a.show(c,"jigeditor");}
  
}
