import java.util.NoSuchElementException;

public class DoublyLinkedList<T> {

  private static final class Node<T> {
    T data;
    Node<T> next;
    Node<T> prev;

    Node(T data) {
      this.data = data;
    }
  }

  private Node<T> head;
  private Node<T> tail;
  private int size;

  public void addFirst(T data) {
    Node<T> newNode = new Node<>(data);
    if (isEmpty()) {
      head = tail = newNode;
    } else {
      newNode.next = head;
      head.prev = newNode;
      head = newNode;
    }
    size++;
  }

  public void addLast(T data) {
    Node<T> newNode = new Node<>(data);
    if (isEmpty()) {
      head = tail = newNode;
    } else {
      tail.next = newNode;
      newNode.prev = tail;
      tail = newNode;
    }
    size++;
  }

  public T removeFirst() {
    if (isEmpty())
      throw new NoSuchElementException();
    T data = head.data;
    head = head.next;
    if (head == null)
      tail = null;
    else
      head.prev = null;
    size--;
    return data;
  }

  public T removeLast() {
    if (isEmpty())
      throw new NoSuchElementException();
    T data = tail.data;
    tail = tail.prev;
    if (tail == null)
      head = null;
    else
      tail.next = null;
    size--;
    return data;
  }

  public T get(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException();
    Node<T> current;
    if (index < size / 2) {
      current = head;
      for (int i = 0; i < index; i++)
        current = current.next;
    } else {
      current = tail;
      for (int i = size - 1; i > index; i--)
        current = current.prev;
    }
    return current.data;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void print() {
    Node<T> temp = head;
    while (temp != null) {
      System.out.print(temp.data + (temp.next != null ? " <=> " : ""));
      temp = temp.next;
    }
    System.out.println();
  }

  public static void main(String[] args) {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    list.addLast(10);
    list.addLast(20);
    list.addFirst(5);

    System.out.print("List Content: ");
    list.print();

    System.out.println("Element at index 1: " + list.get(1));
    System.out.println("Removed: " + list.removeLast());
    list.print();
  }
}
