package net.appitiza.task.network

import io.reactivex.Observable
import net.appitiza.task.model.storeModel.StoreDetails
import retrofit2.http.GET
import retrofit2.http.Query


interface StoreApi {

    @GET("services.php")
    fun getStore(@Query("action") action: String,
                 @Query("langId") langId: Int,
                 @Query("countryId") countryId: Int,
                 @Query("areaId") areaId: Int,
                 @Query("rId") rId: Int): Observable<StoreDetails>


}