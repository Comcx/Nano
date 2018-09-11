# (Nano (' programming language))
(-- "A new tiny programming language which pursues minimalism")

      ______  ___   ____   _______
      / _  | / _ | / _  | / __   /
     / / | |/ __ |/ / | |/ /_ / /
    / /  | /_/ |_/ /  | |\ ____/    by Comcx
<br><br><br>

## (> Getting started!
### (Quick use
   - **Step 0:**  (Pre-installs: make sure you have installed JDK whose version >= 1.8)  
   - **Step 1:**  `git clone https://github.com/Comcx/Nano` or just download from web  
   - **Step 2:**  (if (use repl) ((commandInput: `xxx$ java -jar nano.jar`) (commandInput: `xxx$ java -jar nano.jar -l <filename>`)))  
   - **Step 3:**  Enjoy it! (; "input ':q' to quit repl")  
   **)**  

## (> Grammar
  
- **(Overview:**  
  
  You may have been used to the usual format of functional call, for instance 'f(a, b)'.   
  However, in Nano, `f(a, b)` is replaced by `(f a b)` or simply `f a b`  
  Like Lisp, Nano uses lists to express any expression. Further more, Nano is curry supported by default.  
  For example:    
  `(+ 1 2)`  
  will be parsed as   
  `((+ 1) 2)`  
  **)**<br><br>
  
  
- **(:= Bindings:**  
  
  `(:= (<name> <value>) expression)`  
  **)**<br><br>
  
  
- **(: Lambdas:**  
  
  `(: <variable> <expression>)`  
  **)**<br><br>  
  
  
- **((+-*/) Supported atomic operators:**  
  
  `(; (' addition)       (+ a b))`  
  `(; (' substraction)   (- a b))`  
  `(; (' multiplication) (* a b))`  
  `(; (' division)       (/ a b)) `  
  `(; (' equal)          (= a b))`  
  `(; (' quote)          (' symbol)`  
  **)**<br><br>
  
- **(; Complements**
  
  `; whatever in a line` or you can construct by:  
  `(:= (; (: c (: e (if (' true) (e c))))))` then you can use this as:  
  `(; <complement> <expression>)`  
  
  **)**
  
  
- **(:) Interesting features:**  
  
  - ***(1. Church Numeral supported!)***   
  
    Church numeral can be used to replace loops:  
    ***0*** &nbsp; is defined as `(:= (0 (: f (: x x))))`  
    ***+1*** is defined as `(:= (+1 (: n (: f (: x (f (n f x)))))))`  
    ***1*** &nbsp; is defined as `(+1 0)`  
    ***2*** &nbsp; is defined as `(+1 1)`  
    ...  
    ***n*** &nbsp; is defined as `(+1 (n-1))` 
      
    Therefore, for example, you can define a lambda ^ to calculate power operations:  
    `(:= (^ (: n (: r ((- r 1) (: m (* m n)) n)))) (^ 2 3))` // the expression calculates power(2, 3) which is 8  
  **)**
    
  - ***???***


## (> Example  
  
  ```
  0 ; This file constructs list structure using Nano, you can build a list of (1,2,3,4,5) by typing '([] 1 2 3 4 5 0)'
  1 (:= (cons (: x (: y
  2         (: i (if (= i 0) (x y))))))
  3         
  4 (:= (car (: z (z 0)))
  5 (:= (cdr (: z (z 1)))
  6 
  7 (:= (list (: self (: x (: y
  8         (if (= y 0) ((cons y x)
  9                 (self self (cons y x))))))))
 10                 
 11 (:= ([] (list list))
 12  
 13 (:= (.first 0)
 14 (:= (.second 1)
 15 
 16 
 17  (([] 1 2 3 4 0) .second .second .first)
 18 
 19 )))))))
```

  
## (> Drawbacks  
  
  - Church numeral can't be passed as data
  - Module problem
  - For the pursuing of minimalism, float number is not supported

