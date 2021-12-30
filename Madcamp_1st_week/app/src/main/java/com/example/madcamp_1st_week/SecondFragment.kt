package com.example.madcamp_1st_week

import android.os.Bundle
import android.renderscript.Sampler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madcamp_1st_week.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private lateinit var feedAdapter: FeedAdapter
    private var _binding:FragmentSecondBinding? = null
    private val binding get() = _binding!!

    val feedImageList = mutableListOf<FeedItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedAdapter = FeedAdapter(this.requireContext())

        feedImageList.add(FeedItem(R.drawable.test_1, "123"))
        feedImageList.add(FeedItem(R.drawable.test_2, "123"))
        feedImageList.add(FeedItem(R.drawable.test_3, "123"))
        feedImageList.add(FeedItem(R.drawable.test_4, "123"))
        feedImageList.add(FeedItem(R.drawable.test_5, "123"))
        feedImageList.add(FeedItem(R.drawable.test_6, "123"))
        feedImageList.add(FeedItem(R.drawable.test_7, "123"))
        feedImageList.add(FeedItem(R.drawable.test_8, "123"))
        feedImageList.add(FeedItem(R.drawable.test_9, "123"))
        feedImageList.add(FeedItem(R.drawable.test_10, "123"))
        feedImageList.add(FeedItem(R.drawable.test_11, "123"))
        feedImageList.add(FeedItem(R.drawable.test_12, "123"))
        feedImageList.add(FeedItem(R.drawable.test_13, "123"))
        feedImageList.add(FeedItem(R.drawable.test_14, "123"))


        feedAdapter.itemList = feedImageList
        binding.feedGalleryView.adapter = feedAdapter

        binding.feedGalleryView.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem: FeedItem = parent?.getItemAtPosition(position) as FeedItem

                val feedDetailFragment = FeedDetailFragment()
                var bundle = Bundle()
                bundle.putInt("img", selectedItem.resourceID)
                feedDetailFragment.setArguments(bundle)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment, feedDetailFragment)
                    ?.commitAllowingStateLoss()
            }
        }
    }
}