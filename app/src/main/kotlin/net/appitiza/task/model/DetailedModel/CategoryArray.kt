package net.appitiza.task.model.DetailedModel


data class CategoryArray(
        val menuName_eng: String,
        val menuName_arb: String,
        val menuCatId: String,
        val mastMId: String,
        val isOffer: Boolean,
        val menuArray: List<MenuArray>
)
