(ns faktura.specs.bank
  (:require [clojure.spec.alpha :as s]))

(def account-number-regex #"\d{2} (\d{4} ){5}\d{4}")
(def account-number?
  (s/with-gen
   (s/and string? (partial re-matches account-number-regex))
   #(s/gen #{"00 0000 0000 0000 0000 0000 0000"})))
(s/def ::name string?)
(s/def ::number account-number?)
(s/def ::bank (s/keys :req-un [::name ::number]))
