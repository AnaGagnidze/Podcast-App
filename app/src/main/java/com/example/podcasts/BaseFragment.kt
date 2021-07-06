package com.example.podcasts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflater<I> = (LayoutInflater, ViewGroup?, Boolean) -> I

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflater<VB>
) : Fragment() {



    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = inflate.invoke(inflater, container, false)
            setUpFragment()
        }
        return binding.root
    }

    abstract fun setUpFragment()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}