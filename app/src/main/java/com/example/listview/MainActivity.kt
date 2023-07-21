package com.example.listview

import android.app.Activity
import android.app.Dialog
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ExpandableListAdapter
import androidx.appcompat.app.AlertDialog
import com.example.listview.databinding.ActivityMainBinding
import java.text.FieldPosition


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var array= mutableListOf<String>( "Bailey","kinley","Bisleri","Aqua Fresh")
    lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1, array)
        binding.listView.adapter= adapter

        //will be reason of crash
        //  binding.listView.setOnClickListener {}


        binding.listView.setOnItemClickListener { parent, view, position, id->
            AlertDialog.Builder(this)
                .setTitle("Delete or Update")
                .setMessage("Do you  want to delete or update")
                .setPositiveButton("Delete",{_,_->
                    array.removeAt(position)
                    adapter.notifyDataSetChanged()
                })
                .setNegativeButton("Update",{_,_->
                    showUpdateDialog(position)
                })
                .show()
        }
        binding.listView.setOnItemLongClickListener { parent, view, position, id->
            array.removeAt(position)
            adapter.notifyDataSetChanged()
            return@setOnItemLongClickListener true
        }
        binding.fab.setOnClickListener{
            var dialog= Dialog(this)
            dialog.setContentView(R.layout.add_item_layout)
            dialog.show()
            var etName= dialog.findViewById<EditText>(R.id.etName)
            var btnAdd = dialog.findViewById<Button>(R.id.btnAdd)

            btnAdd.setOnClickListener {
                if(etName.text.toString().isNullOrEmpty()){
                    etName.error= "Enter item name"
                }else {
                    array.add(etName.text.toString())
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
        }

    }


    private fun showUpdateDialog(position: Int) {
        var dialog= Dialog(this)
        dialog.setContentView((R.layout.add_item_layout))
        dialog .show()
        var etName=dialog.findViewById<EditText>(R.id.etName)
        var btnAdd = dialog.findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener {
            if (etName.text.toString().isNullOrEmpty()){
                etName.error="Enter your name "
            }else{
                array.add(etName.text.toString())
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }

    }

}
