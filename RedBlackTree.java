import java.util.ArrayList;
import java.util.List;

/* Class RedBlack Tree
 * The implement of all the functions are based on the algorithem in the book of the course
 * The only change is the use of generic type instead of int type
 */
public class RedBlackTree<T extends Comparable<T>> 
{
    // Root initialized to nill.
    private RedBlackNode<T> nil = new RedBlackNode<T>();
    private RedBlackNode<T> root = nil;

    
    // Default Constructor
    public RedBlackTree() 
    {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
    }
    
    /* @param: node, the RedBlackNode we must check to see whether it's nil.
     * @return: return's true of node is nil and false otherwise
     */
    private boolean isNil(RedBlackNode node)
    {
        return node == nil;
    }
    
    /* @param: x, The node which the lefRotate is to be performed on.
     * @return: return's the size of the tree.
     * The function Return's the number of nodes including the root which the RedBlackTree rooted at root has.
     */
    public int size()
    {
        return root.numLeft + root.numRight + 1;
    }
    
    /* The Algorithem based on the one in page 234 in the course book.
     * @param: x, The node which the lefRotate is to be performed on.
     * The function Performs a left Rotate around x
     */
    private void leftRotate(RedBlackNode<T> x)
    {
        RedBlackNode<T> y = x.right;    // Set Y
        x.right = y.left;               // Turn y's left subtree int x's right subtre

        if (!isNil(y.left))             // Check for existence of y.left and make pointer changes
            y.left.parent = x;
            
        y.parent = x.parent;            // Link x's parent to y             

        if (isNil(x.parent))            // x's parent is null
            root = y;

        else if (x.parent.left == x)    // x is the left child of it's parent
            x.parent.left = y;

            else                        // x is the right child of it's parent
                x.parent.right = y;

        y.left = x;                     // Put x on y's left
        x.parent = y;
    }
 
    /* The Algorithem based on the one in page 187 in the study instructions book.
     * @param: x, The node which the rightRotate is to be performed on.
     * The function Performs a right Rotate around x
     */
    private void rightRotate(RedBlackNode<T> x)
    {
        RedBlackNode<T> y = x.left;         // Set Y
        x.left = y.right;                   // Turn y's right subtree int x's left subtre

        if (!isNil(y.right))                // Check for existence of y.right and make pointer changes
            y.right.parent = x;
            
        y.parent = x.parent;                // Link x's parent to y             

        if (isNil(x.parent))                // x's parent is null
            root = y;

        else if (x.parent.right == x)       // x is the right child of it's parent
            x.parent.right = y;

            else                            // x is the left child of it's parent
                x.parent.left = y;

        y.right = x;                        // Put x on y's right
        x.parent = y;
    }
    
    /* The Algorithem based on the one in page 217 in the course book.
     * because RBT is based on binary-search tree, the algorithem is working on RBT too.
     * @param: x, a RedBlackNode
     * @return: returns the current minimum value in the tree
     */
    public RedBlackNode<T> TreeMinimum(RedBlackNode<T> x)
    {
        while (!isNil(x.left))               // while there is a smaller key, keep going left
            x = x.left;
            
        return x;
    }

    /* The Algorithem based on the one in page 218 in the course book.
     * because RBT is based on binary-search-tree structure, the algorithem is working on RBT too.
     * @param: x, a RedBlackNode whose successor we must find.
     * @return: return's the node the with the next largest key from x.key
     */
    public RedBlackNode<T> TreeSuccessor(RedBlackNode<T> x)
    {
        // if x.right is not nil, call treeMinimum(x.right) and return it's value
        if (!isNil(x.right))
            return TreeMinimum(x.right);

        RedBlackNode<T> y = x.parent;

        // while x is it's parent's right child...
        while (!isNil(y) && x == y.right)
        {
            // Keep moving up in the tree
            x = y;
            y = y.parent;
        }
          
        return y;               // Return successor
    }
        
    /* The Algorithem based on the one in page 216 in the course book.
     * because RBT is based on binary-search tree, the algorithem is working on RBT too.
     * @param: k, the key whose node we want to search for.
     * @return: returns a node with the key, if not found, returns null.
     * The function searches for a node with key k and returns the first such node, if no such node is found returns null
     */
    public RedBlackNode<T> search(T k)
    {
        // Initialize a pointer to the root to traverse the tree
        RedBlackNode<T> x = root;

        // While we haven't reached the end of the tree and haven't found a node with a key equal to key
        while (!isNil(x) && x.key.equals(k))
        {
            // go left or right based on value of current and key
            if (x.key.compareTo(k) < 0)
                x = x.right;

            else
                x = x.left;
        }

        return x;
    }
   
    /* @param: the key you want to insert to the tree. 
     * The function add new key to the tree.
     */
    public void insert(T key) 
    {
        insert(new RedBlackNode<T>(key));
    }
    
