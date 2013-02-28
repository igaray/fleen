package org.fleen.grammarEditor.overview;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.fleen.grammarEditor.C;

@SuppressWarnings("serial")
public class Overview extends JPanel{
  
  public OverviewGrid grid;
  
  public Overview(){
    
    JLabel lblStats=new JLabel("statsnull");
    grid=new OverviewGrid();
    JScrollPane scrollPane = new JScrollPane(grid);
    grid.setAutoscrolls(true);
    scrollPane.setBackground(C.OG_COLOR_BACKGROUND);
    
    GroupLayout groupLayout = new GroupLayout(this);
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup()
          .addGap(12)
          .addComponent(lblStats, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
          .addGap(12))
        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
          .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addComponent(lblStats, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
    );
    setLayout(groupLayout);
   
   

  }
}
