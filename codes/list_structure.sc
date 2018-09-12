

 
(:= (add (: x (: y (+ x y))))
 
(:= (cons (: x (: y
    (: n (if (= n 0) (x y))))))
(:= (car (: z (z 0)))
(:= (cdr (: z (z 1)))
(:= (true (' true))
(:= (false (' false))
(:= (; (: msg (: e (if true (e 0)))))


(:= (test (: self (: n
    (if (= n 0) (0
        (+ n (self self (- n 1))))))))


(:= (fac (: self (: n
    (if (= n 0) (1
        (* n (self self (- n 1))))))))
; nothing
(:= (zero (: f (: x x)))
(:= (+1 (: n (: f 
    (: x (f (n f x))))))

(:= (l (cons 2 5))

(:= (rat (: x (: y (cons x y))))
(:= (numer (: z (car z)))
(:= (denom (: z (cdr z)))

 ;(; (' test?) (10 (: a (+ a a)) (car (cons 5 5))))
 ;(' canyouseeme?)
 ;(5 (: x (+ x x)) (numer (rat 2 3)))
 (test)

)))))))))))))))













