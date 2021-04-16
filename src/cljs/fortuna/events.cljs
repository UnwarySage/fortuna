(ns fortuna.events
  (:require
   [re-frame.core :as re-frame]
   [fortuna.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(defn-traced change-expression
  [db [_ roll-id new-expression]]
  (assoc-in db [:rolls roll-id :expression] new-expression))

(re-frame/reg-event-db
 ::change-expression
 change-expression)


(defn-traced change-roll-name 
  [db [_ roll-id new-name]]
 (assoc-in db [:rolls roll-id :name] new-name))

(re-frame/reg-event-db
 ::change-roll-name
 change-roll-name)

(defn-traced create-roll
  [db _]
 (let [max-id (apply max (keys (:rolls db)))]
   (assoc-in db [:rolls (inc max-id)]
            {:name "New Roll"
             :expression "2d6+1"})))

(re-frame/reg-event-db
 ::create-roll
 create-roll)
 
(defn-traced perform-roll
  [db [_ roll-id
       db]])

(re-frame/reg-event-db
 ::perform-roll
 perform-roll)

