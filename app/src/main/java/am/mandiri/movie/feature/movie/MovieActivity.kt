package am.mandiri.movie.feature.movie

import am.mandiri.movie.R
import am.mandiri.movie.base.BaseActivity
import am.mandiri.movie.base.CustomLinearLayoutManager
import am.mandiri.movie.model.Movie
import am.mandiri.util.RecyclerViewLoadMoreListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_genre.*

class MovieActivity : BaseActivity() {

    private lateinit var viewModel: MovieViewModel

    private lateinit var presenter: MoviePresenter

    private val adapter = MovieAdapter()

    companion object {
        var titles = ""
        var genre : Int = 0
        fun start(context: Context, genreVal: Int, genreTitle: String): Intent {
            val intent = Intent(context, MovieActivity::class.java)
            genre = genreVal
            titles = genreTitle
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)

        setupToolbar()
        setupViewModel()
        setupPresenter()
        setupRecyclerView()
        setupRefreshLayout()

        observe(viewModel.error, this::whenErrorChanged)
        observe(viewModel.movieList, this::whenMovieListChanged)
        observe(viewModel.paginationHasEnded, this::whenPaginationHasEndedChanged)
    }

    private fun setupToolbar(){
        val ab = supportActionBar
        ab?.title = titles
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
    }

    private fun setupPresenter() {
        presenter = MoviePresenter(viewModel)
        presenter.setup(genre)
    }

    private fun setupRecyclerView() {
        rvGenre.layoutManager = CustomLinearLayoutManager(this)
        rvGenre.addOnScrollListener(RecyclerViewLoadMoreListener {
            if (presenter.ongoingRequestHasFinished()) {
                presenter.fetchNextPage()
            }
        })

        rvGenre.adapter = adapter
        adapter.footerLayout = R.layout.progress
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun setupRefreshLayout() {
        srlGenre.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        srlGenre?.setOnRefreshListener {
            presenter.fetch()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.fetch()
    }

    private fun whenErrorChanged(reason: String) {
        srlGenre.isRefreshing = false
        adapter.footerLayout = if (adapter.items.count() == 0) R.layout.empty else R.layout.nothing
        Snackbar.make(findViewById(android.R.id.content), reason, Snackbar.LENGTH_SHORT).show()
    }

    private fun whenMovieListChanged(movieList: List<Movie>) {
        srlGenre.isRefreshing = false
        adapter.items = movieList
        adapter.footerLayout = if (adapter.items.count() == 0) R.layout.empty else R.layout.progress
        if (adapter.items.count() <= 20) {
            rvGenre.scrollToPosition(0)
        }
    }

    private fun whenPaginationHasEndedChanged(paginationHasEnded: Boolean) {
        if (paginationHasEnded) {
            adapter.footerLayout = if (adapter.items.count() == 0) R.layout.empty else R.layout.nothing
        } else {
            adapter.footerLayout = R.layout.progress
        }
    }

}

