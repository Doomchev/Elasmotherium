a <= b < c <= d

push a
push b
x <= y ? 
ifFalseGoto 1
(last - 1) = last
push c
push y, x < y
ifFalseGoto 1
(last - 1) = last
push d
push y, x <= y
ifFalseGoto 1
pop 1, push true
goto 2
1: pop 1, push false
2: 