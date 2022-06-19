package com.example.progettoprogrammazionemobile

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    @get:Rule
//    var registration : ActivityScenarioRule<Registration> = ActivityScenarioRule(Registration::class.java)
    @get:Rule
    var login: ActivityScenarioRule<Login> = ActivityScenarioRule(Login::class.java)

    @Test
    fun loginClick() {
        val email = "dennis@gmail.com"
        val password = "123456"

        onView(withId(R.id.registrationEmail)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard())

        onView(withId(R.id.loginbutton)).perform((click()))
    }

//    @Test
//    fun registrationClick() {
//        val email = "mario@gmail.com"
//        val nome = "Mario"
//        val cognome = "Rossi"
//        val password = "123456"
//        val confirm = "123456"
//        val stato = " Italia"
//        val descrizione = " sono Mario rossi"
//        val dataNascita = "20/04/2000"
//
//        onView(withId(R.id.nome)).perform(typeText(nome), closeSoftKeyboard())
//        onView(withId(R.id.surname)).perform(typeText(cognome), closeSoftKeyboard())
//        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard())
//        onView(withId(R.id.passconfirm)).perform(typeText(confirm), closeSoftKeyboard())
//        onView(withId(R.id.state)).perform(typeText(stato), closeSoftKeyboard())
//        onView(withId(R.id.description)).perform(typeText(descrizione), closeSoftKeyboard())
//        onView(withId(R.id.data)).perform(typeText(dataNascita), closeSoftKeyboard())
//        onView(withId(R.id.registrationEmail)).perform(typeText(email), closeSoftKeyboard())
//
//        onView(withId(R.id.registrationButton)).perform((click()))
//    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.progettoprogrammazionemobile", appContext.packageName)
    }
}