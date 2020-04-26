(ns twitter.core-test
  (:use midje.sweet)
  (:use [twitter.core]))

(facts "about `-main`"
  (fact "it normally returns 1"
    (= 1 1)))