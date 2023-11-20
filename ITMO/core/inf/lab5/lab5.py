from random import randint as r
with open("lab5-3.csv", "w+") as f:
    for y in range(r(10,50)):
        for x in range(r(10, 50)):
            s=""
            for i in range(r(0,10)): s+=chr(r(97, 122))
            f.write(s + ";")
        f.write("\n")