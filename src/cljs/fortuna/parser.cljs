(ns fortuna.parser
  (:require [instaparse.core :as insta :refer-macros [defparser]]
            [com.rpl.specter :as spc]
            [clojure.edn :as edn]))


(def expression-parser-string
  "expression = dice-pool
  dice-pool = roll (('+'|'-'|'*') roll)*
  roll = dice-amount <('D'|'d')> dice-sides ?[bonus] *{modifiers}
  dice-amount = #'\\d+'
  dice-sides = #'\\d+'
  bonus = #'[+-]\\d+'
  modifiers = (modifier-explode | (modifier-keep-highest | modifier-keep-lowest) | modifier-multiply-by)+
  modifier-explode = <'!'>
  modifier-keep-highest = <'kh'>#'\\d+'
  modifier-keep-lowest = <'kl'>#'\\d+'
  modifier-multiply-by = <'*'>#'\\d+'")

(def parser-transforms
  {:dice-sides (fn [inp-num] [:dice-sides (edn/read-string inp-num)])
   :dice-amount (fn [inp-num] [:dice-amount (edn/read-string inp-num)])
   :bonus (fn [inp-num] [:bonus (edn/read-string inp-num)])})

;; Declaring the expression so that static analysis doesn't panic
(declare expression)

(defparser expression  expression-parser-string)


(defn parse-expression [inp-string]
  (insta/transform parser-transforms
   (expression inp-string)))


(defn parse-worked? [inp-parse]
  (not (insta/failure? inp-parse)))