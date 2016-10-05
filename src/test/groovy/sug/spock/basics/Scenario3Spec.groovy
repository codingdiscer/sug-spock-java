package sug.spock.basics

import spock.lang.Specification

/**
 * Execute tests using mocks and spies
 */
class Scenario3Spec extends Specification {

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




























    //************ test cases ***************************

    def 'test replacing Service1 with a mock'() {
        given:
        MyFriendlyService mockFriendlyService = Mock()

        when:
        String message = new OtherService(friendlyService: mockFriendlyService, handleInternally: false).getMessage('robert')

        then:
        1 * mockFriendlyService.getGreeting('robert') >> 'another greeting'
        message == 'another greeting'
    }



    def 'test replacing an interface with a mock'() {
        given:
        FriendlyService mockFriendlyService = Mock()

        when:
        String message = new OtherService(friendlyService: mockFriendlyService, handleInternally: false).getMessage('robert')

        then:
        1 * mockFriendlyService.getGreeting('robert') >> 'interface greeting'
        message == 'interface greeting'
    }


    def 'test overriding a method call using a spy'() {
        given:
        OtherService otherService = Spy()
        otherService.handleInternally = true

        when:   'we do not override the implementation of the getInternalMessage() call'
        String message = otherService.getMessage('Constance')

        then:
        1 * otherService.getInternalMessage({ it == 'Constance' })
        message == 'wazzup Constance!'



        when:   'we do override the implementation of the getInternalMessage() call'
        message = otherService.getMessage('Constance')

        then:
        1 * otherService.getInternalMessage({ it == 'Constance' }) >> 'overriding message'
        message == 'overriding message'
        noExceptionThrown()



        when:   'we override the implementation to throw an exception'
        otherService.getMessage('Constance')

        then:   'the exception is thrown from the getMessage() method'
        1 * otherService.getInternalMessage({ it == 'Constance' }) >> { throw new Exception('mock exception') }
        Exception e = thrown()
        e.message == 'mock exception'
    }

}
