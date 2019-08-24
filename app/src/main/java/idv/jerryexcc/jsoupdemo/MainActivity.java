package idv.jerryexcc.jsoupdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //匿名呼叫非同步執行
        //Jsoup需在背景執行網路request
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                //因可能有連線問題, 故需寫在try catch內
                try {
                    //建立連線, 並取得連線後的response
                    Connection.Response response = Jsoup.connect("https://www.google.com.tw").execute();
                    //取得的response轉成Document物件, 方便直接取得HTML的各個節點
                    Document doc = response.parse();
                    Log.d("TAG", "" + doc);

                    //取得<title>標籤內的值
                    String title = doc.title();
                    Log.d("TAG", "Title: " + title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
//        }.execute();
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}

/*
 * execute()
 * 建立一個queue並執行非同步任務
 * 假設你開了兩個任務並同時都用execute()
 * 系統就會把兩個任務丟入同一個queue內
 * 並逐一執行
 *
 * executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
 * 同一時間並行執行
 * 一樣是建立兩個任務
 * 兩個任務會同時一起執行
 *
 * */