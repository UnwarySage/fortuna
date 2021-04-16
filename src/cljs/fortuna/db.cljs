(ns fortuna.db)

(def default-db
  {:name "re-frame"
   :rolls {0 {:name "Battleaxe Damage"
              :expression-string "2d6"
              :expression-structure
              [:expression [:dice-pool {:dice-amount 2, :dice-sides 6}]]}
           1 {:name "Attack roll"
              :expression-string "2d20"
              :expression-structure 
              [:expression [:dice-pool {:dice-amount 2, :dice-sides 20}]]}}
   :ui {:present-page "home"}})
