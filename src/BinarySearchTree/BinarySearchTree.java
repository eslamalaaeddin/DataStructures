package BinarySearchTree;


/*
insertion
deletion
traversing
 */

import java.util.*;

public class BinarySearchTree<T extends Comparable<T>> {

    private int nodeCount = 0;

    private Node root = null;

    private class Node {
        T value;
        Node leftChild, rightChild;

        public Node(Node leftChild, Node rightChild, T elem) {
            this.value = elem;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return nodeCount;
    }

    // Add an element to this binary tree. Returns true
    // if we successfully perform an insertion
    public boolean add(T elem) {

        // Check if the value already exists in this
        // binary tree, if it does ignore adding it
        if (contains(elem)) {
            return false;

            // Otherwise add this element to the binary tree
        } else {
            root = add(root, elem);
            nodeCount++;
            return true;
        }
    }

    // Private method to recursively add a value in the binary tree
    private Node add(Node node, T elem) {
        // Base case: found a leaf node
        if (node == null) {
            node = new Node(null, null, elem);

        } else {
            // Pick a subtree to insert element
            if (elem.compareTo(node.value) < 0) {
                node.leftChild = add(node.leftChild, elem);
            } else {
                node.rightChild = add(node.rightChild, elem);
            }
        }

        return node;
    }

    // Remove a value from this binary tree if it exists, O(n)
    public boolean remove(T elem) {

        // Make sure the node we want to remove
        // actually exists before we remove it
        if (contains(elem)) {
            root = remove(root, elem);
            nodeCount--;
            return true;
        }
        return false;
    }

    private Node remove(Node node, T elem) {

        if (node == null) return null;

        int cmp = elem.compareTo(node.value);

        // Dig into left subtree, the value we're looking
        // for is smaller than the current value
        if (cmp < 0) {
            node.leftChild = remove(node.leftChild, elem);

            // Dig into right subtree, the value we're looking
            // for is greater than the current value
        } else if (cmp > 0) {
            node.rightChild = remove(node.rightChild, elem);

            // Found the node we wish to remove
        } else {

            // This is the case with only a right subtree or
            // no subtree at all. In this situation just
            // swap the node we wish to remove with its right child.
            if (node.leftChild == null) {
                return node.rightChild;

                // This is the case with only a left subtree or
                // no subtree at all. In this situation just
                // swap the node we wish to remove with its left child.
            } else if (node.rightChild == null) {

                return node.leftChild;

                // When removing a node from a binary tree with two links the
                // successor of the node being removed can either be the largest
                // value in the left subtree or the smallest value in the right
                // subtree. In this implementation I have decided to find the
                // smallest value in the right subtree which can be found by
                // traversing as far left as possible in the right subtree.
            } else {

                // Find the leftmost node in the right subtree
                Node tmp = findMin(node.rightChild);

                // Swap the value
                node.value = tmp.value;

                // Go into the right subtree and remove the leftmost node we
                // found and swapped value with. This prevents us from having
                // two nodes in our tree with the same value.
                node.rightChild = remove(node.rightChild, tmp.value);

                // If instead we wanted to find the largest node in the left
                // subtree as opposed to smallest node in the right subtree
                // here is what we would do:
                // Node tmp = findMax(node.left);
                // node.value = tmp.value;
                // node.left = remove(node.left, tmp.value);

            }
        }

        return node;
    }

    // Helper method to find the leftmost node (which has the smallest value)
    private Node findMin(Node node) {
        while (node.leftChild != null) node = node.leftChild;
        return node;
    }

    // Helper method to find the rightmost node (which has the largest value)
    private Node findMax(Node node) {
        while (node.rightChild != null) node = node.rightChild;
        return node;
    }

    // returns true is the element exists in the tree
    public boolean contains(T elem) {
        return contains(root, elem);
    }

    // private recursive method to find an element in the tree
    private boolean contains(Node node, T elem) {

        // Base case: reached bottom, value not found
        if (node == null) return false;

        int cmp = elem.compareTo(node.value);

        // Dig into the left subtree because the value we're
        // looking for is smaller than the current value
        if (cmp < 0) return contains(node.leftChild, elem);

            // Dig into the right subtree because the value we're
            // looking for is greater than the current value
        else if (cmp > 0) return contains(node.rightChild, elem);

            // We found the value we were looking for
        else return true;
    }

    // Computes the height of the tree, O(n)
    public int height() {
        return height(root);
    }

    // Recursive helper method to compute the height of the tree
    private int height(Node node) {
        if (node == null) return 0;
        return Math.max(height(node.leftChild), height(node.rightChild)) + 1;
    }

    // This method returns an iterator for a given TreeTraversalOrder.
    // The ways in which you can traverse the tree are in four different ways:
    // preorder, inorder, postorder and levelorder.
    public Iterator<T> traverse(TreeTraversalOrder order) {
        switch (order) {
            case PRE_ORDER:
                return preOrderTraversal();
            case IN_ORDER:
                return inOrderTraversal();
            case POST_ORDER:
                return postOrderTraversal();
            case LEVEL_ORDER:
                return levelOrderTraversal();
            default:
                return null;
        }
    }

    // Returns as iterator to traverse the tree in pre order
    private Iterator<T> preOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                Node node = stack.pop();
                if (node.rightChild != null) stack.push(node.rightChild);
                if (node.leftChild != null) stack.push(node.leftChild);
                return node.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in order
    private Iterator<T> inOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            Node trav = root;

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {

                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();

                // Dig left
                while (trav != null && trav.leftChild != null) {
                    stack.push(trav.leftChild);
                    trav = trav.leftChild;
                }

                Node node = stack.pop();

                // Try moving down right once
                if (node.rightChild != null) {
                    stack.push(node.rightChild);
                    trav = node.rightChild;
                }

                return node.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in post order
    private Iterator<T> postOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final Stack<Node> stack1 = new Stack<>();
        final Stack<Node> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            Node node = stack1.pop();
            if (node != null) {
                stack2.push(node);
                if (node.leftChild != null) stack1.push(node.leftChild);
                if (node.rightChild != null) stack1.push(node.rightChild);
            }
        }
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !stack2.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                return stack2.pop().value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in level order
    private Iterator<T> levelOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                return root != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
                Node node = queue.poll();
                if (node.leftChild != null) queue.offer(node.leftChild);
                if (node.rightChild != null) queue.offer(node.rightChild);
                return node.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
