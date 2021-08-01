(defproject faktura "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [clj-pdf "2.5.8"]
                 [clojure.java-time "0.3.2"]
                 [clojurewerkz/money "1.10.0"]
                 [org.clojure/test.check "0.10.0"]]
  :repl-options {:init-ns faktura.core}
  :main faktura.core)
