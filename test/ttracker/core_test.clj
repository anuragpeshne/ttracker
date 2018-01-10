(ns ttracker.core-test
  (:require [clojure.test :refer :all]
            [ttracker.core :refer :all]))

(deftest test-main
  (is (-main 12 12)
      1))

(deftest test-osx-notify
  (testing "Notification on OSX"
    (is (let [process (osx-notify "Test body" "Test heading" "test sub")]
          (.waitFor process))
        0)))
