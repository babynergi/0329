package com.project.car_accident;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;

import static android.content.ContentValues.TAG;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    //Firebase
    private static final int RC_SIGN_IN = 10;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth firebaseAuth;

    //hashkey init
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hashkey
        mContext = getApplicationContext();
        getHashKey(mContext);

        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("73364439023-86eo8fnc21a9lhgu64e4a8f0r48kttm5.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton button = (SignInButton) findViewById(R.id.btn_googleSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click test");
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    @Nullable
    public static String getHashKey(Context context) {
        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }

        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }
    }

    //Intent Result 반환
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //RC_SIGN_IN
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess())
            {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();


                Log.d(TAG, "이름 =" + account.getDisplayName());
                Log.d(TAG, "이메일=" + account.getEmail());
                Log.d(TAG, "getId()=" + account.getId());
                Log.d(TAG, "getAccount()=" + account.getAccount());
                Log.d(TAG, "getIdToken()=" + account.getIdToken());


                firebaseAuthWithGoogle(account);
            } else {
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "아이디 생성완료", Toast.LENGTH_SHORT).show();
                        } else
                        {

                            Toast.makeText(getApplicationContext(), "아이디 생성실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onStart()
    { // 사용자가 현재 로그인되어 있는지 확인
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser!=null)
        { // 만약 로그인이 되어있으면 다음 액티비티 실행
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("알림", "onConnectionFailed");
    }


}