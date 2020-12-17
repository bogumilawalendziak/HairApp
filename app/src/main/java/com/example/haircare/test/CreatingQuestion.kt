package com.example.haircare.test

object CreatingQuestion {
    const val HAIR_TYPE: String = "hair_Type"

    fun getQuestion(): ArrayList<SingleQuestion> {

        val questionList = ArrayList<SingleQuestion>()
        //Questions
        val que1 = SingleQuestion(
            1,
            "Czy twoje włosy mają tendencję do puszenia?",
            "tak",
            "tylko przy dużej wilgotności",
            "nie",
            "tylko na końcówkach"
        )
        val que2 = SingleQuestion(
            2,
            "Czy twoje włosy bez uzycia suszarki schną szybko",
            "szybko",
            "długo - ok 2h",
            "bardzo dłgo- pow 5h",
            "część włosów schnie szybciej"
        )
        val que3 = SingleQuestion(
            3,
            "Czy Twoje włosy się strączkują?",
            "Nie, nie mam tego problemu",
            "Tak, strączkują się i muszę często je przeczesywać",
            "Tak, ale to loki więc o to mi chodzi",
            "Czasami się strączkują, ale nie jest to duży problem"
        )
        val que4 = SingleQuestion(
            4,
            "Czy Twoje włosy są podatne na stylizację?",
            "Tak, bardzo łatwo je wystylizować, zakręcić",
            "Są podatne, ale nie na każdy rodzaj stylizacji lub szybko wracają do stanu wyjściowego",
            "Nie, są bardzo oporne na stylizację",
            "Są naturalnie kręcone"
        )

        questionList.add(que1)
        questionList.add(que2)
        questionList.add(que3)
        questionList.add(que4)

        return questionList
    }
}
