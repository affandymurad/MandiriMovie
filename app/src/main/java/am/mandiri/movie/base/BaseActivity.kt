package am.mandiri.movie.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseActivity : AppCompatActivity() {

    fun <T> observe(observable: LiveData<T>, observer: (T) -> Unit) {
        observable.observe(this, Observer {
            if (it == null) return@Observer
            observer(it)
        })
    }
}