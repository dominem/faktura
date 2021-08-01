(ns faktura.specs.address
  (:require [clojure.spec.alpha :as s]
            [faktura.specs.common :as common]))

(s/def ::city ::common/non-empty-str)
(s/def ::zip-code ::common/non-empty-str)
(s/def ::street ::common/non-empty-str)
(s/def ::address (s/keys :req-un [::city ::zip-code ::street]))
