package fr.tandjigora.naturecollection

class PlantModel(
    val id: String = "plant0",
    val name: String = "Tulipe",
    val description: String = "Petite description",
    val imageUrl: String = "htpp://graven.yt/plant.jpg",
    val grow: String = "Lente",
    val water: String = "Faible",
    var price: Int = 5,
    var quantity : Int = 0,
    var liked: Boolean = false,
    var inBasket: Boolean = false

)