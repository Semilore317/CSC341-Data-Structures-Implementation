public class MyQueue {

    int[] queue = new int[5];
    int front = 0;
    int rear = 0;
    int size = 0;

    public void enqueue(int data) {
        if (size == queue.length) {
            System.out.println("Queue is full");
        } else {
            queue[rear] = data;
            rear = (rear + 1) % queue.length;
            size++;
            System.out.println(data + " added to queue");
        }
    }

    public void dequeue() {
        if (size == 0) {
            System.out.println("Queue is empty");
        } else {
            int removed = queue[front];
            front = (front + 1) % queue.length;
            size--;
            System.out.println(removed + " removed from queue");
        }
    }

    public void peek() {
        if (size == 0) {
            System.out.println("Queue is empty");
        } else {
            System.out.println("Front element: " + queue[front]);
        }
    }

    public void display() {
        System.out.print("Queue: ");

        for (int i = 0; i < size; i++) {
            System.out.print(queue[(front + i) % queue.length] + " ");
        }

        System.out.println();
    }

    public static void main(String[] args) {

        MyQueue q = new MyQueue();

        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);

        q.display();

        q.dequeue();

        q.display();

        q.peek();
    }
}