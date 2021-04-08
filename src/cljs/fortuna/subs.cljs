(ns fortuna.subs
  (:require
   [re-frame.core :as re-frame]
   [fortuna.parser :as par]))

(re-frame/reg-sub
 ::all-roll-data
 (fn [db _]
   (get db :rolls)))


(re-frame/reg-sub
 ::roll-expression
 (fn [db [_sub-name roll-id]]
   (get-in db [:rolls roll-id :expression])))

(re-frame/reg-sub
 ::expression-structure
 (fn [[_sub-name roll-id]]
   [(re-frame/subscribe [::roll-expression roll-id])])
 (fn [[expression-string] [_sub-name _roll-id]]
   (par/parse-expression expression-string)))

(re-frame/reg-sub
 ::roll-name
 (fn [db [_sub-name roll-id]]
   (get-in db [:rolls roll-id :name])))

(re-frame/reg-sub
 ::expression-structure-status
 (fn [[_sub-name roll-id]]
   [(re-frame/subscribe [::expression-structure roll-id])])
 (fn [[parse] [_sub-name _roll-id]]
   (par/parse-worked? parse)))

(re-frame/reg-sub
 ::roll-list
 (fn [db _query]
   (get db :rolls)))

(re-frame/reg-sub
 ::sorted-roll-ids
 (fn [db _query]
   (keys (:rolls db))))