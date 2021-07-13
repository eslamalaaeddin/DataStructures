package BinarySearchTree;

import java.util.*;

public class MyBinarySearchTree<T extends Comparable<T>> {
    private int nodeCount = 0;

    private Node root = null;

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return nodeCount;
    }

    // TODO: 6/25/2021 Implement it again
    public boolean add(T element) {
        if (contains(element)) {
            return false;
        } else {
            root = add(root, element);
            nodeCount++;
            return true;
        }
    }

    // TODO: 6/25/2021 Implement it again
    private Node add(Node node, T element) {
        if (node == null) {
            node = new Node(null, null, element);
        } else {

            if (element.compareTo(node.value) < 0) {
                node.leftChild = add(node.leftChild, element);
            } else{
                node.rightChild = add(node.rightChild, element);
            }
        }


        return node;
    }

    public boolean contains(T element) {
        return contains(root, element);
    }

    private boolean contains(Node node, T element) {
        // i am in the leaf or the tree is empty
        if (node == null) return false;

        int comparator = element.compareTo(node.value);

        if (comparator < 0)
            return contains(node.leftChild, element);

        else if (comparator > 0)
            return contains(node.rightChild, element);

        else return true;

    }

    public boolean remove(T element){

        if (isEmpty())
            throw new RuntimeException("Empty BST");

        if (contains(element)){
            root = remove(root, element);
            nodeCount--;
            return true;
        }

        throw new RuntimeException("Element is not found");

    }

    private Node remove(Node node, T element){
        //First case
        if (node == null) return null;

        int comparator = element.compareTo(node.value);

        if (comparator < 0){
            node.leftChild = remove(node.leftChild, element);
        }
        else if (comparator > 0){
            node.rightChild = remove(node.rightChild, element);
        }

        else {

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
    private Node findMax(Node node){
        while (node.rightChild != null) node = node.rightChild;
        return node;
    }


    private class Node {
        T value;
        Node leftChild, rightChild;

        public Node(Node leftChild, Node rightChild, T elem) {
            this.value = elem;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
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
                /*
                 Islam thinks that order is important
                 as pushing the right child first will make the left child on top of the stack,
                 so when we pop it we traverse the left side instead of the right one
                 */
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
