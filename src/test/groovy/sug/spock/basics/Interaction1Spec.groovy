package sug.spock.basics

import spock.lang.Specification

/**
 * Tests a very basic scenario
 */
class Interaction1Spec extends Specification {

    public interface FriendlyInterface {
        // returns a friendly greeting
        String getGreeting(String name);
    }

    public class FriendlyService implements FriendlyInterface {
        @Override
        public String getGreeting(String name) {
            return new StringBuilder("hello ").append(name).append("!").toString();
        }
    }




















    //************ test case ***************************

    def 'test the getGreeting() method returns as expected'() {
        FriendlyService myFriendlyService = new FriendlyService()

        when:
        String greeting = myFriendlyService.getGreeting('billy')

        then:
        greeting == 'hello billy'
    }

}
