# (Nano (' programming language))
(-- "A new tiny programming language")

      ______   ___   ____   _______
      / _  |  / _ | / _  | / __   /
     / / | | / __ |/ / | |/ /_ / /
    / /  | |/_/ |_/ /  | |\ ____/    by Comcx

## (> Getting start!
### (Quick use
Step 0: (Pre-installs: make sure you have installed JDK whose version >= 1.8)  
Step 1: git clone https://github.com/Comcx/Nano  
Step 2: (input: java -jar nano.jar)  
Step 3: Enjoy it!  
**)**  

## (> Grammar  
  
**(Overview**  
  
  Like Lisp, Nano use lists to express any expression. However, Nano is curry supported by default.  
  For example:    
  `(+ 1 2)`  
  will be parsed as   
  `((+ 1) 2)`  
**)**  
  
  
**(Bindings**  
  `(:= (<name> <value>) expression)`  
**)**
  
  
**(Lambdas**  
`(: <variable> <expression>)`  
**)**  
  
  
**(Supported atomic operators**  
`(+ a b) addition`  
`(- a b) substraction`  
`(* a b) multiplication`  
`(/ a b) division`  
`(= a b) equal of numbers`  
`(' symbol) quote`  
**)**  <br><br>
  
  
**(Interesting features**  
(1 Church Numeral supported!)  
**)**




