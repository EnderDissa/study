import re
import time
def next():
    time.sleep(0.25)
    print('тесты кончились, продолжить? (any/n)')
    n=input()
    print('\n')
    if n=='n': exit()


def dop(test):
    reg=r'(((?<=[\?\!\.]\s))([^\s\.\?\!]+\s+){0,1}(([^\s\.\?\!]*\s+){0,3}\S*)\?)'
    match=re.findall(reg,test)
    return match


tests=['one two three four five six seven? fourr five six seven? one? one two?   tw   tbd?YDU?']
for t in tests:
    if dop(t):
        for r in range(len(dop(t))):
            print(dop(t)[r][0])
input()
with open('Macbeth.txt') as f:
    s='';k=0
    for test in f:
        s+=test
    s=s.replace('\n    ',' ')
    s = s.replace(';', '.')
    s=s.replace('.?','?')
    s=s.replace(',',', ')
    s=s.replace('  ',' ')
    s=s.replace('\n ',' ')

    print(s)

    if dop(s):
        for r in range(len(dop(s))):
            print(dop(s)[r][0])
            k+=1
    print(k)
    #119
    #print(123,test[4:-1],123)
    #if dop(test[4:-1]): print(dop(test[4:-1]))










input()
isu=326890
print('%6 =',isu%6,'     %4 =',isu%4,'    %7 =',isu%7,'\n')

#       =-{\
def one(test):
    regex = r'=-{\\'
    return len(re.findall(regex, test))
tests =[[r"its raining =-{\|its pouring =-{\\",2],[r"dont be =-{\\\, be =-))",1],[r'=====-----{{{{\\\\\\\\\\\\\\\\\\\\',0],[r'-={=-}-={---=+]{}=-{\\=-{\\\-188923]',2],['',0]]
for s in tests:
    time.sleep(0.5)
    test=s[0];rez=s[1]
    if one(test)==rez:
        print(rez,'    (',test,')',sep='')
    else:
        print('не работает',test,one(test))

next()



def two(test):
    regex=re.compile(r'(\s|:)+(((([0-1][0-9])|([2][0-3])):[0-5][0-9]:([0-5][0-9]))|((([0-1][0-9])|([2][0-3])):[0-5][0-9]))')
    return regex.sub('(TBD)',test)
tests=[r"22:00 abc 01:35:00",'Уважаемые студенты! В эту субботу в 15:00 планируется доп. занятие на 2 часа. То есть в 17:00:01 оно уже точно кончится. а в 23:59:00 тем более!','на самом деле времени в сутках 24:00:01','время вашей регистрации:18:35 27 декабря 2022 года', "21290:827^994300:00:0045734732:89047572736340022:99:22:22:219847:32592349:342394782396","DВрем для групп: Р12:17:00 Р14:17:30 Р14:18:00 Р5:18:30"]
for s in tests:
    time.sleep(0.5)
    print(two(s))
next()


def three(test):
    match = re.match(r'^[_\w\.]+@(\w+(\.\w+)+)$', test)
    return match.groups()[0] if match is not None else "Fail!"

tests=['students.spam@yandex.ru','ok','spam@yandex','google@google.com','@.com', 'qwertyu@qwertyu@gmail.com']

for s in tests:
    print(s,three(s),sep='  ')
    time.sleep(0.5)
input('\nработа программы завершена!')