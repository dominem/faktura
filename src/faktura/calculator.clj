(ns faktura.calculator
  (:require [clojurewerkz.money.amounts :as ma]
            [clojurewerkz.money.currencies :as mc]
            [java-time :as t]
            [faktura.constants :refer [locale-pl date-format]])
  (:import [java.util Locale]))

(defn calculate-deadline-date
  [invoice]
  (->> (t/days (:payment-deadline invoice))
       (t/plus (t/local-date date-format (:issue-date invoice)))
       (str)
       (assoc invoice :payment-deadline-date)))

(defn determine-currency
  [invoice]
  (update invoice :currency mc/for-code))

(defn set-locale
  [invoice]
  (update invoice :locale #(Locale. %)))

(defn set-unit-for-item
  [item]
  (update item :unit #(case % :service "usł." "-")))

(defn set-unit-for-items
  [invoice]
  (update invoice :items (partial mapv set-unit-for-item)))

(defn set-currency-for-items
  [invoice]
  (update invoice :items
          (partial mapv #(assoc % :currency (:currency invoice)))))

(defn calculate-net-price
  [item]
  (->> (ma/round (ma/amount-of (:currency item) (:net-price item)) 2 :floor)
       (assoc item :net-price)))

(defn calculate-net-value
  [item]
  (->> (:amount item)
       (ma/multiply (:net-price item))
       (assoc item :net-value)))

(defn calculate-tax-value
  [item]
  (->> (ma/multiply (:net-value item) (/ (:tax item) 100) :floor)
       (assoc item :tax-value)))

(defn calculate-gross-value
  [item]
  (->> (:tax-value item)
       (ma/plus (:net-value item))
       (assoc item :gross-value)))

(defn calculate-item
  [item]
  (-> item
      calculate-net-price
      calculate-net-value
      calculate-tax-value
      calculate-gross-value))

(defn calculate-items
  [invoice]
  (update invoice :items (partial mapv calculate-item)))

(defn calculate-items-total-value-for-tax
  [items-by-tax items tax value-key]
  (->> (reduce ma/plus (map value-key items))
       (assoc-in items-by-tax [tax value-key])))

(defn calculate-items-total-values-for-tax
  [items-by-tax [tax items]]
  (-> items-by-tax
      (calculate-items-total-value-for-tax items tax :net-value)
      (calculate-items-total-value-for-tax items tax :tax-value)
      (calculate-items-total-value-for-tax items tax :gross-value)))

(defn calculate-values-by-tax
  [invoice]
  (->> (:items invoice)
       (group-by :tax)
       (reduce calculate-items-total-values-for-tax {})
       (assoc invoice :values-by-tax)))

(defn calculate-total-value
  [invoice value-key]
  (->> (vals (:values-by-tax invoice))
       (map value-key)
       (reduce ma/plus)
       (assoc-in invoice [:totals value-key])))

(defn calculate-tax-summary
  [invoice]
  (-> invoice
      (calculate-total-value :net-value)
      (calculate-total-value :tax-value)
      (calculate-total-value :gross-value)))

(defn calculate-paid
  [invoice]
  (update invoice :paid (partial ma/amount-of (:currency invoice))))

(defn calculate-to-pay
  [invoice]
  (->> (:paid invoice)
       (ma/minus (get-in invoice [:totals :gross-value]))
       (assoc invoice :to-pay)))

(defn calculate-summary
  [invoice]
  (->> invoice
       calculate-paid
       calculate-to-pay))

(defn calculate
  [invoice]
  (-> invoice
      calculate-deadline-date
      determine-currency
      set-locale
      set-unit-for-items
      set-currency-for-items
      calculate-items
      calculate-values-by-tax
      calculate-tax-summary
      calculate-summary))
