(ns fortuna.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
 :subs/roll-data
 (fn [db [sub-name roll-id]]
   (get (:rolls db)
        roll-id)))

(re-frame/reg-sub
 :subs/roll-history
 (fn [db _]
   (:roll-history db)))
