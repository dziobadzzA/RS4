package com.example.myfirstapplication.screens.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapplication.databinding.ActivityAddDatabaseBinding
import com.example.myfirstapplication.databinding.ActivityAddDatabaseBinding.inflate
import com.example.myfirstapplication.model.AddMentor
import com.example.myfirstapplication.model.Mentor


class AddDatabaseFragment : Fragment() {

    private lateinit var viewModel: AddDatabaseFragmentViewModel
    private var _binding: ActivityAddDatabaseBinding? = null
    private val binding get() = _binding!!

    private lateinit var addMentor: AddMentor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, AddDatabaseViewModelFactory()).get(AddDatabaseFragmentViewModel::class.java)
        binding.button.setOnClickListener{
            addItem()
        }

        return binding.root
    }


    private fun addItem() {

        val  mentor = Mentor()
        mentor.name = binding.name.text.toString()
        mentor.firstname =  binding.firstname.text.toString()
        mentor.number = "+" + binding.number.text.toString()
        addMentor.addMentor(mentor)

        binding.name.setText("")
        binding.firstname.setText("")
        binding.number.setText("")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddMentor)
            addMentor =  context
        else
            Toast.makeText(context, "Repeat please, addMentor fragment not attach", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}