package br.testesoftdesign.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import br.testesoftdesign.R
import br.testesoftdesign.databinding.FragmentDetailCharacterBinding
import br.testesoftdesign.models.Items
import br.testesoftdesign.models.v2.Base
import br.testesoftdesign.models.v2.Results
import com.squareup.picasso.Picasso


class DetailCharacterFragment : Fragment() {

    private lateinit var binding: FragmentDetailCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_character, container, false)
        setHasOptionsMenu(true)

        configureInfosDetail()

        return binding.root
    }


    @SuppressLint("SetTextI18n")
    private fun configureInfosDetail() {
        val base = arguments?.getParcelable<Base>("base") as Base

        if (base.data == null) {
            context?.let {
                Toast.makeText(it, "Dados inválidos", Toast.LENGTH_LONG).show()
                Navigation.findNavController(binding.root).popBackStack()
            }
        }

        for (r: Results in base.data?.results!!) {

            val url = "${r.thumbnail.path}/detail.${r.thumbnail.extension}"
            configToolbar(r.name)

            Picasso.get()
                .load(url)
                .placeholder(R.mipmap.ic_marvel_logo)
                .error(R.mipmap.ic_marvel_logo)
                .fit()
                .into(binding.ivPhoto)

            binding.tvDescription.text =
                if (r.description.isNullOrBlank()) "Descrição não encontrada" else r.description


            val arrayAdapter: ArrayAdapter<Items>? =
                context?.let {
                    ArrayAdapter<Items>(
                        it,
                        android.R.layout.simple_list_item_1,
                        r.comics.items
                    )
                }

            binding.lvComics.adapter = arrayAdapter

        }
    }

    private fun configToolbar(title: String) {
        (activity as HostActivity).configureToolbar(
            showToolbar = true,
            showBackArrow = true,
            title = title
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Navigation.findNavController(binding.root).popBackStack()
        return super.onOptionsItemSelected(item)
    }
}