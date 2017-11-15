import java.util.Stack;

public class Tree {


    public static void main(String[] args) {
        Node nodeJ = new Node<String>(null, "J", null);
        Node nodeI = new Node<String>(null, "I", null);
        Node nodeG = new Node<String>(null, "G", nodeJ);
        Node nodeK = new Node<String>(null, "K", null);
        Node nodeH = new Node<String>(null, "H", nodeK);
        Node nodeF = new Node<String>(nodeI, "F", null);
        Node nodeE = new Node<String>(null, "E", null);
        Node nodeC = new Node<String>(nodeF, "C", nodeG);
        Node nodeD = new Node<String>(nodeH, "D", null);
        Node nodeB = new Node<String>(nodeD, "B", nodeE);
        Node nodeA = new Node<String>(nodeB, "A", nodeC);

        Tree tree = new Tree();

//        System.out.println("---递归前序遍历---");
//        tree.preTraverseRecursion(nodeA);
//        System.out.println("---前序遍历---");
//        tree.preTraverse(nodeA);
//        System.out.println("---递归中序遍历---");
//        tree.midTraverseRecursion(nodeA);
//        System.out.println("---中序遍历---");
//        tree.midTraverse(nodeA);

        System.out.println("---递归后序遍历---");
        tree.postTraverseRecursion(nodeA);
        System.out.println("---后序遍历---");
        tree.postTraverse(nodeA);

    }

    private static class Node<T> {
        Node leftChild;
        Node rightChild;
        T obj;

        public Node(Node leftChild, T obj, Node rightChild) {
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.obj = obj;
        }
    }

    /**
     * 递归前序遍历
     *
     * @param node
     */
    private void preTraverseRecursion(Node node) {
        if (node == null)
            return;
        System.out.println(node.obj.toString());
        preTraverseRecursion(node.leftChild);
        preTraverseRecursion(node.rightChild);
    }

    /**
     * 循环前序遍历
     *
     * @param node
     */
    private void preTraverse(Node node) {
        if (node == null)
            return;
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node item = stack.pop();
            System.out.println(item.obj);
            if (item.rightChild != null)
                stack.push(item.rightChild);
            if (item.leftChild != null)
                stack.push(item.leftChild);
        }
    }

    /**
     * 递归中序遍历
     *
     * @param node
     */
    private void midTraverseRecursion(Node node) {
        if (node == null)
            return;
        midTraverseRecursion(node.leftChild);
        System.out.println(node.obj.toString());
        midTraverseRecursion(node.rightChild);
    }

    private void midTraverse(Node node) {
        if (node == null)
            return;
        Stack<Node> stack = new Stack<>();

        Node pre = node;
        while (!stack.isEmpty() || pre != null) {

            while (pre != null) {
                stack.push(pre);
                pre = pre.leftChild;
            }

            if (!stack.isEmpty()) {
                pre = stack.pop();
                System.out.println(pre.obj);
                pre = pre.rightChild;
            }
        }
    }

    /**
     * 递归后序遍历
     *
     * @param node
     */
    private void postTraverseRecursion(Node node) {
        if (node == null)
            return;
        postTraverseRecursion(node.leftChild);
        postTraverseRecursion(node.rightChild);
        System.out.println(node.obj.toString());
    }

    /**
     * 后序遍历
     *
     * @param node
     */
    private void postTraverse(Node node) {
        Node pre = node;
        Node lastNode = null;
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || pre != null) {
            while (pre != null) {
                stack.push(pre);
                pre = pre.leftChild;
            }

            if (!stack.isEmpty()) {
                pre = stack.peek();
                if (pre.rightChild != null && pre.rightChild != lastNode) {
                    pre = pre.rightChild;
                } else {
                    pre = stack.pop();
                    lastNode = pre;
                    System.out.println(pre.obj);
                    pre = null;
                }
            }
        }
    }


}
