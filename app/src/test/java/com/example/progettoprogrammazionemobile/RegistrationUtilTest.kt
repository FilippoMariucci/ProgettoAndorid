package com.example.progettoprogrammazionemobile

import com.google.common.truth.Truth.assertThat

import org.junit.Test

class RegistrationUtilTest{


    // in questo modo si puo chiamare la funziona come si vuole
    @Test
    fun ` email vuota restituisce false` () {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123456",
            "123456"
        )
        assertThat(result).isFalse()

    }

    @Test
    fun ` email valida e password ripetuta correttamente ritornano true` () {
        val result = RegistrationUtil.validateRegistrationInput(
            "nicola@gmail.com",
            "123456",
            "123456"
        )
        assertThat(result).isTrue()

    }

    @Test
    fun ` password confermata incorretta ritorna false` () {
        val result = RegistrationUtil.validateRegistrationInput(
            "nicola@gmail.com",
            "123456",
            "111111"
        )
        assertThat(result).isFalse()

    }

    @Test
    fun `password vuota ritorna false` () {
        val result = RegistrationUtil.validateRegistrationInput(
            "nicola@gmail.com",
            "",
            ""
        )
        assertThat(result).isFalse()

    }

    @Test
    fun `email già esistente ritorna false` () {
        val result = RegistrationUtil.validateRegistrationInput(
            "dennis@gmail.com",
            "123456",
            "123456"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password minore di 6 caratteri ritorna false` () {
        val result = RegistrationUtil.validateRegistrationInput(
            "nicola@gmail.com",
            "123456ciao",
            "123456ciao"
        )
        assertThat(result).isTrue()

    }

}