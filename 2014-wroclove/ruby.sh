echo 'aaa' | ruby -e 'puts $<.read.upcase'
echo 'aaa' | ruby -n 'puts $_.upcase'
echo 'aaa' | ruby -n -e 'puts $_.upcase'
echo 'aaa' | ruby -p -e '$_.upcase!'

echo 'aaa\
bbb\
ccc' | ruby -pe '$_.upcase!'

echo -e 'aaa\tbbb\tccc\
ddd\teee' | ruby -nae 'puts $F.count'

echo -e 'aaa;bbb;ccc\
ddd;eee' | ruby -F';' -nae 'puts $F.count'
