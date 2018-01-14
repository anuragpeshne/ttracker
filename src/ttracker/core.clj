(ns ttracker.core
  (:require [clojure.tools.cli :refer [parse-opts]])
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

(defn- format-write-date
  [time]
  (let [formatter (java.time.format.DateTimeFormatter/ofPattern "yyyy-MM-dd")]
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
    (.write wrtr (str (format-write-time start-time) " " description))
    (.newLine wrtr)
    (.write wrtr (str (indent (clojure.string/join ":" tags))
                      (indent (str duration "m"))))
    (.newLine wrtr)
    (.write wrtr (indent (str "Untracked:" (format-write-date start-time))))
    (.newLine wrtr)))

(defn parse-args
  [args]
  (let [cli-options
        [["-f" "--file \"path/to/file\"" :required "File"
          :id :file]
         ["-d" "--description \"description here\"" "Description"
         :id :desc
         :default "NA"]
         ["-t" "--tags \"Tag:SubTag\"" "Tags"
         :id :tags
         :default "Untagged"]
         ["-h" "--help"]]
        parsed-args (parse-opts args cli-options)
        duration  (-> parsed-args :arguments (get 0))
        file-path (-> parsed-args :options :file)
        tags      (-> parsed-args :options :tags (clojure.string/split #":"))
        desc      (-> parsed-args :options :desc)
        errors    (-> parsed-args :errors)
        errors (if (nil? file-path) (conj errors "file is required") errors)
        errors (if (nil? duration) (conj errors "specify duration is required") errors)]
    [duration file-path tags desc errors]))

(defn -main
  "entry point: parses args and calls next function."
  [& args]
  (let [[duration file-path tags desc errors] (parse-args args)
        start-time (java.time.LocalDateTime/now)]
    (println (str "Session started at " (format-display-time start-time)))
    (Thread/sleep (* 60 1000 (Integer/parseInt duration)))
    (osx-notify duration"Time's up")
    (let [end-time (java.time.LocalDateTime/now)]
      (println (str "Session ended at   " (format-display-time end-time)))
      (write-record file-path [start-time, duration, desc, tags]))))
