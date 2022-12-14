package www.oplibrary.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    TextView txtWelcome;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navView;
    SharedPreferences sharedPref1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        txtWelcome = findViewById(R.id.txtWelcome);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        sharedPref1 = getSharedPreferences("login_details", Context.MODE_PRIVATE);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setNavigationDrawer();
        String text = getString(R.string.welcome_message, sharedPref1.getString("USER_ID", ""));
        txtWelcome.setText(text);
    }

    private void setNavigationDrawer() {
        navView.setNavigationItemSelectedListener(item -> {
            Fragment frag = null;
            int itemId = item.getItemId();
            if (itemId == R.id.nav_add_book) {//frag
                frag = new AddBookFragment();
            } else if (itemId == R.id.nav_issue_book) {
                frag = new IssueBookFragment();
            } else if (itemId == R.id.nav_return_book) {
                frag = new ReturnBookFragment();
            } else if (itemId == R.id.nav_search_book) {
                frag = new SearchBookFragment();
            } else if (itemId == R.id.nav_list_books) {
                frag = new ListBooksFragment();
            } else if (itemId == R.id.nav_logout) {
                //logout
                SharedPreferences.Editor editor = sharedPref1.edit();
                editor.clear();
                editor.apply();
                finish();
                //redirect to login
                intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            if (frag != null) {
                FragmentTransaction frgTrans = getSupportFragmentManager().beginTransaction();
                frgTrans.replace(R.id.frame, frag);
                frgTrans.commit();
                mDrawerLayout.closeDrawers();
                return true;
            }
            return false;
        });
    }
}