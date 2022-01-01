package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madcamp_1st_week.databinding.FragmentFeedDetailBinding

class FeedDetailFragment : Fragment() {
    private var _binding:FragmentFeedDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val resourceID = args?.getInt("img")!! // 이전 fragment로부터 넘겨받은 resourceID 값을 변수에 저장
        binding.feedDetailImg.setImageResource(resourceID) // 현재 fragment의 feedDetailImg의 Image Resource를 resourceID 값으로 저장
    }
}