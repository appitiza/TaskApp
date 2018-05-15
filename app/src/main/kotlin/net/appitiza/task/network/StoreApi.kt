package net.appitiza.task.network

import net.appitiza.task.model.Post
import io.reactivex.Observable
import net.appitiza.task.model.RestaurantAreaInfo
import net.appitiza.task.model.StoreDetails
import retrofit2.http.GET

interface StoreApi {

    @GET("/Get")
    fun getStore(): Observable<List<RestaurantAreaInfo>>
}