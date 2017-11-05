/**
 * Created by qgg on 2017/11/4.
 */
public class LinkedList<T> {

    private Node first;
    private Node last;
    private int size;

    public void addLast(T obj) {
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
            addLast(obj);
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

    public void clear() {
        if (size == 0)
            return;

        Node p = null;
        while (first != null) {
            p = first;
            first = first.next;
            p.next = null;
            p.prev = null;
            size--;
        }

        last = null;

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

    /**
     * 就地翻转
     */
    public void reverse() {
        if (size < 1)
            return;

        Node p = first;//①记录原首结点
        while (p.next != null) {
            Node q = p.next;//②取出当前要翻转的结点
            p.next = p.next.next;//③原首结点指向翻转结点的下个结点
            q.next = first;//④翻转结点指向首结点
            q.prev = null;//⑤翻转结点上个结点指向当前首结点的上个结点
            first.prev = q;//⑥当前首结点上个结点置为翻转结点
            first = q;//⑦修改首结点位置，重复2-7
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

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(0, new Integer(2));
        linkedList.addLast(new Integer(4));
        linkedList.addLast(new Integer(6));
        linkedList.add(0, new Integer(8));
        linkedList.add(2, new Integer(10));
        linkedList.add(2, new Integer(12));
        for (int i = 0; i < linkedList.getSize(); i++) {
            System.out.println("Value:" + linkedList.get(i).item);
        }
        linkedList.remove(3);
        System.out.println("翻转");
        linkedList.reverse();
        for (int i = 0; i < linkedList.getSize(); i++) {
            System.out.println("Value:" + linkedList.get(i).item);
        }

        linkedList.clear();
        System.out.println("size:" + linkedList.getSize());
    }
}
