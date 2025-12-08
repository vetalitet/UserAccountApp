package com.vetalitet.useraccountapp.core.base

import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected fun showError(message: String?) {
        Toast.makeText(requireContext(), message ?: "Unknown error", LENGTH_LONG).show()
    }

}