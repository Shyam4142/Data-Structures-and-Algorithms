// SplayTree Source Code
// Name: Shyam Venkatesan
/* Implements a SplayTree object with methods insertion, deletion, search, and preorderTraversal. 
   Uses the splay property to bring the last accessed node to the root.
*/

// Imports scanner object for taking in input in the main method
import java.util.Scanner;

// The SplayTree class
public class SplayTree
{
    // Instance variable that holds the SplayTree's root, initialized to null
    private Node root = null;
    
    // Nested class Node that is used to make individual Node objects
    private class Node
    {
        // Integer value stored in each node is key
        int key;
        // Node pointers to the left child, right child, and parent
        Node left, right, parent;
        // Node constructor takes in key as an input
        Node(int key)
        {
            // Sets key variable to input value
            this.key = key;
            // left, right, parent pointers all set to null
            left = right = parent = null;
        }
    }
    
    // The intsertion method takes in a key value and has no return
    public void insertion(int key)
    {
       // The new node to be inserted, created with Node constructor
       Node node = new Node(key);
       // If the root is null, it is set to node, a traversal occurs, and the method is exited
       if(root == null)
       {
           root = node;
           preorderTraversal();
           return;
       }
       // cur starts at root, prev starts with null
       Node cur = root;
       Node prev = null;
       // Loops as long as cur isn't null (gets to open slot)
       while(cur != null)
       {
           // Sets prev to cur
           prev = cur;
           // Moves cur left or right depending on value
           if(key < cur.key)
            cur = cur.left;
           else if(key > cur.key)
            cur = cur.right;
            // If a matching key value is already found, prints a message and returns
           else
            {
                System.out.println("Key " + key + " already used.");
                return;
            }
        }
        // If the key is less than the parent's key
        if(key < prev.key)
        {
            // Parent's left pointer set to node, new node's parent set to prev, and the new node is splayed
            prev.left = node;
            prev.left.parent = prev;
            splay(prev.left);
        }
        // Otherwise
        else
        {
            // Parent's right pointer set to node, new node's parent set to prev, and the new node is splayed
            prev.right = node;
            prev.right.parent = prev;
            splay(prev.right);
        }
        // Displays the result of the insertion
        preorderTraversal();
    }
    
    // The deletion method takes in a key value and has no return
    public void deletion(int key)
    {
        // If there is a null root, a message is printed and the method returns
        if(root == null)
        {
            System.out.println("Empty Tree");
            return;
        }
        // Informs that the result is from splaying before the deletion
        System.out.println("Splaying before deletion");
        // Stores result from search method for key in state, prints out the splayed tree from moving node to be deleted to the root
        boolean state = search(key);
        // If the key doesn't exist, a message is printed before returning
        if(state == false)
        {
            System.out.println("Key " + key + " not in tree");
            return;
        }
        // Case 1: only right child, root is given value of the right child, parent set to null
        if(root.left == null && root.right != null)
        {
            root = root.right;
            root.parent = null;
        }
        // Case 2: only left child, root is given value of the left child, parent set to null
        else if(root.right == null && root.left != null)
        {
            root = root.left;
            root.parent = null;
        }
        // Case 3: no children, root set to null
        else if(root.right == null && root.left == null)
        {
            root = null;
        }
        // Case 4: two children, finds inorder successor
        else if(root.right != null && root.right != null)
        {
            Node cur = root.right;
            Node prev = null;
            while(cur.left != null)
            {
                prev = cur;
                cur = cur.left;
            }
            // If the root's right child has no left children, the root is set to the right child, and the right pointer is set to null
            if(prev == null)
            {
                root.key = cur.key;
                root.right = null;
            }
            // Otherwise, the root is set to the inorder successor and the sucessor's parent's left pointer is set to null
            else
            {
                root.key = cur.key;
                prev.left = null;
            }
        }
        // Next traversal is after the deletion
        System.out.println("After deletion");
        // Calls preorderTraversal method
        preorderTraversal();
    }
    
    // The search method takes in a key parameter and returns a boolean (true = found, false = not found)
    public boolean search(int key)
    {
        // Sets cur to ther root
        Node cur = root;
        // Loops as long as there is a cur
        while(cur != null)
        {
            // If the key is less than the current key and there is a left node, moves left
            if(key < cur.key && cur.left != null)
                cur = cur.left;
            // If the key is greater than the current key and there is a right node, moves right
            else if(key > cur.key && cur.right != null)
                cur = cur.right;
            // If the key is found, the node is splayed, a preorderTraversal is called, and true is returned
            else if(key == cur.key)
            {
                splay(cur);
                preorderTraversal();
                return true;
            }
            // If the key is not found, the preceding node is splayed, a preorderTraversal is called, and false is returned
            else
            {
                splay(cur);
                preorderTraversal();
                return false;
            }
        }
        // In any other case, false is returned
        return false;
    }
    
    // preorderTraversal has no return or parameters, it prints the tree in a preorder traversal
    public void preorderTraversal()
    {
        // If root is null, message is printed, and method returned
        if(root == null)
        {
            System.out.println("Empty Tree");
            return;
        }
        // Calls the recursive helper method with root as an argument
        preorderTraversalHelper(root);
        // New line for readability
        System.out.println();
    }
    
