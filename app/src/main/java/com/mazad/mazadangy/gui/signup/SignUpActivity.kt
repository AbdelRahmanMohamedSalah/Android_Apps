
package com.mazad.mazadangy.gui.signup

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mazad.mazadangy.R
import com.mazad.mazadangy.utels.ToastUtel
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.IOException
import java.util.*

private val IMAGE_PICK_CODE = 1000;
//Permission code
private val PERMISSION_CODE = 1001;

class SignUpActivity : AppCompatActivity(), SignUpInterface {
    lateinit var signUpPresenter: SignUpPresenter
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseRefrance: DatabaseReference
    lateinit var uri_load: String
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    // val storage = FirebaseStorage.getInstance()

    companion object {
        var antekaFav: Boolean = false
        var otherFav: Boolean = false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseDatabase = FirebaseDatabase.getInstance()
        signUpPresenter = SignUpPresenter(this)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        progressbarSignUpActivity.visibility = View.GONE

        signUpBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                signUpData()
                progressbarSignUpActivity.visibility = View.VISIBLE

            }
        })


        imageProfileSignUp.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery();
                }
            } else {
                //system OS is < Marshmallow

                pickImageFromGallery();
            }
        }
    }


    private fun signUpData() {
        var firstName = firstName.text.toString().trim()
        var lastName = lNameEt.text.toString().trim()
        var neckName = nickName.text.toString().trim()
        var email = emailTv.text.toString().trim()
        var password = passwordTv.text.toString().trim()
        var conPass = confirmPassTv.text.toString().trim()
        var phone = phoneTv.text.toString().trim()
        var interistTv = interistTv.text.toString().trim()
        if (antekatChB.isChecked) {
            antekaFav = true

        }
        if (othertChB.isChecked) {
            otherFav = true
        }
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
                neckName
            )
            && TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conPass) || TextUtils.isEmpty(
                phone
            )
            || TextUtils.isEmpty(interistTv)
        ) {
            ToastUtel.errorToast(this, "من فضلك املأ الفراغات")
        } else if (!password.equals(conPass)) {
            ToastUtel.errorToast(this, "كلمة السر غير متطابفة!")

        } else {
            signUpPresenter.checkSignUp(email, password, this)
        }


    }

    override fun errorSignUp() {
        ToastUtel.errorToast(this, "من فضلك ادخل البيانات بشكل صحيح ")
    }

    override fun sucessSignUp(uId: String) {
        databaseRefrance = firebaseDatabase.getReference("users").child(uId)
        databaseRefrance.child("firstName").setValue(firstName.text.toString().trim())
        databaseRefrance.child("lastName").setValue(lNameEt.text.toString().trim())
        databaseRefrance.child("nickname").setValue(nickName.text.toString().trim())
        databaseRefrance.child("email").setValue(emailTv.text.toString().trim())
        databaseRefrance.child("phoneNumber").setValue(phoneTv.text.toString().trim())
        databaseRefrance.child("uId").setValue(uId)
        databaseRefrance.child("image_profile").setValue(uri_load)
        databaseRefrance.child("interest").setValue(interistTv.text.toString().trim())
//        databaseRefrance.child("categories").child("anteka").setValue(antekaFav)
//        databaseRefrance.child("categories").child("other").setValue(otherFav)

        finish()
    }

    override fun noConnection() {
        ToastUtel.errorToast(this, "من فضلك تاكد من وجود انترنت ")

    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

//    companion object {
//        //image pick code
//        private val IMAGE_PICK_CODE = 1000;
//        //Permission code
//        private val PERMISSION_CODE = 1001;
//    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
        pickImageFromGallery()

    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageProfileSignUp.setImageURI(data?.data)
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageProfileSignUp?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }


            uploadImage()
        }
    }


    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            ref?.putFile(filePath!!)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.getResult()

                    //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(mAu.getInstance().getCurrentUser().getUid());
                    val url = downloadUri!!.toString()

                    uri_load =
                        filePath.toString()
                    println("uri load= " + uri_load + "");
                    println("download uri = " + downloadUri + "");
//                    databaseRefrance.child("image_profile").setValue(downloadUri.toString())

                    // finish()
                }
            }
        }
        //  key=uri_load.toString();
//                        databaseRefrance.child("image_profile").setValue(uri_load.toString())


        //   Upload upload = new Upload(editTextName.getText().toString().trim(),

        // Toast.makeText(getContext(),url,Toast.LENGTH_LONG).show();
        //  String uploadId = ref.push().getKey();
        //ref.child(uploadId).setValue(upload);


//            ?.addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {
//                    fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {
//                        val uri_load =
//                            taskSnapshot.storage.downloadUrl.result
//                        println("uri = "+uri_load);
        //  key=uri_load.toString();
//                        databaseRefrance.child("image_profile").setValue(uri_load.toString())
//                        ToastUtel.showSuccessToast(this, "تم تسجيل حساب جديد بنجاح")
//                        if (uri_load.toString().length > 7) {
//
//                            finish()

    }


//        if (imageProfileSignUp == null) return
//
//        val filename = "images/" + UUID.randomUUID().toString()
//        val ref = storage.getReference(filename).child("profile_image")
//
//
//        // imageProfileSignUp.setImageBitmap(imageBitmap)
//
//        val uri = Uri.parse(imageProfileSignUp.toString())
//        System.out.print("Uri " + uri);
//        println("Image = " + imageProfileSignUp.toString())
//
//        ref.putFile(uri!!)
//            .addOnSuccessListener {
//                //                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")
//
//                ref.downloadUrl.addOnSuccessListener {
//                    //                    Log.d(TAG, "File Location: $it")
//                    databaseRefrance.child("ProfileImage")
//                        .setValue(ref.downloadUrl.toString().trim())
//
//                }
//            }
//            .addOnFailureListener {
//                //                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
//            }
}


