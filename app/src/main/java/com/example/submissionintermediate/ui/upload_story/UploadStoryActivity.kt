package com.example.submissionintermediate.ui.upload_story

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.submissionintermediate.R
import com.example.submissionintermediate.data.mechanism.Resource
import com.example.submissionintermediate.data.remote.response.FileUploadResponse
import com.example.submissionintermediate.databinding.ActivityUploadStoryBinding
import com.example.submissionintermediate.ui.base.BaseActivity
import com.example.submissionintermediate.ui.main.MainActivity
import com.example.submissionintermediate.utils.Utils.afterTextChanged
import com.example.submissionintermediate.utils.reduceFileImage
import com.example.submissionintermediate.utils.uriToFile
import java.io.File
import org.koin.androidx.viewmodel.ext.android.viewModel

class UploadStoryActivity : BaseActivity() {

    private lateinit var binding: ActivityUploadStoryBinding
    private val viewModel: UploadStoryViewModel by viewModel()

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.permission_message),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_activity_upload_story)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.etDescription.afterTextChanged {
            viewModel.dataChanged(null, binding.etDescription.text.toString())
        }

        binding.btnCamera.setOnClickListener {
            startCameraX()
        }

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        viewModel.formState.observe(this) {
            binding.buttonAdd.setEnable(it)
        }

        binding.buttonAdd.setText(resources.getString(R.string.upload))
        binding.buttonAdd.setOnClickListener {
            viewModel.addNewStory(binding.etDescription.text.toString())
                ?.observe(this, uploadStoryObserver)
        }
    }

    private val uploadStoryObserver = Observer<Resource<FileUploadResponse>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.buttonAdd.setLoading(true)
            }
            is Resource.Success -> {
                binding.buttonAdd.setLoading(false)
                val intent = Intent()
                intent.putExtra("message", res.data?.message ?: "Success")
                setResult(MainActivity.UPLOAD_RESULT, intent)
                finish()
            }
            is Resource.Error -> {
                binding.buttonAdd.setLoading(false)
                Toast.makeText(this, res.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            viewModel.dataChanged(reduceFileImage(myFile), binding.etDescription.text.toString())
            binding.preview.setImageBitmap(BitmapFactory.decodeFile(myFile.path))
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@UploadStoryActivity)
            viewModel.dataChanged(myFile, binding.etDescription.text.toString())
            binding.preview.setImageURI(selectedImg)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}