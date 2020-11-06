package com.example.mymvdb.ui.ui.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymvdb.R
import com.example.mymvdb.api.POSTER_BASE_URL
import com.example.mymvdb.database.MovieDetails
import com.example.mymvdb.databinding.FavoritesListItemBinding
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.coroutines.launch

class FavoritesListAdapter (
    public val    context: Context , private val  favoritesViewModel: FavoritesViewModel
) : RecyclerView.Adapter<FavoritesListAdapter.FavoritesViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var movies = emptyList<MovieDetails>() // Cached copy of movies

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding=FavoritesListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val itemView = inflater.inflate(R.layout.favorites_list_item, parent, false)
        return FavoritesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val current = movies[position]
        with(holder) {
           binding.apply {
               favListItemAdultIV.visibility=if (current.adult) View.VISIBLE else View.GONE
               favListItemReleaseDateTV.text=current.release_date
               favListItemTitleTV.text=current.original_title
               favListItemVoteAverageValueTv.text="${current.vote_average} /10"
               favListItemRBar.rating=current.vote_average.toFloat().div(2)
               val posterPathUrl :String= POSTER_BASE_URL + current?.poster_path
               Glide.with(context).load(posterPathUrl).into(itemView.list_item_poster_IV)
            favListItemRemove.setOnClickListener{
                favoritesViewModel.viewModelScope.launch { favoritesViewModel.delete(current) }
                notifyDataSetChanged()
            }
           }
           /* itemView.setOnClickListener {
                val intent = Intent(it.context, MovieDetailActivity::class.java)
                intent.putExtra("movie",current)
                context.startActivity(intent)
            }*/
        }
    }

    internal fun setmovies(movies: List<MovieDetails>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size
}
