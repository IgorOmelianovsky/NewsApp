package com.example.p57_news.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.p57_news.R
import com.example.p57_news.adapter.ArticleAdapter
import com.example.p57_news.databinding.FragmentHomeBinding
import com.example.p57_news.model.Article
import com.example.p57_news.ui.MainActivity
import com.example.p57_news.util.AppConstants
import com.example.p57_news.util.ClickHelper
import com.example.p57_news.util.Resource
import com.example.p57_news.viewmodel.MainViewModel

class HomeFragment : Fragment(), ClickHelper {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialise()
        initialiseButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(model: Article) {
        viewModel.setCurrentArticle(model)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.placeHolder, WebFragment())
            .commit()
    }

    private fun initialise() = with(binding) {
        viewModel = (requireActivity() as MainActivity).viewModel
        adapter = ArticleAdapter(this@HomeFragment)
        recyclerView.adapter = adapter

        observe()
    }

    private fun observe() {
        viewModel.currentTopHeadlines.observe(viewLifecycleOwner) {
            when (it) {
                Resource.Loading -> loading(true)

                is Resource.Error -> {
                    loading(false)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Success -> {
                    loading(false)
                    adapter.submitList(it.response.articles)
                }
            }
        }

        viewModel.currentCategory.observe(viewLifecycleOwner) {
            viewModel.getTopHeadlines(it)
        }
    }

    private fun loading(enable: Boolean) = with(binding) {
        if (enable) {
            recyclerView.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun initialiseButtons() = with(binding) {
        btnBusiness.setOnClickListener {
            viewModel.setCurrentCategory(AppConstants.BUSINESS)
        }

        btnEntertainment.setOnClickListener {
            viewModel.setCurrentCategory(AppConstants.ENTERTAINMENT)
        }

        btnGeneral.setOnClickListener {
            viewModel.setCurrentCategory(AppConstants.GENERAL)
        }

        btnHealth.setOnClickListener {
            viewModel.setCurrentCategory(AppConstants.HEALTH)
        }

        btnScience.setOnClickListener {
            viewModel.setCurrentCategory(AppConstants.SCIENCE)
        }

        btnSports.setOnClickListener {
            viewModel.setCurrentCategory(AppConstants.SPORTS)
        }

        btnTechnology.setOnClickListener {
            viewModel.setCurrentCategory(AppConstants.TECHNOLOGY)
        }
    }

}






















