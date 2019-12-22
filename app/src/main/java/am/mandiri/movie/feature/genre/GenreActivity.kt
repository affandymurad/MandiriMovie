package am.mandiri.movie.feature.genre

import am.mandiri.movie.R
import am.mandiri.movie.base.BaseActivity
import am.mandiri.movie.base.CustomLinearLayoutManager
import am.mandiri.movie.model.Genre
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_genre.*

class GenreActivity : BaseActivity() {

    private lateinit var viewModel: GenreViewModel

    private lateinit var presenter: GenrePresenter

    private val adapter = GenreAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)

        setupViewModel()
        setupPresenter()
        setupRecyclerView()
        setupRefreshLayout()

        presenter.fetch()

        observe(viewModel.error, this::whenErrorChanged)
        observe(viewModel.genreList, this::whenGenreListChanged)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(GenreViewModel::class.java)
    }

    private fun setupPresenter() {
        presenter = GenrePresenter(viewModel)
    }

    private fun setupRecyclerView() {
        rvGenre.layoutManager = CustomLinearLayoutManager(this)
        rvGenre.adapter = adapter
        adapter.footerLayout = R.layout.progress
    }

    private fun setupRefreshLayout() {
        srlGenre.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        srlGenre?.setOnRefreshListener {
            presenter.fetch()
        }
    }

    private fun whenErrorChanged(reason: String) {
        srlGenre.isRefreshing = false
        adapter.footerLayout = if (adapter.items.count() == 0) R.layout.empty else R.layout.nothing
        Snackbar.make(findViewById(android.R.id.content), reason, Snackbar.LENGTH_SHORT).show()
    }

    private fun whenGenreListChanged(billingList: List<Genre>) {
        srlGenre.isRefreshing = false
        adapter.items = billingList
        adapter.footerLayout = if (adapter.items.count() == 0) R.layout.empty else R.layout.nothing
        if (adapter.items.count() <= 20) {
            rvGenre.scrollToPosition(0)
        }
    }

}

