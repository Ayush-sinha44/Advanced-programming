"""
Assignment 14: Demonstrating Cyclic References and Garbage Collection in Python

This program creates two objects that reference each other, forming a cycle.
Even after deleting the main references, the objects remain in memory because
their internal references keep their reference counts above zero.

Python's cyclic garbage collector detects and removes them.
"""

import sys
import gc


class Node:
    def __init__(self, name):
        self.name = name
        self.link = None

    def __del__(self):
        print(f"Node {self.name} is being destroyed")


# Enable automatic garbage collection
gc.enable()

print("=== Creating Two Nodes ===")
A = Node("A")
B = Node("B")

# Create a cycle
A.link = B
B.link = A

print("\n=== Reference Counts ===")
# sys.getrefcount(obj) returns actual count + 1 because the function
# itself temporarily holds a reference.
print(f"Reference count of A: {sys.getrefcount(A)}")
print(f"Reference count of B: {sys.getrefcount(B)}")

print("\n=== Objects Tracked by GC Before Deletion ===")
tracked_before = [obj for obj in gc.get_objects() if isinstance(obj, Node)]
for obj in tracked_before:
    print(f"Node {obj.name} is currently in memory")

print("\n=== Deleting Main References ===")
del A
del B

print("Variables A and B have been deleted.")
print("But the objects still reference each other, forming a cycle.")

print("\n=== Objects Still in Memory Before gc.collect() ===")
tracked_after_del = [obj for obj in gc.get_objects() if isinstance(obj, Node)]
for obj in tracked_after_del:
    print(f"Node {obj.name} is still in memory")

print("\n=== Forcing Garbage Collection ===")
unreachable = gc.collect()
print(f"Number of unreachable objects collected: {unreachable}")

print("\n=== Objects Remaining After gc.collect() ===")
tracked_after_gc = [obj for obj in gc.get_objects() if isinstance(obj, Node)]

if not tracked_after_gc:
    print("No Node objects remain in memory.")
else:
    for obj in tracked_after_gc:
        print(f"Node {obj.name} is still in memory")
