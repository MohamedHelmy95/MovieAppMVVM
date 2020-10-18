package com.example.mymvdb.ui.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymvdb.R

class UpComingFragment : Fragment() {

    private lateinit var upComingViewModel: UpComingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        upComingViewModel =
                ViewModelProvider(this).get(UpComingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_upcoming, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        upComingViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}