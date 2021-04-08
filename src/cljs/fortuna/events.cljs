(ns fortuna.events
  (:require
   [re-frame.core :as re-frame]
   [fortuna.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::change-expression
 (fn-traced [db [_ roll-id new-expression]]
   (assoc-in db [:rolls roll-id :expression] new-expression)))

(re-frame/reg-event-db
 ::change-roll-name
 (fn-traced [db [_ roll-id new-name]]
   (assoc-in db [:rolls roll-id :name] new-name)))

(re-frame/reg-event-db 
 ::create-roll 
 (fn-traced [db _]
   (let [max-id (rand 15000)]
        (assoc-in db [:rolls (inc max-id)] 
                 {:name "New Roll"
                  :expression "2d6+1"}))))

(re-frame/reg-event-db
 ::perform-roll
 (fn-traced [db [_ roll-id]]
            db))

