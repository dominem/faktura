(ns faktura.header)

(defn logo [invoice]
  [:image {:scale 20} (:logo (:seller invoice))])

(defn c
  [borders text]
  [:cell {:set-border borders :leading 8}
   [:paragraph {:size 8} text]])
(def cb (partial c [:bottom]))
(def cbl (partial c [:bottom :left]))
(def cbr (partial c [:bottom :right]))

(defn header-table
  [invoice]
  [:table
   {:align       :justified
    :header      [[:paragraph {:size 14 :leading 14} (:name invoice)]]
    :border      true
    :cell-border true
    :widths      [60 40 60 40]}
   [(cbl "Data wystawienia:") (cb (:issue-date invoice))
    (cbl "Data sprzedaży:") (cbr (:sale-date invoice))]
   [(cbl "Termin płatności:") (cb (:payment-deadline-date invoice))
    (cbl "Metoda płatności:") (cbr (:payment-method invoice))]])

(defn header
  [invoice]
  (if (:logo (:seller invoice))
   [:table {:border false :widths [30 70]}
    [[:cell (logo invoice)] [:cell (header-table invoice)]]]
   [:table {:border false :widths [100]}
    [[:cell (header-table invoice)]]]))
