package sug.spock.basics

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Shows an example of a data driven test
 */
class DataDrivenSpec extends Specification {

    @Unroll
    def 'test raising #base to the power of #power using a data table'() {
        expect:
        Math.pow(base, power) == output

        where:
        base | power | output
        4    | 0     | 1
        5    | 1     | 5
        6    | 2     | 36
        7    | 3     | 401
    }









    @Unroll
    def 'test raising #base to the power of #power using data pipes'() {
        expect:
        Math.pow(base, power) == output

        where:
        base    << [4, 5, 6, 7]
        power   << [0, 1, 2, 3]
        output  << [1, 5, 36, 400]
    }



}
