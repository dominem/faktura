(ns faktura.specs.contractor
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.string :refer [join]]
            [faktura.specs.address :as address]))

(def nip-regex #"\d{10}")
(def nip?
  (s/with-gen
    (s/and string? (partial re-matches nip-regex))
    #(gen/fmap join (gen/shuffle (set (range 10))))))
(s/def ::name string?)
(s/def ::nip nip?)
(s/def ::contractor (s/keys :req-un [::name ::address/address ::nip]))
