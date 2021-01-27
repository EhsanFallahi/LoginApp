package com.ehsanfallahi.loginapp.ui.user.userList

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.data.dataModel.Data
import com.ehsanfallahi.loginapp.databinding.UserListFragmentBinding
import com.ehsanfallahi.loginapp.ui.MainActivity
import com.ehsanfallahi.loginapp.ui.user.userDetail.UserDetailFragmentArgs
import com.ehsanfallahi.loginapp.util.Constant.Companion.IMAGE_PICK_CODE
import com.ehsanfallahi.loginapp.util.Constant.Companion.PERMISSION_CODE
import com.ehsanfallahi.loginapp.util.RecyclerViewAdapter
import com.ehsanfallahi.loginapp.util.ScopedFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class UserListFragment : ScopedFragment() {


    private lateinit var viewModel: UserListViewModel
    private lateinit var binding:UserListFragmentBinding
    private val args:UserListFragmentArgs by navArgs()
    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        email=args.email
        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        binding= DataBindingUtil.inflate(inflater,R.layout.user_list_fragment,container,false)

        setUpNewsRecyclerView()

        binding.btnAdd.setOnClickListener {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)==
                        PackageManager.PERMISSION_DENIED){
                    val permissions= arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions,PERMISSION_CODE)
                }else{
                        pickImage()
                }
            }else{

            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.clearPreferences()
            findNavController().navigate(R.id.action_userListFragment_to_loginFragment)
        }

        binding.txtEmail.text=email

        return binding.root
    }


    private fun pickImage() {
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,IMAGE_PICK_CODE)
    }

    private fun setUpNewsRecyclerView() =launch {
        binding.rvListUser.apply {
            layoutManager= LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
            val users= viewModel.getAllNews.await()
            users.observe(viewLifecycleOwner, Observer {
                binding.progress.visibility= View.GONE
                if(it==null ) return@Observer
                binding.rvListUser.adapter= RecyclerViewAdapter(it.data)

            })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE->{
                if (grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pickImage()
                }else{
                    Toast.makeText(requireContext(),"مجوز صادر نشد!",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==Activity.RESULT_OK && requestCode== IMAGE_PICK_CODE){
            binding.imgUser.setImageURI(data?.data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().finish()
    }
}