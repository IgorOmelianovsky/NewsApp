package com.example.p57_news.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.view.postDelayed
import com.example.p57_news.databinding.FragmentWebBinding
import com.example.p57_news.ui.MainActivity
import com.example.p57_news.viewmodel.MainViewModel

class WebFragment : Fragment() {
    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebBinding.inflate(inflater)
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

    private fun initialise() = with(binding) {
        viewModel = (requireActivity() as MainActivity).viewModel
        val webView = webView
        webView.webViewClient = WebViewClient()

        viewModel.currentArticle.observe(viewLifecycleOwner) {
            webView.loadUrl(it.url)
        }

        btnFavorite.setOnClickListener {
            it.postDelayed(400) {
                viewModel.insertArticle(viewModel.currentArticle.value!!)
                it.visibility = View.GONE
            }
        }
    }

}















