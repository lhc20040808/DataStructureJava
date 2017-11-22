import java.util.Stack;

/**
 * 二叉排序树
 */
public class BinaryTree {

    private Node root;

    public void put(int i) {
        if (root == null) {
            root = new Node(null, i, null);
            return;
        }

        Node node = root;
        while (true) {
            if (i < node.obj) {
                if (node.leftChild != null)
                    node = node.leftChild;
                else {
                    node.leftChild = new Node(null, i, null);
                    break;
                }
            }

            if (i > node.obj) {
                if (node.rightChild != null)
                    node = node.rightChild;
                else {
                    node.rightChild = new Node(null, i, null);
                    break;
                }
            }
        }
    }

    private void midTraverse() {
        Stack<Node> stack = new Stack<>();
        Node top = root;
        while (!stack.isEmpty() || top != null) {
            while (top != null) {
                stack.push(top);
                top = top.leftChild;
            }

            if (!stack.isEmpty()) {
                top = stack.pop();
                System.out.println(top.obj);
                top = top.rightChild;
            }
        }
    }

    private static class Node {
        Node leftChild;
        Node rightChild;
        int obj;

        public Node(Node leftChild, int obj, Node rightChild) {
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.obj = obj;
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.put(10);
        tree.put(1);
        tree.put(5);
        tree.put(69);
        tree.put(38);
        tree.put(45);
        tree.put(80);
        tree.midTraverse();
    }

}
