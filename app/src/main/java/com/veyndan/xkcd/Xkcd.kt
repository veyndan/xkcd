package com.veyndan.xkcd

import android.app.Application
import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory
import com.squareup.sqlbrite3.BriteDatabase
import com.squareup.sqlbrite3.SqlBrite
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber

class Xkcd(private val application: Application) {

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://xkcd.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    private val service: Service = retrofit.create(Service::class.java)

    private val sqlBrite = SqlBrite.Builder()
            .logger { message -> Timber.tag("Database").v(message) }
            .build()

    private val database: BriteDatabase by lazy {
        val configuration = SupportSQLiteOpenHelper.Configuration.builder(application)
                .name("todo.db")
                .callback(DbCallback())
                .build()
        val factory = FrameworkSQLiteOpenHelperFactory()
        val helper = factory.create(configuration)
        sqlBrite.wrapDatabaseHelper(helper, Schedulers.io()).apply {
            setLoggingEnabled(true)
        }
    }

    fun comics(): Observable<Response<Comic>> {
        val disk = service.num(2).toObservable()
        val network = service.latest()
                .map { it.body()!!.num }
                .flatMapObservable { latest -> Observable.range(1, latest) }
                .flatMapSingle { service.num(it) }

        return disk.firstElement()
                .flatMapObservable { firstDiskComic ->
                    network.takeWhile { networkComic ->
                        firstDiskComic.body()!!.num != networkComic.body()!!.num
                    }
                }
                .switchIfEmpty(network)
    }

    private interface Service {

        @GET("info.0.json")
        fun latest(): Single<Response<Comic>>

        @GET("{num}/info.0.json")
        fun num(@Path("num") num: Int): Single<Response<Comic>>
    }
}
