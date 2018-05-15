package net.appitiza.task.network

import net.appitiza.task.model.Post
import io.reactivex.Observable
import net.appitiza.task.model.RestaurantAreaInfo
import net.appitiza.task.model.StoreDetails
import retrofit2.http.GET
import retrofit2.http.Query



interface StoreApi {

    @GET("/services.php")
    fun getStore(@Query("action") action: String,
                 @Query("langId") langId: Int,
                 @Query("countryId") countryId: Int,
                 @Query("areaId") areaId: Int,
                 @Query("rId") rId: Int) : Observable<StoreDetails>
    @GET("/services.php?action=restaurantAreaInfo&langId=1&countryId=21&areaId=1&rId=366")
    fun getStore() : Observable<StoreDetails>
}