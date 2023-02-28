import com.google.firebase.firestore.DocumentId
/*
Class holding information about drinks that is
 offered by resturant with documentid.
 */
data class Drink(
    @DocumentId var drinkId:String? = null,
    var imagePath: String = "",
    var drinkName:String = "", // ex: Coca Cola, Pepsi, Falcon etc..
    var drinkSize: String = "", // ex: 33cl, 50cl, 1 bottle etc..
    var drinkPrice: Double = 0.0,
    var drinkType:String = "", // ex: wine, soda, beer etc..
    var count: Int = 0,
    @DocumentId var restaurantDocumentId: String? = null
)