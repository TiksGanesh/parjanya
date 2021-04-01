package com.example.parjanya.reusable

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UtilsTest {


    @Before
    fun setUp(){

    }


    @Test
    fun `test date conversion for null input and empty output`(){
        val output = Utils.convertUnixTimeStampToHumanDate(null)
        Assert.assertEquals("",output)
    }


    @Test
    fun `test date conversion for 0 input and empty output`(){
        val output = Utils.convertUnixTimeStampToHumanDate(null)
        Assert.assertEquals("",output)
    }

    @Test
    fun `test date conversion for valid input and valid output`(){
        val input = 1617172200L
        val expected = "31 March"
        val output = Utils.convertUnixTimeStampToHumanDate(input)
        Assert.assertEquals(expected,output)
    }

    @Test
    fun `test for degree celsius symbol output for null input`() {
        val value:Double? = null
        val expected = "0 \u2103"
        val output = Utils.appendDegreeCelsiusToValue(value)
        Assert.assertEquals(expected, output)
    }

    @Test
    fun `test for degree celsius symbol output for valid input`() {
        val value:Double? = 32.76
        val expected = "32.76 \u2103    "
        val output = Utils.appendDegreeCelsiusToValue(value)
        Assert.assertEquals(expected, output)
    }


    @After
    fun tearDown(){

    }
}