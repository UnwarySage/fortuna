(ns fortuna.events-test
  (:require [fortuna.events :as evs]

            [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest test-change-expression
  (let [db (evs/initialize-db :some :value)
        after-db (evs/change-expression db [::evs/change-expression 0 "4d8+7"])
        before-parse [:expression [:dice-pool {:dice-amount 2 :dice-sides 6}]]
        after-parse [:expression [:dice-pool {:dice-amount 4 :dice-sides 8 :bonus 7}]]]
    ;; Does the DB have what we expect?
    (is (= "2d6" (get-in db [:rolls 0 :expression-string])))
    (is (= before-parse (get-in db [:rolls 0 :expression-structure])))
    ;; now see if it updated correctly with the parse
    (is (= "4d8+7" (get-in after-db [:rolls 0 :expression-string])))
    (is (= after-parse (get-in after-db [:rolls 0 :expression-structure])))
    ;; finally, an isolation test, outside of the thing we should touch, did we touch anything?
    (is (=  (update db :rolls dissoc 0) (update after-db :rolls dissoc 0)))))

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
