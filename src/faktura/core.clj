(ns faktura.core
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [faktura.calculator :refer [calculate]]
            [faktura.pdf :refer [pdf!]]
            [faktura.specs.invoice :as invoice]
            #_[cljfx.api :as fx]))

(defn conform-invoice
  [invoice]
  (if (s/valid? ::invoice/invoice invoice)
    invoice
    (s/explain ::invoice/invoice invoice)))

(defn generate-pdf!
  [invoice]
  (pdf! (calculate (conform-invoice invoice))))

(comment (generate-pdf! (read-string (slurp "src/faktura/data/invoice.edn"))))
(comment (generate-pdf! (read-string (slurp "src/faktura/data/invoice_ela.edn"))))
(comment (map generate-pdf! (gen/sample (s/gen ::invoice/invoice))))
(comment
  (take 1 (gen/sample (s/gen ::invoice/invoice))))

(defn -main
  []
  (generate-pdf! (read-string (slurp "src/faktura/data/invoice.edn")))
  (generate-pdf! (read-string (slurp "src/faktura/data/invoice_ela.edn")))
  #_(fx/on-fx-thread
    (fx/create-component
      {:fx/type :stage
       :showing true
       :title   "Faktura"
       :width   800
       :height  600
       :scene   {:fx/type :scene
                 :root    {:fx/type   :v-box
                           :alignment :center
                           :children  [{:fx/type :label
                                        :text    "Faktura"}]}}})))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Faktura VAT"})
