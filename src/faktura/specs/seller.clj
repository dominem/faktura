(ns faktura.specs.seller
  (:require [clojure.spec.alpha :as s]
            [faktura.specs.bank :as bank]
            [faktura.specs.contractor :as contractor]))

(s/def ::logo string?)
(s/def ::seller
  (s/merge ::contractor/contractor
           (s/keys :opt-un [::bank/bank ::logo])))
