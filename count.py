import os
counter=0;
def count(predir):
    files=os.listdir(predir);
    for f in files:
        fi=predir+'/'+f
        if os.path.isfile(fi):
            pa=os.path.splitext(f)
            if pa[1]=='.c' or pa[1]=='.cpp' or pa[1]=='.java' or pa[1]=='.py':
                readlinenum(fi)
        elif os.path.isdir(fi):
            count(fi)

def readlinenum(f):
    print f
    rf=open(f,'r')
    line=rf.readline()
    while line:
        global counter
        counter=counter+1
        line=rf.readline()

count('.')
print counter
