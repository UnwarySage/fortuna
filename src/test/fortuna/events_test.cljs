(ns fortuna.events-test
  (:require [fortuna.events :as evs]

            [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest test-change-expression
  (let [db (evs/initialize-db :some :value)
        after-db (evs/change-expression db [::evs/change-expression 0 "4d6"])]
    (is (= "2d6" (get-in db [:rolls 0 :expression]) ))
    (is (= "4d6" (get-in after-db [:rolls 0 :expression])))))

(deftest test-change-roll-name 
  (let [db (evs/initialize-db :some :value)
        after-db (evs/change-roll-name db [::evs/change-roll-name 0 "Pumpernickel"])]
    (is (= "Battleaxe Damage" (get-in db [:rolls 0 :name])))
    (is (= "Pumpernickel" (get-in after-db [:rolls 0 :name])))))

(deftest test-create-roll
  (let [db (evs/initialize-db :some :value)
        after-db (evs/create-roll db [::evs/create-roll])
        init-count (count (:rolls db))]
    ;; is the db sane
    (is (= init-count (count (:rolls db))))
    ;; has the db updated
    (is (= (inc init-count) (count (:rolls after-db))))
    ;; does it have a good name?
    (is (= "New Roll" (get-in after-db [:rolls 2 :name])))))
