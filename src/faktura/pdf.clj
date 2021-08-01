(ns faktura.pdf
  (:require [clj-pdf.core :as clj-pdf]
            [faktura.header :refer [header]]
            [faktura.contractors :refer [contractors]]
            [faktura.items :refer [items]]
            [faktura.summary :refer [summary]]))

(defn pdf-metadata
  [invoice]
  {:title                  (:name invoice)
   :footer                 "Strona: "
   :pages                  true
   :register-system-fonts? true
   :font                   {:ttf-name "resources/fonts/courier_prime/CourierPrime-Regular.ttf"
                            :encoding :unicode
                            :size     8}})

(defn pdf!
  [invoice out-path]
  (clj-pdf/pdf
    [(pdf-metadata invoice)
     (header invoice)
     (contractors invoice)
     (items invoice)
     (summary invoice)]
    out-path))
