package com.example.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.extensions.setUp
import com.example.podcasts.R

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

    fun showDialog(desc: String){
        val dialog = Dialog(requireContext())
        dialog.setUp(R.layout.dialog_layout)
        dialog.findViewById<TextView>(R.id.description).text = desc
        dialog.findViewById<Button>(R.id.closingButton).setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}