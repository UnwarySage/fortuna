(ns fortuna.events
  (:require
   [re-frame.core :as re-frame]
   [fortuna.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [fortuna.roller :as rolls]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-active-panel
 (fn-traced [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
  ::modify-roll
  (fn-traced [db [_ roll-id new-roll-data]]
   (update-in db [:rolls roll-id] merge new-roll-data)))

(re-frame/reg-event-db
  :make-roll
  (fn-traced [db [_ roll-id]]
   ;; Fetch the roll data, and generate the roll
   (let [roll-data (get-in db [:rolls roll-id])
         new-roll (rolls/perform-roll roll-data)]
    ;;Updating the DB
    (-> db
        ;;Put this last result into the rolls info
        (assoc-in [:rolls roll-id :last-roll] new-roll)
        ;;Add the role and it's information to history 
        (update-in [:roll-history] conj 
                   (merge 
                     roll-data
                     ;;update it's last-roll
                     {:last-roll new-roll}
                     ;;and give it a unique id
                     {:id (inc (:id (last (:roll-history db))))}))))))
