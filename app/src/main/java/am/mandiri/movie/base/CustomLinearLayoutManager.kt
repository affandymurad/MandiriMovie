package am.mandiri.movie.base

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomLinearLayoutManager(context: Context?) : LinearLayoutManager(context) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: Exception) {
            Log.d("milk-crown", "silent a bug .-. : ${e.localizedMessage}")
        }
    }

}