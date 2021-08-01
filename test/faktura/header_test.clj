(ns faktura.header-test
  (:require [clojure.test :refer :all]
            [faktura.header :refer :all]))

(deftest deadline-date-test
  (testing "7 days"
    (is (= (deadline-date "2019-01-01" 7)
           "2019-01-08")))
  (testing "14 days"
    (is (= (deadline-date "2019-01-01" 14)
           "2019-01-15")))
  (testing "60 days"
    (is (= (deadline-date "2019-01-01" 60)
           "2019-03-02"))))
