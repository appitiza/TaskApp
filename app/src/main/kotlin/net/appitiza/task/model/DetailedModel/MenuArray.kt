package net.appitiza.task.model.DetailedModel

data class MenuArray(
        val itemId: String,
        val itemResId: String,
        val itemFoodType: String,
        val itemCatId: String,
        val itemName_eng: String,
        val itemName_arb: String,
        val itemDesc_eng: String,
        val itemDesc_arb: String,
        val itemDet_eng: String,
        val itemDet_arb: String,
        val itemMinQty: String,
        val itemProt: String,
        val itemCarb: String,
        val itemFat: String,
        val calories: Int,
        val itemImage: String,
        val logoImage: String,
        val logoImage1: String,
        val logoImage2: String,
        val isType: String,
        val itemPrice: String,
        val isPromo: String,
        val isRecomm: String,
        val isMostSell: String,
        val status: String,
        val isSoldout: String,
        val orgitemImage: String,
        val itemImage_2: String,
        val orgitemImage_2: String,
        val itemImage_3: String,
        val orgitemImage_3: String,
        val choiceArray: List<Any>
)