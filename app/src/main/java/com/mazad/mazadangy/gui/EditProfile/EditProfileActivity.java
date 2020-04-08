package com.mazad.mazadangy.gui.EditProfile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mazad.mazadangy.R;
import com.mazad.mazadangy.model.UserModel;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {
    LinearLayout layout_changePassword, layout_information;
    TextView text_changePass;
    private int count = 0;
    Dialog dialogPassword;
    Button change;
    EditText etFirstName, etLastName, etNickName, etEmail, etPhoneNumber, etInterest, Etoldpassword, Etnewpassword, Etconfirmpassword;
    Button BtnSave;
    UserModel userModel;
    DatabaseReference databaseReference;
    private FirebaseUser user;
    ProgressBar progresChangePass, progresChangeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_profile);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        userModel = (UserModel) bundle.getSerializable("userModel");
        user = FirebaseAuth.getInstance().getCurrentUser();

        init();
        declare();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userModel.getUId().toString());
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progresChangeItem.setVisibility(View.VISIBLE);
                if (textMatch()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                    progresChangeItem.setVisibility(View.GONE);
                    builder.setMessage("تاكيد تعديل البيانات");
                    builder.setPositiveButton("تاكيد", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();


                            progresChangeItem.setVisibility(View.VISIBLE);
                            HashMap<String, Object> result = new HashMap<>();
                            result.put("uId", userModel.getUId().toString());
                            result.put("firstName", etFirstName.getText().toString());
                            result.put("lastName", etLastName.getText().toString());
                            result.put("email", etEmail.getText().toString());
                            result.put("phoneNumber", etPhoneNumber.getText().toString());
                            result.put("nickname", etNickName.getText().toString());
                            result.put("interest", etInterest.getText().toString());
                            result.put("image_profile", userModel.getImage_profile().toString());

                            databaseReference.setValue(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(EditProfileActivity.this, "تم التعديل ", Toast.LENGTH_SHORT).show();
                                        progresChangeItem.setVisibility(View.GONE);
                                        finish();
                                    } else {
                                        progresChangeItem.setVisibility(View.GONE);

                                        Toast.makeText(EditProfileActivity.this, "تاكد من البيانات ", Toast.LENGTH_SHORT).show();

                                    }
                                }


                            });
                        }
                    });
                    builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            progresChangeItem.setVisibility(View.GONE);

                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }


            }
        });


        dialogPassword = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        dialogPassword.setContentView(R.layout.changepassword);
        change = dialogPassword.findViewById(R.id.btnchangepss);
        Etoldpassword = dialogPassword.findViewById(R.id.etOldPasswordEditProfileActivity);
        Etnewpassword = dialogPassword.findViewById(R.id.etPasswordEditProfileActivity);
        Etconfirmpassword = dialogPassword.findViewById(R.id.etConfirmPasswordEditProfileActivity);
        progresChangePass = dialogPassword.findViewById(R.id.progressPasswordDialog);

        progresChangePass.setVisibility(View.GONE);

        text_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPassword.show();
                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progresChangePass.setVisibility(View.VISIBLE);

                        if
                        (Etconfirmpassword.getText().toString().length() < 6 || Etconfirmpassword.getText().toString().length() < 6 || Etconfirmpassword.getText().toString().length() < 6) {

                            Toast.makeText(EditProfileActivity.this, "برجاء ادخال البيانات صحيحه", Toast.LENGTH_SHORT).show();
                        } else {
                            if (Etnewpassword.getText().toString().equals(Etconfirmpassword.getText().toString())) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                                progresChangePass.setVisibility(View.GONE);

                                builder.setMessage("تاكيد تغيير كلمه المرور؟");

                                builder.setPositiveButton("تاكيد", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        progresChangePass.setVisibility(View.VISIBLE);

                                        final String email = user.getEmail();
                                        String oldpass = Etoldpassword.getText().toString();

                                        AuthCredential credential = EmailAuthProvider.getCredential(email, oldpass);
                                        System.out.println("email and pass = " + email + " " + oldpass);
                                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {


                                                    String newPass = Etnewpassword.getText().toString();
                                                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (!task.isSuccessful()) {
                                                                Toast.makeText(EditProfileActivity.this, "حدث خطا ", Toast.LENGTH_SHORT).show();
                                                                progresChangePass.setVisibility(View.GONE);
                                                            } else {
                                                                Toast.makeText(EditProfileActivity.this, "تم تغيير كلمه المرور", Toast.LENGTH_SHORT).show();
                                                                dialogPassword.hide();
                                                            }
                                                        }
                                                    });


                                                } else {
                                                    Toast.makeText(EditProfileActivity.this, "كلمه المرور غير صحيحه", Toast.LENGTH_SHORT).show();
                                                    progresChangePass.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                                    }
                                });

                                builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        progresChangePass.setVisibility(View.GONE);
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();


                            } else {
                                Toast.makeText(EditProfileActivity.this, "كلمه السر الجديده غير مطابقه", Toast.LENGTH_SHORT).show();
                                progresChangePass.setVisibility(View.GONE);

                            }

                        }

                    }
                });

            }
        });


    }

    public void init() {
        layout_information = findViewById(R.id.layoutInformationEditProfile);
        text_changePass = findViewById(R.id.textChangePasswordEditProfileActivity);
        etFirstName = findViewById(R.id.etFirstNameEditProfileActivity);
        etLastName = findViewById(R.id.etLastNameEditProfileActivity);
        etNickName = findViewById(R.id.etNickNameEditProfileActivity);
        etEmail = findViewById(R.id.etEmailEditProfileActivity);
        etPhoneNumber = findViewById(R.id.etPhoneNumberEditProfileActivity);
        etInterest = findViewById(R.id.etInterestEditProfileActivity);
        BtnSave = findViewById(R.id.btnSaveEditProfileActivity);
        progresChangeItem = findViewById(R.id.progressChangeItemEditProfileActivity);

    }

    public void declare() {
        etFirstName.setText(userModel.firstName + "");
        etLastName.setText(userModel.lastName + "");
        etNickName.setText(userModel.nickname + "");
        etEmail.setText(userModel.email + "");
        etPhoneNumber.setText(userModel.phoneNumber + "");
        etInterest.setText(userModel.interest + "");

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean textMatch() {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String neckName = etNickName.getText().toString();
        String email = etEmail.getText().toString();
//        String password = passwordTv.text.toString().trim()
//        String conPass = confirmPassTv.text.toString().trim()
        String phone = etPhoneNumber.getText().toString();
        String interistTv = etInterest.getText().toString();
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(neckName)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(interistTv)
        ) {
            progresChangeItem.setVisibility(View.GONE);
            Toast.makeText(EditProfileActivity.this, "من فضلك املأ الفراغات", Toast.LENGTH_SHORT).show();


        } else {
            if (phone.toString().length() < 7) {
                Toast.makeText(this, "برجاء ادخال رقم المحمول صحيح", Toast.LENGTH_SHORT).show();
            } else if (isEmailValid(email.toString())) {

                return true;
            } else {
                Toast.makeText(this, "برجاء ادخال الايميل بصيغه صحيحه", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
}