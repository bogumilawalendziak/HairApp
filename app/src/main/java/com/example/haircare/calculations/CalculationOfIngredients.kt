package com.example.haircare.calculations

import com.example.haircare.calendar.TaskEntity
import com.github.mikephil.charting.data.PieEntry

class CalculationOfIngredients {

    fun amountIngredients(list: MutableList<TaskEntity>): ArrayList<PieEntry> {
        var list = list
        var protein: Int = 0
        var humektant: Int = 0
        var emolients: Int = 0
        var allTask: Int = list!!.size

                list?.forEach { taskEntity ->
                    when (taskEntity.peh) {
                        "proteiny" -> {
                            protein += 1
                        }
                        "humektanty" -> {
                            humektant += 1
                        }
                        "emolienty" -> {
                            emolients += 1
                        }
                    }
                }
               /* val proteinPerc = ingredientsPercentage(protein, allTask)
                val humektantPerc = ingredientsPercentage(humektant, allTask)
                val emolientPerc = ingredientsPercentage(emolients, allTask)*/
        val inglist= ArrayList<PieEntry>()
        inglist.add(PieEntry(protein.toFloat(),"proteiny"))
        inglist.add(PieEntry(humektant.toFloat(),"humetanty"))
        inglist.add(PieEntry(emolients.toFloat(),"emolienty"))
                return inglist
            }


    private fun ingredientsPercentage(ingredient: Int, all: Int): Int {
        return (ingredient * 100 / all).toInt()
    }
}
