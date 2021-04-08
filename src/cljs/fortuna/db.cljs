(ns fortuna.db)

(def default-db
  {:name "re-frame"
   :rolls {1 {:name "Battleaxe Damage"
              :expression "2d6+4"}
           2 {:name "Attackroll"
              :expression "2d20+6kl1"}}
   :ui {:present-page "home"}})
