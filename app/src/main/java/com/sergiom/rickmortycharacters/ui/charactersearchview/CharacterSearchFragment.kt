package com.sergiom.rickmortycharacters.ui.charactersearchview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergiom.data.models.CharacterModel
import com.sergiom.rickmortycharacters.R
import com.sergiom.rickmortycharacters.databinding.FragmentCharacterSearchBinding
import com.sergiom.rickmortycharacters.ui.charactersearchview.viewadapter.CharacterAdapter
import com.sergiom.rickmortycharacters.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CharacterSearchFragment : Fragment(), CharacterAdapter.ItemClickListener {

    private var binding: FragmentCharacterSearchBinding by autoCleared()
    private val viewModel: CharacterSearchViewModel by viewModels()
    private lateinit var adapter: CharacterAdapter
    private val dbCharacters = "dbCharacters"
    private val characterDetail = "characterDetail"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCharacterSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setSearchView()
        setupRecyclerView()
        binding.goToCharactersDb.setOnClickListener {
            goToFragment(dbCharacters)
        }
    }

    private fun setListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    it.loading.let { isVisible ->
                        binding.rickImage.isVisible = isVisible
                        binding.goToCharactersDb.isVisible = !isVisible
                    }
                    it.error?.let { error ->
                        Toast.makeText(context, "ERROR: $error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setNextPages(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getNextPage(query).collectLatest {
                adapter.submitData(it)
                binding.charactersRecyclerView.scrollToPosition(0)
            }
            adapter.loadStateFlow.collectLatest { loadState ->
                when (val currentState = loadState.refresh) {
                    is LoadState.Error -> {
                        viewModel.setError(currentState.error.message.toString())
                    }
                    else -> {
                        //Do nothing
                    }
                }
            }
        }
    }

    private fun setSearchView() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        setNextPages(it)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.length?.let {
                        if (it > 0) {
                            setNextPages(newText)
                        }
                    }
                    return true
                }
            })
            queryHint = getString(R.string.action_search)
            setOnClickListener {
                this.onActionViewExpanded()
            }
            setOnQueryTextFocusChangeListener { _, hasFocus ->
                if(!hasFocus) {
                    if (this.query.toString().isEmpty()) {
                        this.onActionViewCollapsed()
                        viewModel.query = ""
                        setNextPages("") //Default characters at the end
                    }
                    this.clearFocus()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CharacterAdapter(this)
        binding.apply {
            charactersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            charactersRecyclerView.adapter = adapter
        }
        setNextPages(viewModel.query)
    }

    override fun onItemClicked(item: CharacterModel) {
        viewModel.saveCharacter(item)
        goToFragment(characterDetail, item.id)
    }

    private fun goToFragment(name: String, itemId: Int = 0) {
        val navController = findNavController()
        viewModel.query = binding.searchView.query.toString()
        if (name == characterDetail) {
            navController.navigate(CharacterSearchFragmentDirections.actionCharacterSearchFragmentToCharacterDetailFragment(characterId = itemId))
        } else {
            navController.navigate(CharacterSearchFragmentDirections.actionCharacterSearchFragmentToCharactersDBFragment())
        }
    }

    companion object {
        /**
         * @return A new instance of fragment CharacterSearchFragment.
         */
        @JvmStatic
        fun newInstance() = CharacterSearchFragment()
    }
}