products=[
    {"name":"Laptop","stock":5},
    {"name":"Mouse","stock":15},
    {"name":"Keyboard","stock":8},
    {"name":"Monitor","stock":20},
    {"name":"Printer","stock":3}
]

print("Products with stock less than 10:")
for p in products:
    if p["stock"]<10:
        print(p["name"],"- Stock:",p["stock"])
