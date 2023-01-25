package com.epam.engx.selenium.pages.utils

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Tag
import spock.lang.Title

@Title('The Page Object parses money representation on web page')
@Narrative('''
As a Page Object
I want to parse the USD money representation of the estimated monthly cost
So that I am able to provide a model of bill estimation for the test.
''')
@Tag('fast')
class USMoneyParserSpec extends Specification {

    def 'parse usd money representation'() {

        given: 'parser for string representation of dollar amount'
        @Subject def parser = new USMoneyParser()

        when: 'we process the string representation of the amount in US dollars'
        def money = parser.apply representation

        then: 'we get the money object with the correct amount and currency'
        with(money) {
            numberStripped == amount
            currency.currencyCode == 'USD'
        }

        where: 'monthly rent in US dollars'
        representation  | amount
        'USD 1,080.20'  | 1080.20
        'USD 1000000'   | 1000000
        'USD 1,000,000' | 1000000
        'USD .36'       | .360
        'USD 0.362'     | .362
        'USD -10'       | -10
    }
}
