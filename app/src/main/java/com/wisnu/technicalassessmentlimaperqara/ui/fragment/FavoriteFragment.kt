package com.wisnu.technicalassessmentlimaperqara.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wisnu.technicalassessmentlimaperqara.data.models.FavEntity
import com.wisnu.technicalassessmentlimaperqara.databinding.FragmentFavoriteBinding
import com.wisnu.technicalassessmentlimaperqara.ui.activity.DetailActivity
import com.wisnu.technicalassessmentlimaperqara.ui.adapter.FavoriteAdapter
import com.wisnu.technicalassessmentlimaperqara.ui.viewmodel.DetailViewModel
import com.wisnu.technicalassessmentlimaperqara.utils.visible


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
    }

    private fun setObservable() {
        val favoriteViewModel = setViewModel()
        favoriteViewModel.getAllDataFav().observe(viewLifecycleOwner) {
            if (it.isEmpty()){
                binding.tvLoadState.visible()
            }else{
                setFav(it)
            }
        }

        favoriteViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setViewModel(): DetailViewModel {
        val factory = DetailViewModel.ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(requireActivity(), factory)[DetailViewModel::class.java]
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setFav(mainData: List<FavEntity>) {
        val items = arrayListOf<FavEntity>()
        mainData.map {
            val item = FavEntity(
                name = it.name,
                rating = it.rating,
                background_image = it.background_image,
                released = it.released,
                id = it.id
            )
            items.add(item)
        }
        val adapter = FavoriteAdapter(items)
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavEntity) {
                requireActivity().startActivity(Intent(context, DetailActivity::class.java).apply {
                    putExtra("id",data.id)
                })
            }
        })
    }
}