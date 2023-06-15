package com.example.noteapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.Entity.NoteEntity
import com.example.noteapp.R
import com.example.noteapp.databinding.NoteItemBinding
import java.util.ArrayList

class NoteAdapter (updatepin : (NoteEntity) -> Unit) : RecyclerView.Adapter<NoteAdapter.NotesHolder>(){

    var updatepin = updatepin
    lateinit var  note : ArrayList<NoteEntity>

    class NotesHolder(itemView: NoteItemBinding) : RecyclerView.ViewHolder(itemView.root){

        var binding = itemView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        var  binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesHolder(binding)
    }

    override fun getItemCount(): Int {
        return note.size
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {

        holder.binding.apply {
            note.get(position).apply {
                titlent.text =  title
                textnt.text = text
                if (pin) {
                    imgpin.setImageResource(R.drawable.pin)
                } else {
                    imgpin.setImageResource(R.drawable.unpin)
                }
                imgpin.setOnClickListener {
                    updatepin.invoke(note.get(position))
                }
            }
        }

    }

    fun update(note: ArrayList<NoteEntity>){
        this.note = note
    }
    fun setnote(note: ArrayList<NoteEntity>){
        this.note = note
    }
}