import java.util.*;
import java.util.LinkedList;

/**
 * 平衡二叉树
 */
public class AVLTree {
    private static final int LH = 1;
    private static final int RH = -1;
    private static final int EH = 0;

    private Node root;
    private boolean isTaller;

    public boolean insert(int i) {
        if (root == null) {
            root = new Node(i);
            return true;
        } else {
            return insert(root, i);
        }
    }

    private boolean insert(Node node, int i) {

        if (node.num == i) {
            return false;
        }

        if (i < node.num) {
            if (node.leftChild == null) {
                node.leftChild = new Node(i);
                isTaller = true;
            } else {
                insert(node.leftChild, i);
            }

            if (isTaller) {
                switch (node.balance) {
                    case EH:
                        node.balance = LH;
                        break;
                    case RH:
                        node.balance = EH;
                        break;
                    case LH:
//                    System.out.println(node + " 为根结点子树[左边]不平衡");
                        leftBalance(node);
                        isTaller = false;
                        break;
                }
            }

        } else {

            if (node.rightChild == null) {
                node.rightChild = new Node(i);
                isTaller = true;
            } else {
                insert(node.rightChild, i);
            }

            if (isTaller) {
                switch (node.balance) {
                    case EH:
                        node.balance = RH;
                        break;
                    case LH:
                        node.balance = EH;
                        break;
                    case RH:
//                    System.out.println(node + " 为根结点子树[右边]不平衡");
                        rightBalance(node);
                        isTaller = false;
                        break;
                }
            }

        }

        return true;
    }

    /**
     * 左旋
     * 当平衡因子小于-1时，平衡二叉树进行左旋。
     *
     * @param node
     */
    public void turnLeft(Node node) {
        Node parent = findParent(node);
        Node nr = node.rightChild;
        //step1 最小不平衡子树根节点的右孩子指向原右孩子的左孩子
        node.rightChild = nr.leftChild;
        //step2 父结点指向新的平衡树的根节点
        if (parent == null) {
            root = nr;
        } else if (parent.leftChild == node) {
            parent.leftChild = nr;
        } else if (parent.rightChild == node) {
            parent.rightChild = nr;
        }
        //step3
        nr.leftChild = node;
    }

    /**
     * 右旋
     * 当平衡因子大于1时，平衡二叉树右旋
     *
     * @param node
     */
    public void turnRight(Node node) {
        Node parent = findParent(node);
        Node nl = node.leftChild;
        //step1
        node.leftChild = nl.rightChild;
        //step2
        if (parent == null) {
            root = nl;
        } else if (parent.leftChild == node) {
            parent.leftChild = nl;
        } else if (parent.rightChild == node) {
            parent.rightChild = nl;
        }
        //step3
        nl.rightChild = node;
    }

    public void leftBalance(Node node) {
        Node nl = node.leftChild;
        switch (nl.balance) {
            case LH:
                nl.balance = EH;
                node.balance = EH;
                turnRight(node);
                break;
            case RH:
                Node nlr = nl.rightChild;
                switch (nlr.balance) {
                    case LH:
                        nl.balance = EH;
                        node.balance = RH;
                        break;
                    case RH:
                        node.balance = EH;
                        nl.balance = LH;
                        break;
                    case EH:
                        node.balance = nl.balance = EH;
                        break;
                }
                nlr.balance = EH;
                turnLeft(nl);
                turnRight(node);
                break;
        }
    }

    public void rightBalance(Node node) {
        Node nr = node.rightChild;
        switch (nr.balance) {
            case RH:
                nr.balance = EH;
                node.balance = EH;
                turnLeft(node);
                break;
            case LH:
                Node nrl = nr.leftChild;
                switch (nrl.balance) {
                    case LH:
                        node.balance = EH;
                        nr.balance = RH;
                        break;
                    case RH:
                        node.balance = LH;
                        nr.balance = EH;
                        break;
                    case EH:
                        node.balance = EH;
                        nr.balance = EH;
                        break;
                }
                nrl.balance = EH;
                turnRight(nr);
                turnLeft(node);
                break;
        }
    }

    /**
     * 通过层次遍历寻找父结点，基于大小值进行优化
     *
     * @param node
     * @return
     */
    private Node findParent(Node node) {
        if (root == null || node == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        Node result = null;
        while (!queue.isEmpty()) {
            Node tmp = queue.poll();
            if (tmp.leftChild == node || tmp.rightChild == node) {
                result = tmp;
                break;
            }
            if (tmp.compareTo(node) > 0) {
                if (tmp.leftChild != null)
                    queue.offer(tmp.leftChild);
            } else {
                if (tmp.rightChild != null)
                    queue.offer(tmp.rightChild);
            }
        }
        return result;
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
                System.out.println(top.num);
                top = top.rightChild;
            }
        }
    }

    private void levelTraverse() {
        System.out.println("---层次遍历---");
        if (root == null) {
            return;
        }
        Queue<Node> stack = new LinkedList<>();
        stack.offer(root);
        while (!stack.isEmpty()) {
            Node tmp = stack.poll();
            System.out.println(tmp);
            if (tmp.leftChild != null)
                stack.offer(tmp.leftChild);
            if (tmp.rightChild != null)
                stack.offer(tmp.rightChild);
        }
    }

    private static class Node implements Comparable<Node> {
        Node leftChild;
        Node rightChild;
        int num;
        int balance;

        Node(int num) {
            this.leftChild = null;
            this.rightChild = null;
            this.num = num;
        }

        @Override
        public int compareTo(Node o) {
            return num - o.num;
        }

        @Override
        public String toString() {
            return "num:" + num + " | balance:" + balance + " [左结点:" + leftChild + " 右结点:" + rightChild + "]";
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(3);
        tree.insert(2);
        tree.insert(1);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.insert(7);
        tree.insert(10);
        tree.insert(9);
        tree.insert(8);
        tree.levelTraverse();
    }

}
