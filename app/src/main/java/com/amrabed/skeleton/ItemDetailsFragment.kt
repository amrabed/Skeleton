package com.amrabed.skeleton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_details_fragment.*

/**
 * Fragment to show the details of the selected item from the item list
 */
class ItemDetailsFragment : Fragment() {
    private val viewModel: ItemViewModel by lazy {
        ViewModelProvider(activity as ViewModelStoreOwner).get(ItemViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_details_fragment, parent, false)
        viewModel.getSelected().observe(viewLifecycleOwner, Observer { item ->
            title.text = item.title
            summary.text = item.summary
            Glide.with(activity!!).load(item.image).centerCrop().placeholder(R.drawable.placeholder)
                .into(image)

            edit.setOnClickListener {
                parentFragmentManager.beginTransaction().replace(R.id.fragment, ItemEditFragment())
                    .addToBackStack(null).commit()
            }
        })
        return view
    }
}
