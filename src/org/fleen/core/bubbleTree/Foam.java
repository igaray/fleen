package org.fleen.core.bubbleTree;

/*
 * A Foam is 1..n contiguous masses of bubbles. These masses may be pierced.
 * parents surround children, and we can have multiple levels of nesting
 * a foam is a node in the bubbletree. It implements bubbletreeelement.
 * 
 * Every bubble has a foam. Is part of a foam. A Bubble's foam is the first nonnull Bubble.foam in it's ancestry.
 * 
 * A foam has 1..n Bubbles. Is composed of Bubbles. A Foam's Bubbles are all Bubbles in the branch/es rooted
 * at the Foam's root Bubble/s - up to the Bubbles with nonnull Foams, then a new Foam starts. This new Foam
 * is a child of this Foam. 
 * 
 * It's really simple. It's basically just an abstract node (+ tags maybe)
 */
public class Foam extends BubbleTreeNode_Abstract{
  
  private static final long serialVersionUID=-128342895299754440L;

}
