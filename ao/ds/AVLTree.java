package ao.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Self balancing Binary-Search Tree
 */
public class AVLTree<T extends Comparable<T>> {

    public static void main(String[] args) {
        AVLTree<Integer> avlt = new AVLTree<>();

        int size = 10;
        System.out.println(" adding " + size * size + " items...\n");
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                int k = (int) (Math.random() * size * 100);
                if (!avlt.add(k)) {
                    i--;
                }
            }
        }

        System.out.println("\n\n");
        testNodeFault(avlt.root, 0);
        
    }

    public static <T extends Comparable<T>> void testNodeFault(Node<T> node, int depth) {
        
        char[] tabs = new char[depth + 1];
        Arrays.fill(tabs, ' ');

        if (node == null) {
            return;
        }

        System.out.println(String.valueOf(tabs) + "{\"" + node + "\": " + node.value);
        
        if (node.left != null) {
            if (node.value.compareTo(node.left.value) != 1) {
                throw new RuntimeException(node + " has to be bigger than " + node.left);
            }
            testNodeFault(node.left, depth + 1);
        }

        if (node.right != null) {
            if (node.value.compareTo(node.right.value) != -1) {
                throw new RuntimeException(node + " has to be smaller than " + node.right);
            }
            testNodeFault(node.right, depth + 1);
        }

        System.out.println(String.valueOf(tabs) + "}");

    }

    private Node<T> root;

    public boolean add(T value) {

        if (root == null) {
            root = new Node<>(value);
            return true;
        }

        Node<T> curr = root;
        List<Node<T>> nodes = new ArrayList<>();
        nodes.add(null); // root's parent

        loop_find_pos:
        while (curr != null) {
            
            switch  (curr.value.compareTo(value)) {
                case 0:
                    return false;

                case 1:
                    if (curr.left == null) {
                        curr.left = new Node<>(value);
                        break loop_find_pos;
                    }
                    nodes.add(curr);
                    curr = curr.left;
                    break;

                case -1:
                    if (curr.right == null) {
                        curr.right = new Node<>(value);
                        break loop_find_pos;
                    }
                    nodes.add(curr);
                    curr = curr.right;
            }
            
        }

        balance(nodes);

        return true;

    }
    
    public T get(T value) {

        if (root == null) {
            return null;
        }

        Node<T> start = root;
        while (start != null) {
            switch (start.value.compareTo(value)) {
            case 0:
                return start.value;
            
            case 1:
                start = start.left;
                break;
            
            case -1:
                start = start.right;
            }
        }

        return null;

    }

    public T remove(T value) {

        return value;
    }

    private void balance(List<Node<T>> nodes) {

        if (nodes == null || nodes.size() < 2) {
            return;
        }

        for (int i = nodes.size() - 1; i > 0; i--) {
            Factor factor = factor(nodes.get(i));
            if (factor == Factor.Balanced) {
                continue;
            }

            if (factor == Factor.LeftHeavy) {
                rotateRight(nodes.get(i-1), nodes.get(i));
            }
            else {
                rotateLeft(nodes.get(i-1), nodes.get(i));
            }

        }
                
    }

    private void rotateLeft(Node<T> parent, Node<T> node) {

        if (node == null || node.right == null) {
            throw new RuntimeException("Illegal left rotation!");
        }

        Node<T> top = node.right;
        if (top.right == null) { // check if it is necessary rotate left-right
            top = top.left;
            top.left = node;
            top.right = node.right;
            node.right.left = null;
            node.right = null;            
        } 
        else {
            Node<T> oldLeft = top.left;
            top.left = node;
            node.right = oldLeft;    
        }

        if (parent == null) {
            root = top;
        } 
        else {
            if (parent.left == node) {
                parent.left = top;
            } 
            else if (parent.right == node) {
                parent.right = top;
            } 
            else {
                throw new RuntimeException(node + "Parent-Child releation is broken!");
            }     
        }

    }

    private void rotateRight(Node<T> parent, Node<T> node) {
        
        if (node == null || node.left == null) {
            throw new RuntimeException("Illegal right rotation!");
        }
        
        Node<T> top = node.left;
        if (top.left == null) { //check if it is necessary rotate right-left
            top = top.right;
            top.right = node;
            top.left = node.left;
            node.left.right = null;
            node.left = null;
        } 
        else {
            Node<T> oldRight = top.right;
            top.right = node;
            node.left = oldRight;    
        }

        if (parent == null) {
            root = top;
        } 
        else {
            if (parent.left == node) {
                parent.left = top;
            } 
            else if (parent.right == node) {
                parent.right = top;
            } 
            else {
                throw new RuntimeException(node + "Parent-Child releation is broken!");
            }     
        }

    }

    private enum Factor { Balanced, LeftHeavy, RightHeavy }

    public static Factor factor(Node<?> node) {
        
        if (node == null) {
            return Factor.Balanced;
        }

        int factor = node.getWeight();

        if (factor < -1) {
            return Factor.LeftHeavy;
        }
        else if (factor > 1) {
            return Factor.RightHeavy;
        }
        return Factor.Balanced;
    }

    static class Node<E extends Comparable<E>> {

        private E value;

        private Node<E> left;
        private Node<E> right;

        public Node(E value) {
            this.value = value;
        }

        int getHeight() {

            int leftHeight = 0;
            int rightHeight = 0;

            if (left != null) {
                leftHeight = left.getHeight();
            }

            if (right != null) {
                rightHeight = right.getHeight();
            }

            return 1 + Math.max(rightHeight, leftHeight);
        }

        public int getWeight() {
            
            int leftHeight = 0;
            int rightHeight = 0;

            if (left != null) {
                leftHeight = left.getHeight();
            }

            if (right != null) {
                rightHeight = right.getHeight();
            }

            return rightHeight - leftHeight;

        }

        @Override
        public String toString() {
            String v = "v=" + value;

            if (left != null) {
                v += "(" + left.value + ")";
            }

            if (right != null) {
                v += "(" + right.value + ")";
            }

            v += "[" + getWeight() + "," + getHeight() + "]";
            return v;
        }
    }


}