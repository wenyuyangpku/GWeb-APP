package com.example.wenyuyang.gwebapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements CoursesAdapter.ListItemListener {

    private String mGWid = "G33274971";
    private String mPassword = "199626";

    //The count of courses
    private RecyclerView mCoursesList;
    private CoursesAdapter mCourseAdapter;

    public static List<CourseInfo> mClassCourses = new ArrayList<>();
    public static List<SelectCourseInfo> mSelectCoursesInfo = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("GWid")) {
            mGWid = intentThatStartedThisActivity.getStringExtra("GWid");
        }
        if (intentThatStartedThisActivity.hasExtra("Password")) {
            mPassword = intentThatStartedThisActivity.getStringExtra("Password");
        }

        readSelectCourseInfo(getResources().openRawResource(R.raw.course_detail));

        loadCourses mLoadTask = new loadCourses();
        mLoadTask.execute((Void) null);
    }

    //Show onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {

            case R.id.nav_add_course: {
                Context context = MainActivity.this;
                Class destinationActivity = SelectCoursesActivity.class;
                Intent startLogin = new Intent(context, destinationActivity);
                startLogin.putExtra("GWid","G33274971");
                startLogin.putExtra("Password","199626");
                startActivity(startLogin);
                return true;
            }
            case R.id.nav_now_course:{
                System.out.println("ffffffffffffffffff");
                return true;
            }
            case R.id.nav_logout:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }



    private List<String> getCourses() {
        //A HashMap to store cookies
        final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        //A HttpClient to login in Gweb
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {

            //Save cookies
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                //Remove the Proxy
                List<Cookie> noProxyCookies = new ArrayList<>();
                for (Cookie cookie : cookies) {
                    if (!cookie.name().contains("PROXY_HASH"))
                        noProxyCookies.add(cookie);
                }

                //Clear cookies store and put new cookies in the store
                cookieStore.clear();
                cookieStore.put(url.host(), noProxyCookies);
            }

            //Load cookies
            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        }).build();

        //Login Gweb and get cookies
        //First website Get()
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.Gweb1_WWW))
                .get()
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Second Gweb Post()
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        //TODO:change id and password
        RequestBody body = RequestBody.create(mediaType, "PIN=" + mPassword
                + "&sid=" + mGWid);
        request = new Request.Builder()
                .url(getResources().getString(R.string.Gweb2_Val))
                .post(body)
                .addHeader("Referer", getResources().getString(R.string.Gweb1_WWW))
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .addHeader("Accept-Encoding", "identity")
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Third Gweb Get()
        request = new Request.Builder()
                .url(getResources().getString((R.string.Gweb3_User)))
                .get()
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Fourth Gweb Get()
        request = new Request.Builder()
                .url(getResources().getString(R.string.Gweb4_Menu))
                .get()
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Fifth Gweb Get()
        request = new Request.Builder()
                .url(getResources().getString(R.string.Gweb5_Reg))
                .get()
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Sixth Gweb Get()
        request = new Request.Builder()
                .url(getResources().getString(R.string.Gweb6_Detl))
                .get()
                .addHeader("Referer", getResources().getString(R.string.Gweb5_Reg))
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Seventh Gweb Post()
        mediaType = MediaType.parse("application/x-www-form-urlencoded");
        body = RequestBody.create(mediaType, "term_in=201803");
        request = new Request.Builder()
                .url(getResources().getString(R.string.Gweb6_Detl))
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Referer", getResources().getString(R.string.Gweb7_Crs))
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
            getCoursesInformationFromHtml(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Jsoup the Html
    private void getCoursesInformationFromHtml(String html) {

        //clear mCourseList
        mClassCourses.clear();

        org.jsoup.nodes.Document doc = Jsoup.parse(html);

        //cssQuery for the course information
        Elements tables = doc.select("table.datadisplaytable");


        //tag to type of value
        int typeTag = 0;

        CourseInfo courseInfo = new CourseInfo();
        for (org.jsoup.nodes.Element table : tables) {

            typeTag++;
            if (typeTag % 2 == 1) {
                courseInfo = new CourseInfo();
                courseInfo.name = table.child(0).text();

            }
            //tag to record information
            int tag = 0;

            for (int index = 0; index < table.children().size(); index++) {

                //name of course

                for (org.jsoup.nodes.Element child : table.child(index).children()) {
                    for (org.jsoup.nodes.Element grandchild : child.children()) {
                        tag++;
                        courseInfo.changeCourseInfo(typeTag, tag, grandchild.text());
                    }
                }
            }
            if (typeTag % 2 == 0)
                mClassCourses.add(courseInfo);
        }
    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {

        Context context = MainActivity.this;
        Class destinationActivity = DetailCourseActivity.class;
        Intent showCourseDetail = new Intent(context, destinationActivity);
        showCourseDetail.putExtra("item", clickedItemIndex);
        startActivity(showCourseDetail);
    }


    private class loadCourses extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            return getCourses();
        }

        @Override
        protected void onPostExecute(List<String> s) {
            super.onPostExecute(s);
            loadCourseInformationToRecycleview();
        }
    }

    //Loading the course information to Recycleview
    private void loadCourseInformationToRecycleview() {
        mCoursesList = (RecyclerView) findViewById(R.id.rv_Courses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCoursesList.setLayoutManager(layoutManager);
        mCourseAdapter = new CoursesAdapter(mClassCourses.size(), this);
        mCoursesList.setAdapter(mCourseAdapter);
    }

    //Reading the select courses information
    public void readSelectCourseInfo(InputStream inputStream) {

        InputStreamReader inputStreamReader = null;
        inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                SelectCourseInfo selectCourse = new SelectCourseInfo();
                selectCourse.CRN = line;
                line = reader.readLine();
                selectCourse.Subject = line;
                line = reader.readLine();
                selectCourse.Course = line;
                line = reader.readLine();
                selectCourse.Section = line;
                line = reader.readLine();
                selectCourse.Campus = line;
                line = reader.readLine();
                selectCourse.Credit = line;
                line = reader.readLine();
                selectCourse.Title = line;
                line = reader.readLine();
                selectCourse.Days = line;
                line = reader.readLine();
                selectCourse.Time = line;
                line = reader.readLine();
                selectCourse.SectionCapacity = line;
                line = reader.readLine();
                selectCourse.SectionActual = line;
                line = reader.readLine();
                selectCourse.SectionRemaining = line;
                line = reader.readLine();
                selectCourse.WaitlistCapacity = line;
                line = reader.readLine();
                selectCourse.WaitlistActual = line;
                line = reader.readLine();
                selectCourse.WaitlistRemaining = line;
                line = reader.readLine();
                selectCourse.CrosslistCapacity = line;
                line = reader.readLine();
                selectCourse.CrosslistActual = line;
                line = reader.readLine();
                selectCourse.CrosslistRemaining = line;
                line = reader.readLine();
                selectCourse.Instructor = line;
                line = reader.readLine();
                selectCourse.Date = line;
                line = reader.readLine();
                selectCourse.Location = line;
                line = reader.readLine();
                selectCourse.Attribute = line;
                line = reader.readLine();
                selectCourse.Detail = line;
                mSelectCoursesInfo.add(selectCourse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("#######################");
    }

}