    /* The Algorithem based on the one in page 236 in the course book.
     * @param: z, the node to be inserted into the Tree rooted at root.
     * The function inserts z into the appropriate position in the RedBlackTree while updating numLeft and numRight values.
     */
    private void insert(RedBlackNode<T> z) 
    {
            // Create a reference to root & initialize a node to nil
            RedBlackNode<T> y = nil;
            RedBlackNode<T> x = root;

            while (!isNil(x))
            {
                y = x;

                if (z.key.compareTo(x.key) < 0)     // if z.key is < than the current key, go left
                {
                    x.numLeft++;                    // Update x.numLeft as z is < than x
                    x = x.left;
                }

                else                                // else, if z.key >= x.key so go right
                {
                    x.numRight++;                   // Update x.numRight as z is => x
                    x = x.right;
                }
            }
            
            // y will hold z's parent
            z.parent = y;        

            // Depending on the value of y.key, put z as the left or right child of y
            if (isNil(y))
                root = z;
                
            else if (z.key.compareTo(y.key) < 0)
                y.left = z;
                
                else
                    y.right = z;

            // Initialize z's children to nil and z's color to red
            z.left = nil;
            z.right = nil;
            z.color = RedBlackNode.RED;

            // Call insertFixup(z)
            insertFixup(z);
    }

