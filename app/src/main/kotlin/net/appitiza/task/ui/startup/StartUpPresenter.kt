package net.appitiza.task.ui.startup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import net.appitiza.task.R
import net.appitiza.task.base.BasePresenter
import net.appitiza.task.ui.mapdisplay.MapDisplayActivity
import net.appitiza.task.ui.store.StoreActivity


class StartUpPresenter(startUpView: StartUpView) : BasePresenter<StartUpView>(startUpView) {



    fun moveToStore(view : View,context: Context) {

        val intent = Intent(context, StoreActivity::class.java)
        val p1 = Pair(view, context.getString(R.string.image_store_detailed))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, p1)
        context.startActivity(intent, options.toBundle())


    }
    fun moveToMap(view : View,context: Context) {

        val intent = Intent(context, MapDisplayActivity::class.java)
        val p1 = Pair(view, context.getString(R.string.image_store_detailed))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, p1)
        context.startActivity(intent, options.toBundle())


    }

    fun moveToSetLoction(view : View,context: Context) {

        val intent = Intent(context, StoreActivity::class.java)
        val p1 = Pair(view, context.getString(R.string.image_store_detailed))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, p1)
        context.startActivity(intent, options.toBundle())


    }

    override fun onViewDestroyed() {
    }
}