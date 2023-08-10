# coding: utf-8
from time import sleep
from datetime import datetime
def printext(s,n):
    with open(str(n)+'.json','w+') as f:
        f.write(''.join(s))
        print(str(n)+'.json записан способом №'+str(n)+'!')

def first(x):
    x=list(x.split('*'))
    flag=0;flags=['{','  },{',]
    x[0]='['+x[0];x[-1]=x[-1]+'\n}]'
    for i in range(len(x)-1):
        if '- \n' in x[i]:
            x[i]=x[i].replace('-',flags[flag],1)
            flag=1
        if "\"" in x[i+1] and not '{' in x[i]:
            x[i]=x[i][:-1]+',\n'
    return x

def second(s):
    import yaml
    import json
    YML=s
    with open(YML) as yamlin,open('2.json','w') as jsonout:
        yamlsafe=yaml.safe_load(yamlin)
        json.dump(yamlsafe,jsonout,ensure_ascii=False)
    print('2.json записан способом №2!')
def third(s):
    import re
    s=''.join(s);stri=''
    regex1=r'(-\s\n(.|\n\s\s)+)(?!-\s\n)'
    regex2=r'\S\n(?!.*})'
    reg=re.findall(regex1,s)
    #print(reg)
    for x in range(len(reg)):
        reg[x]=''.join(reg[x][0][1:])
        #print(reg[x],777)
    stri='\n  },{'.join(reg)
    stri='[{'+stri+'\n}]'
    stri=re.sub(regex2,'",\n',stri)
    #print(stri)
    return stri
        #if "\"" in s[i + 1] and not '{' in s[i]:
            #s[i] = s[i][:-1] + ',\n'












YML='monday.yaml'
with open (YML) as f:
    s=f.readlines()

frst=first('*'.join(s))
printext(frst,1)
sleep(0.25)
scnd=second(YML)
sleep(0.25)
thrd=third(s)
printext(thrd,3)
sleep(0.25)

i=input('\nзапуск стократного теста\n')
if i=='bb':
    exit()

t1s=datetime.now()
for i in range(100):
    frst = first('*'.join(s))
    printext(frst, 1)
t1e=datetime.now();t1d=t1e-t1s
sleep(0.5)
t2s=datetime.now()
for i in range(100):
    scnd = second(YML)
t2e=datetime.now();t2d=t2e-t2s
sleep(0.5)
t3s=datetime.now()
for i in range(100):
    thrd = third(s)
    printext(thrd, 3)
t3e=datetime.now();t3d=t3e-t3s
sleep(0.5)
print('\n'*3+'способом №1 выполнено за '+str(t1d)[7:]+' сек.\nспособом №2 выполнено за '+str(t2d)[7:]+' сек.\nспособом №3 выполнено за '+str(t3d)[7:]+" сек.")