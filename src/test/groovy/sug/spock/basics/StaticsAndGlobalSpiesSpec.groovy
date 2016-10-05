package sug.spock.basics

import ch.qos.logback.classic.Logger
import groovy.util.logging.Slf4j
import spock.lang.Specification

/**
 * Test class to test static methods
 */
class StaticsAndGlobalSpiesSpec extends Specification {


    interface FriendlyService {
        // returns a friendly greeting
        String getGreeting(String name)
    }

    @Slf4j
    static class MyFriendlyService implements FriendlyService {
        @Override
        String getGreeting(String name) {
            log.info "inside getGreeting(${name})..."
            return "hello ${name}!"
        }

        static String getGreetingStatically(String name) {
            new MyFriendlyService().getGreeting(name)
        }
    }



    //************ test cases ***************************

    def 'test normal static call'() {
        when:
        String greeting = MyFriendlyService.getGreetingStatically('elizabeth')

        then:
        greeting == 'hello elizabeth!'
    }




    def 'test overriding the static method with a Spy'() {

        given: 'declare a global spy on the class under test'
        GroovySpy(MyFriendlyService, global: true)


        when: 'the static method is called without overriding..'
        String greeting = MyFriendlyService.getGreetingStatically('elizabeth')

        then: 'the standard greeting is returned'
        greeting == 'hello elizabeth!'


        when: 'the static method is overridden..'
        greeting = MyFriendlyService.getGreetingStatically('elizabeth')

        then: 'the standard greeting is returned'
        1 * MyFriendlyService.getGreetingStatically(_) >> 'wazzup elizabeth!'
        greeting == 'wazzup elizabeth!'
    }


    def 'test that logging occurs as expected'() {
        given: 'create a spy on the logger class'
        MyFriendlyService myFriendlyService = new MyFriendlyService()
        Logger logger = GroovySpy([global: true, constructorArgs: [MyFriendlyService.name, null, null]], Logger)


        when:
        String greeting = myFriendlyService.getGreeting('elizabeth')

        then:
        greeting == 'hello elizabeth!'
        0 * logger.info({ it == 'inside getGreeting(elizabeth)...' })
    }



}
