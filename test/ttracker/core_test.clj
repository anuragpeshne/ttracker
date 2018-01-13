(ns ttracker.core-test
  (:require [clojure.test :refer :all]
            [ttracker.core :refer :all]))

(deftest test-osx-notify
  (testing "Notification on OSX"
    (is (let [process (osx-notify "Test body" "Test heading" "test sub")]
          (.waitFor process))
        0)))

(deftest test-parse-args
  (testing "Command line argument parsing"
    (let [file "/tmp/ttracker.log"
          desc "this is test description"
          tags "Tag1:Tag2"
          duration "40m"
          [pduration pfile ptags pdesc perr] (parse-args
                                               "-d" desc
                                               "-t" tags
                                               "-f" file
                                               duration)

          [eduration efile etags edesc eerr] (parse-args)]
      (is (= duration pduration))
      (is (= file pfile))
      (is (= tags ptags))
      (is (= desc pdesc))
      (is (= nil perr))

      (is (= eerr (conj () "file is required" "specify duration is required"))))))
