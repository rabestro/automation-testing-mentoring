package com.epam.engx.selenium.pages.utils

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('The Page Object parses money representation on web page')
@Narrative('''
As a Page Object
I want to parse the USD money representation of the estimated monthly cost
So that I am able to provide a model of bill estimation for the test.
''')
class USMoneyParserSpec extends Specification {

    def 'parse usd money representation'() {
        given:
        @Subject def parser = new USMoneyParser()

        when:
        def money = parser.apply representation

        then:
        with(money) {
            numberStripped == amount
            currency.currencyCode == 'USD'
        }

        where:
        representation  | amount
        'USD 1,080.20'  | 1080.20
        'USD 1000000'   | 1000000
        'USD 1,000,000' | 1000000
        'USD .36'       | .360
        'USD 0.362'     | .362
        'USD -10'       | -10
    }
}
