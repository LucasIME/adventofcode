class Node():
    def __init__(self, v, right=None):
        self.v = v
        self.right = right

def gen_llist(n):
    head = Node(1)
    cur = head
    for i in range(2, n+1):
        new = Node(i)
        cur.right = new
        cur = new

    #last one
    cur.right = head

    return head

def process(n):
    llist_head = gen_llist(n)
    cur = llist_head
    while True:
        next = cur.right
        if next is None or next is cur:
            break
        cur.right = next.right
        cur = cur.right

    return cur.v

def main():
    entry = int(input())
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
