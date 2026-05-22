class Stack:
    """A simple stack backed by a Python list."""

    def __init__(self):
        self._items = []

    def push(self, item):
        self._items.append(item)

    def pop(self):
        if self.is_empty():
            raise IndexError("pop from empty stack")
        return self._items.pop()

    def peek(self):
        if self.is_empty():
            raise IndexError("peek from empty stack")
        return self._items[-1]

    def is_empty(self):
        return len(self._items) == 0

    def size(self):
        return len(self._items)

    def __repr__(self):
        return f"Stack({self._items})"


if __name__ == "__main__":
    s = Stack()
    for val in [1, 2, 3, 4]:
        s.push(val)
        print(f"pushed: {val}")

    print(f"peek: {s.peek()}")
    print(f"popped: {s.pop()}")
    print(f"stack after pop: {s}")
