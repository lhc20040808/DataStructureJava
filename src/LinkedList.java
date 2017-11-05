/**
 * Created by qgg on 2017/11/4.
 */
public class LinkedList<T> {

    Node first;
    Node last;
    int size;

    public void add(T obj) {
        Node node = new Node(obj, last, null);
        if (first == null) {
            first = node;
        }

        if (last != null) {
            last.next = node;
        }
        last = node;
        size++;
    }

    public void add(int index, T obj) {
        if (index < 0 || index > size)
            return;

        if (index == size) {
            add(obj);
        } else {
            Node nextNode = get(index);
            Node preNode = nextNode.prev;
            Node newNode = new Node(obj, preNode, nextNode);
            nextNode.prev = newNode;
            if (preNode != null) {
                preNode.next = newNode;
            } else {
                first = newNode;
            }
            size++;
        }
    }

    public void remove(int index) {
        Node node = get(index);

        Node pre = node.prev;
        Node next = node.next;

        if (pre == null) {
            first = node.next;
        } else {
            pre.next = next;
        }

        if (next == null) {
            last = node.prev;
        } else {
            next.prev = pre;
        }

        size--;
    }

    public Node get(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        Node node = null;
        if (index < (size >> 2)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    public void reverse() {
        if (size < 1)
            return;

        Node p = first;//记录原首节点
        while (p.next != null) {
            Node q = p.next;//取出当前要翻转的节点
            p.next = p.next.next;//原首节点指向翻转节点的下个节点
            q.next = first;//翻转节点指向首节点
            q.prev = null;//翻转节点上个节点指向当前首节点的上个节点
            first.prev = q;//当前首节点上个节点置为翻转节点
            first = q;//修改首节点位置
        }

        last = p;
    }

    public int getSize() {
        return size;
    }

    public Node getFirst() {
        return first;
    }

    public Node getLast() {
        return last;
    }

    public static class Node<T> {

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        public T item;
        public Node prev;
        public Node next;
    }
}
