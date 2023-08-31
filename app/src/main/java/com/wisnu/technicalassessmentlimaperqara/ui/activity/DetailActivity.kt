package com.wisnu.technicalassessmentlimaperqara.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.wisnu.technicalassessmentlimaperqara.R
import com.wisnu.technicalassessmentlimaperqara.ViewModelFactory
import com.wisnu.technicalassessmentlimaperqara.data.models.DetailGamesResponse
import com.wisnu.technicalassessmentlimaperqara.data.models.FavEntity
import com.wisnu.technicalassessmentlimaperqara.data.models.Results
import com.wisnu.technicalassessmentlimaperqara.databinding.ActivityDetailBinding
import com.wisnu.technicalassessmentlimaperqara.ui.viewmodel.DetailViewModel
import com.wisnu.technicalassessmentlimaperqara.ui.viewmodel.GamesViewModel
import com.wisnu.technicalassessmentlimaperqara.utils.setOrGone
import com.wisnu.technicalassessmentlimaperqara.utils.visible

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.ViewModelFactory.getInstance(application)
    }

    private var isFavorite: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservable()
    }


    private fun setObservable() {

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)

        val gamesVm: GamesViewModel by viewModels {
            factory
        }

        val id = intent.getIntExtra("id", 0)
        gamesVm.getDetailGames(id).observe(this) {
            when (it) {
                is Results.Error -> Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                Results.Loading -> {
                }

                is Results.Success -> {
                    with(binding) {
                        Glide.with(binding.root)
                            .load(it.data.background_image)
                            .error(R.drawable.baseline_report_gmailerrorred_24)
                            .into(imgStoryDetail)
                        tvPub.text = it.data.publishers[0].name
                        tvRelease.text = it.data.formatRelease()
                        tvRate.text = it.data.rating.toString()
                        tvPlay.text = it.data.playFormated()
                        tvDesc.text = it.data.description_raw
                        tvTitle.text = it.data.name
                    }
                    setFavorite(it.data)
                }
            }
        }
    }


    private fun setFavorite(data: DetailGamesResponse) {
        viewModel.getDataByUsername(data.name).observe(this) {
            isFavorite = it.isNotEmpty()
            val favoriteEntity = FavEntity(data.id, data.background_image,data.name,data.released,data.rating)
            if (it.isEmpty()) {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.ic_favorite_border
                    )
                )
                binding.fabFavorite.contentDescription = getString(R.string.favorite_added)
            } else {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.ic_favorite_full
                    )
                )
                binding.fabFavorite.contentDescription = getString(R.string.favorite_removed)
            }

            binding.fabFavorite.setOnClickListener {
                if (isFavorite) {
                    viewModel.deleteData(favoriteEntity)
                    Toast.makeText(this, R.string.favorite_removed, Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.insertData(favoriteEntity)
                    Toast.makeText(this, R.string.favorite_added, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}