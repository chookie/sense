package com.chookie.sense.client.mood

import spock.lang.Specification


/**
 * Created by Alison on 19/09/15.
 */
class MoodsParserSpec extends Specification {

    def 'should turn a single mood into a TweetMood with just that mood'() {
        when:
        TweetMood decodedMood = MoodsParser.parse('SAD')

        then:
        decodedMood.isSad()
        !decodedMood.isHappy()
        !decodedMood.isConfused()
        decodedMood.moods.size() == 1
    }

    def 'should turn a CSV of multiple moods into a TweetMood with those properties'() {
        when:
        TweetMood decodedMood = MoodsParser.parse('SAD,HAPPY')

        then:
        decodedMood.isSad()
        decodedMood.isHappy()
        decodedMood.isConfused()
        decodedMood.moods.size() == 2
    }
}