    // preorderTraversalHelper is a helper method with no return and a Node parameter
    private void preorderTraversalHelper(Node node)
    {
        // Base case, node is not null
        if(node != null)
        {
            // If the node is a root, RT is printed before the key
            if(node.parent == null)
            {
                System.out.print(node.key +  "RT" + " ");
            }
            // If the node is a left child, L is printed before the key
            else if(node.parent.left == node)
            {
                System.out.print(node.key +  "L" + " ");
            }
            // If the node is a right child, R is printed before the key
            else if(node.parent.right == node)
            {
                System.out.print(node.key +  "R" + " ");
            }
            // Recursive call with node.left as an argument
            preorderTraversalHelper(node.left);
            // Recursive call with node.right as an argument
            preorderTraversalHelper(node.right);
        }
    }
    
    // Splay is a helper method for the splaying procedure, it has no return and a Node parameter
    private void splay(Node node) 
    {
        // Loops as long as node's parent isn't null (bottom -> top splaying). Also, a tree with only a root can't be splayed
        while (node.parent != null) 
        {
            // par is the parent node
            Node par = node.parent;
            // gpar is the grandparent node
            Node gpar = par.parent;
            
            // If the node is a child of the root
            if (gpar == null) 
            {
                // If the node is a left child, calls rightRotate on the parent (zig)
                if (node == par.left)
                rightRotate(par);
                // Otherwise, calls leftRotate on the parent (zig)
                else
                    leftRotate(par);
            } 
            // If the node isn't a child of the root (has a grandparent)
            else 
            {
                // Left-left grandchild, rightRotate's gpar and rightRotate's par (zig-zig)
                if (node == par.left && par == gpar.left) 
                {
                    rightRotate(gpar);
                    rightRotate(par);
                }
                // right-right grandchild, leftRotate's gpar and leftRotate's par (zag-zag)
                else if (node == par.right && par == gpar.right) 
                {
                    leftRotate(gpar);
                    leftRotate(par);
                } 
                // The zig-zag conditions
                else 
                {
                    // right-left grandchild, leftRotate's par and rightRotate's gpar (zag-zig)
                    if (node == par.right) 
                    {
                        leftRotate(par);
                        rightRotate(gpar);
                    } 
                    // left-right grandchild, rightRotate's par and leftRotates's gpar (zig-zag)
                    else 
                    {
                        rightRotate(par);
                        leftRotate(gpar);
                    }
                }
            }
        }
    }
    
    // rightRotate is a helper method for splay that takes in a node parameter and has no return
    private void rightRotate(Node node) 
    {
        // Node par set as the node's parent
        Node par = node.parent;
        // Node child set as the node's left child
        Node child = node.left;
        
        // Node's left  pointer set to child's right pointer
        node.left = child.right;
        // If the child's right pointer isn't null, the parent of the child's right pointer is set to node
        if (child.right != null) 
            child.right.parent = node;
        // The child's parent is set to par
        child.parent = par;
        
        // If the parent is null, the root is set to child
        if (par == null) 
            root = child;
        // If the node is the parent's left child, the parent's left child is changed to child
        else if (node == par.left) 
            par.left = child;
        // Otherwise, the parent's right child is changed to child
        else 
            par.right = child;
        
        // The child's right child becomes node
        child.right = node;
        // The node's parent becomes child
        node.parent = child;
    }
    
    // leftRotate is a helper method for splay that takes in a node parameter and has no return
    private void leftRotate(Node node) 
    {
        // Node par set as the node's parent
        Node par = node.parent;
        // Node child set as the node's right child
        Node child = node.right;
        
        // Node's right  pointer set to child's left pointer
        node.right = child.left;
        // If the child's left pointer isn't null, the parent of the child's left pointer is set to node
        if (child.left != null) 
            child.left.parent = node;
    
        // The child's parent is set to par
        child.parent = par;
        
        // If the parent is null, the root is set to child
        if (par == null) 
            root = child;
        // If the node is the parent's left child, the parent's left child is changed to child
        else if (node == par.left) 
            par.left = child;
        // Otherwise, the parent's right child is changed to child
        else 
            par.right = child;
        
        // The child's left child becomes node
        child.left = node;
        // The node's parent becomes child
        node.parent = child;
    }
    
    // The main method is used for creating class objects and conducting experimental studies
    public static void main (String[] args) 
    {
        // Scanner scan for taking in input
        Scanner scan = new Scanner(System.in);
        // A new SplayTree object is created (can clear the tree to reset it)
        SplayTree sp = new SplayTree();
        // Condition to break out of loop
        boolean toBreak = false;
        // Continues, depending on toBreak
        while(true)
        {
            // Options for user
            System.out.println("1. Insert Key, 2. Delete Key, 3. Search Key, 4. Clear 5. Exit");
            // Asks for input and reads into choice1
            System.out.print("Choose an option: ");
            int choice1 = scan.nextInt();
            int choice2;
            // Switch statement for different actions based on choice1 value (insert, delete, search, clear, exit)
            switch(choice1)
            {
                case 1:
                    System.out.print("Enter a key to insert: ");
                    choice2 = scan.nextInt();
                    sp.insertion(choice2);
                    break;
                case 2: 
                    System.out.print("Enter a key to delete: ");
                    choice2 = scan.nextInt();
                    sp.deletion(choice2);
                    break;
                case 3:
                    System.out.print("Enter a key to search: ");
                    choice2 = scan.nextInt();
                    sp.search(choice2);
                    break;
                case 4:
                    // Sets sp to a new SplayTree to erase the previous tree
                    sp = new SplayTree();
                    System.out.println("Cleared the tree.");
                    break;
                case 5:
                    // toBreak set to true to break from loop
                    toBreak = true;
                    System.out.println("Exiting the program.");
                    break;
            }
            // Breaks from loop if toBreak is true
            if(toBreak)
                break;
        }
    }
}