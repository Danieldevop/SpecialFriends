package us.happycart.specialfriends.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import org.json.JSONException;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import us.happycart.specialfriends.LoginActivity;
import us.happycart.specialfriends.R;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private CircleImageView profileImage;
    private TextView profileImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        profileImage = findViewById(R.id.toolbar_fb_profile_image);
        profileImageName = findViewById(R.id.toolbar_title);

        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();

        }

        else {
            getName();
            getProfile();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void LogoutMenu(MenuItem item) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    public Object getName() {

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            profileImageName.setText(response.getJSONObject().getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );



        return request.executeAsync();
    }

    public Object getProfile() {

        Bundle params = new Bundle();
        params.putBoolean("redirect", false);

        return new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me/picture",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e(TAG, "PROFILE INFO: " + response.getJSONObject());
                        try {
                            Glide.with(getApplicationContext())
                                    .load(response.getJSONObject().getJSONObject("data").get("url"))
                                    .into(profileImage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
}
