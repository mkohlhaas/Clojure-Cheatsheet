(ns cheatsheet.core
  (:require [clojure.repl :refer [apropos dir doc find-doc pst source]]
            [clojure.string :as str]
            [clojure.java.javadoc :refer [javadoc]])
  (:gen-class))

;; ;;;;;;;;;;
;; ;; REPL ;;
;; ;;;;;;;;;;

;; ;;;
;; doc
;; ;;;

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

(doc clojure.core) ; nil
; (out) -------------------------
; (out) clojure.core
; (out)   Fundamental library of the Clojure language

;; ;;;;;;;; 
;; find-doc 
;; ;;;;;;;; 

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

;; ;;;;;;; 
;; apropos 
;; ;;;;;;; 

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

;; ;;; 
;; dir 
;; ;;; 

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

;; ;;;;;;
;; source
;; ;;;;;;

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

;; ;;;
;; pst
;; ;;;

(/ 1 0)
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

;; ;;;;;;;
;; javadoc
;; ;;;;;;;

(javadoc String)

(javadoc "abc")

(javadoc 1)

;; ;;;;;;;;;;;;;;
;; *print-length*
;; ;;;;;;;;;;;;;;

;; Prints 500 items in nvim. In the REPL we are toast -> prints forever)
(iterate inc 0)

;; in nvim this setting is ignored
(binding [*print-length* 10]
  (iterate inc 0))
; (0 1 2 3 4 5 6 7 8 9 ...)

;; ;;;;;;;;;;;;;
;; *print-level*
;; ;;;;;;;;;;;;;

[1 [2 [3]]]
; [1 [2 [3]]]

;; in nvim this setting is ignored
(binding [*print-level* 2]
  [1 [2 [3]]])
;; [1 [2 #]]

;; ;;;;;;;;;;;;
;; *print-meta*
;; ;;;;;;;;;;;;

(binding [*print-meta* true]
  (pr #'defmacro))
;; (out) ^{:added "1.0", :ns ^{:doc "Fundamental library of the Clojure language",
;;                             :author "Rich Hickey" #object[clojure.lang.Namespace 0x2dd56651 "clojure.core"],
;;                             :name defmacro,
;;                             :file "clojure/core.clj",
;;                             :column 1, :line 446, :macro true, :arglists ^{:line 451, :column 15} (# #), :doc "Like defn, but the resulting function name is declared as a\n  macro and will be used as a macro by the compiler when it is\n  called." #'clojure.core/defmacro}}

;; ;;;;;;;;;;;;;;;; 
;; *print-readably* 
;; ;;;;;;;;;;;;;;;; 

(binding [*print-readably* false]
  (pr "a\nb\nc"))
; (out) a
; (out) b
; (out) c

(binding [*print-readably* true]
  (pr "a\nb\nc"))
; (out) "a\nb\nc"

;; ;;;;;;;;;;;
;; *print-dup*
;; ;;;;;;;;;;;

;; `*print-dup*` is very handy when we want to write 
;; clojure code/data to a file to read in later.

;; here we are using only strings for input and output
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

