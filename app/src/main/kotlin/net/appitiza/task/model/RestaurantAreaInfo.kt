package net.appitiza.task.model

data class RestaurantAreaInfo(val restaurantCurrentStatus: String?,
                              val rId: String?,
                              val rInfoId: String?,
                              val rDeliveryCharge: String?,
                              val rMinOrderAmt: String?,
                              val rDeliveryTime: String?,
                              val jDeliveryCharge: String?,
                              val jDeliveryTime: String?,
                              val rImage: String?,
                              val rPriCuisineId: String?,
                              val rCuisine2: String?,
                              val rCuisine3: String?,
                              val delvType: String?,
                              val typeId: String?,
                              val rName: String?,
                              val rhaveDelivery: String?,
                              val rPaymentId: String?,
                              val branchStatus: String?,
                              val areaName: String?,
                              val workingHour: String?,
                              val cntrCurrency: String?,
                              val cuisineTitle1: String?,
                              val cuisineTitle2: Any?,
                              val cuisineTitle3: Any?,
                              val orgImage: String?,
                              val currStatus: Int?,
                              val paymentType: List<PaymentType>?,
                              val hasOffer: Int?,
                              val openingStatus: String?) {


}