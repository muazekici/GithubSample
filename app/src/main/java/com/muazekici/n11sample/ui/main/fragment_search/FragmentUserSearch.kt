package com.muazekici.n11sample.ui.main.fragment_search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.muazekici.n11sample.R
import com.muazekici.n11sample.databinding.FragmentUserSearchBinding
import com.muazekici.n11sample.ui.exts.daggerComponent
import com.muazekici.n11sample.ui.main.fragment_detail.FragmentUserDetail
import com.muazekici.n11sample.ui.navigation.showInParentHost
import com.muazekici.n11sample.ui.utils.MarginItemDecorator
import com.muazekici.n11sample.use_cases.base.ifSuccess
import javax.inject.Inject

class FragmentUserSearch : Fragment(R.layout.fragment_user_search) {

    private var _binding: FragmentUserSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<FragmentSearchViewModel> { vmFactory }

    private val usersAdapter by lazy {
        AdapterUserSearchList(
            { p, o ->
                viewModel.favUser(o)
            }, { p, o ->
                showInParentHost(FragmentUserDetail.newInstance(o.userName), true)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(FragmentUserDetail.FRAGMENT_RESULT_KEY) { k, b ->
            viewModel.favUserUpdate(b.getLong(FragmentUserDetail.FRAG_RESULT_USER_ID))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /* In two fragments, different types of injection technique are presented for  explanation purposes.
        * At here, fragment is injected by a component implementing fragment's component. It is used in cases like that
        * if the fragment will be reused in different activities. */
        context.daggerComponent<FragmentSearchDI>().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).also {
            _binding = FragmentUserSearchBinding.bind(it!!)
            binding.viewModel = viewModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.adapter = usersAdapter

        val margin = resources.getDimensionPixelSize(R.dimen.dimen_user_list_item_margin)
        binding.ListSearchResult.addItemDecoration(
            MarginItemDecorator(
                margin,
                margin,
                margin,
                margin,
                true
            )
        )
        binding.ListSearchResult.itemAnimator = null

        viewModel.friends.observe(viewLifecycleOwner) {
            usersAdapter.submitList(it)
        }

        viewModel.updateId.observe(viewLifecycleOwner) {
            usersAdapter.notifyItemChanged(it)
        }
    }

    override fun onDestroyView() {
        binding.ListSearchResult.adapter = null
        _binding = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance() = FragmentUserSearch()

    }
}