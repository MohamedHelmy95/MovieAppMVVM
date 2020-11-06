package com.example.mymvdb.ui.ui.upcoming

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymvdb.R
import com.example.mymvdb.api.POSTER_BASE_URL
import com.example.mymvdb.movieDetail.Movie
import com.example.mymvdb.ui.ui.singlemovie.MovieDetailActivity
import com.example.mymvdb.utility.NetworkState
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

class UpcomingPagedListAdapter(public val context: Context?) :PagedListAdapter<Movie,RecyclerView.ViewHolder>(MovieDiffCallback()) {
  val MOVIE_VIEW_TYPE=1
    val NETWORK_VIEW_TYPE=2
    private var networkState:NetworkState?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val layoutInflater:LayoutInflater= LayoutInflater.from(parent.context)
        val view:View
        if(viewType==MOVIE_VIEW_TYPE){
            view=layoutInflater.inflate(R.layout.movie_list_item,parent,false )
            return MovieItemViewHolder(view)
        }else{
            view=layoutInflater.inflate(R.layout.network_state_item,parent,false)
            return  NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if(getItemViewType(position)==MOVIE_VIEW_TYPE){
        if (context != null) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
    }else{
        (holder as NetworkStateItemViewHolder).bind(networkState)
    }
    }
private fun hasExtraRow():Boolean{
    return networkState!=null &&networkState!=NetworkState.LOADED
}

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow()&&position==itemCount-1){
            NETWORK_VIEW_TYPE
        }else{
            MOVIE_VIEW_TYPE
        }
    }
    class MovieDiffCallback:DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
             return oldItem==newItem
        }

    }
    class MovieItemViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(movie: Movie?, context :Context){
          itemView.list_item_title_TV.text=movie?.title
            itemView.list_item_releaseDate_TV.text=movie?.release_date
            itemView.list_item_voteAverageValueTv.text="${movie?.vote_average.toString()} /10"
            itemView.list_item_rBar.rating=movie?.vote_average!!.toFloat().div(2)
            val posterPathUrl :String= POSTER_BASE_URL + movie?.poster_path
            Glide.with(context).load(posterPathUrl).into(itemView.list_item_poster_IV)
        itemView.list_item_adult_IV.visibility= if (movie.adult) View.VISIBLE else View.GONE
        itemView.setOnClickListener {
            val intent = Intent(it.context, MovieDetailActivity::class.java)
            intent.putExtra("id",movie?.id)
            context.startActivity(intent)
        }
        }
    }
    class NetworkStateItemViewHolder(view : View):RecyclerView.ViewHolder(view){
        fun bind(networkState: NetworkState?){
            if(networkState!=null&&networkState==NetworkState.LOADING){
                itemView.progress_bar_network.visibility=View.VISIBLE
            }else{
                itemView.progress_bar_network.visibility=View.GONE
            }
            if(networkState!=null&&(networkState==NetworkState.ERROR||networkState==NetworkState.ENDOFLIST)){
                itemView.text_error_network.visibility=View.VISIBLE
                itemView.text_error_network.text=networkState.msg
            }else {
                itemView.text_error_network.visibility=View.GONE
            }

        }
    }
fun setNetworkState(newNetworkState: NetworkState?){
    val previousState:NetworkState?=this.networkState
    val hadExtraRow:Boolean=hasExtraRow()
    this.networkState=newNetworkState
    val hasExtraRow:Boolean=hasExtraRow()
    if(hadExtraRow!=hasExtraRow){
        if(hadExtraRow)
        notifyItemRemoved(super.getItemCount())
        else
            notifyItemInserted(super.getItemCount())
    }else if(hasExtraRow&&previousState!=newNetworkState){
        notifyItemChanged(itemCount-1)
    }
}
}

