/* Class RedBlackNode
 * This class represent one node in a RB Tree
 * We used generic type and classes to make the implement simple and compatible
 */
class RedBlackNode<T extends Comparable<T>> 
{
    // GLOBAL VARIABLES
    public static final int BLACK = 0;      // Possible color for this node
    public static final int RED = 1;        // Possible color for this node
    public T key;                           // The key of each node
    public int color;                       // The color of the node
    
    protected RedBlackNode<T> parent;       // The parent node
    protected RedBlackNode<T> left;         // The left child
    protected RedBlackNode<T> right;        // The right child
    
    protected int numLeft = 0;              // The number of elements to the left of each node
    protected int numRight = 0;             // The number of elements to the right of each node

    // Default Constructor
    public RedBlackNode()
    {
        color = BLACK;
        numLeft = 0;
        numRight = 0;
        parent = null;
        left = null;
        right = null;
    }

    /* Constructor which sets key to the argument.
     * @param: the key to insert to the node
     */
    RedBlackNode(T key)
    {
        this();
        this.key = key;
    }
}