(ns faktura.core
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [faktura.calculator :refer [calculate]]
            [faktura.pdf :refer [pdf!]]
            [faktura.specs.invoice :as invoice :refer [conform-invoice]]))

(defn -main
  [invoice-edn-path invoice-out-path]
  (-> invoice-edn-path
      (slurp)
      (read-string)
      (conform-invoice)
      (calculate)
      (pdf! invoice-out-path)))

(comment
  (first (gen/sample (s/gen ::invoice/invoice)))
  (-> (first (gen/sample (s/gen ::invoice/invoice)))
      (conform-invoice)
      (calculate)
      (pdf! "out/invoice_test.pdf"))
  (-main "in/invoice.edn" "out/invoice_20210801_1429.pdf"))
