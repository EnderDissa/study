d=['r1','r2','i1','r3','i2','i3','i4'];i=0;flag=0
n=list(input('введите код: '));lenn=len(n)

for s in n:
    if s!='0' and s!='1' or lenn!=7:
        exit('ты ввёл что-то неправильно!')
#print(lenn,k,flag)

s1=str(n[::2].count('1')%2)
s2=str((n[1:3]+n[5:7]).count('1')%2)
s3=str(n[3:].count('1')%2)
s=int(s3+s2+s1,2)

if s:
    n[s - 1] =str((int(n[s - 1]) + 1) % 2)
    print("сообщение:" + n[2]+n[4]+n[5]+n[6])
    print("я исправил ошибку в бите №" + str(s) + ", " + d[s - 1])
else:
    print("сообщение:" + n[2]+n[4]+n[5]+n[6])
    print("ошибок не было!")
