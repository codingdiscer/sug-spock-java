package sug.spock.basics

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Execute tests using the data pipe and data tables syntax, and shows use of @Unroll
 */
class Interaction2Spec extends Specification {

    interface FriendlyInterface {
        // returns a friendly greeting
        String getGreeting(String name)
    }

    class FriendlyService implements FriendlyInterface {
        @Override
        String getGreeting(String name) {
            return "hello ${name}!"
        }
    }

    class OtherService {
        FriendlyInterface friendlyInterface;    // reference to the interface
        boolean handleInternally = false;   // determines if our call should be handled internally or not

        String getMessage(String name) {
            if(handleInternally) {
                return getInternalMessage(name)
            }
            return friendlyInterface.getGreeting(name)
        }

        protected String getInternalMessage(String name) {
            return "wazzup ${name}!"
        }
    }

























    //************ test case ***************************

    @Unroll
    def 'test use of data pipes where internal = #internal'() {
        given:
        OtherService otherService = new OtherService(friendlyInterface: new FriendlyService(), handleInternally: internal)

        expect:
        otherService.getMessage('brian') == output

        where:
        internal << [true, false]
        output << ['wazzup brian', 'hello brian']
    }










    @Unroll
    def 'test use of data table where internal = #internal'() {
        given:
        OtherService otherService = new OtherService(friendlyInterface: new FriendlyService(), handleInternally: internal)

        expect:
        otherService.getMessage('brian') == output

        where:
        internal | output
        true     | 'wazzup brian'
        false    | 'hello brian'
    }



}
