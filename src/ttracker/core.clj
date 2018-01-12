(ns ttracker.core
  (:gen-class))
(use 'clojure.java.io)

(defn- format-display-time
  "Give back current time according to the formatter given.
   Default formatter- ISO_LOCAL_TIME : '10:15:30' "
  ([time] (format-display-time time java.time.format.DateTimeFormatter/ISO_LOCAL_TIME))
  ([time, formatter]
   (.format time formatter)))

(defn- format-write-time
  [time]
  (let [formatter (java.time.format.DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm:ss")]
    (.format time formatter)))

(defn- indent
  "This is a simple function to prepend `inp-str` with spaces."
  [inp-str]
  (str "  " inp-str))

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

(defn write-record
  "Takes in time-record and writes it to a file.
  Log example:
  2018-01-31 22:00 40m
    Task/description
    Work:Thesis:read docs
  "
  [file-path, [start-time, duration, description, tags]]
  (with-open [wrtr (writer file-path :append true)]
    (.write wrtr (str (format-write-time start-time) " " duration "m"))
    (.newLine wrtr)
    (.write wrtr (indent description))
    (.newLine wrtr)
    (.write wrtr (indent (clojure.string/join ":" tags)))
    (.newLine wrtr)))

(defn -main
  "entry point: parses args and calls next function."
  [pomo-min & tags]
  (let [start-time (java.time.LocalDateTime/now)
        file-path "/tmp/ttracker.log"]
    (println (str "Session started at " (format-display-time start-time)))
    ;(Thread/sleep (* 60 1000 (Integer/parseInt pomo-min)))
    (osx-notify pomo-min "Time's up")
    (let [end-time (java.time.LocalDateTime/now)]
      (println (str "Session ended at   " (format-display-time end-time)))
      (write-record file-path [start-time, pomo-min, "test", tags]))))
