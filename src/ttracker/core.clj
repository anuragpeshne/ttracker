(ns ttracker.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn osx-notify
  ([message, title]           (osx-notify message title "")) ; blank subtitle
  ([message, title, subtitle] (osx-notify message title subtitle "Glass"))
  ([message, title, subtitle, sound-name]
   (..
    Runtime
    getRuntime
    (exec (into-array
           ["osascript", "-e",
            (str
             "display notification \"" message
             "\" with title \""        title
             "\" subtitle \""          subtitle
             "\" sound name \""        sound-name
             "\"")])))))
