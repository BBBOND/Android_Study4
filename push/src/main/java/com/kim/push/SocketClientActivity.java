package com.kim.push;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 此部分与极光推送无关是自定义Socket连接
 * Created by 伟阳 on 2016/2/24.
 */
public class SocketClientActivity extends AppCompatActivity {

    private static final String TAG = "SocketClientActivity";

    private EditText etIp;
    private EditText etText;
    private ToggleButton tBtnLink;
    private Button btnSend;

    private Socket socket = null;
    private BufferedWriter writer = null;
    private BufferedReader reader = null;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.obj != null) {
                Toast.makeText(SocketClientActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);

        init();
        initEvent();
//        start();
    }

    private void init() {
        etIp = (EditText) findViewById(R.id.et_ip);
        etIp.setText("127.0.0.1");
        etText = (EditText) findViewById(R.id.et_text);
        tBtnLink = (ToggleButton) findViewById(R.id.tbtn_link);
        btnSend = (Button) findViewById(R.id.btn_send);
    }

    private void initEvent() {
        tBtnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked()) {
                    startSocket();
                } else {
                    stopSocket();
                }
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tBtnLink.isChecked()) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                writer.write(etText.getText().toString());
                                writer.newLine();
                                writer.flush();
                                Message message = handler.obtainMessage();
                                message.obj = reader.readLine();
                                message.sendToTarget();
                                Log.d(TAG, "发送成功");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    private void startSocket() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(etIp.getText().toString(), 9090);
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Log.d(TAG, "连接成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void stopSocket() {
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
            if (writer != null) {
                writer.close();
                writer = null;
            }
            if (reader != null) {
                reader.close();
                reader = null;
            }
            Log.d(TAG, "断开成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() {
        //此为java控制台用法
//        BufferedReader inputReader;
//        try {
//            inputReader = new BufferedReader(new InputStreamReader(System.in));
//            String inputContent;
//            while (!(inputContent = inputReader.readLine()).equals("bye")) {
//                Log.i(TAG, "inputContent: " + inputContent);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
