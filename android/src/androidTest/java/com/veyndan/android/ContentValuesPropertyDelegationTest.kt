package com.veyndan.android

import android.content.ContentValues
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContentValuesPropertyDelegationTest {

    class User(contentValues: ContentValues) {
        val name: String by contentValues
        val age: Int     by contentValues
    }

    @Test
    fun useAppContext() {
        val contentValues = ContentValues().apply {
            put("name", "Veyndan Stuart")
            put("age", 21)
        }

        val user = User(contentValues)

        assertThat(user.name, `is`("Veyndan Stuart"))
        assertThat(user.age, `is`(21))
    }
}
