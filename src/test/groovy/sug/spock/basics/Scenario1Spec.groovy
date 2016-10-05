package sug.spock.basics

import spock.lang.Specification

/**
 * Tests a very basic scenario
 */
class Scenario1Spec extends Specification {

    public interface FriendlyService {
        // returns a friendly greeting
        String getGreeting(String name);
    }

    public class MyFriendlyService implements FriendlyService {
        @Override
        public String getGreeting(String name) {
            return new StringBuilder("hello ").append(name).append("!").toString();
        }
    }




















    //************ test case ***************************

    def 'test the getGreeting() method returns as expected'() {
        MyFriendlyService myFriendlyService = new MyFriendlyService()

        when:
        String greeting = myFriendlyService.getGreeting('billy')

        then:
        greeting == 'hello billy!'
    }

}
