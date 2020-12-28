package com.example.haircare.plans

object PlanCreate {
    const val HAIR_TYPE: String = "hair type"

    fun getPlan(): ArrayList<Plan>{
        val getPlanList = ArrayList<Plan>()
        val srednioporowate = Plan(mapOf<String,Array<Int>>("monday" to arrayOf(1,2,3),"tuesday" to arrayOf(1,2,3),"wednesday" to arrayOf(1,2,3),"thursday" to arrayOf(1,2,3),"friday" to arrayOf(1,2,3),"saturday" to arrayOf(1,2,3),"sunday" to arrayOf(1,2,3)),"srednioporowate")
        val niskoporowate = Plan(mapOf<String,Array<Int>>("monday" to arrayOf(1,5,3),"tuesday" to arrayOf(1,2,3),"wednesday" to arrayOf(1,2,3),"thursday" to arrayOf(1,2,3),"friday" to arrayOf(1,2,3),"saturday" to arrayOf(1,2,3),"sunday" to arrayOf(1,2,3)),"niskoporowate")
        val wysokoporowate = Plan(mapOf<String,Array<Int>>("monday" to arrayOf(1,7,3),"tuesday" to arrayOf(1,2,3),"wednesday" to arrayOf(1,2,3),"thursday" to arrayOf(1,2,3),"friday" to arrayOf(1,2,3),"saturday" to arrayOf(1,2,3),"sunday" to arrayOf(1,2,3)),"wysokoporowate")

        val volume = Plan(mapOf<String,Array<Int>>("monday" to arrayOf(1,9,3),"tuesday" to arrayOf(1,9,3),"wednesday" to arrayOf(1,2,3),"thursday" to arrayOf(1,2,3),"friday" to arrayOf(1,2,3),"saturday" to arrayOf(1,2,3),"sunday" to arrayOf(1,2,3)),"volume")
        val greasy = Plan(mapOf<String,Array<Int>>("monday" to arrayOf(1,9,3),"tuesday" to arrayOf(1,2,3),"wednesday" to arrayOf(1,2,3),"thursday" to arrayOf(1,2,3),"friday" to arrayOf(1,2,3),"saturday" to arrayOf(1,2,3),"sunday" to arrayOf(1,2,3)),"greasy")
        val grow = Plan(mapOf<String,Array<Int>>("monday" to arrayOf(1,2,9),"tuesday" to arrayOf(1,2,3),"wednesday" to arrayOf(1,2,3),"thursday" to arrayOf(1,2,3),"friday" to arrayOf(1,2,3),"saturday" to arrayOf(1,2,3),"sunday" to arrayOf(1,2,3)),"grow")

        getPlanList.add(srednioporowate)
        getPlanList.add(niskoporowate)
        getPlanList.add(wysokoporowate)
        getPlanList.add(volume)
        getPlanList.add(grow)
        getPlanList.add(greasy)
        println("volume"+ volume.taskList.get("tuesday"))
        return getPlanList
    }
}