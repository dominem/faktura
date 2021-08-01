(ns faktura.summary
  (:require [faktura.money :as m]))

(defn p [text] [:paragraph {:size 6 :align :center :leading 8} text])
(defn c [meta text] [:cell (merge {:size 8 :leading 8} meta) text])
(defn cc [text] (c {:align :center} text))
(defn cr
  ([text] (c {:align :right} text))
  ([meta text] (c (merge {:align :right} meta) text)))

(defn values-by-tax
  [invoice [tax values]]
  (let [fm #(m/format-money % (:money-formatter invoice) (:locale invoice))]
    [(cc (str tax "%"))
    (cr (fm (:net-value values)))
    (cr (fm (:tax-value values)))
    (cr (fm (:gross-value values)))]))

(defn tax-totals
  [invoice]
  (let [fm #(m/format-money % (:money-formatter invoice) (:locale invoice))]
   [(cc "Razem")
    (cr (fm (:net-value (:totals invoice))))
    (cr (fm (:tax-value (:totals invoice))))
    (cr (fm (:gross-value (:totals invoice))))]))

(defn tax
  [invoice]
  ((comp vec concat)
   [:table]
   [{:border true
     :header [(p "Stawka VAT")
              (p "Wartość netto")
              (p "Kwota VAT")
              (p "Wartość brutto")]}]
   ((comp vec concat)
    (mapv (partial values-by-tax invoice)
          (sort-by key (:values-by-tax invoice)))
    [(tax-totals invoice)])))

(defn payment
  [invoice]
  (let [fm #(m/format-money % (:money-formatter-to-pay invoice) (:locale invoice))]
   [:table
    {:border false :widths [60 40]}
    [(cr "Zapłacono") (cr (fm (:paid invoice)))]
    [(cr {:style :bold} "Do zapłaty") (cr {:style :bold} (fm (:to-pay invoice)))]
    [(cr "Razem") (cr (fm (:gross-value (:totals invoice))))]
    #_[nil (cr "Słownie")]
    #_[(cr {:colspan 2} "siedem tysięcy trzysta osiemdziesiąt złotych 0/100")]]))

(defn summary
  [invoice]
  [:table {:border false :widths [50 50]}
   [[:cell (tax invoice)] [:cell (payment invoice)]]])
