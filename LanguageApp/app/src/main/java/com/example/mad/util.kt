package com.example.mad

import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (Fragment) -> T,
    val cleanUp: ((T?) -> Unit)?,
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerObserver = Observer<LifecycleOwner?> { owner ->
                if (owner == null) {
                    cleanUp?.invoke(binding)
                    binding = null
                }
            }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(
                    viewLifecycleOwnerObserver
                )
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(
                    viewLifecycleOwnerObserver
                )
            }
        })
    }

    override fun getValue(
        thisRef: Fragment,
        property: KProperty<*>,
    ): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = thisRef.viewLifecycleOwner.lifecycle
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED).not()) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }

        return viewBindingFactory(thisRef).also { this.binding = it }
    }
}

inline fun <T : ViewBinding> Fragment.viewBinding(
    crossinline viewBindingFactory: (View) -> T,
    noinline cleanUp: ((T?) -> Unit)? = null,
): FragmentViewBindingDelegate<T> =
    FragmentViewBindingDelegate(this, { f -> viewBindingFactory(f.requireView()) }, cleanUp)


inline fun <T : ViewBinding> AppCompatActivity.viewInflateBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }
