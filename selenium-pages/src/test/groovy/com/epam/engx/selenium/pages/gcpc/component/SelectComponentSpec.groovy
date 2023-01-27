package com.epam.engx.selenium.pages.gcpc.component

import org.openqa.selenium.By
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement
import spock.lang.*

import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate


@Title('The user selects menu option')
@Narrative('''
As a user
I want to select the required menu option
So that I am able to estimate the monthly cost of a VM
''')
@Rollup
@Tag('fast')
class SelectComponentSpec extends Specification {
    static final SELECTED = 'aria-selected'
    static final AREA_ID = 'some-id'

    def fakeSearchStrategy = new Function<String, Predicate<WebElement>>() {
        @Override
        Predicate<WebElement> apply(String text) {
            (element) -> element.getAttribute("textContent") == text
        }
    }

    def options = ['one', 'two', 'three'].collect { name ->
        Stub(WebElement) {
            getAttribute('textContent') >> name
        }
    }

    def menu = Stub(WebElement) {
        getAttribute('aria-owns') >> AREA_ID
    }

    def root = Stub(SearchContext) {
        findElement(By.id(AREA_ID)) >> Stub(WebElement) {
            findElements(_ as By) >> options
        }
    }

    Consumer<WebElement> jsActionClick = Mock()

    @Subject
    SelectComponent component

    void setup() {
        component = new SelectComponent(root, menu, jsActionClick, fakeSearchStrategy)
    }

    def 'setting an option that is already selected'() {

        given: 'the option we are going to look for is already selected'
        options[index].getAttribute(SELECTED) >> true

        when: 'we set the component to a value that matches the already selected option'
        component.set(value)

        then: 'mouse click on the menu'
        1 * jsActionClick.accept(menu)

        and: 'no more mouse clicks'
        0 * jsActionClick.accept(_)

        where: 'value of menu options and their index in the list'
        value   | index
        'one'   | 0
        'two'   | 1
        'three' | 2
    }

    def 'setting an option that is not selected'() {

        given: 'the option we are going to look for is not selected'
        options[index].getAttribute(SELECTED) >> false

        when: 'we set the component to a value that matches the option'
        component.set(value)

        then: 'mouse click on the menu'
        1 * jsActionClick.accept(menu)

        and: 'mouse click on the option'
        1 * jsActionClick.accept(options[index])

        and: 'no further action is taken'
        0 * _

        where: 'value of menu options and their index in the list'
        value   | index
        'one'   | 0
        'two'   | 1
        'three' | 2
    }

    def 'setting a non-existing option'() {

        when: 'we are trying to select a non-existing option'
        component.set('zero')

        then: 'component throws an exception'
        thrown NoSuchElementException
    }
}
