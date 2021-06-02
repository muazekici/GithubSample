package com.muazekici.n11sample.ui.main.fragment_detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.muazekici.n11sample.R
import com.muazekici.n11sample.databinding.FragmentUserDetailBinding
import com.muazekici.n11sample.databinding.FragmentUserSearchBinding
import com.muazekici.n11sample.ui.exts.daggerComponent
import com.muazekici.n11sample.ui.exts.withArgs
import com.muazekici.n11sample.ui.main.di.MainActivityComponent
import com.muazekici.n11sample.ui.main.fragment_search.FragmentSearchDI
import com.muazekici.n11sample.ui.main.fragment_search.FragmentSearchViewModel
import timber.log.Timber
import javax.inject.Inject

class FragmentUserDetail : Fragment(R.layout.fragment_user_detail) {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<FragmentDetailViewModel> { vmFactory }

    private lateinit var userName: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /* In two fragments, different types of injection technique are presented for  explanation purposes.
        * At here, fragment is injected  directly from Activity Component. It is used in cases like that
        * if it is 100% sure that the fragment will be used only in the corresponding activity. */
        context.daggerComponent<MainActivityComponent>().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userName = requireArguments().getString(USER_NAME_KEY)!!
        viewModel.getUserDetail(userName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).also {
            _binding = FragmentUserDetailBinding.bind(it!!)
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favUpdate.observe(viewLifecycleOwner) {
            setFragmentResult(
                FRAGMENT_RESULT_KEY, bundleOf(
                    FRAG_RESULT_USER_ID to it.id
                )
            )
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        fun newInstance(userName: String) = FragmentUserDetail().withArgs {
            putString(USER_NAME_KEY, userName)
        }

        private const val USER_NAME_KEY = "UserNameKey"

        const val FRAGMENT_RESULT_KEY = "UserDetailResultKey"
        const val FRAG_RESULT_USER_ID = "UserDetailResultUserId"

    }

}