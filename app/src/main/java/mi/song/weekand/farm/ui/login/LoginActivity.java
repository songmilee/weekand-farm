package mi.song.weekand.farm.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.ActivityLoginBinding;
import mi.song.weekand.farm.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginInterface.View {
    private final String TAG = LoginActivity.class.getSimpleName();

    private FirebaseAuth auth;
    private ActivityLoginBinding binding;

    private LoginInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();
        if(user != null)
            launchCorpActivity(user);
    }

    private void init(){
        presenter = new LoginPresenter(this);

        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.loginEmail.getText().toString();
            String pw = binding.loginPw.getText().toString();

            showProgress();

            if(!presenter.isValidateForm(email, pw)){
                sendMessage(getString(R.string.login_from_not_valid));
                dismissProgress();
                return;
            }
            presenter.loginWithAccount(auth, email, pw);
        });

        binding.signUpBtn.setOnClickListener(v -> {
            String email = binding.loginEmail.getText().toString();
            String pw = binding.loginPw.getText().toString();

            showProgress();
            if(!presenter.isValidateForm(email, pw)){
                sendMessage(getString(R.string.login_from_not_valid));
                dismissProgress();
                return;
            }
            presenter.createUserAccount(auth, email, pw);
        });
    }

    @Override
    public void launchCorpActivity(FirebaseUser user){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void setEmailFieldNotify(String msg) {
        binding.loginEmail.setError(msg);
    }

    @Override
    public void setPwFieldNotify(String msg) {
        binding.loginPw.setError(msg);
    }

    @Override
    public void showProgress() {
        binding.loginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        binding.loginProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void sendMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
