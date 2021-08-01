(ns faktura.items
  (:require [faktura.money :as m]))

(defn p [text] [:paragraph {:size 6 :align :center :leading 8} text])
(defn c [meta text] [:cell (merge {:size 8 :leading 8} meta) text])
(defn cl [text] (c {:align :left} text))
(defn cc [text] (c {:align :center} text))
(defn cr
  ([text] (c {:align :right} text))
  ([meta text] (c (merge {:align :right} meta) text)))

(defn item
  [invoice index item]
  (let [fm #(m/format-money % (:money-formatter invoice) (:locale invoice))]
   [(cr (str (+ index 1)))
    (cl (:name item))
    (cc (:unit item))
    (cr (str (:amount item)))
    (cr (fm (:net-price item)))
    (cr (str (:tax item) "%"))
    (cr (fm (:net-value item)))
    (cr (fm (:gross-value item)))]))

(def items-metadata
  {:widths [4 40 5 5 13 7 13 13]
   :header [(p "Lp.")
            (p "Nazwa")
            (p "Jedn.")
            (p "Ilość")
            (p "Cena netto")
            (p "Stawka")
            (p "Wartość netto")
            (p "Wartość brutto")]})

(defn items
  [invoice]
  (vec (concat [:table]
               [items-metadata]
               (map-indexed (partial item invoice) (:items invoice)))))
