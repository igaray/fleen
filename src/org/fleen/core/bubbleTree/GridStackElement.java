package org.fleen.core.bubbleTree;

/*
 * The grid stack is a series of GridStackElements in the bubbletree
 * The first GSE is a root grid specification, thereafter 0..n GSE grid transforms
 * This interface has just the one method : getGrid()
 * We get the summed parameters from the root, through whatever transforms, to this 
 * element, and use them to construct a Grid. 
 */
public interface GridStackElement{
  
  Grid getGrid();
  
}
