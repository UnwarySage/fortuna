(ns fortuna.subs
  (:require
   [re-frame.core :as re-frame]
   [fortuna.parser :as par]))

(re-frame/reg-sub
 ::all-roll-data
 (fn [db _query]
   (get db :rolls)))


(re-frame/reg-sub 
 ::roll-data
 (fn [_]
   (re-frame/subscribe [::all-roll-data]))
 (fn [all-roll-data [_sub_name roll-id]]
   (get all-roll-data roll-id)))


(re-frame/reg-sub 
 ::roll-expression
 (fn [[_sub_name roll-id]]
   (re-frame/subscribe [::roll-data roll-id]))
 (fn [roll-data _query]
   (:expression-string roll-data)))

(re-frame/reg-sub
 ::expression-structure
 (fn [[_sub-name roll-id]]
   (re-frame/subscribe [::roll-data roll-id]))
 (fn [roll-data [_sub-name _roll-id]]
   (:expression-structure roll-data)))

(re-frame/reg-sub
 ::roll-name
 (fn [[_sub-name roll-id]]
   (re-frame/subscribe [::roll-data roll-id]))
 (fn [roll-data [_sub-name _roll-id]]
   (:name roll-data)))

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