package net.appitiza.task.model.detailedmodel


data class CategoryArray(
        val menuName_eng: String,
        val menuName_arb: String,
        val menuCatId: String,
        val mastMId: String,
        val isOffer: Boolean,
        val menuArray: List<MenuArray>
)
