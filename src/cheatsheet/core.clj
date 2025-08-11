(ns cheatsheet.core
  (:require [clojure.repl :refer [apropos dir doc find-doc pst source]]
            [clojure.string :as str]
            [clojure.java.javadoc :refer [javadoc]]
            [clojure.math :as m]
            [clojure.walk :as w]
            [clojure.data.avl :as avl]
            [flatland.ordered.set :as fl]
            [flatland.ordered.map :as fm]
            [clojure.data.int-map :as im]
            [clojure.data.priority-map :as pm]
            [clojure.set :as cl-set])
  (:gen-class))

;; ;;;;;;;;;;
;; ;; REPL ;;
;; ;;;;;;;;;;

;; ;;;;;;;;;;;;;
;; Documentation
;; ;;;;;;;;;;;;;

;; doc
;; find-doc
;; apropos
;; dir
;; source
;; pst
;; javadoc

(doc map)
; (out) -------------------------
; (out) clojure.core/map
; (out) ([f] [f coll] [f c1 c2] [f c1 c2 c3] [f c1 c2 c3 & colls])
; (out)   Returns a lazy sequence consisting of the result of applying f to
; (out)   the set of first items of each coll, followed by applying f to the
; (out)   set of second items in each coll, until any one of the colls is
; (out)   exhausted.  Any remaining items in other colls are ignored. Function
; (out)   f should accept number-of-colls arguments. Returns a transducer when
; (out)   no collection is provided.

(doc clojure.core)
; (out) -------------------------
; (out) clojure.core
; (out)   Fundamental library of the Clojure language

(find-doc "depth-first")
; (out) -------------------------
; (out) clojure.core/tree-seq
; (out) ([branch? children root])
; (out)   Returns a lazy sequence of the nodes in a tree, via a depth-first walk.
; (out)    branch? must be a fn of one arg that returns true if passed a node
; (out)    that can have children (but may not).  children must be a fn of one
; (out)    arg that returns a sequence of the children. Will only be called on
; (out)    nodes for which branch? returns true. Root is the root node of the
; (out)   tree.
; (out) -------------------------
; (out) clojure.walk/postwalk
; (out) ([f form])
; (out)   Performs a depth-first, post-order traversal of form.  Calls f on
; (out)   each sub-form, uses f's return value in place of the original.
; (out)   Recognizes all Clojure data structures. Consumes seqs as with doall.

