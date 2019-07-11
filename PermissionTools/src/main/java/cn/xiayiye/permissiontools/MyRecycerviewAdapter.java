package cn.xiayiye.permissiontools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cn.xiayiye.permissiontools.permission.PermissionConstants;
import cn.xiayiye.permissiontools.permission.PermissionFail;
import cn.xiayiye.permissiontools.permission.PermissionHelper;
import cn.xiayiye.permissiontools.permission.PermissionSucceed;

import static java.security.AccessController.getContext;

public class MyRecycerviewAdapter extends RecyclerView.Adapter<MyRecycerviewAdapter.ViewHolder> {

    private Activity mContext;

    public MyRecycerviewAdapter(Activity mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //.executeObj(MyRecycerviewAdapter.this)
                HashMap<String, Object> param = new HashMap<>();
                param.put("key", "这是一个附带参数的权限请求");
                PermissionHelper.with(mContext).requestCode(PermissionConstants.CAMERA).setParam(param).requestPermission(Manifest.permission.CAMERA).request();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_content);
        }

    }


    @PermissionSucceed(requestCode = PermissionConstants.CAMERA)
    private void callStorageWrite(HashMap<String, Object> param) {

        Toast.makeText(mContext, "Adapter回调您已经同意了这个请求" + param.get("key"), Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(requestCode = PermissionConstants.CAMERA)
    private void callStorageWriteFail(HashMap<String, Object> param) {

        Toast.makeText(mContext, "Adapter回调您已经拒绝了这个请求" + param.get("key"), Toast.LENGTH_SHORT).show();
    }

}
