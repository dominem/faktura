(ns faktura.specs.invoice
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [faktura.specs.seller :as seller]
            [faktura.specs.buyer :as buyer]
            [faktura.specs.items :as items]))

(def filename-regex #"^[a-zA-Z0-9_]+$")
(def filename?
  (s/with-gen
    (s/and string? (partial re-matches filename-regex))
    #(gen/fmap (fn [s] (str "filename" s)) (gen/string-alphanumeric))))

(def date-regex #"^[1-9][0-9]*-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
(def date?
  (s/with-gen
   (s/and string? (partial re-matches date-regex))
   #(gen/fmap (fn [[y m d]] (format "%s-%s-%s" y m d))
              (gen/tuple (s/gen #{2019 2020 2021})
                         (s/gen #{10 11 12})
                         (s/gen (set (range 10 31)))))))

(def money-formatter? #{:pl :pl-append-PLN})

(s/def ::name string?)
(s/def ::filename filename?)
(s/def ::issue-date date?)
(s/def ::sale-date date?)
(s/def ::payment-deadline #{14})
(s/def ::payment-method #{"przelew"})
(s/def ::currency #{"PLN"})
(s/def ::locale #{"pl"})
(s/def ::money-formatter money-formatter?)
(s/def ::money-formatter-to-pay money-formatter?)
(s/def ::paid (s/and number? #(>= % 0)))
(s/def ::invoice
  (s/keys
    :req-un [::name
             ::filename
             ::issue-date
             ::sale-date
             ::payment-deadline
             ::payment-method
             ::currency
             ::locale
             ::money-formatter
             ::money-formatter-to-pay
             ::paid
             ::seller/seller
             ::buyer/buyer
             ::items/items]))
