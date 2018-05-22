package net.appitiza.task.network

import io.reactivex.Observable
import net.appitiza.task.model.detailedmodel.ListCategory
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailedApi {
    @GET("services.php")
    fun getDetailed(@Query("action") action: String,
                    @Query("rId") rId: String?,
                    @Query("cuisineType") cuisinetype: Int,
                    @Query("countryId") countryId: Int,
                    @Query("langId") langId: Int
    ): Observable<ListCategory>
}