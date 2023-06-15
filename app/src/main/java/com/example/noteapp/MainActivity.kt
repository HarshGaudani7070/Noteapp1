package com.example.noteapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.noteapp.Adapter.NoteAdapter
import com.example.noteapp.Database.RoomDB
import com.example.noteapp.Entity.NoteEntity
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.databinding.AddDialogBinding
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

class MainActivity : AppCompatActivity() {

    lateinit var db: RoomDB
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RoomDB.init(this)


        initView()

    }

    private fun initView() {

        binding.add.setOnClickListener {
            addNoteDialog()
        }

        adapter = NoteAdapter {

            var isPin = false
            if (it.pin) {
                isPin = false
            } else {
                isPin = true
            }
            var data = NoteEntity(it.title, it.text, it.date, isPin)
            data.id = it.id
            db.note().updateNote(data)
            adapter.update(filterNote(db.note().getNote()))
        }
        adapter.setnote(filterNote(db.note().getNote()))
        binding.note.layoutManager = GridLayoutManager(this, 2)
        binding.note.adapter = adapter

    }

    fun filterNote(list: List<NoteEntity>): ArrayList<NoteEntity> {
        var newList = ArrayList<NoteEntity>()
        for (l: NoteEntity in list) {
            if (!l.pin) {
                newList.add(l)
            }
        }

        for (l: NoteEntity in list) {
            if (l.pin) {
                newList

                    .add(l)
            }
        }

        return newList
    }

    private fun addNoteDialog() {
        var dialog = Dialog(this)
        var bind = AddDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.submit.setOnClickListener {

            var title = bind.title.text.toString()
            var text = bind.text.text.toString()
            var format = SimpleDateFormat("DD/MM/YYYY hh:mm:ss a")
            var current = format.format(Date())
            var date = NoteEntity(title, text, current, pin = true)
            db.note().addNote(date)
            adapter.update(filterNote(db.note().getNote()))
            dialog.dismiss()
        }

        dialog.show()
    }

}
