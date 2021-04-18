(ns fortuna.events
  (:require
   [re-frame.core :as re-frame]
   [fortuna.db :as db]
   [fortuna.parser :as par]
   [day8.re-frame.tracing :refer-macros [defn-traced]]))

(defn initialize-db [_ _]
  db/default-db)

(re-frame/reg-event-db
 ::initialize-db
 initialize-db)


(defn-traced change-ui-tab
  [db [_event-id new-tab]]
  (assoc-in db [:ui :present-tab] new-tab))

(re-frame/reg-event-db
 ::change-ui-tab
 change-ui-tab)


(defn-traced change-expression
  [db [_ roll-id new-expression]]
  (let [new-parse (par/parse-expression new-expression)]
    (-> db
        (assoc-in [:rolls roll-id :expression-string] new-expression)
        (assoc-in [:rolls roll-id :expression-structure] new-parse))))

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

