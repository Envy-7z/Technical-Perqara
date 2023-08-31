package com.wisnu.technicalassessmentlimaperqara.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import com.wisnu.technicalassessmentlimaperqara.ViewModelFactory
import com.wisnu.technicalassessmentlimaperqara.data.models.Result
import com.wisnu.technicalassessmentlimaperqara.databinding.FragmentHomeBinding
import com.wisnu.technicalassessmentlimaperqara.ui.activity.DetailActivity
import com.wisnu.technicalassessmentlimaperqara.ui.adapter.GamesAdapter
import com.wisnu.technicalassessmentlimaperqara.ui.viewmodel.GamesViewModel
import com.wisnu.technicalassessmentlimaperqara.utils.LoadingStateAdapter
import com.wisnu.technicalassessmentlimaperqara.utils.visible
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val gameAdapter: GamesAdapter by lazy {
        GamesAdapter(
            callback = ::openDetail
        )
    }

    lateinit var dataGame: PagingData<Result>
    var job: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservable()
        setupViews()

    }

    private fun setObservable() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val gamesVm: GamesViewModel by activityViewModels {
            factory
        }
        gamesVm.getGames().observe(viewLifecycleOwner) {
            binding.rvUsers.visible()
            dataGame = it
            gameAdapter.submitData(lifecycle, dataGame)
        }

    }

    private fun setupViews() {
        with(binding) {

            etSearch.addTextChangedListener {
                job?.cancel()
                job = lifecycleScope.launch {
                    delay(1000L)
                    it.let {
                        if (it.toString().isNotEmpty()) {
                            doSearch(it.toString())
                        }
                    }
                }
            }
            swipeBlast.setOnRefreshListener { gameAdapter.refresh() }

            rvUsers.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
            rvUsers.adapter = gameAdapter.withLoadStateFooter(LoadingStateAdapter {
                gameAdapter.retry()
            })

            gameAdapter.addLoadStateListener {
                binding.swipeBlast.isRefreshing = it.refresh is LoadState.Loading

                val isErrorOrEmpty = it.refresh is LoadState.Error ||
                        (it.refresh is LoadState.NotLoading && gameAdapter.itemCount == 0)
                tvLoadState.isVisible = isErrorOrEmpty
                if (it.refresh is LoadState.Error) {
                    tvLoadState.text = "Something Went Wrong"
                }

                if (it.refresh is LoadState.NotLoading && gameAdapter.itemCount == 0) {
                    tvLoadState.text = "Data is empty"
                }
                rvUsers.isVisible = !isErrorOrEmpty
            }
        }
    }

    private fun doSearch(search: String) {
        if (search.isNotEmpty()) {
            val filterList = dataGame.filter {
                it.name.contains(search, true)
            }
            gameAdapter.submitData(lifecycle, filterList)
        } else {
            gameAdapter.submitData(lifecycle, dataGame)
        }
    }


    private fun openDetail(item: Result) {
        requireActivity().startActivity(Intent(context, DetailActivity::class.java).apply {
            putExtra("id",item.id)
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}