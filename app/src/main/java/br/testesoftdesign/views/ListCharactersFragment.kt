package br.testesoftdesign.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
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
import br.testesoftdesign.databinding.FragmentListCharactersBinding
import br.testesoftdesign.repository.RepositoryImpl
import br.testesoftdesign.utils.JsonUtils
import br.testesoftdesign.utils.NetworkUtils
import br.testesoftdesign.utils.RetrofitUtils
import br.testesoftdesign.viewmodels.Factory
import br.testesoftdesign.viewmodels.MainViewModel

class ListCharactersFragment : Fragment() {

    lateinit var binding: FragmentListCharactersBinding
    lateinit var characters: List<String>
    lateinit var vm: MainViewModel

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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_characters, container, false)
        hideList(true)

        setHasOptionsMenu(true)
        configToolbar()
        configListView()
        setObservables()

        return binding.root
    }

    private fun configListView() {
        characters = context?.let { JsonUtils.getListCharacters(it) }!!

        val arrayAdapter: ArrayAdapter<String>? =
            context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_list_item_1,
                    characters
                )
            }

        binding.lvCharacters.adapter = arrayAdapter

        binding.lvCharacters.setOnItemClickListener { _, _, i, _ ->
            hideList(true)
            context?.let {
                if (NetworkUtils.hasNetwork(it)) {
                    val name: String = characters[i]
                    if (name.isNotEmpty()) {
                        vm.searchByName(name = name)
                    } else {
                        hideList(false)
                        showToast(it, "Herói não encontrado")
                    }
                } else {
                    hideList(false)
                    showToast(it, "Conecte-se a internet")
                }
            }
        }

        hideList(false)
    }

    private fun configToolbar() {
        (activity as HostActivity).configureToolbar(
            showToolbar = true,
            showBackArrow = true,
            title = "Personagens"
        )
    }

    private fun setObservables() {
        vm.getResultSearchByName().observe(viewLifecycleOwner) {
            it?.let {
                if (it.code > 0) {
                    val bundle = Bundle()
                    bundle.putParcelable("base", it)

                    binding.root.findNavController()
                        .navigate(
                            R.id.action_listCharactersFragment_to_detailCharacterFragment,
                            bundle
                        )
                } else {
                    context?.let { itContext -> showToast(itContext, "Herói não encontrado") }
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Navigation.findNavController(binding.root).popBackStack()
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(it: Context, text: String) {
        Toast.makeText(it, text, Toast.LENGTH_LONG).show()
    }

    private fun hideList(hide: Boolean) {
        binding.hideList = hide
    }

    override fun onPause() {
        vm.clearMutables()
        super.onPause()
    }
}