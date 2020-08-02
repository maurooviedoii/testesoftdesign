package br.testesoftdesign.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import br.testesoftdesign.R
import br.testesoftdesign.databinding.FragmentSearchBinding
import br.testesoftdesign.repository.RepositoryImpl
import br.testesoftdesign.utils.JsonUtils
import br.testesoftdesign.utils.NetworkUtils
import br.testesoftdesign.utils.RetrofitUtils
import br.testesoftdesign.viewmodels.Factory
import br.testesoftdesign.viewmodels.MainViewModel


class SearchFragment : Fragment() {

    private lateinit var vm: MainViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instanceVM()
    }

    private fun instanceVM() {
        vm = ViewModelProvider(
            this,
            Factory(
                RepositoryImpl(
                    RetrofitUtils.createEndpoint()
                )
            )
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.context = this
        hideButtons(false)

        setAdapterEt()
        setObservables()

        return binding.root
    }

    private fun setObservables() {
        vm.getResultSearchByName().observe(viewLifecycleOwner) {
            it?.let {
                if (it.code > 0) {
                    val bundle = Bundle()
                    bundle.putParcelable("base", it)

                    binding.root.findNavController()
                        .navigate(R.id.action_searchFragment_to_detailCharacterFragment, bundle)
                } else {
                    hideButtons(false)
                    context?.let { itContext -> showToast(itContext, "Herói não encontrado") }
                }
            }
        }

    }

    private fun setAdapterEt() {
        val adapter: ArrayAdapter<String>? = context?.let {
            context?.let { JsonUtils.getListCharacters(it) }?.let { listNames ->
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    listNames
                )
            }
        }
        binding.etSearchCharacter.setAdapter(adapter)
    }

    fun searchByName() {
        hideButtons(true)

        context?.let {
            if (NetworkUtils.hasNetwork(it)) {
                val name: String = binding.etSearchCharacter.text.toString()
                if (name.isNotEmpty()) {
                    vm.searchByName(name)
                } else {
                    hideButtons(false)
                    showToast(it, "Herói não encontrado")
                }

            } else {
                hideButtons(false)
                showToast(it, "Conecte-se a internet")
            }
        }
    }

    fun searchAll() {
        hideButtons(true)

        Navigation.findNavController(binding.root)
            .navigate(R.id.action_searchFragment_to_listCharactersFragment)
    }

    private fun showToast(it: Context, text: String) {
        Toast.makeText(it, text, Toast.LENGTH_LONG).show()
    }

    private fun hideButtons(hide: Boolean) {
        binding.hideButtons = hide
    }

    override fun onPause() {
        vm.clearMutables()
        super.onPause()
    }

    override fun onResume() {
        hideButtons(false)
        (activity as HostActivity).supportActionBar?.hide()
        super.onResume()
    }


}