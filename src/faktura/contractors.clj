(ns faktura.contractors)

(defn h [s] [:paragraph {:size 12} s])
(defn p [s l] [:paragraph {:size 8 :leading l} s])
(defn p-1 [s] (p s 15))
(defn p-2 [s] (p s 13))
(defn p-n [s] (p s 10))

(defn basic-data
  [contractor header]
  [(h header)
   (p-1 (:name contractor))
   (p-n (:street (:address contractor)))
   (p-n (let [{:keys [zip-code city]} (:address contractor)]
          (str zip-code " " city)))
   (p-2 (str "NIP: " (:nip contractor)))])

(defn bank-data
  [seller]
  [(p-2 (:name (:bank seller)))
   (p-n (:number (:bank seller)))])

(defn seller-cell
  [seller]
  (vec (concat [:cell] (basic-data seller "Sprzedawca") (bank-data seller))))

(defn buyer-cell
  [buyer]
  (vec (concat [:cell] (basic-data buyer "Nabywca"))))

(defn contractors
  [invoice]
  [:table {:border false :widths [50 50]}
   [(seller-cell (:seller invoice))
    (buyer-cell (:buyer invoice))]])
