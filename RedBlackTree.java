import java.util.ArrayList;
import java.util.List;

/**
 * Class RedBlack Tree
 * The implementation of all the functions are based on the algorithem in the book of the course.
 * The only change is the use of generic type instead of int type.
 * 
 * @author: Ofir Sasson and Amit Reuveni
 * @version: 2017
 */
public class RedBlackTree<T extends Comparable<T>> 
{
    // Root initialized to nill.
    private RedBlackNode<T> nil = new RedBlackNode<T>();
    private RedBlackNode<T> root = nil;
    
    public int k;                   // The maximum size of the tree.
    public int currSize;            // The current size of the tree.

    // Default Constructor.
    public RedBlackTree(int k) 
    {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
        this.k = k;
        currSize = 0;
    }
    
    /** 
     * Function for checking if the node is nil.
     *
     * @param: node, The RedBlackNode we must check to see whether it's nil.
     * @return: Returns true if node is nil and false otherwise.
     */
    private boolean isNil(RedBlackNode node)
    {
        return node == nil;
    }
        
    /** 
     * The Algorithm is based on the one in page 234 in the course book.
     * The function Performs a left Rotate around x.
     *
     * @param: x, The node which the lefRotate is to be performed on.
     */
    private void leftRotate(RedBlackNode<T> x)
    {
        RedBlackNode<T> y = x.right;    // Set Y.
        x.right = y.left;               // Turn y's left subtree into x's right subtree.

        if (!isNil(y.left))             // Check for existence of y.left and make pointer changes.
            y.left.parent = x;
            
        y.parent = x.parent;            // Link x's parent to y.            

        if (isNil(x.parent))            // x's parent is nil.
            root = y;

        else if (x.parent.left == x)    // x is the left child of its parent.
            x.parent.left = y;

            else                        // x is the right child of its parent.
                x.parent.right = y;

        y.left = x;                     // Put x on y's left.
        x.parent = y;
    }
 
    /**
     * The Algorithm based on the one in page 187 in the study instructions book.
     * The function Performs a right Rotate around x.
     * 
     * @param: x, The node which the rightRotate is to be performed on.
     */
    private void rightRotate(RedBlackNode<T> x)
    {
        RedBlackNode<T> y = x.left;         // Set Y.
        x.left = y.right;                   // Turn y's right subtree into x's left subtree.

        if (!isNil(y.right))                // Check for existence of y.right and make pointer changes.
            y.right.parent = x;
            
        y.parent = x.parent;                // Link x's parent to y.         

        if (isNil(x.parent))                // x's parent is nil.
            root = y;

        else if (x.parent.right == x)       // x is the right child of its parent.
            x.parent.right = y;

            else                            // x is the left child of its parent.
                x.parent.left = y;

        y.right = x;                        // Put x on y's right.
        x.parent = y;
    }
    
    /**
     * The Algorithm is based on the one in page 217 in the course book.
     * because RBT is based on binary-search tree, the algorithm is viable for our RedBlackTree.
     *
     * @param: x, a RedBlackNode.
     * @return: Returns the current minimum value in the tree.
     */
    public RedBlackNode<T> TreeMinimum(RedBlackNode<T> x)
    {
        // While there is a smaller key, keep going left.
        while (!isNil(x.left))
            x = x.left;
            
        return x;
    }

    /**
     * The Algorithm is based on the one in page 217 in the course book.
     * because RBT is based on binary-search tree, the algorithem is viable for our RedBlackTree.
     *
     * @param: x, a RedBlackNode.
     * @return: Returns the current maximum value in the tree.
     */
    public RedBlackNode<T> TreeMaximum(RedBlackNode<T> x)
    {
        // While there is a smaller key, keep going left.
        while (!isNil(x.right)) 
            x = x.right;
            
        return x;
    }

