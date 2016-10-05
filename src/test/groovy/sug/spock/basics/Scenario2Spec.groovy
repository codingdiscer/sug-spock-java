package sug.spock.basics

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Execute tests using the data pipe syntax
 */
class Scenario2Spec extends Specification {

    interface FriendlyService {
        // returns a friendly greeting
        String getGreeting(String name)
    }

    class MyFriendlyService implements FriendlyService {
        @Override
        String getGreeting(String name) {
            return "hello ${name}!"
        }
    }

    class OtherService {
        FriendlyService friendlyService;    // reference to the interface
        boolean handleInternally = false;   // determines if our call should be handled internally or not

        String getMessage(String name) {
            if(handleInternally) {
                return getInternalMessage(name)
            }
            return friendlyService.getGreeting(name)
        }

        protected String getInternalMessage(String name) {
            return "wazzup ${name}!"
        }
    }

























    //************ test case ***************************

    @Unroll
    def 'test use of data pipes where internal = #internal'() {
        expect:
        new OtherService(friendlyService: new MyFriendlyService(), handleInternally: internal).getMessage('brian') == output

        where:
        internal << [true, false]
        output << ['wazzup brian!', 'hello brian!']
    }



}
