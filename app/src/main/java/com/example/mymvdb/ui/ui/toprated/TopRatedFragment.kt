package com.example.mymvdb.ui.ui.toprated

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mymvdb.R
import com.example.mymvdb.ui.ui.singlemovie.MovieDetailActivity

class TopRatedFragment : Fragment() {

    private lateinit var topRatedViewModel: TopRatedViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        topRatedViewModel =
                ViewModelProvider(this).get(TopRatedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_toprated, container, false)
        val btn: Button = root.findViewById(R.id.btn)
        topRatedViewModel.text.observe(viewLifecycleOwner, {
            btn.setOnClickListener {
                val intent = Intent(it.context, MovieDetailActivity::class.java)
                intent.putExtra("id",299534)
                startActivity(intent)
            }


        })
        return root
    }
}