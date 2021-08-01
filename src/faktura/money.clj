(ns faktura.money
  (:require [clojurewerkz.money.format :as mf])
  (:import (org.joda.money.format MoneyFormatterBuilder)))

(def money-formatters
  {:pl
   (-> (MoneyFormatterBuilder.)
       mf/append-amount-localized
       (.toFormatter))

   :pl-append-PLN
   (-> (MoneyFormatterBuilder.)
       mf/append-amount-localized
       (.appendLiteral " ")
       mf/append-currency-symbol-localized
       (.toFormatter))})

(defn format-money
  [money formatter locale]
  (mf/format money (get money-formatters formatter) locale))
