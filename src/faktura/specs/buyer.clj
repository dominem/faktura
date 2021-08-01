(ns faktura.specs.buyer
  (:require [clojure.spec.alpha :as s]
            [faktura.specs.contractor :as contractor]))

(s/def ::buyer ::contractor/contractor)
