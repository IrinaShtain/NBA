package com.shtain.nba.presentation.common.viewbinding

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Holds and manages ViewBinding inside a Fragment.
 */
interface ViewBindingHolder<T : ViewBinding> {

    /**
     *  Binding of the current fragment
     *
     * This property is only valid between onCreateView and onDestroyView.
     */
    val binding: T

    /**
     * Saves the binding for cleanup on onDestroy, calls the specified function [onBound] with `this` value
     * as its receiver and returns the bound view root.
     */
    fun initBinding(binding: T, fragment: Fragment, onBound: (T.() -> Unit)? = null): View
}

class ViewBindingHolderImpl<T : ViewBinding> : ViewBindingHolder<T>, DefaultLifecycleObserver {

    private lateinit var fragmentName: String

    private var _binding: T? = null

    /**
     * This flag is needed for not nullifying binding in post transaction if the fragment is initialized again
     */
    private val needClearBinding = AtomicBoolean()

    override val binding: T
        get() = _binding
            ?: throw IllegalStateException("Accessing binding outside of Fragment lifecycle: $fragmentName")

    var lifecycle: Lifecycle? = null


    /**
     * To not leak memory we nullify the binding when the view is destroyed.
     */
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        lifecycle?.removeObserver(this) // not mandatory, but preferred
        lifecycle = null
        needClearBinding.set(true)
        mainHandler.post {
            if (needClearBinding.getAndSet(false))
                _binding = null
        }
    }

    override fun initBinding(binding: T, fragment: Fragment, onBound: (T.() -> Unit)?): View {
        _binding = binding
        lifecycle = fragment.viewLifecycleOwner.lifecycle
        lifecycle?.addObserver(this)
        needClearBinding.set(false)
        fragmentName = fragment.javaClass.simpleName
        onBound?.invoke(binding)
        return binding.root
    }

    private companion object {

        private val mainHandler = Handler(Looper.getMainLooper())

    }
}