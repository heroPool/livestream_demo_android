package cn.ucai.live.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.easemob.livedemo.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.live.I;
import cn.ucai.live.net.IUserRegisterModel;
import cn.ucai.live.net.OnCompleteListener;
import cn.ucai.live.net.UserRegisterModel;
import cn.ucai.live.utils.CommonUtils;
import cn.ucai.live.utils.MD5;
import cn.ucai.live.utils.Result;
import cn.ucai.live.utils.ResultUtils;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.email)
    EditText text_username;
    @BindView(R.id.password)
    EditText text_password;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.usernick)
    EditText text_usernick;
    @BindView(R.id.comfirm_password)
    EditText confirmPassword;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;
    IUserRegisterModel usergisterModel;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        usergisterModel = new UserRegisterModel();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernick = text_usernick.getText().toString().trim();
                final String username = text_username.getText().toString().trim();
                final String pwd = text_password.getText().toString().trim();
                final String password = MD5.getMessageDigest(pwd);
                String confirm_pwd = confirmPassword.getText().toString().trim();
                if (inputCheck(usernick, username, password, confirm_pwd)) return;
//                if (TextUtils.isEmpty(usernick.getText())) {
//                    showToast("昵称不能为空");
//                    return;
//                }
//                if (TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(text_password.getText())) {
//                    showToast("用户名和密码不能为空");
//                    return;
//                }
//                if (!confirmPassword.getText().toString().equals(text_password.getText().toString())) {
//                    showToast("密码不一致");
//
//                    text_password.setText("");
//                    confirmPassword.setText("");
//                    text_password.requestFocus();
//                    return;
//                }

                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("正在注册...");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                //进行服务器注册
                registerAppServer(usernick, username, password);

            }
        });
    }

    private void registerAppServer(String usernick, final String username, final String password) {
        usergisterModel.register(RegisterActivity.this, username, usernick, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Result resultFromJson = ResultUtils.getResultFromJson(result, Result.class);
                    if (resultFromJson.isRetMsg()) {
                        //进行环信注册
                        registerEMServer(username, password);

                    } else if (resultFromJson.getRetCode() == I.MSG_REGISTER_USERNAME_EXISTS) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();

                    } else if (resultFromJson.getRetCode() == I.MSG_REGISTER_FAIL) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void SuperWeChatClientungister(String username) {
        usergisterModel.unregister(RegisterActivity.this, username, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(RegisterActivity.this, "服务端取消注册", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    private void registerEMServer(final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(username, password);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            showToast("注册成功");
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    SuperWeChatClientungister(username);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            showLongToast("注册失败：" + e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    private boolean inputCheck(String usernick, String username, String pwd, String confirm_pwd) {
        if (TextUtils.isEmpty(usernick)) {
            CommonUtils.showShortToast("昵称为空！");
            text_usernick.requestFocus();

            return true;
        } else if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
            this.text_username.requestFocus();
            return true;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            text_password.requestFocus();
            return true;
        } else if (TextUtils.isEmpty(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            confirmPassword.requestFocus();
            return true;
        } else if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
