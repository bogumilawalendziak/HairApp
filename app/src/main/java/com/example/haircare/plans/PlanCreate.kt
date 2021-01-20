package com.example.haircare.plans

object PlanCreate {

    val hairType= null
    fun getPlan(): ArrayList<Plan>{
        val getPlanList = ArrayList<Plan>()
        val srednioporowate = Plan(mapOf<Int,Array<Int>>(0 to arrayOf(1,4,2,3),1 to arrayOf(5,2,3),2 to arrayOf(7,2,3),3 to arrayOf(7,1,2,3),4 to arrayOf(1,3,9), 5 to arrayOf(1,5,3),6 to arrayOf(1,2,3)),"srednioporowate")
        val niskoporowate = Plan(mapOf<Int,Array<Int>>(0 to arrayOf(1,4,5,3),1 to arrayOf(5,7,9,3),2 to arrayOf(6,2,3),3 to arrayOf(7,6,3),4 to arrayOf(1,2,8),5 to arrayOf(1,3,9),6 to arrayOf(1,2,3)),"niskoporowate")
        val wysokoporowate = Plan(mapOf<Int,Array<Int>>(0 to arrayOf(1,4,7,3),1 to arrayOf(5,2,4,3),2 to arrayOf(5,2,3),3 to arrayOf(7,8,3),4 to arrayOf(1,9,3),5 to arrayOf(1,8,3),6 to arrayOf(1,2,3)),"wysokoporowate")
        val ownPlan = Plan(mapOf<Int,Array<Int>>(99 to arrayOf(0)),"no_plan")

        val volume = Plan(mapOf<Int,Array<Int>>(0 to arrayOf(1,9,3),3 to arrayOf(4,9,3),1 to arrayOf(5,2,3),2 to arrayOf(9,6,2,3),3 to arrayOf(7,2,3),4 to arrayOf(1,2,3),6 to arrayOf(1,2,3)),"volume")
        val greasy = Plan(mapOf<Int,Array<Int>>(0 to arrayOf(1,9,3),3 to arrayOf(2,4,2,3),1 to arrayOf(1,5,3),2 to arrayOf(1,6,3),3 to arrayOf(2,7,9),4 to arrayOf(1,2,3),6 to arrayOf(1,2,3)),"greasy")
        val grow = Plan(mapOf<Int,Array<Int>>(0 to arrayOf(1,4,9),3 to arrayOf(2,5,3),1 to arrayOf(1,2,9),2 to arrayOf(1,6,3),3 to arrayOf(1,7,9),4 to arrayOf(1,2,3),6 to arrayOf(1,2,3)),"grow")

        getPlanList.add(srednioporowate)
        getPlanList.add(niskoporowate)
        getPlanList.add(wysokoporowate)
        getPlanList.add(volume)
        getPlanList.add(grow)
        getPlanList.add(greasy)
        return getPlanList
    }
}