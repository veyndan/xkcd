package com.veyndan.xkcd

import com.trello.navi2.Event
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.navi2.rx.RxNavi
import com.veyndan.xkcd.ui.FeedUI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.setContentView

class MainActivity : NaviAppCompatActivity() {

    init {
        val component = FeedUI(FeedUI.State(comics = emptyList()))

        RxNavi.observe(this, Event.CREATE)
                .subscribe { _ -> component.setContentView(this) }

        RxNavi.observe(this, Event.CREATE)
                .observeOn(Schedulers.io())
                .flatMap { Xkcd(application).comics().take(2) }
                .observeOn(AndroidSchedulers.mainThread())
                .map { response -> response.body()!! }
                .map { comic -> FeedUI.State(comics = component.state.comics + comic) }
                .takeUntil(RxNavi.observe(this, Event.DESTROY))
                .subscribe { state -> component.state = state }
    }
}
