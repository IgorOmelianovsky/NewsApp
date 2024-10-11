package com.example.p57_news.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.p57_news.R
import com.example.p57_news.adapter.ArticleAdapter
import com.example.p57_news.databinding.FragmentFavoritesBinding
import com.example.p57_news.model.Article
import com.example.p57_news.ui.MainActivity
import com.example.p57_news.util.ClickHelper
import com.example.p57_news.util.SwipeHelper
import com.example.p57_news.viewmodel.MainViewModel

class FavoritesFragment : Fragment(), ClickHelper {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialise()
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
        adapter = ArticleAdapter(this@FavoritesFragment, viewModel)
        val itemTouchHelper = SwipeHelper.getItemTouchHelper(adapter)
        recyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(recyclerView)

        viewModel.readArticles().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}























