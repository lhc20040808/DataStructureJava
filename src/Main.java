public class Main {

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(0, new Integer(2));
        linkedList.add(new Integer(4));
        linkedList.add(new Integer(6));
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
    }
}
