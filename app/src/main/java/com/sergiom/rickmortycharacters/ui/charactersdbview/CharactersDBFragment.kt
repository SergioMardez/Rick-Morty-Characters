package com.sergiom.rickmortycharacters.ui.charactersdbview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.rickmortycharacters.R
import com.sergiom.rickmortycharacters.databinding.FragmentCharactersDBBinding
import com.sergiom.rickmortycharacters.ui.charactersdbview.viewdbadapter.CharacterDBAdapter
import com.sergiom.rickmortycharacters.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [CharactersDBFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CharactersDBFragment : Fragment(), CharacterDBAdapter.ItemClickListener {

    private var binding: FragmentCharactersDBBinding by autoCleared()
    private val viewModel: CharacterDBViewModel by viewModels()
    private lateinit var adapter: CharacterDBAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =  FragmentCharactersDBBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setupRecyclerView()
        binding.backButtonDB.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.deleteAllCharacters.setOnClickListener {
            setDialog()
        }
    }

    private fun setListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    it.characters.observe(viewLifecycleOwner) { characters ->
                        adapter.setItems(characters)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CharacterDBAdapter(this)
        binding.apply {
            charactersDBRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            charactersDBRecyclerView.adapter = adapter
        }
    }


    override fun onItemClicked(item: CharacterDataBaseModel) {
        findNavController().navigate(CharactersDBFragmentDirections.actionCharactersDBFragmentToCharacterDetailFragment(characterId = item.id))
    }

    private fun setDialog() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.dialog_ok) { _, _ ->
                    viewModel.deleteAllCharacters()
                }
                setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.setMessage(R.string.dialog_delete_message)
            builder.create()
        }
        alertDialog?.show()
    }

    companion object {
        /**
         * @return A new instance of fragment CharactersDBFragment.
         */
        @JvmStatic
        fun newInstance() = CharactersDBFragment()
    }

}