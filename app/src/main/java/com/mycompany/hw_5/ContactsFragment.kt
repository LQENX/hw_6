package com.mycompany.hw_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import com.mycompany.hw_5.databinding.ContactsFtagmentBinding


class ContactsFragment : Fragment(), RecyclerListener {
    private lateinit var binding: ContactsFtagmentBinding
    private val contacts: MutableList<ContactItem> =
        mutableListOf(
                ContactItem("Tony", "Ray", "891726"),
                ContactItem("Angelo", "Martelli", "448526"),
                ContactItem("John", "Wick", "000000"),
        )

    companion object {
        const val SHARED_CONTACT = "com.mycompany.hw_5.shared_contact"
        const val CONTACT_POSITION = "com.mycompany.hw_5.contact_position"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ContactsFtagmentBinding.inflate(layoutInflater, container, false)
        resultObserver()
        setupRecycler()

        return binding.root
    }

    private fun resultObserver() {
        requireActivity()
                .supportFragmentManager
                .setFragmentResultListener(
                        ContactEditFragment.REQUEST_KEY,
                        this,
                        { _, result ->
                            val editContact = result.getParcelable<ContactItem>(SHARED_CONTACT)
                            val contactPosition = result.getInt(CONTACT_POSITION)
                            editContact?.let { contacts[contactPosition] = editContact }
                        })
    }

    override fun onItemClick(view: View, position: Int) {
        val args = setupArgs(position)
        val targetFragment = ContactEditFragment()
        navigateToFragment(targetFragment, args)
    }

    private fun navigateToFragment(targetFragment: Fragment, args: Bundle) {
        targetFragment.apply { arguments = args }
        (requireActivity() as MainActivity).navigateTo(targetFragment)
    }

    private fun setupArgs(position: Int) = Bundle().apply {
        putParcelable(SHARED_CONTACT, contacts[position])
        putInt(CONTACT_POSITION, position)
    }

    private fun setupRecycler() {
        binding.recyclerContacts.adapter = ContactsAdapter(contacts = contacts, listener = this)
    }

}