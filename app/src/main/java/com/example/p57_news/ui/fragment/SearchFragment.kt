package com.example.p57_news.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.p57_news.R
import com.example.p57_news.adapter.ArticleAdapter
import com.example.p57_news.databinding.FragmentSearchBinding
import com.example.p57_news.model.Article
import com.example.p57_news.ui.MainActivity
import com.example.p57_news.util.ClickHelper
import com.example.p57_news.util.Resource
import com.example.p57_news.viewmodel.MainViewModel

class SearchFragment : Fragment(), ClickHelper {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialise()
        initialiseSearch()
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
        adapter = ArticleAdapter(this@SearchFragment)
        recyclerView.adapter = adapter

        observe()
    }

    private fun observe() {
        viewModel.currentEverything.observe(viewLifecycleOwner) {
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

    private fun initialiseSearch() = with(binding) {
        btnSearch.setOnClickListener {
            val query = etQuery.text.toString().trim()

            if (query.isNotBlank()) {
                viewModel.getEverything(query)
                etQuery.setText("")
            }
        }
    }

}