    /* The Algorithem based on the one in page 236 in the course book.
     * @param: z, the node which was inserted and may have caused a violation of the RedBlackTree properties.
     * The function fixes up the violation of the RedBlackTree properties that may have been caused during insert(z)
     */
    private void insertFixup(RedBlackNode<T> z)
    {
        RedBlackNode<T> y = nil;
        
        // While there is a violation of the RedBlackTree properties..
        while (z.parent.color == RedBlackNode.RED)
        {             
            if (z.parent == z.parent.parent.left)               // If z's parent is the the left child of it's parent.
            {                
                y = z.parent.parent.right;                      // Initialize y to z 's cousin

                // Case 1: if y is red...recolor
                if (y.color == RedBlackNode.RED)             
                {
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }
                
                // Case 2: if y is black & z is a right child
                else if (z == z.parent.right)
                {
                    // leftRotate around z's parent
                    z = z.parent;
                    leftRotate(z);
                }

                // Case 3: else y is black & z is a left child
                else
                {
                    // Recolor and rotate round z's grandpa
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    rightRotate(z.parent.parent);
                }
            }

            // If z's parent is the right child of it's parent.
            else
            {
                y = z.parent.parent.left;                       // Initialize y to z's cousin

                // Case 1: if y is red...recolor
                if (y.color == RedBlackNode.RED)
                {
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }

                // Case 2: if y is black and z is a left child
                else if (z == z.parent.left)
                {
                    // rightRotate around z's parent
                    z = z.parent;
                    rightRotate(z);
                }
                
                // Case 3: if y  is black and z is a right child
                else
                {
                    // recolor and rotate around z's grandpa
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        
        // Color root black at all times
        root.color = RedBlackNode.BLACK;
    }
      
    /* The Algorithem based on the one in page 242 in the course book.
     * @param: v, the key value which we want to remove.
     * The function remove's z from the RedBlackTree rooted at root.
     * The function uses search to locate the node in the tree that consist the value we want to erase.
     */
    public void remove(RedBlackNode<T> v)
    {
        // Z is the node we want to remove
        RedBlackNode<T> z = search(v.key);
        
        // The value we are looking to remove is not in the tree
        if (isNil(z))
            return;

        // Declare variables
        RedBlackNode<T> x = nil;
        RedBlackNode<T> y = nil;

        // If either one of z's children is nil, then we must remove z
        if (isNil(z.left) || isNil(z.right))
            y = z;

        // else we must remove the successor of z
        else y = treeSuccessor(z);

        // Let x be the left or right child of y (y can only have one child)
        if (!isNil(y.left))
            x = y.left;
            
        else
            x = y.right;

        // link x's parent to y's parent
        x.parent = y.parent;

        // If y's parent is nil, then x is the root
        if (isNil(y.parent))
            root = x;

        // else if y is a left child, set x to be y's left sibling
        else if (y.parent.left == y)
            y.parent.left = x;

        // else if y is a right child, set x to be y's right sibling
        else if (y.parent.right == y)
            y.parent.right = x;

        // if y != z, trasfer y's satellite data into z
        if (y != z)
            z.key = y.key;

        // Update the numLeft and numRight numbers which might need updating due to the deletion of z.key.
        fixNodeData(x,y);

        // If y's color is black, it is a violation of the RedBlackTree properties so call removeFixup()
        if (y.color == RedBlackNode.BLACK)
            removeFixup(x);
    }

    /* The Algorithem based on the one in page 243 in the course book.
     * @param: x, the child of the deleted node from remove(RedBlackNode v).
     * The function restores the Red Black properties that may have been violated during the removal of a node in remove(RedBlackNode v)
     */
    private void removeFixup(RedBlackNode<T> x)
    {
        RedBlackNode<T> w;

        // While we haven't fixed the tree completely...
        while (x != root && x.color == RedBlackNode.BLACK)
        {      
            if (x == x.parent.left)           // if x is it's parent's left child
            {
                // set w to x's sibling
                w = x.parent.right; 

                // Case 1: w's color is red.
                if (w.color == RedBlackNode.RED)
                {
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }

                // Case 2: both of w's children are black
                if (w.left.color == RedBlackNode.BLACK && w.right.color == RedBlackNode.BLACK)
                {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }
                
                // Case 3 and Case 4
                else
                {
                    
                    // Case 3: w's right child is black
                    if (w.right.color == RedBlackNode.BLACK)
                    {
                        w.left.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    
                    // Case 4: w = black, w.right = red
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.right.color = RedBlackNode.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
              
            else                        // if x is it's parent's right child
            {
                // set w to x's sibling
                w = x.parent.left;

                // Case 1: w's color is red
                if (w.color == RedBlackNode.RED)
                {
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }

                // Case 2: both of w's children are black
                if (w.right.color == RedBlackNode.BLACK && w.left.color == RedBlackNode.BLACK)
                {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }

                // Case 3 and Case 4
                else
                {
                    
                    // Case 3: w's left child is black
                     if (w.left.color == RedBlackNode.BLACK)
                     {
                        w.right.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    // Case 4: w = black, and w.left = red
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.left.color = RedBlackNode.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }

        // set x to black to ensure there is no violation of RedBlack tree Properties
        x.color = RedBlackNode.BLACK;
    }

    /* The Algorithem isn't base on the course book, and there is file that contains the verity of it.
     * @param: y, the RedBlackNode which was actually deleted from the tree
     * @param: key, the value of the key that used to be in y
     * The function Update the numLeft and numRight numbers which might need updating due to the deletion of z.key in remove(RedBlackNode v)
     */
    private void fixNodeData(RedBlackNode<T> x, RedBlackNode<T> y)
    {
        // Initialize two variables which will help us traverse the tree
        RedBlackNode<T> current = nil;
        RedBlackNode<T> track = nil;


        // if x is nil, then we will start updating at y.parent
        // Set track to y, y.parent's child
        if (isNil(x))
        {
            current = y.parent;
            track = y;
        }

        // if x is not nil, then we start updating at x.parent
        // Set track to x, x.parent's child
        else
        {
            current = x.parent;
            track = x;
        }

        // while we haven't reached the root
        while (!isNil(current))
        {
            // if the node we deleted has a different key than
            // the current node
            if (y.key != current.key) 
            {
                // if the node we deleted is greater than
                // current.key then decrement current.numRight
                if (y.key.compareTo(current.key) > 0)
                    current.numRight--;

                // if the node we deleted is less than
                // current.key thendecrement current.numLeft
                if (y.key.compareTo(current.key) < 0)
                    current.numLeft--;
            }

            // if the node we deleted has the same key as the
            // current node we are checking
            else
            {
                // the cases where the current node has any nil
                // children and update appropriately
                
                if (isNil(current.left))
                    current.numLeft--;
                    
                else if (isNil(current.right))
                    current.numRight--;

                // the cases where current has two children and
                // we must determine whether track is it's left
                // or right child and update appropriately
                else if (track == current.right)
                    current.numRight--;
                    
                else if (track == current.left)
                    current.numLeft--;
            }

            // update track and current
            track = current;
            current = current.parent;
        }
    }

    /* The Algorithem isn't base on the course book, and there is file that contains the verity of it.
     * @param: key, any Comparable object.
     * @return: return's teh number of elements smaller than key.
     */
    public int numSmaller(T key)
    {
        // Call findNumSmaller(root,key) which will return the number of nodes whose key is greater than key
        return findNumSmaller(root,key);
    }

    /* The Algorithem isn't base on the course book, and there is file that contains the verity of it.
     * @param: node, the root of the tree, the key who we must compare other node key's to.
     * @return: the number of nodes smaller than key.
     */
    public int findNumSmaller(RedBlackNode<T> node, T key)
    {
        // Base Case: if node is nil, return 0
        if (isNil(node)) 
            return 0;

        // If key is less than node.key, look to the left as all
        // elements on the right of node are greater than key
        else if (key.compareTo(node.key) <= 0)
            return findNumSmaller(node.left,key);

        // If key is larger than node.key, all elements to the left of
        // node are smaller than key, add this to our total and look
        // to the right.
            else
                return 1+ node.numLeft + findNumSmaller(node.right,key);
    }

    /* The Algorithem isn't base on the course book, and there is file that contains the verity of it.
     * Returns sorted list of keys smaller than key.  Size of list will not exceed minReturned
     * @param: Key to search for.
     * @param: minReturned minimum number of results to return.
     * @return: List of keys smaller than key.  List may not exceed minReturned.
     */
    public List<T> getSmallerThan(T key, Integer maxReturned) {
        List<T> list = new ArrayList<T>();
        getSmallerThan(root, key, list);
        return list.subList(0, Math.min(maxReturned, list.size()));
    }

    private void getSmallerThan(RedBlackNode<T> node, T key, List<T> list) 
    {
        if (isNil(node)) 
            return;
            
            else if (node.key.compareTo(key) < 0) 
            {
                getSmallerThan(node.left, key, list);
                list.add(node.key);
                getSmallerThan(node.right, key, list);
            }
            
            else 
                getSmallerThan(node.right, key, list);
    }
}
