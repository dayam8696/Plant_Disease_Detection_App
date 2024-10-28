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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ai_plant_detection_project.databinding.DetectFragmentBinding
import isomora.com.greendoctor.Classifier
import java.io.IOException

class DetectFragment : Fragment() {

    private var _binding: DetectFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mClassifier: Classifier
    private lateinit var mBitmap: Bitmap

    private val mCameraRequestCode = 0
    private val mGalleryRequestCode = 2
    private val mInputSize = 224
    private val mModelPath = "plant_disease_model.tflite"
    private val mLabelPath = "plant_labels.txt"
    private val mSamplePath = "soybean.JPG"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = DetectFragmentBinding.inflate(inflater, container, false)

        // Initialize the Classifier
        mClassifier = Classifier(requireContext().assets, mModelPath, mLabelPath, mInputSize)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()


        // Load and display the sample image on initialization
        loadSampleImage()

        // Set up click listeners for buttons
        setupListeners()

        return binding.root
    }

    private fun loadSampleImage() {
        try {
            // Load the sample image from assets
            requireContext().assets.open(mSamplePath).use { inputStream ->
                mBitmap = BitmapFactory.decodeStream(inputStream)
                mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)

                // Display the sample image
                binding.leafImageView.setImageBitmap(mBitmap)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error loading sample image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupListeners() {
        binding.apply {
            // Camera Button Listener
            icCamera.setOnClickListener {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, mCameraRequestCode)
            }

            // Gallery Button Listener
            icGallery.setOnClickListener {
                val galleryIntent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                startActivityForResult(galleryIntent, mGalleryRequestCode)
            }

            // Predict Disease Button Listener
            analyzeButton.setOnClickListener {
                if (::mBitmap.isInitialized) {
                    val result = mClassifier.recognizeImage(mBitmap).firstOrNull()
                    if (result != null) {
                        mResultTextView.text =
                            "${result.title}\nConfidence: ${"%.2f".format(result.confidence * 100)}%"
                    } else {
                        mResultTextView.text = "No recognizable disease detected."
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please select or capture an image first.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                mCameraRequestCode -> {
                    mBitmap = data.extras?.get("data") as Bitmap
                    mBitmap = scaleImage(mBitmap)
                    binding.leafImageView.setImageBitmap(mBitmap)
                }
                mGalleryRequestCode -> {
                    val uri = data.data
                    uri?.let {
                        try {
                            mBitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver, it
                            )
                            mBitmap = scaleImage(mBitmap)
                            binding.leafImageView.setImageBitmap(mBitmap)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Action canceled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scaleImage(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = mInputSize.toFloat() / width
        val scaleHeight = mInputSize.toFloat() / height
        val matrix = Matrix().apply { postScale(scaleWidth, scaleHeight) }
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
