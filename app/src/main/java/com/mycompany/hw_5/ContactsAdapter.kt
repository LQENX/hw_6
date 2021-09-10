package com.mycompany.hw_5

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mycompany.hw_5.databinding.ContactItemBinding

class ContactsAdapter (
    private val contacts: List<ContactItem>,
    private val listener: RecyclerListener
    )
    : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private lateinit var binding: ContactItemBinding


    inner class ViewHolder(private val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var contact: ContactItem

        fun bind(contact: ContactItem) {
            this.contact = contact
            binding.contactName.text = contact.name
            binding.contactSurname.text = contact.surname
            binding.contactPhone.text = contact.phoneNumber
            binding.root.setOnClickListener { listener.onItemClick(binding.root, adapterPosition) }
            bindViewToGlide(binding.contactThumbnail)
        }

        private fun bindViewToGlide(imageView: ImageView) {
            Glide
                .with(binding.root)
                .load(contact.image)
                .placeholder(R.drawable.ic_downloading)
                .error(R.drawable.ic_error_image)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ContactItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size
}