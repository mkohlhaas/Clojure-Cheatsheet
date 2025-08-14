(defproject cheatsheet "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure           "1.12.0"]
                 [org.clojure/data.avl          "0.2.0"]
                 [org.clojure/tools.reader      "1.5.2"]
                 [org.flatland/ordered          "1.15.12"]
                 [org.clojure/data.int-map      "1.3.0"]
                 [org.clojure/data.priority-map "1.2.0"]]
  :plugins [[dev.weavejester/lein-cljfmt        "0.13.1"]]
  :main ^:skip-aot cheatsheet.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
