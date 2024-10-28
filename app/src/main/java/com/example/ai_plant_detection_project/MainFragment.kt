package com.example.ai_plant_detection_project

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ai_plant_detection_project.databinding.MainFragmentBinding
import isomora.com.greendoctor.Classifier
import java.io.IOException

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
//
//    private lateinit var mClassifier: Classifier
//    private lateinit var mBitmap: Bitmap
//
//    private val mCameraRequestCode = 0
//    private val mGalleryRequestCode = 2
//
//    private val mInputSize = 224
//    private val mModelPath = "plant_disease_model.tflite"
//    private val mLabelPath = "plant_labels.txt"
//    private val mSamplePath = "soybean.JPG"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = MainFragmentBinding.inflate(inflater, container, false)
//
//        // Initialize the Classifier
//        mClassifier = Classifier(requireContext().assets, mModelPath, mLabelPath, mInputSize)
//
//        resources.assets.open(mSamplePath).use {
//            mBitmap = BitmapFactory.decodeStream(it)
//            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
//            binding.mPhotoImageView.setImageBitmap(mBitmap)
//        }
//
//        // Setup listeners (including navigation button)
        setupListeners()

        // Return the root view
        return binding.root
    }

    private fun setupListeners() {
//        // Camera button listener
//        binding.mCameraButton.setOnClickListener {
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(cameraIntent, mCameraRequestCode)
//        }
        binding.checkButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_detectFragment)
        }
//
//        // Gallery button listener
//        binding.mGalleryButton.setOnClickListener {
//            val galleryIntent = Intent(Intent.ACTION_PICK).apply {
//                type = "image/*"
//            }
//            startActivityForResult(galleryIntent, mGalleryRequestCode)
//        }
//
//        // Detect Disease button listener
//        binding.mDetectButton.setOnClickListener {
//            val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
//            binding.mResultTextView.text =
//                "${results?.title}\nConfidence: ${results?.confidence}"
       }

        // Navigation button listener (corrected placement)



//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == Activity.RESULT_OK && data != null) {
//            when (requestCode) {
//                mCameraRequestCode -> {
//                    mBitmap = data.extras!!.get("data") as Bitmap
//                    mBitmap = scaleImage(mBitmap)
//                    binding.mPhotoImageView.setImageBitmap(mBitmap)
//                }
//                mGalleryRequestCode -> {
//                    val uri = data.data
//                    uri?.let {
//                        mBitmap = MediaStore.Images.Media.getBitmap(
//                            requireActivity().contentResolver, it
//                        )
//                        mBitmap = scaleImage(mBitmap)
//                        binding.mPhotoImageView.setImageBitmap(mBitmap)
//                    }
//                }
//            }
//        }
//    }

//    private fun scaleImage(bitmap: Bitmap): Bitmap {
//        val width = bitmap.width
//        val height = bitmap.height
//        val scaleWidth = mInputSize.toFloat() / width
//        val scaleHeight = mInputSize.toFloat() / height
//        val matrix = Matrix().apply { postScale(scaleWidth, scaleHeight) }
//        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null // Avoid memory leaks
//    }
}
