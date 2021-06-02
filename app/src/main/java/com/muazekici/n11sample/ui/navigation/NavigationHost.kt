package com.muazekici.n11sample.ui.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.muazekici.n11sample.ui.navigation.NavigationHost.Companion.INITIAL_FRAG_TAG

interface NavigationHost {

    fun showFragment(
        fragment: Fragment,
        addToBackStack: Boolean,
        backStackTag: String?,
        transactionOp: (transaction: FragmentTransaction, fragmentManager: FragmentManager, containerId: Int, fragment: Fragment) -> Unit
    )

    val fragmentContainer: Int


    fun consumeBackPress(): Boolean

    fun clearFlowFromStack(flowTag: String?, isInclusive: Boolean = true)

    companion object {
        const val INITIAL_FRAG_TAG = "INITIAL_FRAG"
    }
}


abstract class NavigationHostActivity() :
    AppCompatActivity(), NavigationHost {

    protected fun initNavigation(fragment: Fragment, savedInstanceState: Bundle?) {
        if (savedInstanceState == null && supportFragmentManager.findFragmentById(fragmentContainer) == null) {
            supportFragmentManager.beginTransaction().add(
                fragmentContainer, fragment,
                INITIAL_FRAG_TAG
            ).commit()
        }
    }

    override fun showFragment(
        fragment: Fragment,
        addToBackStack: Boolean,
        backStackTag: String?,
        transactionOp: (transaction: FragmentTransaction, fragmentManager: FragmentManager, containerId: Int, fragment: Fragment) -> Unit
    ) {
        supportFragmentManager.beginTransaction()
            .apply {
                transactionOp(this, supportFragmentManager, fragmentContainer, fragment)
                if (addToBackStack) addToBackStack(backStackTag)
            }
            .commit()
    }

    override fun consumeBackPress(): Boolean {
        val childFrag = supportFragmentManager.findFragmentById(fragmentContainer)
        if (childFrag is NavigationHost) {
            if (childFrag.consumeBackPress()) return true
        }
        return if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
            true
        } else {
            false
        }
    }

    override fun clearFlowFromStack(flowTag: String?, isInclusive: Boolean) {
        supportFragmentManager.popBackStack(
            flowTag,
            if (isInclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0
        )
    }

    override fun onBackPressed() {
        if (!consumeBackPress()) {
            super.onBackPressed()
        }
    }
}


fun Fragment.showInParentHost(
    fragment: Fragment,
    isBackStack: Boolean = false,
    backStackTag: String? = null
) {
    val parentFrag = parentFragment
    val parentActivity = activity
    if (parentFrag is NavigationHost) {
        parentFrag.showFragment(
            fragment,
            isBackStack,
            backStackTag,
            simpleDetachTransaction
        )
    } else if (parentActivity is NavigationHost) {
        parentActivity.showFragment(
            fragment,
            isBackStack,
            backStackTag,
            simpleDetachTransaction
        )
    }
}

val simpleReplaceTransaction: (transaction: FragmentTransaction, fragmentManager: FragmentManager, containerId: Int, fragment: Fragment) -> Unit =
    { transaction, _, containerId, fragment ->
        transaction.replace(
            containerId,
            fragment
        )
    }

val simpleDetachTransaction: (transaction: FragmentTransaction, fragmentManager: FragmentManager, containerId: Int, fragment: Fragment) -> Unit =
    { transaction, fm, containerId, fragment ->
        fm.findFragmentById(containerId)?.let {
            transaction.detach(it)
        }
        transaction.add(containerId,fragment)
    }