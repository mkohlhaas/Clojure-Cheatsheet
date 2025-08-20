#_{:clj-kondo/ignore [:unused-namespace]}
(ns cheatsheet.core
  (:require
   [clojure.data.avl :as avl]
   [clojure.data.int-map :as im]
   [clojure.data.priority-map :as pm]
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer [javadoc]]
   [clojure.math :as m]
   [clojure.pprint :as pp]
   [clojure.reflect :as refl]
   [clojure.repl :refer [apropos dir doc find-doc pst source]]
   [clojure.set :as cl-set]
   [clojure.string :as str]
   [clojure.tools.reader.edn :as reader-edn]
   [clojure.walk :as w]
   [clojure.xml :as xml]
   [flatland.ordered.map :as fm]
   [flatland.ordered.set :as fl])
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

;; doc

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

;; find-doc

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

;; apropos

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

;; dir

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

;; source

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

;; pst

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

;; javadoc

;; opens web browser

(comment
  (javadoc String)
  (javadoc "abc")
  (javadoc 1))

;; ;;;;;
;; Other
;; ;;;;;

;; *print-dup*
;; *print-length*
;; *print-level*
;; *print-meta*
;; *print-readably*

;; *print-length*

;; Prints 500 items in nvim. In the REPL we are toast -> prints forever)
(iterate inc 0)

;; in nvim this setting is ignored
(binding [*print-length* 10]
  (iterate inc 0))
; (0 1 2 3 4 5 6 7 8 9 ...)

[1 [2 [3]]]
; [1 [2 [3]]]

;; *print-level*

;; in nvim this setting is ignored
(binding [*print-level* 2]
  [1 [2 [3]]])
;; [1 [2 #]]

;; *print-meta*

(binding [*print-meta* true]
  (pr #'defmacro))
;; (out) ^{:added "1.0", :ns ^{:doc "Fundamental library of the Clojure language",
;;                             :author "Rich Hickey" #object[clojure.lang.Namespace 0x2dd56651 "clojure.core"],
;;                             :name defmacro,
;;                             :file "clojure/core.clj",
;;                             :column 1, :line 446, :macro true, :arglists ^{:line 451, :column 15} (# #), :doc "Like defn, but the resulting function name is declared as a\n  macro and will be used as a macro by the compiler when it is\n  called." #'clojure.core/defmacro}}

;; *print-readably*

(binding [*print-readably* false]
  (pr "a\nb\nc"))
; (out) a
; (out) b
; (out) c

(binding [*print-readably* true]
  (pr "a\nb\nc"))
; (out) "a\nb\nc"

;; *print-dup*

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

;; into

(into () '(1 2 3))      ; (3 2 1)
(into () [1 2 3])       ; (3 2 1)
(into [] '(1 2 3))      ; [1 2 3]
(into [1 2 3] '(4 5 6)) ; [1 2 3 4 5 6]

(into {} [[:a "a"] [:b "b"]]) ; {:a "a", :b "b"}

;; NOTE: error (due to performance reasons)
;; (into {} ['(:a "a") '(:b "b")])

;; conj

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

;; (let [s (range 1e6)]
;;   (time (into #{} s))                 ; ~100mb
;;   (time (into (im/int-set) s))        ; ~1mb
;;   (time (into (im/dense-int-set) s))) ; ~150kb
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

;; private function (can only be called from the same namespace)
(defn- bar [] "World!")
(bar) ; "World!"

(identity 42) ; 42

(def boring (constantly 10))
(boring 1 2 3)              ; 10
(boring)                    ; 10
(boring "Is anybody home?") ; 10

(let [files (file-seq (java.io.File. (System/getProperty "java.io.tmpdir")))]
  (count (filter #(.isDirectory %) files)))
; 14

(let [files (file-seq (java.io.File. (System/getProperty "java.io.tmpdir")))]
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
;; (defn fib [n]
;;   (condp = n
;;     0 1
;;     1 1
;;     (+ (fib (dec n)) (fib (- n 2)))))

;; (time (fib 30)) ; 1346269
; (out) "Elapsed time: 457.057039 msecs"

;; Fibonacci number with recursion and memoize.
;; (def m-fib (memoize fib))

;; (time (m-fib 30)) ; 1346269
; (out) "Elapsed time: 443.001231 msecs"

;; (time (m-fib 30)) ; 1346269
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
;; Using mutually recursive functions to implement a finite state machine (FSM).

(defn elevator [commands]
  (letfn
   [(ff-open [[cmd & r]] ; 1st floor open
      "When the elevator is open on the 1st floor
          it can either close or be done."
      #(case cmd
         :close (ff-closed r)
         :done  true
         false))
    (ff-closed [[cmd & r]] ; 1st floor closed
      "When the elevator is closed on the 1st floor
          it can either open or go up."
      #(case cmd
         :open (ff-open r)
         :up   (sf-closed r)
         false))
    (sf-closed [[cmd & r]] ; 2nd floor closed
      "When the elevator is closed on the 2nd floor
          it can either go down or open."
      #(case cmd
         :down (ff-closed r)
         :open (sf-open r)
         false))
    (sf-open [[cmd & r]] ; 2nd floor open
      "When the elevator is open on the 2nd floor
          it can either close or be done"
      #(case cmd
         :close (sf-closed r)
         :done  true
         false))
    (trampoline ff-open commands)]))

(elevator [:close :open :close :up :open :open :done]) ; false
(elevator [:close :up :open :close :down :open :done]) ; true

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

;; ifn?

;; An anonymous function is a function as you'd expect
(ifn? (partial + 5))    ; true
((partial + 5) 7)       ; 12

(ifn? [1 2 3])          ; true
([1 2 3] 0)             ; 1

(ifn? {:a "a", :b "b"}) ; true
({:a "a", :b "b"} :a)   ; "a"

(ifn? 'foo)             ; true
('a {'a "a", 'b "b"})   ; "a"

(ifn? :a)               ; true
(:a {:a "a", :b "b"})   ; "a"

(ifn? 1)                ; false

;; ;;;;;;;;;;;;
;; ;; Macros ;;
;; ;;;;;;;;;;;;

;; ;;;;;;
;; Create
;; ;;;;;;

;; defmacro

(defmacro unless [pred a b]
  `(if (not ~pred) ~a ~b))

(unless false
        (println "Will print")
        (println "Will not print"))
; (out) Will print

; clojure.walk/macroexpand-all (form): (unless false (println "Will print") (println "Will not print"))
(if (clojure.core/not false)
  (println "Will print")
  (println "Will not print"))
; (out) Will print

;; ;;;;;;
;; Branch
;; ;;;;;;

;; and
;; or
;; when
;; when-not
;; when-let
;; when-first
;; if-not
;; if-let
;; cond
;; condp
;; case
;; when-some
;; if-some

;; and

(and true true)   ; true
(and true false)  ; false
(and false true)  ; false
(and false false) ; false

;; or

(or true true)   ; true
(or true false)  ; true
(or false true)  ; true
(or false false) ; false

;; when

(when (= 1 1) true)    ; true
(when (not= 1 1) true) ; nil

;; when-not

(when-not false 42) ; 42
(when-not true  42) ; nil

;; when-let

(defn drop-one
  [coll]
  (when-let [s (seq coll)]
    (rest s)))

(drop-one [1 2 3]) ; (2 3)
(drop-one [])      ; nil

;; when-first

(when-first [a [1 2 3]] (str a))       ; "1"
(when-first [a []] (str a))            ; nil
(when-first [a "test string"] (str a)) ; "t"

; (when-first [a :a] (str a))
; (err) Execution error (IllegalArgumentException)

;; if-not

(if-not (zero? 0) :then :else) ; :else

;; if-let

(if-let [[w n] (re-find #"a(\d+)x" "aaa123xxx")]
  [w n]
  :not-found)
; ["a123x" "123"]

(if-let [[w n] (re-find #"a(\d+)x" "bbb123yyy")]
  [w n]
  :not-found)
; :not-found

;; cond

(defn grade [g]
  (cond
    (>= g 90) "A"
    (>= g 80) "B"
    (>= g 70) "C"
    (>= g 60) "D"
    :else "F"))

(map grade (range 99 50 -10))  ; ("A" "B" "C" "D" "F")

;; condp
;; see example above

;; case

(let [x 30]
  ;; "good"
  (time (cond
          (= x 10) :ten
          (= x 20) :twenty
          (= x 30) :thirty
          :else :dunno))

  ;; "better"
  (time (condp = x
          10 :ten
          20 :twenty
          30 :thirty
          :dunno))

  ;; "best"
  (time (case x
          10 :ten
          20 :twenty
          30 :thirty
          :dunno)))
; (out) "Elapsed time: 0.104215 msecs"
; (out) "Elapsed time: 0.179686 msecs"
; (out) "Elapsed time: 0.001332 msecs"

;; when-some

(when-some [x false] {:x x}) ; {:x false}
(when-let  [x false] {:x x}) ; nil

(when-some [x nil] {:x x})   ; nil

;; if-some

(if-some [_a 10]    :true :false)   ; => :true
(if-some [_a true]  :true :false)   ; => :true
(if-some [_a false] :true :false)   ; => :true
(if-some [_a nil]   :true :false)   ; => :false

(if-let [_a 10]     :true :false)   ; => :true
(if-let [_a true]   :true :false)   ; => :true
(if-let [_a false]  :true :false)   ; => :false
(if-let [_a nil]    :true :false)   ; => :false

;; ;;;;
;; Loop
;; ;;;;

;; for
;; doseq
;; dotimes
;; while

;; for

;; NOTE: list comprehension

;; for, :let, :when, :while

(for [x (range 40)
      :when (= 1 (rem x 4))]
   x)
; (1 5 9 13 17 21 25 29 33 37)

(for [x (iterate #(+ 4 %) 0)
      :let [z (inc x)]
      :while (< z 40)]
  z)
; (1 5 9 13 17 21 25 29 33 37)

(for [[x y] (partition 2 (range 20))]
  (+ x y))
; (1 5 9 13 17 21 25 29 33 37)

(for [x (range 10)]
  (* x x))
; (0 1 4 9 16 25 36 49 64 81)

(for [x (range 3)
      y (range 3)]
  [x y])
; ([0 0] [0 1] [0 2] [1 0] [1 1] [1 2] [2 0] [2 1] [2 2])

(for [x (range 20)
      :let [y (* x x)]
      :when (even? y)]
  y)
; (0 4 16 36 64 100 144 196 256 324)

(for [meth (.getMethods java.awt.Frame)
      :let [name (.getName meth)]
      :when (re-find #"Vis" name)]
  name)
; ("setVisible" "isVisible")

;; doseq

;; like `for` but with side-effects
(doseq [x (range 3)
        y (range 3)]
  (println [x y]))
; (out) [0 0]
; (out) [0 1]
; (out) [0 2]
; (out) [1 0]
; (out) [1 1]
; (out) [1 2]
; (out) [2 0]
; (out) [2 1]
; (out) [2 2]

;; dotimes

(dotimes [n 5] (println "n is" n))
; (out) n is 0
; (out) n is 1
; (out) n is 2
; (out) n is 3
; (out) n is 4

;; while

(let [a (atom 10)]
  (while (pos? @a)
    (println @a)
    (swap! a dec)))
; (out) 10
; (out) 9
; (out) 8
; (out) 7
; (out) 6
; (out) 5
; (out) 4
; (out) 3
; (out) 2
; (out) 1

;; ;;;;;;;
;; Arrange
;; ;;;;;;;

;; ..
;; doto
;; ->       (see examples above)
;; ->>      (see examples above)
;; as->     (see examples above)
;; cond->   (see examples above)
;; cond->>  (see examples above)
;; some->   (see examples above)
;; some->>  (see examples above)

;; ..

(.. System (getProperties) (get "os.name")) ; "Linux"

; clojure.walk/macroexpand-all (form): (.. System (getProperties) (get "os.name"))
(. (. System (getProperties)) (get "os.name"))

;; Java:    System.getProperties()
;; Clojure: (. System (getProperties))

;; (..   System (getProperties)  (get "os.name")) ; Clojure
;; (. (. System (getProperties)) (get "os.name")) ; Clojure
;; (.    System.getProperties()  (get "os.name")) ; Clojure & Java
;; System.getProperties().get("os.name")          ; Java

(.. "abc" toUpperCase (equals "ABC")) ; true

;; doto

(doto (java.util.HashMap.)
  (.put "a" 1)
  (.put "b" 2)
  (println))
; (out) #object[java.util.HashMap 0x5423d646 {a=1, b=2}]
; {"a" 1, "b" 2}

;; ;;;;;
;; Scope
;; ;;;;;

;; binding
;; locking
;; time
;; with-in-str
;; with-local-vars
;; with-open
;; with-out-str
;; with-precision  (see examples above)
;; with-redefs
;; with-redefs-fn

;; binding

(def ^:dynamic x 1)
(def ^:dynamic y 1)

(+ x y) ; 2

(binding [x 2
          y 3]
  (+ x y))
; 5

(+ x y) ; 2

;; locking

;; (let [o (Object.)]
;;   (future (locking o
;;             (Thread/sleep 5000)
;;             (println "done1")))
;;   (Thread/sleep 1000) ; give first instance 1 sec to acquire the lock
;;   (locking o
;;     (Thread/sleep 1000)
;;     (println "done2")))
; (out) done1
; (out) done2

;; time

;; (time (Thread/sleep 100))
; (out) "Elapsed time: 100.479384 msecs"

;; with-in-str

(defn prompt [question]
  (println question)
  (read-line))

(with-in-str "34" (prompt "How old are you?"))
; (out) How old are you?
; "34"

;; with-local-vars

;; @var = (var-get var)
(defn factorial [x]
  (with-local-vars [acc 1, cnt x]
    (while (> @cnt 0)
      (var-set acc (* @acc @cnt))
      (var-set cnt (dec @cnt)))
    @acc))

(factorial 5) ; 120

;; with-open

;; `println` uses `*out*`
(defn log [message]
  (let [timestamp (.format (java.text.SimpleDateFormat. "yyyy-MM-dd'T'HH:mmZ")
                           (java.util.Date.))]
    (println timestamp "[INFO]" message)))

(defn process-events [events]
  (doseq [event events]
    (log (format "Event %s has been processed" (:id event)))))

;; redirecting output by `binding` `*out*` to a file
(let [file (java.io.File. (System/getProperty "java.io.tmpdir") "event-stream.log")]
  (with-open [file (io/writer file :append true)]
    (binding [*out* file]
      (process-events [{:id 88896} {:id 88898}]))))
; see file in /tmp/event-stream.log

;; with-out-str

(with-out-str (print "this should return as a string"))
; "this should return as a string"

;; with-redefs

;; primarily used in testing
;; (deftest is-a-macro
;;   (with-redefs [http/post (fn [url] {:body "Goodbye world"})]
;;     (is (= {:body "Goodbye world"} (http/post "http://service.com/greet")))))

;; Clojure’s with-redefs can’t replace :inline versions of functions, since they’ve
;; already been expanded by the time with-redefs gets ahold of them
(with-redefs [< +] (< 1 2))         ; true (< not replaced by +)
(with-redefs [< +] (apply < [1 2])) ; 3 (now it has been replaced)

;; with-redefs-fn

;; primarily used in testing
;; (deftest is-a-fn
;;   (with-redefs-fn {#'http/post (fn [url] {:body "Hello world again"})}
;;     #(is (= {:body "Hello world again"} (http/post "http://service.com/greet")))))

;; ;;;;
;; Lazy
;; ;;;;

;; lazy-cat
;; lazy-seq
;; delay

;; lazy-cat

(lazy-cat [1 2 3] [4 5 6]) ; (1 2 3 4 5 6)

;; lazy-seq

(defn positive-numbers
  ([] (positive-numbers 1))
  ([n] (lazy-seq (cons n (positive-numbers (inc n))))))

(take 5 (positive-numbers)) ; (1 2 3 4 5)

;; delay

(def my-delay (delay (println "did some work") 100))

@my-delay
; (out) did some work
; 100

@my-delay
; 100

;; ;;;
;; Doc
;; ;;;

;; assert
;; comment
;; doc

;; assert

(assert true) ; nil

;; (assert false)
; (err) Execution error (AssertionError)

;; comment

#_{:clj-kondo/ignore [:unresolved-symbol]}
(comment
  (functioncall-1)
  (functioncall-2))

;; comments must be syntactically valid code
;; (comment
;;  a : b) ; Unresolved symbol: a

;; doc (see examples above)

;; ;;;;;;;;;;;;;;;
;; ;; Sequences ;;
;; ;;;;;;;;;;;;;;;

;; ;;;;;;;;;;;;;;;;;;;;;;;
;; ; Creating a Lazy Seq ;
;; ;;;;;;;;;;;;;;;;;;;;;;;

;; ;;;;;;;;;;;;;;;
;; From collection
;; ;;;;;;;;;;;;;;;

;; seq
;; vals    (see examples above)
;; keys    (see examples above)
;; rseq    (see examples above)
;; subseq  (see examples above)
;; rsubseq (see examples above)
;; sequence

;; seq

(seq '(1))    ; (1)
(seq [1 2 3]) ; (1 2 3)
(seq "abc")   ; (\a \b \c)

;; `seq` acts like `empty`
(seq nil) ; nil
(seq '()) ; nil
(seq [])  ; nil
(seq "")  ; nil

(every? seq       ["1" [1] '(1) {:1 1} #{1}]) ; true
(every? not-empty ["1" [1] '(1) {:1 1} #{1}]) ; true

(seq {:key1 "value1" :key2 "value2"}) ; ([:key1 "value1"] [:key2 "value2"])

;; sequence

;; sequence can take a form!!! (see example xml-seq)

(sequence [1 2 3]) ; (1 2 3)
(sequence [])      ; ()
(sequence nil)     ; ()

(and (not= (sequence  nil) (seq  nil))
     (not= (sequence '())  (seq '()))
     (not= (sequence  [])  (seq  []))
     (not= (sequence  "")  (seq  "")))
; true

(and (= (sequence  [1 2 3])            (seq  [1 2 3]))
     (= (sequence '(1 2 3))            (seq '(1 2 3)))
     (= (sequence (sorted-set 1 2 3))  (seq (sorted-set 1 2 3))))
; true

;; ;;;;;;;;;;;;;;;;
;; From producer fn
;; ;;;;;;;;;;;;;;;;

;; lazy-seq (see examples above)
;; repeatedly
;; iterate
;; iteration

;; repeatedly

(take 5 (repeatedly #(rand-int 10))) ; (2 7 0 0 9)

(repeat 5 (rand-int 10)) ; (8 8 8 8 8)

;; iterate

(take 5 (iterate inc 0)) ; (0 1 2 3 4)

;; iteration

;; can be used to consume APIs that return paginated or batched data.

(def pages
  (atom {"a" {:items ["Rose" "Tulip" "Marigold" "Lavender" "Daffodil"] :next-page "b"}
         "b" {:items ["Sunflower" "Petunia" "Chrysanthemum" "Dandelion" "Pansy"] :next-page "c"}
         "c" {:items ["Orchid" "Daisy" "Lily" "Geranium" "Hyacinth"] :next-page "d"}
         "d" {:items ["Azalea" "Begonia" "Iris" "Poppy" "Jasmine"]}}))

;; And let's set up a function to access that atom, with a delay to simulate
;; network slowness…
(defn get-page! [id]
  (Thread/sleep 1000)
  (get @pages id))

(get-page! "a")
; {:items ["Rose" "Tulip" "Marigold" "Lavender" "Daffodil"], :next-page "b"}

;; lazy way to get pages…
;; (-> (iteration get-page! :initk "a", :vf :items, :kf :next-page)
;;     first
;;     time)
; (out) "Elapsed time: 1000.332569 msecs"
; ["Rose" "Tulip" "Marigold" "Lavender" "Daffodil"]

;; (-> (iteration get-page! :initk "a", :vf :items, :kf :next-page)
;;     second
;;     time)
; (out) "Elapsed time: 2000.435424 msecs"
; ["Sunflower" "Petunia" "Chrysanthemum" "Dandelion" "Pansy"]

;; (->> (iteration get-page! :initk "a", :vf :items, :kf :next-page)
;;      (sequence cat)
;;      last
;;      time)
; (out) "Elapsed time: 4001.354911 msecs"
; "Jasmine"

;; ;;;;;;;;;;;;;
;; From constant
;; ;;;;;;;;;;;;;

;; repeat
;; range

;; repeat

(take 5 (repeat "x")) ; ("x" "x" "x" "x" "x")

(repeat 5 "x")        ; ("x" "x" "x" "x" "x")

;; range

(range)        ; (0 1 2 3 4 5 6 7 8 9 10 11 ...)
(range 10)     ; (0 1 2 3 4 5 6 7 8 9)
(range 0 10 2) ; (0 2 4 6 8)

;; ;;;;;;;;;;
;; From other
;; ;;;;;;;;;;

;; file-seq
;; line-seq
;; resultset-seq (used in database queries)
;; re-seq        (see examples above)
;; tree-seq
;; xml-seq
;; iterator-seq
;; enumeration-seq

;; file-seq

(->> (io/file "/usr/bin")
     (file-seq)
     (filter #(.isFile %))
     (map str)
     (take 5))
; ("/usr/bin/qmlscene6"
;  "/usr/bin/troff"
;  "/usr/bin/sbcinfo"
;  "/usr/bin/pbmreduce"
;  "/usr/bin/moc-qt5")

;; line-seq

(with-open [rdr (io/reader "/etc/passwd")]
  (-> rdr
      line-seq
      count))
; 34

(with-open [r (io/reader "names.csv")]
  (into #{} (for [line (rest (line-seq r))
                  :let [[_gender name _year _occurrences] (clojure.string/split line #";")]]
              (str/capitalize name))))
; #{"Mary" "John" "Sally"}

;; tree-seq

;; depth-first
(tree-seq seq? identity '((1 2 (3)) (4)))
; (((1 2 (3)) (4)) (1 2 (3)) 1 2 (3) 3 (4) 4)

;; xml-seq

(def feeds
  [[:nytimes  "https://rss.nytimes.com/services/xml/rss/nyt/World.xml"]
   [:guardian "https://www.theguardian.com/world/rss"]
   [:wsj      "https://feeds.a.dj.com/rss/RSSWorldNews.xml"]])

(pmap
 (fn [[feed url]]
   (let [content (comp first :content)]
     [feed
      (sequence
       (comp
        (filter (comp string? content))
        (filter (comp #{:title} :tag))
        (map content))
       (xml-seq (xml/parse url)))]))
 feeds)

;; iterator-seq

;; Java 8 streams as sequences
(->> "Clojure is the best language"
     (.splitAsStream #"\s+")
     .iterator
     iterator-seq)
; ("Clojure" "is" "the" "best" "language")

;; enumeration-seq

(enumeration-seq (java.util.StringTokenizer. "exciting example"))
; ("exciting" "example")

;; ;;;;;;;;
;; From seq
;; ;;;;;;;;

;; keep
;; keep-indexed

;; keep

(keep even? (range 1 10))
; (false true false true false true false true false)

(keep #(when (odd? %) %) (range 10))
; (1 3 5 7 9)

;; keep-indexed

;; (keep-indexed #(when (odd? #p %1) #p %2) [:a :b :c :d :e])
;; 0
;; 1
;; :b
;; 2
;; 3
;; :d
;; 4
; (:b :d)

(keep-indexed #(when (odd? %1) %2) [:a :b :c :d :e])

;; ;;;;;;;;;;;;;;;;;;;
;; ; Seq in, Seq out ;
;; ;;;;;;;;;;;;;;;;;;;

;; ;;;;;;;;;;;
;; Get shorter
;; ;;;;;;;;;;;

;; distinct
;; filter
;; remove
;; take-nth
;; for (see examples above)
;; dedupe
;; random-sample

;; distinct

(distinct [1 2 1 3 1 4 1 5]) ; (1 2 3 4 5)

;; filter

(filter even? (range 10)) ; (0 2 4 6 8)

;; remove

(remove neg? [1 -2 2 -1 3 7 0]) ; (1 2 3 7 0)

;; take-nth

(take-nth 2 (range 10)) ; (0 2 4 6 8)

;; dedupe

(dedupe [1 2 3 3 3 1 1 6]) ; (1 2 3 1 6)

;; random-sample

;; resulting sequence shows different lengths
(random-sample 0.5 [1 2 3 4 5]) ; (1 2 5)

(take 10 (random-sample 0.01 (range))) ; (115 137 180 350 503 969 1039 1119 1243 1804)

;; ;;;;;;;;;;
;; Get longer
;; ;;;;;;;;;;

;; cons
;; conj
;; concat
;; lazy-cat (see examples above)
;; mapcat
;; cycle
;; interleave
;; interpose

;; cons
(cons 1 '(2 3 4 5 6)) ; (1 2 3 4 5 6)
(cons 1 [2 3 4 5 6])  ; (1 2 3 4 5 6)

;; conj

(conj  [1 2 3] 4) ; [1 2 3 4]
(conj '(1 2 3) 4) ; (4 1 2 3)

;; concat

(concat [1 2] [3 4]) ; (1 2 3 4)

(concat [:a :b] nil [1 [2 3] 4]) ; (:a :b 1 [2 3] 4)

;; mapcat

(mapcat reverse [[3 2 1 0] [6 5 4] [9 8 7]]) ; (0 1 2 3 4 5 6 7 8 9)
(map    reverse [[3 2 1 0] [6 5 4] [9 8 7]]) ; ((0 1 2 3) (4 5 6) (7 8 9))

;; cycle

(take 9 (cycle (range 3))) ; (0 1 2 0 1 2 0 1 2)

;; interleave

(interleave [:fruit :color :temp]
            ["grape" "red" "hot"])
; (:fruit "grape" :color "red" :temp "hot")

;; interpose

(interpose ", " ["1" "2" "3"])         ; ("1" ", " "2" ", " "3")
(interpose ", " ["one" "two" "three"]) ; ("one" ", " "two" ", " "three")

;; ;;;;;;;;;;
;; Tail-items
;; ;;;;;;;;;;

;; rest
;; nthrest
;; next
;; fnext
;; nnext
;; drop
;; drop-while
;; take-last
;; for (see examples above)

;; rest
(rest [1 2 3 4 5])           ; (2 3 4 5)
(rest ["a" "b" "c" "d" "e"]) ; ("b" "c" "d" "e")

(rest '())                   ; ()
(rest nil)                   ; ()

;; nthrest

(nthrest (range 20) 5) ; (5 6 7 8 9 10 11 12 13 14 15 16 17 18 19)
(nthrest (range 20) 0) ; (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19)

;; next

(next '(:alpha :bravo :charlie))         ; (:bravo :charlie)
(next (next '(:one :two :three)))        ; (:three)
(next (next (next '(:one :two :three)))) ; nil

;; fnext

(next  ['(a b c) '(b a c)]) ; ((b a c))
(fnext ['(a b c) '(b a c)]) ; (b a c)

;; nnext

(next  '(1 2 3 4 5))                  ; (2 3 4 5)
(next (next  '(1 2 3 4 5)))           ; (3 4 5)
(nnext '(1 2 3))                      ; (3)
(nnext '(1 2 3 4 5))                  ; (3 4 5)
(nnext  ['(a b c) '(b a c) '(c a b)]) ; ((c a b))

;; drop

(drop 0 [1 2 3 4]) ; (1 2 3 4)
(drop 2 [1 2 3 4]) ; (3 4)
(drop 5 [1 2 3 4]) ; ()

;; drop-while

(drop-while neg? [-1 -2 -6 -7 1 2 3 4 -5 -6 0 1]) ; (1 2 3 4 -5 -6 0 1)

;; take-last

(take-last 2 [1 2 3 4]) ; (3 4)
(take-last 0 [1 2 3 4]) ; nil
(take-last 5 [1 2 3 4]) ; (1 2 3 4)

;; ;;;;;;;;;;
;; Head-items
;; ;;;;;;;;;;

;; take
;; take-while
;; butlast
;; drop-last
;; for (see examples above)

;; take

(take 3 '(1 2 3 4 5 6)) ; (1 2 3)

;; take-while

(take-while neg? [-2 -1 0 1 2 3]) ; (-2 -1)

;; butlast

(butlast [1 2 3])                     ; (1 2)
(butlast (butlast [1 2 3]))           ; (1)
(butlast (butlast (butlast [1 2 3]))) ; nil

;; drop-last

;; always creates a lazy sequence
(drop-last [1 2 3])                          ; (1 2)
(drop-last (drop-last [1 2 3]))              ; (1)
(drop-last (drop-last (drop-last [1 2 3])))  ; ()

;; ;;;;;;
;; Change
;; ;;;;;;

;; conj     (see examples above)
;; concat   (see examples above)
;; distinct (see examples above)
;; flatten
;; group-by (see examples above)
;; partition
;; partition-all
;; partition-by
;; split-at
;; split-with
;; filter
;; remove   (see examples above)
;; replace  (see examples above)
;; shuffle
;; partitionv
;; partitionv-all
;; splitv-at

;; flatten

(flatten [1 [2 3]])                      ; (1 2 3)
(flatten '(1 2 3))                       ; (1 2 3)
(flatten '(1 2 [3 (4 5)]))               ; (1 2 3 4 5)
(flatten nil)                            ; ()

;; (flatten 5)                           ; ()
;; (flatten {:name "Hubert" :age 23})    ; ()
(flatten (seq {:name "Hubert" :age 23})) ; (:name "Hubert" :age 23)

;; partition

(partition 3 (range 18))         ; ((0 1 2) (3 4 5) (6 7 8) (9 10 11) (12 13 14) (15 16 17))
(partition 3 (range 20))         ; ((0 1 2) (3 4 5) (6 7 8) (9 10 11) (12 13 14) (15 16 17))
(partition 3 (range 21))         ; ((0 1 2) (3 4 5) (6 7 8) (9 10 11) (12 13 14) (15 16 17) (18 19 20))
(partition 3 5 (range 18))       ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17))
(partition 3 2 (range 13))       ; ((0 1 2) (2 3 4) (4 5 6) (6 7 8) (8 9 10) (10 11 12))
(partition 3 5 [0] (range 21))   ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17) (20 0))
(partition 3 5 [0] (range 22))   ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17) (20 21 0))
(partition 3 5 [0] (range 23))   ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17) (20 21 22))
(partition 3 5 [0] (range 24))   ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17) (20 21 22))
(partition 3 5 [0 0] (range 21)) ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17) (20 0 0))

;; partitionv

;; the same but using vectors
(partitionv 3 (range 18))         ; ([0 1 2] [3 4 5] [6 7 8] [9 10 11] [12 13 14] [15 16 17])
(partitionv 3 (range 20))         ; ([0 1 2] [3 4 5] [6 7 8] [9 10 11] [12 13 14] [15 16 17])
(partitionv 3 (range 21))         ; ([0 1 2] [3 4 5] [6 7 8] [9 10 11] [12 13 14] [15 16 17] [18 19 20])
(partitionv 3 5 (range 18))       ; ([0 1 2] [5 6 7] [10 11 12] [15 16 17])
(partitionv 3 2 (range 13))       ; ([0 1 2] [2 3 4] [4 5 6] [6 7 8] [8 9 10] [10 11 12])
(partitionv 3 5 [0] (range 21))   ; ([0 1 2] [5 6 7] [10 11 12] [15 16 17] [20 0])
(partitionv 3 5 [0] (range 22))   ; ([0 1 2] [5 6 7] [10 11 12] [15 16 17] [20 21 0])
(partitionv 3 5 [0] (range 23))   ; ([0 1 2] [5 6 7] [10 11 12] [15 16 17] [20 21 22])
(partitionv 3 5 [0] (range 24))   ; ([0 1 2] [5 6 7] [10 11 12] [15 16 17] [20 21 22])
(partitionv 3 5 [0 0] (range 21)) ; ([0 1 2] [5 6 7] [10 11 12] [15 16 17] [20 0 0])

;; partition-all

(partition-all 3   (range 18)) ; ((0 1 2) (3 4 5) (6 7 8) (9 10 11) (12 13 14) (15 16 17))
(partition-all 3   (range 20)) ; ((0 1 2) (3 4 5) (6 7 8) (9 10 11) (12 13 14) (15 16 17) (18 19))
(partition-all 3   (range 21)) ; ((0 1 2) (3 4 5) (6 7 8) (9 10 11) (12 13 14) (15 16 17) (18 19 20))
(partition-all 3 5 (range 18)) ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17))
(partition-all 3 2 (range 13)) ; ((0 1 2) (2 3 4) (4 5 6) (6 7 8) (8 9 10) (10 11 12) (12))
(partition-all 3 5 (range 21)) ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17) (20))
(partition-all 3 5 (range 22)) ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17) (20 21))
(partition-all 3 5 (range 23)) ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17) (20 21 22))
(partition-all 3 5 (range 24)) ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17) (20 21 22))
(partition-all 3 5 (range 21)) ; ((0 1 2) (5 6 7) (10 11 12) (15 16 17) (20))

;; partitionv-all
;; the same just using vectors

;; partition-by

(partition-by identity [1 1 1 1 2 2 3])      ; ((1 1 1 1) (2 2) (3))
(partition-by count ["a" "b" "ab" "ac" "c"]) ; (("a" "b") ("ab" "ac") ("c"))
(partition-by odd? [1 3 5 2 4 7 9])          ; ((1 3 5) (2 4) (7 9))
(partition-by even? [1 3 5 2 4 7 9])         ; ((1 3 5) (2 4) (7 9))

;; split-at

(split-at 2 [1 2 3 4 5]) ; [(1 2) (3 4 5)]

;; splitv-at
;; the same just using vectors

;; split-with

(split-with (partial >= 3) [1 2 3 4 5]) ; [(1 2 3) (4 5)]
(split-with (partial <= 3) [1 2 3 4 5]) ; [() (1 2 3 4 5)]

;; filter

;; filter-in
(filter even? (range 10)) ; (0 2 4 6 8)

(filter #(= (count %) 1) ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""]) ; ("a" "b" "n" "f" "q")

(filter #(> (second %) 100)
        {:a 1
         :b 2
         :c 101
         :d 102
         :e -1})
; ([:c 101] [:d 102])

;; pred is called with key/value pairs
;; #p [:a 1]
;; #p [:b 2]
;; #p [:c 101]
;; #p [:d 102]
;; #p [:e -1]

(let [filter-transducer (filter odd?)]
  (transduce filter-transducer conj (range 10)))
; [1 3 5 7 9]

;; shuffle

(shuffle '(1 2 3)) ; [2 1 3]
(shuffle '(1 2 3)) ; [1 2 3]
(shuffle '(1 2 3)) ; [3 1 2]

(repeatedly 3 #(shuffle [1 2 3]))  ; ([2 1 3] [2 1 3] [3 2 1])
(repeatedly 3 #(shuffle [1 2 3]))  ; ([3 2 1] [3 1 2] [3 1 2])
(repeatedly 3 #(shuffle [1 2 3]))  ; ([2 3 1] [1 2 3] [2 3 1])

;; ;;;;;;;;;
;; Rearrange
;; ;;;;;;;;;

;; reverse
;; sort
;; sort-by
;; compare (see examples above)

;; reverse

(reverse '(1 2 3))              ; (3 2 1)
(reverse "clojure")             ; (\e \r \u \j \o \l \c)
(apply str (reverse "clojure")) ; "erujolc"

;; sort

(sort [3 1 2 4])                          ; (1 2 3 4)
(sort > (vals {:foo 5, :bar 2, :baz 10})) ; (10 5 2)

;; sort-by

(sort-by count   ["aaa" "bb" "c"])    ; ("c" "bb" "aaa")
(sort-by first   [[2 2] [3 2] [1 2]]) ; ([1 2] [2 2] [3 2])
(sort-by first > [[2 2] [3 2] [1 2]]) ; ([3 2] [2 2] [1 2])

;; ;;;;;;;;;;;;;
;; Process items
;; ;;;;;;;;;;;;;

;; map
;; pmap
;; map-indexed
;; mapcat  (see examples above)
;; for     (see examples above)
;; replace (see examples above)
;; seque

;; map

(map inc [1 2 3 4 5])   ; (2 3 4 5 6)
(map + [1 2 3] [4 5 6]) ; (5 7 9)

(apply map vector [[:a :b :c]
                   [:d :e :f]
                   [:g :h :i]])
; ([:a :d :g] [:b :e :h] [:c :f :i])

(map #(vector (first %) (* 2 (second %)))
     {:a 1 :b 2 :c 3})
; ([:a 2] [:b 4] [:c 6])

;; pmap

;; see example above with xml-seq
(pmap inc [1 2 3 4 5]) ; (2 3 4 5 6)

;; map-indexed

(map-indexed vector "clojure")                     ; ([0 \c] [1 \l] [2 \o] [3 \j] [4 \u] [5 \r] [6 \e])
(map-indexed (fn [idx item] [idx item]) "clojure") ; ([0 \c] [1 \l] [2 \o] [3 \j] [4 \u] [5 \r] [6 \e])

;; seque

(->> (range 1 100)
     (map #(do (println "Loading" % "...") %))
     (map #(do (println "Processing" % "...") %))
     (take 10)
     (doall))
; (out) Loading 1 ...
; (out) Loading 2 ...
; (out) Loading 3 ...
; (out) Loading 4 ...
; (out) Loading 5 ...
; (out) Loading 6 ...
; (out) Loading 7 ...
; (out) Loading 8 ...
; (out) Loading 9 ...
; (out) ...
; (out) Processing 1 ...
; (out) Processing 2 ...
; (out) Processing 3 ...
; (out) Processing 4 ...
; (out) Processing 5 ...
; (out) Processing 6 ...
; (out) Processing 7 ...
; (out) Processing 8 ...
; (out) Processing 9 ...
; (out) ...

(->> (range 1 100)
     (seque 10) ; n elements in queue, can be less
     (map #(do (println "Loading" % "...") %))
     (map #(do (println "Processing" % "...") %))
     (take 10)
     (doall))
; (out) Loading 1 ...
; (out) Processing 1 ...
; (out) Loading 2 ...
; (out) Processing 2 ...
; (out) Loading 3 ...
; (out) Processing 3 ...
; (out) Loading 4 ...
; (out) Processing 4 ...
; (out) Loading 5 ...
; (out) Processing 5 ...
; (out) Loading 6 ...
; (out) Processing 6 ...
; (out) Loading 7 ...
; (out) Processing 7 ...
; (out) Loading 8 ...
; (out) Processing 8 ...
; (out) Loading 9 ...
; (out) Processing 9 ...
; (out) Loading 10 ...
; (out) Processing 10 ...

(comment
  (javadoc java.io.File))

;; `memfn` treats a Java method as a first-class fn
;; Basically converts a Java method to a Clojure function.
(defn search-files [q root n]
  (->> (java.io.File. root)
       file-seq
       (map (memfn getPath))
       (filter #(re-find q %))
       (seque n)))

#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(defn paginate [root n]
  (let [search (search-files #"\.clj$" root 1000)]
    (loop [results (partition n search)]
      (println (with-out-str (clojure.pprint/write (first results))))
      (println "more?")
      (when (= "y" (read-line))
        (recur (rest results))))))

;; Do this in the REPL!
;; Note: seque is now producing 997 items ahead.
;; (paginate "/home/schmidh/Gitrepos/" 3)
;; ("/Users/reborg/.atom/fixtures/bad.clj"
;;  "/Users/reborg/.atom/fixtures/empty.clj"
;;  "/Users/reborg/.atom/fixtures/good.clj")
;; more?

;; ;;;;;;;;;;;;;;;
;; ; Using a Seq ;
;; ;;;;;;;;;;;;;;;

;; ;;;;;;;;;;;;
;; Extract item
;; ;;;;;;;;;;;;

;; first
;; second
;; last
;; rest
;; next
;; ffirst
;; nfirst
;; fnext
;; nnext
;; nth
;; nthnext
;; rand-nth
;; when-first
;; max-key
;; min-key

;; first

(first '(:alpha :bravo :charlie)) ; :alpha
(first nil)                       ; nil
(first [])                        ; nil
(first [nil])                     ; nil

;; second
(second '(:alpha :bravo :charlie)) ; :bravo
(second [1 2 3])                   ; 2
(second {:a 1 :b 2 :c 3})          ; [:b 2]
(second #{1 2 3})                  ; 3 (NOTE:)
(next   #{1 2 3})                  ; (3 2)
(second [1 2])                     ; 2
(second [1])                       ; nil
(second [])                        ; nil
(second nil)                       ; nil

;; last

(last [1 2 3 4 5])                                          ; 5
(last ["a" "b" "c" "d" "e"])                                ; "e"
(last {:a 1 :b 2 :c 3 :d 4})                                ; [:d 4]
(last {:a 1 :b 2 :c 3 :d 4 :e 5})                           ; [:e 5]
(last {:a 1 :b 2 :c 3 :d 4 :e 5 :f 6 :g 7 :h 8 :i 9})       ; [:a 1] (NOTE:Oops. Be careful with maps and sets)
(last {:a 1 :b 2 :c 3 :d 4 :e 5 :f 6 :g 7 :h 8 :i 9 :j 10}) ; [:a 1]
(last [])                                                   ; nil

;; rest

;; always returns a seq
(rest [1 2 3 4 5])             ; (2 3 4 5)
(rest ["a" "b" "c" "d" "e"])   ; ("b" "c" "d" "e")
(rest '())                     ; ()
(rest nil)                     ; ()

;; next

;; can return nil
(next '(:alpha :bravo :charlie))         ; (:bravo :charlie)
(next (next '(:one :two :three)))        ; (:three)
(next (next (next '(:one :two :three)))) ; nil
(next nil)                               ; nil

;; ffirst

(ffirst '([])) ; nil
(ffirst ['(a b c) '(b a c)]) ; a
(ffirst  '([a b c] [b a c])) ; a

;; nfirst

(nfirst [])                                    ; nil
(nfirst ['(a b c) '(b a c) '(c b a) '(a c b)]) ; (b c)
(nfirst {:a 1, :b 2, :c 3, :d 4})              ; (1)

;; fnext

(fnext ['(a b c) '(b a c)]) ; (b a c)
(fnext '([a b c] [b a c]))  ; [b a c]
(fnext {:a 1 :b 2 :c 3})    ; [:b 2] (NOTE: Looks dangerous to use a map!)
(fnext [])                  ; nil
(fnext [1])                 ; nil

;; nnext

(nnext '(1 2 3))                              ; (3)
(nnext [])                                    ; nil
(nnext ['(a b c) '(b a c) '(c b a) '(a c b)]) ; ((c b a) (a c b))
(nnext {:a 1, :b 2, :c 3, :d 4})              ; ([:c 3] [:d 4])
(nnext #{:a :b :c})                           ; (:a) (NOTE: Oops)

;; nth

(def my-seq ["a" "b" "c" "d"])

(nth my-seq 0)        ; "a"
(nth my-seq 1)        ; "b"
;; (nth [] 0)         ; (err) Execution error (IndexOutOfBoundsException)
(nth [0 1 2] 77 1337) ; 1337

;; nthnext

(nthnext (range 10) 3)      ; (3 4 5 6 7 8 9)
(nthnext [] 3)              ; nil
(nthnext [1 2 3 4 5 6 7] 4) ; (5 6 7)

;; rand-nth

(let [food [:ice-cream :steak :apple]]
  (rand-nth food))
; :steak

;; when-first

(when-first [a [1 2 3]] a) ; 1
(when-first [a []]  a)     ; nil
(when-first [a nil] a)     ; nil

;; max-key

(max-key count "asd" "bsd" "dsd" "long word")         ; "long word"
(max-key count ["asd" "bsd" "dsd" "long word"])       ; ["asd" "bsd" "dsd" "long word"]
(apply max-key count ["asd" "bsd" "dsd" "long word"]) ; "long word"

(key (apply max-key val {:a 3 :b 7 :c 9}))            ; :c
(max-key val {:a 3 :b 7 :c 9})                        ; {:a 3, :b 7, :c 9}
(apply max-key val {:a 3 :b 7 :c 9})                  ; [:c 9]

;; min-key

(letfn [(distance-squared
          ;; "Euclidean distance between two collections considered as coordinates"
          [c1 c2]
          (->> (map - c1 c2)
               (map #(* % %))
               (reduce +)))
        (rgb-to-key-colour
          ;; "Find colour in colour map closest to the supplied [r g b] triple"
          [rgb-triple colour-map]
          (colour-map
           (apply min-key (partial distance-squared rgb-triple) (keys colour-map))))]
  (let [key-colours
        {[224 41 224]  :purple
         [24 180 46]   :green
         [12 129 245]  :blue
         [254 232 23]  :yellow
         [233 233 233] :white
         [245 27 55]   :red
         [231 119 41]  :orange}]
    (rgb-to-key-colour [255 0 0] key-colours)))
; :red

;; ;;;;;;;;;;;;;;
;; Construct coll
;; ;;;;;;;;;;;;;;

;; zipmap
;; into (see examples above)
;; reduce
;; reductions
;; set
;; vec
;; into-array
;; to-array-2d
;; mapv
;; filterv

;; zipmap

(zipmap [:a :b :c :d :e] [1 2 3 4 5]) ; {:a 1, :b 2, :c 3, :d 4, :e 5}
(zipmap [:a :b :c] [1 2 3 4])         ; {:a 1, :b 2, :c 3}
(zipmap [:a :b :c] [1 2])             ; {:a 1, :b 2}

;; reduce

(reduce + [1 2 3 4 5])   ; 15
(reduce + [])            ; 0
(reduce + [1])           ; 1
(reduce + [1 2])         ; 3
(reduce + 1 [])          ; 1
(reduce + 1 [2 3])       ; 6

(reduce conj #{} [:a :b :c]) ; #{:c :b :a}
(into #{} [:a :b :c])        ; #{:c :b :a}

(reduce
 (fn [primes number]
   (if (some zero? (map (partial mod number) primes))
     primes
     (conj primes number)))
 [2]
 (take 100 (iterate inc 3)))
; [2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97 101]

(letfn [(factorial [n]
          (reduce * (range 1 (inc n))))]
  (factorial 5))
; 120

;; reductions

(reductions + [1 1 1 1]) ; (1 2 3 4)

(letfn [(factorials [n]
          (reductions * (range 1 (inc n))))]
  (factorials 10))
; (1 2 6 24 120 720 5040 40320 362880 3628800)

;; set

(set '(1 1 2 3 2 4 5 5)) ; #{1 4 3 2 5}

;; vec

(vec '(1 2 3)) ; [1 2 3]

;; into-array

(into-array (range 4))        ; [0, 1, 2, 3]

;; (into-array [2 "4" "8" 5]) ; (err) Execution error (IllegalArgumentException)

;; to-array-2d

(def a (to-array-2d [[1 2 3] [4 5 6]]))
(alength a)          ; 2
(alength (aget a 0)) ; 3
(aget a 0 0)         ; 1
(aget a 0 1)         ; 2
(aget a 0 2)         ; 3
(aget a 1 0)         ; 4
(aget a 1 1)         ; 5
(aget a 1 2)         ; 6

;; mapv

(mapv inc  [1 2 3 4 5])    ; [2 3 4 5 6]
(mapv inc '(1 2 3 4 5))    ; [2 3 4 5 6]
(mapv + '(1 2 3) '(4 5 6)) ; [5 7 9]

;; filterv

(filterv even? (range 10)) ; [0 2 4 6 8]

;; ;;;;;;;;;;
;; Pass to fn
;; ;;;;;;;;;;

;; apply

(str        "str1" "str2" "str3")  ; "str1str2str3"
(apply str ["str1" "str2" "str3"]) ; "str1str2str3"

(apply max  [1 2 3]) ; 3
(apply max #{1 2 3}) ; 3

(apply map vector [[:a :b]
                   [:c :d]
                   [:e :f]])
; ([:a :c :e] [:b :d :f])

(map vector [:a :b] [:c :d] [:e :f])
; ([:a :c :e] [:b :d :f])

(apply + 1 2 '(3 4)) ; 10
(+ 1 2 3 4)          ; 10

;; ;;;;;;
;; Search
;; ;;;;;;

;; some   (see examples above)
;; filter (see examples above)

;; ;;;;;;;;;;;;;;;;
;; Force evaluation
;; ;;;;;;;;;;;;;;;;

;; doseq (see examples above)
;; dorun
;; doall
;; run!

;; dorun

;; https://clojuredocs.org/clojure.core/dorun#example-559b66f5e4b00f9508fd66f6
;; Good example used in database programming

;; doall

;; https://clojuredocs.org/clojure.core/doall#example-559b6ac2e4b020189d740548
;; Good example used in database programming

;; run!

(run! prn (range 5)) ; nil
; (out) 0
; (out) 1
; (out) 2
; (out) 3
; (out) 4

;; ;;;;;;;;;;;;;;;;
;; Check for forced
;; ;;;;;;;;;;;;;;;;

;; realized?

(realized? (future (Thread/sleep 1000))) ; false

(let [f (future)]
  @f
  (realized? f))
; true

;; ;;;;;;;;;;;;;;;;;
;; ;; Transducers ;;
;; ;;;;;;;;;;;;;;;;;

;; http://clojure.org/reference/transducers
;; Composition of the transformer runs right-to-left
;; but builds a transformation stack that runs left-to-right.

;; 'https://stackoverflow.com/questions/26317325/can-someone-explain-clojure-transducers-to-me-in-simple-terms/26322910#26322910'

;; nested calls
(reduce + (filter odd? (map #(+ 2 %) (range 0 10)))) ; 35

;; functional composition
(def xform-1
  (comp
   (partial filter odd?)
   (partial map #(+ 2 %))))

(reduce + (xform-1 (range 0 10))) ; 35

;; threading macro
(defn xform-2 [xs]
  (->> xs
       (map #(+ 2 %))
       (filter odd?))) ; #'cheatsheet.core/xform-2

(reduce + (xform-2 (range 0 10))) ; 35

;; transducers
(def xform-3
  (comp
   (map #(+ 2 %))
   (filter odd?)))

(transduce xform-3 + (range 0 10)) ; 35

;; ;;;;;;;;;;;;;
;; Off the shelf
;; ;;;;;;;;;;;;;

;; map            (see examples above)
;; mapcat         (see examples above)
;; filter         (see examples above)
;; remove         (see examples above)
;; take           (see examples above)
;; take-while     (see examples above)
;; take-nth       (see examples above)
;; drop           (see examples above)
;; drop-while     (see examples above)
;; replace        (see examples above)
;; partition-by   (see examples above)
;; partition-all  (see examples above)
;; keep           (see examples above)
;; keep-indexed   (see examples above)
;; map-indexed    (see examples above)
;; distinct       (see examples above)
;; interpose      (see examples above)
;; cat
;; dedupe         (see examples above)
;; random-sample  (see examples above)
;; halt-when
;; partitionv-all (see examples above)

;; cat

(into [] (comp cat cat (map inc)) [[[1] [2]] [[3] [4]]]) ; [2 3 4 5]

(into [] cat [[[1] [2]] [[3] [4]]])                      ; [[1] [2] [3] [4]]
(into [] (comp cat cat) [[[1] [2]] [[3] [4]]])           ; [1 2 3 4]
(into [] (comp cat cat (map inc)) [[[1] [2]] [[3] [4]]]) ; [2 3 4 5]

(repeat 5 :a) ; (:a :a :a :a :a)

(into
 []
 (map-indexed (fn [index val] (repeat index val)))
 (range 5))
; [() (1) (2 2) (3 3 3) (4 4 4 4)]

(into
 []
 (comp
  (map-indexed (fn [index val] (repeat index val)))
  cat)
 (range 5))
; [1 2 2 3 3 3 4 4 4 4]

;; [Janet explains Clojure transducers](https://www.youtube.com/watch?v=M8dUPhDYp_M&pp=ygUSdHJhbnNkdWNlciBjbG9qdXJl)

;; (time
;;  (->> (range 100000000)
;;       (map inc)
;;       (filter even?)
;;       (reduce +)))
; (out) "Elapsed time: 10137.015369 msecs"
; 2500000050000000

;; transducers are more performant on large datasets
;; (time
;;  (transduce
;;   (comp (map inc) (filter even?))
;;   +
;;   (range 100000000)))
; (out) "Elapsed time: 7407.423421 msecs"
; 2500000050000000

;; halt-when

(let [vowels (set "aeiou")]
  (transduce
   (remove vowels)
   str
   "hello"))
; "hll"

(let [letters (set "abcdefghijklmnopqrstuvwxyz")
      vowels  (set "aeiou")]
  (transduce
   (comp (remove vowels)
         (halt-when (complement letters)))
   str "hello"))
; "hll"

(let [letters (set "abcdefghijklmnopqrstuvwxyz")
      vowels  (set "aeiou")]
  (transduce
   (comp (remove vowels)
         (halt-when (complement letters)))
   str "hello world"))
; \space

;; ;;;;;;;;;;;;;;;
;; Create your own
;; ;;;;;;;;;;;;;;;

;; TODO: not really good examples
;; completing
;; ensure-reduced
;; unreduced

;; completing

(transduce (map inc)             -  0 (range 10)) ;  55
(transduce (map inc) (completing -) 0 (range 10)) ; -55

;; ensure-reduced

;; unreduced

;; ;;;
;; Use
;; ;;;

;; into
;; sequence
;; transduce
;; eduction

;; into

(into
 []
 (comp (map #(+ 2 %))
       (filter odd?))
 (range 10))
; [3 5 7 9 11]

;; sequence

(sequence
 (comp (map #(+ 2 %))
       (filter odd?))
 (range 10))
; (3 5 7 9 11)

;; transduce

(transduce
 (comp (map #(+ 2 %))
       (filter odd?))
 conj
 (range 10))
; [3 5 7 9 11]

;; eduction

(eduction (map inc) [1 2 3])                  ; (2 3 4)
(eduction (filter even?) (range 5))           ; (0 2 4)
(eduction (filter even?) (map inc) (range 5)) ; (1 3 5)

;; ;;;;;;;;;;;;;;;;;
;; Early termination
;; ;;;;;;;;;;;;;;;;;

;; reduced
;; reduced?
;; deref

;; reduced

(reduce (fn [a v] (+ a v)) (range 10)) ; 45

(reduce (fn [a v] (if (< a 100) (+ a v) (reduced :big))) (range 10)) ; 45
(reduce (fn [a v] (if (< a 100) (+ a v) (reduced :big))) (range 20)) ; :big
(reduce (fn [a v] (if (< a 100) (+ a v) (reduced :big))) (range))    ; :big

;; reduced?

(reduced? :foo)           ; false
(reduced? (reduced :foo)) ; true

;; deref

(def b (atom 0))

(deref b) ; 0
@b        ; 0

;; ;;;;;;;;
;; ;; IO ;;
;; ;;;;;;;;

;; (System/getProperty "java.io.tmpdir")

;; ;;;;;;;;;;;
;; to/from ...
;; ;;;;;;;;;;;

;; spit

;; io/IOFactory ; to see the options (:append, :encoding)

(let [tmpdir (System/getProperty "java.io.tmpdir")]
  (spit (str tmpdir "/" "test.txt") "test"))

;; slurp

(let [tmpdir (System/getProperty "java.io.tmpdir")]
  (slurp (str tmpdir "/" "test.txt")))
; "test"

;; ;;;;;;;;
;; to *out*
;; ;;;;;;;;

;; pr
;; prn
;; print
;; printf
;; println
;; newline
;; pp/print-table

;; pr

(pr "clojure")
; (out) "clojure"

;; prn

(prn "clojure")
; (out) "clojure"

;; print

(print "clojure")
; (out) clojure

;; printf

(printf "1 + 2 is %s%n" 3)
; (out) 1 + 2 is 3

;; println

(println "clojure")
; (out) clojure

;; newline

(newline)
; (out)

;; pp/print-table

(let [big-int (:members (refl/reflect clojure.lang.BigInt))]
  (pp/print-table [:name :type :flags] (sort-by :name big-int)))

; (out) |               :name |                :type |                     :flags |
; (out) |---------------------+----------------------+----------------------------|
; (out) |                 ONE |  clojure.lang.BigInt |  #{:public :static :final} |
; (out) |                ZERO |  clojure.lang.BigInt |  #{:public :static :final} |
; (out) |                 add |                      |                 #{:public} |
; (out) |              bipart | java.math.BigInteger |          #{:public :final} |
; (out) |           bitLength |                      |                 #{:public} |
; (out) |           byteValue |                      |                 #{:public} |
; (out) | clojure.lang.BigInt |                      |                #{:private} |
; (out) |         doubleValue |                      |                 #{:public} |
; (out) |              equals |                      |                 #{:public} |
; (out) |          floatValue |                      |                 #{:public} |
; (out) |      fromBigInteger |                      |         #{:public :static} |
; (out) |            fromLong |                      |         #{:public :static} |
; (out) |            hashCode |                      |                 #{:public} |
; (out) |              hasheq |                      |                 #{:public} |
; (out) |            intValue |                      |                 #{:public} |
; (out) |           longValue |                      |                 #{:public} |
; (out) |               lpart |                 long |          #{:public :final} |
; (out) |                  lt |                      |                 #{:public} |
; (out) |            multiply |                      |                 #{:public} |
; (out) |            quotient |                      |                 #{:public} |
; (out) |           remainder |                      |                 #{:public} |
; (out) |    serialVersionUID |                 long | #{:private :static :final} |
; (out) |          shortValue |                      |                 #{:public} |
; (out) |        toBigDecimal |                      |                 #{:public} |
; (out) |        toBigInteger |                      |                 #{:public} |
; (out) |            toString |                      |                 #{:public} |
; (out) |             valueOf |                      |         #{:public :static} |

;; ;;;;;;;;;
;; to writer
;; ;;;;;;;;;

;; pp/pprint
;; pp/cl-format

;; pp/pprint

(let [big-map (zipmap
               [:a :b :c :d :e]
               (repeat
                (zipmap [:a :b :c :d :e]
                        (take 5 (range)))))]
  (pp/pprint big-map))
; (out) {:a {:a 0, :b 1, :c 2, :d 3, :e 4},
; (out)  :b {:a 0, :b 1, :c 2, :d 3, :e 4},
; (out)  :c {:a 0, :b 1, :c 2, :d 3, :e 4},
; (out)  :d {:a 0, :b 1, :c 2, :d 3, :e 4},
; (out)  :e {:a 0, :b 1, :c 2, :d 3, :e 4}}

;; pp/cl-format

(pp/cl-format nil "~:d" 1234567) ; "1,234,567"

;; ;;;;;;;;;
;; to string
;; ;;;;;;;;;

;; format
;; with-out-str
;; pr-str
;; prn-str
;; print-str
;; println-str

;; format

(format "Hello there, %s" "Bob") ; "Hello there, Bob"

;; with-out-str

(with-out-str (println "this returns as string")) ; "this returns as string\n"

;; pr-str

(let [x [1 2 3 4 5]]
  (pr-str x))
; "[1 2 3 4 5]"

;; prn-str

(let [x [1 2 3 4 5]]
  (prn-str x))
; "[1 2 3 4 5]\n"

(prn-str 1 "foo" \b \a \r {:a 2})
; "1 \"foo\" \\b \\a \\r {:a 2}\n"

;; print-str

(print-str 1 "foo" \b \a \r {:a 2})
; "1 foo b a r {:a 2}"

;; println-str

(println-str 1 "foo" \b \a \r {:a 2})
; "1 foo b a r {:a 2}\n"

;; ;;;;;;;;;
;; from *in*
;; ;;;;;;;;;

;; read-line
;; edn/read

(edn/read (java.io.PushbackReader. (io/reader (.getBytes ":edn")))) ; :edn

;; ;;;;;;;;;;;
;; from reader
;; ;;;;;;;;;;;

;; line-seq (see examples above)
;; edn/read (see examples above)

;; ;;;;;;;;;;;
;; from string
;; ;;;;;;;;;;;

;; with-in-str (see examples above)
;; edn/read-string
;; reader-edn/read-string

(let [sample-map        {:foo "bar" :bar "foo"}
      sample-map-as-edn (prn-str sample-map)]
  (print   "Sample map as EDN:       " sample-map-as-edn)
  (println "Convert EDN back to map: " (edn/read-string sample-map-as-edn)))
; (out) Sample map as EDN:        {:foo "bar", :bar "foo"}
; (out) Convert EDN back to map:  {:foo bar, :bar foo}

;; ;;;;
;; Open
;; ;;;;

;; with-open (see examples above)
;; io/reader (see examples above)
;; io/writer (see examples above)
;; io/input-stream
;; io/output-stream

;; io/input-stream

(letfn [(file->bytes [file]
          (with-open [xin  (io/input-stream file)
                      xout (java.io.ByteArrayOutputStream.)]
            (io/copy xin xout)
            (.toByteArray xout)))]
  (file->bytes (io/file (str (System/getProperty "java.io.tmpdir") "/" "test.txt"))))
; [116, 101, 115, 116]

;; io/output-stream

(with-open [o (io/output-stream (str (System/getProperty "java.io.tmpdir") "/" "test.txt"))]
  (.write o 65)) ; Writes 'A'

;; ;;;;
;; Misc
;; ;;;;

;; flush
;; file-seq (see examples above)
;; *in*
;; *out*
;; *err*
;; io/file (see examples above)
;; io/copy (see examples above)
;; io/delete-file
;; io/resource
;; io/as-file
;; io/as-url
;; io/as-relative-path

;; flush

(doseq [x (range 20)]
  (Thread/sleep 100)
  (pr x)
  (flush))

(doseq [x (range 20)]
  (Thread/sleep 100)
  (pr x))

;; *out*
;; *err*

(binding [*out* *err*]
  (prn "prn can be used")
  (println "println can also be used"))
; (err) "prn can be used"
; (err) println can also be used

;; io/delete-file

(io/delete-file (str (System/getProperty "java.io.tmpdir") "/" "test.txt"))

;; io/resource

;; (with-open [o (io/output-stream "test.txt")]
;;   (.write o 65)) ; Writes 'A'

;; (slurp (io/resource "test.txt"))

;; io/as-file

(.exists (io/as-file "dummy.txt"))   ; false
(.exists (io/as-file "project.clj")) ; true

;; io/as-url

(import 'java.io.File)

(io/as-url (File. "/tmp"))           ; #object[java.net.URL 0x3d824c23 "file:/tmp/"]
(io/as-url "http://clojuredocs.org") ; #object[java.net.URL 0x3c91aa2d "http://clojuredocs.org"]

;; io/as-relative-path

(io/as-relative-path "this/is/a/relative/path")      ; "this/is/a/relative/path"
;; (io/as-relative-path "/this/is/an/absolute/path") ; (err) /this/is/an/absolute/path is not a relative path
