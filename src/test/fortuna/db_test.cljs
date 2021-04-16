(ns fortuna.db-test
  (:require
   [cljs.test :refer-macros [deftest is testing run-tests]]
   [fortuna.db :as db]))

(deftest db-structure
  (is (map? db/default-db))
  (is (:rolls db/default-db)))

(deftest ui-db-structure
  (is (map? (:ui db/default-db))))