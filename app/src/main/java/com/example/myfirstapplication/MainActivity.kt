package com.example.myfirstapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.database.DatabaseSql
import com.example.myfirstapplication.database.MentorDatabase
import com.example.myfirstapplication.databinding.ActivityMainBinding
import com.example.myfirstapplication.model.AddMentor
import com.example.myfirstapplication.model.Mentor
import com.example.myfirstapplication.model.State
import com.example.myfirstapplication.screens.add.SwipeList


class MainActivity : AppCompatActivity(), AddMentor, SwipeDelete {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private var adapter:DataAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val application = requireNotNull(this).application

        viewModel =
         ViewModelProvider(
              this, MainViewModelFactory(DatabaseSql.getInstance(application), application)).get(MainViewModel::class.java)

        binding.floatingActionButton.setOnClickListener{
            runOpenFragment(viewModel.fragmentAddDatabase, viewModel.addFragment)
            viewModel.addFragment.active = true
            workButton(false)
        }

        binding.imageButton.setOnClickListener {
           runOpenFragment(viewModel.fragmentFilterFragment, viewModel.filterFragment)
           viewModel.filterFragment.active = true
           workButton(false)
        }

        adapter =  DataAdapter(this)
        binding.list.adapter = adapter

        val swipeToDeleteCallBack = object : SwipeList() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                val position = viewHolder.adapterPosition
                deleteItem(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(binding.list)

        if (viewModel.addFragment.active){
            workButton(false)
        }
        else if (viewModel.filterFragment.active){
            workButton(false)
        }

        viewModel.mentors?.observe(this, Observer {
            adapter?.deleteItems()
            viewModel.mentors?.value?.let {
                adapter?.addItemsAll(it.toList())
            }

        })

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun runOpenFragment(fragment: Fragment, state: State) {

        val fragmentTr: FragmentTransaction = supportFragmentManager.beginTransaction()

        if (!state.checkState()) {
            fragmentTr.add(R.id.container, fragment, state.name)
            state.updateState()
        }
        if (state.checkState()) {
            fragmentTr.replace(R.id.container, fragment, state.name)
        }

        fragmentTr.commit()
    }

    override fun onBackPressed() {
        backWork()
    }

    private fun workButton(state:Boolean){
        binding.floatingActionButton.isVisible = state
        binding.floatingActionButton.isClickable = state
        binding.imageButton.isVisible = state
        binding.imageButton.isClickable = state
        binding.list.isVisible = state
        binding.list.isClickable = state
    }

    private fun backWork(){

        var work = true
        val fragmentTr: FragmentTransaction = supportFragmentManager.beginTransaction()

        when {

            viewModel.addFragment.active -> {
                fragmentTr.remove(supportFragmentManager.fragments[0])
                viewModel.addFragment.active = false
            }
            viewModel.filterFragment.active -> {
                fragmentTr.remove(supportFragmentManager.fragments[0])
                viewModel.filterFragment.active = false
                viewModel.updateSortType()
            }
            else -> {
                work = false
            }

        }

        if (work){
            fragmentTr.addToBackStack(null)
            fragmentTr.commit()
        }


        workButton(true)

    }

    override fun addMentor(mentor: Mentor) {
        backWork()
        viewModel.insert(mentor)
        val temp = viewModel.convertToDatabase(mentor)
        temp.Id = viewModel.indexses + 1
        // adapter?.addItems(temp)
        viewModel.updateSortType()
        adapter?.notifyDataSetChanged()
    }

    override fun updateMentor(mentor: Mentor) {
        backWork()
        viewModel.updateMentor(mentor)
        adapter?.notifyDataSetChanged()
    }

    override fun getUnique(mentor: Mentor):MentorDatabase? {
        return viewModel.getUnique(mentor)
    }

    override fun deleteItem(position:Int) {
         val temp = adapter?.getItem(position)
         temp?.let { viewModel.clearMentor(it) }
         adapter?.deleteItem(position)
    }


}
