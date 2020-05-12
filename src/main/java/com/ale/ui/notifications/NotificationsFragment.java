package com.ale.ui.notifications;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ale.R;

import java.io.IOException;
import java.lang.ref.WeakReference;

import util.HttpWeChatPosion;

public class NotificationsFragment extends Fragment {

    private Button get;
    private String result;
    private Boolean flag;
    private Handler handler = new Myhandler(this);

    static class Myhandler extends Handler {
        WeakReference<NotificationsFragment> mac;
        private ClipboardManager clipboardManager;
        private ClipData clipData;

        public Myhandler(NotificationsFragment notificationsFragment) {
            mac = new WeakReference<NotificationsFragment>(notificationsFragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            NotificationsFragment manager = mac.get();
            switch (msg.what) {
                case 1:
                    String result = (String) msg.obj;
                    TextView textView = manager.getActivity().findViewById(R.id.poison);
                    textView.setText(result);
                    TextView change = manager.getActivity().findViewById(R.id.chang_text);
                    change.setText("当前为毒鸡汤");
                    Toast.makeText(manager.getActivity(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                    clipboardManager = (ClipboardManager) manager.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipData = ClipData.newPlainText("Label", result);
                    clipboardManager.setPrimaryClip(clipData);
                    break;
                case 2:
                    String result1 = (String) msg.obj;
                    TextView textView1 = manager.getActivity().findViewById(R.id.wechat_result);
                    textView1.setText(result1);
                    TextView chang = manager.getActivity().findViewById(R.id.chang_text);
                    chang.setText("当前为朋友圈");
                    Toast.makeText(manager.getActivity(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                    clipboardManager = (ClipboardManager) manager.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipData = ClipData.newPlainText("Label", result1);
                    clipboardManager.setPrimaryClip(clipData);
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        flag = true;
        Switch aswitch = getActivity().findViewById(R.id.change_wechat_poison_);
        aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    flag = false;
                    Toast.makeText(getActivity(), "切换为朋友圈", Toast.LENGTH_SHORT).show();
                } else {
                    flag = true;
                    Toast.makeText(getActivity(), "切换为毒鸡汤", Toast.LENGTH_SHORT).show();
                }
            }
        });
        get = getActivity().findViewById(R.id.send);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (flag == true) {
                                result = HttpWeChatPosion.get(flag);
                                Message message = new Message();
                                message.what = 1;
                                message.obj = result;
                                handler.sendMessage(message);
                                Log.d("msg", "接收的消息" + result);
                            } else {
                                result = HttpWeChatPosion.get(flag);
                                Message message = new Message();
                                message.what = 2;
                                message.obj = result;
                                handler.sendMessage(message);
                                Log.d("msg", "接收的消息" + result);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}