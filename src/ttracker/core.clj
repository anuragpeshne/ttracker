(ns ttracker.core
  (:gen-class))

(defn- time-now
  "Give back current time according to the formatter given.
   Default formatter- ISO_LOCAL_TIME : '10:15:30' "
  ([] (time-now java.time.format.DateTimeFormatter/ISO_LOCAL_TIME))
  ([formatter]
   (.format (java.time.LocalDateTime/now) formatter)))

(defn osx-notify
  "Takes in message, title and shows native OSX desktop notification."
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

(defn -main
  "entry point: parses args and calls next function."
  [pomo-min & tags]
  (do
    (println (str "Session started at " (time-now)))
    (Thread/sleep (* 60 1000 (Integer/parseInt pomo-min)))
    (println (str "Session ended at   " (time-now)))
    (osx-notify pomo-min "Time's up")))
