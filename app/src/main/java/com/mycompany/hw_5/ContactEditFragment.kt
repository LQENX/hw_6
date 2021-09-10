package com.mycompany.hw_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mycompany.hw_5.databinding.ContactsEditFragmentBinding


class ContactEditFragment : Fragment() {
    private lateinit var binding: ContactsEditFragmentBinding

    private var sharedContact: ContactItem? = null


    companion object {
        const val REQUEST_KEY = "com.mycompany.hw_5.request_key"
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = ContactsEditFragmentBinding.inflate(layoutInflater, container, false)
        setupSaveButtonClick()
        setupEditTexts()
        return binding.root
    }

    private fun setupSaveButtonClick() {
        binding.contactEditButtonSave.setOnClickListener { onSaveButtonClick() }
    }

    private fun setupEditTexts() {
        arguments?.let { args ->
            sharedContact = args.getParcelable(ContactsFragment.SHARED_CONTACT)
            sharedContact?.let { contact ->
                binding.contactEditName.setText(contact.name)
                binding.contactEditSurname.setText(contact.surname)
                binding.contactEditPhoneNumber.setText(contact.phoneNumber)
                Glide
                    .with(this)
                    .load(contact.image)
                    .placeholder(R.drawable.ic_downloading)
                    .error(R.drawable.ic_error_image)
                    .into(binding.contactImage)
            }
        }
    }

    private fun onSaveButtonClick() {
        val typedName = insertNameFromEdt()
        val typedSurname = insertSurnameFromEdt()
        val typedPhoneNumber = insertPhoneNumberFromEdt()

        if (typedName == "" || typedSurname == "" || typedPhoneNumber == "") {
            Toast.makeText(
                    requireContext(),
                    "Fill in the blank fields",
                    Toast.LENGTH_SHORT
            ).show()
        } else {
            putArgsForResult(typedName, typedSurname, typedPhoneNumber)
            val targetFragment = ContactsFragment()
            navigateToFragment(targetFragment)
        }
    }

    private fun navigateToFragment(targetFragment: ContactsFragment) {
        (requireActivity() as MainActivity).navigateTo(targetFragment)
    }

    private fun putArgsForResult(typedName: String, typedSurname: String, typedPhoneNumber: String) {
        val sameImage = sharedContact?.image?: ""
        val args = Bundle().apply {
            putParcelable(
                    ContactsFragment.SHARED_CONTACT,
                    ContactItem(typedName, typedSurname, typedPhoneNumber, sameImage)
            )
            putInt(
                    ContactsFragment.CONTACT_POSITION,
                    arguments?.getInt(ContactsFragment.CONTACT_POSITION)?: 0
            )
        }
        requireActivity()
                .supportFragmentManager
                .setFragmentResult(REQUEST_KEY, args)
    }

    private fun insertNameFromEdt(): String = binding.contactEditName.text.toString()

    private fun insertSurnameFromEdt(): String = binding.contactEditSurname.text.toString()

    private fun insertPhoneNumberFromEdt(): String = binding.contactEditPhoneNumber.text.toString()
}