    /**
     * The Algorithm is based on the one in page 218 in the course book.
     * because RBT is based on binary-search-tree structure, the algorithem is viable for our RedBlackTree.
     *
     * @param: x, a RedBlackNode whose successor we must find.
     * @return: Returns the node that has the next higher key in the Tree after x.
     */
    public RedBlackNode<T> TreeSuccessor(RedBlackNode<T> x)
    {
        // If x.right is not nil, call treeMinimum(x.right) and return its value.
        if (!isNil(x.right))
            return TreeMinimum(x.right);

        RedBlackNode<T> y = x.parent;

        // While x is its parents right child:
        while (!isNil(y) && x == y.right)
        {
            // Keep moving up in the tree.
            x = y;
            y = y.parent;
        }
        
        // Return successor.
        return y;         
    }
           
    /**
     * A function that calls the actuall function.
     *
     * @param: the key you want to insert to the tree. 
     */
    public void Insert(T key) 
    {        
        RedBlackNode<T> z = new RedBlackNode<T>(key);       // Create a new node for the tree.       
        RedBlackNode<T> max = root;                         // Create a new node for the maximum in the tree.
        
        // If the tree is full (current size = k).
        if (currSize == k)
        {
                // Search the maximum and put it's value in max.
                max = TreeMaximum(max);

                // If z is smaller then the maximum, we delete the maximum and proceed to insert z to the tree.
                if (z.key.compareTo(max.key) < 0)
                {
                    Remove(max);
                    Insert(z);  // Call the actual Insert function.
                }

                // Else, we don't insert z and end the program.
                else
                    return;
        }
        
        // If the tree is not full yet, insert the node to the tree.
        else
        {
            Insert(z);  // Call the actual Insert function.
            currSize++;
        }
    }
    
    /**
     * The Algorithm is based on the one in page 236 in the course book.
     * The function inserts the node z into the appropriate position in the RedBlackTree while updating numLeft and numRight values.
     *
     * @param: z, the node to be inserted into the Tree rooted at root.
     */
    private void Insert(RedBlackNode<T> z) 
    {
            // Create a reference to root & initialize a node to nil.
            RedBlackNode<T> y = nil;
            RedBlackNode<T> x = root;                       

            while (!isNil(x))
            {
                y = x;

                if (z.key.compareTo(x.key) < 0)     // If z.key is < than the current key, go left.
                {
                    x = x.left;
                }

                else                                // Else, if z.key >= x.key so go right.
                {               
                    x = x.right;
                }
            }
            
            // z's parent will hold y.
            z.parent = y;        

            // Depending on the value of y.key, put z as the left or right child of y.
            if (isNil(y))
                root = z;
                
            else if (z.key.compareTo(y.key) < 0)
                y.left = z;
                
                else
                    y.right = z;

            // Initialize z's children to nil and z's color to red.
            z.left = nil;
            z.right = nil;
            z.color = RedBlackNode.RED;

            // Call insertFixup(z).
            InsertFixup(z);
    }

