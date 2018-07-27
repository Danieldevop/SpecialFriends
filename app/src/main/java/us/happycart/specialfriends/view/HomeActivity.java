package us.happycart.specialfriends.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import us.happycart.specialfriends.LoginActivity;
import us.happycart.specialfriends.R;

public class HomeActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView profileImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profileImage = findViewById(R.id.toolbar_fb_profile_image);
        profileImageName = findViewById(R.id.toolbar_title);

        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();

        }

        else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

            GraphRequest request = GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/picture",
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            Glide.with(getApplicationContext())
                                    .load(response.getRequest())
                                    .into(profileImage);
                        }
                    }
            );

            request.executeAsync();


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
}
