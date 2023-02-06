package com.example.feedme

object DataManagerDishes {

    val dishes = mutableListOf<Dishes>()

    init {

        createMockData()

    }


    fun createMockData () {
        dishes.add(
            Dishes(
                "Pizza Margaritha", "Mozzarella, tomat, basilika", 85.0, 65.0,
                null, false, false, true, true, true,
                true,false,
                true, false, "Huvudrätt", true, true, false, true,
                5.0, null, null, null, ""
            )
        )


        dishes.add(
            Dishes(
                "Pizza Prosciutto", "Mozzarella, tomat, prosciutto", 95.0, 75.0,
                null, false, false, true, true,
                true, true,false,
                false, false, "Huvudrätt", true, true, false, false,
                5.0, null, null, null, ""
            )
        )
        dishes.add(
            Dishes(
                "Pizza Vesuvio", "Mozzrella, tomat, skinka", 85.0, 65.0,
                null, false, false,
                true, true, true, false, false,
                false, false, "Huvudrätt", true, true, false, false,
                5.0, null, null, null, ""
            )
        )
        dishes.add(
            Dishes(
                "Pizza Verduro",
                "Mozzrella, tomat, zucchini, färska tomater, paprika, basilika",
                85.0,
                65.0,
                null,
                false,false,
                true,
                true,


                true,
                false,
                 false,
                true,
                false,
                "Huvudrätt",
                true,
                true,
                false,
                true,
                5.0,
                null,
                null,
                null,
                ""
            )
        )
        dishes.add(
            Dishes(
                "Pizza Salami", "Mozzrella, tomat, salami, basilika",
                85.0, 65.0,
                null, false, false,true, true,
                 true, true,false,
                false, false, "Huvudrätt", true, true, false, false,
                5.0, null, null, null, ""
            )
        )
        dishes.add(
            Dishes(
                "Pizza Tonno", " Mozzrella, tomat, tonfisk, lök, basilika", 85.0, 65.0,
                null,false, false, true, true,
                false, true,false,
                false, false, "Huvudrätt", true, true, false, false,
                5.0, null, null, null, ""
            )
        )
        dishes.add(
            Dishes(
                "Tiramisu", "En klassiker", 55.0, null,
                null, false, false, false,true, true, true, false,
                false, false, "Efterrätt", false, false, false, false,
                null, null, null, null, ""
            )
        )
        dishes.add(
            Dishes(
                "Pana Cotta", "En klassiker", 55.0, null,
                null,false, false, false, true, true, true, false,
                false, false, "Efterrätt", false, false, false, false,
                null, null, null, null, ""
            )
        )
        dishes.add(
            Dishes(
                "Minestrone", "En klassiker", 55.0, null,
                null, false, false, false,true, true, true, false,
                false, false, "Förrätt", false, false, false, false,
                null, null, null, null, ""
            )
        )


    }
}