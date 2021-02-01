package com.example.haircare.customplan

import org.junit.Assert.assertEquals
import org.junit.Test

class CreateCustomPlanTest {
    @Test
    fun `incorrect day of week name returns 0`() {
     val createCustomPlan: CreateCustomPlan = CreateCustomPlan()
       val result= createCustomPlan.takeDay("incorrectName")
        assertEquals(result,-1)
    }
    fun `correct day of week name returns day of week as number`() {
        val createCustomPlan: CreateCustomPlan = CreateCustomPlan()
        val result= createCustomPlan.takeDay("poniedziałek")
        assertEquals(result,0)
    }
}