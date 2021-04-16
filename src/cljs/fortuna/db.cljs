(ns fortuna.db)

(def default-db
  {:name "re-frame"
   :rolls {0 {:name "Battleaxe Damage"
              :expression "2d6"}
           1 {:name "Attack roll"
              :expression "2d20"}}
   :ui {:present-page "home"}})