    /**
     * The Algorithm is based on the one in page 236 in the course book.
     * The function fixes up the violation of the RedBlackTree properties that may have been caused during insert(z).
     *
     * @param: z, the node which was inserted and may have caused a violation of the RedBlackTree properties.
     */
    private void InsertFixup(RedBlackNode<T> z)
    {
        RedBlackNode<T> y = nil;
        
        // While there is a violation of the RedBlackTree properties..
        while (z.parent.color == RedBlackNode.RED)
        {             
            if (z.parent == z.parent.parent.left)               // If z's parent is the the left child of it's parent.
            {                
                y = z.parent.parent.right;                      // Initialize y to z 's cousin.

                // Case 1: if y is red...recolor.
                if (y.color == RedBlackNode.RED)             
                {
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }
                
                // Case 2: if y is black & z is a right child.
                else if (z == z.parent.right)
                {
                    // leftRotate around z's parent.
                    z = z.parent;
                    leftRotate(z);
                }

                // Case 3: else y is black & z is a left child.
                else
                {
                    // Recolor and rotate round z's grandpa.
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    rightRotate(z.parent.parent);
                }
            }

            // If z's parent is the right child of it's parent.
            else
            {
                y = z.parent.parent.left;                       // Initialize y to z's cousin.

                // Case 1: if y is red, recolor.
                if (y.color == RedBlackNode.RED)
                {
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }

                // Case 2: if y is black and z is a left child.
                else if (z == z.parent.left)
                {
                    // rightRotate around z's parent
                    z = z.parent;
                    rightRotate(z);
                }
                
                // Case 3: if y  is black and z is a right child.
                else
                {
                    // Recolor and rotate around z's grandpa.
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        
        // Color root black at all times.
        root.color = RedBlackNode.BLACK;
    }   
    
    /**
     * The Algorithm is based on the one in page 242 in the course book.
     * The function gets the maximum in the tree from the function insert, and delete the maximum.
     *
     * @param: z, the node we want to delete.
     * The function remove's z from the RedBlackTree rooted at root.
     */
    private void Remove(RedBlackNode<T> z)
    {
        // Declare variables.
        RedBlackNode<T> x = nil;
        RedBlackNode<T> y = nil;

        // If either one of z's children is nil, then we must remove z.
        if (isNil(z.left) || isNil(z.right))
            y = z;

        // Else we must remove the successor of z.
        else y = TreeSuccessor(z);

        // Let x be the left or right child of y (y can only have one child).
        if (!isNil(y.left))
            x = y.left;
            
        else
            x = y.right;

        // Link x's parent to y's parent.
        x.parent = y.parent;

        // If y's parent is nil, then x is the root.
        if (isNil(y.parent))
            root = x;

        // Else if y is a left child, set x to be y's left sibling.
        else if (y.parent.left == y)
            y.parent.left = x;

        // Else if y is a right child, set x to be y's right sibling.
        else if (y.parent.right == y)
            y.parent.right = x;

        // If y != z, trasfer y's satellite data into z.
        if (y != z)
            z.key = y.key;
             
        // If y's color is black, it is a violation of the RedBlackTree properties so call removeFixup().
        if (y.color == RedBlackNode.BLACK)
            RemoveFixup(x);
    }

    /**
     * The Algorithm is based on the one in page 243 in the course book.
     * The function restores the Red Black properties that may have been violated during the removal of a node in remove(RedBlackNode v).
     *
     * @param: x, the child of the deleted node from remove(RedBlackNode v).
     */
    private void RemoveFixup(RedBlackNode<T> x)
    {
        RedBlackNode<T> w;

        // While we haven't fixed the tree completely...
        while (x != root && x.color == RedBlackNode.BLACK)
        {      
            if (x == x.parent.left)           // If x is it's parent's left child.
            {
                // Set w to x's sibling.
                w = x.parent.right; 

                // Case 1: w's color is red.
                if (w.color == RedBlackNode.RED)
                {
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }

                // Case 2: both of w's children are black.
                if (w.left.color == RedBlackNode.BLACK && w.right.color == RedBlackNode.BLACK)
                {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }
                
                // Case 3 and Case 4.
                else
                {
                    
                    // Case 3: w's right child is black.
                    if (w.right.color == RedBlackNode.BLACK)
                    {
                        w.left.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    
                    // Case 4: w = black, w.right = red.
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.right.color = RedBlackNode.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
              
            else       // If x is it's parent's right child.
            {
                // Set w to x's sibling.
                w = x.parent.left;

                // Case 1: w's color is red.
                if (w.color == RedBlackNode.RED)
                {
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }

                // Case 2: both of w's children are black.
                if (w.right.color == RedBlackNode.BLACK && w.left.color == RedBlackNode.BLACK)
                {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }

                // Case 3 and Case 4.
                else
                {
                    
                    // Case 3: w's left child is black.
                     if (w.left.color == RedBlackNode.BLACK)
                     {
                        w.right.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    // Case 4: w = black, and w.left = red.
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.left.color = RedBlackNode.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }

        // Set x to black to ensure there is no violation of RedBlack tree Properties.
        x.color = RedBlackNode.BLACK;
    }    

    /** 
     * This is a function that calls the actual function.
     */
    public void printkMin()
    {   
       RedBlackNode<T> x = root;
       printkMin(x);
    }

    /** 
     * The Algorithm is based on the one in page 214 in the course book.
     * This function overloads the printkMin() function.
     * The Algorithm Prints the tree that contains the smallest k values.
     *
     * @param: x, the root of the tree that is to be printed.
     */
    private void printkMin(RedBlackNode<T> x)
    {   
       if (!isNil(x))
       {
            printkMin(x.left);
            System.out.print(x.key + " ");
            printkMin(x.right);
       }
    }
}