(apropos "args")
; (clojure.core/*command-line-args*)

;; `apropos` can accept regex patterns,
;; e.g. find only definitions ending with "reduce"
(apropos #".*reduce")
; (clojure.core/areduce
;  clojure.core/ensure-reduced
;  clojure.core/reduce
;  clojure.core/reduce-kv
;  clojure.core/reduced
;  clojure.core/reduced?
;  clojure.core/unreduced
;  clojure.core.protocols/coll-reduce
;  clojure.core.protocols/internal-reduce
;  clojure.core.protocols/kv-reduce
;  clojure.core.reducers/reduce
;  clojure.core.reducers/reducer)

;; get all public definitions from all currently-loaded namespaces
(apropos #"^+")
; (arrangement.core/rank
;  cheatsheet.core/cheatsheet.core.proxy$java.lang.Object$SignalHandler$d8c00ec7
;  cheatsheet.core-test/a-test
;  cider.nrepl.pprint/pprint
;  clj-stacktrace.core/clojure-fn-subs
;  clj-stacktrace.core/parse-exception
;  clj-stacktrace.core/parse-trace-elem
;  clj-stacktrace.core/parse-trace-elems
;  clojure.core/*
;  clojure.core/*'
;  clojure.core/*1
;  clojure.core/*2
;  clojure.core/*3
;  clojure.core/*agent*
;  clojure.core/*allow-unresolved-vars*
;  clojure.core/*assert*
;  clojure.core/*clojure-version*
;  clojure.core/*command-line-args*
;  clojure.core/*compile-files*
;  ...)

(dir clojure.string)
; (out) blank?
; (out) capitalize
; (out) ends-with?
; (out) escape
; (out) includes?
; (out) index-of
; (out) join
; (out) last-index-of
; (out) lower-case
; (out) re-quote-replacement
; (out) replace
; (out) replace-first
; (out) reverse
; (out) split
; (out) split-lines
; (out) starts-with?
; (out) trim
; (out) trim-newline
; (out) triml
; (out) trimr
; (out) upper-case

(count
 (str/split-lines
  (with-out-str
    (dir clojure.string))))
; 21

;; number of public vars in clojure.core
(count
 (str/split-lines
  (with-out-str
    (dir clojure.core))))
; 671

(str/upper-case "attention")
; "ATTENTION"

(source max)
; (out) (defn max
; (out)   "Returns the greatest of the nums."
; (out)   {:added "1.0"
; (out)    :inline-arities >1?
; (out)    :inline (nary-inline 'max)}
; (out)   ([x] x)
; (out)   ([x y] (. clojure.lang.Numbers (max x y)))
; (out)   ([x y & more]
; (out)    (reduce1 max (max x y) more)))

;; (/ 1 0)
(pst)
; (err) ArithmeticException Divide by zero
; (err) 	clojure.lang.Numbers.divide (Numbers.java:190)
; (err) 	clojure.lang.Numbers.divide (Numbers.java:3911)
; (err) 	cheatsheet.core/eval4285 (form-init6311696647390071399.clj:159)
; (err) 	cheatsheet.core/eval4285 (form-init6311696647390071399.clj:159)
; (err) 	clojure.lang.Compiler.eval (Compiler.java:7194)
; (err) 	clojure.lang.Compiler.eval (Compiler.java:7149)
; (err) 	clojure.core/eval (core.clj:3215)
; (err) 	clojure.core/eval (core.clj:3211)
; (err) 	nrepl.middleware.interruptible-eval/evaluate/fn--1269/fn--1270 (interruptible_eval.clj:87)
; (err) 	clojure.core/apply (core.clj:667)
; (err) 	clojure.core/with-bindings* (core.clj:1990)
; (err) 	clojure.core/with-bindings* (core.clj:1990)

(javadoc String)

(javadoc "abc")

(javadoc 1)

;; ;;;;;
;; Other
;; ;;;;;

;; *print-dup*
;; *print-length*
;; *print-level*
;; *print-meta*
;; *print-readably*

;; Prints 500 items in nvim. In the REPL we are toast -> prints forever)
(iterate inc 0)

;; in nvim this setting is ignored
(binding [*print-length* 10]
  (iterate inc 0))
; (0 1 2 3 4 5 6 7 8 9 ...)

[1 [2 [3]]]
; [1 [2 [3]]]

;; in nvim this setting is ignored
(binding [*print-level* 2]
  [1 [2 [3]]])
;; [1 [2 #]]

(binding [*print-meta* true]
  (pr #'defmacro))
;; (out) ^{:added "1.0", :ns ^{:doc "Fundamental library of the Clojure language",
;;                             :author "Rich Hickey" #object[clojure.lang.Namespace 0x2dd56651 "clojure.core"],
;;                             :name defmacro,
;;                             :file "clojure/core.clj",
;;                             :column 1, :line 446, :macro true, :arglists ^{:line 451, :column 15} (# #), :doc "Like defn, but the resulting function name is declared as a\n  macro and will be used as a macro by the compiler when it is\n  called." #'clojure.core/defmacro}}

(binding [*print-readably* false]
  (pr "a\nb\nc"))
; (out) a
; (out) b
; (out) c

(binding [*print-readably* true]
  (pr "a\nb\nc"))
; (out) "a\nb\nc"

;; `*print-dup*` is very handy when we want to write
;; clojure code/data to a file to read in later.

;; here we are using only strings for input and output
;; see for a version with file persistence:
;; https://clojuredocs.org/clojure.core/*print-dup*#example-542692cec026201cdc326da7
(defn serialize
  [data-structure]
  (with-out-str
    (binding [*print-dup* true]
      (prn data-structure))))

;; this allows us to then read in the structure at a later time
(defn deserialize [str]
  (read-string str))

(let [data-structure {:name "Fred", :age "23"}
      serialized (serialize data-structure)]
  (println serialized)
  (deserialize serialized))
; (out) #=(clojure.lang.PersistentArrayMap/create {:name "Fred", :age "23"})
; (out) {:name "Fred", :age "23"} when *print-dup* set to `false`:
; {:name "Fred", :age "23"} ; results in this simple case with strings are the same

;; ;;;;;;;;;;;;;;;;
;; ;; Primitives ;;
;; ;;;;;;;;;;;;;;;;

;; ;;;;;;;;;;;
;; ; Numbers ;
;; ;;;;;;;;;;;

;; ;;;;;;;;
;; Literals
;; ;;;;;;;;

7         ;; Long https://docs.oracle.com/javase/8/docs/api/java/lang/Long.html
0xff      ;; hex     => 255
017       ;; oct     => 15
2r1011    ;; base 2  => 11
36rCRAZY  ;; base 36 => 21429358
7N        ;; BigInt
22/7      ;; Ratio
2.78      ;; Double https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html
1.2e-5
4.2M      ;; BigDecimal https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html

;; ;;;;;;;;;;
;; Arithmetic
;; ;;;;;;;;;;

;; +
;; -
;; *
;; /
;; quot
;; rem
;; mod
;; inc
;; dec
;; max
;; min
;; +'
;; -'
;; *'
;; inc'
;; dec'
;; abs
;; m/floor-div
;; m/floor-mod
;; m/ceil
;; m/floor
;; m/rint
;; m/round
;; m/pow
;; m/sqrt
;; m/cbrt
;; m/E
;; m/exp
;; m/expm1
;; m/log
;; m/log10
;; m/log1p
;; m/PI
;; m/sin
;; m/cos
;; m/tan
;; m/asin
;; m/acos
;; m/atan
;; m/atan2

(+)                     ; 0
(+ 2 3 5 7 11)          ; 28
(- 2 3 5 7 11)          ; -24
(*)                     ; 1
(* 2 3 5 7 11)          ; 2310
(/ 2 3 5 7 11)          ; 2/1155
(quot 20 3)             ; 6
(rem 20 3)              ; 2
(mod 20 3)              ; 2
(mod -20 3)             ; 1
(inc 1)                 ; 2
(dec 1)                 ; 0
(max 2 3 5 7 11)        ; 11
(min 2 3 5 7 11)        ; 2
(+')                    ; 0
(+' 2 3 5 7 11)         ; 28 (arbitrary precision)
(-' 2 3 5 7 11)         ; -24 (arbitrary precision)
(*')                    ; 1
(*' 2 3 5 7 11)         ; 2310 (arbitrary precision)
(inc' 1)                ; 2 (arbitrary precision)
(dec' 1)                ; 0 (arbitrary precision)
(dec' Long/MIN_VALUE)   ; auto-promotes on integer overflow => -9223372036854775809N
;; (dec Long/MIN_VALUE)
;; (err) Execution error (ArithmeticException)
(abs -3.14159)          ; 3.14159
(m/floor-div 20 -6)     ; -4 (rounds to negative infinity)
(m/floor-mod 20 -6)     ; -4
(m/ceil -6.4)           ; -6.0
(m/floor -6.4)          ; -7.0
(m/rint 10.5)           ; 10.0 (if two values are equally close, return the EVEN(!!!) one.)
(m/rint 11.5)           ; 12.0
(m/round 11.5)          ; 12
(m/pow 2 3)             ; 8.0
(m/sqrt 25)             ; 5.0
(m/cbrt 125)            ; 5.0
m/E                     ; 2.718281828459045
(m/exp 2)               ; 7.38905609893065
(m/expm1 2)             ; 6.38905609893065
(m/log m/E)             ; 1.0
(m/log10 10)            ; 1.0
(m/log10 100)           ; 2.0
(m/log1p (- m/E 1))     ; 1.0
m/PI                    ; 3.141592653589793
(m/sin m/PI)            ; 1.2246467991473532E-16
(m/cos m/PI)            ; -1.0
(m/tan m/PI)            ; -1.2246467991473532E-16
(m/asin 1.0)            ; 1.5707963267948966
(/ m/PI 2)              ; 1.5707963267948966
(m/acos 1.0)            ; 0.0
(m/atan 1.0)            ; 0.7853981633974483
(m/atan2 1.0 1.0)       ; 0.7853981633974483
(/ m/PI 4)              ; 0.7853981633974483

;; ;;;;;;;
;; Compare
;; ;;;;;;;

;; ==
;; <
;; >
;; <=
;; >=
;; compare

(compare 4 4) ;  0
(compare 4 3) ;  1
(compare 3 4) ; -1

(compare [1 2 3] [4 5 6])    ; -1
(compare [4 5 6] [1 2 3])    ;  1
(compare [1 2 3] [1 2 3])    ;  0
(compare [1 2 3 4] [5 6 7])  ;  1
(compare [5 6 7] [1 2 3 4])  ; -1

(== 4 3)  ; false
(== 3 3)  ; true
(< 4 3)   ; false
(> 4 3)   ; true
(<= 4 3)  ; false
(>= 4 3)  ; true

;; ;;;;;;;
;; Bitwise
;; ;;;;;;;

;; bit-and
;; bit-or
;; bit-xor
;; bit-not
;; bit-flip
;; bit-set
;; bit-shift-right
;; bit-shift-left
;; bit-and-not
;; bit-clear
;; bit-test
;; unsigned-bit-shift-right

;; ;;;;
;; Cast
;; ;;;;

(byte 2)          ; 2
(short 2)         ; 2
(int 2)           ; 2
(long 2)          ; 2
(float 2)         ; 2.0
(double 2)        ; 2.0
(bigdec 2)        ; 2M
(bigint 2)        ; 2N
(num 22.0)        ; 22.0
(rationalize 2.2) ; 11/5
(biginteger 2)    ; 2

;; ;;;;
;; Test
;; ;;;;

(zero? 0)               ; true
(pos? 1)                ; true
(neg? -1)               ; true
(even? 2)               ; true
(odd? 1)                ; true
(number? 1)             ; true
(rational? (m/sqrt 3))  ; false
(integer? 2.2)          ; false
(ratio? 2.2)            ; false
(decimal? 2M)           ; true
(float? 2)              ; false
(double? 2)             ; false
(int? 2)                ; true
(nat-int? 2)            ; true
(neg-int? -2)           ; true
(pos-int? 2)            ; true
(NaN? (Math/sqrt -1))   ; true
(infinite? (/ 1.0 0.0)) ; true

;; ;;;;;;
;; Random
;; ;;;;;;

(rand)        ; 0.5333524422648103
(rand-int 10) ; 7
(m/random)    ; 0.4264921204096218 (random distribution)

;; ;;;;;;;;;;
;; BigDecimal
;; ;;;;;;;;;;

(with-precision 10 (/ 1M 6)) ; 0.1666666667M
(with-precision 20 (/ 1M 6)) ; 0.16666666666666666667M

;; ;;;;;;;;;
;; Unchecked
;; ;;;;;;;;;

;; *unchecked-math*
;; unchecked-add
;; unchecked-dec
;; unchecked-inc
;; unchecked-multiply
;; unchecked-negate
;; unchecked-subtract

;; (set! *unchecked-math* false)
;; (+ Long/MAX_VALUE 1)
;; (err) long overflow

Long/MAX_VALUE               ; 9223372036854775807
Long/MIN_VALUE               ; -9223372036854775808
(set! *unchecked-math* true) ; true
(+ Long/MAX_VALUE 1)         ; -9223372036854775808

(set! *unchecked-math* false)         ; false
(unchecked-add Long/MAX_VALUE 1)      ; -9223372036854775808
(unchecked-dec Long/MIN_VALUE)        ; 9223372036854775807
(unchecked-inc Long/MAX_VALUE)        ; -9223372036854775808
(unchecked-multiply Long/MAX_VALUE 2) ; -2
(unchecked-negate Long/MAX_VALUE)     ; -9223372036854775807
(unchecked-negate Long/MIN_VALUE)     ; -9223372036854775808
(unchecked-subtract Long/MIN_VALUE 1) ; 9223372036854775807

;; ;;;;;;;;;;;
;; ; Strings ;
;; ;;;;;;;;;;;

;; ;;;;;;
;; Create
;; ;;;;;;

;; "a string"
;; str
;; format

"a string"          ; "a string"
(str 1)             ; "1"
(str 1 2 3)         ; "123"
(str [1 2 3])       ; "[1 2 3]"
(apply str [1 2 3]) ; "123"
(format "%5d" 3)    ; "    3" (google for "java format specifiers")

;; ;;;
;; Use
;; ;;;

;; count
;; get
;; subs
;; compare
;; parse-boolean
;; parse-double
;; parse-long
;; parse-uuid
;; str/replace
;; str/reverse
;; str/join
;; str/escape
;; str/split
;; str/split-lines
;; str/replace-first
;; str/index-of
;; str/last-index-of

(count "string")                  ; 6
(get "string" 0)                  ; \s
(subs "string" 0 3)               ; "str"
(compare "string" "zeichenkette") ; -7 (distance between the first characters)

(str/join [1 2 3])                                              ; "123"
(str/join ", " [1 2 3])                                         ; "1, 2, 3"
(str/escape
 "I want 1 < 2 as HTML, & other good things."
 {\< "&lt;", \> "&gt;", \& "&amp;"})                            ; "I want 1 &lt; 2 as HTML, &amp; other good things."
(str/split "Clojure is awesome!" #" ")                          ; ["Clojure" "is" "awesome!"]
(str/split "q1w2e3r4t5y6u7i8o9p0" #"\d+")                       ; ["q" "w" "e" "r" "t" "y" "u" "i" "o" "p"]
(str/split-lines "test\nstring")                                ; ["test" "string"]
(str/replace "The color is red" #"red" "blue")                  ; "The color is blue"
(str/replace-first "A good day, sir. Good day." #"day" "night") ; "A good night, sir. Good day."
(str/reverse (str/join (map char (range 97 (+ 97 25)))))        ; "yxwvutsrqponmlkjihgfedcba"
(str/index-of "ababc" "abc")                                    ; 2
(str/index-of "ababc" "abc" 3)                                  ; nil
(str/last-index-of "ababc" "ab")                                ; 2
(str/last-index-of "ababc" "b")                                 ; 3

(parse-boolean "true")                              ; true
(parse-double "232.234")                            ; 232.234
(parse-long "234234")                               ; 234234
(parse-uuid "b6883c0a-0342-4007-9966-bc2dfa6b109e") ; #uuid "b6883c0a-0342-4007-9966-bc2dfa6b109e"

;; ;;;;;
;; Regex
;; ;;;;;

;; #"pattern"
;; re-find
;; re-seq
;; re-matches
;; re-pattern
;; re-matcher
;; re-groups
;; str/replace
;; str/replace-first
;; str/re-quote-replacement

;; only first match
(re-find #"\d+" "672-345-456-3212") ; "672"

;; with a matcher
(let [*matcher* (re-matcher #"\d+" "abc12345def")]
  (re-find *matcher*)) ; "12345"

;; easier if you want to find only first match
(re-find #"\d+" "abc12345def")

;; with a matcher we can find all matches
(let [phone-number "672-345-456-3212"
      matcher (re-matcher #"\d+" phone-number)]
  (println (re-find matcher))
  (println (re-find matcher))
  (println (re-find matcher))
  (println (re-find matcher)))
; (out) 672
; (out) 345
; (out) 456
; (out) 3212

;; get a sequence of matches
(re-seq #"\w+" "mary had a little lamb") ; ("mary" "had" "a" "little" "lamb")

;; match the whole string
(re-matches #"hello" "hello, world") ; nil
(re-matches #"hello.*" "hello, world") ; "hello, world"

;; `re-pattern` creates a regex from string (might be useful for regex composition)
(re-find (re-pattern "\\d+") "abc123def")  ; "123"

;; groups
(let [phone-number "672-345-456-3212"
      matcher (re-matcher #"((\d+)-(\d+))" phone-number)]
  (println (re-find matcher))     ; `re-find` uses `re-groups`
  (println (re-find matcher)))
; (out) [672-345 672-345 672 345]
; (out) [456-3212 456-3212 456 3212]

(let [s "May 2018, June 2019"]
  ;; (str/replace s #"May|June" "$10")                             ; (err) No group 1
  (str/replace s #"May|June" (str/re-quote-replacement "$10 in"))) ; "$10 in 2018, $10 in 2019"

;; ;;;;;;;
;; Letters
;; ;;;;;;;

;; str/capitalize
;; str/lower-case
;; str/upper-case

;; (clojure.string/) capitalize lower-case upper-case

(str/lower-case "mIxEd CaSe") ; "mixed case"
(str/upper-case "mIxEd CaSe") ; "MIXED CASE"
(str/capitalize "mIxEd CaSe") ; "Mixed case"

;; ;;;;
;; Trim
;; ;;;;

;; str/trim
;; str/trim-newline
;; str/triml
;; str/trimr

(str/trim "     a      ") ; "a"
(str/triml "     a      ") ; "a      "
(str/trimr "     a      ") ; "     a"
(str/trim-newline "test\n\r") ; "test"
(str/trim-newline "test\n")  ; "test"
(str/trim-newline "test\n\test") ; "test\n\test"

;; ;;;;
;; Test
;; ;;;;

;; string?
;; str/blank?
;; str/starts-with?
;; str/ends-with?
;; str/includes?

(string? "abc") ; true

(str/blank? "abc") ; false
(str/blank? "")    ; true
(str/blank? "   ") ; true
(str/blank? nil)   ; true
(str/blank? "\n")  ; true

(str/starts-with? "abcde" "a")   ; true
(str/starts-with? "abcde" "ab")  ; true
(str/starts-with? "abcde" "abc") ; true

(str/ends-with? "abcde" "e")     ; true
(str/ends-with? "abcde" "de")    ; true
(str/ends-with? "abcde" "cde")   ; true

(str/includes? "abcde" "bcd")    ; true

;; ;;;;;;;;;
;; ; Other ;
;; ;;;;;;;;;

;; ;;;;;;;;;;
;; Characters
;; ;;;;;;;;;;

;; char
;; char?
;; char-name-string
;; char-escape-string

(char 97) ; \a

(char? \a)            ; true
(char? (first "abc")) ; true
(char? "a")           ; false
(char? 97)            ; false

(map char-name-string [\backspace \tab \newline \formfeed \return \space])
; ("backspace" "tab" "newline" "formfeed" "return" "space")

(map char-escape-string [\backspace \tab \newline \formfeed \return \space])
; ("\\b" "\\t" "\\n" "\\f" "\\r" nil)

;; ;;;;;;;;
;; Keywords
;; ;;;;;;;;

;; keyword
;; keyword?
;; find-keyword

(keyword "foo")           ; :foo
(keyword "user" "foo")    ; :user/foo

(keyword? 'foo)           ; false
(keyword? :foo)           ; true
(keyword? :user/foo)      ; true

(find-keyword "foo")      ; :foo
(find-keyword "user/foo") ; :user/foo

;; ;;;;;;;
;; Symbols
;; ;;;;;;;

;; symbol
;; symbol?
;; gensym

(symbol "clojure.core" "foo") ; clojure.core/foo
(symbol "foo")                ; foo

(symbol? 'a) ; true
(symbol? 1)  ; false
(symbol? :a) ; false

(gensym)       ; G__4285
(gensym "foo") ; foo4288

;; auto-gensym (in syntax quoting; generates same symbol)
`(foo#) ; (foo__4289__auto__)
`(foo#) ; (foo__4293__auto__)

;; ;;;;;;;;;;;;;;;
;; Misc - literals
;; ;;;;;;;;;;;;;;;

true
false
nil

;; ;;;;;;;;;;;;;;;;;
;; ;; Collections ;;
;; ;;;;;;;;;;;;;;;;;

;; ;;;;;;;;;;;;;;;
;; ; Collections ;
;; ;;;;;;;;;;;;;;;

;; ;;;;;;;;;;;
;; Generic ops
;; ;;;;;;;;;;;

;; count
;; bounded-count
;; empty
;; not-empty
;; into
;; conj
;; w/walk
;; w/prewalk
;; w/prewalk-demo
;; w/prewalk-replace
;; w/postwalk
;; w/postwalk-demo
;; w/postwalk-replace
;; w/prewalk-replace
;; w/postwalk
;; w/postwalk-demo
;; w/postwalk-replace

(count {:b 2, :c 3, :a 1, :d {:d 10, :c 3, :a 11, :b 22}})           ; 4
(counted? {:b 2, :c 3, :a 1, :d {:d 10, :c 3, :a 11, :b 22}})        ; true
(bounded-count 3 {:b 2, :c 3, :a 1, :d {:d 10, :c 3, :a 11, :b 22}}) ; 4
(bounded-count 3 (map identity [1 2 3 4 5 6]))                       ; 3

(empty '(1 2)) ; ()
(empty  [1 2]) ; []
(empty  {1 2}) ; {}
(empty #{1 2}) ; #{}

(not-empty [1 3 5]) ; [1 3 5]
(not-empty [])      ; nil

(into () '(1 2 3))      ; (3 2 1)
(into () [1 2 3])       ; (3 2 1)
(into [] '(1 2 3))      ; [1 2 3]
(into [1 2 3] '(4 5 6)) ; [1 2 3 4 5 6]

(conj '(3 2 1) 4) ; (4 3 2 1)
(conj  [1 2 3] 4) ; [1 2 3 4]

(conj {:firstname "John" :lastname "Doe"} {:age 25 :nationality "Chinese"})
; {:firstname "John", :lastname "Doe", :age 25, :nationality "Chinese"}

(w/walk #(* 2 %) #(apply + %) [1 2 3 4 5])       ; 30
(w/walk #(* 2 %) (partial reduce +) [1 2 3 4 5]) ; 30

(w/prewalk-demo {:b 2, :c 3, :a 1, :d {:d 10, :c 3, :a 11, :b 22}})
; (out) Walked: {:b 2, :c 3, :a 1, :d {:d 10, :c 3, :a 11, :b 22}}
; (out) Walked: [:b 2]
; (out) Walked: :b
; (out) Walked: 2
; (out) Walked: [:c 3]
; (out) Walked: :c
; (out) Walked: 3
; (out) Walked: [:a 1]
; (out) Walked: :a
; (out) Walked: 1
; (out) Walked: [:d {:d 10, :c 3, :a 11, :b 22}]
; (out) Walked: :d
; (out) Walked: {:d 10, :c 3, :a 11, :b 22}
; (out) Walked: [:d 10]
; (out) Walked: :d
; (out) Walked: 10
; (out) Walked: [:c 3]
; (out) Walked: :c
; (out) Walked: 3
; (out) Walked: [:a 11]
; (out) Walked: :a
; (out) Walked: 11
; (out) Walked: [:b 22]
; (out) Walked: :b
; (out) Walked: 22

;; sort nested maps
(let [m {:b 2, :c 3, :a 1, :d {:d 10, :c 3, :a 11, :b 22}}
      f (fn [item]
          (println item)
          (if (map? item)
            (into (sorted-map) item)
            item))]
  (w/prewalk f m))
; {:a 1, :b 2, :c 3, :d {:a 11, :b 22, :c 3, :d 10}}

(let [m {:b 2, :c 3, :a 1, :d {:d 10, :c 3, :a 11, :b 22}}
      f (fn [item]
          (println item)
          (if (number? item)
            (* 2 item)
            item))]
  (w/prewalk f m))
; {:b 4, :c 6, :a 2, :d {:d 20, :c 6, :a 22, :b 44}}

(w/postwalk-demo {:b 2, :c 3, :a 1, :d {:d 10, :c 3, :a 11, :b 22}})
; (out) Walked: :b
; (out) Walked: 2
; (out) Walked: [:b 2]
; (out) Walked: :c
; (out) Walked: 3
; (out) Walked: [:c 3]
; (out) Walked: :a
; (out) Walked: 1
; (out) Walked: [:a 1]
; (out) Walked: :d
; (out) Walked: :d
; (out) Walked: 10
; (out) Walked: [:d 10]
; (out) Walked: :c
; (out) Walked: 3
; (out) Walked: [:c 3]
; (out) Walked: :a
; (out) Walked: 11
; (out) Walked: [:a 11]
; (out) Walked: :b
; (out) Walked: 22
; (out) Walked: [:b 22]
; (out) Walked: {:d 10, :c 3, :a 11, :b 22}
; (out) Walked: [:d {:d 10, :c 3, :a 11, :b 22}]
; (out) Walked: {:b 2, :c 3, :a 1, :d {:d 10, :c 3, :a 11, :b 22}}

(w/postwalk-demo {:a 1 :b 2})
; (out) Walked: :a
; (out) Walked: 1
; (out) Walked: [:a 1]
; (out) Walked: :b
; (out) Walked: 2
; (out) Walked: [:b 2]
; (out) Walked: {:a 1, :b 2}

;; sort nested maps
(let [m {:b 2, :c 3, :a 1, :d {:d 10, :c 3, :a 11, :b 22}}]
  (w/postwalk (fn [x]
                (if (map? x)
                  (into (sorted-map) x)
                  x)) m))
; {:a 1, :b 2, :c 3, :d {:a 11, :b 22, :c 3, :d 10}}

(w/prewalk-replace {:a 1 :b 2} [:a :b])             ; [1 2]
(w/prewalk-replace {:a 1 :b 2} [:a :b :c])          ; [1 2 :c]
(w/prewalk-replace {:a 1 :b 2} [:a :b [:a :b] :c])  ; [1 2 [1 2] :c]

(w/postwalk-replace {:a 1 :b 2} [:a :b])            ; [1 2]
(w/postwalk-replace {:a 1 :b 2} [:a :b :c])         ; [1 2 :c]
(w/postwalk-replace {:a 1 :b 2} [:a :b [:a :b] :c]) ; [1 2 [1 2] :c]

;; ;;;;;;;;;;;;;
;; Content tests
;; ;;;;;;;;;;;;;

;; distinct?
;; empty?
;; every?
;; not-every?
;; some
;; not-any?

(distinct? 1 2 3)   ; true
(distinct? 1 2 3 1) ; false

(empty? ())   ; true
(empty? '(1)) ; false

(every? even? '(2 4 6)) ; true
(every? even? '(1 2 3)) ; false

(not-every? odd? '(1 2 3)) ; true
(not-every? odd? '(1 3))   ; false

(some even? '(1 2 3 4)) ; true
(some even? '(1 3 5 7)) ; nil

(not-any? odd? '(2 4 6)) ; true
(not-any? odd? '(1 2 3)) ; false

;; ;;;;;;;;;;;;
;; Capabilities
;; ;;;;;;;;;;;;

;; sequential?
;; associative?
;; sorted?
;; counted?
;; reversible?

(sequential? '(1 2 3))    ; true
(sequential? [1 2 3])     ; true
(sequential? (range 1 5)) ; true
(sequential? '())         ; true
(sequential? [])          ; true
(sequential? nil)         ; false
(sequential? 1)           ; false
(sequential? "abc")       ; false
(sequential? {:a 2 :b 1}) ; false
(sequential? #{1 2 3})    ; false

(associative? {:a 1 :b 2}) ; true
(associative? [1 2 3])     ; true
(associative? '(1 2 3))    ; false
(associative? #{:a :b :c}) ; false
(associative? "fred")      ; false

(sorted? (sorted-set 5 3 1 2 4))      ; true
(sorted? (sorted-map :a 1 :c 3 :b 2)) ; true
(sorted? [1 2 3 4 5])                 ; false

(counted? [:a :b :c])       ; true
(counted? '(:a :b :c))      ; true
(counted? {:a 1 :b 2 :c 3}) ; true
(counted? #{:a :b :c})      ; true
(counted? "asdf")           ; false

(reversible? [])           ; true
(reversible? (sorted-map)) ; true
(reversible? (sorted-set)) ; true
(reversible? '())          ; false
(reversible? {})           ; false
(reversible? #{})          ; false

;; ;;;;;;;;;;
;; Type tests
;; ;;;;;;;;;;

;; coll?
;; list?
;; vector?
;; set?
;; map?
;; seq?
;; record?
;; map-entry?

(coll? {})           ; true
(coll? #{})          ; true
(coll? [])           ; true
(coll? '())          ; true
(coll? (seq "fred")) ; true
(coll? 4)            ; false
(coll? "fred")       ; false
(coll? true)         ; false
(coll? nil)          ; false

(list? '(1 2 3))   ; true
(list? 0)          ; false
(list? {})         ; false
(list? [])         ; false
(list? (range 10)) ; false

(vector? [1 2 3])                  ; true
(vector? (vec '(1 2 3)))           ; true
(first {:a 1 :b 2 :c 3})           ; [:a 1]
(vector? (first {:a 1 :b 2 :c 3})) ; true
(vector? '(1 2 3))                 ; false
(vector? {:a 1 :b 2 :c 3})         ; false
(vector? #{:a :b :c})              ; false

(set? #{1 2 3})           ; true
(set? (hash-set 1 2 3))   ; true
(set? (sorted-set 1 2 3)) ; true
(set? [1 2 3])            ; false
(set? {:a 1 :b 2})        ; false

(map? {:a 1 :b 2 :c 3})       ; true
(map? (hash-map :a 1 :b 2))   ; true
(map? (sorted-map :a 1 :b 2)) ; true
(map? (array-map :a 1 :b 2))  ; true
(map? '(1 2 3))               ; false
(map? #{:a :b :c})            ; false

;; https://insideclojure.org/2015/01/02/sequences/
(seq? '(1 2 3))       ; true
(seq? (range 1 5))    ; true
(seq? #{1 2 3})       ; false
(seq? [1 2 3])        ; false
(sequential? [1 2 3]) ; true (NOTE:)
(seq? 1)              ; false
(seq? {:a 2 :b 1})    ; false

(defrecord R [x])
(def r (->R 1))
r                       ; {:x 1}
(record? r)             ; true
(def r2 (assoc r :y 2))
(record? r2)            ; true
(record? {:x 1})        ; false

(map-entry? (first {:a 1 :b 2})) ; true

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ; Lists (conj, pop, peek at beginning) ;
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; ;;;;;;
;; Create
;; ;;;;;;

;; ()
;; list
;; list*

'(a b c d e f g)            ; (a b c d e f g)
'(1 2 3)                    ; (1 2 3)

(list 'a 'b 'c 'd 'e 'f 'g) ; (a b c d e f g)
(list 1 2 3)                ; (1 2 3)

(list  1 [2 3])   ; (1 [2 3])
(list* 1 [2 3])   ; (1 2 3)
(list  1 2 [3 4]) ; (1 2 [3 4])
(list* 1 2 [3 4]) ; (1 2 3 4)

;; ;;;;;;;
;; Examine
;; ;;;;;;;

;; first
;; nth
;; peek
;; .indexOf
;; .lastIndexOf

(first '(:alpha :bravo :charlie)) ; :alpha

(nth ["a" "b" "c" "d"] 0) ; "a"
(nth ["a" "b" "c" "d"] 1) ; "b"
(nth ["a" "b" "c" "d"] 2) ; "c"
(nth ["a" "b" "c" "d"] 3) ; "d"
;; (nth ["a" "b" "c" "d"] 4) ; (err) Execution error (IndexOutOfBoundsException)

(def large-vec (vec (range 0 10000)))
(time (last large-vec)) ; 9999
; (out) "Elapsed time: 0.869513 msecs"
(time (peek large-vec)) ; 9999
; (out) "Elapsed time: 0.224653 msecs"

(.indexOf     ["a" "b" "c" "d"]                 "b") ; 1
(.lastIndexOf ["a" "b" "c" "d" "a" "b" "c" "d"] "b") ; 5

;; ;;;;;;
;; Change
;; ;;;;;;

;; cons
;; conj
;; rest
;; pop

(cons 1 '(2 3 4 5 6)) ; (1 2 3 4 5 6)
(cons 1 [2 3 4 5 6])  ; (1 2 3 4 5 6)

(conj  [1 2 3] 4) ; [1 2 3 4]
(conj '(3 2 1) 4) ; (4 3 2 1)

(rest  [1 2 3 4 5])           ; (2 3 4 5)
(rest '(1 2 3 4 5))           ; (2 3 4 5)
(rest  ["a" "b" "c" "d" "e"]) ; ("b" "c" "d" "e")
(rest '("a" "b" "c" "d" "e")) ; ("b" "c" "d" "e")

(pop  [1 2 3]) ; [1 2]
(pop '(1 2 3)) ; (2 3)

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ; Vectors (conj, pop, peek at end) ;
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; ;;;;;;
;; Create
;; ;;;;;;

;; []
;; vector
;; vec
;; vector-of
;; mapv
;; filterv

[1 2 3]                    ; [1 2 3]
(vector 1 2 3)             ; [1 2 3] (the same)
(vec '(1 2 3))             ; [1 2 3]
(vector-of :int 1 2 3)     ; [1 2 3]
(mapv inc '(1 2 3 4 5))    ; [2 3 4 5 6]
(filterv even? (range 10)) ; [0 2 4 6 8]

;; ;;;;;;;
;; Examine
;; ;;;;;;;

;; (my-vec idx) → (nth my-vec idx)
;; get
;; peek
;; .indexOf
;; .lastIndexOf

([1 2 3] 1)     ; 2
(get [1 2 3] 1) ; 2
(let [large-vec (vec (range 0 10000))]
  (time (last large-vec))
  (time (peek large-vec)))
; (out) "Elapsed time: 0.514349 msecs"
; (out) "Elapsed time: 0.004688 msecs"
(.indexOf [1 2 3] 2)       ; 1
(.lastIndexOf [1 2 3 2] 2) ; 3

;; ;;;;;;
;; Change
;; ;;;;;;

;; assoc
;; assoc-in
;; pop
;; subvec
;; replace
;; conj
;; rseq
;; update
;; update-in

(assoc nil :key1 4)                         ; {:key1 4}
(assoc {}
       :key1 "value"
       :key2 "another value")               ; {:key1 "value", :key2 "another value"}
(assoc [1 2 3] 0 10)                        ; [10 2 3]

(peek [1 2 3])                              ; 3
(pop [1 2 3])                               ; [1 2]
(let [users [{:name "James" :age 26}
             {:name "John"  :age 43}]]
  (assoc-in users [1 :age] 88))             ; [{:name "James", :age 26}
                                            ;  {:name "John",  :age 88}))
(subvec [1 2 3 4 5 6 7] 2)                  ; [3 4 5 6 7]
(subvec [1 2 3 4 5 6 7] 2 4)                ; [3 4]
(replace [10 9 8 7 6] [0 2 4])              ; [10 8 6]
(replace {2 :two, 4 :four} [0])             ; [0]
(replace {2 :two, 4 :four} [1])             ; [1]
(replace {2 :two, 4 :four} [2])             ; [:two]
(replace {2 :two, 4 :four} [3])             ; [3]
(replace {2 :two, 4 :four} [4])             ; [:four]
(replace {2 :two, 4 :four} [4 2 3 4 5 6 2]) ; [:four :two 3 :four 5 6 :two]
(conj  [1 2 3] 4)                           ; [1 2 3 4]
(conj '(1 2 3) 4)                           ; (4 1 2 3)
(rseq (vec (range 10)))                     ; (9 8 7 6 5 4 3 2 1 0)
(rseq (into (sorted-map) {:a 1 :b 2}))      ; ([:b 2] [:a 1])
(let [nums (vec (range 1000000))]
  (time (reverse nums))
  (time (rseq nums)))
; (out) "Elapsed time: 26.456683 msecs"
; (out) "Elapsed time: 0.006211 msecs"
(let [p {:name "James" :age 26}]
  (update p :age inc))                      ; {:name "James", :age 27}
(let [users [{:name "James" :age 26}  {:name "John" :age 43}]]
  (update-in users [1 :age] inc))           ; [{:name "James", :age 26}
                                            ;  {:name "John",  :age 44}))

;; ;;;
;; Ops
;; ;;;

(reduce-kv #(assoc %1 %3 %2) {} {:a 1 :b 2 :c 3})  ; {1 :a, 2 :b, 3 :c}
(cl-set/map-invert {:a 1 :b 2 :c 3})               ; {1 :a, 2 :b, 3 :c}

;; reduce-kv

;; ;;;;;;;;
;; ; Sets ;
;; ;;;;;;;;

;; ;;;;;;;;;;;;;;;
;; Create unsorted
;; ;;;;;;;;;;;;;;;

;; #{}
;; set
;; hash-set

(set '(1 1 2 3 2 4 5 5))    ; #{1 4 3 2 5}
(set  [1 1 2 3 2 4 5 5])    ; #{1 4 3 2 5}
;; #{1 1 2 3 2 4 5 5}       ; (err) Duplicate key
(hash-set 1 1 2 3 2 4 5 5)  ; #{1 4 3 2 5}

;; ;;;;;;;;;;;;;
;; Create sorted
;; ;;;;;;;;;;;;;

;; sorted-set
;; sorted-set-by
;; avl/sorted-set
;; avl/sorted-set-by
;; fl/ordered-set
;; im/int-set
;; im/dense-int-set

(sorted-set 3 2 1)          ; #{1 2 3}
(sorted-set 3 2 1 1)        ; #{1 2 3}
(apply sorted-set #{2 1 3}) ; #{1 2 3}
(sorted-set-by > 3 5 8 2 1) ; #{8 5 3 2 1}
(avl/sorted-set 0 1 2)      ; #{0 1 2}
(avl/sorted-set-by > 0 1 2) ; #{2 1 0}
(fl/ordered-set 4 3 1 8 2)  ; #{4 3 1 8 2}
(let [s (range 1e6)]
  (time (into #{} s))                 ; ~100mb
  (time (into (im/int-set) s))        ; ~1mb
  (time (into (im/dense-int-set) s))) ; ~150kb
; (out) "Elapsed time: 459.188488 msecs"
; (out) "Elapsed time: 343.193301 msecs"
; (out) "Elapsed time: 222.736581 msecs"

;; ;;;;;;;
;; Examine
;; ;;;;;;;

;; (my-set item) → ( get my-set item)
;; contains?

(contains? (sorted-set 3 2 1) 1) ; true
(get       (sorted-set 3 2 1) 1) ; 1
((sorted-set 3 2 1) 1)           ; 1

(contains? (sorted-set 3 2 1) 4) ; false
(get       (sorted-set 3 2 1) 4) ; nil
((sorted-set 3 2 1) 4)           ; nil

;; ;;;;;;
;; Change
;; ;;;;;;

;; conj
;; disj

(conj #{1 3 4} 2)   ; #{1 4 3 2}
(conj #{1 3 4} 2 5) ; #{1 4 3 2 5}

(disj #{1 2 3})     ; #{1 3 2}
(disj #{1 2 3} 2)   ; #{1 3}
(disj #{1 2 3} 4)   ; #{1 3 2}
(disj #{1 2 3} 1 3) ; #{2}

;; ;;;;;;;
;; Set ops
;; ;;;;;;;

;; (clojure.set/)
;; cl-set/union
;; cl-set/difference
;; cl-set/intersection
;; cl-set/select

(cl-set/union)                      ; #{}
(cl-set/union #{1 2})               ; #{1 2}
(cl-set/union #{1 2} #{2 3})        ; #{1 3 2}
(cl-set/union #{1 2} #{2 3} #{3 4}) ; #{1 4 3 2}

(cl-set/difference #{1 2 3})                  ; #{1 3 2}
(cl-set/difference #{1 2} #{2 3})             ; #{1}
(cl-set/difference #{1 2 3} #{1} #{1 4} #{3}) ; #{2}

(cl-set/intersection #{1})                  ; #{1}
(cl-set/intersection #{1 2} #{2 3})         ; #{2}
(cl-set/intersection #{1 2} #{2 3} #{3 4})  ; #{}
(cl-set/intersection #{1 :a} #{:a 3} #{:a}) ; #{:a}

(cl-set/select odd? #{1 2 3}) ; #{1 3}

;; ;;;;
;; Test
;; ;;;;

;; (clojure.set/)
;; subset?
;; superset?

(cl-set/subset? #{2 3} #{1 2 3 4}) ; true
(cl-set/subset? #{2 4} #{1 2 3 4}) ; true
(cl-set/subset? #{2 5} #{1 2 3 4}) ; false

(cl-set/superset? #{0} #{0})       ; true
(cl-set/superset? #{0 1} #{0})     ; true
(cl-set/superset? #{0} #{0 1})     ; false

;; ;;;;;;;;;;;
;; Sorted sets
;; ;;;;;;;;;;;

;; rseq
;; subseq
;; rsubseq

(rseq (sorted-set 3 2 1))            ; (3 2 1)
(subseq (sorted-set 1 2 3 4) > 2)    ; (3 4)
(rsubseq (sorted-set 1 2 3 4 5) < 3) ; (2 1)

;; ;;;;;;;;
;; ; Maps ;
;; ;;;;;;;;

;; ;;;;;;;;;;;;;;;
;; Create unsorted
;; ;;;;;;;;;;;;;;;

;; {}
;; hash-map
;; array-map
;; zipmap
;; bean
;; frequencies
;; group-by
;; (clojure.set/)
;; index

(hash-map)                  ; {}
{}                          ; {}
(hash-map :key1 1, :key2 2) ; {:key2 2, :key1 1}
(hash-map :key1 1, :key1 2) ; {:key1 2}
;; {:key1 1, :key1 2}       ; (err) Duplicate key

(array-map :a 10 :b 20)               ; {:a 10, :b 20}
(apply array-map [:a 10 :b 20 :c 30]) ; {:a 10, :b 20, :c 30}

(zipmap [:a :b :c :d :e] [1 2 3 4 5]) ; {:a 1, :b 2, :c 3, :d 4, :e 5}

(import java.util.Date)
(bean (Date.))
; {:day 0,
;  :date 10,
;  :time 1754849990408,
;  :month 7,
;  :seconds 50,
;  :year 125,
;  :class java.util.Date,
;  :timezoneOffset -120,
;  :hours 20,
;  :minutes 19}

(frequencies ['a 'b 'a 'a]) ; {a 3, b 1}

(let [students
      [{:name "Alice" :age 23 :gender :female}
       {:name "Bob" :age 21 :gender :male}
       {:name "John" :age 23 :gender :male}
       {:name "Maria" :age 22 :gender :female}
       {:name "Julie" :age 22 :gender :female}]]
  (frequencies (map :gender students)))
; {:female 3, :male 2}

(group-by count ["a" "ab" "abc" "cd" "b"]) ; {1 ["a" "b"], 2 ["ab" "cd"], 3 ["abc"]}
(group-by odd? (range 10))                 ; {false [0 2 4 6 8], true [1 3 5 7 9]}

(let [weights #{{:name 'betsy :weight 1000}
                {:name 'shyq :weight 1000}
                {:name 'jake :weight 756}}]
  (cl-set/index weights [:weight]))
; {{:weight 1000} #{{:name shyq, :weight 1000} {:name betsy, :weight 1000}},
;  {:weight 756}  #{{:name jake, :weight 756}}}

;;=> {23 2, 21 1, 22 2}
;; ;;;;;;;;;;;;;
;; Create sorted
;; ;;;;;;;;;;;;;

;; sorted-map
;; sorted-map-by
;; avl/sorted-map
;; sorted-map-by
;; fm/ordered-map
;; pm/priority-map

(sorted-map :z 0, :a 28, :b 35) ; {:a 28, :b 35, :z 0}
(into (sorted-map) {:b 2 :a 1}) ; {:a 1, :b 2}

(sorted-map-by > 1 "a", 2 "b", 3 "c") ; {3 "c", 2 "b", 1 "a"}
(avl/sorted-map 0 0 1 1 2 2)          ; {0 0, 1 1, 2 2}
(avl/sorted-map-by > 0 0 1 1 2 2)     ; {2 2, 1 1, 0 0}
(fm/ordered-map :b 2 :a 1 :d 4)       ; {:b 2, :a 1, :d 4}

;; sorted by value
(pm/priority-map :a 2 :b 1 :c 3 :d 5 :e 4 :f 3) ; {:b 1, :a 2, :c 3, :f 3, :e 4, :d 5}

;; ;;;;;;;
;; Examine
;; ;;;;;;;

;; (my-map k) → (get my-map k) also (:key my-map) → (get my-map :key)
;; get-in
;; contains?
;; find
;; keys
;; vals

(let [m {:username "sally"
         :profile {:name "Sally Clojurian"
                   :address {:city "Austin" :state "TX"}}}]
  (get-in m [:profile :name]))
; "Sally Clojurian"

(let [m {:username "sally"
         :profile {:name "Sally Clojurian"
                   :address {:city "Austin" :state "TX"}}}]
  ((m :profile) :name))
; "Sally Clojurian"

(let [m {:username "sally"
         :profile {:name "Sally Clojurian"
                   :address {:city "Austin" :state "TX"}}}]
  ((:profile m) :name))
; "Sally Clojurian"

(contains? {:a 1} :a)   ; true
(contains? {:a nil} :a) ; true
(contains? {:a 1} :b)   ; false

(find {:a 1 :b 2 :c 3} :a) ; [:a 1]
(find {:a 1 :b 2 :c 3} :d) ; nil

(keys {:keys :and, :some :values}) ; (:keys :some)
(vals {:keys :and, :some :values}) ; (:and :values)

;; ;;;;;;
;; Change
;; ;;;;;;

;; assoc
;; assoc-in
;; dissoc
;; merge
;; merge-with
;; select-keys
;; update
;; update-in
;; cl-set/rename-keys
;; cl-set/map-invert
;; update-keys
;; update-vals
;; NOTE: GitHub: Medley

(assoc {:key1 "old value1" :key2 "value2"}
       :key1 "value1" :key3 "value3")
; {:key1 "value1", :key2 "value2", :key3 "value3"}

(let [users [{:name "James" :age 26}  {:name "John" :age 43}]]
  (assoc-in users [1 :age] 88))
; [{:name "James", :age 26} {:name "John", :age 88}]

(dissoc {:a 1 :b 2 :c 3} :b) ; {:a 1, :c 3}

(merge {:a 1 :b 2 :c 3} {:b 9 :d 4}) ; {:a 1, :b 9, :c 3, :d 4}

(merge-with +
            {:a 1  :b 2}
            {:a 9  :b 98 :c 0})
; {:a 10, :b 100, :c 0}
(merge-with into
            {"Lisp" ["Common Lisp" "Clojure"]
             "ML" ["Caml" "Objective Caml"]}
            {"Lisp" ["Scheme"]
             "ML" ["Standard ML"]})
; {"Lisp" ["Common Lisp" "Clojure" "Scheme"],
;  "ML"   ["Caml" "Objective Caml" "Standard ML"]}

(select-keys {:a 1 :b 2} [:a])         ; {:a 1}
(select-keys {:a 1 :b 2} [:a :c])      ; {:a 1}
(select-keys {:a 1 :b 2 :c 3} [:a :c]) ; {:a 1, :c 3}

(let [p {:name "James" :age 26}]
  (update p :age inc))
; {:name "James", :age 27}

(let [users [{:name "James" :age 26}
             {:name "John" :age 43}]]
  (update-in users [1 :age] inc))
; [{:name "James", :age 26}
;  {:name "John",  :age 44}))

(cl-set/rename-keys {:a 1, :b 2} {:a :new-a, :b :new-b}) ; {:new-a 1, :new-b 2}

(cl-set/map-invert {:a 1, :b 2}) ; {1 :a, 2 :b}

(update-keys {"foo" 42 "bar" 1337} keyword) ; {:foo 42, :bar 1337}
(update-keys {:foo 42 :bar 1337} name)      ; {"foo" 42, "bar" 1337}
(update-keys {:foo 42 :bar 1337} str)       ; {":foo" 42, ":bar" 1337}

(update-vals {:a 1, :b 2} inc)              ; {:a 2, :b 3}
(update-vals {:a {:x 7, :y 100}, :b {:x 8, :y 150}, :c {:x 9, :y 200}} :x)
; {:a 7, :b 8, :c 9}

;; ;;;
;; Ops
;; ;;;

;; reduce-kv

(reduce-kv
 (fn [m k v] (assoc m k (inc v)))
 {}
 {:a 1 :b 2})
;; #p m => {}
;; #p m => {:a 2}
; {:a 2, :b 3}

;; ;;;;;
;; Entry
;; ;;;;;

;; key
;; val

(map key {:a 1 :b 2}) ; (:a :b)
(keys    {:a 1 :b 2}) ; (:a :b)

(map val {:a 1 :b 2}) ; (1 2)
(vals    {:a 1 :b 2}) ; (1 2)

;; ;;;;;;;;;;;
;; Sorted maps
;; ;;;;;;;;;;;

;; rseq
;; subseq
;; rsubseq

(rseq (into (sorted-map) {:a 1 :b 2})) ; ([:b 2] [:a 1])

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ; Queues (conj at end, peek & pop from beginning) ;
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; ;;;;;;
;; Create
;; ;;;;;;

;; clojure.lang.PersistentQueue/EMPTY
;; (no literal syntax or constructor fn)

clojure.lang.PersistentQueue/EMPTY ; <-()-<

;; ;;;;;;;
;; Examine
;; ;;;;;;;

;; peek

(-> clojure.lang.PersistentQueue/EMPTY
    (peek))
; nil

;; ;;;;;;
;; Change
;; ;;;;;;

;; conj
;; pop

(-> clojure.lang.PersistentQueue/EMPTY
    (conj 1 2 3))
; <-(1 2 3)-<

(-> clojure.lang.PersistentQueue/EMPTY
    (conj 1 2 3)
    (pop))
; <-(2 3)-<

(-> clojure.lang.PersistentQueue/EMPTY
    (conj 1 2 3)
    (pop)
    (peek))
; 2

;; ;;;;;;;;;;;;;;;
;; ;; Functions ;;
;; ;;;;;;;;;;;;;;;

;; ;;;;;;
;; Create
;; ;;;;;;

;; fn
;; defn
;; defn-
;; identity
;; constantly
;; memfn
;; comp
;; complement
;; partial
;; juxt
;; memoize
;; fnil
;; every-pred
;; some-fn

(map (fn [x] (* x x)) (range 1 10)) ; (1 4 9 16 25 36 49 64 81)

(reduce (fn [m [k v]] (assoc m v k)) {} {:b 2 :a 1 :c 3}) ; {2 :b, 1 :a, 3 :c}

;; Anonymous function with a name.
;; Not so anonymous now is it? This is useful in stack traces.
(fn add [a b] (+ a b))
;; (add 1 2) ; Unresolved symbol: add

(defn foo [a b c] (* a b c))
(foo 1 2 3) ; 6

;; private function
(defn- bar [] "World!")
(bar) ; "World!"

(identity 42) ; 42

(def boring (constantly 10))
(boring 1 2 3)              ; 10
(boring)                    ; 10
(boring "Is anybody home?") ; 10

(let [files (file-seq (java.io.File. "/tmp/"))]
  (count (filter #(.isDirectory %) files)))
; 14

(let [files (file-seq (java.io.File. "/tmp/"))]
  (count (filter (memfn isDirectory) files)))
; 14

((comp - /) 8 3) ; -8/3
((comp (partial apply str) reverse str) "hello" "clojuredocs") ; "scoderujolcolleh"

(let [not-empty? (complement empty?)]
  (not-empty? [1 2]))
; true

(let [hundred-times (partial * 10 10)]
  (hundred-times 5))
; 500

;; extract values from a map
((juxt :a :b) {:a 1 :b 2 :c 3 :d 4})               ; [1 2]
((juxt identity name) :a)                          ; [:a "a"]
(into {} (map (juxt identity name) [:a :b :c :d])) ; {:a "a", :b "b", :c "c", :d "d"}

;; Fibonacci number with recursion.
(defn fib [n]
  (condp = n
    0 1
    1 1
    (+ (fib (dec n)) (fib (- n 2)))))

(time (fib 30)) ; 1346269
; (out) "Elapsed time: 457.057039 msecs"

;; Fibonacci number with recursion and memoize.
(def m-fib (memoize fib))

(time (m-fib 30)) ; 1346269
; (out) "Elapsed time: 443.001231 msecs"

(time (m-fib 30)) ; 1346269
; (out) "Elapsed time: 0.125388 msecs"

;; fnil

(defn say-hello [name] (str "Hello " name))
(def say-hello-with-defaults (fnil say-hello "World"))
(say-hello-with-defaults "Sir") ; "Hello Sir"
(say-hello-with-defaults nil)   ; "Hello World"

(defn say-hello-1 [first other] (str "Hello " first " and " other))
(def say-hello-with-defaults-1 (fnil say-hello-1 "World" "People"))
(say-hello-with-defaults-1 nil nil)       ; "Hello World and People"
(say-hello-with-defaults-1 "Sir" nil)     ; "Hello Sir and People"
(say-hello-with-defaults-1 nil "Ma'am")   ; "Hello World and Ma'am"
(say-hello-with-defaults-1 "Sir" "Ma'am") ; "Hello Sir and Ma'am"

;; every-pred

((every-pred number? odd?) 3 9 11)                             ; true
(filter (every-pred pos? ratio?) [0 2/3 -2/3 1/4 -1/10 5 3/3]) ; (2/3 1/4)
(ratio? 3/3) ; false

;; some-fn

(def fizzbuzz
  (some-fn #(and (= (mod % 3) 0) (= (mod % 5) 0) "FizzBuzz")
           #(and (= (mod % 3) 0) "Fizz")
           #(and (= (mod % 5) 0) "Buzz")
           str))

(map fizzbuzz (range 1 18))
; ("1"
;  "2"
;  "Fizz"
;  "4"
;  "Buzz"
;  "Fizz"
;  "7"
;  "8"
;  "Fizz"
;  "Buzz"
;  "11"
;  "Fizz"
;  "13"
;  "14"
;  "FizzBuzz"
;  "16"
;  "17")

;; ;;;;
;; Call
;; ;;;;

;; apply
;; ->
;; ->>
;; trampoline
;; as->
;; cond->
;; cond->>
;; some->
;; some->>

;; apply

(str "str1" "str2" "str3")         ; "str1str2str3"
(apply str ["str1" "str2" "str3"]) ; "str1str2str3"

;; ->

(first (.split (.replace (.toUpperCase "a b c d") "A" "X") " "))
; "X"

(-> "a b c d"
    .toUpperCase
    (.replace "A" "X")
    (.split " ")
    first)
; "X"

;; ->>

(->> (range)
     (map #(* % %))
     (filter even?)
     (take 10)      ; (0 4 16 36 64 100 144 196 256 324)
     (reduce +))
; 1140

;; trampoline

;; From "The Joy of Clojure, Second Edition".
;; Using mutually recursive functions to implement a finite state machine (FSM)
;; This machine has three states {a b c} and seven transitions {:a-b :a-c :b-a :b-c :c-a :c-b :final}.

(defn fsm [cmds]
  (letfn
   [(a-> [[_ & rs]]
      #(case _
         :a-b (b-> rs)
         :a-c (c-> rs)
         false))
    (b-> [[_ & rs]]
      #(case _
         :b-a (a-> rs)
         :b-c (c-> rs)
         false))
    (c-> [[_ & rs]]
      #(case _
         :c-a (a-> rs)
         :c-b (c-> rs)
         :final true
         false))] (trampoline a-> cmds)))

(fsm [:a-b :b-c :c-a :a-c :final])
; true

;; as->

(as-> 0 n
  (inc n)
  (inc n))
; 2

(-> [10 11]
    (conj 12)                     ; [10 11 12]
    (as-> xs (map - xs [3 2 1]))  ; [10 11 12] - [3 2 1] => [7 9 1]
    (reverse))
; (11 9 7)

;; cond->

;; `cond->` is threaded through all lines!!!
(cond-> 1
  true inc
  false (* 42)
  (= 2 2) (* 3))
; 6

(letfn [(divisible-by? [divisor number] (zero? (mod number divisor)))
        (say [n]
          (cond-> nil
            (divisible-by? 3 n) (str "Fizz")
            (divisible-by? 5 n) (str "Buzz")
            :always             (or (str n))))]
  (say 15))
; "FizzBuzz"

;; cond->>

(defn do-stuff
  [coll {:keys [map-fn max-num-things batch-size]}]
  (cond->> coll
    map-fn         (map map-fn)
    max-num-things (take max-num-things)
    batch-size     (partition batch-size)))

(do-stuff [1 2 3 4] {})                              ; [1 2 3 4]
(do-stuff [1 2 3 4] {:map-fn str})                   ; ("1" "2" "3" "4")
(do-stuff [1 2 3 4] {:map-fn str :batch-size 2})     ; (("1" "2") ("3" "4"))
(do-stuff [1 2 3 4] {:map-fn str :max-num-things 3}) ; ("1" "2" "3")

;; some->

;; (-> {:a 1}
;;     :b
;;     inc)
; (err) Execution error (NullPointerException)

(some-> {:a 1}
        :b
        inc)
; nil

;; some->>

(some->> {:y 3 :x 5}
         (:y)
         (- 2))
; -1

;; ;;;;
;; Test
;; ;;;;

;; fn?
;; ifn?

;; fn?

(fn? inc)      ; true
(fn? (fn []))  ; true
(fn? #(+ % 5)) ; true
(fn? 5)        ; false

;; ;;;;;;;;;;;;
;; ;; Macros ;;
;; ;;;;;;;;;;;;

;; ;;;;;;;;;;;;;;;
;; ;; Sequences ;;
;; ;;;;;;;;;;;;;;;

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ;; Transducers (clojure.org/reference/transducers) ;;
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; ;;;;;;;;
;; ;; IO ;;
;; ;;;;;;;;
