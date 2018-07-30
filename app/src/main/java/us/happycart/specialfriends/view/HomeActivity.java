package us.happycart.specialfriends.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import us.happycart.specialfriends.Adapters.FacebookFriendsAdapter;
import us.happycart.specialfriends.Adapters.FacebookFriendsAdapterHorizontal;
import us.happycart.specialfriends.LoginActivity;
import us.happycart.specialfriends.R;

public class HomeActivity extends AppCompatActivity {

    //RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String TAG = "HomeActivity";
    private CircleImageView profileImage;
    private TextView profileImageName;


    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

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
        } else {
            getName();
            getProfile();

            Log.d(TAG, "RECYCLER VIEW: STARTED.");
            initImagesBitmaps();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initImagesBitmaps() {
        Log.d(TAG, "initImagesBitmaps: preparing Bitmaps");

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Australia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

        initRecyclerView();
        initRecyclerHorizontal();
    }

    private void initRecyclerHorizontal() {
        Log.d(TAG, "THIS IS HORIZONTAL RECYCLER");
        mRecyclerView = findViewById(R.id.recycler_view_horizontal);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FacebookFriendsAdapterHorizontal(this, mImageUrls, mNames);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void initRecyclerView() {
        Log.d(TAG, "SETTING ADAPTER");
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FacebookFriendsAdapter(this, mImageUrls, mNames);
        mRecyclerView.setAdapter(mAdapter);
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
                        Log.d(TAG, "PROFILE INFO: " + response.getJSONObject());
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
