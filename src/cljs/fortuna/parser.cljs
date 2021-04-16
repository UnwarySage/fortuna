(ns fortuna.parser
  (:require [instaparse.core :as insta :refer-macros [defparser]]
            ;;[com.rpl.specter :as spc]
            [clojure.edn :as edn]))


(def expression-parser-string
  "expression = dice-pool
  dice-pool = roll (('+'|'-'|'*') roll)*
  roll = dice-amount <('D'|'d')> dice-sides bonus? modifier-sequence?
  dice-amount = num
  dice-sides = num
  bonus = num
  modifier-sequence = modifier+
  <modifier> = (modifier-explode | (modifier-keep-highest | modifier-keep-lowest) | modifier-multiply-by)+
  modifier-explode = <'!'>
  modifier-keep-highest = <'kh'> num
  modifier-keep-lowest = <'kl'> num
  modifier-multiply-by = <'*'> num
  num = #'(\\-|\\+)?\\d+'")



;; Declaring the expression so that static analysis doesn't panic
(declare expression)

(defparser expression  expression-parser-string)


(defn parse-worked? [inp-parse]
  (not (insta/failure? inp-parse)))

;; post processing functions
(defn tag-to-pair
  "takes a vector, and if it has one item, 
   makes it into a vec of that item and true,
   otherwise returns the vector"
  [inp-vec]
  (if (and
       (= 1 (count inp-vec))
       (vector? inp-vec))
    [(first inp-vec) true]
    inp-vec))


(defn modifier-sequence-format
  "takes a modifier sequence from instaparse and formats it to a vector of vectors"
  [& modifiers]
  {:modifier-sequence
   (mapv tag-to-pair modifiers)})

(def parser-transforms
  {:num edn/read-string
   :dice-sides (fn [inp-num] {:dice-sides inp-num})
   :dice-amount (fn [inp-num] {:dice-amount inp-num})
   :roll merge
   :modifier-sequence modifier-sequence-format
   :bonus (fn [inp-num] [:bonus inp-num])})


;; final parse function
(defn parse-expression [inp-string]
  (insta/transform parser-transforms
                   (expression inp-string)))
