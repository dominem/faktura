(ns faktura.specs.items
  (:require [clojure.spec.alpha :as s]
            [faktura.specs.common :as common]))

(s/def ::name ::common/non-empty-str)
(s/def ::unit #{:service})
(s/def ::amount pos-int?)
(s/def ::net-price (s/and number? #(> % 0.01)))
(s/def ::tax (s/and int? #(>= % 0) #(<= % 100)))
(s/def ::item (s/keys :req-un [::name ::unit ::amount ::net-price ::tax]))
(s/def ::items (s/coll-of ::item :min-count 1))
