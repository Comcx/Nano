# (Nano (:: Programming Language))
(-- "A new tiny programming language which pursues minimalism")
(-- latest version: Nano-0.7x)

      ______  ___   ____   _______
      / _  | / _ | / _  | / __   /
     / / | |/ __ |/ / | |/ /_ / /
    / /  | /_/ |_/ /  | |\ ____/    by Comcx

<img width="640" height="350" src="https://github.com/Comcx/Nano/blob/master/nano-logo.jpg"/>
<br><br><br>

## (> Getting started!
### (Quick use
   - **Step 0:**  (Pre-installs: make sure you have installed JDK whose version >= 1.8)  
   - **Step 1:**  `git clone https://github.com/Comcx/Nano` or just download from web  
   - **Step 2:**  (if (use repl) ((commandInput: `xxx$ java -jar nano-0.7x.jar`) (commandInput: `xxx$ java -jar nano-0.7x.jar -l <filename>`)))  
   - **Step 3:**  Enjoy it! (; "input ':q' to quit repl")  
       
     <img width="540" height="400" src="https://github.com/Comcx/Nano/blob/master/repl.JPG"/>
  
   **)**  
     
### (REPL use  
  
  - **Global environment:**  
    You can type in `def <variable> <expression>` to add variable to global environment  
    
  - **Commands:**  
    `:q` to quit repl  
    `:?` to ask for help  
    `:t` to show type info  
    `:na` to load defs  
    `:no` to load *.no files  
    ...  
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
    
  However, we have created a new format to express `(a (b c))` with less parentheses,  
  see ./codes/use_less_parentheses.no for detail.  
    
  p.s. a* => list of a  
  
  **)**<br><br>
  
  
- **(= Bindings:**  
  
  `(= (<typed name> <value>)* expression)`  
  **)**<br><br>
  
- **(: Type declarations:**  
  
  `(: <name> expression)`  
  **)**<br><br>
  
- **(def Definitions:**  
  
  `(def <name> expression)`  
  **)**<br><br>
  
  
  
- **(\ Lambdas:**  
  
  `(\ <variable>* <expression>)`  
  **)**<br><br>  
  
- **(if Conditions:**
    
  `(if <predication> <expression> <expression>)`  
  **)**
  
- **((+-*/) Supported atomic operators:**  
  
  `(; (' addition)       (+ a b))`  
  `(; (' substraction)   (- a b))`  
  `(; (' multiplication) (* a b))`  
  `(; (' division)       (/ a b))`   
  `(; (' equal)          (== a b))`  
  `(; (' atomic)         (atom x))`  
  `(; (' list)           (:: a b))`   
  `(; (' selector)       (0: <list>))`  
  `(; (' selector)       (1: <list>))`  
  `(; (' quote)          (' <expression>))`  
  ``(; (' eval)           (` <expression>))``  
  `(; (' read)         (@ <str>))`   
  **)**<br><br>
  
  
- **(< Extra operators:**
    
  `(; (' compare)    (< a b))`  
  `(; (' print)      (print <expression>))`  
  `(; (' read-file)  (read-file <url>))`  
  `(; (' write-file) (write-file <url>))`  
  
  **)**<br><br>
  
- **(; Complements:**
  
  `; whatever in a line` or you can construct by:  
  `(def (; (: c (: e (if true e c)))))` then you can use this as:  
  `(; <complement> <expression>)`  
  
  **)**  <br><br>
  
- **(" Strings:**

  `"<str content>"` which is just syntax sugar of list  

  **)**  <br><br>
  
- **(! Supported features:**  
    
    - ***(1. Lexical scoping***  
      We choose lexical scoping instead of dynamic scoping.  
        
      ***)***  
        
    - ***(2. Carbage Collection***  
      Since we write Nano using Scala on jvm, Nano supports garbage collection automatically  
      
      ***)***  
      
    - ***(3. Type System***  
      Now we have simple typed lambda system    
      
      ***)***
      
    
  **)**
  
  
- **(:) Interesting features:**  
  
  - ***(1. Church Numeral supported!***   
  
    Church numeral can be used to replace loops:  
    ***0*** &nbsp; is defined as `(def (0 (: f (: x x))))`  
    ***+1*** is defined as `(def (+1 (: n (: f (: x (f (n f x)))))))`  
    ***1*** &nbsp; is defined as `(+1 0)`  
    ***2*** &nbsp; is defined as `(+1 1)`  
    ...  
    ***n*** &nbsp; is defined as `(+1 (n-1))` 
      
    Therefore, for example, you can define a lambda ^ to calculate power operations:  
    `(= (^ (: n (: r (r (: m (* m n)) 1)))) (^ 2 3))` // the expression calculates power(2, 3) which is 8  
  **)**
    
  - ***(2. Meta programming supported!***
    
    Meta programming gives you the ability to operate program itself as data!  
    For example, define e: `(def (e (' (* x x))))`, you bind the expression itself to e.  
    then, we can define lambda square as:  
    ``(= (square (: x (` e))) (square 7))`` which calculate the square of 7.  
    
    Further more, you may have noticed the operatoer :: mentioned above, the expression `:: a b`  
    just return a new expression `(a, b)` as `(+ a b)`, it is an expression which belongs to Nano itself.  
    **)**
    
  - ***(3. Closure supported!***  
      
    ***)***  
      
      
  - ***(4. Module constructed!***  
      
      First of all, Nano don't offer module system directly for the reason that we can build it ourselves.  
      There may be hundreds of solutions, but I just list one of them and I have used it to build libraies:  
      - Module file syntax:
      `(let (' <var>) (' <expression>))`  
      - Usage:  
      First, you should use magical expressions(see `./codes/bindings_with_less_parentheses.no`)  
      import module like this:  
      ``(:= <module name> (` (@ (read-file <file name>))))``  
      For convenience, let's say `<module name>` is `foo` then  
      you can write:  
      ``(` (foo -> (' <expression>)))`` where you can use everything inside the module.  
        
      Here's one example:  
      ```
      ; magical expressions
      (= (:D (: f (: bind (bind f))))
      (= (-> (: f f))
      (= ($ (: f (: g
           (:D (: x (f (g -> x)))))))

      (= (let (: v (: e  
           (:D (: exp (:: (:: (' :=) (:: v e)) exp))))))
      
      ; import lib_list.no
      (= (import (: s (` (@ (read-file s)))))

      (= (foo_module (import "lib_list.no"))

        ; use the module
        (` (foo_module -> 
             (' (head ([ 1 2 3 4 5 ])))
        ))


      ))))))
      ```
      
    ***)***  
      
  **)**


## (> Example  
  
  ```
  0 ; This file constructs list structure using Nano, you can build a list of (1,2,3,4,5) by typing '([ 1 2 3 4 5 ])'
  1 (= (cons (: x (: y
  2         (: i (if (= i 0) (x y))))))
  3         
  4 (= (car (: z (z 0)))
  5 (= (cdr (: z (z 1)))
  6 
  7 (= (list (: self (: x (: y
  8         (if (= y (' ])) (x
  9                 (self self (cons y x))))))))
 10                 
 11 (= ([ (list list))
 12 (= (] (' ])) 
 13 (= (.first 0)
 14 (= (.second 1)
 15 
 16 
 17  (([ 1 2 3 4 ]) .second .second .first)
 18 
 19 ))))))))
```

  
## (> Drawbacks  
  
  - Church numeral can't be passed as data
  - Module problem
  - For the pursuing of minimalism, float number is not supported

