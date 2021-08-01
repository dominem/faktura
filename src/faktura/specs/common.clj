(ns faktura.specs.common
  (:require [clojure.spec.alpha :as s]))

(s/def ::non-empty-str (s/and string? (complement empty?)))
