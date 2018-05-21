package net.appitiza.task.network

import io.reactivex.Observable
import net.appitiza.task.model.StoreDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailedApi {
    @GET("services.php")
    fun getDetailed(@Query("action") action: String,
                    @Query("rId") rId: Int,
                    @Query("cuisineType") cuisinetype: Int,
                    @Query("countryId") countryId: Int,
                    @Query("langId") langId: Int
    ): Observable<StoreDetails>
}