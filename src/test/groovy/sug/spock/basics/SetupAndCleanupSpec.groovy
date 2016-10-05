package sug.spock.basics

import spock.lang.Specification
import spock.lang.Shared

/**
 * Shows how to use shared resources, and how to setup and cleanup.
 */
class SetupAndCleanupSpec extends Specification {


	class ExpensiveResource {

		// flags if this resource is ready or not to be used
		boolean ready = false;

		// Performs initialization
		void initExpensively() {
			// do something that you wouldn't want to do over and over, like
			// create connections to external resources
			ready = true;
		}

		// close down the external connections
		void close() {
			ready = false;
		}

		// do some work
		String performWork() throws Exception {
			if(!ready) {
				throw new Exception("not ready for work");
			}
			return "ok, here is some useful work.";
		}
	}











	//************ test cases ***************************

	@Shared ExpensiveResource expensiveResource
	
	// this method is called only once before all tests - it prepares the expensive resource for use
	def setupSpec() {
		expensiveResource = new ExpensiveResource()
		expensiveResource.initExpensively()
		println "inside setupSpec() - after expensiveResource initialization, ready=${expensiveResource.ready}"
	}

	// this method is called only once after all tests - close down the expensive resource
	def cleanupSpec() {
		expensiveResource.close()
		println "inside cleanupSpec() - just closed down the expensiveResource, ready=${expensiveResource.ready}"
	}
	
	// this method is called before each test - it resets the ready
	// flag to true to ensure it is ready before each test
	def setup() {
		if(expensiveResource != null) {
			expensiveResource.ready = true
		}
		println "inside setup() - just reset the expensiveResource, ready=${expensiveResource.ready}"
	}

	// this method is called after each test - does nothing
	def cleanup() {
		println "inside cleanup() - nothing going on here"
	}
	






	def 'test that performWork() will throw an exception if the resource is not ready'() {
		given:
		expensiveResource.ready = false
				
		when:	'the resource is requested to do some work'
		expensiveResource.performWork()
		
		then:	'an exception is thrown because the resource is not ready'
		def exception = thrown(Exception)
		exception.message.contains('not ready for work')		
	}



	
	def 'test that performWork() does what it says'() {
		when:	'the resource is requested to do some work'
		String output = expensiveResource.performWork()
		
		then:	'work is performed and no exception is thrown'
		output.contains('work')
		noExceptionThrown()
	}
	
	
}
