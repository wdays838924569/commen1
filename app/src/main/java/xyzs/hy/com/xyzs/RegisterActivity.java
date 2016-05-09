package xyzs.hy.com.xyzs;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.*;
import android.view.View.*;
import android.view.*;

import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.exception.*;
import xyzs.hy.com.xyzs.entity.User;

import android.content.*;

public class RegisterActivity extends Activity implements OnClickListener {
    private Button btnCaptcha;
    private Button register;
    private EditText editUsername;
    private EditText editCaptcha;
    private EditText editPassword;
    private String phone;
    private String captchaCode;
    private String password;
    private String imagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        initLayout();
    }

    private void initLayout() {
        btnCaptcha = (Button) findViewById(R.id.btn_captcha);
        register = (Button) findViewById(R.id.btn_register);
        btnCaptcha.setOnClickListener(this);
        register.setOnClickListener(this);

        editUsername = (EditText) findViewById(R.id.edit_username);
        editCaptcha = (EditText) findViewById(R.id.edit_captcha);
        editPassword = (EditText) findViewById(R.id.edit_password);
    }

    private void register() {
//		final BmobFile bmobFile = new BmobFile(new File(imagePath));
//		bmobFile.uploadblock(this, new UploadFileListener() {
//				@Override
//				public void onSuccess()
//				{
        //String image = bmobFile.getFileUrl(RegisterActivity.this);
        User user = new User();
        user.setMobilePhoneNumber(phone);
        user.setPassword(password);
        //user.setHeadSculpture(image);
        user.signOrLogin(RegisterActivity.this, captchaCode, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int p1, String p2) {
                Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_SHORT)
                        .show();
            }
        });
//				}
//				@Override
//				public void onFailure(int p1, String p2)
//				{
//				}					
//			});
    }

    private void getCaptcha() {
        BmobSMS.requestSMSCode(this, phone, "XYZS_y", new RequestSMSCodeListener() {
            @Override
            public void done(Integer p1, BmobException p2) {
                if (p2 == null) {
                    Toast.makeText(getApplicationContext(), "短信已发送，注意查收！！", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "发送失败，请重试！", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    public void onClick(View p1) {
        phone = editUsername.getText().toString();
        captchaCode = editCaptcha.getText().toString();
        password = editPassword.getText().toString();
        switch (p1.getId()) {
            case R.id.btn_captcha:
                if (phone == null) {
                    Toast.makeText(getApplicationContext(), "手机号码不能为空！", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    getCaptcha();
                }
                break;
            case R.id.btn_register:
                if (phone == null && password == null && captchaCode == null) {
                    Toast.makeText(getApplicationContext(), "请完善密码和手机号！", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    register();
                }
                break;
        }
    }

}