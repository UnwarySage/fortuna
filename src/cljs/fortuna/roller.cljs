(ns fortuna.roller)
 
(defn roll-die
 "rolls the given die" 
  [sides]
  (inc (rand-int sides)))

(defn roll-pool [amount sides]
  (vec (take amount (repeatedly #(roll-die sides))))) 



(defn perform-roll [{:keys [sides amount modifier]}]
  (conj (roll-pool amount sides) modifier))  

(defn roll-info-string [{:keys [amount sides modifier]}]
  (if 
    (and modifier
     (= modifier 0))
    (str amount "d" sides)
    (str amount "d" sides (when (< 0 modifier) "+")
         modifier))) 
    
