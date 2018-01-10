(defproject ttracker "0.1.0-SNAPSHOT"
  :description "command line time tracker"
  :url "https://github.com/anuragpeshne/ttracker"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot ttracker.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
